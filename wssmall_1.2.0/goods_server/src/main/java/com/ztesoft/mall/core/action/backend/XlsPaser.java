package com.ztesoft.mall.core.action.backend;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import jxl.Sheet;
import jxl.Workbook;

import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.service.IGoodsManager;

	public class XlsPaser {
		private static Logger logger = Logger.getLogger(XlsPaser.class);
		@Resource
		protected IGoodsManager goodsManager;
		private static XlsPaser paser;
		
		
		public IGoodsManager getGoodsManager() {
			return goodsManager;
		}
		public void setGoodsManager(IGoodsManager goodsManager) {
			this.goodsManager = goodsManager;
		}
		public static XlsPaser getInstance(){
			if (paser==null){
				return new XlsPaser();
			}
			return paser;
		}
		/*
		 * 解析从前台过来的批量导入商品文件
		 */
		public List paseXls(String fileName) throws Exception{
			Workbook workbook =null;
			Sheet sheet[]=null;
			String lab=null;
			int rows=0;
			int cols=0;
			List<Goods> list=new ArrayList<Goods>();
			String json="";
//			try {
				workbook=Workbook.getWorkbook(new File(fileName));
				sheet=workbook.getSheets();
				rows=sheet[0].getRows();
				cols=sheet[0].getColumns();
				for (int i = 2; i < rows; i++) {
					Goods goods=new Goods();
//					GoodsDetail detail=new GoodsDetail();
					String subStypeName=sheet[0].getCell(0, i).getContents();
					if(this.goodsManager==null)
						this.goodsManager = SpringContextHolder.getBean("goodsManager");
					String catId=this.goodsManager.getCatID(subStypeName);
					goods.setCat_id(catId);
					goods.setType_id(goodsManager.getTypeID(sheet[0].getCell(1,i).getContents()));
//					goods.setType_name(sheet[0].getCell(1,i).getContents());
					goods.setBrand_id(goodsManager.getBrandID(sheet[0].getCell(2,i).getContents()));
					String stypeId=goodsManager.getStypeID(sheet[0].getCell(3,i).getContents());
					goods.setStype_id(stypeId);
					goods.setSub_stype_id(goodsManager.getSubStypeID(sheet[0].getCell(4,i).getContents(),stypeId));
					goods.setName(sheet[0].getCell(5,i).getContents());
					goods.setSimple_name(sheet[0].getCell(6,i).getContents());
					goods.setSn(sheet[0].getCell(7,i).getContents());
					goods.setMktprice(Double.parseDouble(sheet[0].getCell(8,i).getContents()));
					goods.setPrice(Double.parseDouble(sheet[0].getCell(9,i).getContents()));
					String selledObject=sheet[0].getCell(10,i).getContents();
					goods.setRole_type(selledObject);
					goods.setNormal_price(sheet[0].getCell(11,i).getContents());
					goods.setSilver_price(sheet[0].getCell(12,i).getContents());
					goods.setGold_price(sheet[0].getCell(13,i).getContents());
					int marketEnable=0;
					if ("是".equals(sheet[0].getCell(14,i).getContents())){
						marketEnable=1;
					}
					goods.setMarket_enable(marketEnable);
					
					goods.setStore(Integer.parseInt(sheet[0].getCell(15,i).getContents()));
					goods.setWeight(Double.parseDouble(sheet[0].getCell(16,i).getContents()));
					goods.setPoint(Integer.parseInt(sheet[0].getCell(17,i).getContents()));
					
					//解析商品详情
//					detail.setSeries(sheet[0].getCell(18,i).getContents());
//					detail.setModel(sheet[0].getCell(19,i).getContents());
//					detail.setColor(sheet[0].getCell(20,i).getContents());
//					detail.setPlatform(sheet[0].getCell(21,i).getContents());
//					detail.setOs(sheet[0].getCell(22,i).getContents());
//					detail.setCpu_style(sheet[0].getCell(23,i).getContents());
//					detail.setCpu_model(sheet[0].getCell(24,i).getContents());
//					detail.setCpu_speed(sheet[0].getCell(25,i).getContents());
//					goods.setGoodsDetail(detail);
					//[{"color":"月光银","cpu_model":"i5-3337U","cpu_speed":"1.8GHz主频, 睿频可达2.7GH"}]
					for (int j = 18; j < cols; j++) {
						if (sheet[0].getCell(j,i).getContents()!= null && !"".equals(sheet[0].getCell(j,i).getContents())){
							if (j == 18){
								json+="[{\""+sheet[0].getCell(j,0).getContents()+"\":\""+sheet[0].getCell(j,i).getContents()+"\"";
							}else if (j == cols-1 ){
								json+=",\""+sheet[0].getCell(j,0).getContents()+"\":\""+sheet[0].getCell(j,i).getContents()+"\"}]";
							}else{
								json+=",\""+sheet[0].getCell(j,0).getContents()+"\":\""+sheet[0].getCell(j,i).getContents()+"\"";
							}
							
						}
						}
					goods.setJson(json);
					logger.info("json:"+json);
					list.add(goods);
					
				}
			return list;
		}
	}


