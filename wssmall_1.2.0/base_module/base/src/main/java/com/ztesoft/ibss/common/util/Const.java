package com.ztesoft.ibss.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.powerise.ibss.util.DBTUtil;
import com.powerise.ibss.util.SysSet;
import com.ztesoft.common.util.PageModel;

public class Const {
	
	public final static String SYSDATE = DBTUtil.current(); //设置时间为系统时间
	/*
	 * 规则审核状态
	 * 00N 新建
		00X 无效
		00A 有效
		00M 审核中
	 */
	public static final String RULE_STATUS_00N = "00N";
	public static final String RULE_STATUS_00X = "00X";
	public static final String RULE_STATUS_00A = "00A";
	public static final String RULE_STATUS_00M = "00M";
	
	/*分额控制类型 NO “无份额控制“，
	MO “按金额控制”  ，
	PN “按产品数量控制 'CO'按地市控制
	不同份额控制方式，处理逻辑有区别*/
	public static final String CONTROL_NO = "NO";
	public static final String CONTROL_MO = "MO";
	public static final String CONTROL_PN = "PN";
	public static final String CONTROL_CO = "CO";
	
	public static final int USER_AUDIT_STATUS_NOT = -1;//不通过
	public static final int USER_AUDIT_STATUS_0 = 0;//未审核
	public static final int USER_AUDIT_STATUS_1 = 1;//通过
	
	//类型：0按月 1按周 2按天(暂时只做按月的规则)
	public static final int MEMBER_RULE_KIND_0 = 0;
	public static final int MEMBER_RULE_KIND_1 = 1;
	public static final int MEMBER_RULE_KIND_2 = 2;
	
	/**
	 * 供货商管理员
	 */
	public static final String ADMIN_RELTYPE_SUPPER = "SUPPER";
	/**
	 * 供货商员工
	 */
	public static final String ADMIN_RELTYPE_SUPPER_STAFF = "SUPPER_STAFF";
	/**
	 * 电信部门
	 */
	public static final String ADMIN_CHINA_TELECOM_SUPPLIER_ID = "-1";
	
	/**
	 * 普通会员
	 */
	public static final String MEMBER_LV_COMMON = "0";
	/**
	 * 经销商
	 */
	public static final String MEMBER_LV_SUPPLIER = "2";
	/**
	 * 电信部门
	 */
	public static final String MEMBER_LV_CHINA_TELECOM_DEP = "3";
	/**
	 * 电信员工
	 */
	public static final String MEMBER_LV_CHINA_TELECOM_STAFF = "1";
	
	public static final String SESSION_MEMBER_LV = "SESSION_MEMBER_LV";
	public static final String COMMONAGE_LV = MEMBER_LV_COMMON;
	
	public static final String ACTION_METHOD = "execMethod" ;
	
	public static final String ACTION_RESULT = "RESULT" ;

	public static final String ACTION_PARAMETER = "PARAMETER" ;
	
	public static final String ACTION_ERROR = "ERROR" ;
	
	
	public static final String PAGE_PAGESIZE = "page_size" ;
	public static final String PAGE_PAGEINDEX = "page_index" ;
	public static final String PAGE_PAGECOUNT = "page_count" ;
	public static final String PAGE_TOTALCOUNT = "total_count" ;
	public static final String PAGE_DATALIST = "data_list" ;
	public static final String PAGE_MODEL = "PAGE_MODEL" ;
	
	public static final String PAGE_MAX_SIZE = "100" ;//每页最大数量
	public static final int UN_JUDGE_ERROR = -999999999 ;  //这种异常就跳过，不需要处理。
	
	//系统项目编码如yn,gx等  
	public static String YN_PROJECT_CODE = "yn";
	public static String GX_PROJECT_CODE = "gx";
	public static String GZ_PROJECT_CODE = "gz";
	public static String HA_PROJECT_CODE = "ha";
	public static String JX_PROJECT_CODE = "jx";
	
	//错误级别，弹出到页面上：10000表示提示信息，9999表示异常信息
	public static final int ALERT_LEVEL = 10000;
	public static final int ERROR_LEVEL = 9999;
	
	//订单条件类型
	public static String ORDER_ADJUST_PRICE_SHIP = "ship";
	
	public static String ORDER_HIS_TIME = "order_his_time";
	
	/**
	 * 系统省份编码
	 * @return
	 */
	public static String getProjectCode(){
		return getSystemInfo("PROVINCE_CODE");
	}
	/**
	 * 得到系统配置文件ibss.xml中system节点中配置的系统信息
	 * @param str
	 * @return
	 */
	public static String getSystemInfo(String str){
		try {
			return SysSet.getSystemSet(str);
		} catch (FrameException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static Map getParam(DynamicDict dto){
		try {
			return (Map)dto.getValueByName(ACTION_PARAMETER)  ;
		} catch (FrameException e) {
			return getEmptyMap() ;
//			e.printStackTrace();
		}
	}
	
	private static Map getEmptyMap(){
		return new HashMap() ;
	}
	
	public static int getPageSize(Map m ){
		return ((Integer)m.get("pageSize")).intValue() ;
	}
	
	public static int getPageIndex(Map m ){
		return ((Integer)m.get("pageIndex")).intValue() ;
	}
	
	/**
	 * 根据tStr字段 构建新map
	 * @param sm  原map
	 * @param tStr 从原map，根据tStr字段作为key，构建需要的map
	 * @return
	 */
	public static Map getMapForTargetStr(Map sm , String tStr){
		if(sm == null || sm.isEmpty() ||
				tStr == null || "".equals(tStr.trim()))
			return getEmptyMap() ; 
		Map tm = new HashMap() ;
		Iterator it = sm.keySet().iterator() ;
		String[] tStrArray = tStr.split(",") ;
		for(int i = 0 , j= tStrArray.length ; i< j ; i++) {
			String n = tStrArray[i];
			if (null != n && sm.get(n.trim()) != null) {
				tm.put(n, sm.get(n));
			}
		}
		return tm ;
	}
	
	public static boolean containValue(Map m , String name ){
		return m.get(name) != null ;
	}
	
	public static  boolean containStrValue(Map m , String name) {
		if( !containValue( m , name ) )
			return false ;
		String t = (String)m.get(name) ;
		return t != null && !"".equals(t.trim()) ;
	}
	
	public static  String getStrValue(Map m , String name) {
		
		if (null == m || m.isEmpty()) return "";
		
		Object t = m.get(name) ;
		if(t == null )
			return "" ;
		return (m.get(name)+"").trim() ;
	}
	
	public static  int getIntValue(Map m , String name) {
		Object t = m.get(name) ;
		if(t == null )
			return 0 ;
		//String ret = ((String)m.get(name)).trim() ;
		String ret = "" + t;
		return Integer.parseInt(ret) ;
	}
	
	public static int getPageIndex(DynamicDict dict){
		String pageIndex = "1";
		try {
			pageIndex = (String) dict.getValueByName(PAGE_PAGEINDEX);
		} catch (FrameException e) {
		}
		return Integer.parseInt(pageIndex);
	}
	public static int getPageSize(DynamicDict dict){
		String pageSize = PAGE_MAX_SIZE;
		try {
			pageSize = (String) dict.getValueByName(PAGE_PAGESIZE);
		} catch (FrameException e) {
		}
		return Integer.parseInt(pageSize);
	}
	public static void setPageModel(DynamicDict dict, PageModel pager) throws FrameException {
		HashMap page = new HashMap();
		page.put(PAGE_TOTALCOUNT, ""+pager.getTotalCount());
		page.put(PAGE_PAGEINDEX,""+pager.getPageIndex());
		page.put(PAGE_PAGESIZE, ""+pager.getPageSize());
		page.put(PAGE_PAGECOUNT, ""+pager.getPageCount());
		page.put(PAGE_DATALIST, pager.getList());
		dict.setValueByName(PAGE_MODEL, page);//返回到页面的HashMap对象
	}
	public static void setPageModel(DynamicDict dict)throws FrameException {
		Object pm = dict.getValueByName(PAGE_MODEL,false);
		if(pm instanceof PageModel){
			setPageModel(dict,(PageModel)pm);
		}else{
			setPageModel(dict, new PageModel());
		}
	}
	
	/**
	 * PageModel对象转Map
	 * @param pm
	 * @return
	 * @throws FrameException
	 */
	public static Map getPM(PageModel pm) throws FrameException {
		Map ret = new HashMap();
		ret.put(PAGE_TOTALCOUNT, "" + pm.getTotalCount());
		ret.put(PAGE_PAGEINDEX, "" + pm.getPageIndex());
		ret.put(PAGE_PAGESIZE, "" + pm.getPageSize());
		ret.put(PAGE_PAGECOUNT, "" + pm.getPageCount());
		ret.put(PAGE_DATALIST, pm.getList());
		return ret;
	}
	
	/**
	 * 一个bean以map的形式展示
	 * @param obj
	 * @return
	 * @throws FrameException
	 */
	public static Map beanToMap(Object obj) throws FrameException {
		try {
			return BeanUtils.describe(obj);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		return null;
	}
	
	/**
	 * 把map的值设给bean，map的key须与bean的属性一一对应。
	 * @param obj
	 * @return
	 * @throws FrameException
	 */
	public static void mapToBean(Object obj, Map param) throws FrameException {
		try {
			BeanUtils.populate(obj, param);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
	}
	
	public static final Map<String,String> POINT_VALUE = new HashMap<String,String>();
	static{
		POINT_VALUE.put("buygoods", "购买商品");
		POINT_VALUE.put("email_check", "邮箱认证");
		POINT_VALUE.put("onlinepay", "在线支付");
		POINT_VALUE.put("comment_img", "带照片评论");
		POINT_VALUE.put("register", "注册会员");
		POINT_VALUE.put("register_link", "通过链接注册");
		POINT_VALUE.put("comment", "文字评论");
		POINT_VALUE.put("login", "登录");
		POINT_VALUE.put("exchange_coupon", "优惠券兑换");
		POINT_VALUE.put("order_pay_get", "订单支付");
		POINT_VALUE.put("operator_adjust", "管理员调整");
	}
	
	/**
	 * 获取积分获得途径的中文
	 * @作者 MoChunrun 
	 * @创建日期 2013-9-5 
	 * @param key
	 * @return
	 */
	public static String getPointCNValue(String key){
		String value = POINT_VALUE.get(key);
		if(value==null){
			return key;
		}else{
			return value;
		}
	}
	
	/**
	 * 返回适合sql in条件的字符串
	 * @param str
	 * @return
	 */
	public static String getInWhereCond(String str){
		if(StringUtils.isEmpty(str))
			return null;
		String[] strs = str.trim().split(",");
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<strs.length;i++){
			sb.append("'"+strs[i]+"'");
			if(i<strs.length-1)
				sb.append(",");
		}
		return sb.toString();
	}
	
	public static String getInWhereCond(String[] strs){
		if(strs==null || strs.length==0)
			return null;
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<strs.length;i++){
			sb.append("'"+strs[i]+"'");
			if(i<strs.length-1)
				sb.append(",");
		}
		return sb.toString();
	}
	
	public static String getInWhereCond(List list){
		if(list==null || list.size()==0)
			return null;
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<list.size();i++){
			sb.append("'"+list.get(i)+"'");
			if(i<list.size()-1)
				sb.append(",");
		}
		return sb.toString();
	}
	
	public static String[] splitStr(String str){
		if(str==null || "".equals(str))
			return new String[0];
		List<String> strList = new ArrayList<String>();
		int begin = str.indexOf(",");
		while(begin!=-1){
			if(begin==0){
				strList.add("");
			}
			else{
				strList.add(str.substring(0, begin));
			}
			str = str.substring(begin+1);
			if(StringUtils.isEmpty(str))
				strList.add("");
			else if(str.indexOf(",")==-1)
				strList.add(str);
			begin = str.indexOf(",");
		}
		String[] strs = new String[strList.size()];
		for(int i=0;i<strList.size();i++){
			strs[i] = strList.get(i);
		}
		return strs;
	}
	
	/**
	 * 订单属性定议表属性类别 order_ext订单扩展属性 order_item_ext子订单扩展属性 order_item_pro_ext 子订单货品扩展属性
	 * order_delivery_ext 物流扩展属性
	 */
	public static final String ATTR_DEF_SPACE_TYPE_ORDER_EXT = "order_ext";
	public static final String ATTR_DEF_SPACE_TYPE_ORDER_PAY_EXT = "order_pay_ext";
	public static final String ATTR_DEF_SPACE_TYPE_ORDER_ITEM_EXT = "order_item_ext";
	public static final String ATTR_DEF_SPACE_TYPE_ORDER_ITEM_PRO_EXT = "order_item_pro_ext";
	public static final String ATTR_DEF_SPACE_TYPE_ORDER_DELIVERY = "order_delivery_ext";
	public static final String COMM_SPACE_ID = "-999";//属性定议公共数据ID
	public static final String COMM_SUB_SPACE_ID = "-1";//属性定义公共子类型type
	//ding.xiaotao 20180523新增
	public static final String TABLE_SPACE_ID = "999";//执行表及属性处理器所需配置
	
	
	
	//订单状态 [新]自动化流程处理使用
	public static final String ORDER_FLOW_STATUS_0 = "0";//待处理
	public static final String ORDER_FLOW_STATUS_1 = "1";//已处理
	public static final String ORDER_FLOW_STATUS_2 = "2";//处理失败
	
	public static final String ORDER_STAND_AUTO_SERVICE_CODE = "ORDERSTANDARDIZE_AUTO";
	public static final String ORDERSTANDARDIZE_CODE = "ORDERSTANDARDIZE";
	public static final String FLOW_DEAL_TYPE_AM = "C";
	public static final String FLOW_DEAL_TYPE_END = "B";
	
	//订单处理日志类型
	public static final String ORDER_HANDLER_TYPE_LOCK = "lock";//锁定
	public static final String ORDER_HANDLER_TYPE_EXCEPTION = "exception";//异常
	public static final String ORDER_HANDLER_TYPE_EXCEPTION_RECOVER = "exception_recover";//异常恢复
	public static final String ORDER_HANDLER_TYPE_SUSPEND = "suspend";//挂起
	public static final String ORDER_HANDLER_TYPE_ENTRUTS = "entrust";//委托
	public static final String ORDER_HANDLER_TYPE_REBUT = "rebut";//驳回
	public static final String ORDER_HANDLER_TYPE_RETURNED = "returned";//退单
	public static final String ORDER_HANDLER_TYPE_REFUND = "refund";//退款
	public static final String ORDER_HANDLER_TYPE_CANCELRETURNED = "cancelreturned"; //取消退单
	public static final String ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE = "DIC_HANDLE_TYPE";//环节处理
	public static final String ORDER_HANDLER_TYPE_RECOVER = "recover";//归档订单恢复
	public static final String ORDER_HANDLER_TYPE_DEFAULT = "DEFAULT";
	
	public static final int CACHE_SPACE = 1;
	
	public static final int WIDGET_CACHE_SPACE = 888;
	
	public static final int CACHE_SPACE_ORDERSTD = 300;//收单标准化用缓存空间
	public static final int CACHE_TIME_ORDERSTD = 60 * 24 * 60 * 5;//收单标准化用缓存时间 5天
	
	public static final String UPDATE_ORDER = "UPDATE_ORDER";//更新订单
	public static final String GET_CARD_INFO = "GET_CARD_INFO";//获取卡信息
	public static final String PRE_COMMIT = "PRE_COMMIT";//预提交
	public static final String PAY_RESULT = "PAY_RESULT";//支付结果
	public static final String WRITE_CARD_RESULT = "WRITE_CARD_RESULT";//写卡结果通知
	public static final String ORDER_ARCHIVE = "ORDER_ARCHIVE";//订单归档
	public static final String WRITE_CARD_SUCCESS = "WRITE_CARD_SUCCESS";//写卡成功
	public static final String UPDATE_DELIVERY="UPDATE_DELIVERY";//物流更新
}
