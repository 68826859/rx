package com.rx.pub.file.utils;

//import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
	public class FileUtils {
	
	private static Log log = LogFactory.getLog(FileUtils.class);
	
	private static int BUFFER_SIZE = 16 * 1024;
	
	
	/**
	* 拷贝文件
	* @param src 源文件
	* @param target 目标文件
	* @return 是否拷贝成功
	* @throws Exception
	*/
	public static boolean copyFile(File src, File target) throws Exception {
	if (src == null || !src.exists() || src.length() == 0 || !src.canRead()){
			return false;
	}
	if (target == null){
			return false;
	}
	BufferedInputStream bis = null;
	BufferedOutputStream bos = null;
		
	try {
			if (!target.exists()){
				if (!target.createNewFile()){
					return false;
				}
			}
			if (!target.canWrite()){
				return false;
			}
			bis = new BufferedInputStream(new FileInputStream(src));
			bos = new BufferedOutputStream(new FileOutputStream(target));
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = -1;
			while((len = bis.read(buffer)) != -1){
				bos.write(buffer, 0, len);
			}
	} catch (IOException e) {
			if (log.isErrorEnabled())log.error("拷贝文件出错", e);
			throw new Exception("拷贝文件出错");
	}finally{
			try {
				if (bis != null){
					bis.close();
				}
				if (bos != null){
					bos.close();
				}
			} catch (IOException e) {
				if (log.isErrorEnabled())log.error("", e);
			}
	}
	return target.exists();
	}
	
	/**
	* 拷贝文件
	* @param inFile 源文件
	* @param outFilePath 输出文件路径
	* @param outFileName 输出文件名称
	* @return
	* @throws Exception
	*/
	public static boolean copyFile(File inFile,String outFilePath,String outFileName) throws Exception{
	File file = newFile(outFilePath, outFileName);
		
	return copyFile(inFile, file);
	}
	
	/**
	* 拷贝文件
	* @param inFilePath 输入文件路径
	* @param inFileName 输入文件各称
	* @param outFilePath 输出文件路径
	* @param outFileName 输出文件名称
	* @return
	* @throws Exception
	*/
	public static boolean copyFile(String inFilePath,String inFileName,String outFilePath,String outFileName) throws Exception{
	File file = newFile(outFilePath, outFileName);
		
	return copyFile(new File(inFilePath + inFileName), file);
	}
	
	/**
	* 把文件内容全部读取为byte[]，文件内容最好不要太大
	* @param file
	* @param deleteAfterRead 是否删除原文件
	* @return
	* @throws Exception
	*/
	public static byte[] readFile(File file, boolean deleteAfterRead) throws Exception{

	FileInputStream fis = null;
	byte[] bFile = null;
	try {
			fis = new FileInputStream(file);
			bFile = new byte[fis.available()];
			fis.read(bFile);
	} catch (Exception e) {
			throw new Exception("读取文件错误");
	} finally{
			if (fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					if (log.isErrorEnabled())log.error("关闭文件读取流错误", e);
				}
				fis = null;
				if (deleteAfterRead){
					file.delete();
				}
			}
	}
	return bFile;
	}
	/**
	* 把文件内容全部读取为byte[]，读取完后不删除原文件。文件内容最好不要太大
	* @param file
	* @return
	* @throws Exception
	*/
	public static byte[] readFile(File file) throws Exception{
	return readFile(file, false);
	}
	
	public static String[] readAsStringArray(File file) throws Exception{
	List<String> ltArr = new ArrayList<String>();
	if (file == null || !file.exists()){
			return new String[]{};
	}

	BufferedReader br = null;
	try {
			br = new BufferedReader(new FileReader(file));
			while(br.ready()){
				ltArr.add(br.readLine());
			}
	} catch (Exception e) {
			throw new Exception("读取文件错误");
	}finally{
			if (br != null){
				try {
					br.close();
				} catch (IOException e) {
					if (log.isErrorEnabled())log.error("关闭文件读取流错误", e);
				}
			}
	}
	return ltArr.toArray(new String[]{});
	}
	/**
	* 把byte[]文件流，指定路径和文件名保存
	* @param bfile 需要保存的文件流
	* @param filePath 保存路径
	* @param fileName 保存文件名
	* @throws IOException
	*/
	public static void saveFile(byte[] bfile, String filePath,String fileName) throws IOException {   
        BufferedOutputStream bos = null;   
        FileOutputStream fos = null;   
        try {   
        	File file = newFile(filePath, fileName);   
              
            fos = new FileOutputStream(file);   
            bos = new BufferedOutputStream(fos);   
            bos.write(bfile);   
        } catch (IOException e) { 
            throw e;   
        } finally {   
            if (bos != null) {   
                try {   
                    bos.close();   
                } catch (IOException e) {   
                	log.error(e.getMessage(), e);
                }   
            }   
            if (fos != null) {   
                try {   
                    fos.close();   
                } catch (IOException e) {   
                	log.error(e.getMessage(), e);
                }   
            }   
        }   
    }  
	public static void saveFile(InputStream inStream, String filePath,String fileName) throws IOException {   
	try {
			File file = newFile(filePath, fileName);   
            
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = inStream.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			inStream.close();
	} catch (IOException e) {
			log.error(e.getMessage(), e);
	}
    }  
	private static File newFile(String filePath,String fileName){
	File file = null;
	File dir = new File(filePath);
        if(!(dir.exists()&&dir.isDirectory())){//判断文件目录是否存在   
            dir.mkdirs();   
        }   
        if(filePath.lastIndexOf("/") == filePath.length()-1 || filePath.lastIndexOf("\\") == filePath.length()-1){
        	file = new File(filePath+fileName); 
        }else{
        	file = new File(filePath+"/"+fileName); 
        }
        return file;
	}
	public static void deleteAllFilesOfDir(File file) throws IOException {
	try {
			if (!file.exists())
		        return;
		    if (file.isFile()) {
		        file.delete();
		        return;
		    }
		    File[] files = file.listFiles();
		    for (int i = 0; i < files.length; i++) {
		        deleteAllFilesOfDir(files[i]);
		    }
		    file.delete();
	} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw e;
	}
	}
	public static void deleteAllFilesOfDir(String path) throws IOException {
	deleteAllFilesOfDir(new File(path));
	}
	
	
	/*
	public static boolean base64ToFile(String base64Str, String filepath) {
	if (base64Str == null) return false;

	try {
			byte[] b = Base64.decodeBase64(base64Str);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(filepath);
			out.write(b);
			out.flush();
			out.close();
			return true;
	} catch (Exception e) {
			e.printStackTrace();
			return false;
	}
	
	}*/

}
