package com.rx.pub.oss.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.rx.base.cache.CacheHelper;
import com.rx.base.file.FileAccessEnum;
import com.rx.base.result.DataResult;
import com.rx.base.result.type.BusinessException;
import com.rx.pub.oss.utils.OssHelper;
import com.rx.pub.oss.vo.OssVo;
import com.rx.web.user.RxPermission;

@RestController
@RequestMapping("/oss")
public class OssController {
	
    //@Resource
    //private CommonsMultipartResolver commonsMultipartResolver;

    /**
    *@Description: 文件上传
    *@Param: 
    *@return: 
    */
    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    @RxPermission()
    public DataResult uploadFile(@RequestParam("file") MultipartFile[] files, HttpServletRequest request, Integer accessCode, String filePerfix) throws Exception{
        List<OssVo> list = new CopyOnWriteArrayList<OssVo>();
        //String str = PropertiesHelper.getValue("BASE_URL");
        // 获取访问权限枚举
        FileAccessEnum accessEnum = FileAccessEnum.findByValue(accessCode, FileAccessEnum.公共读);
        //判断 request 是否有文件上传,即多部分请求
        if( files != null && files.length > 0){
            for (MultipartFile file : files) {
                //取得当前上传文件的文件名称
                String srcFileName = file.getOriginalFilename();
                //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                if(StringUtils.hasText(srcFileName)){
                    //FileTypeEnum type = FileTypeEnum.suffixFileName(srcFileName);
                	//if(type==null) throw new BusinessException(String.format("不支持文件:%s 类型上传", srcFileName));
                	String extension = null;//FilenameUtils.getExtension(srcFileName);
                    String randomFileName = OssHelper.simpleUpload(file.getInputStream(), extension, accessEnum, filePerfix);
                    OssVo vo = new OssVo();
                    vo.setSrcName(srcFileName);
                    vo.setUrl(randomFileName);
                    vo.setFullUrl(OssHelper.getUrl(randomFileName));
                    vo.setFileSize(file.getSize());
                    list.add(vo);
                }
            }
        }
        if(list.size()<=0){
            throw new BusinessException("无文件上传");
        }else{
            return new DataResult(list);
        }
    }

    /**
     * 获取临时token
     * @return
     * @throws Exception
     */
    @RequestMapping("/token")
    @ResponseBody
    @RxPermission()
    public DataResult token() throws Exception{
    	Map<String, String> respMap = new LinkedHashMap<String, String>();
    	try{
    		respMap = OssHelper.assumeRole();
            long pttl = CacheHelper.getCacher().pttl(OssHelper.roleSessionName);
    		long expiration = new Date().getTime() + (pttl) - (1L * 60 * 1000);
    		// 设置过期时间的时间戳
            respMap.put("Expiration", expiration+"");
        }catch(Exception e){
            throw new BusinessException("获取token失败", e);
        }
        return  new DataResult(respMap);
    }
    
   /* @RequestMapping("/getUrl")
    @ResponseBody
    public DataResult getUrl(String objectName) throws Exception{
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("path", OssHelper.getUrl(objectName));
    	map.put("thumb", OssHelper.getUrl(objectName, "image/resize,l_300"));
        return  new DataResult(map);
    }*/
}
