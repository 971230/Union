package com.ztesoft.net.mall.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.model.GoodsImportLog;
import com.ztesoft.net.mall.core.model.ImportMidData;
import com.ztesoft.net.mall.core.model.TemplateCell;
import com.ztesoft.net.mall.core.service.IImportManager;

public class ExcelUtils {

	public static List<ImportMidData> readExcel(File file,String fileName){
		Workbook wb = null;
		List<TemplateCell> cells = new ArrayList<TemplateCell>();
		List<ImportMidData> datas = new ArrayList<ImportMidData>();
		
		try{
			FileInputStream is = new FileInputStream(file);
	        if (!fileName.endsWith(".xlsx")){  
	            wb = new HSSFWorkbook(is);  
	        }else{  
	            wb = new XSSFWorkbook(is);  
	        }
	        
	        //遍历sheet
	        int sheetNum = wb.getNumberOfSheets();
	        if(sheetNum>1){
	        	sheetNum--;
	        }
			for(int k=0;k<1;k++){
				
				Sheet sheet = wb.getSheetAt(k);
				//遍历行
				for (int i=1;i<=sheet.getLastRowNum();i++) {
					GoodsImportLog log = new GoodsImportLog();
					Row row = sheet.getRow(i);
					if(row!=null){
						//遍历列
						for (int j=0;j<row.getLastCellNum();j++ ) {
							TemplateCell templateCell = new TemplateCell();
							Cell cell = row.getCell(j);
							if(cell == null){
								continue;
							}
							// 读取当前单元格的值
							String title = "";
							if(sheet.getRow(0) != null && sheet.getRow(0).getCell(j) != null){
								 title = sheet.getRow(0).getCell(j).getStringCellValue();
							}
							cell.setCellType(Cell.CELL_TYPE_STRING);
							String cellValue = cell.getStringCellValue();
							if(StringUtils.isNotBlank(title)){
								title = title.trim();
							}
							if(StringUtils.isNotBlank(cellValue)){
								cellValue = cellValue.trim();
							}
							
							templateCell.setCname(title);
							templateCell.setValue(cellValue);
							cells.add(templateCell);
						}
						ImportMidData data = new ImportMidData();
						IImportManager importManager = SpringContextHolder.getBean("importManager");
						String id = importManager.getSequence("S_ES_IMPORT_MID_DATA");
						data.setId(id);
						String json = JSON.toJSONString(cells);
						data.setData_json(json);
						datas.add(data);
						
						cells.clear();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return datas;
	}
	
	public static List<ImportMidData> readCsv(File file,String fileName){
		List<ImportMidData> datas = new ArrayList<ImportMidData>();
		try{
			BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String tempStr = "";
			String[] titles = null;
			String[] contents = null;
			int count = 0;
	        while((tempStr=buffer.readLine())!=null) {
	            if(count==0){
	            	titles = tempStr.split(",");
	            	count++;
	            	continue;
	            }
	            else{
	            	contents = Const.splitStr(tempStr);
	            	count++;
	            }
	            int size = titles==null?0:titles.length;
	            List<TemplateCell> cells = new ArrayList<TemplateCell>();
	            for(int i=0;i<size;i++){
	            	TemplateCell cell = new TemplateCell();
	            	cell.setCname(titles[i]);
	            	cell.setValue(contents[i]);
	            	cells.add(cell);
	            }
	            String json = JSON.toJSONString(cells);
				ImportMidData data = new ImportMidData();
				data.setData_json(json);
				datas.add(data);
	        }
		}catch(Exception e){
			e.printStackTrace();
		}
		return datas;
	}
}
