package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ztesoft.form.util.StringUtil;

public class MoneyAuditExcelUtil {
	private static Logger logger = Logger.getLogger(MoneyAuditExcelUtil.class);
	/**
	 * 【Excel导入】解析Excel为集合
	 *  标题当做map 里面的key
	 * @throws Exception 
	 */
	public static List<Map<String,String>> parserExcelToList(File file,String file_name,int titleInRow) throws Exception {
		FileInputStream is;
		Workbook wb = null;
		try {
			logger.info(file_name);
			is = new FileInputStream(file);
			if (!file_name.endsWith(".xlsx")&&!file_name.endsWith(".csv")){  
		       wb = new HSSFWorkbook(is);  
		    }else{  
		       wb = new XSSFWorkbook(is);  
		    }    
		} catch (IOException e) {
			e.printStackTrace();
		}  
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		if (wb == null)
			return list;

		//遍历sheet
        int sheetNum = wb.getNumberOfSheets();
        if(sheetNum>1){
        	sheetNum--;
        }
        for(int k=0;k<sheetNum;k++){
			Sheet sheet = wb.getSheetAt(k);//得到工作表Sheet对象
			//遍历行
			for (int i=1;i<=sheet.getLastRowNum();i++) {
				Row row = sheet.getRow(i);
				Map<String,String> cellMap = new HashMap<String,String>();;
				if(row!=null&&i>titleInRow){//忽略标题行之前的内容
					//遍历列
					for (int j=0;j<row.getLastCellNum();j++ ) {	//  
						Cell cell = row.getCell(j);
						
						// 读取当前单元格的值
						String title = "";//标题
						if(sheet.getRow(titleInRow) != null && sheet.getRow(titleInRow).getCell(j) != null){
							 sheet.getRow(titleInRow).getCell(j).setCellType(Cell.CELL_TYPE_STRING);
							 title = sheet.getRow(titleInRow).getCell(j).getStringCellValue();
							 if(StringUtil.isEmpty(title)){
								// throw new Exception("excel 头部不能为空");
								 //continue;
							 }else{
								 title=title.trim().replaceAll("\r|\n", "").replaceAll(" ", "");
							 }
						}
						String cellValue = "";//值
						if(cell != null){
							/*Integer cell_type_string=null;
							if(cellTypeMap!=null){
								cell_type_string=cellTypeMap.get(title);
							}
							if(cell_type_string==null){
								cell_type_string=Cell.CELL_TYPE_STRING;
							}
							cell.setCellType(cell_type_string);
							cellValue = cell.getStringCellValue();*/
							cellValue=getCallVal( cell);
							if(cellValue==null){
								cellValue="";
							}
						}
						cellValue = cellValue.trim();
						cellMap.put(title.trim(), cellValue);	
					}
					list.add(cellMap);
				}
			}
		}
	 return list;
	}
	private static String getCallVal(Cell cell){
		String ret;  
        switch (cell.getCellType()) {  
        case Cell.CELL_TYPE_BLANK:  
            ret = "";  
            break;  
        case Cell.CELL_TYPE_BOOLEAN:  
            ret = String.valueOf(cell.getBooleanCellValue());  
            break;  
        case Cell.CELL_TYPE_ERROR:  
            ret = null;  
            break;  
        /*case Cell.CELL_TYPE_FORMULA:  
            Workbook wb = cell.getSheet().getWorkbook();  
            CreationHelper crateHelper = wb.getCreationHelper();  
            FormulaEvaluator evaluator = crateHelper.createFormulaEvaluator();  
            ret = getCellValue(evaluator.evaluateInCell(cell));  
            break; */ 
        case Cell.CELL_TYPE_NUMERIC:  
            if (DateUtil.isCellDateFormatted(cell)) {   
                Date theDate = cell.getDateCellValue();  
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                ret = simpleDateFormat.format(theDate);  
            } else {   
                ret = NumberToTextConverter.toText(cell.getNumericCellValue());  
            }  
            break;  
        case Cell.CELL_TYPE_STRING:  
            ret = cell.getRichStringCellValue().getString();  
            break;  
        default:  
            ret = null;  
        }  
          
        return ret; //有必要自行trim
	}
	
	/**
	 * 获取批量插入的List  对象
	 * @param file  文件
	 * @param fieldMap  key  是字段名，value是excel列表名
	 * @param addMap  需要入库的非excel表数据。
	 * @return
	 */
	public static List<Map<String,String>> getBatchMap(File file,Map<String,String> fieldMap,
			Map<String,String> addMap,String file_name,int titleInRow) {
		List<Map<String,String>> batchMap = new ArrayList<Map<String,String>>();
		List<Map<String, String>> list=new ArrayList<Map<String,String>>();
		try {
			
			list = MoneyAuditExcelUtil.parserExcelToList(file,file_name,titleInRow-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Map<String, String> map : list) {
			Map<String,String> newDataMap=new HashMap<String, String>();
			if(addMap!=null&&!addMap.isEmpty()){
				newDataMap.putAll(addMap);
			}
			
			Set<String> set=fieldMap.keySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				String value = fieldMap.get(key);
				String new_value=map.get(value);
				newDataMap.put(key, new_value);
			}
			batchMap.add(newDataMap);
		}
		return batchMap;
	}

	
	public static void main(String[] args) {
		File file =new File("E:\\9.20导入数据数据CBSS-test.xlsx");
		MoneyAuditExcelUtil.getBatchMap(file, null, null,file.getName(),1);
	}


}
