package com.rx.pub.file.controller;

//import com.alibaba.simpleimage.ImageRender;
//import com.alibaba.simpleimage.SimpleImageException;
//import com.alibaba.simpleimage.render.ReadRender;
//import com.alibaba.simpleimage.render.ScaleParameter;
//import com.alibaba.simpleimage.render.ScaleRender;
//import com.alibaba.simpleimage.render.WriteRender;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.rx.base.file.FileAccessEnum;
import com.rx.base.file.RxFilePersistencer;
import com.rx.base.page.Pager;
import com.rx.base.result.DataResult;
import com.rx.base.result.type.BusinessException;
import com.rx.base.result.type.ValidateException;
import com.rx.base.utils.StringUtil;
import com.rx.base.cache.CacheHelper;
import com.rx.ext.annotation.ExtClass;
import com.rx.extrx.spring.SpringProvider;
import com.rx.pub.file.base.RxFilePathParam;
import com.rx.pub.file.dto.UploadDto;
import com.rx.pub.file.po.RxFile;
import com.rx.pub.file.service.RxFileService;
import com.rx.pub.file.utils.FileConverter;
import com.rx.pub.file.utils.ImageUtil;
import com.rx.pub.file.vo.UploadParam;
import com.rx.spring.utils.SpringContextHelper;
import com.rx.web.user.RxPermission;
import com.rx.web.user.RxUser;
import com.rx.web.utils.HttpServletHelper;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/file")
@ExtClass(extend = SpringProvider.class, alternateClassName = "FileController")
public class FileController {
	
    @Autowired
    private RxFileService rxFileService;

    /**
     * 列出
     * @return
     * @throws Exception
     */
    @RxPermission
    @RequestMapping("/list")
    public DataResult list(UploadParam uParam) throws Exception{
    	 //vo.setOwner(RxUser.getUserId());
    	 //vo.setOwnerType(RxUser.getUser().getClass().getName());
        //return new DataResult(rxFileService.listMine(vo));
    	
    	HttpServletRequest request = HttpServletHelper.getRequest();
    	Pager<RxFile> page = new Pager<RxFile>();
    	
    	String cp = request.getParameter("page");
        if (cp == null || "".equals(cp.trim())) cp = "1";
        page.setPageNum(Integer.parseInt(cp));
        String ps = request.getParameter("limit");
        if (ps == null || ps.trim().equals(""))
            ps = request.getParameter("pageSize");
        if (ps == null || "".equals(ps.trim())) {
        } else {
            page.setPageSize(Integer.parseInt(ps));
        }
    	
    	
    	java.util.List<RxFile> res = new ArrayList<RxFile>();
    	if(org.springframework.util.StringUtils.hasText(uParam.getReuseKey())){
    		String reuse = uParam.getReuseKey();
    		Object reuseObj = CacheHelper.getCacher().getObject(reuse);
    		if(reuseObj != null) {
    			ArrayList<String> list = (ArrayList<String>)reuseObj;
    			page.setTotal(list.size());
    			//PathParam param = new PathParam();
    			//param.setHeight(160);
    			//param.setWidth(160);
    			long start = page.getStartRow();
    			int index = 0;
    			long end = start + page.getPageSize();
    			
    			ArrayList<String> rList = new ArrayList<String>();
    			rList.addAll(list);
    			Collections.reverse(rList);
    			for(String str : rList) {
    				if(start <= index && index < end) {
	    				RxFile f = new RxFile();
	    				f.setId(str);
	    				f.setSrc(FileConverter.getFullPathForPath(str,null));
	    				f.setFileName(str);
	    				res.add(f);
    				}
    				index++;
    			}
    		}
    	}
    	page.setList(res);
    	return new DataResult(page);
    }

    /**
     * 单文件上传带业务，入库
     * @param file				文件对象
     * @param filePrefix		文件前缀，用于添加目录
     * @param fileAccess		访问权限
     * @param typeSuffix		文件类型后缀
     * @param contentTypes		content-type范围
     * @param fileValidateMsg	验证信息
     * @param width				宽度（当typeSuffix为image时有效）
     * @param height			高度（当typeSuffix为image时有效）
     * @param mark				标记
     * @param prop				文件属性
     * @return
     * @throws Exception
     */
    @RxPermission
    @RequestMapping("/upload/business")
    public DataResult uploadBusImage(MultipartFile file, String filePrefix, Integer fileAccess, 
    		String typeSuffix, String[] contentTypes, String fileValidateMsg, Integer width, Integer height,
    		String mark, String prop) throws Exception{
    	if(file == null) return new BusinessException("请选择文件上传");
    	String contentType = file.getContentType();
		// 验证文件
        validateFile(typeSuffix, contentTypes, fileValidateMsg, contentType);
        String fileName = file.getOriginalFilename();
        String fileExtName = FilenameUtils.getExtension(fileName).trim();
        long size = file.getSize();
        // 压缩图片
        InputStream is;
        if("image".equals(typeSuffix) && ((width != null && width.intValue() != 0) || (height != null && height.intValue() != 0))) {
        	ByteArrayInputStream byteIn = new ByteArrayInputStream(ImageUtil.scaleImage(file.getBytes(), width, height));
        	is = byteIn;
        }else {
        	is = file.getInputStream();
        }
        // 流转字节
        byte[] in_b;
    	try{
	    	ByteArrayOutputStream swapStream = new ByteArrayOutputStream(); 
	    	byte[] buff = new byte[100];
	    	int rc = 0; 
	    	while ((rc = is.read(buff, 0, 100)) > 0) { 
	    		swapStream.write(buff, 0, rc); 
	    	} 
	    	in_b = swapStream.toByteArray();
	    	swapStream.close();
	    	is.close();
    	}catch(Exception e){
    		throw new BusinessException("读取文件流异常.");
    	}
    	// 持久化图片
		String path = SpringContextHelper.getBean(RxFilePersistencer.class).save(FileAccessEnum.findByValue(fileAccess,FileAccessEnum.公共读),in_b,filePrefix,fileExtName);
        String id = DigestUtils.md5DigestAsHex(in_b);
    	RxFile record = rxFileService.selectByPrimaryKey(id);
    	RxFile record2 = new RxFile();
		record2.setPath(path);
    	record2.setOwner(RxUser.getUser().getId());
    	record2.setOwnerType(RxUser.getUser().getClass().getName());
    	record2.setFileName(fileName);
    	record2.setContentType(contentType);
    	record2.setUploadSize(size);
    	record2.setMark(mark);
    	record2.setProp(prop);
    	if(record == null){
    		record2.setId(id);
    	}else{
    		record2.setId(UUID.randomUUID().toString());
    	}
        // 入库
        UploadDto dto = rxFileService.addUpload(record2);
        return new DataResult(dto);
    }
	
    /**
     * base64文件上传，不入库
     * @param fileString		文件文本流
     * @param fileName			文件名
     * @param contentType		content-type类型
     * @param filePrefix		文件前缀，用于添加目录
     * @param fileAccess		访问权限
     * @param typeSuffix		文件类型后缀
     * @param contentTypes		content-type范围
     * @param fileValidateMsg	验证信息
     * @param width				宽度（当typeSuffix为image时有效）
     * @param height			高度（当typeSuffix为image时有效）
     * @return
     * @throws Exception
     */
    @RxPermission
	@RequestMapping("/upload/base64")
	public DataResult uploadBase64File(Integer fileAccess, String fileString, String fileName, String filePrefix,
			String contentType, String typeSuffix, String[] contentTypes, String fileValidateMsg, Integer width, Integer height) throws Exception {
		if(!org.springframework.util.StringUtils.hasText(fileString)) return new BusinessException("请选择文件上传");
		// 验证文件
        validateFile(typeSuffix, contentTypes, fileValidateMsg, contentType);
        byte[] bytes = Base64.decodeBase64(fileString);
        //long size = bytes.length;
        String fileExtName = FilenameUtils.getExtension(fileName).trim();
        // 压缩图片
        if("image".equals(typeSuffix) && ((width != null && width.intValue() != 0) || (height != null && height.intValue() != 0))) {
        	bytes = ImageUtil.scaleImage(bytes, width, height);
        }
        return new DataResult(SpringContextHelper.getBean(RxFilePersistencer.class).save(FileAccessEnum.findByValue(fileAccess,FileAccessEnum.公共读 ),bytes,filePrefix,fileExtName));
	}
	
	/**
	 * 单文件上传，不入库
	 * @param file				文件对象
     * @param fileAccess		访问权限
     * @param filePrefix		文件前缀，用于添加目录
     * @param typeSuffix		文件类型后缀
     * @param contentTypes		content-type范围
     * @param fileValidateMsg	验证信息
     * @param width				宽度（当typeSuffix为image时有效）
     * @param height			高度（当typeSuffix为image时有效）
	 * @return
	 * @throws Exception
	 */
    //@Permission
    //@RxPermission
    @RequestMapping("/upload")
	public DataResult uploadFile(MultipartFile file, UploadParam uParam) throws Exception {
    	if(null == file)return new BusinessException("请选择文件上传");
        //取得当前上传文件的文件名称
        String contentType = file.getContentType();
        // 验证文件
        validateFile(uParam.getTypeSuffix(), uParam.getContentTypes(), uParam.getFileValidateMsg(), contentType);
        String fileName = file.getOriginalFilename();
        String fileExtName = fileName.substring(fileName.lastIndexOf(".")+1).trim();
        //long size = file.getSize();
        InputStream is;
        // 压缩图片
        if("image".equals(uParam.getTypeSuffix()) && ((uParam.getWidth() != null && uParam.getWidth().intValue() != 0) || (uParam.getHeight() != null && uParam.getHeight().intValue() != 0))) {
        	ByteArrayInputStream byteIn = new ByteArrayInputStream(ImageUtil.scaleImage(file.getBytes(), uParam.getWidth(), uParam.getHeight()));
        	is = byteIn;
        }else {
        	is = file.getInputStream();
        }
        // 流转字节
        byte[] in_b;
    	try{
	    	ByteArrayOutputStream swapStream = new ByteArrayOutputStream(); 
	    	byte[] buff = new byte[100];
	    	int rc = 0; 
	    	while ((rc = is.read(buff, 0, 100)) > 0) { 
	    		swapStream.write(buff, 0, rc); 
	    	} 
	    	in_b = swapStream.toByteArray();
	    	swapStream.close();
	    	is.close();
    	}catch(Exception e){
    		throw new BusinessException("读取文件流异常.");
    	}
    	
    	String path = SpringContextHelper.getBean(RxFilePersistencer.class).save(FileAccessEnum.findByValue(uParam.getFileAccess(),FileAccessEnum.公共读), in_b, uParam.getFilePrefix(), fileExtName);

    	/*if(!uParam.isFullPath()) {
    		path = FileConverter.getFullPathForPath(path,null);
    		//FileConverter.pathConverter.convert(path,null);
    	}//获取不到小程序传的fullPath,目前暂时都是传递全路径*/
		path = FileConverter.getFullPathForPath(path,null);

    	if(org.springframework.util.StringUtils.hasText(uParam.getReuseKey())){
    		String reuse = uParam.getReuseKey();
    		Object reuseObj = CacheHelper.getCacher().getObject(reuse);
    		ArrayList<String> list = null;
    		if(reuseObj != null) {
    			list = (ArrayList<String>)reuseObj;
    		}else {
    			list = new ArrayList<String>();
    		}
    		list.add(path);
    		CacheHelper.getCacher().put(reuse, list);
    	}
    	
        return new DataResult("上传成功",path);
    }

    
    
    @RxPermission
    @RequestMapping("/getPath")
    public DataResult getPath(String path,RxFilePathParam param) throws Exception{
    	String fullPath = FileConverter.getFullPathForPath(path,param);
        return new DataResult().setData(fullPath);
    }
    
    
    /**
     * 下载文件
     * @return
     * @throws Exception
     */
    @RequestMapping("/download2/{id}")
    public void downloadFile2(@PathVariable("id") String id) throws Exception{
    	
    	Assert.notNull(id,"id不能为空");
    	RxFile record = rxFileService.selectByPrimaryKey(id);
    	Assert.notNull(record,"没有对应的文件资源");
    	
    	HttpServletRequest request = HttpServletHelper.getRequest();
    	String tmpdir = request.getServletContext().getRealPath("/downloadTempDir");
    	
    	File tmpdirF = new File(tmpdir);
    	if(!tmpdirF.isDirectory()){
    		tmpdirF.mkdir();
    	}
    	String name = id +".tmp";
	File f = new File(tmpdirF,name);
	HttpServletResponse response = HttpServletHelper.getResponse();
	response.setHeader("Content-Type",record.getContentType()+";filename="+URLEncoder.encode(record.getFileName(), "UTF-8"));
		
    	if(!f.exists()){
    		byte[] bs = record.fileBytes();
    		if(bs == null){
    			throw new ValidateException("未成功下载到文件");
    		}
    		f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bs);
            fos.flush();
            IOUtils.closeQuietly(fos);
    	}
    	FileInputStream in = new FileInputStream(f);
        OutputStream out = response.getOutputStream();
        byte buffer[] = new byte[1024];
        int len = 0;
        while((len = in.read(buffer)) > 0){
            out.write(buffer, 0, len);
        }
        IOUtils.closeQuietly(in);
    }
    
    /**
     * 下载图片
     * @return
     * @throws Exception
     */
    @RequestMapping("/download/{id}")
    public void downloadFile(@PathVariable("id") String id) throws Exception{
    	
    	Assert.notNull(id,"id不能为空");
    	RxFile record = rxFileService.selectByPrimaryKey(id);
    	Assert.notNull(record,"没有对应的图片资源");
    	
    	HttpServletRequest request = HttpServletHelper.getRequest();
    	String tmpdir = request.getServletContext().getRealPath("/downloadTempDir");
    	
    	File tmpdirF = new File(tmpdir);
    	if(!tmpdirF.isDirectory()){
    		tmpdirF.mkdir();
    	}
    	
    	int w = 0;
    	int h = 0;
    	String wh = request.getParameter("w");
    	if(!StringUtil.isNull(wh)){
    		w = Integer.parseInt(wh);
    	}
    	String ht = request.getParameter("h");
    	if(!StringUtil.isNull(ht)){
    		h = Integer.parseInt(ht);
    	}
    	
    	
    	boolean scale = w != 0 || h != 0;
    	
    	String name = id +".tmp";
    	if(scale){//有参数的情况
    		name = id + "_" + w + "-" + h +".tmp";
    	}
	File f = new File(tmpdirF,name);
		
	String contentType = record.getContentType();
	String fileName = record.getFileName();
		
    	if(!f.exists()){
    		byte[] bs = record.fileBytes();
    		if(bs == null){
    			throw new ValidateException("未成功下载到文件");
    		}
    		f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            if(scale){
            	String fileExtName = fileName.substring(fileName.lastIndexOf(".")+1).trim();
            	if(!createScaleImage2(bs,fos,fileExtName,w,h)){
            		contentType = "image/jpeg";
            	}
            }else {
                fos.write(bs);
            }
            fos.flush();
            IOUtils.closeQuietly(fos);
    	}
    	
    	FileInputStream in = new FileInputStream(f);
    	HttpServletResponse response = HttpServletHelper.getResponse();
    	response.setHeader("Content-Type",contentType+";filename="+URLEncoder.encode(fileName, "UTF-8"));
    	
        OutputStream out = response.getOutputStream();
        byte buffer[] = new byte[1024];
        int len = 0;
        while((len = in.read(buffer)) > 0){
            out.write(buffer, 0, len);
        }
        IOUtils.closeQuietly(in);
        //out.close();
    }

    /**
     * 验证文件contentType是否以typeSuffix结尾，或在contentTypes数组范围内
     * @param typeSuffix	类型后缀
     * @param contentTypes	contentType范围
     * @param fileValidateMsg	验证信息
     * @param contentType	contentType类型
     */
	private void validateFile(String typeSuffix, String[] contentTypes, String fileValidateMsg, String contentType) {
		if(org.springframework.util.StringUtils.hasText(typeSuffix)){
            if(!contentType.startsWith(typeSuffix)){
                if(typeSuffix.equals("image")){
                    if(fileValidateMsg == null){
                        fileValidateMsg = "请上传图片";
                    }
                }
                throw new BusinessException(fileValidateMsg);
            }
        }
        if(fileValidateMsg == null){
            fileValidateMsg = "请上传正确的文件格式";
        }
        if(contentTypes != null && contentTypes.length > 0){
            boolean e = true;
            for(String ct : contentTypes){
                if(ct.equalsIgnoreCase(contentType)){
                    e = false;
                    break;
                }
            }
            if(e){
                throw new BusinessException(fileValidateMsg);
            }
        }
	}
    
    private static InputStream fixExifImage(InputStream inputStream) throws IOException, ImageProcessingException {
        int orientation = 1;
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
            Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
        }catch (Exception ignore) {
        }
        Integer turn = 360;
        if(orientation == 0||orientation == 1) {
            return inputStream;
        } else if(orientation == 3) {
            turn = 180;
        } else if(orientation == 6) {
            turn = 90;
        } else if(orientation == 8) {
            turn = 270;
        }
        try {
            BufferedImage src = ImageIO.read(inputStream);
            byte[] des = ImageUtil.spin(src, turn);
            return new ByteArrayInputStream(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputStream;
    }
    
    public boolean createScaleImage2(byte[] bs,FileOutputStream fos,String suffixName,int w,int h) throws IOException{
    	boolean res = true;
    	ByteArrayInputStream insteam = new ByteArrayInputStream(bs);
        BufferedImage image = ImageIO.read(insteam);
        if(image == null){
        	/*
        	image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
             // 获取图形上下文
             Graphics g = image.createGraphics();
             // 设定图像背景色(因为是做背景，所以偏淡)
             g.setColor(new Color(255, 255, 255));
             g.fillRect(0, 0, w, h);
             // 设置字体
             g.setFont(new Font("Times New Roman", Font.PLAIN, 50));
             g.drawString(suffixName, 0, 0);
             // 图象生效
             g.dispose();
            */
        	//image = ImageIO.read(new ByteArrayInputStream(Base64Utils.decodeFromUrlSafeString("R0lGODlhAQABAID_AMDAwAAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw")));
        	res = false;
        }
        if(res)
        {
	        ImageIcon imageIcon = new ImageIcon(image);
	        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
	        Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
	        Image im = imageIcon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
	        g2D.drawImage(im, 0, 0, imageIcon.getImageObserver());
	        ImageIO.write(bufferedImage, "png", fos);
    	}else{
    		
            BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2D = (Graphics2D) bufferedImage.getGraphics();
            g2D.setColor(new Color(255, 255, 255));
            g2D.setFont(new Font("Times New Roman", Font.PLAIN, 30));
            g2D.drawString(suffixName, 10,30);
            ImageIO.write(bufferedImage,"png", fos);
    	}
        //g2D.drawImage(bufferedImage, 0, 0, imageIcon.getImageObserver());
    	return res;
    }
    
    /*
	public static boolean createScaleImage(InputStream inStream,File out,int w,int h){
	if(!out.isFile()){
        	try {
				out.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
        }
        ScaleParameter scaleParam = new ScaleParameter();  //将图像缩略到1024x1024以内，不足1024x1024则不做任何处理  
        if(w != 0){
        	scaleParam.setMaxWidth(w);
        }
        if(h != 0){
        	scaleParam.setMaxHeight(h);
        }
        FileOutputStream outStream = null;  
        WriteRender wr = null;  
        boolean ok = true;
        try {
            outStream = new FileOutputStream(out);  
            ImageRender rr = new ReadRender(inStream);  
            ImageRender sr = new ScaleRender(rr, scaleParam);  
            wr = new WriteRender(sr, outStream);
            wr.render();                            //触发图像处理  
        } catch(Exception e) {  
            e.printStackTrace();
            ok = false;
        } finally {  
            IOUtils.closeQuietly(inStream);         //图片文件输入输出流必须记得关闭  
            IOUtils.closeQuietly(outStream);  
            if (wr != null) {  
                try {  
                    wr.dispose();                   //释放simpleImage的内部资源  
                } catch (SimpleImageException ignore) {  
                    // skip ...   
                }  
            }  
        }
        return ok;
	}
	*/
	
	/*private DataResult uploadOperate3333333333333333333(FileAccessEnum fileAccess,InputStream fileInput, String typeSuffix, String[] contentTypes,String fileValidateMsg, String contentType, String fileName, long size) throws Exception{
	validateFile(typeSuffix, contentTypes, fileValidateMsg, contentType);
    
	    byte[] in_b;
		try{
	    	ByteArrayOutputStream swapStream = new ByteArrayOutputStream(); 
	    	byte[] buff = new byte[100];
	    	int rc = 0; 
	    	while ((rc = fileInput.read(buff, 0, 100)) > 0) { 
	    		swapStream.write(buff, 0, rc); 
	    	} 
	    	in_b = swapStream.toByteArray();
	    	
	    	swapStream.close();
		}catch(Exception e){
			throw new BusinessException("读取文件流异常.");
		}
		
		String id = DigestUtils.md5DigestAsHex(in_b);
		RxFile record = rxFileService.selectByPrimaryKey(id);
		RxFile record2 = new RxFile();
		record2.setOwner(RxUser.getUserId());
		record2.setFileName(fileName);
		record2.setContentType(contentType);
		record2.setUploadSize(size);
		record2.setCreateTime(new Date());
		if(record == null){
			//String fileExtName = fileName.substring(fileName.lastIndexOf(".")+1).trim();
			//String path = FastdfsClient.uploadFile(in_b, fileExtName);
			
			String key = FilePersistence.getFilePersistencer().save(fileAccess,in_b);
			
			record2.setPath(key);
			//if(path == null){
			//	throw new BusinessException("保存文件出错.");
			//}
			record2.setId(id);
			//record2.setPath(path);
		}else{
			record2.setId(UUID.randomUUID().toString());
			record2.setPath(record.getPath());
		}
		
	    UploadVo vo = rxFileService.addUpload(record2);
	    return new DataResult(vo);
	}*/
}
