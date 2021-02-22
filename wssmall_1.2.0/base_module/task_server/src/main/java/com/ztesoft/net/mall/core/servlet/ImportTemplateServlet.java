package com.ztesoft.net.mall.core.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.model.TemplateCell;
import com.ztesoft.net.mall.core.model.TemplateColumn;
import com.ztesoft.net.mall.core.service.IImportManager;

public class ImportTemplateServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String template_type = request.getParameter("template_type");
		if("EXCEL".equals(template_type)){
			exportExcel(request,response);
		}
		else if("CSV".equals(template_type)){
			exportCsv(request,response);
		}
		else{
			
		}
	}
	
	public static boolean exportExcel(HttpServletRequest request, HttpServletResponse response){
		OutputStream os =null;
        WritableWorkbook wbook = null;
        boolean isSuccess = false;
        try {
        	String template_id = request.getParameter("template_id");
    		TemplateColumn column = getTemplateColumns(template_id);
    		if(column==null)
    			return isSuccess;
    		String fileTitle = column.getName();
    		List<TemplateCell> cells = column.getCells();
    		if(cells==null || cells.size()==0)
    			return isSuccess;

            String fileName = new String(fileTitle.getBytes("gb2312"), "iso-8859-1"); // 乱码解决
            os = response.getOutputStream();// 取得输出流
            response.reset();// 清空输出流
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");// 设定输出文件头
            response.setContentType("application/msexcel");// 定义输出类型

            wbook = Workbook.createWorkbook(os); // 建立excel文件
            WritableSheet wsheet = wbook.createSheet(fileTitle, 0); // sheet名称
           
            // 设置excel标题
            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
            WritableCellFormat wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBackground(jxl.format.Colour.AQUA);
            wcfFC = new WritableCellFormat(wfont);
            
            // 开始生成主体内容
            int cellSize = cells.size();
            for (int i = 0; i < cellSize; i++) {
            	TemplateCell cell = cells.get(i);
                wsheet.addCell(new Label(i, 0, cell.getCname(), wcfFC));
                wsheet.setColumnView(i, 23);// 设置宽度为23
                wsheet.setRowView(0, 340);//设置标题行高
            }

            // 设置excel标题
            WritableFont cfont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
            WritableCellFormat ccfFC = new WritableCellFormat(cfont);
            ccfFC.setBackground(jxl.format.Colour.AQUA);
            ccfFC = new WritableCellFormat(cfont);

            for (int i = 0; i < cellSize; i++) {
            	TemplateCell cell = cells.get(i);
                wsheet.addCell(new Label(i, 1, cell.getEname(), ccfFC));
                wsheet.setColumnView(i, 23);// 设置宽度为23
            }

            // 主体内容生成结束
            wbook.write(); // 写入文件
            isSuccess = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            isSuccess = false;
        }finally {
            if(wbook != null) {
                try {
                    wbook.close();
                } catch (WriteException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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
        return isSuccess;
	}
	
	public static boolean exportCsv(HttpServletRequest request, HttpServletResponse response){

		boolean isSucess=false;
        
		OutputStream os =null;
        OutputStreamWriter osw=null;
        try {
        	String template_id = request.getParameter("template_id");
        	TemplateColumn column = getTemplateColumns(template_id);
        	if(column==null)
        		return false;
    		String fileTitle = column.getName();
    		List<TemplateCell> cells = column.getCells();
    		if(cells==null || cells.size()==0)
    			return false;
    		
    		String fileName = new String(fileTitle.getBytes("gb2312"), "iso-8859-1"); // 乱码解决
            os = response.getOutputStream();// 取得输出流
            response.reset();// 清空输出流
            response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".csv");// 设定输出文件头
            response.setContentType("application/ms-txt");// 定义输出类型
    		
            osw = new OutputStreamWriter(os);
            StringBuilder title = new StringBuilder();
            int cellSize = cells.size();
            for(int i=0;i<cellSize;i++){
            	TemplateCell cell = cells.get(i);
            	title.append(cell.getCname());
            	if(i<cellSize-1){
            		title.append(",");
            	}
            }
            osw.write(title.toString());
            isSucess=true;
        } catch (Exception e) {
            isSucess=false;
        }finally{
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
            if(os!=null){
                try {
                    os.close();
                    os=null;
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }
        return isSucess;
    }
	
	public static TemplateColumn getTemplateColumns(String template_id){
		TemplateColumn column = null;
		try{
			IImportManager importManager = SpringContextHolder.getBean("importManager");
			column = importManager.getTemplateColumns(template_id);
		}catch(Exception e){
			e.printStackTrace();
		}
		return column;
	}
}
