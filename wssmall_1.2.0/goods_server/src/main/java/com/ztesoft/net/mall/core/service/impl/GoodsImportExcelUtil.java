package com.ztesoft.net.mall.core.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ztesoft.net.mall.core.model.GoodsImportLog;
import com.ztesoft.net.mall.core.model.TerminalImportLog;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

public class GoodsImportExcelUtil {

	public static List<GoodsImportLog> readExcel(File file,String fileName){
		List<GoodsImportLog> logs = new LinkedList<GoodsImportLog>();
		try{
			FileInputStream is = new FileInputStream(file);
			boolean islegal = true;
			Workbook wb = null;
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
			for(int k=0;k<sheetNum;k++){
				
				Sheet sheet = wb.getSheetAt(k);
				
				//遍历行
				for (int i=1;i<=sheet.getLastRowNum();i++) {
					GoodsImportLog log = new GoodsImportLog();
					Row row = sheet.getRow(i);
					if(row!=null){
						//遍历列
						for (int j=0;j<row.getLastCellNum();j++ ) {
							
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
							
							if("活动编码".equals(title)){
								if(StringUtils.isEmpty(cellValue))
									islegal = false;
								log.setRel_code(cellValue);
							}
							else if("活动名称".equals(title)){
								if(StringUtils.isEmpty(cellValue))
									islegal = false;
								log.setAtv_name(cellValue);
							}
							else if("活动描述".equals(title)){
								log.setAtv_desc(cellValue);
							}
							else if("活动期限".equals(title)){
								if(StringUtils.isEmpty(cellValue))
									islegal = false;
								log.setAtv_months(cellValue);
							}
							else if("活动类型编码值".equals(title)){
								if(StringUtils.isEmpty(cellValue))
									islegal = false;
								log.setAtv_code(cellValue);
							}
							else if("优惠购机方案".equals(title)){
								log.setOffer_terminals(cellValue);
							}
							else if("预存金额".equals(title)){
								Double deposit_fee = (cellValue==null || "".equals(cellValue)) ? 0d:Double.valueOf(cellValue);
								log.setDeposit_fee(deposit_fee);
							}
							else if("首月返还".equals(title)){
								Double order_return = (cellValue==null || "".equals(cellValue)) ? 0d:Double.valueOf(cellValue);
								log.setOrder_return(order_return);
							}
							else if("按月返还金额".equals(title)){
								Double mon_return = (cellValue==null || "".equals(cellValue)) ? 0d:Double.valueOf(cellValue);
								log.setMon_return(mon_return);
							}
							else if("备注".equals(title)){
								log.setComments(cellValue);
							}
							else if("产品编码".equals(title)){
								if(StringUtils.isEmpty(cellValue))
									islegal = false;
								log.setProduct_code(cellValue);
							}
							else if("产品名称".equals(title)){
								if(StringUtils.isEmpty(cellValue))
									islegal = false;
								log.setProduct_name(cellValue);
							}
							else if("产品描述".equals(title)){
								log.setGoods_desc(cellValue);
							}
							else if("产品品牌描述".equals(title)){
								log.setProd_brand_desc(cellValue);
							}
							else if("付费类型".equals(title)){
								log.setFee_type(cellValue);
							}
							else if("网别".equals(title)){
								log.setNo_gen(cellValue);
							}
							else if("合约费用".equals(title)){
								if(StringUtils.isEmpty(cellValue))
									islegal = false;
								double contract_fee = (cellValue==null || "".equals(cellValue))?0d:Double.valueOf(cellValue);
								log.setContract_fee(contract_fee);
							}
							else if("机型名称".equals(title)){
								log.setTerminals_name(cellValue);//model_name
							}
							else if("机型编码".equals(title)){
								if(StringUtils.isEmpty(cellValue))
									islegal = false;
								log.setTerminals_code(cellValue);
							}
							else if("品牌".equals(title)){
								log.setBrand_name(cellValue);
							}
							else if("品牌编码".equals(title)){
								log.setBrand_code(cellValue);
							}
							else if("型号".equals(title)){
								log.setModel_name(cellValue);
							}
							else if("型号编码".equals(title)){
								if(StringUtils.isEmpty(cellValue))
									islegal = false;
								log.setModel_code(cellValue);
							}
							else if("颜色".equals(title)){
								log.setColor_name(cellValue);
							}
							else if("颜色编码".equals(title)){
								if(StringUtils.isEmpty(cellValue))
									islegal = false;
								log.setColor_code(cellValue);
							}
							else if("资源类别名称".equals(title)){
								log.setGoods_type_name(cellValue);
							}
							log.setDeal_flag(0);
							log.setOper_id(ManagerUtils.getAdminUser().getUserid());
							log.setDeal_num(0);
						}
						if(islegal){
							logs.add(log);
						}
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return logs;
	}
	
	public static List<TerminalImportLog> readPhoneExcel(File file,String fileName){
		List<TerminalImportLog> logs = new LinkedList<TerminalImportLog>();
		try{
			FileInputStream is = new FileInputStream(file);

			Workbook wb = null;
	        if (!fileName.endsWith(".xlsx")){  
	            wb = new HSSFWorkbook(is);  
	        }else{  
	            wb = new XSSFWorkbook(is);  
	        }
	        
	        //遍历sheet
			for(int k=0;k<wb.getNumberOfSheets();k++){
				
				Sheet sheet = wb.getSheetAt(k);
				
				//遍历行
				for (int i=1;i<=sheet.getLastRowNum();i++) {
					TerminalImportLog log = new TerminalImportLog();
					Row row = sheet.getRow(i);
					
					//遍历列
					for (int j=0;j<row.getLastCellNum();j++ ) {
						
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
						
						if("品牌".equals(title)){
							log.setBrand_name(cellValue);
						}
						else if("型号".equals(title)){
							log.setModel_name(cellValue);
						}
						else if("颜色".equals(title)){
							log.setColor_name(cellValue);
						}
						else if("是否是4G".equals(title)){
							log.setIs_4g(cellValue);
						}
						else if("手机终端标识".equals(title)){
							log.setTerminal_id(cellValue);
						}
						else if("手机终端编码".equals(title)){
							log.setSn(cellValue);
						}
						else if("手机终端序列".equals(title)){
							log.setSku(cellValue);
						}
						else if("手机终端品牌".equals(title)){
							log.setBrand_code(cellValue);
						}
						else if("手机终端型号".equals(title)){
							log.setModel_code(cellValue);
						}
						else if("手机终端颜色".equals(title)){
							log.setColor_code(cellValue);
						}
						else if("手机终端外形".equals(title)){
							log.setTerminal_shape(cellValue);
						}
						else if("系统类型".equals(title)){
							log.setSystem_type_code(cellValue);
						}
						else if("手机终端系统".equals(title)){
							log.setSystem_type_name(cellValue);
						}
						else if("网络制式".equals(title)){
							log.setNetwork(cellValue);
						}
						else if("数据业务".equals(title)){
							log.setData_transfer(cellValue);
						}
						else if("浏览器".equals(title)){
							log.setBrowser(cellValue);
						}
						else if("机身内存".equals(title)){
							log.setTerminal_memory(cellValue);
						}
						else if("可扩展内存".equals(title)){
							log.setEx_memory(cellValue);
						}
						else if("屏幕尺寸".equals(title)){
							log.setScreen_size(cellValue);
						}
						else if("是否支持宽屏".equals(title)){
							log.setIs_widescreen(cellValue);
						}
						else if("屏幕类型".equals(title)){
							log.setScreen_type(cellValue);
						}
						else if("分辨率".equals(title)){
							log.setScreen_ratio(cellValue);
						}
						else if("触摸屏".equals(title)){
							log.setTouch_screen(cellValue);
						}
						else if("屏幕色彩".equals(title)){
							log.setScreen_color(cellValue);
						}
						else if("音乐播放".equals(title)){
							log.setMusic_play(cellValue);
						}
						else if("视频播放".equals(title)){
							log.setVideo_play(cellValue);
						}
						else if("是否支持".equals(title)){
							log.setIs_support(cellValue);
						}
						else if("电子书".equals(title)){
							log.setE_book(cellValue);
						}
						else if("录音".equals(title)){
							log.setSound_record(cellValue);
						}
						else if("彩信功能".equals(title)){
							log.setIs_mms(cellValue);
						}
						else if("是否Office功能".equals(title)){
							log.setIs_office(cellValue);
						}
						else if("摄像头".equals(title)){
							log.setFront_camera(cellValue);
						}
						else if("副摄像头".equals(title)){
							log.setBack_camera(cellValue);
						}
						else if("录像".equals(title)){
							log.setVideo_record(cellValue);
						}
						else if("传感器类型".equals(title)){
							log.setSensor_type(cellValue);
						}
						else if("变焦模式".equals(title)){
							log.setZoom_model(cellValue);
						}
						else if("是否支持GPRS".equals(title)){
							log.setIs_gprs(cellValue);
						}
						else if("是否支持蓝牙".equals(title)){
							log.setIs_bluetooth(cellValue);
						}
						else if("电池配置".equals(title)){
							log.setBattery(cellValue);
						}
						else if("电池容量".equals(title)){
							log.setBattery_capacity(cellValue);
						}
						else if("理论通话时间".equals(title)){
							log.setTalk_time(cellValue);
						}
						else if("理论待机时间".equals(title)){
							log.setStandby_time(cellValue);
						}
						else if("超长待机".equals(title)){
							log.setLong_standby(cellValue);
						}
						else if("大图".equals(title)){
							log.setBig_image(cellValue);
						}
						else if("小图".equals(title)){
							log.setSmall_image(cellValue);
						}
						else if("机身重量".equals(title)){
							log.setWeight(cellValue);
						}
						else if("特性".equals(title)){
							log.setCharacter(cellValue);
						}
						else if("特色功能".equals(title)){
							log.setSpecial_feature(cellValue);
						}
						else if("增值业务".equals(title)){
							log.setVa_business(cellValue);
						}
						else if("机身尺寸".equals(title)){
							log.setTerminal_size(cellValue);
						}
						else if("描述".equals(title)){
							log.setTerminal_desc(cellValue);
						}
						else if("说明".equals(title)){
							log.setExplanation(cellValue);
						}
						else if("电池类型".equals(title)){
							log.setBattery_type(cellValue);
						}
						else if("备注".equals(title)){
							log.setComments(cellValue);
						}
						else if("协议价格".equals(title)){
							log.setPrice(cellValue);
						}
						else if("商城产品CODE".equals(title)){
							log.setSc_product_code(cellValue);
						}
						else if("商城型号CODE".equals(title)){
							log.setSc_model_code(cellValue);
						}
						else if("商城颜色CODE".equals(title)){
							log.setSc_color_code(cellValue);
						}
						else if("手机操作系统版本".equals(title)){
							log.setSystem_version(cellValue);
						}
						else if("CPU信息".equals(title)){
							log.setCpu_desc(cellValue);
						}
						else if("合约费用".equals(title)){
							log.setRam_desc(cellValue);
						}
						else if("运行时内存".equals(title)){
							log.setRun_memory(cellValue);
						}
						else if("是否双卡双待".equals(title)){
							log.setIs_double_card(cellValue);
						}
						else if("终端显示类型".equals(title)){
							log.setDisplay_type(cellValue);
						}
						else if("是否大屏".equals(title)){
							log.setIs_large_scn(cellValue);
						}
						log.setDeal_flag(0);
						log.setOper_id(ManagerUtils.getAdminUser().getUserid());
						log.setDeal_num(0);
					}
					logs.add(log);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return logs;
	}

}