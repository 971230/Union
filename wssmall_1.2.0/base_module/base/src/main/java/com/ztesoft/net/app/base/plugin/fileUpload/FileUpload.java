package com.ztesoft.net.app.base.plugin.fileUpload;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description: 数据批量导入
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author 于彤
 * @version 1.0
 */
public class FileUpload {
	private static Logger logger = Logger.getLogger(FileUpload.class);
	public FileUpload() {
	}

	private File myDir;

	private File[] contents;

	private Vector vectorList = new Vector();

	private Iterator currentFileView;

	private File currentFile;

	private String path = new String("");

	private InputStreamReader InStream;

	private BufferedReader in;

	private List fileItems = null;

	private DiskFileUpload fu = new DiskFileUpload();

	private int DefaultListSize = 1000;

	public void load(HttpServletRequest request) throws Exception {
		fileItems = fu.parseRequest(request);
	}

	public String getFieldValue(String parameter) throws Exception {
		Iterator iter = fileItems.iterator();
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			if (parameter.equalsIgnoreCase(item.getFieldName())) {
				if (item.isFormField()) {
					return item.getString();
				} else {
					return item.getName();
				}
			}
		}
		return null;
	}

	public void setFile(String FilePath) {
		this.currentFile = new File(FilePath);
	}

	public File getFile() {
		return this.currentFile;
	}

	/**
	 * 设置浏览的路径
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/*******************************************************************************************************************
	 * 返回当前目录路径
	 */
	public String getDirectory() {
		return myDir.getPath();
	}

	/**
	 * 刷新列表
	 */
	public void refreshList() {
		if (this.path.equals("")) {
			path = "c:\\";
		}
		myDir = new File(path);

		vectorList.clear();
		contents = myDir.listFiles();
		// 重新装入路径下文件
		for (int i = 0; vectorList.add(contents[i]); i++) {
			currentFileView = vectorList.iterator();
		}
	}

	/**
	 * 移动当前文件集合的指针指到下一个条目
	 * 
	 * @return 成功返回true,否则false
	 */
	public boolean nextFile() {
		while (currentFileView.hasNext()) {
			currentFile = (File) currentFileView.next();
			return true;
		}
		return false;
	}

	/**
	 * 返回当前指向的文件对象的文件名称
	 */
	public String getFileName() {
		return currentFile.getName();
	}

	/**
	 * 返回当前指向的文件对象的文件尺寸
	 */
	public String getFileSize() {
		return new Long(currentFile.length()).toString();
	}

	/**
	 * 返回当前指向的文件对象的最后修改日期
	 */
	public String getFileTimeStamp() {
		return new Date(currentFile.lastModified()).toString();
	}

	/**
	 * 返回当前指向的文件对象是否是一个文件目录
	 */
	public boolean getFileType() {
		return currentFile.isDirectory();
	}

	public void FileClass(File file) throws Exception {
		try {
			in = new BufferedReader(new FileReader(file));
			this.in = in;

		} catch (FileNotFoundException e) {
			logger.info("file is not existed");
			throw e;
		} catch (Exception e) {
			try {
				in.close();
			} catch (IOException r) {
				logger.info("open the file has error");
			}
			throw e;
		}
	}

	public String getLine() {
		String s = "";
		try {
			s = in.readLine();
		} catch (Exception e) {
			logger.info("the error that read from file" + e.getMessage());
			throw new RuntimeException(e);
		}
		// logger.info("sssss=="+s);
		return s;
	}

	public List getdata(String parameter, String operId) {

		// List list = Collections.synchronizedList(new ArrayListEx());
		List list = new LinkedList();
		try {
			// InStream = new InputStreamReader(new FileInputStream(currentFile),"GBK");
			InStream = new InputStreamReader(getInputStream(parameter), "GBK");// "UTF-8"
			in = new BufferedReader(InStream);
			this.InStream = InStream;
			this.in = in;
			String line;
			while ((line = in.readLine()) != null) {
				list.add(line + "," + operId);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		finally{
			//add by xxlm11111
			if(this.InStream!=null){
				try{
					this.InStream.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	// 重写这个方法
	public List getdataForCons(String parameter, String operId) {

		// List list = Collections.synchronizedList(new ArrayListEx());
		List list = new LinkedList();
		try {
			// InStream = new InputStreamReader(new FileInputStream(currentFile),"GBK");
			InStream = new InputStreamReader(getInputStream(parameter), "GBK");// "UTF-8"
			in = new BufferedReader(InStream);
			this.InStream = InStream;
			this.in = in;
			String line;
			while ((line = in.readLine()) != null) {
				list.add(line + "※" + operId);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public InputStream getIS(String parameter) throws Exception {
		return getInputStream(parameter);
	}

	private InputStream getInputStream(String parameter) throws Exception {
		Iterator iter = fileItems.iterator();
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			if (parameter.equalsIgnoreCase(item.getFieldName())) {
				if (!item.isFormField()) {
					try {
						return item.getInputStream();
					} catch (Exception ex) {
						throw ex;
					}
				} else {
					throw new Exception("This is not a file!");
				}
			}
		}
		return null;
	}

	// public List[] getdata(int ListSize)
	// {
	//
	// // List list = Collections.synchronizedList(new ArrayListEx());
	// int size = ListSize;
	// int counter = 0;
	// int retListSize = 0;
	// if (size <= 0)
	// {
	// size = DefaultListSize;
	// }
	// // List list = new LinkedList();
	// try{
	// InStream = new InputStreamReader(new FileInputStream(currentFile),"GBK");
	// in = new BufferedReader(InStream);
	// this.InStream = InStream;
	// this.in = in;
	// String line;
	// while ((line = in.readLine()) != null)
	// {
	// if (counter == 0)
	// {
	// List list = new ArrayListEx(size);
	// }
	// counter++;
	// List list = new ArrayListEx(size);
	// retListSize++;
	// list.add(line);
	// }
	// }catch(Exception e)
	// {
	// throw new RuntimeException(e);
	// }
	// return list;
	// }

	public void fileClose() {
		try {
			in.close();
			InStream.close();

		} catch (IOException e) {
			logger.info("the error is that closed file");
			throw new RuntimeException(e);
		}
	}

	public BufferedReader getIn() {
		return in;
	}

}
