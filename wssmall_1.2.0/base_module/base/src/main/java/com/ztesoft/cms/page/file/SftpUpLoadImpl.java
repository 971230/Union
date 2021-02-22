package com.ztesoft.cms.page.file;

import com.jcraft.jsch.*;
import com.ztesoft.cms.page.file.vo.FileBean;
import com.ztesoft.ibss.common.util.*;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class SftpUpLoadImpl extends CommonUpLoadInf {
	Logger logger =Logger.getLogger(SftpUpLoadImpl.class);
	
	ChannelSftp sftp =new ChannelSftp();
	public SftpUpLoadImpl() {
		super();
	}
	@Override
	public void downLoad() throws Exception {
		// TODO Auto-generated method stub

	}

	public void download(HashMap config,Map param) {
		String path = (String) param.get("path");//sftp目录地址
		String downloadFile = path+(String) param.get("fileName");//下载的文件名
		File file = (File) param.get("saveFile");//保存在本地的地址
		
		try {
			connect(config,path);
            sftp.get(downloadFile, new FileOutputStream(file));
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("下载文件出错！", e);
        } finally {
			sftp.disconnect();
		}
    }
	
	@SuppressWarnings("unchecked")
	public void delete(String pathname,HashMap config) throws Exception{
		if (ftpConfig == null || ftpConfig.size() == 0) {// 无配置相关ftp服务器参数
			
		}
		//for (Iterator iter = ftpConfig.iterator(); iter.hasNext();) {
			//HashMap config = (HashMap) iter.next();
			connect(config,"");
			try {
				try{
					this.sftp.rm(pathname);
				}catch (Exception e) {
					//e.printStackTrace();
				}
			} finally {
				sftp.disconnect();
			}
		//}
	}
	
	/*public void deleteIndexFile(String fileName) throws Exception{
		if (ftpConfig == null || ftpConfig.size() == 0) {// 无配置相关ftp服务器参数
			
		}
		for (Iterator iter = ftpConfig.iterator(); iter.hasNext();) {
			HashMap config = (HashMap) iter.next();
			connect(config,"");
			try {
				try{
					String remoteFile = Const.getStrValue(config, "path")+SearchConfig.SERVER_INDEX_FOLDER_NAME+"/" + fileName;
					this.sftp.rm(remoteFile);
				}catch (Exception e) {
				}
			} finally {
				sftp.disconnect();
			}
		}
	}*/

	private ChannelSftp connect(HashMap config,String path) throws Exception {
		if (sftp.isConnected()) {
			return sftp;
		}
		String server = (String) config.get("server");
		String username = (String) config.get("username");
		String password = (String) config.get("password");

		String host = server.split("\\:")[0];
		String port = "";
		if (server.indexOf(":") > 0) {
			port = server.split("\\:")[1];
		}
		if (org.apache.commons.lang.StringUtils.isEmpty(port))
			port = "22";
		JSch jsch = new JSch();
		Session sshSession = jsch.getSession(username, host, new Integer(port).intValue());
		sshSession.setPassword(password);
		Properties sshConfig = new Properties();
	    sshConfig.put("StrictHostKeyChecking", "no");
	    sshSession.setConfig(sshConfig);
		sshSession.connect();
		Channel channel = sshSession.openChannel("sftp");
		channel.connect();
		sftp = (ChannelSftp) channel;
		
		return sftp;
	}

	
    //文件上传
	public Map uploadNew(Map fileMap,HashMap config,String path) throws Exception {
	
		File srcFile = (File) fileMap.get("FILE_ITEM");
		FileBean fileBean = (FileBean) fileMap.get("FILE_BEAN");
		
		FileInputStream fis = null;
		connect(config,path+fileBean.getFolder());
		config.putAll(fileMap);

		try {
			fis = new FileInputStream(srcFile);
			String file_path = fileBean.getPath();
			String remoteFile = path+ fileBean.getFolder();
            File rfile = new File(remoteFile);
            String rpath = rfile.getPath();
            try {
                createDir(rpath, sftp);
            } catch (Exception e) {
            	e.printStackTrace();
            }

            this.sftp.put(fis, srcFile.getName());
            fileMap.put("FTP_FILE_PATH", remoteFile);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			fis.close();
			IOUtils.closeQuietly(fis);
			
			sftp.disconnect();
		}
	
		return fileMap;
	}
	
	/*  //文件上传
	public Map uploadIndexFileNew(Map fileMap,HashMap config) throws Exception {
	
		String path = (String) config.get("path");
		
		connect(config,path);
		config.putAll(fileMap);

		File srcFile = (File) fileMap.get("FILE_ITEM");
		FileBean fileBean = (FileBean) fileMap.get("FILE_BEAN");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(srcFile);
			String file_name = fileBean.getName();
			String remoteFile = path+SearchConfig.SERVER_INDEX_FOLDER_NAME+"/" + file_name;
			
			File rfile = new File(remoteFile);
            String rpath = rfile.getParent();
            try {
                createDir(rpath, sftp);
            } catch (Exception e) {
            	e.printStackTrace();
            }

            this.sftp.put(fis, srcFile.getName());
            fileMap.put("FTP_FILE_PATH", remoteFile);

		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("FTP客户端出错！", e);
		} finally {
			IOUtils.closeQuietly(fis);
			sftp.disconnect();
		}
	
		return fileMap;
	}*/
	
	public Map add(String file_name,Map obj, SqlMapExe sqlExe) throws Exception {
		
		file_name = file_name.replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("</br>", "").replaceAll("< /br>", "");
		Map retMap = new HashMap();
		String rela_no ="wssnet";
		String f_leven_folder_name = uploadTools.getFileNameBySysdate();
		String s_leven_folder_name =uploadTools.getFileNameByPrefix(uploadTools.getFileExt(file_name));
		String down_file_name = uploadTools.getRandomFileName(file_name); //随机生成
		Map fileMap = new HashMap();
		fileMap.put("RELA_NO", rela_no);
		fileMap.put("FILE_NAME", file_name);
		fileMap.put("CREATE_STAFF_NO","11"); //获取登录的工号，后续需要修改
		fileMap.put("DOWN_FILE_NAME", down_file_name);
		fileMap.put("UPLOAD_TYPE", "");
		fileMap.put("FILE_DESC", "");

		String ACCESS_PATH = uploadTools.getFtpFileFolder(access_path_folder, file_name)+"/"+down_file_name;
		fileMap.put("ACCESS_PATH",ACCESS_PATH );
		fileMap.put("LOCAL_FILE_PATH", uploadTools.getFtpFileFolder(file_foder, file_name) );
		fileMap.put("FTP_FILE_PATH",uploadTools.getFtpPathByAccessPath(ACCESS_PATH));
		fileMap.put("UPLOAD_FILE_PATH", "");
		fileMap.put("F_LEVEN_FOLDER_NAME", f_leven_folder_name);
		fileMap.put("S_LEVEN_FOLDER_NAME", s_leven_folder_name);
		//fileMap.put("CREATE_STAFF_NO",Context.currentContext().getUserId()); //获取登录的工号，后续需要修改
		fileMap.put("CREATE_STAFF_NO","9999"); //获取登录的工号，后续需要修改
		
		
		obj.put("content", "please enter message");
		obj.put("file_path", ACCESS_PATH);
		saveTwbFileUpload(sqlExe, fileMap);
		retMap.put("file_path",ACCESS_PATH);
		this.mod(obj);
		return retMap;
	}
	
	
	public Map insertFileData(String access_path,Map obj, SqlMapExe sqlExe) throws Exception {
		
		Map retMap = new HashMap();
		String rela_no ="wssnet";
		String file_path_arr []= access_path.split("/");
		String f_leven_folder_name =file_path_arr[3];
		String s_leven_folder_name =file_path_arr[4];
		String down_file_name = file_path_arr[file_path_arr.length-1]; //随机生成
		Map fileMap = new HashMap();
		fileMap.put("RELA_NO", rela_no);
		fileMap.put("FILE_NAME", down_file_name);
		fileMap.put("DOWN_FILE_NAME", down_file_name);
		fileMap.put("UPLOAD_TYPE", "");
		fileMap.put("FILE_DESC", "");
		fileMap.put("ACCESS_PATH",access_path );
		String local_path =uploadTools.getLocalPathByAccessPath(access_path);
		fileMap.put("LOCAL_FILE_PATH",local_path);
		fileMap.put("FTP_FILE_PATH",uploadTools.getFtpPathByAccessPath(access_path));
		fileMap.put("UPLOAD_FILE_PATH", "");
		fileMap.put("F_LEVEN_FOLDER_NAME", f_leven_folder_name);
		fileMap.put("S_LEVEN_FOLDER_NAME", s_leven_folder_name);
//		fileMap.put("CREATE_STAFF_NO",Context.currentContext().getUserId()); //获取登录的工号，后续需要修改
		fileMap.put("CREATE_STAFF_NO","9999"); //获取登录的工号，后续需要修改
		String file_no = saveTwbFileUpload(sqlExe, fileMap);
		retMap.put("content_no", file_no);
		return retMap;
		
	}
	

	public String  saveTwbFileUpload(SqlMapExe sqlExe,Map param){
		
		String strCREATE_STAFF_NO = Const.getStrValue(param,"CREATE_STAFF_NO");
		String strFILE_NAME = Const.getStrValue(param,"FILE_NAME");
		String strRELA_NO = Const.getStrValue(param,"RELA_NO");
		String strDOWN_FILE_NAME = Const.getStrValue(param,"DOWN_FILE_NAME");
		
		String strFTP_FILE_PATH = Const.getStrValue(param,"FTP_FILE_PATH");
		String strUPLOAD_FILE_PATH = Const.getStrValue(param,"UPLOAD_FILE_PATH");
		String strLOCAL_FILE_PATH = Const.getStrValue(param,"LOCAL_FILE_PATH");
		String strACCESS_PATH = Const.getStrValue(param,"ACCESS_PATH");
		String strF_LEVEN_FOLDER_NAME = Const.getStrValue(param,"F_LEVEN_FOLDER_NAME");
		String strS_LEVEN_FOLDER_NAME = Const.getStrValue(param,"S_LEVEN_FOLDER_NAME");
		
		//按随机数生成序列
		int n = 1000 + (int) (Math.random() * 90000);
		String file_no = DateFormatUtils.formatDate(CrmConstants.DATE_TIME_FORMAT_14)+ String.valueOf(n);
		
		//插入文件表
		String sql = "INSERT INTO TWB_FILE_UPLOAD(CONTENT_NO, FILE_NAME, OPER_DATE, RELA_NO, "
				+ "STAFF_NO, DOWN_FILE_NAME,F_LEVEN_FOLDER_NAME,S_LEVEN_FOLDER_NAME,ACCESS_PATH,ftp_file_path,local_file_path,upload_file_path) "
				+ "VALUES(?, ?, getdate(),? ,?,?,?,?,?,?,?,?)";
		sqlExe.excuteUpdate(sql, new String[]{file_no,strFILE_NAME,strRELA_NO,strCREATE_STAFF_NO,strDOWN_FILE_NAME,strF_LEVEN_FOLDER_NAME,strS_LEVEN_FOLDER_NAME,strACCESS_PATH,strFTP_FILE_PATH,strLOCAL_FILE_PATH,strUPLOAD_FILE_PATH});
		
		return file_no;
	}
	

	public Map mod(Object object) throws Exception {
		Map fileMap =(Map)object;
		if (ftpConfig == null || ftpConfig.size() == 0) {// 无配置相关ftp服务器参数
			return fileMap;
		}
		//更新本地文件
		String content = Const.getStrValue(fileMap, "content");
		String file_path = Const.getStrValue(fileMap, "file_path");
		String file_arr [] =file_path.split("/");
		String local_file_path = "";
		String file_name = null;
		for (int i = file_arr.length-3; i < file_arr.length-1; i++) 
			local_file_path +=seperator+file_arr[i];
		file_name = file_arr[file_arr.length-1];
		File file = new File(file_foder+local_file_path);
		if (!file.exists()) {
			file.mkdirs();
		}
		File temp = new File(file_foder+local_file_path, file_name);
		if (temp.exists()) {
			temp.delete();
		}
		
		OutputStreamWriter out = null;
		out = new OutputStreamWriter(new FileOutputStream(temp),"utf-8");
		out.write(content);
		out.flush();
		out.close();
		
		//更新服务器文件
		for (Iterator iter = ftpConfig.iterator(); iter.hasNext();) {
			HashMap config = (HashMap) iter.next();
			String path =Const.getStrValue(config, "path");
			connect(config,path);
			config.putAll(fileMap);
			FileInputStream fis = null;
			try {
				String remoteFile = path+ local_file_path+seperator+file_name;
                File rfile = new File(remoteFile);
                String rpath = rfile.getParent();
                try {
                    createDir(rpath, sftp);
                } catch (Exception e) {
                	logger.debug("*******create path failed" + rpath);
                }
                
				sftp.put(new FileInputStream(temp), temp.getName());
               
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("FTP客户端出错！", e);
			} finally {
				IOUtils.closeQuietly(fis);
				sftp.disconnect();
			}
		}
		return fileMap;
	}
	
	
	
	 /**
     * create Directory
     * @param filepath
     * @param sftp
     */
    private void createDir(String filepath, ChannelSftp sftp){
    	
        boolean bcreated = false;
        boolean bparent = false;
        File file = new File(filepath);
        String ppath = file.getParent();
        ppath = ppath.replaceAll("\\\\",this.seperator);
        filepath = filepath.replaceAll("\\\\", this.seperator);
        try {
            this.sftp.cd(ppath);
            bparent = true;
        } catch (SftpException e1) {
            bparent = false;
        }
        try {
            if(bparent){
                try {
                    this.sftp.cd(filepath);
                    bcreated = true;
                } catch (Exception e) {
                    bcreated = false;
                }
                if(!bcreated){
                    this.sftp.mkdir(filepath);
                    this.sftp.cd(filepath);
                    bcreated = true;
                }
                return;
            }else{
                createDir(ppath,sftp);
                this.sftp.cd(ppath);
                this.sftp.mkdir(filepath);
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }
        
        try {
            this.sftp.cd(filepath);
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }
    
	

}
