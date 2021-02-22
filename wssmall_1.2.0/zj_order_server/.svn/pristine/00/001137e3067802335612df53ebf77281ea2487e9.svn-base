package com.ztesoft.net.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
public class SFTPChannel {

	/**
	 * @param args
	 */
	Session session = null;
    Channel channel = null;
    private ChannelSftp sftp = null;

    private static final Logger LOG = Logger.getLogger(SFTPChannel.class.getName());

    public ChannelSftp getChannel(Map<String, String> sftpDetails, int timeout) throws JSchException {

        String ftpHost = sftpDetails.get("ip");
        String port = sftpDetails.get("port");
        String ftpUserName = sftpDetails.get("userName");
        String ftpPassword = sftpDetails.get("passWord");

        int ftpPort = 21;
        if (port != null && !port.equals("")) {
            ftpPort = Integer.valueOf(port);
        }

        JSch jsch = new JSch(); // 创建JSch对象
        session = jsch.getSession(ftpUserName, ftpHost, ftpPort); // 根据用户名，主机ip，端口获取一个Session对象
        LOG.debug("Session created.");
        if (ftpPassword != null) {
            session.setPassword(ftpPassword); // 设置密码
        }
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config); // 为Session对象设置properties
        session.setTimeout(timeout); // 设置timeout时间
        session.connect(); // 通过Session建立链接
        LOG.debug("Session connected.");

        LOG.debug("Opening Channel.");
        channel = session.openChannel("sftp"); // 打开SFTP通道
        channel.connect(); // 建立SFTP通道的连接
        LOG.debug("Connected successfully to ftpHost = " + ftpHost + ",as ftpUserName = " + ftpUserName
                + ", returning: " + channel);
        return (ChannelSftp) channel;
    }

    public void closeChannel() throws Exception {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	* 批量上传文件
	* 
	* @param remotePath
	*            远程保存目录
	* @param localPath
	*            本地上传目录(以路径符号结束)
	* @param del
	*            上传后是否删除本地文件
	* @return
	*/
	public boolean bacthUploadFile(String remotePath, String localPath,
	boolean del,Map<String, String> sftpDetails, int timeout) {
	try {
		sftp = getChannel(sftpDetails, 20000);
	File file = new File(localPath);
	File[] files = file.listFiles();
	for (int i = 0; i < files.length; i++) {
	if (files[i].isFile()
	&& files[i].getName().indexOf("bak") == -1) {
	if (this.uploadFile(remotePath, files[i].getName(),
	localPath, files[i].getName())
	&& del) {
	deleteFile(localPath + files[i].getName());
	 
	}
	}
	}
	return true;
	} catch (Exception e) {
	e.printStackTrace();
	} finally {
		sftp.disconnect();
		channel.disconnect();
	}
	return false;
	 
	}
	
	/**
	* 上传单个文件
	* 
	* @param remotePath
	*            远程保存目录
	* @param remoteFileName
	*            保存文件名
	* @param localPath
	*            本地上传目录(以路径符号结束)
	* @param localFileName
	*            上传的文件名
	* @return
	*/
	public boolean uploadFile(String remotePath, String remoteFileName,
	String localPath, String localFileName) {
	FileInputStream in = null;
	try {
	//createDir(remotePath);
	File file = new File(localPath + localFileName);
	in = new FileInputStream(file);
	sftp.cd(remotePath);
	sftp.put(in, remoteFileName);
	return true;
	} catch (FileNotFoundException e) {
	e.printStackTrace();
	} catch (SftpException e) {
	e.printStackTrace();
	} finally {
	if (in != null) {
	try {
	in.close();
	} catch (IOException e) {
	e.printStackTrace();
	}
	}
	}
	return false;
	}
	
	/**
	* 创建目录
	* 
	* @param createpath
	* @return
	*/
	public boolean createDir(String createpath) {
	try {
	if (isDirExist(createpath)) {
	this.sftp.cd(createpath);
	return true;
	}
	String pathArry[] = createpath.split("/");
	StringBuffer filePath = new StringBuffer("/");
	for (String path : pathArry) {
	if (path.equals("")) {
	continue;
	}
	filePath.append(path + "/");
	if (isDirExist(filePath.toString())) {
	sftp.cd(filePath.toString());
	} else {
	// 建立目录
	sftp.mkdir(filePath.toString());
	// 进入并设置为当前目录
	sftp.cd(filePath.toString());
	}
	 
	}
	this.sftp.cd(createpath);
	return true;
	} catch (SftpException e) {
	e.printStackTrace();
	}
	return false;
	}
	
	/**
	* 判断目录是否存在
	* 
	* @param directory
	* @return
	*/
	public boolean isDirExist(String directory) {
	boolean isDirExistFlag = false;
	try {
	SftpATTRS sftpATTRS = sftp.lstat(directory);
	isDirExistFlag = true;
	return sftpATTRS.isDir();
	} catch (Exception e) {
	if (e.getMessage().toLowerCase().equals("no such file")) {
	isDirExistFlag = false;
	}
	}
	return isDirExistFlag;
	}
	
	/**
	* 删除本地文件
	* 
	* @param filePath
	* @return
	*/
	public boolean deleteFile(String filePath) {
	File file = new File(filePath);
	if (!file.exists()) {
	return false;
	}
	 
	if (!file.isFile()) {
	return false;
	}
	 
	return file.delete();
	}
}
