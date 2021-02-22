package com.ztesoft.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.ztesoft.net.framework.util.StringUtil;

/**
 * 
 * @Package com.ztesoft.common.util
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author zhouqiangang 
 * @date 2015年12月2日 下午2:38:33
 */
public class SequenceTools {
	
	/**
	 * Service标识
	 */
	private static String serviceId = "00" ;
	
	/**
	 * 主键自增序列
	 */
	private static AtomicLong short18PrimaryKeyAtomicLong = new AtomicLong();
	
	private static AtomicLong short20PrimaryKeyAtomicLong = new AtomicLong();
	
	private static AtomicLong defualt22PrimaryKeyAtomicLong = new AtomicLong();
	
	/**
	 * 随机数长度最大值
	 */
	private static java.util.Map<Integer,Long> maxRandomMap = new java.util.HashMap<Integer,Long>();
	
	/**
	 * 初始化Service标识
	 */
	static{
		String tempServiceId = System.getProperty("serviceid");
		if (!StringUtil.isEmpty(tempServiceId)) {
			SequenceTools.serviceId = tempServiceId;
		}
	}
	
	
	
	/**
	 * 生成随机数  时间格式+随机数
	 * @param dateFormatType 时间格式
	 * @param randomNum 随机数个数
	 * @return
	 */
	public static String getDateRandom(String dateFormatType, int randomNum) {//365*24
		StringBuilder randomBuffer = new StringBuilder();
		randomBuffer.append(new SimpleDateFormat(dateFormatType).format(new Date(System.currentTimeMillis())));
		randomBuffer.append(getServer_id());randomNum+=2;
		while (randomBuffer.length() - dateFormatType.length() <= randomNum) {
			randomBuffer.append(Math.round(Math.random() * 99));
		}
		return randomBuffer.toString().substring(0, dateFormatType.length() + randomNum);
	}
	
	public static String getServer_id(){
		String serviceid="00";
		if(!StringUtil.isEmpty(System.getProperty("serviceid"))){
			serviceid=System.getProperty("serviceid");
		}
		return serviceid;
	}
	
	
    /**
     * add by wui
	 * 生成随机数  时间格式+随机数
	 * @param dateFormatType 时间格式
	 * @param randomNum 随机数个数
	 * @return
	 */
    static AtomicInteger icInt = new AtomicInteger();
	public static String getFlowDateRandom(String dateFormatType, int randomNum) {//365*24
		StringBuilder randomBuffer = new StringBuilder();
		randomBuffer.append(new SimpleDateFormat(dateFormatType).format(new Date(System.currentTimeMillis())));
		String serv_id =getFlowServer_id();
		randomBuffer.append(serv_id);
//		int currValue =icInt.incrementAndGet();
		int prandomNum = randomNum;
		if(serv_id.length() ==1)//配置一位，则多生成一位随机数
			prandomNum=randomNum+1;
		icInt.compareAndSet(getMaxValue(prandomNum), 0);
		int curValue =icInt.incrementAndGet();
		String curStrValue =curValue+"";
		int leftValueLen = prandomNum-curStrValue.length();
		//补0
		String leftValue ="";
		for (int i = 0; i <leftValueLen; i++) {
			leftValue+="0";
		}
		randomBuffer.append(leftValue+curStrValue);
		return randomBuffer.toString();
	}
	
	/**
     * add by wui
	 * 生成随机数  时间格式+随机数
	 * @param dateFormatType 时间格式
	 * @param randomNum 随机数个数
	 * @return
	 */
    static AtomicInteger ordIcInt = new AtomicInteger();
	public static String getOrderDateRandom(String dateFormatType, int randomNum) {//365*24
		StringBuilder randomBuffer = new StringBuilder();
		randomBuffer.append(new SimpleDateFormat(dateFormatType).format(new Date(System.currentTimeMillis())));
		int prandomNum = randomNum;
//		String serv_id = getServer_id();
//		randomBuffer.append(serv_id);
//		int currValue =icInt.incrementAndGet();
		
//		if(serv_id.length() ==1)//配置一位，则多生成一位随机数
//			prandomNum=randomNum+1;
		ordIcInt.compareAndSet(getMaxValue(prandomNum), 0);
		int curValue =ordIcInt.incrementAndGet();
		String curStrValue =curValue+"";
		int leftValueLen = prandomNum-curStrValue.length();
		//补0
		String leftValue ="";
		for (int i = 0; i <leftValueLen; i++) {
			leftValue+="0";
		}
		randomBuffer.append(leftValue+curStrValue);
		return randomBuffer.toString();
	}
		
	public static int getMaxValue(int number){
		String strValue ="";
		for (int i = 0; i < number; i++) {
			strValue=strValue+"9";
		}
		return (new Integer(strValue).intValue()-10);
	}
	public static String getFlowServer_id(){
		String serviceid="0";
		if(!StringUtil.isEmpty(System.getProperty("serviceid"))){
			serviceid=System.getProperty("serviceid");
		}
		return serviceid;
	}
	
//   public static void main(String[] args) {
//    	String seq  =SequenceTools.getDateRandom(com.ztesoft.ibss.common.util.DateUtil.DATE_FORMAT_7,2).substring(2);
//		String mmStr=seq.substring(2,4);
//		String ddStr=seq.substring(4,6);
//		String day = (new Integer(mmStr)*new Integer(ddStr))+"";
//		seq = seq.substring(0,2)+day+seq.substring(6);
//     	long lastValue  =new Long(seq).longValue();
//     	System.out.println(lastValue);
//	}
	
	
	/**
	 * 
	 * @Description: 主键生成 	注意:计算主键总长度为[2位年份]+[2为Service编码]+[3位当天在一年的天数]+[6位时分秒HHmmss]+[5位随机数]
	 * @param randomNum   随机数长度,不包含Service编码
	 * @return   
	 * @author zhouqiangang
	 * @date 2015年11月11日 上午9:56:14
	 */
	public static String getShort18PrimaryKey(){
		Calendar calendar =Calendar.getInstance();
		StringBuilder randomBuffer = new StringBuilder();
		// 增加时间[2位年]
		randomBuffer.append(calendar.get(Calendar.YEAR)-2000);
		//增加Service标识
		randomBuffer.append(SequenceTools.serviceId);
		// 增加 [当天在一年的天数]+[HHmmss]
		randomBuffer.append(String.format("%03d",calendar.get(Calendar.DAY_OF_YEAR))).append(new SimpleDateFormat("HHmmss").format(calendar.getTime()));
		// 如果当前主键自增序列等于随机数长度最大值.则把数字设置为0
		SequenceTools.short18PrimaryKeyAtomicLong.compareAndSet(getMaxRandom(5), 0);
		// 获取当前序列值
		long currentSeqValue = SequenceTools.short18PrimaryKeyAtomicLong.incrementAndGet();
		// 补0操作 (%固定写法,0为补充什么值,randomLength补多少位,d为参数是正数型 )
		randomBuffer.append(String.format(new StringBuffer().append("%0").append(5).append("d").toString(), currentSeqValue));

		return randomBuffer.toString();
	}
	
	/**
	 * 
	 * @Description: 主键生成 	注意:计算主键总长度为[2位年份]+[2为Service编码]+[10位月日时分秒MMddHHmmss]+[6位随机数]
	 * @param randomNum   随机数长度,不包含Service编码
	 * @return   
	 * @author zhouqiangang
	 * @date 2015年11月11日 上午9:56:14
	 */
	public static String getShort20PrimaryKey(){
		Calendar calendar =Calendar.getInstance();
		StringBuilder randomBuffer = new StringBuilder();
		// 增加时间[2位年]
		randomBuffer.append(calendar.get(Calendar.YEAR)-2000);
		//增加Service标识
		randomBuffer.append(SequenceTools.serviceId);
		// 增加时间[年]+[当天在一年的天数]+[HHmmss]
		randomBuffer.append(new SimpleDateFormat("MMddHHmmss").format(calendar.getTime()));
		// 如果当前主键自增序列等于随机数长度最大值.则把数字设置为0
		SequenceTools.short20PrimaryKeyAtomicLong.compareAndSet(getMaxRandom(6), 0);
		// 获取当前序列值
		long currentSeqValue = SequenceTools.short20PrimaryKeyAtomicLong.incrementAndGet();
		// 补0操作 (%固定写法,0为补充什么值,randomLength补多少位,d为参数是正数型 )
		randomBuffer.append(String.format(new StringBuffer().append("%0").append(6).append("d").toString(), currentSeqValue));

		return randomBuffer.toString();
	}
	
	/**
	 * 
	 * @Description: 主键生成 	注意:计算主键总长度为[2位年份]+[2为Service编码]+[13位月日时分秒毫秒MMddHHmmssSSS]+[5位随机数]
	 * @param dateFormatType   日期格式化类型
	 * @param randomNum	       随机数长度,不包含Service编码
	 * @return   
	 * @author zhouqiangang
	 * @date 2015年11月10日 下午4:33:20
	 */
	public static String getdefualt22PrimaryKey() {
		StringBuilder randomBuffer = new StringBuilder();
		Calendar calendar =Calendar.getInstance();
		// 增加时间[2位年]
		randomBuffer.append(calendar.get(Calendar.YEAR)-2000);
		// 增加Service标识
		randomBuffer.append(SequenceTools.serviceId);
		// 增加时间
		randomBuffer.append(new SimpleDateFormat("MMddHHmmssSSS").format(calendar.getTime()));
		// 如果当前主键自增序列等于随机数长度最大值.则把数字设置为0
		SequenceTools.defualt22PrimaryKeyAtomicLong.compareAndSet(getMaxRandom(5), 0);
		// 获取当前序列值
		long currentSeqValue = SequenceTools.defualt22PrimaryKeyAtomicLong.incrementAndGet();
		// 补0操作 (%固定写法,0为补充什么值,randomLength补多少位,d为参数是正数型 )
		randomBuffer.append(String.format(new StringBuffer().append("%0").append(5).append("d").toString(), currentSeqValue));

		return randomBuffer.toString();
	}
	
	
	/**
	 * 
	 * @Description: 默认标识生成  格式:  [2位年份]+[2为Service编码]+[13位月日时分秒毫秒MMddHHmmssSSS]+[5位随机数]
	 * @return   
	 * @author zhouqiangang
	 * @date 2015年11月10日 下午4:33:00
	 */
	public static String getDefaultPrimaryKey(){
		return getdefualt22PrimaryKey();
	}
	
	
	/**
	 * 
	 * @Description: 订单标识生成   格式:[2位年份]+[2为Service编码]+[13位月日时分秒毫秒MMddHHmmssSSS]+[5位随机数]
	 * @return   
	 * @author zhouqiangang
	 * @date 2015年11月10日 下午4:32:17
	 */
	public static String getOrderPrimaryKey(){
		return getdefualt22PrimaryKey();
	}
   
	/**
	 * 
	 * @Description: 获取随机数长度最大值
	 * @param randomLength  随机数长度
	 * @return   
	 * @author zhouqiangang
	 * @date 2015年11月10日 下午4:31:55
	 */
	public static synchronized long getMaxRandom(int randomLength){
		Long maxRandom = SequenceTools.maxRandomMap.get(randomLength);
		if(null == maxRandom || String.valueOf(maxRandom).length() != randomLength){
			StringBuffer strBuffer = new StringBuffer();
			for (int i = 0; i < randomLength; i++) {
				strBuffer.append("9");
			}
			maxRandom = new Long(strBuffer.toString());
			SequenceTools.maxRandomMap.put(randomLength, maxRandom);
		}
		return maxRandom;
	}
	
	
	
	
	public static void main(String[] args) {
		new Thread(){
			@Override
			public void run() {
				int i = 0;
				while(i<100000){
					System.out.println(getdefualt22PrimaryKey());
					i++;
				}
			}
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				int i = 0;
				while(i<100000){
					System.out.println(getdefualt22PrimaryKey());
					i++;
				}
			}
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				int i = 0;
				while(i<100000){
					System.out.println(getdefualt22PrimaryKey());
					i++;
				}
			}
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				int i = 0;
				while(i<100000){
					System.out.println(getdefualt22PrimaryKey());
					i++;
				}
			}
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				int i = 0;
				while(i<100000){
					System.out.println(getdefualt22PrimaryKey());
					i++;
				}
			}
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				int i = 0;
				while(i<100000){
					System.out.println(getdefualt22PrimaryKey());
					i++;
				}
			}
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				int i = 0;
				while(i<100000){
					System.out.println(getdefualt22PrimaryKey());
					i++;
				}
			}
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				int i = 0;
				while(i<100000){
					System.out.println(getdefualt22PrimaryKey());
					i++;
				}
			}
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				int i = 0;
				while(i<100000){
					System.out.println(getdefualt22PrimaryKey());
					i++;
				}
			}
		}.start();
		
		new Thread(){
			@Override
			public void run() {
				int i = 0;
				while(i<100000){
					System.out.println(getdefualt22PrimaryKey());
					i++;
				}
			}
		}.start();
		
	
		
//		for(int i =0 ; i<1000; i++){
//			
//		}
	}
	
	
}
