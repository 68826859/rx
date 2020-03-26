package com.rx.base.file;

public interface RxFilePersistencer {
	/**
	 * 保存文件
	 * @param fileAccess	访问权限
	 * @param bs			文件字节流
	 * @param filePrefix	文件前缀
	 * @param fileExtraName	文件拓展名
	 * @return 
	 */
	public String save(FileAccessEnum fileAccess,byte[] bs,String filePrefix,String fileExtraName);
	
	/**
	 * 删除文件
	 * @param path	相对路径path
	 */
	public void delete(String path);
	
	/**
	 * 读取文件
	 * @param file 相对路径path
	 * @return
	 */
	public byte[] read(String path);
}
