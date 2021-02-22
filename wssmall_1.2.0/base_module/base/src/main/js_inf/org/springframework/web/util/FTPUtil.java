package org.springframework.web.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

public class FTPUtil { 
        public static Log logger = LogFactory.getLog(FTPUtil.class); 

        private static String userName;         		//FTP 登录用户名 
        private static String password;         		//FTP 登录密码 
        private static String ip;                     	//FTP 服务器地址IP地址 
        private static int port = 21;                   //FTP 端口 
        private static FTPClient ftpClient = null; 		//FTP 客户端代理 
        
        //FTP状态码 
        public static int i = 1; 
        
        public static void main(String[] args) { 
        		Map map = new HashMap();
        		map.put("ip", "10.45.47.190");
        		map.put("port", "21");
        		map.put("userName", "weblogic");
        		map.put("passWord", "weblogic");
        		setArg(map);
                setFileType(FTP.BINARY_FILE_TYPE);// 设置传输二进制文件 
                //uploadFile(new File("e:\\a.txt"), "/weblogic/ftpupload/"); 
                uploadFile("ccccc".getBytes(), "/weblogic/ftpupload/", "b.txt");
                closeConnect();// 关闭连接 
        		       
			
			//FTPUtil.download("/weblogic/ftpupload/a.txt", "e:/a.txt", map);
			
        } 

        /** 
         * 上传单个文件，并重命名 
         * 
         * @param localFile--本地文件路径 
         * @param remoteFile--远程文件路径
         * @param distFolder--新的文件名,可以命名为空"" 
         * @return true 上传成功，false 上传失败 
         */ 
        public static boolean uploadFile_bak(File localFile, String remoteFile, final String distFolder) { 
        		logger.debug("                    -------------------------"); 
                boolean flag = true; 
                try { 
                        connectServer(); 
                        ftpClient.setFileType(FTP.BINARY_FILE_TYPE); 
                        ftpClient.enterLocalPassiveMode(); 
                        ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE); 
                        InputStream input = new FileInputStream(localFile); 
                        
                        ftpClient.changeWorkingDirectory(remoteFile); 

                        logger.debug("b>>>>>>> : " + localFile.getPath() + " " + ftpClient.printWorkingDirectory()); 
                        flag = ftpClient.storeFile(localFile.getName(), input); 
                        if (flag) { 
                                logger.info("上传文件成功！"); 
                        } else { 
                                logger.info("上传文件失败！"); 
                        } 
                        input.close(); 
                } catch (IOException e) { 
                        e.printStackTrace(); 
                        logger.debug("本地文件上传失败！", e); 
                } catch (Exception e) { 
                        e.printStackTrace(); 
                } 
                return flag; 
        } 
        
        public static boolean uploadFile(byte [] buf,String remoteDir,String fileName){
        	ByteArrayInputStream input = null;
        	logger.debug("                    -------------------------"); 
            boolean flag = true; 
            try { 
            	input = new ByteArrayInputStream(buf);
                FTPClient ftpClient = new FTPClient(); 
                ftpClient.setControlEncoding("GBK"); 
                ftpClient.setDefaultPort(port); 
                ftpClient.configure(getFtpConfig()); 
                ftpClient.connect(ip); 
                ftpClient.login(userName, password); 
                ftpClient.setDefaultPort(port); 
                int reply = ftpClient.getReplyCode(); 
                ftpClient.setDataTimeout(30000); 
                if (!FTPReply.isPositiveCompletion(reply)) { 
                        ftpClient.disconnect(); 
                        System.err.println("FTP server refused connection."); 
                        flag = false; 
                }
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE); 
                ftpClient.enterLocalPassiveMode(); 
                ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE); 
                ftpClient.changeWorkingDirectory(remoteDir); 

                flag = ftpClient.storeFile(fileName, input); 
                if (flag) { 
                        logger.info("上传文件成功！"); 
                } else { 
                        logger.info("上传文件失败！"); 
                } 
            } catch (IOException e) { 
                    e.printStackTrace(); 
                    logger.debug("本地文件上传失败！", e); 
            } catch (Exception e) { 
                    e.printStackTrace(); 
            }finally{
            	if(input!=null){
            		try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
            	}
            }
            return flag; 
        }
        
        public static boolean uploadFile(File localFile, String remoteFile) { 
    		logger.debug("                    -------------------------"); 
            boolean flag = true; 
            InputStream input = null;
            try { 
                    FTPClient ftpClient = new FTPClient(); 
                    ftpClient.setControlEncoding("GBK"); 
                    ftpClient.setDefaultPort(port); 
                    ftpClient.configure(getFtpConfig()); 
                    ftpClient.connect(ip); 
                    ftpClient.login(userName, password); 
                    ftpClient.setDefaultPort(port); 
                    int reply = ftpClient.getReplyCode(); 
                    ftpClient.setDataTimeout(30000); 
                    if (!FTPReply.isPositiveCompletion(reply)) { 
                            ftpClient.disconnect(); 
                            System.err.println("FTP server refused connection."); 
                            // logger.debug("FTP 服务拒绝连接！"); 
                            flag = false; 
                    }
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE); 
                    ftpClient.enterLocalPassiveMode(); 
                    ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE); 
                    input = new FileInputStream(localFile); 
                    ftpClient.changeWorkingDirectory(remoteFile); 

                    flag = ftpClient.storeFile(localFile.getName(), input); 
                    if (flag) { 
                            logger.info("上传文件成功！"); 
                    } else { 
                            logger.info("上传文件失败！"); 
                    } 
            } catch (IOException e) { 
                    e.printStackTrace(); 
                    logger.debug("本地文件上传失败！", e); 
            } catch (Exception e) { 
                    e.printStackTrace(); 
            }finally{
            	if(input!=null){
            		try {
						input.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
            	}
            }
            return flag; 
    } 

        /** 
         * 上传多个文件 
         * 
         * @param localFile--本地文件夹路径 
         * @return true 上传成功，false 上传失败 
         */ 
        public static String uploadManyFile(String localFile) { 
                boolean flag = true; 
                StringBuffer strBuf = new StringBuffer(); 
                try { 
                        connectServer(); 
                        File file = new File(localFile);        // 在此目录中找文件 
                        File fileList[] = file.listFiles(); 
                        for (int i = 0; i < fileList.length; i++) {
                        	File f = fileList[i];
                                if (f.isDirectory()) {            // 文件夹中还有文件夹 
                                        uploadManyFile(f.getAbsolutePath()); 
                                } else { 
                                } 
                                if (!flag) { 
                                        strBuf.append(f.getName() + "\r\n"); 
                                } 
                        } 
                        logger.debug(strBuf.toString()); 
                } catch (NullPointerException e) { 
                        e.printStackTrace(); 
                } catch (Exception e) { 
                        e.printStackTrace(); 
                        logger.debug("本地文件上传失败！", e); 
                } 
                return strBuf.toString(); 
        }  
        /**
         * 下载文件
         * @param ftpDirectoryAndFileName
         * @param localDirectoryAndFileName
         * @return
         */
        public static long download(String ftpDirectoryAndFileName, String localDirectoryAndFileName, Map ftpConfig){
    		
    		long result = 0;
    		InputStream is = null;
    		FileOutputStream os = null;
    		try {
    			
    			String ip = ftpConfig.get("ip").toString();
    			int port = Integer.parseInt(ftpConfig.get("port").toString());
    			String userName = ftpConfig.get("userName").toString();
    			String passWord = ftpConfig.get("passWord").toString();
    			
    			//jdk 1.6
//    			FtpClient ftpClient = new FtpClient();
//    			ftpClient.openServer(ip, port);
//    			ftpClient.login(userName, passWord);
//    			ftpClient.binary() ;
//    			is = ftpClient.get(ftpDirectoryAndFileName);
    			
    			//jdk 1.7
    			FtpClient ftpClient = null;
    			try {
					ftpClient = FtpClient.create(ip);
					ftpClient.login(userName,null, passWord);
					ftpClient.setBinaryType();
					is = ftpClient.getFileStream(ftpDirectoryAndFileName);
				} catch (FtpProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    			java.io.File outfile = new java.io.File(localDirectoryAndFileName);
    			os = new FileOutputStream(outfile);
    			byte[] bytes = new byte[1024];
    			int c;
    			while ((c = is.read(bytes)) != -1) {
    				os.write(bytes, 0, c);
    				result = result + c;
    			}
    			
    		} catch (IOException e) {
    			e.printStackTrace();
    			throw new RuntimeException("下载文件失败:" + e.getMessage());
    		} finally {
    			if (is != null) {
    				try {
    					is.close();
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
    			}
    			if (os != null) {
    				try {
    					os.close();
    				} catch (IOException e) {
    					e.printStackTrace();
    				}
    			}
    		}
    		return result;
    	}
        

        /** 
         * 删除一个文件 
         */ 
        public static boolean deleteFile(String filename) { 
                boolean flag = true; 
                try { 
                        connectServer(); 
                        flag = ftpClient.deleteFile(filename); 
                        if (flag) { 
                                logger.info("删除文件成功！"); 
                        } else { 
                                logger.info("删除文件失败！"); 
                        } 
                } catch (IOException ioe) { 
                        ioe.printStackTrace(); 
                } 
                return flag; 
        } 

        /** 
         * 删除目录 
         */ 
        public static void deleteDirectory(String pathname) { 
                try { 
                        connectServer(); 
                        File file = new File(pathname); 
                        if (file.isDirectory()) { 
                                File file2[] = file.listFiles(); 
                        } else { 
                                deleteFile(pathname); 
                        } 
                        ftpClient.removeDirectory(pathname); 
                } catch (IOException ioe) { 
                        ioe.printStackTrace(); 
                } 
        } 

        /** 
         * 删除空目录 
         */ 
        public static void deleteEmptyDirectory(String pathname) { 
                try { 
                        connectServer(); 
                        ftpClient.removeDirectory(pathname); 
                } catch (IOException ioe) { 
                        ioe.printStackTrace(); 
                } 
        } 

        /** 
         * 列出服务器上文件和目录 
         * 
         * @param regStr --匹配的正则表达式 
         */ 
        public static void listRemoteFiles(String regStr) { 
                connectServer(); 
                try { 
                        String files[] = ftpClient.listNames(regStr); 
                        if (files == null || files.length == 0) 
                                logger.info("没有任何文件!"); 
                        else { 
                                for (int i = 0; i < files.length; i++) { 
                                        logger.info(files[i]); 
                                } 
                        } 
                } catch (Exception e) { 
                        e.printStackTrace(); 
                } 
        } 

        /** 
         * 列出Ftp服务器上的所有文件和目录 
         */ 
        public static void listRemoteAllFiles() { 
                connectServer(); 
                try { 
                        String[] names = ftpClient.listNames(); 
                        for (int i = 0; i < names.length; i++) { 
                                logger.info(names[i]); 
                        } 
                } catch (Exception e) { 
                        e.printStackTrace(); 
                } 
        } 

        /** 
         * 关闭连接 
         */ 
        public static void closeConnect() { 
                try { 
                        if (ftpClient != null) { 
                                ftpClient.logout(); 
                                ftpClient.disconnect(); 
                        } 
                } catch (Exception e) { 
                        e.printStackTrace(); 
                } 
        } 

        /** 
         * 设置传输文件的类型[文本文件或者二进制文件] 
         * 
         * @param fileType--BINARY_FILE_TYPE、ASCII_FILE_TYPE 
         * 
         */ 
        public static void setFileType(int fileType) { 
                try { 
                        connectServer(); 
                        ftpClient.setFileType(fileType); 
                } catch (Exception e) { 
                        e.printStackTrace(); 
                } 
        } 

        /** 
         * 扩展使用 
         * 
         * @return ftpClient 
         */ 
        protected static FTPClient getFtpClient() { 
                connectServer(); 
                return ftpClient; 
        } 

        /** 
         * 设置参数 
         * 
         * @param configFile --参数的配置文件 
         */ 
        public static void setArg(Map ftpConfig) {
        	
        	userName = ftpConfig.get("userName").toString();
            password = ftpConfig.get("passWord").toString();
            ip = ftpConfig.get("ip").toString();
            port = Integer.parseInt(ftpConfig.get("port").toString());
        } 

        /** 
         * 连接到服务器 
         * 
         * @return true 连接服务器成功，false 连接服务器失败 
         */ 
        public static boolean connectServer() { 
                boolean flag = true; 
                if (ftpClient == null) { 
                        int reply; 
                        try { 
                                ftpClient = new FTPClient(); 
                                ftpClient.setControlEncoding("GBK"); 
                                ftpClient.setDefaultPort(port); 
                                ftpClient.configure(getFtpConfig()); 
                                ftpClient.connect(ip); 
                                ftpClient.login(userName, password); 
                                ftpClient.setDefaultPort(port); 
                                reply = ftpClient.getReplyCode(); 
                                ftpClient.setDataTimeout(30000); 

                                if (!FTPReply.isPositiveCompletion(reply)) { 
                                        ftpClient.disconnect(); 
                                        System.err.println("FTP server refused connection."); 
                                        // logger.debug("FTP 服务拒绝连接！"); 
                                        flag = false; 
                                } 
//                                logger.info(i); 
                                i++; 
                        } catch (SocketException e) { 
                                flag = false; 
                                e.printStackTrace(); 
                                System.err.println("登录ftp服务器 " + ip + " 失败,连接超时！"); 
                        } catch (IOException e) { 
                                flag = false; 
                                e.printStackTrace(); 
                                System.err.println("登录ftp服务器 " + ip + " 失败，FTP服务器无法打开！"); 
                        } 
                } 
                return flag; 
        } 

        /** 
         * 进入到服务器的某个目录下 
         * 
         * @param directory 
         */ 
        public static void changeWorkingDirectory(String directory) { 
                try { 
                        connectServer(); 
                        ftpClient.changeWorkingDirectory(directory); 
                } catch (IOException ioe) { 
                        ioe.printStackTrace(); 
                } 
        } 

        /** 
         * 返回到上一层目录 
         */ 
        public static void changeToParentDirectory() { 
                try { 
                        connectServer(); 
                        ftpClient.changeToParentDirectory(); 
                } catch (IOException ioe) { 
                        ioe.printStackTrace(); 
                } 
        } 

        /** 
         * 重命名文件 
         * 
         * @param oldFileName --原文件名 
         * @param newFileName --新文件名 
         */ 
        public static void renameFile(String oldFileName, String newFileName) { 
                try { 
                        connectServer(); 
                        ftpClient.rename(oldFileName, newFileName); 
                } catch (IOException ioe) { 
                        ioe.printStackTrace(); 
                } 
        } 

        /** 
         * 设置FTP客服端的配置--一般可以不设置 
         * 
         * @return ftpConfig 
         */ 
        private static FTPClientConfig getFtpConfig() { 
                FTPClientConfig ftpConfig = new FTPClientConfig(FTPClientConfig.SYST_UNIX); 
                ftpConfig.setServerLanguageCode(FTP.DEFAULT_CONTROL_ENCODING); 
                return ftpConfig; 
        } 

        /** 
         * 转码[ISO-8859-1 -> GBK] 不同的平台需要不同的转码 
         * 
         * @param obj 
         * @return "" 
         */ 
        private static String iso8859togbk(Object obj) { 
                try { 
                        if (obj == null) 
                                return ""; 
                        else 
                                return new String(obj.toString().getBytes("iso-8859-1"), "GBK"); 
                } catch (Exception e) { 
                        return ""; 
                } 
        } 

        /** 
         * 在服务器上创建一个文件夹 
         * 
         * @param dir 文件夹名称，不能含有特殊字符，如 \ 、/ 、: 、* 、?、 "、 <、>... 
         */ 
        public static boolean makeDirectory(String dir) {
                connectServer(); 
                boolean flag = true; 
                try { 
                        // logger.info("dir=======" dir); 
                        flag = ftpClient.makeDirectory(dir); 
                        if (flag) { 
                        	logger.debug("make Directory " + dir + " succeed"); 

                        } else { 

                        	logger.debug("make Directory " + dir + " false"); 
                        } 
                } catch (Exception e) { 
                        e.printStackTrace(); 
                } 
                return flag; 
        } 
        
       

}
