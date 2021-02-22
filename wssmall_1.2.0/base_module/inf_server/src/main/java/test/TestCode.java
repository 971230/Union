package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.ztesoft.common.util.StringUtils;

public class TestCode {

	public static void main(String[] args) throws Exception {
		TimeZone tz = TimeZone.getTimeZone("ETC/GMT-8");
		TimeZone.setDefault(tz);
//		deploySource("G:/联通电商平台/代码部署/代金券.txt","G:/联通电商平台/代码部署/");
//		deploySource("G:/联通电商平台/代码部署/合约惠机C1.txt","G:/联通电商平台/代码部署/");
		deploySource("D:/代码部署/file.txt","D:/代码部署/class/");
	}
	
	/**
	 * 生成部署文件
	 * @param fileName  修改文件
	 * @param savePath
	 * @throws Exception 
	 */
	public static void  deploySource(String fileName , String destPath) throws Exception{
		if(StringUtils.isEmpty(fileName) || StringUtils.isEmpty(destPath)){
			System.out.println("源文件、目标目录为空，请检查！");
			return;
		}
		File file = new File(fileName);
		BufferedReader read = new BufferedReader(new FileReader(file));
		String recordValue = "";
		String sourcePath = "";
		String savePath = "";
		String destValue = ""; 
		String arrTmp[] = null;
		File tmpFile = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHH");
		String currentTime = dateFormat.format(new Date());
		while( (recordValue = read.readLine()) != null ){
//			System.out.println(recordValue);
			if(recordValue.startsWith("/wssmall_ecs/10.0/ecs_server/")){
				sourcePath = "C:/Workspace/workspace_wssmall/ecs_server/target/classes/";
				arrTmp = recordValue.split("/ecs_server/src/main/java/");
				destValue = sourcePath + arrTmp[1];
				tmpFile = new File(arrTmp[1]);
				savePath = destPath + "/" + currentTime + "/ecs_server/" + tmpFile.getParent();
				mkdir(savePath);
				copyFiles(destValue,savePath);
			}else if(recordValue.startsWith("/wssmall_ecs/10.0/inf_server/")){
				sourcePath = "C:/Workspace/workspace_wssmall/inf_server/target/classes/";
				arrTmp = recordValue.split("/inf_server/src/main/java/");
				destValue = sourcePath + arrTmp[1];
				tmpFile = new File(arrTmp[1]);
				savePath = destPath + "/" + currentTime + "/inf_server/" + tmpFile.getParent();
				mkdir(savePath);
				copyFiles(destValue,savePath);
			}else if(recordValue.startsWith("/wssmall_ecsord/")){
				sourcePath = "C:/Workspace/workspace_wssmall/ecsord_server/target/classes/";
				arrTmp = recordValue.split("/ecsord_server/src/main/java/");
				destValue = sourcePath + arrTmp[1];
				tmpFile = new File(arrTmp[1]);
				savePath = destPath + "/" + currentTime + "/ecsord_server/" + tmpFile.getParent();
				mkdir(savePath);
				copyFiles(destValue,savePath);
			}else if(recordValue.startsWith("/wssmall/2.0.0.7/mgWeb")){
				///wssmall/2.0.0.7/mgWeb/src/main/webapp/shop/admin/orderexp/exp_params.jsp
				String []tmp = recordValue.split("/");
				sourcePath = "C:/Workspace/workspace_wssmall/mgWeb/src/main/webapp/";
				arrTmp = recordValue.split("/mgWeb/src/main/webapp/");
				destValue = sourcePath + arrTmp[1];
				tmpFile = new File(arrTmp[1]);
				savePath = destPath + "/" + currentTime + "/"+tmp[3]+"/" + tmpFile.getParent();
				mkdir(savePath);
				copyFiles(destValue,savePath);
			}else if(recordValue.startsWith("/wssmall/2.0.0.7")){
				String []tmp = recordValue.split("/");
				sourcePath = "C:/Workspace/workspace_wssmall/"+tmp[3]+"/target/classes/";
				if("sdk".equals(tmp[3])){
					arrTmp = recordValue.split("/zte/net/");
					arrTmp[1] = "/zte/net/" + arrTmp[1];
				}else{
					arrTmp = recordValue.split("/"+tmp[3]+"/src/main/java/");
				}
				destValue = sourcePath + arrTmp[1];
				tmpFile = new File(arrTmp[1]);
				String parent = tmpFile.getParent();
				if(null == parent){
					parent = "";
				}
				savePath = destPath + "/" + currentTime + "/"+tmp[3]+"/" + parent;
				mkdir(savePath);
				copyFiles(destValue,savePath);
			}else{
				System.out.println(recordValue);
				System.out.println("未配置的目录，请检查处理!");
			}
		}
		read.close();
	}
	
	public static void mkdir(String dir){
		File dirFile = new File(dir);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
	}
	
	public static void copyFiles(String fileName , String savePath){
		File f1 = new File(fileName);
		String filePath = f1.getParent();
		String name = f1.getName().split("\\.")[0];
		File d1 = new File(filePath);
		File fileLst[] = d1.listFiles();
		for(File file : fileLst){
			if(file.getName().startsWith(name)){
				copy(file.getAbsolutePath(),savePath + "/" + file.getName());
			}
		}
	}
	
	public static void copy(String oldFile , String newFile){
		FileInputStream fin = null;
		FileOutputStream fout = null;
		FileChannel in = null;
		FileChannel out = null;
		try{
			fin = new FileInputStream(oldFile);
			fout = new FileOutputStream(newFile);
			in = fin.getChannel();
			out = fout.getChannel();
			in.transferTo(0, in.size(), out);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				out.close();
				in.close();
				fin.close();
				fout.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
