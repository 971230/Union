package com.ztesoft.net.mall.core.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.lang3.StringUtils;

import com.ztesoft.net.mall.core.model.Ciudad;
import com.ztesoft.net.mall.core.model.NumeroImportLog;
import commons.CommonTools;

@Deprecated
public class ReadWriteExcelUtil {


	/**
	 * 從excel文件中讀取所有的內容
	 * 
	 * @param file
	 *            excel文件
	 * @return excel文件的內容
	 */
	public List readExcel(File file,String tipo,Map map) {
		StringBuffer sb = new StringBuffer();
		Workbook wb = null;
		try {
			// 构造Workbook（工作薄）对象
			wb = Workbook.getWorkbook(file);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		List l = null;
		
		if (wb == null)
			return l;

		// 获得了Workbook对象之后，就可以通过它得到Sheet（工作表）对象了
		Sheet[] sheets = wb.getSheets();

		 if(tipo.equals("ciudad")){
			l= importCiudad(sheets,map);
		} else if(tipo.equals("numero")){
			l= importNumero(sheets,map);
		} else if(tipo.equals("goods")){
			l = importGoods(sheets,map);
		}
		
		// 最后关闭资源，释放内存
		wb.close();
		return l;
	}
	
	private List importCiudad(Sheet[] sheets,Map map) {
		
		List ciudads= new ArrayList();
		
		if (sheets != null && sheets.length > 0) {
			// 对每个工作表进行循环
			for (int i = 0; i < sheets.length; i++) {
				// 得到当前工作表的行数
				int rowNum = sheets[i].getRows();
				for (int j = 1; j < rowNum; j++) {
					Ciudad c = new Ciudad();
					// 得到当前行的所有单元格
					Cell[] cells = sheets[i].getRow(j);
					
					if (cells != null && cells.length > 0) {
						// 对每个单元格进行循环
						for (int k = 0; k < cells.length; k++) {
							// 读取当前单元格的值
							String title = sheets[i].getCell(k,0).getContents();
							String cellValue = cells[k].getContents();
							if(title.equals("序号")){
								c.setSeg_id(cellValue);
							}else if(title.equals("地市编码")){
								c.setRegion_id(cellValue);
							}else if(title.equals("地市名称")){
								c.setRegion_name(cellValue);
							}else if(title.equals("号段")){
								c.setSeg_no(cellValue);
							}else if(title.equals("数据来源")){
								c.setSource_from(cellValue);
							}
						}
					}
					ciudads.add(c);
				}
			}
		}
		return ciudads;
	}

	private List importNumero(Sheet[] sheets,Map map) {

		List list= new ArrayList();
		
		if (sheets != null && sheets.length > 0) {
			// 对每个工作表进行循环 
			int sheetNum = sheets.length;
			if(sheetNum>1){
				sheetNum--;
			}
			for (int i = 0; i < sheetNum; i++) {
				// 得到当前工作表的行数
				int rowNum = sheets[i].getRows();
				for (int j = 1; j < rowNum; j++) {
					NumeroImportLog log = new NumeroImportLog();
					// 得到当前行的所有单元格
					Cell[] cells = sheets[i].getRow(j);
					
					if (cells != null && cells.length > 0) {
						// 对每个单元格进行循环
						for (int k = 0; k < cells.length; k++) {
							// 读取当前单元格的值
							String title = sheets[i].getCell(k,0).getContents();
							String cellValue = cells[k].getContents();
							if(title.trim().equals("手机号码")){
								if(StringUtils.isEmpty(cellValue)) {
									CommonTools.addFailError("第" + (j+1) + "行的" + title.trim() + "不能为空！");
								}
								log.setDn_no(cellValue);
							}else if(title.trim().equals("网别")){
								if(StringUtils.isEmpty(cellValue)) {
									CommonTools.addFailError("第" + (j+1) + "行的" + title.trim() + "不能为空！");
								}
								log.setNo_gen(cellValue);
							}else if(title.trim().equals("预存款")){
								if(StringUtils.isEmpty(cellValue)) {
									CommonTools.addFailError("第" + (j+1) + "行的" + title.trim() + "不能为空！");
								}
									log.setDeposit(Double.valueOf(cellValue));
							}else if(title.trim().equals("合约期")){
								if(StringUtils.isEmpty(cellValue)) {
									CommonTools.addFailError("第" + (j+1) + "行的" + title.trim() + "不能为空！");
								}
									log.setPeriod(Integer.valueOf(cellValue));
							}else if(title.trim().equals("最低消费额")){
								if(StringUtils.isEmpty(cellValue)) {
									CommonTools.addFailError("第" + (j+1) + "行的" + title.trim() + "不能为空！");
								}
									log.setLowest(Double.valueOf(cellValue));
							}else if(title.trim().equals("付费方式")){
								if(StringUtils.isEmpty(cellValue)) {
									CommonTools.addFailError("第" + (j+1) + "行的" + title.trim() + "不能为空！");
								}
								log.setCharge_type(cellValue);
							}else if(title.trim().equals("减免金额")){
								if(StringUtils.isEmpty(cellValue)) {
									CommonTools.addFailError("第" + (j+1) + "行的" + title.trim() + "不能为空！");
								}
								if(!StringUtils.isEmpty(cellValue)){
									log.setFee_adjust(Double.valueOf(cellValue));
								}
								else{
									log.setFee_adjust(0d);
								}
							}else if(title.trim().equals("号码分组编码")){
								if(!StringUtils.isEmpty(cellValue)){
									log.setGroup_code(cellValue);
								}
							}
						}
					}
					list.add(log);
				}
			}
		}
		return list;
	}
	
	public List importGoods(Sheet[] sheets,Map map){
		List list= new ArrayList();
		
		if (sheets != null && sheets.length > 0) {
			// 对每个工作表进行循环
			for (int i = 0; i < sheets.length; i++) {
				// 得到当前工作表的行数
				int rowNum = sheets[i].getRows();
				for (int j = 1; j < rowNum; j++) {
					Map good = new HashMap();
					// 得到当前行的所有单元格
					Cell[] cells = sheets[i].getRow(j);
					if (cells != null && cells.length > 0) {
						// 对每个单元格进行循环
						for (int k = 0; k < cells.length; k++) {
							// 读取当前单元格的值
							//需要读取的列：活动名称，活动期限，活动类型编码，预存金额，按月返还金额，
							//产品编码，产品名称，合约费用，机型名称，型号编码，颜色编码
							String title = sheets[i].getCell(k,0).getContents();
							String cellValue = cells[k].getContents();
							if("活动名称".equals(title)){
								good.put("atv_name", cellValue);
							}
							else if("活动期限".equals(title)){
								good.put("atv_months", cellValue);
							}
							else if("活动类型编码".equals(title)){
								good.put("atv_code", cellValue);
							}
							else if("预存金额".equals(title)){
								good.put("deposit_fee", cellValue);
							}
							else if("按月返还金额".equals(title)){
								good.put("mon_return", cellValue);
							}
							else if("产品编码".equals(title)){
								good.put("product_code", cellValue);
							}
							else if("产品名称".equals(title)){
								good.put("product_name", cellValue);
							}
							else if("合约费用".equals(title)){
								good.put("price", cellValue);
								good.put("mktprice", cellValue);
							}
							else if("机型名称".equals(title)){
								good.put("model_name", cellValue);
							}
							else if("型号编码".equals(title)){
								good.put("model_code", cellValue);
							}
							else if("颜色编码".equals(title)){
								good.put("color", cellValue);
							}
						}
					}
					list.add(good);
				}
			}
		}
		return list;
	}
	
	/**
	 * 把內容寫入excel文件中
	 * 
	 * @param fileName
	 *            要寫入的文件的名稱
	 */
	public static void writeExcel(String fileName) {
		WritableWorkbook wwb = null;
		try {
			// 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
			wwb = Workbook.createWorkbook(new File(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (wwb != null) {
			// 创建一个可写入的工作表
			// Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
			WritableSheet ws = wwb.createSheet("sheet1", 0);

			// 下面开始添加单元格
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 5; j++) {
					// 这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行
					Label labelC = new Label(j, i, "这是第" + (i + 1) + "行，第"
							+ (j + 1) + "列");
					try {
						// 将生成的单元格添加到工作表中
						ws.addCell(labelC);
					} catch (RowsExceededException e) {
						e.printStackTrace();
					} catch (WriteException e) {
						e.printStackTrace();
					}

				}
			}

			try {
				// 从内存中写入文件中
				wwb.write();
				// 关闭资源，释放内存
				wwb.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
	}

}
