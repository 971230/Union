package com.zte.cbss.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.zte.cbss.autoprocess.model.CustomBill;
import com.zte.cbss.autoprocess.model.ParameterData;

/**
 * 一体化ext转换实现
 * 
 * @author acer
 * 
 */
public class ExtGeneration implements ConvertExt {
	private static Logger logger = Logger.getLogger(ExtGeneration.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	SimpleDateFormat ymsdf = new SimpleDateFormat("yyyy-MM");
	SimpleDateFormat ymdsdf = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public String getExt(ParameterData data) {

		String baseDiscntCode = "";
		String basePackageCode = "";
		CustomBill bill = data.getBill();
		String proId = bill.getSelectedProductId();

		/**
		 * 这些数据需要从bill获得
		 */
		String activityCode = bill.getActivityCode(); // 活动类型
		List<String> addServiceCode = bill.getAddServiceCode();
		// String callDisplay = bill.getCallDisplay();//來電顯示
		String firstFeeType = bill.getFirstFeeType();// "01"套餐包外资费 "02"全月套餐
														// "03"套餐减半
		String messageCode = bill.getMessageCode();// messageCode短信资费包
		if ("99999823".equals(proId)) {
			basePackageCode = "59999771";
			baseDiscntCode = "5608000";

		} else if ("99999824".equals(proId)) {
			basePackageCode = "59999774";
			baseDiscntCode = "5607000";

		} else if ("99999825".equals(proId)) {
			basePackageCode = "59999777";
			baseDiscntCode = "5606000";

		} else if ("99999826".equals(proId)) {
			basePackageCode = "59999780";
			baseDiscntCode = "5605000";

		} else if ("99999827".equals(proId)) {
			basePackageCode = "59999783";
			baseDiscntCode = "5604000";

		} else if ("99999828".equals(proId)) {
			basePackageCode = "59999786";
			baseDiscntCode = "5603000";

		} else if ("99999829".equals(proId)) {
			basePackageCode = "59999789";
			baseDiscntCode = "5602000";

		} else if ("99999830".equals(proId)) {
			basePackageCode = "59999792";
			baseDiscntCode = "5601000";
		}

		List<String> extList = new ArrayList<String>();
		extList.add(getProductExt(proId, activityCode));
		extList.add(getBasicPackageExt(proId, activityCode, baseDiscntCode,
				basePackageCode, firstFeeType, messageCode, addServiceCode));
		extList.add(getBasicServiceExt(proId, addServiceCode));
		extList.add(getSubItemExt());
		if (addServiceCode != null && !addServiceCode.isEmpty()) {
			extList.add(getOptionSpExt(addServiceCode, proId));
		}
		extList.add(getProductTypeExt(proId, activityCode));

		String returnExt = StringUtils.join(extList, ",");
		return "{" + returnExt + "}";
	}

	/**
	 * 1.基本套餐 2。来电显示（默认选择） 3。短信资费（短彩信的数据格式不知道以及字段没明确 ，暂时用messageCode）
	 * 
	 * @param proId
	 * @param activityCode
	 * @param baseDiscntCode
	 * @param basePackageCode
	 * @param firstFeeType
	 * @param messageCode
	 * @return
	 */
	public String getBasicPackageExt(String proId, String activityCode,
			String baseDiscntCode, String basePackageCode, String firstFeeType,
			String messageCode, List<String> addServiceCode) {// firstFeeType
																// "01"套餐包外资费
																// "02"全月套餐
																// "03"套餐减半

		int baseCode = 0;
		int packageCode = 0;
		int baseCodeExtend = 0;
		int extraCal = 0;// 特殊额外计算
		if (firstFeeType.equals("01")) {

			packageCode = Integer.parseInt(basePackageCode);
			baseCode = Integer.parseInt(baseDiscntCode);
			packageCode -= 2;
			baseCode += 20000;

		} else if (firstFeeType.equals("02")) {

			packageCode = Integer.parseInt(basePackageCode);
			baseCode = Integer.parseInt(baseDiscntCode);

		} else if (firstFeeType.equals("03")) {

			packageCode = Integer.parseInt(basePackageCode);
			packageCode -= 1;
			baseCode = Integer.parseInt(baseDiscntCode);
			baseCode += 10000;

		}

		if ("99999828".equals(proId)) {// 136需要额外增加1000
			extraCal = 1000;
		} else if ("99999826".equals(proId)) {// 196套餐需要额外扣减1000
			extraCal = -1000;
		}

		Date now = new Date();

		String ext = "\"TF_B_TRADE_DISCNT\": {\"ITEM\": [ {\"ID\": \"\",\"ID_TYPE\": \"1\", \"PRODUCT_ID\": \""
				+ proId
				+ "\",\"PACKAGE_ID\": \""
				+ packageCode
				+ "\", \"DISCNT_CODE\": \""
				+ baseCode
				+ "\",\"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \""
				+ sdf.format(now)
				+ "\",\"END_DATE\": \"2050-01-01 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },"
				+ " {\"ID\": \"\",\"ID_TYPE\": \"1\", \"PRODUCT_ID\": \""
				+ proId
				+ "\",\"PACKAGE_ID\": \""
				+ packageCode
				+ "\",\"DISCNT_CODE\": \""
				+ (baseCode + 30000 + baseCodeExtend + extraCal)
				+ "\",\"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \""
				+ sdf.format(now)
				+ "\",\"END_DATE\": \"2050-01-01 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\", \"X_DATATYPE\": \"NULL\" },"
				+ " {\"ID\": \"\",\"ID_TYPE\": \"1\", \"PRODUCT_ID\": \""
				+ proId
				+ "\",\"PACKAGE_ID\": \""
				+ packageCode
				+ "\",\"DISCNT_CODE\": \""
				+ (baseCode + 60000)
				+ "\",\"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \""
				+ sdf.format(now)
				+ "\",\"END_DATE\": \"2050-01-01 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\", \"X_DATATYPE\": \"NULL\" },"
				+ " {\"ID\": \"\",\"ID_TYPE\": \"1\", \"PRODUCT_ID\": \""
				+ proId
				+ "\",\"PACKAGE_ID\": \"59999768\",\"DISCNT_CODE\": \"5990170\",\"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \""
				+ sdf.format(now)
				+ "\",\"END_DATE\": \"2050-12-30 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\", \"X_DATATYPE\": \"NULL\" },"
				+

				"";
		String mExt = "";
		String messageId = ""; // DISCNT_CODE

		// 默认要包含套餐“基本套餐短彩信资费0.1元/条”
		mExt = "{\"ID\": \"\",\"ID_TYPE\": \"1\",\"PRODUCT_ID\": \""
				+ proId
				+ "\",\"PACKAGE_ID\": \"59999767\", \"DISCNT_CODE\": \"5690000\", \"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \""
				+ sdf.format(now)
				+ "\",\"END_DATE\": \"2050-12-30 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\"},";

		if ("5561000".equals(messageCode)) { // 10元包200条

			messageId = "5901000";

		} else if ("5562000".equals(messageCode)) { // 20元包400条

			messageId = "5902000";

		} else if ("5563000".equals(messageCode)) { // 30元包600条

			messageId = "5903000";

		}

		String tempExt = "";
		if (StringUtils.isNotBlank(messageId)) {
			tempExt = "{\"ID\": \"\",\"ID_TYPE\": \"1\",\"PRODUCT_ID\": \""
					+ proId
					+ "\",\"PACKAGE_ID\": \"59999767\", \"DISCNT_CODE\": \""
					+ messageId
					+ "\", \"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \""
					+ sdf.format(now)
					+ "\",\"END_DATE\": \"2050-12-30 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\"},";
		}

		mExt = tempExt + mExt;
		String messageUpgradeCode = ""; // 短信网龄升级

		String mupExt = getMessageUpgradeExt(proId, messageUpgradeCode);
		String cpExt = getContractPlanExt(activityCode);
		String optExt = getOptionalServiceExt(addServiceCode, proId);

		ext = ext + mExt + optExt + mupExt + cpExt;

		return ext;

	}

	/**
	 * 增值业务可选包(不确定所给的信息)
	 * 
	 * @param addServiceCode
	 * @param proId
	 * @return
	 */
	public String getOptionalServiceExt(List<String> addServiceCode,
			String proId) {

		String ext = "";
		if (addServiceCode != null && !addServiceCode.isEmpty()) {
			if (addServiceCode.contains("5574000")) {

				ext = "{ \"ID\": \"\", \"ID_TYPE\": \"1\",\"PRODUCT_ID\": \""
						+ proId
						+ "\",\"PACKAGE_ID\": \"59999766\",\"DISCNT_CODE\": \"5990150\",\"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \""
						+ sdf.format(new Date())
						+ "\",\"END_DATE\": \"2050-12-30 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\":\"\", \"X_DATATYPE\": \"NULL\"},";

			}
		}
		return ext;
	}

	/**
	 * 短信网龄升级包
	 * 
	 * @param proId
	 * @param messageUpgradeCode
	 * @return
	 */
	public String getMessageUpgradeExt(String proId, String messageUpgradeCode) {

		Date nextMonth = DateUtils.addMonths(new Date(), 1);

		String ext = "{\"ID\": \"\",\"ID_TYPE\": \"1\",\"PRODUCT_ID\": \""
				+ proId
				+ "\",\"PACKAGE_ID\": \"59999764\",\"DISCNT_CODE\": \"5702000\", \"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \""
				+ ymsdf.format(nextMonth)
				+ "-01 00:00:00\",\"END_DATE\": \"2050-12-30 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\", \"X_DATATYPE\": \"NULL\"},";

		return ext;
	}

	/**
	 * 合约计划
	 * 
	 * @param activityCode
	 * @return
	 */
	public String getContractPlanExt(String activityCode) {

		String ext = "";
		String PACKAGE_ID = "";
		String DISCNT_COD = "";

		Date now = new Date();
		String nextMonth = ymsdf.format(DateUtils.addMonths(now, 1))
				+ "-01 00:00:00";
		String nextYearMonth = ymsdf.format(DateUtils.addYears(
				DateUtils.addMonths(now, 1), 1))
				+ "-01 00:00:00";
		Date lastDayNextYearThisMonth = null;
		try {
			lastDayNextYearThisMonth = DateUtils.addDays(
					sdf.parse(nextYearMonth), -1);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		if ("89000100".equals(activityCode)) {

			PACKAGE_ID = "51000106";
			DISCNT_COD = "20000348";

		} else if ("89000101".equals(activityCode)) {

			PACKAGE_ID = "51000107";
			DISCNT_COD = "20000350";

		}

		ext = " {\"ID\": \"\",\"ID_TYPE\": \"1\", \"PRODUCT_ID\": \""
				+ activityCode
				+ "\", \"PACKAGE_ID\": \""
				+ PACKAGE_ID
				+ "\",\"DISCNT_CODE\": \""
				+ DISCNT_COD
				+ "\",\"SPEC_TAG\": \"0\",\"MODIFY_TAG\": \"0\",\"START_DATE\": \""
				+ nextMonth
				+ "\",\"END_DATE\": \""
				+ ymdsdf.format(lastDayNextYearThisMonth)
				+ " 23:59:59\",\"RELATION_TYPE_CODE\": \"\",\"USER_ID_A\": \"-1\", \"ITEM_ID\": \"5114052901257892\", \"X_DATATYPE\": \"NULL\"}]}";

		return ext;
	}

	/**
	 * 产品ext
	 * 
	 * @param proId
	 * @param activityCode
	 * @return
	 */
	public String getProductExt(String proId, String activityCode) {

		Date now = new Date();
		String nextMonth = ymsdf.format(DateUtils.addMonths(now, 1))
				+ "-01 00:00:00";
		String nextYearMonth = ymsdf.format(DateUtils.addYears(
				DateUtils.addMonths(now, 1), 1))
				+ "-01 00:00:00";
		Date lastDayNextYearThisMonth = null;
		try {
			lastDayNextYearThisMonth = DateUtils.addDays(
					sdf.parse(nextYearMonth), -1);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String ext = "\"TF_B_TRADE_PRODUCT\": {\"ITEM\": [ {\"PRODUCT_ID\": \""
				+ proId
				+ "\",\"PRODUCT_MODE\": \"00\",\"START_DATE\": \""
				+ sdf.format(now)
				+ "\",\"END_DATE\": \"2050-12-30 00:00:00\",\"MODIFY_TAG\": \"0\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"BRAND_CODE\": \"4G00\", \"X_DATATYPE\": \"NULL\" },"
				+ "{\"PRODUCT_ID\": \""
				+ activityCode
				+ "\",\"PRODUCT_MODE\": \"50\",\"START_DATE\": \""
				+ nextMonth
				+ "\",\"END_DATE\": \""
				+ ymdsdf.format(lastDayNextYearThisMonth)
				+ " 23:59:59\", \"MODIFY_TAG\": \"0\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"BRAND_CODE\": \"-1  \",\"X_DATATYPE\": \"NULL\"}"
				+ " ]}";
		return ext;

	}

	/**
	 * 默认基本业务功能包
	 * 
	 * @param proId
	 * @return
	 */
	public String getBasicServiceExt(String proId, List<String> addServiceCode) {
		StringBuilder builder = new StringBuilder();

		builder.append("\"TF_B_TRADE_SVC\": { \"ITEM\": [ ");
		builder.append("{\"SERVICE_ID\": \"50004\",\"PRODUCT_ID\": \"").append(
				proId);
		builder.append(
				"\",  \"PACKAGE_ID\": \"59999768\", \"MODIFY_TAG\": \"0\", \"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\", \"USER_ID_A\": \"-1\", \"ITEM_ID\": \"\", \"X_DATATYPE\": \"NULL\"},");
		builder.append("{ \"SERVICE_ID\": \"50000\",\"PRODUCT_ID\": \"")
				.append(proId);
		builder.append(
				"\", \"PACKAGE_ID\": \"59999793\",\"MODIFY_TAG\": \"0\", \"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\"},");
		builder.append("{ \"SERVICE_ID\": \"50003\", \"PRODUCT_ID\": \"")
				.append(proId);
		builder.append(
				"\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
		builder.append("{ \"SERVICE_ID\": \"50001\", \"PRODUCT_ID\": \"")
				.append(proId);
		builder.append(
				"\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
		builder.append("{ \"SERVICE_ID\": \"50010\", \"PRODUCT_ID\": \"")
				.append(proId);
		builder.append(
				"\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
		builder.append("{ \"SERVICE_ID\": \"50014\", \"PRODUCT_ID\": \"")
				.append(proId);
		builder.append(
				"\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
		builder.append("{ \"SERVICE_ID\": \"50100\", \"PRODUCT_ID\": \"")
				.append(proId);
		builder.append(
				"\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
		builder.append("{ \"SERVICE_ID\": \"50103\", \"PRODUCT_ID\": \"")
				.append(proId);
		builder.append(
				"\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
		builder.append("{ \"SERVICE_ID\": \"50300\", \"PRODUCT_ID\": \"")
				.append(proId);
		builder.append(
				"\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
		builder.append("{ \"SERVICE_ID\": \"50006\", \"PRODUCT_ID\": \"")
				.append(proId);
		builder.append(
				"\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
		builder.append("{ \"SERVICE_ID\": \"50007\", \"PRODUCT_ID\": \"")
				.append(proId);
		builder.append(
				"\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
		builder.append("{ \"SERVICE_ID\": \"50019\", \"PRODUCT_ID\": \"")
				.append(proId);
		builder.append(
				"\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
		builder.append("{ \"SERVICE_ID\": \"50020\", \"PRODUCT_ID\": \"")
				.append(proId);
		builder.append(
				"\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
		builder.append("{ \"SERVICE_ID\": \"50021\", \"PRODUCT_ID\": \"")
				.append(proId);
		builder.append(
				"\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" },");
		builder.append("{ \"SERVICE_ID\": \"50022\", \"PRODUCT_ID\": \"")
				.append(proId);
		builder.append(
				"\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"")
				.append(sdf.format(new Date()));
		builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" }");
		if (addServiceCode == null || addServiceCode.isEmpty()) {
			builder.append("] }");
		} else if (addServiceCode.contains("5574000")||addServiceCode.contains("5572000")) {
			builder.append(",{ \"SERVICE_ID\": \"50201\", \"PRODUCT_ID\": \"")
					.append(proId);
			builder.append(
					"\",\"PACKAGE_ID\": \"59999793\", \"MODIFY_TAG\": \"0\",\"START_DATE\": \"")
					.append(sdf.format(new Date()));
			builder.append("\", \"END_DATE\": \"2050-12-31 00:00:00\",\"USER_ID_A\": \"-1\",\"ITEM_ID\": \"\",\"X_DATATYPE\": \"NULL\" }");
			builder.append("] }");
		} else {
			builder.append("] }");
		}

		return builder.toString();
	}

	/**
	 * itemId可变不确定
	 * 
	 * @return
	 */
	public String getSubItemExt() {

		Date now = new Date();
		String nextYearMonth = ymsdf.format(DateUtils.addYears(
				DateUtils.addMonths(now, 1), 1))
				+ "-01 00:00:00";
		Date lastDayNextYearThisMonth = null;
		try {
			lastDayNextYearThisMonth = DateUtils.addDays(
					sdf.parse(nextYearMonth), -1);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return "\"TF_B_TRADE_SUB_ITEM\": {\"ITEM\": [ { \"ATTR_TYPE_CODE\": \"3\",\"ITEM_ID\": \"5114052901257892\", \"ATTR_CODE\": \"tradeId\", \"ATTR_VALUE\": \"5114052934748554\",\"START_DATE\": \""
				+ sdf.format(now)
				+ "\",\"END_DATE\": \""
				+ ymdsdf.format(lastDayNextYearThisMonth) + " 23:59:59\" }]}";

	}

	public String getOptionSpExt(List<String> addServiceCode, String proId) {

		String ext = null;
		if (addServiceCode.contains("5574000")) {

			ext = " \"TF_B_TRADE_SP\": { \"ITEM\": [ {\"SP_ID\": \"82000011\",\"PRODUCT_ID\": \""
					+ proId
					+ "\",\"PACKAGE_ID\": \"59999766\", \"MODIFY_TAG\": \"0\",\"SP_SERVICE_ID\": \"99104953\",\"ITEM_ID\": \"\",\"FIRST_BUY_TIME\": \""
					+ sdf.format(new Date())
					+ "\", \"START_DATE\": \""
					+ sdf.format(new Date())
					+ "\", \"END_DATE\": \"2050-12-30 00:00:00\",\"PARTY_ID\": \"90213\",\"SP_PRODUCT_ID\": \"8200001100\",\"X_DATATYPE\": \"NULL\"},"
					+ "{\"SP_ID\": \"70000001\",\"PRODUCT_ID\": \""
					+ proId
					+ "\",\"PACKAGE_ID\": \"59999766\", \"MODIFY_TAG\": \"0\",\"SP_SERVICE_ID\": \"99000472\",\"ITEM_ID\": \"\",\"FIRST_BUY_TIME\": \""
					+ sdf.format(new Date())
					+ "\", \"START_DATE\": \""
					+ sdf.format(new Date())
					+ "\", \"END_DATE\": \"2050-12-30 00:00:00\",\"PARTY_ID\": \"90213\",\"SP_PRODUCT_ID\": \"8200001100\",\"X_DATATYPE\": \"NULL\"}]}";

		}else{
		if (addServiceCode.contains("5573000")
				&& addServiceCode.contains("5572000")) {

			ext = " \"TF_B_TRADE_SP\": { \"ITEM\": [ {\"SP_ID\": \"82000011\",\"PRODUCT_ID\": \""
					+ proId
					+ "\",\"PACKAGE_ID\": \"59999766\", \"MODIFY_TAG\": \"0\",\"SP_SERVICE_ID\": \"99104953\",\"ITEM_ID\": \"\",\"FIRST_BUY_TIME\": \""
					+ sdf.format(new Date())
					+ "\", \"START_DATE\": \""
					+ sdf.format(new Date())
					+ "\", \"END_DATE\": \"2050-12-30 00:00:00\",\"PARTY_ID\": \"90213\",\"SP_PRODUCT_ID\": \"8200001100\",\"X_DATATYPE\": \"NULL\"},"
					+ "{\"SP_ID\": \"70000001\",\"PRODUCT_ID\": \""
					+ proId
					+ "\",\"PACKAGE_ID\": \"59999766\", \"MODIFY_TAG\": \"0\",\"SP_SERVICE_ID\": \"99000472\",\"ITEM_ID\": \"\",\"FIRST_BUY_TIME\": \""
					+ sdf.format(new Date())
					+ "\", \"START_DATE\": \""
					+ sdf.format(new Date())
					+ "\", \"END_DATE\": \"2050-12-30 00:00:00\",\"PARTY_ID\": \"90213\",\"SP_PRODUCT_ID\": \"8200001100\",\"X_DATATYPE\": \"NULL\"}]}";
		} else if (addServiceCode.contains("5573000")
				&& !addServiceCode.contains("5572000")) {
		
			ext = " \"TF_B_TRADE_SP\": { \"ITEM\": [ {\"SP_ID\": \"82000011\",\"PRODUCT_ID\": \""
					+ proId
					+ "\",\"PACKAGE_ID\": \"59999766\", \"MODIFY_TAG\": \"0\",\"SP_SERVICE_ID\": \"99104953\",\"ITEM_ID\": \"\",\"FIRST_BUY_TIME\": \""
					+ sdf.format(new Date())
					+ "\", \"START_DATE\": \""
					+ sdf.format(new Date())
					+ "\", \"END_DATE\": \"2050-12-30 00:00:00\",\"PARTY_ID\": \"90213\",\"SP_PRODUCT_ID\": \"8200001100\",\"X_DATATYPE\": \"NULL\"}]}";

		} else if (!addServiceCode.contains("5573000")
				&& addServiceCode.contains("5572000")) {
			
			ext = " \"TF_B_TRADE_SP\": { \"ITEM\": ["
					+ " {\"SP_ID\": \"70000001\",\"PRODUCT_ID\": \""
					+ proId
					+ "\",\"PACKAGE_ID\": \"59999766\", \"MODIFY_TAG\": \"0\",\"SP_SERVICE_ID\": \"99000472\",\"ITEM_ID\": \"\",\"FIRST_BUY_TIME\": \""
					+ sdf.format(new Date())
					+ "\", \"START_DATE\": \""
					+ sdf.format(new Date())
					+ "\", \"END_DATE\": \"2050-12-30 00:00:00\",\"PARTY_ID\": \"90213\",\"SP_PRODUCT_ID\": \"8200001100\",\"X_DATATYPE\": \"NULL\"}]}";

		 }
		}
		return ext;
	}

	public String getProductTypeExt(String proId, String activityCode) {

		Date now = new Date();
		String nextMonth = ymsdf.format(DateUtils.addMonths(now, 1))
				+ "-01 00:00:00";
		String nextYearMonth = ymsdf.format(DateUtils.addYears(
				DateUtils.addMonths(now, 1), 1))
				+ "-01 00:00:00";
		Date lastDayNextYearThisMonth = null;
		try {
			lastDayNextYearThisMonth = DateUtils.addDays(
					sdf.parse(nextYearMonth), -1);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String ext = "\"TF_B_TRADE_PRODUCT_TYPE\": {\"ITEM\": [{\"PRODUCT_ID\": \""
				+ proId
				+ "\", \"PRODUCT_MODE\": \"00\",\"START_DATE\": \""
				+ sdf.format(now)
				+ "\",\"END_DATE\": \"2050-12-30 00:00:00\",\"MODIFY_TAG\": \"0\",\"X_DATATYPE\": \"NULL\",\"PRODUCT_TYPE_CODE\": \"4G000001\" },"
				+ "{ \"PRODUCT_ID\": \""
				+ activityCode
				+ "\",\"PRODUCT_MODE\": \"50\",\"START_DATE\": \""
				+ nextMonth
				+ "\",\"END_DATE\": \""
				+ ymdsdf.format(lastDayNextYearThisMonth)
				+ " 23:59:59\",\"MODIFY_TAG\": \"0\",\"X_DATATYPE\": \"NULL\", \"PRODUCT_TYPE_CODE\": \"CFSF001\"}] }";

		return ext;
	}

	public static void main(String[] args) {

		// logger.info(new
		// ExtGeneration().getBasicPackageExt("99999823","89000100", "5608000",
		// "59999771", "01",null));

		ParameterData data = new ParameterData();
		CustomBill bill = new CustomBill();
		bill.setSelectedProductId("99999830");
		bill.setActivityCode("89000100");
		bill.setFirstFeeType("02");
		List<String> addServiceCode = new ArrayList<String>();
		addServiceCode.add("5574000");
		addServiceCode.add("5573000");
		addServiceCode.add("5572000");
		
		bill.setMessageCode("5561000");
	//	bill.setAddServiceCode(addServiceCode);
		data.setBill(bill);
		logger.info(new ExtGeneration().getExt(data));

	}

}
