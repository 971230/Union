package com.ztesoft.net.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.utils.ExportExcelHelper;

/**
 * 
* @作者 MoChunrun 
* @创建日期 2013-9-17 
* @版本 V 1.0
 */
public class DownLoadUtil {
	private static Logger logger = Logger.getLogger(DownLoadUtil.class);
	public static void export(Page webPage,String fileTitile,String [] custCols,String [] str,HttpServletResponse response){
		  List<Object> list = webPage.getResult();
		  
		  ExportExcelHelper exh = new ExportExcelHelper();
		  
		  
		  OutputStream os =null;
		  WritableWorkbook wbook = null;
		  
		  try {
		    String title = "";
		    
		    for(int i = 0; i < custCols.length; i++ ){
		        title += custCols[i] + ",";
		    }
		      
		    title = title.substring(0, title.length() -1);      
		    title = new String(title.getBytes("gb2312"), "gbk");
		    
		    String titles[] = title.split(",");
		    String fileName = new String(fileTitile.getBytes("gb2312"), "iso-8859-1"); // 乱码解决
		    os = response.getOutputStream();// 取得输出流
		    response.reset();// 清空输出流
		    response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");// 设定输出文件头
		    response.setContentType("application/msexcel;charset=utf-8");// 定义输出类型
		    response.setCharacterEncoding("utf-8");
		    //response.setContentType("application/vnd.ms-excel;charset=GB18030");// 定义输出类型
		
		    wbook = Workbook.createWorkbook(os); // 建立excel文件
		    WritableSheet wsheet = wbook.createSheet(fileTitile, 0); // sheet名称
		
		    // 设置excel标题
		    WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
		        UnderlineStyle.NO_UNDERLINE, Colour.RED);
		    WritableCellFormat wcfFC = new WritableCellFormat(wfont);
		    wcfFC.setBackground(Colour.AQUA);
		    wcfFC = new WritableCellFormat(wfont);
		
		    // 开始标题
		    for (int i = 0; i < titles.length; i++) {
		      wsheet.addCell(new Label(i, 0, titles[i], wcfFC));
		      wsheet.setColumnView(i, 23);// 设置宽度为23
		    }
		    
		    //生成内容
		      if(list != null && !list.isEmpty()){
		        for(int j=0;j<list.size();j++){
		          Object obj = list.get(j);
		          //获取对象的属性数及属性值
		          for(int k=0;k<str.length;k++){
		            String name = str[k];
		            String attr = String.valueOf(exh.getFieldValueByName(name,obj));
		            if("null".equals(attr) || StringUtil.isEmpty(attr))
		            	attr ="";
		            Label label = new Label(k,j+1,attr);
		            wsheet.addCell(label);
		          }
		       	}
		       }
		    
		    // 主体内容生成结束
		    wbook.write(); // 写入文件
		  } catch (Exception ex) {
		    ex.printStackTrace();
		  }finally {
		    if(wbook != null) {
		      try {
		        wbook.close();
		      } catch (WriteException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      }
		    }
		    
		    if(os != null) {
		      try {
				os.close();  // 关闭流
		      }catch (Exception e) {
		        e.printStackTrace();
		      }
		    }
		  }
		}
	/**
	 * excel表格数据导出  生成多张sheet
	 * @param dataList
	 * @param fileTitle
	 * @param response
	 */
	public static void exportForMoreSheet(List<Map> dataList,String fileTitle,HttpServletResponse response){
		  ExportExcelHelper exh = new ExportExcelHelper();
		  OutputStream os =null;
		  WritableWorkbook wbook = null;
		  
		  try {
		    
		    String fileName = new String(fileTitle.getBytes("gb2312"), "iso-8859-1"); // 乱码解决
		    os = response.getOutputStream();// 取得输出流
		    response.reset();// 清空输出流
		    response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");// 设定输出文件头
		    response.setContentType("application/msexcel;charset=utf-8");// 定义输出类型
		    response.setCharacterEncoding("utf-8");
		    //response.setContentType("application/vnd.ms-excel;charset=GB18030");// 定义输出类型
		    // 设置excel标题
		    WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
		        UnderlineStyle.NO_UNDERLINE, Colour.RED);
		    WritableCellFormat wcfFC = new WritableCellFormat(wfont);
		    wcfFC.setBackground(Colour.AQUA);
		    wcfFC = new WritableCellFormat(wfont);
		    wbook = Workbook.createWorkbook(os); // 建立excel文件
		    
		    for (int h=0;h<dataList.size()-1;h++) {
		    	Map mapData=dataList.get(h);
		    	String sheetTitile=(String) mapData.get("sheetTitile");
		    	List list =(List) mapData.get("list");
		    	WritableSheet wsheet = wbook.createSheet(sheetTitile,h); // sheet名称
		    	String [] custCols =(String[]) mapData.get("content");
				String [] str =(String[]) mapData.get("title");
	            String title = "";
			    for(int i = 0; i < str.length; i++ ){
			        title += str[i] + ",";
			    }
			    title = title.substring(0, title.length() -1);      
			    title = new String(title.getBytes("gb2312"), "gbk");
			    String titles[] = title.split(",");
			    
			    // 开始标题
			    for (int i = 0; i < titles.length; i++) {
			      wsheet.addCell(new Label(i, 0, titles[i], wcfFC));
			      wsheet.setColumnView(i, 23);// 设置宽度为23
			    }
			    
			    //生成内容
			      if(list != null && !list.isEmpty()){
			        for(int j=0;j<list.size();j++){
			          Object obj = list.get(j);
			          //获取对象的属性数及属性值
			          for(int k=0;k<custCols.length;k++){
			            String name = custCols[k];
			            String attr = String.valueOf(exh.getFieldValueByName(name,obj));
			            if("null".equals(attr) || StringUtil.isEmpty(attr))
			            	attr ="";
			            Label label = new Label(k,j+1,attr);
			            wsheet.addCell(label);
			          }
			       	}
			       }
			}
		    
		    // 主体内容生成结束
		    wbook.write(); // 写入文件
		  } catch (Exception ex) {
		    ex.printStackTrace();
		  }finally {
		    if(wbook != null) {
		      try {
		        wbook.close();
		      } catch (WriteException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      }
		    }
		    
		    if(os != null) {
		      try {
				os.close();  // 关闭流
		      }catch (Exception e) {
		        e.printStackTrace();
		      }
		    }
		  }
		}
	public static void export(List list,String fileTitile,String [] custCols,String [] str,HttpServletResponse response){
		  ExportExcelHelper exh = new ExportExcelHelper();
		  
		  
		  OutputStream os =null;
		  WritableWorkbook wbook = null;
		  
		  try {
		    String title = "";
		    
		    for(int i = 0; i < custCols.length; i++ ){
		        title += custCols[i] + ",";
		    }
		      
		    title = title.substring(0, title.length() -1);      
		    title = new String(title.getBytes("gb2312"), "gbk");
		    
		    String titles[] = title.split(",");
		    String fileName = new String(fileTitile.getBytes("gb2312"), "iso-8859-1"); // 乱码解决
		    os = response.getOutputStream();// 取得输出流
		    response.reset();// 清空输出流
		    response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");// 设定输出文件头
		    response.setContentType("application/msexcel;charset=utf-8");// 定义输出类型
		    response.setCharacterEncoding("utf-8");
		    //response.setContentType("application/vnd.ms-excel;charset=GB18030");// 定义输出类型
		
		    wbook = Workbook.createWorkbook(os); // 建立excel文件
		    WritableSheet wsheet = wbook.createSheet(fileTitile, 0); // sheet名称
		
		    // 设置excel标题
		    WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
		        UnderlineStyle.NO_UNDERLINE, Colour.RED);
		    WritableCellFormat wcfFC = new WritableCellFormat(wfont);
		    wcfFC.setBackground(Colour.AQUA);
		    wcfFC = new WritableCellFormat(wfont);
		
		    // 开始标题
		    for (int i = 0; i < titles.length; i++) {
		      wsheet.addCell(new Label(i, 0, titles[i], wcfFC));
		      wsheet.setColumnView(i, 23);// 设置宽度为23
		    }
		    
		    //生成内容
		      if(list != null && !list.isEmpty()){
		        for(int j=0;j<list.size();j++){
		          Object obj = list.get(j);
		          //获取对象的属性数及属性值
		          for(int k=0;k<str.length;k++){
		            String name = str[k];
		            String attr = String.valueOf(exh.getFieldValueByName(name,obj));
		            if("null".equals(attr) || StringUtil.isEmpty(attr))
		            	attr ="";
		            Label label = new Label(k,j+1,attr);
		            wsheet.addCell(label);
		          }
		       	}
		       }
		    
		    // 主体内容生成结束
		    wbook.write(); // 写入文件
		  } catch (Exception ex) {
		    ex.printStackTrace();
		  }finally {
		    if(wbook != null) {
		      try {
		        wbook.close();
		      } catch (WriteException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      } catch (IOException e) {
		        // TODO Auto-generated catch block
		        e.printStackTrace();
		      }
		    }
		    
		    if(os != null) {
		      try {
				os.close();  // 关闭流
		      }catch (Exception e) {
		        e.printStackTrace();
		      }
		    }
		  }
		}
	
	public static void export(String fileTitle,String[] templateTitle,String[] templateContents,HttpServletResponse response){
		String title = "";
        String content = "";
        
        OutputStream os =null;
        WritableWorkbook wbook = null;
		try {
			
			for(int i = 0; i < templateTitle.length; i++ ){
                title += templateTitle[i] + ",";
                content += templateContents[i] + ",";
            }
			
			title = title.substring(0, title.length() -1);
            title = new String(title.getBytes("gb2312"), "gbk");

            content = content.substring(0, content.length() -1);
            content = new String(content.getBytes("gb2312"), "gbk");

            String titles[] = title.split(",");
            String contents[] = content.split(",");

            String fileName = new String(fileTitle.getBytes("gb2312"), "iso-8859-1"); // 乱码解决
            os = response.getOutputStream();// 取得输出流
            response.reset();// 清空输出流
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");// 设定输出文件头
            response.setContentType("application/msexcel");// 定义输出类型

            wbook = Workbook.createWorkbook(os); // 建立excel文件
            WritableSheet wsheet = wbook.createSheet(fileTitle, 0); // sheet名称

            // 设置excel标题
            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBackground(Colour.AQUA);
            wcfFC = new WritableCellFormat(wfont);

            // 开始生成主体内容
            for (int i = 0; i < titles.length; i++) {
                wsheet.addCell(new Label(i, 0, titles[i], wcfFC));
                wsheet.setColumnView(i, 18);// 设置宽度为18
            }

            // 设置excel标题
            WritableFont cfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat ccfFC = new WritableCellFormat(cfont);
            ccfFC.setBackground(Colour.AQUA);
            ccfFC = new WritableCellFormat(cfont);
            
            for (int i = 0; i < contents.length; i++) {
                wsheet.addCell(new Label(i, 1, contents[i], ccfFC));
                wsheet.setColumnView(i, 18);// 设置宽度为18
            }
            wbook.write(); // 写入文件
       	}catch(FileNotFoundException e1){
       		logger.info("没有找到您要的文件");
		}catch(Exception e){
		    logger.info("系统错误，请及时与管理员联系");
		}finally{
			if(wbook != null) {
                try {
                    wbook.close();
                } catch (WriteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if(os != null) {
                try {
                    os.close(); // 关闭流
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
		}
	}
	
	public static boolean downLoadFile(String filePath,
        HttpServletResponse response, String fileName, String fileType)
        throws Exception {
        File file = new File(filePath);  //根据文件路径获得File文件
        //设置文件类型(这样设置就不止是下Excel文件了，一举多得)
        if("pdf".equals(fileType)){
           response.setContentType("application/pdf;charset=GBK");
        }else if("xls".equals(fileType)){
           response.setContentType("application/msexcel;charset=GBK");
        }else if("doc".equals(fileType)){
           response.setContentType("application/msword;charset=GBK");
        }else if("xlsx".equals(fileType)){
            response.setContentType("application/msexcel;charset=GBK");
         }

        //文件名
        response.setHeader("Content-Disposition", "attachment;filename=\""
            + new String(fileName.getBytes("GBK"), "ISO8859-1") + "\"");
        response.setContentLength((int) file.length());
        byte[] buffer = new byte[4096];// 缓冲区
        BufferedOutputStream output = null;
        BufferedInputStream input = null;
        try {
          output = new BufferedOutputStream(response.getOutputStream());
          input = new BufferedInputStream(new FileInputStream(file));
          int n = -1;

          //遍历，开始下载
          while ((n = input.read(buffer, 0, 4096)) > -1) {
             output.write(buffer, 0, n);
          }
          output.flush();   //不可少
          response.flushBuffer();//不可少
        } catch (Exception e) {
          //异常自己捕捉       

        } finally {

           //关闭流，不可少
           if (input != null)
                input.close();
           if (output != null)
                output.close();
        }

       return false;
    }
}
