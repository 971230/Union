package com.ztesoft.common.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.StringUtils;

public class FileUtil {
	private static Logger logger = Logger.getLogger(FileUtil.class);
	// 移动某个指定的文件，但移动成功后不会覆盖已存在的目标文件
	public static boolean moveA_File(String sourceFileName,
			String targetFileName) {
		return FileUtil.moveA_File(sourceFileName, targetFileName, false);
	}

	// 移动某个指定的文件，但移动成功后可以根据isOverlay的值来决定是否覆盖已存在的目标文件
	public static boolean moveA_File(String sourceFileName,
			String targetFileName, boolean isOverlay) {
		// 判断原文件是否存在
		File sourceFile = new File(sourceFileName);
		if (!sourceFile.exists()) {
			logger.info("移动文件失败：原文件" + sourceFileName + "不存在！");
			return false;
		} else if (!sourceFile.isFile()) {
			logger.info("移动文件失败：" + sourceFileName + "不是一个文件！");
			return false;
		}
		File targetFile = new File(targetFileName);
		if (targetFile.exists()) {// 判断目标文件是否存在
			if (isOverlay) {// 如果目标文件存在，是否允许覆盖目标文件
				// 删除已存在的目标文件，无论目标文件是目录还是单个文件
				logger.info("该文件已存在，准备删除它！");
				if (!targetFile.delete()) {
					logger.info("文件移动失败，删除文件" + targetFileName + "失败！");
					return false;
				}
			} else {
				logger.info("文件移动失败，文件" + targetFileName + "已存在！");
				return false;
			}
		} else {
			if (!targetFile.getParentFile().exists()) {
				// 如果目标文件所在的目录不存在，则创建目录
				logger.info("该文件所在目录不存在，正在创建！");
				if (!targetFile.getParentFile().mkdirs()) {
					logger.info("移动文件失败，创建文件所在的目录失败！");
					return false;
				}
			}
		}
		// 移动原文件至目标文件
		if (sourceFile.renameTo(targetFile)) {
			logger.info("移动文件" + sourceFileName + "到" + targetFileName
					+ "成功！");
			return true;
		} else {
			logger.info("移动文件" + sourceFileName + "到" + targetFileName
					+ "失败！");
			return true;
		}
	}

	public static boolean moveDir(String sourceDirName, String targetDirName) {
		// 默认为不覆盖目标文件
		return FileUtil.moveDir(sourceDirName, targetDirName, false);
	}

	// 移动某个指定的目录，但移动成功后可以根据isOverlay的值来决定是否覆盖当前已存在的目标目录
	public static boolean moveDir(String sourceDirName, String targetDirName,
			boolean isOverlay) {
		// 判断原目录是否存在
		File sourceDir = new File(sourceDirName);
		if (!sourceDir.exists()) {
			logger.info("移动目录失败，原始目录" + sourceDirName + "不存在！");
			return false;
		} else if (!sourceDir.isDirectory()) {
			logger.info("移动目录失败，" + sourceDirName + "不是一个目录！");
			return false;
		}
		// 如果目标文件夹名不以文件分隔符结尾，自动添加文件分隔符
		if (!targetDirName.endsWith(File.separator)) {
			targetDirName = targetDirName + File.separator;
		}
		File targetDir = new File(targetDirName);
		// 如果目标文件夹存在，
		if (targetDir.exists()) {
			if (isOverlay) {
				// 允许覆盖则删除已存在的目标目录
				logger.info("该目录已存在，准备删除它！");
				if (!delFolder(targetDirName)) {
					logger.info("移动目录失败：删除目录" + targetDirName + "失败！");
				}
			} else {
				logger.info("移动目录失败：该目录" + targetDirName + "已存在！");
				return false;
			}
		} else {
			// 创建目标目录
			logger.info("该目录不存在，正在创建！");
			if (!targetDir.mkdirs()) {
				logger.info("移动目录失败：创建该目录失败！");
				return false;
			}
		}
		boolean flag = true;
		// 移动原目录下的文件和子目录到目标目录下
		File[] files = sourceDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 移动子文件
			if (files[i].isFile()) {
				flag = FileUtil.moveA_File(files[i].getAbsolutePath(),
						targetDirName + files[i].getName(), isOverlay);
				if (!flag) {
					break;
				}
			}
			// 移动子目录
			else if (files[i].isDirectory()) {
				flag = FileUtil.moveDir(files[i].getAbsolutePath(),
						targetDirName + files[i].getName(), isOverlay);
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			logger.info("目录" + sourceDirName + "移动到" + targetDirName
					+ "失败！");
			return false;
		}
		// 删除原目录
		if (delFolder(sourceDirName)) {
			logger.info("目录" + sourceDirName + "移动到" + targetDirName
					+ "成功！");
			return true;
		} else {
			logger.info("目录" + sourceDirName + "移动到" + targetDirName
					+ "失败！");
			return false;
		}
	}

	public static void main(String[] args) {
		// 移动文件，如果目标文件存在，则替换
		logger.info("调用moveA_File方法的结果如下：");
		String sourceFileName = "D:\\temp\\keytoolcmd.txt";
		String targetFileName = "D:\\test\\temp\\keytoolcmd.txt.";
		FileUtil.moveA_File(sourceFileName, targetFileName, true);
		// 移动目录，如果目标目录存在，则不覆盖
		logger.info("\n调用moveDir方法的结果如下：");
		String sourceDirName = "D:\\temp\\aa";
		String targetDirName = "F:\\abc";
		FileUtil.moveDir(sourceDirName, targetDirName);

		File f = new File("d:\\myHomework\\Work");
		File fileList[] = f.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			fileList[i].renameTo(new File("d:\\myHomework\\Backup\\"
					+ fileList[i].getName()));
		}
	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myDelFile = new File(filePath);
			myDelFile.delete();
		} catch (Exception e) {
			logger.info("删除文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如c:/fqf
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static boolean delFolder(String folderPath) {
		boolean isDel = false;
		try {
			delAllFile(folderPath); // 删除文件夹里面所有内容
			// 删除空文件夹
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			isDel = myFilePath.delete();
		} catch (Exception e) {
			logger.info("删除文件夹操作出错");
			e.printStackTrace();
		}
		return isDel;
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					// logger.info(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			logger.info("复制单个文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			File newFile = new File(newPath);
			if (!newFile.exists()) {
				(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			}
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			logger.info("复制整个文件夹内容操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * 移动目录到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/*
	 * @return true---是Windows操作系统
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	public static String readFileContent(String filePath) throws IOException {

		InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(
				filePath);
		if (in == null) {
			return "";
		}
		String result = StringUtils.convertStreamToString(in);

		return result;
	}
}
