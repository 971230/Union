package com.ztesoft.mall.core.action.backend;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;


import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.consts.GoodsConsts;
import com.ztesoft.net.eop.sdk.database.BaseJdbcDaoSupport;
import com.ztesoft.net.eop.sdk.utils.UploadUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.context.spring.SpringContextHolder;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.framework.util.FileBaseUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.ActivityAttr;
import com.ztesoft.net.mall.core.model.ActivityCo;
import com.ztesoft.net.mall.core.model.Goods;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.model.Promotion;
import com.ztesoft.net.mall.core.model.PromotionActivity;
import com.ztesoft.net.mall.core.service.IDcPublicInfoManager;
import com.ztesoft.net.mall.core.service.IDictManager;
import com.ztesoft.net.mall.core.service.IGroupBuyManager;
import com.ztesoft.net.mall.core.service.ILimitBuyManager;
import com.ztesoft.net.mall.core.service.IPromotionActivityManager;
import com.ztesoft.net.mall.core.service.ITagManager;
import com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy;
import com.ztesoft.net.mall.core.service.impl.cache.DictManagerCacheProxy;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 促销活动
 * 
 * @author lzf<br/>
 *         2010-4-20下午05:34:37<br/>
 *         version 1.0
 */
public class ActivityAction extends WWAction  {


	
	private IPromotionActivityManager promotionActivityManager;
	private IDcPublicInfoManager dcPublicInfoManager;

	@Resource
	private ITagManager tagManager;
	private ILimitBuyManager limitBuyManager ;
	private IGroupBuyManager groupBuyManager ;
	
	private PromotionActivity activity;
	private Promotion promotion;
	private ActivityAttr activityAttr;
	private String batch_id;
	private String deal_flag;
	private String begin_time;
	private String end_time;
	private String[] id;
	private String enable ;
	private String pmt_solution;

	private String activity_id;
	private List tagLst = null;
	private String[] tag_ids;
	private String picFileName;
	private File pic;
	private String isNetStaff = "F";

	private File file;
	private String fileFileName;

	private String org_ids;

	private String promotion_type;    
	private List promotionTypeList;  //活动类型
	private Product product;         //产品
	private Goods goods;         //商品货品
	private String dialog;           //页面对已的弹出框
	private String action;       //动作，add 新增，edit 修改
	private String isSearch;    //是否执行搜索操作
	
	private String s_type;//选择类型
	private List<Map> package_class_lst;  //套餐分类
	private List<Map> relief_no_class_lst;  //靓号减免类型
	private String type_id;//选择类型ID  直降转兑包10010  增值业务10008  礼品10007
	
	private String [] package_class_chk;  //选中的套餐分类
	private String [] relief_no_class_chk;  //选中的靓号减免类型
	private String [] goods_id;//赠品ID
	private String regionName;//地市名称
	private List<Map> regionList;
	
	//批量同步活动---zengxianlian
	private String postData;
	
	public List<Map> getRelief_no_class_lst() {
		return relief_no_class_lst;
	}

	public void setRelief_no_class_lst(List<Map> relief_no_class_lst) {
		this.relief_no_class_lst = relief_no_class_lst;
	}

	public String[] getPackage_class_chk() {
		return package_class_chk;
	}

	public void setPackage_class_chk(String[] package_class_chk) {
		this.package_class_chk = package_class_chk;
	}

	public String[] getRelief_no_class_chk() {
		return relief_no_class_chk;
	}

	public void setRelief_no_class_chk(String[] relief_no_class_chk) {
		this.relief_no_class_chk = relief_no_class_chk;
	}

	private Map<String, String> params = new HashMap<String, String>(0);
	

	/**
	 * 发布活动
	 * @return
	 */
	public String doPublishActivity(){
		try {
			promotionActivityManager.doPublishActivity(activity_id,org_ids);
			this.json = "{'result':0,'message':'操作成功'}";
		} catch (Exception e) {
			this.json = "{'result':1,'message':'操作失败'}";
			e.printStackTrace();
		}
		
		return JSON_MESSAGE;
	}
	
	/**
	 * 获取活动信息列表
	 * @return
	 */
	public String list() {
		
		if(activity == null){
			activity = new PromotionActivity();
		}
		this.webpage = promotionActivityManager.list(activity,this.getPage(), this.getPageSize());
		
		//判断是否为电信员工
		if (ManagerUtils.isNetStaff()) {
			this.isNetStaff = "T";
		}
		
		return "list";
	}

	/**
	 * 点击添加
	 * @return
	 */
	public String add() {

		this.tagLst = this.tagManager
				.ListByCatType(GoodsConsts.TAGS_CAT_TYPE_200);
		
		return "add";
	}

	/**
	 * 点击修改活动
	 * @return
	 */
	public String edit() {
		
		//活动类的标签库规格数据
		this.tagLst = this.tagManager.ListByCatType(GoodsConsts.TAGS_CAT_TYPE_200);
		
		//活动信息
		this.activity = this.promotionActivityManager.get(this.activity_id);
		//解析图片路径
		String attUrl = this.activity.getAtturl();
		if (attUrl != null) {
			attUrl = UploadUtil.replacePath(attUrl);
			activity.setAtturl(attUrl);
		}
		
		//活动拥有的标签
		List tagLst = this.tagManager.list(this.activity_id);
		if (tagLst != null) this.tag_ids = (String[])tagLst.toArray(new String[tagLst.size()]);
		
		this.begin_time = this.activity.getBegin_time();
		if (this.begin_time != null && this.begin_time.indexOf(" ") != -1) {
			this.begin_time = this.begin_time.substring(0, this.begin_time.indexOf(" "));
		}
		
		this.end_time = this.activity.getEnd_time();
		if (this.end_time != null && this.end_time.indexOf(" ") != -1) {
			this.end_time = this.end_time.substring(0, this.end_time.indexOf(" "));
		}
		
		//判断是否为电信员工
		if (ManagerUtils.isNetStaff()) {
			this.isNetStaff = "T";
		}
		
		return "edit";
	}
    /**
     * 修改活动状态
     */
	public String editEnable(){
		String editEnable ="";
		if("1".equals(this.enable)){
			editEnable = "0";
			//this.activity = promotionActivityManager.get(this.activity_id);
			
			if("008".equals(this.promotion_type)){
				
				this.groupBuyManager.editGroupState(this.pmt_solution,1);
				
			}
			if("009".equals(this.promotion_type)){
				
				this.limitBuyManager.editLimitState(this.pmt_solution,1);
				
			}
		}else{
			editEnable = "1";
			if("008".equals(this.promotion_type)){
				
				this.groupBuyManager.editGroupState(this.pmt_solution,0);
				
			}
			if("009".equals(this.promotion_type)){
				
				this.limitBuyManager.editLimitState(this.pmt_solution,0);
				
			}
		}
		try {
			this.promotionActivityManager.editEnable(editEnable, this.activity_id);
			this.msgs.add("活动状态修改成功！");
			this.urls.put("返回活动列表","promotion!listActivity.do");
			return WWAction.MESSAGE;
		} catch (Exception e) {
			logger.info(e);
			this.msgs.add(e.getMessage());
			return WWAction.MESSAGE;
		}
		
	}
	/**
	 * 添加活动保存
	 * @return
	 * @throws ParseException 
	 */
	public String saveAdd() throws ParseException {
		
		//设置图片路径
		if (this.pic != null) {

			if (FileBaseUtil.isAllowUp(this.picFileName)) {
				String path = UploadUtil.upload(this.pic, this.picFileName, "goods");
				this.activity.setAtturl(path);
			} else {
				this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp,swf格式文件。");
				return WWAction.MESSAGE;
			}
		}
		
		this.activity.setBegin_time(this.begin_time);
		this.activity.setEnd_time(this.end_time);
		try {
			this.promotionActivityManager.add(activity, this.tag_ids);
			this.msgs.add("活动添加成功");
		} catch (Exception e) {
			this.msgs.add("活动添加失败");
			e.printStackTrace();
		}
		
		this.urls.put("促销活动列表", "activity!list.do");
		return WWAction.MESSAGE;
	}

	/**
	 * 修改活动保存
	 * @return
	 * @throws ParseException 
	 */
	public String saveEdit() throws ParseException {

		//设置图片路径
		if (this.pic != null) {

			if (FileBaseUtil.isAllowUp(this.picFileName)) {
				String path = UploadUtil.upload(this.pic, this.picFileName, "goods");
				this.activity.setAtturl(path);
			} else {
				this.msgs.add("不允许上传的文件格式，请上传gif,jpg,bmp,swf格式文件。");
				return WWAction.MESSAGE;
			}
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getParameter("begin_time");
		this.activity.setBegin_time(begin_time);
		this.activity.setEnd_time(end_time);
		try {
			this.promotionActivityManager.edit(activity, this.tag_ids);
			this.msgs.add("活动修改成功");
		} catch (Exception e) {
			this.msgs.add("活动修改失败");
			e.printStackTrace();
		}
		this.urls.put("促销活动列表", "activity!list.do");
		
		return WWAction.MESSAGE;
	}

	public String delete() {
		try {
			this.promotionActivityManager.delete(id);
			this.json = "{'result':0,'message':'删除成功'}";
		} catch (Exception e) {
			this.json = "{'result':1;'message':'删除失败'}";
			e.printStackTrace();
		}
		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 批量导入活动
	 * @return
	 * @throws Exception 
	 */
	public String importActivity() throws Exception{
		
		
		try {
			Map result = this.promotionActivityManager.importActivity(file,fileFileName);
			if(!StringUtils.isEmpty(Const.getStrValue(result, "EXISTS_FILE"))){
				this.msgs.add("该文件【"+ fileFileName +"】已经导入，不能重复导入！");
				this.urls.put("活动导入日志列表", "activity!listActivityImportLogsECS.do");
				return WWAction.MESSAGE;
			}
			if(!StringUtils.isEmpty(Const.getStrValue(result, "NO_DATA"))){
				this.msgs.add("没有活动数据，请检查导入文件内容！");
				this.urls.put("活动导入日志列表", "activity!listActivityImportLogsECS.do");
				return WWAction.MESSAGE;
			}
			Integer successCount = (Integer) result.get("successCount");
			Integer failureCount = (Integer) result.get("failureCount");
			Integer totalCount = (Integer) result.get("totalCount");
			String batch_id = (String) result.get("batch_id");
			this.msgs.add("此次导入的活动总数是" + totalCount + "个。"
					+ " 其中，成功导入数为" + successCount + "个，失败数为" + failureCount + "个 ，批次号为"+batch_id);
			this.urls.put("活动导入日志列表", "activity!listActivityImportLogsECS.do");
		} catch (Exception e) {
			e.printStackTrace();
			this.msgs.add("活动导入失败"+e.getMessage());
			this.urls.put("活动导入日志列表", "activity!listActivityImportLogsECS.do");
		}
		this.promotionActivityManager.importActivity();
		return WWAction.MESSAGE;
	}

	/**
	 * 批量校验活动
	 * @return
	 * @throws Exception 
	 */
	public String checkActivity() throws Exception{
        try {
        	this.promotionActivityManager.checkImport(file,fileFileName,ServletActionContext.getResponse());
        }  
        catch(Exception e) {  
            e.printStackTrace();  
        }
		return WWAction.MESSAGE;
	}
	
	public String listActivityImportLogsECS(){
		this.webpage = this.promotionActivityManager.listActivityImportLogsECS(batch_id, begin_time, end_time, deal_flag, this.getPage(), this.getPageSize());		
		return "activity_import_logs";
	}
	
	public String dowloadBatchImportTemplate(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRealPath("/").replaceAll("\\\\", "/");
		if(path.endsWith("\\") || path.endsWith("/")){
			path += "shop/admin/activities/";
		}
		else{
			path += "/shop/admin/activities/";
		}
		String fileName = "活动批量导入模板.xls";
		String fileType = "xls";
		
		HttpServletResponse response = ServletActionContext.getResponse();
		
		try {
			DownLoadUtil.downLoadFile(path+fileName,response,fileName,fileType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//批量发布活动
	public String batchActivityPublish(){
		String message = "";
		if(activity == null){
			activity = new PromotionActivity();
			activity.setEnable(-1);
		}
		if(activityAttr == null){
			activityAttr = new ActivityAttr();
		}
		if(promotion == null){
			promotion = new Promotion();
		}
		try{
			message = promotionActivityManager.batchActivityPublish(activity, activityAttr, promotion);
		}catch(Exception e){
			e.printStackTrace();
		}
		this.msgs.add(message);
		this.urls.put("活动列表", "activity!listActivityECS.do");
		return WWAction.MESSAGE;
	}
	
	/**
	 * 获取活动信息列表ECS
	 * @return
	 */
	public String listActivityECS() {
		
		if(activity == null){
			activity = new PromotionActivity();
			activity.setEnable(-1);
		}
		if(activityAttr == null){
			activityAttr = new ActivityAttr();
		}
		if(promotion == null){
			promotion = new Promotion();
		}
		try{
			if("true".equals(isSearch)){
				this.webpage = promotionActivityManager.listECS(activity, activityAttr, promotion,this.getPage(), this.getPageSize());
			}else{
				this.webpage = new Page();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "list_activity_ecs";
	}
	
	public void exportActivity(){
		List list;
//		this.webpage = promotionActivityManager.listECS(activity, activityAttr, promotion,this.getPage(), this.getPageSize());
//		list=this.webpage.getResult();
		list=promotionActivityManager.queryActivityExport(activity);
		String[] title=new String[]{"活动编码","活动名称","活动状态","活动类型","金额","货品包名称","适用商品","套餐分类","最低套餐档次","最高套餐档次","活动地市","活动修改生效时间","活动商城",
	    		"活动起止时间","活动内容","直降转兑包","直降转兑包SKU","赠送业务","赠送业务SKU","礼品","礼品SKU","靓号减免类型"};
		
		String[] content = { "pmt_code","activity_name","activity_enable","pmt_type", "pmt_solution", "relation_name","goods", "package_class",
				"min_price", "max_price", "region_name","modify_eff_time" ,"org_id_str","begin_end_time",
				"brief","gifts_zjzdb","skus_zjzdb","gifts_zsyw","skus_zsyw","gifts_lp","skus_lp","relief_no_class"};
		
		
		String fileTitle = "活动导出";
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("webpage", this.webpage);
		if (excel != null && !"".equals(excel)) {
		DownLoadUtil.export(list, fileTitle, title, content, ServletActionContext.getResponse());
		}
	}
	
	
	public String listActivityModifyLogs(){
		this.webpage = promotionActivityManager.listActivityModifyLogsECS(activity, activityAttr, promotion,this.getPage(), this.getPageSize());
		return "activity_modify_logs";
	}
	
	/**
	 * 跳转到活动新增或修改页面
	 * @return
	 * @throws ParseException 
	 */
	public String showActivityWinECS() throws ParseException{
		this.listRegions();
		if(activity != null){
			String activity_id = activity.getId();
			if(StringUtils.isNotBlank(activity_id)){
				try{
					activity = this.promotionActivityManager.get(activity.getId());				
					promotion = this.promotionActivityManager.getPromotion(activity_id);
					Promotion giftPromo = this.promotionActivityManager.getGiftPromo(activity_id);
					String pmt_id = promotion.getPmt_id();
					String pmt_type = promotion.getPmt_type();
					String gift_pmt_id = giftPromo.getPmt_id();
					
					//套餐分类
					if (StringUtils.isNotEmpty(this.activity.getPackage_class())) {
						this.package_class_chk = this.activity.getPackage_class().split(",");
					}
					
					//靓号减免类型
					if (StringUtils.isNotEmpty(this.activity.getRelief_no_class())) {
						this.relief_no_class_chk = this.activity.getRelief_no_class().split(",");
					}
					
					//查询适用商品
					List<Goods> goodsList = this.promotionActivityManager.getGoodsByPmtId(pmt_id);
					List<Goods> giftList = this.promotionActivityManager.getGoodsByPmtId(gift_pmt_id);
					
					//如果是满赠，则查询试用商品作为赠品
					if(Consts.PMT_TYPE_MANZENG.equals(pmt_type)){
						giftList = goodsList;
						goodsList = new ArrayList<Goods>();
					}
					
					//处理试用商品，用goods_id,和name分别用逗号隔开
					String pmt_goods_ids = "" ;   //活动商品ID,多个用逗号隔开
					String pmt_goods_names = "" ; //活动商品名称,多个用逗号隔开
					for(int i=0;i<goodsList.size();i++){
						Goods goods = goodsList.get(i);
						if(i>0){
							pmt_goods_ids += ",";
							pmt_goods_names += ",";
						}
						pmt_goods_ids += goods.getGoods_id();
						pmt_goods_names += goods.getName();
					}
					if(activityAttr == null){
						activityAttr = new ActivityAttr();
					}
					activityAttr.setPmt_goods_ids(pmt_goods_ids);
					activityAttr.setPmt_goods_names(pmt_goods_names);
					activityAttr.setGoodsList(goodsList);
					activityAttr.setGiftList(giftList);
					
					//获取商品发布组织
					List<ActivityCo> coList = this.promotionActivityManager.getActivityCo(activity_id);
					String act_org_ids =""  ;   //活动商城id,多个用逗号隔开
					String act_org_names = "" ; //活动商城id,多个用逗号隔开
					for(int i=0;i<coList.size();i++){
						ActivityCo activityCo = coList.get(i);
						if(i>0){
							act_org_ids += ",";
							act_org_names += ",";
						}
						act_org_ids += activityCo.getOrg_id();
						act_org_names += activityCo.getOrg_name();
					}
					activityAttr.setAct_org_ids(act_org_ids);
					activityAttr.setAct_org_names(act_org_names);
					
					//獲取初始的地市,由頁面去計算
					if(null==this.params)
						this.params=new HashMap<String, String>();
					this.params.put("regionids", this.activity.getRegion());
					
					//获取发布状态
					Integer publish_status = this.promotionActivityManager.getActivityPubStatus(activity_id);
					activityAttr.setPublish_status(publish_status.toString());
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}

		} else {
			
			//新增时，信息默认设置
			String dateFormat="yyyy-MM-dd HH:mm:ss";
			activity = new PromotionActivity();
			activity.setBegin_time(DateFormatUtils.formatDate(dateFormat));
			activity.setEnd_time(DateFormatUtils.getLastDayInMonthWithoutDefaultFormat(DateFormatUtils.formatDate(dateFormat),dateFormat));
			activity.setModify_eff_time(activity.getBegin_time());
			
			promotion = new Promotion();
			promotion.setOrder_money_from(0.00);
			promotion.setPmt_solution("0");

		}
		DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
		
		//套餐分类
        this.package_class_lst = dcPublicCache.getList("201408051515");
        if(this.package_class_lst != null){
        	for(Map m:package_class_lst){
            	m.remove("checked");
            }
        	if(this.package_class_chk != null) {
        		for(String s:package_class_chk){
            		for(Map m:this.package_class_lst){
            			if(s != null && s.equals(m.get("pkey"))){
            				m.put("checked", "checked");
            				break ;
            			}
            		}
            	}
        	}
        	
        }
        	
        //靓号减免类型
        this.relief_no_class_lst = dcPublicCache.getList("20140604");
        if(this.relief_no_class_lst!=null){
        	for(Map m:relief_no_class_lst){
            	m.remove("checked");
            }
        	
        	if(this.relief_no_class_chk != null) {
        		for(String s:this.relief_no_class_chk){
            		for(Map m:this.relief_no_class_lst){
            			if(s!=null && s.equals(m.get("pkey"))){
            				m.put("checked", "checked");
            				break ;
            			}
            		}
            	}
        	}
        }
        
		return "add_edit_win_ecs";
	}
	
    /**
     * 查询商品
     * @return
     */
    public String goodsList(){
        String name = "";
        String sku = "";
        if(product != null){
        	name = product.getName();
        	sku = product.getSku();
        }
        String type = this.goods.getType();
        if (StringUtils.isEmpty(type)) {
        	type = "goods";
        }
        if ("package".equals(type)) {
        	//查询货品包
        	webpage = this.promotionActivityManager.getPackageList(this.getPage(), this.getPageSize(), name, sku);
        } else {
        	webpage = this.promotionActivityManager.getGoodsList(this.getPage(), this.getPageSize(),
        			name, type, type_id, sku);
        }
        
    	return  "goods_list";
    }
	
	/**
	 * 选择销售组织
	 * @return
	 */
	public String selectSaleOrg(){
		return "select_sale_org";
	}
	
	/**
	 * Is the activity to be saved breaking the weak constraint
	 * @return
	 */
	public String checkBeforeSave()
	{
		//需要校驗的信息有貨品包,商城,生失效時間,用戶類型,地市
		String jsonRtn="{\"code\":{code},\"message\":\"{message}\"}";
		json = jsonRtn.replace("{code}", "0").replace("{message}", "");
		
		if(null==this.activity || null==this.promotion || null==this.activityAttr)
		{
			this.json=jsonRtn.replace("{code}", "1").replace("{message}", "出错了");
		}
		else
		{
			String[] relations=this.activity.getRelation_id().split(",");
			String[] relations_name=this.activity.getRelation_name().split(",");
			String[] cities=this.activity.getRegion().split(",");
			String[] cities_name=this.regionName.split(",");
			int user_type=this.activity.getUser_type();
			String[] malls=this.activityAttr.getAct_org_ids().split(",");
			String[] malls_name=this.activityAttr.getAct_org_names().split(",");
			
			String doubleActivitySQLString="select count(distinct d.pmt_code) from es_pmt_goods a join es_relation_detail b on a.goods_id = b.object_id and b.relation_id = ? join es_promotion c on a.pmt_id = c.pmt_id and c.pmt_type in ('006','011') join es_promotion_activity d on c.pmta_id = d.id and d.enable = 1 and d.user_type=? {IFID} join es_activity_co e on e.activity_id = d.id and e.org_id = ? where a.source_from = '"+ManagerUtils.getSourceFrom()+"' and (d.region = '440000' or instr(d.region, ?)>0) and (to_char(d.begin_time, 'yyyy-mm-dd hh24:mi:ss') between '{BEGINTIME}' and '{ENDTIME}' or to_char(d.end_time, 'yyyy-mm-dd hh24:mi:ss') between '{BEGINTIME}' and '{ENDTIME}' or '{BEGINTIME}' between to_char(d.begin_time, 'yyyy-mm-dd') and to_char(d.end_time, 'yyyy-mm-dd hh24:mi:ss') or '{ENDTIME}' between to_char(d.begin_time, 'yyyy-mm-dd hh24:mi:ss') and to_char(d.end_time, 'yyyy-mm-dd hh24:mi:ss'))";
			doubleActivitySQLString=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString, "{BEGINTIME}", this.activity.getBegin_time());
			doubleActivitySQLString=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString, "{ENDTIME}", this.activity.getEnd_time());
			if(StringUtils.isEmpty(activity.getId()))
			{
				doubleActivitySQLString=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString, "{IFID}", "");
			}
			else
			{
				doubleActivitySQLString=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString, "{IFID}", " and d.id <> '"+activity.getId()+"' ");
			}
			
			BaseJdbcDaoSupport dao=SpringContextHolder.getBean("baseDaoSupport");
			
			outer:
			for(int i=0;i<relations.length;++i)
			{
				for(int j=0;j<malls.length;++j)
				{
					for(int k=0;k<cities.length;++k)
					{
						int result=dao.queryForInt(doubleActivitySQLString, relations[i],user_type,malls[j],cities[k]);
						if(0!=result)
						{
							this.json=jsonRtn.replace("{code}", "1").replace("{message}", relations_name[i]+"已经在"+malls_name[j]+" "+cities_name[k]+"配置了,是否要继续?");
							break outer;
						}
					}
				}
			}
		}
		
//		this.json=jsonRtn.replace("{code}", "1").replace("{message}", "出错了");
		logger.info(this.json);
		return WWAction.JSON_MESSAGE;
	}
	
	
	public String saveOrUpdateActECS(){
		try{
/*
				String doubleActivitySQLString="select count(distinct d.pmt_code) from es_pmt_goods a join es_relation_detail b on a.goods_id = b.object_id and b.relation_id = ? join es_promotion c on a.pmt_id = c.pmt_id and c.pmt_type = '006' join es_promotion_activity d on c.pmta_id = d.id and d.enable = 1 {IFID} join es_activity_co e on e.activity_id = d.id and e.org_id = ? where a.source_from = 'ECS' and (d.region = '440000' or instr( d.region,?)>0 ) and ( to_char(d.begin_time,'yyyy-mm-dd') between '{BEGINTIME}' and '{ENDTIME}' or to_char(d.end_time,'yyyy-mm-dd') between '{BEGINTIME}' and '{ENDTIME}' or '{BEGINTIME}' between to_char(d.begin_time,'yyyy-mm-dd') and to_char(d.end_time,'yyyy-mm-dd') or '{ENDTIME}' between to_char(d.begin_time,'yyyy-mm-dd') and to_char(d.end_time,'yyyy-mm-dd') )";
				String doubleActivitySQLString1="select count(distinct d.pmt_code) from es_pmt_goods a join es_relation_detail b on a.goods_id = b.object_id and b.relation_id = ? join es_promotion c on a.pmt_id = c.pmt_id and c.pmt_type = '006' join es_promotion_activity d on c.pmta_id = d.id and d.enable = 1 {IFID} join es_activity_co e on e.activity_id = d.id and e.org_id = ? where a.source_from = 'ECS' and d.region is not null  and ( to_char(d.begin_time,'yyyy-mm-dd') between '{BEGINTIME}' and '{ENDTIME}' or to_char(d.end_time,'yyyy-mm-dd') between '{BEGINTIME}' and '{ENDTIME}' or '{BEGINTIME}' between to_char(d.begin_time,'yyyy-mm-dd') and to_char(d.end_time,'yyyy-mm-dd') or '{ENDTIME}' between to_char(d.begin_time,'yyyy-mm-dd') and to_char(d.end_time,'yyyy-mm-dd') )";

				doubleActivitySQLString=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString, "{BEGINTIME}", this.activity.getBegin_time());
				doubleActivitySQLString=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString, "{ENDTIME}", this.activity.getEnd_time());
				doubleActivitySQLString1=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString1, "{BEGINTIME}", this.activity.getBegin_time());
				doubleActivitySQLString1=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString1, "{ENDTIME}", this.activity.getEnd_time());
				if(null==this.activity.getId() || "".equalsIgnoreCase(this.activity.getId()))
				{
					doubleActivitySQLString=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString, "{IFID}", "");
					doubleActivitySQLString1=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString1, "{IFID}", "");
				}
				else
				{
					doubleActivitySQLString=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString, "{IFID}", " and d.id <> '"+this.activity.getId()+"' ");
					doubleActivitySQLString1=org.apache.commons.lang.StringUtils.replace(doubleActivitySQLString1, "{IFID}", " and d.id <> '"+this.activity.getId()+"' ");
				}
				
				BaseJdbcDaoSupport dao=SpringContextHolder.getBean("baseDaoSupport");
				String resultTemplate="{'result':'oops';'msg':'{msg}'}";
				int doubleActivityCount=0;
				//the range of the time, the same region, the same relation_id can NOT have two more activcites;
				if(null!=this.activity && null!=this.activity.getRelation_name())
				{
					String[] relation_ids=org.apache.commons.lang.StringUtils.split(this.activity.getRelation_id(), ",");
					String[] relation_names=org.apache.commons.lang.StringUtils.split(this.activity.getRelation_name(),",");
					//String[] regions=org.apache.commons.lang.StringUtils.split(this.activity.getRegion(), ",");
					String[] orgs=org.apache.commons.lang.StringUtils.split(this.activityAttr.getAct_org_ids(),",");
					String[] orgNames=org.apache.commons.lang.StringUtils.split(this.activityAttr.getAct_org_names(), ",");
					String[] region_ids=org.apache.commons.lang.StringUtils.split(this.activity.getRegion(),",");
					String[] regionNames=org.apache.commons.lang.StringUtils.split(regionName, ",");
					//at this time of writing, this is just only ONE region can be selected
					if(StringUtils.equals(activity.getRegion(), "440000")){
						for(int i=0;i<relation_ids.length;++i)
						{
							for(int j=0;j<orgs.length;++j)
							{				
//								doubleActivitySQLString.replace("(d.region = '440000' or (select instr( d.region,?) from dual)>0 )", "d.region is not null");
								doubleActivityCount=dao.queryForInt(doubleActivitySQLString1, relation_ids[i],orgs[j]);
								if(0!=doubleActivityCount)
								{
									this.json=org.apache.commons.lang.StringUtils.replace(resultTemplate	, "{msg}", relation_names[i]+" 已经在商城： "+orgNames[j]+" 这个时间段内配置了");
									return this.JSON_MESSAGE;
								}
							}
						}			
					}
					for(int i=0;i<relation_ids.length;++i)
					{
						for(int j=0;j<orgs.length;++j)
						{							
							for(int k=0;k<region_ids.length;k++){								
								doubleActivityCount=dao.queryForInt(doubleActivitySQLString, relation_ids[i],orgs[j],
										region_ids[k]);
								if(0!=doubleActivityCount)
								{
									this.json=org.apache.commons.lang.StringUtils.replace(resultTemplate	, "{msg}", relation_names[i]+" 已经在商城： "+orgNames[j]+" 地市："+regionNames[k]+" 这个时间段内配置了");
									return this.JSON_MESSAGE;
								}
							}
						}
					}				
				}
			 */
			
			
			if(package_class_chk!=null && package_class_chk.length>0){
				String temp = "";
				for(String s:package_class_chk){
					temp+=s+",";
				}
				temp = temp.substring(0,temp.length()-1);
				activity.setPackage_class(temp);
			}
			
			if(relief_no_class_chk!=null && relief_no_class_chk.length>0){
				String temp = "";
				for(String s:relief_no_class_chk){
					temp+=s+",";
				}
				temp = temp.substring(0,temp.length()-1);
				activity.setRelief_no_class(temp);
			}
			
			if(goods_id!=null && goods_id.length>0){
				String temp = "";
				for(String s:goods_id){
					temp+=s+",";
				}
				temp = temp.substring(0,temp.length()-1);
				activityAttr.setGift_goods_ids(temp);
			}
			if("add".equals(action)){
				activity.setEnable(1);
			}
			promotionActivityManager.saveOrUpdateActECS(activity, activityAttr, promotion);
			this.json = "{'result':'true'}";
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{'result':'false'}";
		}

		return WWAction.JSON_MESSAGE;
	}
	
	/**
	 * 判断活动编码是否存在
	 * @return
	 */
	public String checkPmtCode(){
		String pmt_code = activity.getPmt_code();
		if(StringUtils.isNotBlank(pmt_code)){
			String result = promotionActivityManager.checkPmtCode(pmt_code);
			if("true".equals(result)){
				this.json = "{result:true}";
			}else{
				this.json = "{result:false}";
			}
			
		}else{
			this.json = "{result:false}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
    /**
     * 修改活动状态
     */
	public String editEnableECS(){
		if(activityAttr == null){
			activityAttr = new ActivityAttr();
		}
		promotionActivityManager.editEnableECS(activity,activityAttr);
		this.json = "{result:true}";
		return WWAction.JSON_MESSAGE;
		
	}
	
	public String searchActivitySynLogs(){
		this.webpage = promotionActivityManager.searchActivitySynLogs(params, this.getPage(), this.getPageSize());
		return "activity_syn_logs";
	}
	
	public String publishAgain(){
		int count = promotionActivityManager.publishAgain(params);
		if(count>0){
			this.json = "{result:0,message:'发布失败的活动已经成功重发'}";
		}
		else{
			this.json = "{result:1,message:'未找到可以重发的活动'}";
		}
		return JSON_MESSAGE;
	}
	
	private void listRegions(){
		this.regionList = this.getConsts("DC_COMMON_REGION_GUANGDONG");
	}
	
	public List<Map> getConsts(String key){
		IDictManager dictManager = SpringContextHolder.getBean("dictManager");
		DictManagerCacheProxy dc=new DictManagerCacheProxy(dictManager);
		List<Map> staticDatas=dc.loadData(key);
		return staticDatas;
	}
	
	/**
	 * @author zengxianlian
	 * 活动发布前..检查商品是否同步到指定商城
	 * @return
	 * @throws Exception 
	 */
	public void checkGoodsPublish() throws Exception{
		String[] data1 = postData.split(";");
		String[] data2 = null;
		Map<String,String> params = new HashMap<String,String>(0);
		List<String> ids = new ArrayList<String>(0);
		for(String d : data1){
			data2 = d.split("@");
			params.put(data2[0], data2[2]+"@"+data2[3]+"@"+data2[1]);
			ids.add(data2[0]);
		}
		logger.info(params);
		params = promotionActivityManager.checkPublishAct(params);
		logger.info(params);
		int count = 0;
		StringBuffer goodsCheckMsg = new StringBuffer();
		if(null != params && params.size() > 0){
			for(String key : params.keySet()){
				if(params.get(key).contains("商品没用同步到")){
					goodsCheckMsg.append(params.get(key));
				}else{
					count ++;
				}
			}
		}
		if(count<1){
			String msg = promotionActivityManager.publishAct(ids);
			String message = msg;
			if(null != goodsCheckMsg && goodsCheckMsg.length() > 0){
				message += ";但是部分商品未同步到商城," + goodsCheckMsg.toString();
			}
			this.json = "{\"result\":\"0\",\"message\":\""+message+"\"}";
			render(json,"text/x-json;charset=UTF-8");
		}else{
			StringBuffer sb = new StringBuffer();
			for(String key : params.keySet()){
				sb.append(params.get(key));
			}
			this.json = "{\"result\":\"0\",\"message\":\""+sb.toString()+"\"}";
			render(json,"text/x-json;charset=UTF-8");
		}
	}
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public PromotionActivity getActivity() {
		return activity;
	}

	public void setActivity(PromotionActivity activity) {
		this.activity = activity;
	}

	public IPromotionActivityManager getPromotionActivityManager() {
		return promotionActivityManager;
	}

	public void setPromotionActivityManager(
			IPromotionActivityManager promotionActivityManager) {
		this.promotionActivityManager = promotionActivityManager;
	}

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getActivity_id() {
		return activity_id;
	}

	public void setActivity_id(String activityId) {
		activity_id = activityId;
	}

	public List getTagLst() {
		return tagLst;
	}

	public void setTagLst(List tagLst) {
		this.tagLst = tagLst;
	}

	public String[] getId() {
		return id;
	}

	public void setId(String[] id) {
		this.id = id;
	}
	public String getIsNetStaff() {
		return isNetStaff;
	}

	public void setIsNetStaff(String isNetStaff) {
		this.isNetStaff = isNetStaff;
	}

	public File getPic() {
		return pic;
	}

	public void setPic(File pic) {
		this.pic = pic;
	}

	public String getPicFileName() {
		return picFileName;
	}

	public void setPicFileName(String picFileName) {
		this.picFileName = picFileName;
	}

	public String[] getTag_ids() {
		return tag_ids;
	}

	public void setTag_ids(String[] tag_ids) {
		this.tag_ids = tag_ids;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getPmt_solution() {
		return pmt_solution;
	}

	public void setPmt_solution(String pmt_solution) {
		this.pmt_solution = pmt_solution;
	}

	public ITagManager getTagManager() {
		return tagManager;
	}

	public void setTagManager(ITagManager tagManager) {
		this.tagManager = tagManager;
	}

	public ILimitBuyManager getLimitBuyManager() {
		return limitBuyManager;
	}

	public void setLimitBuyManager(ILimitBuyManager limitBuyManager) {
		this.limitBuyManager = limitBuyManager;
	}

	public IGroupBuyManager getGroupBuyManager() {
		return groupBuyManager;
	}

	public void setGroupBuyManager(IGroupBuyManager groupBuyManager) {
		this.groupBuyManager = groupBuyManager;
	}

	public String getPromotion_type() {
		return promotion_type;
	}

	public void setPromotion_type(String promotion_type) {
		this.promotion_type = promotion_type;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public List getPromotionTypeList() {
		return promotionTypeList;
	}

	public void setPromotionTypeList(List promotionTypeList) {
		this.promotionTypeList = promotionTypeList;
	}

	public IDcPublicInfoManager getDcPublicInfoManager() {
		return dcPublicInfoManager;
	}

	public void setDcPublicInfoManager(IDcPublicInfoManager dcPublicInfoManager) {
		this.dcPublicInfoManager = dcPublicInfoManager;
	}

	public String getDialog() {
		return dialog;
	}

	public void setDialog(String dialog) {
		this.dialog = dialog;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}

	public ActivityAttr getActivityAttr() {
		return activityAttr;
	}

	public void setActivityAttr(ActivityAttr activityAttr) {
		this.activityAttr = activityAttr;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOrg_ids() {
		return org_ids;
	}

	public void setOrg_ids(String org_ids) {
		this.org_ids = org_ids;
	}
	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getIsSearch() {
		return isSearch;
	}

	public void setIsSearch(String isSearch) {
		this.isSearch = isSearch;
	}

	public String getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}

	public String getDeal_flag() {
		return deal_flag;
	}

	public void setDeal_flag(String deal_flag) {
		this.deal_flag = deal_flag;
	}

	public String getS_type() {
		return s_type;
	}

	public void setS_type(String s_type) {
		this.s_type = s_type;
	}

	public List<Map> getPackage_class_lst() {
		return package_class_lst;
	}

	public void setPackage_class_lst(List<Map> package_class_lst) {
		this.package_class_lst = package_class_lst;
	}
	
	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String[] getGoods_id() {
		return goods_id;
	}

	public void setGoods_id(String[] goods_id) {
		this.goods_id = goods_id;
	}

	public List<Map> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<Map> regionList) {
		this.regionList = regionList;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getPostData() {
		return postData;
	}

	public void setPostData(String postData) {
		this.postData = postData;
	}

}
