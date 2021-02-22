package com.zte.cbss.convert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.zte.cbss.autoprocess.model.CustomBill;
import com.zte.cbss.autoprocess.model.ParameterData;

public class ExtGenerationFree extends ExtGeneration implements ConvertExt {
	private static Logger logger = Logger.getLogger(ExtGenerationFree.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	Date now = new Date();

	@Override
	public String getExt(ParameterData data) {
		// TODO Auto-generated method stub

		String flowBaseDiscntCode = "";String callBaseDiscntCode = "";String msgBaseDiscntCode = "";
		CustomBill bill = data.getBill();
		List<String> addServiceCode = bill.getAddServiceCode();
	
		
		String callDisplay = bill.getCallDisplay();//來電顯示
		String flowPackage = bill.getFlowPackage() ;//流量包
		String flowFirstFeeType = bill.getFlowFirstFeeType();// 流量包"01"套餐包外资费 "02"全月套餐 "03"套餐减半
		String callPackage = bill.getCallPackage();//语音包
		String callFirstFeeType = bill.getCallFirstFeeType();// 语音包"01"套餐包外资费 "02"全月套餐 "03"套餐减半
		String messageCode = bill.getMessageCode();// messageCode短信资费包
		String messageFirstFeeType = bill.getMessageFirstFeeType();// 短信包"01"套餐包外资费 "02"全月套餐 "03"套餐减半
	    String proId = bill.getSelectedProductId();
	    
	    
	    StringBuilder  builder = new StringBuilder();
	    builder.append( "\"TF_B_TRADE_DISCNT\": {\"ITEM\": [ ");
	    if(StringUtils.isNotBlank(flowPackage)){
	    	if("02".equals(flowFirstFeeType)){
			    flowBaseDiscntCode = Integer.parseInt(flowPackage)+"";
		   }else if("03".equals(flowFirstFeeType)){
			    flowBaseDiscntCode = (Integer.parseInt(flowPackage)+10000)+"";
		   }else if("01".equals(flowFirstFeeType)){
			   flowBaseDiscntCode = (Integer.parseInt(flowPackage)+20000)+"";
		   }
	    	builder.append("{\"ID\": \"\",\"ID_TYPE\": \"1\",\"PRODUCT_ID\": \"").append(proId);
	    	builder.append("\",\"PACKAGE_ID\": \"51002514\", \"DISCNT_CODE\":\"");
	    	builder.append(flowBaseDiscntCode).append("\", \"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \"");
	    	builder.append(  sdf.format(now)).append("\",\"END_DATE\": \"2050-12-31 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\"},");
	    }
	    if(StringUtils.isNotBlank(callPackage)){
	    	if("02".equals(callFirstFeeType)){
	    		 callBaseDiscntCode = Integer.parseInt(callPackage)+"";
		    }else if("03".equals(callFirstFeeType)){
		    	 callBaseDiscntCode =  (Integer.parseInt(callPackage)+10000)+"";
		    }else if("01".equals(callFirstFeeType)){
		    	 callBaseDiscntCode =  (Integer.parseInt(callPackage)+20000)+"";
		    }
	    	builder.append("{\"ID\": \"\",\"ID_TYPE\": \"1\",\"PRODUCT_ID\": \"").append(proId);
	    	builder.append("\",\"PACKAGE_ID\": \"51002515\", \"DISCNT_CODE\":\"").append(callBaseDiscntCode);
	    	builder.append("\", \"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(now));
	    	builder.append("\",\"END_DATE\": \"2050-12-31 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\"},");
	    }
	    if(StringUtils.isNotBlank(messageCode)){
	    	if("02".equals(messageFirstFeeType)){
	    		msgBaseDiscntCode = Integer.parseInt(messageCode)+"";
		    }else if("03".equals(messageFirstFeeType)){
		    	msgBaseDiscntCode =  (Integer.parseInt(messageCode)+3000)+"";
		    }else if("01".equals(messageFirstFeeType)){
		    	msgBaseDiscntCode =  (Integer.parseInt(messageCode)+6000)+"";
		    }
	    	builder.append("{\"ID\": \"\",\"ID_TYPE\": \"1\",\"PRODUCT_ID\": \"").append(proId);
	    	builder.append("\",\"PACKAGE_ID\": \"51002516\", \"DISCNT_CODE\":\"").append(msgBaseDiscntCode);
	    	builder.append("\", \"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(now));
	    	builder.append("\",\"END_DATE\": \"2050-12-31 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\"},");
	    }
	    if(StringUtils.isNotBlank(callDisplay)){
	    	builder.append("{\"ID\": \"\",\"ID_TYPE\": \"1\",\"PRODUCT_ID\": \"").append(proId);
	    	builder.append("\",\"PACKAGE_ID\": \"51002531\", \"DISCNT_CODE\":\"5557000\", \"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \"" + sdf.format(now) + "\",\"END_DATE\": \"2050-12-31 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\"},");			
	    }
	    //流量包八折优惠
	    builder.append("{\"ID\": \"\",\"ID_TYPE\": \"1\",\"PRODUCT_ID\": \"").append(proId);
	    builder.append("\",\"PACKAGE_ID\": \"51002540\", \"DISCNT_CODE\":\"5992110\", \"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(now));
	    builder.append("\",\"END_DATE\": \"2050-12-31 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\"},");
	    //语音包八折优惠
	    if(StringUtils.isNotBlank(callPackage)){
	    	builder.append("{\"ID\": \"\",\"ID_TYPE\": \"1\",\"PRODUCT_ID\": \"").append(proId);
	    	builder.append("\",\"PACKAGE_ID\": \"51002540\", \"DISCNT_CODE\":\"5992120\", \"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(now));
	    	builder.append("\",\"END_DATE\": \"2050-12-31 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\"},");
	    }
	    
	    
	    String messageUpgradeCode = ""; // 短信网龄升级
	    String mupExt = getMessageUpgradeExt(proId, messageUpgradeCode);
	    String optExt = getOptionalServiceExt(addServiceCode, proId);	
	    String ext = getProductExt(proId)+builder.append(optExt).append(mupExt).append(getBasicServiceExt(proId,addServiceCode,callDisplay)).append(getOptionSpExt(addServiceCode,proId)).append(getProductTypeExt(proId,"")).toString();
		return ext;
	}
	/**
	 * 产品ext
	 * 
	 * @param proId
	 * @param activityCode
	 * @return
	 */
	public String getProductExt(String proId){
		String ext = "{\"TF_B_TRADE_PRODUCT\": {\"ITEM\": [{\"PRODUCT_ID\": \"89002148\",\"PRODUCT_MODE\": \"00\",\"START_DATE\": \""+ sdf.format(now)+"\",\"END_DATE\": \"2050-12-31 00:00:00\",\"MODIFY_TAG\": \"0\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"BRAND_CODE\": \"4G00\",\"X_DATATYPE\": \"NULL\"}]},";
		return ext;
	}
	/**
	 * 默认基本业务功能包
	 * 
	 * @param proId
	 * @return
	 */
	public String getBasicServiceExt(String proId,List<String> addServiceCode,String callDisplay ) {
		StringBuilder  builder = new StringBuilder();
		
		builder.append("\"TF_B_TRADE_SVC\": { \"ITEM\": [ ");
			
		if(callDisplay!=null&&"5557000".equals(callDisplay)){
				
				builder.append("{\"SERVICE_ID\": \"50004\",\"PRODUCT_ID\": \"").append(proId);
				builder.append("\",  \"PACKAGE_ID\": \"51002531\", \"MODIFY_TAG\": \"0\", \"START_DATE\": \"").append(sdf.format(new Date()));
				builder.append( "\", \"END_DATE\": \"2050-12-31 00:00:00\", \"USER_ID_A\": \"-1\", \"ITEM_ID\": \"\", \"X_DATATYPE\": \"NULL\"},");

			}
			builder.append("{ \"SERVICE_ID\": \"50000\",\"PRODUCT_ID\": \"").append(proId);
			builder.append( "\", \"PACKAGE_ID\": \"59999793\",\"MODIFY_TAG\": \"0\", \"START_DATE\": \"").append(sdf.format(new Date()));
			builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\"},");
			builder.append("{ \"SERVICE_ID\": \"50003\", \"PRODUCT_ID\": \"").append(proId);
			builder.append("\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date()));
			builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
			builder.append("{ \"SERVICE_ID\": \"50001\", \"PRODUCT_ID\": \"").append(proId);
			builder.append("\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date()));
			builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
			builder.append( "{ \"SERVICE_ID\": \"50010\", \"PRODUCT_ID\": \"").append(proId);
			builder.append( "\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date()));
			builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
			builder.append("{ \"SERVICE_ID\": \"50014\", \"PRODUCT_ID\": \"").append(proId);
			builder.append( "\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date()));
			builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
			builder.append("{ \"SERVICE_ID\": \"50100\", \"PRODUCT_ID\": \"").append(proId);
			builder.append( "\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date()));
			builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
			builder.append("{ \"SERVICE_ID\": \"50103\", \"PRODUCT_ID\": \"").append(proId);
			builder.append( "\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date()));
			builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
			builder.append("{ \"SERVICE_ID\": \"50300\", \"PRODUCT_ID\": \"").append(proId);
			builder.append( "\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date()));
			builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
			builder.append("{ \"SERVICE_ID\": \"50006\", \"PRODUCT_ID\": \"").append(proId);
			builder.append("\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date()));
			builder.append( "\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
			builder.append( "{ \"SERVICE_ID\": \"50007\", \"PRODUCT_ID\": \"").append(proId);
			builder.append("\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date()));
			builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
			builder.append("{ \"SERVICE_ID\": \"50019\", \"PRODUCT_ID\": \"").append(proId);
			builder.append( "\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date()));
			builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
			builder.append("{ \"SERVICE_ID\": \"50020\", \"PRODUCT_ID\": \"").append(proId);
			builder.append("\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date()));
			builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
			builder.append("{ \"SERVICE_ID\": \"50021\", \"PRODUCT_ID\": \"").append(proId);
            builder.append( "\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date()));
            builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
            builder.append("{ \"SERVICE_ID\": \"50022\", \"PRODUCT_ID\": \"").append(proId);
            builder.append("\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date()));
            builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" }");
        if (addServiceCode == null || addServiceCode.isEmpty()){
        	builder.append("] },");
		}else if(addServiceCode != null && !addServiceCode.isEmpty()){
			builder.append(",{ \"SERVICE_ID\": \"50201\", \"PRODUCT_ID\": \"").append(proId);
			builder.append("\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date()));
			builder.append( "\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" }");
			builder.append("] },");
		}
		
		return builder.toString();
	}
	
	/**
	 * 增值业务可选包
	 * 
	 * @param addServiceCode
	 * @param proId
	 * @return
	 */
	@Override
	public String getOptionalServiceExt(List<String> addServiceCode,
			String proId) {

		StringBuilder builder = new StringBuilder();
		if (addServiceCode != null && !addServiceCode.isEmpty()) {
			if (addServiceCode.contains("5574000")) {

				builder.append("{ \"ID\": \"\", \"ID_TYPE\": \"1\",\"PRODUCT_ID\": \"").append(proId);
				builder.append("\",\"PACKAGE_ID\": \"51002517\",\"DISCNT_CODE\": \"5574000\",\"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date())); 
				builder.append("\",\"END_DATE\": \"2050-12-31 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\":\"\", \"X_DATATYPE\": \"NULL\"},");

			}else if(addServiceCode.contains("5572000")){
				builder.append("{ \"ID\": \"\", \"ID_TYPE\": \"1\",\"PRODUCT_ID\": \"").append(proId);
				builder.append("\",\"PACKAGE_ID\": \"51002517\",\"DISCNT_CODE\": \"5572000\",\"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append(sdf.format(new Date())); 
				builder.append("\",\"END_DATE\": \"2050-12-31 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\":\"\", \"X_DATATYPE\": \"NULL\"},");
			}
		}
		return builder.toString();
	}
	/**
	 * 短信网龄升级包
	 * 
	 * @param proId
	 * @param messageUpgradeCode
	 * @return
	 */
	@Override
	public String getMessageUpgradeExt(String proId, String messageUpgradeCode) {
		
		Date nextMonth = DateUtils.addMonths(new Date(), 1);
		StringBuilder builder = new StringBuilder();
		builder.append("{\"ID\": \"\",\"ID_TYPE\": \"1\",\"PRODUCT_ID\": \"").append(proId);
		builder.append("\",\"PACKAGE_ID\": \"59999764\",\"DISCNT_CODE\": \"5702000\", \"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \"").append( ymsdf.format(nextMonth));
		builder.append("-01 00:00:00\",\"END_DATE\": \"2050-12-31 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\", \"X_DATATYPE\": \"NULL\"}]},");
		return builder.toString();
	}
	
	
	@Override
	public String getOptionSpExt(List<String> addServiceCode, String proId) {

		StringBuilder builder = new StringBuilder();
        //包含5573000时一定会全选三个
		if(addServiceCode!=null&&!addServiceCode.isEmpty()){
		if (addServiceCode.contains("5574000")
				&& addServiceCode.contains("5573000")) {

			builder.append( " \"TF_B_TRADE_SP\": { \"ITEM\": [ {\"SP_ID\": \"82000011\",\"PRODUCT_ID\": \"").append(proId);
			builder.append("\",\"PACKAGE_ID\": \"51002517\", \"MODIFY_TAG\": \"0\",\"SP_SERVICE_ID\": \"99104953\",\"ITEM_ID\": \"\",\"FIRST_BUY_TIME\": \"").append(sdf.format(new Date()));
            builder.append("\", \"START_DATE\": \"").append(sdf.format(new Date()));
            builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"PARTY_ID\": \"90213\",\"SP_PRODUCT_ID\": \"8200001100\",\"X_DATATYPE\": \"NULL\"},");
            builder.append("{\"SP_ID\": \"70000001\",\"PRODUCT_ID\": \"").append(proId);
            builder.append("\",\"PACKAGE_ID\": \"51002517\", \"MODIFY_TAG\": \"0\",\"SP_SERVICE_ID\": \"99000472\",\"ITEM_ID\": \"\",\"FIRST_BUY_TIME\": \"").append(sdf.format(new Date()));
            builder.append("\", \"START_DATE\": \"").append(sdf.format(new Date())).append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"PARTY_ID\": \"90115\",\"SP_PRODUCT_ID\": \"7000000101\",\"X_DATATYPE\": \"NULL\"}]},");
		} else if (addServiceCode.contains("5572000")) {

			builder.append( " \"TF_B_TRADE_SP\": { \"ITEM\": [").append(" {\"SP_ID\": \"70000001\",\"PRODUCT_ID\": \"").append(proId);
			builder.append("\",\"PACKAGE_ID\": \"51002517\", \"MODIFY_TAG\": \"0\",\"SP_SERVICE_ID\": \"99000472\",\"ITEM_ID\": \"\",\"FIRST_BUY_TIME\": \"").append(sdf.format(new Date())); 
			builder.append("\", \"START_DATE\": \"").append(sdf.format(new Date())).append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"PARTY_ID\": \"90115\",\"SP_PRODUCT_ID\": \"7000000101\",\"X_DATATYPE\": \"NULL\"}]},");

		}
		}
		return builder.toString();
	}
     
	@Override
	public String getProductTypeExt(String proId, String activityCode) {
        
		String ext = "\"TF_B_TRADE_PRODUCT_TYPE\": {\"ITEM\": [{\"PRODUCT_ID\": \""
				+ proId
				+ "\", \"PRODUCT_MODE\": \"00\",\"START_DATE\": \"" + sdf.format(now) + "\",\"END_DATE\": \"2050-12-31 00:00:00\",\"MODIFY_TAG\": \"0\",\"X_DATATYPE\": \"NULL\",\"PRODUCT_TYPE_CODE\": \"4G000001\" }]}}";

		return ext;
	}
	public static void main(String[] args) {

		// logger.info(new
		// ExtGeneration().getBasicPackageExt("99999823","89000100", "5608000",
		// "59999771", "01",null));

		ParameterData data = new ParameterData();
		CustomBill bill = new CustomBill();
		bill.setSelectedProductId("89002148");
		//bill.setActivityCode("89000100");
		bill.setFirstFeeType("02");
		List<String> addServiceCode = new ArrayList<String>();
		addServiceCode.add("5574000");addServiceCode.add("5573000");
		//addServiceCode.add("5572000");
		bill.setMessageCode("5562000");
		bill.setFlowPackage("5501000");
	//	bill.setCallPackage("5532000");
		bill.setCallDisplay("5557000");
		bill.setFlowFirstFeeType("01");
		bill.setCallFirstFeeType("02");
		bill.setMessageFirstFeeType("03");
		bill.setAddServiceCode(addServiceCode );
		data.setBill(bill);
		logger.info(new ExtGenerationFree().getExt(data));

	}

}
