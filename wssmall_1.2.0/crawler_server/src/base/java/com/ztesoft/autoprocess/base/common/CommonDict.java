package com.ztesoft.autoprocess.base.common;

/*import util.DictUtil;

import com.help.ConfigConstants;
import com.sunrise.jop.infrastructure.db.DBAccessUser;*/

/**
 * 公共字典类
 * 
 * @author tanghaoyang
 * 
 */
public class CommonDict {/*
	*//**日志记录器*//*
	private static final Log log =LogFactory.getLog(CommonDict.class);
	*//**配置文件*//*
	private static final String CONFIG_FILE="auto-config.properties";	
	*//** 全局测试标识 *//*
	public static boolean IS_TEST = true;
	*//**进行订单分配的工号*//*
	public static String ALLOCATE_OPER_CODE;
	*//**专门处理裸机和配件订单的工号*//*
	public static String LUOJI_AND_PEIJIAN_OPER_CODE;
	*//**查询待分配订单的URL*//*
	public static String URL_DAI_FEN_PEI;
	
	*//** 默认db用户 *//*
	private static DBAccessUser user;
	
	static {
		//读取参数
		try {
			init();
		} catch (Exception e) {
			log.info("读取配置文件出错",e);
		}
		
		user = new DBAccessUser();
		user.setCityid("DB_COMMON");
		user.setOprcode("root");
		user.setIp("127.0.0.1");
	}

	public static void main(String[] args) throws InterruptedException {
		isTestOrders("sf");

		for(int i=0;i<100;i++){
	    int j=CommonDict.getRandomValue(1000);
			Thread.sleep(j);
			logger.info(j);
		}
	}
	
	*//**
	 * 打开一个文件并返回该文件的内容
	 * @param szFileName
	 * @return
	 *//*
	public static String openFile( String szFileName ) {
		BufferedReader bis=null;
		try {
           bis= new BufferedReader(new InputStreamReader(new FileInputStream( new File(szFileName)), "UTF-8") );
           String szContent="";
           String szTemp;
           
           while ( (szTemp = bis.readLine()) != null) {
               szContent+=szTemp+"\n";
           }
           return szContent;
       }
       catch( Exception e ) {
           return "";
       }finally{
    	   if(bis!=null)
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
       }
	} 
	
	*//**
	 * 从配置文件读取参数
	 * @throws Exception 
	 *//*
	public static boolean init() throws Exception {
		InputStream in = null;
		in = ConfigConstants.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
		
		if(null==in){
			throw new Exception("没有读取到指定的配置文件："+CONFIG_FILE);
		}
		
		try {
			if (in != null) {
				Properties properties = new Properties();
				properties.load(in);

				//进行订单分配的工号
				ALLOCATE_OPER_CODE=properties.getProperty("ALLOCATE_OPER_CODE","未配置");

				//专门处理裸机和配件订单的工号
				LUOJI_AND_PEIJIAN_OPER_CODE=properties.getProperty("LUOJI_AND_PEIJIAN_OPER_CODE","未配置");

				//是否测试环节
				IS_TEST=Boolean.parseBoolean(properties.getProperty("IS_TEST", "true"));
				
				//待分配订单查询页面
				URL_DAI_FEN_PEI=properties.getProperty("URL_DAI_FEN_PEI");

			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	*//**
	 * 判断是否测试单，每次从配置文件读取一下
	 *//*
	public static boolean isTestOrders(String orderId) {
		InputStream in = null;
		try {
			in = ConfigConstants.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
			if (in != null) {
				Properties properties = new Properties();
				properties.load(in);
				String ordersId = properties.getProperty("ordersId");
				if (null != ordersId) {
					String[] orders = ordersId.split(",");
					if (orders != null && ordersId.length() > 0) {
						for (String id : orders) {
							if(orderId.equals(id)){
								return true;
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	*//**
	 * 得到测试单号
	 * @return
	 *//*
	public static String getTestOrderId() {
		InputStream in = null;
		try {
			in = ConfigConstants.class.getClassLoader().getResourceAsStream(CONFIG_FILE);
			if (in != null) {
				Properties properties = new Properties();
				properties.load(in);
				String ordersId = properties.getProperty("ordersId");
				if (null != ordersId) {
					String[] orders = ordersId.split(",");
					if (orders != null && ordersId.length() > 0) {
						for (String id : orders) {
							return id;
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}	

	*//**
	 * 返回指定0至指定参数值之间的随机数
	 * @param seed 最大值
	 * @return
	 *//*
	public static int getRandomValue(int seed){
		return (int)(Math.random()*seed);
	}
	
	*//**
	 * 将地市名转化成订单系统地市编码
	 * 
	 * @param city
	 *            地市名称
	 * @return 订单系统地市编码
	 * @throws Exception
	 *//*
	public static String parseOrderRegionCode(String city) throws Exception {
		return DictUtil.getCodeByName("DIC_ZONE_CD", city, null, user);
	}

	*//**
	 * 将总部订单来源转换成总部订单来源编码
	 * 
	 * @param orderOrigin
	 *            总部订单来源
	 * @return 总部订单来源编码
	 * @throws Exception
	 *//*
	public static String parseZBOrderOriginCode(String orderOrigin)
			throws Exception {
		return DictUtil.getCodeByName("DIC_ZB_ORDER_ORIGIN", orderOrigin, null,
				user);
	}

	*//**
	 * 将总部订单归属地市转换成总部地市编码
	 * 
	 * @param orderRegion
	 *            归属地市
	 * @return 总部地市编码
	 * @throws Exception
	 *//*
	public static String parseZBOrderRegionCode(String orderRegion)
			throws Exception {
		return DictUtil.getCodeByName("DIC_ZB_ORDER_REGION", orderRegion, null,
				user);
	}

	*//**
	 * 将总部订单类型转换成总部订单类型编码
	 * 
	 * @param orderType
	 *            总部订单类型
	 * @return 总部订单类型编码
	 * @throws Exception
	 *//*
	public static String parseZBOrderTypeCode(String orderType)
			throws Exception {
		return DictUtil.getCodeByName("DIC_ZB_ORDER_TYPE", orderType, null,
				user);
	}

	*//**
	 * 将总部配送方式转换成总部配送方式编码
	 * 
	 * @param carryMode
	 *            配送方式
	 * @return 总部配送方式编码
	 * @throws Exception
	 *//*
	public static String parseZBOrderCarryCode(String carryMode)
			throws Exception {
		return DictUtil.getCodeByName("DIC_ZB_CARRY_MODE", carryMode, null,
				user);
	}
*/
	
	/**
	 * 返回指定0至指定参数值之间的随机数
	 * @param seed 最大值
	 * @return
	 */
	public static int getRandomValue(int seed){
		return (int)(Math.random()*seed);
	}
}
