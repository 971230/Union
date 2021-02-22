package services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import params.member.req.MemberLvByIdReq;
import params.member.req.MemberLvPriceReq;
import params.member.req.MemberPriceByPIdReq;
import params.order.resp.OrderOuterResp;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.api.spring.ApiContextHolder;
import com.ztesoft.common.util.BeanUtils;
import com.ztesoft.ibss.common.util.Const;
import com.ztesoft.net.app.base.core.model.MemberLv;
import com.ztesoft.net.framework.util.CurrencyUtil;
import com.ztesoft.net.framework.util.StringUtil;
import com.ztesoft.net.mall.core.consts.Consts;
import com.ztesoft.net.mall.core.model.AdjunctItem;
import com.ztesoft.net.mall.core.model.GoodsLvPrice;
import com.ztesoft.net.mall.core.model.OrderOuter;
import com.ztesoft.net.mall.core.model.Product;
import com.ztesoft.net.mall.core.service.IOrderManager;
import com.ztesoft.net.mall.core.service.ITplInstManager;
import com.ztesoft.net.mall.core.utils.ICacheUtil;
import com.ztesoft.net.model.AttrDef;
import com.ztesoft.net.model.AttrInst;
import com.ztesoft.net.model.TempInst;
import com.ztesoft.net.model.TplAttr;
import com.ztesoft.net.model.WarehousePurorder;

/**
 * 外系统订单接口
* @作者 wui 
* @创建日期 2013-10-10 
* @版本 V 1.0
 */
public class OrderOuterServ extends ServiceBase implements OrderOuterInf{
    @Resource
    public ProductInf proudctServ;
	
	private ITplInstManager tplInstManager;
	private ICacheUtil cacheUtil;

	private MemberPriceInf memberPriceServ;
	private MemberLvInf memberLvServ;
	private MemberLVSpecInf memberLVSpecServ;
	
	@Resource
	private GoodsAdjunctInf goodsAdjunctServ;
	
	@Resource
	private IOrderManager orderManager;
	
	private void init(){
		if(null == memberPriceServ) memberPriceServ = ApiContextHolder.getBean("memberPriceServ");
		if(null == memberLvServ) memberLvServ = ApiContextHolder.getBean("memberLvServ");
		if(null == memberLVSpecServ) memberLVSpecServ = ApiContextHolder.getBean("memberLVSpecServ");
	}
	
	/**
	 * 保存外系统信息
	 * @param inMap
	 * @throws Exception
	 */
	@Override
	public OrderOuterResp cerateOuterOrder(Map inMap) throws Exception{ //{app_code:111,app_code:33}
		
		OrderOuterResp orderOuterResp = new OrderOuterResp();
		
		OrderOuter orderOuter = assembleOrderOuter(inMap);
		
		TempInst tempInst = tplInstManager.getTempInstByGoodsIdAndSource(orderOuter.getGoods_id(),(String)inMap.get("source_from"));
		if(tempInst!=null)
			inMap.put("temp_inst_id", tempInst.getTemp_inst_id());
		String outer_order_id = this.baseDaoSupport.getSequences("s_es_order");
		orderOuter.setOrder_id(outer_order_id);
		
		//插入预受理单
		orderManager.saveOrderOuter(orderOuter);
		
		List<AttrInst> attrInsts = assembleOuterInstAttr(inMap,orderOuter);
		orderOuter.setOuterAttrInsts(attrInsts);
		
		//预受理属性
		orderManager.saveOuterAttrInst(orderOuter);
		
		orderOuterResp.setOrderOuter(orderOuter);
		
		return orderOuterResp;
		
	}
	
	
	/**
	 * 组装外系统订单信息
	 * @param map
	 * @throws Exception 
	 */
	public OrderOuter assembleOrderOuter(Map inMap) throws Exception{
		//初始化bean
		init();
		
		OrderOuter orderOuter = new OrderOuter();
		BeanUtils.copyProperties(orderOuter, inMap);
		
		String batchId = this.baseDaoSupport.getSequences("s_es_cust_returned");
		Product product = proudctServ.get((String)inMap.get("product_id"));
		if(!StringUtil.isEmpty(orderOuter.getPrice())){
			product.setPrice(Double.valueOf(orderOuter.getPrice()));
		}
		orderOuter.setProduct_id(product.getProduct_id());
		String goods_id =product.getGoods_id();
		String goods_num = (String)inMap.get("goods_num");
		String certi_type = (String)inMap.get("certi_type");
		if(StringUtil.isEmpty(goods_num))
			goods_num ="1";
		if(StringUtil.isEmpty(certi_type))
			certi_type = Consts.CERTI_TYPE_P;
		//Goods goods = cacheUtil.getGoodsById(goods_id);
		String 	goods_name = product.getName();
		String order_id =this.baseDaoSupport.getSeqByTableName("es_order");
		List<AdjunctItem> adjunctList = (List<AdjunctItem>) inMap.get("adjunctList");
		if(StringUtil.isEmpty(orderOuter.getP_order_amount())){
			int num = Integer.parseInt(goods_num);
			String lv_id = orderOuter.getMember_lv_id()==null?"0":orderOuter.getMember_lv_id();
			double price = product.getPrice();
			MemberPriceByPIdReq  mpreq = new MemberPriceByPIdReq();
			mpreq.setProduct_id(product.getProduct_id());
			mpreq.setMember_lv_id(lv_id);
			GoodsLvPrice gp = memberPriceServ.getPriceByPid(mpreq).getGoodsLvPrice();
			
			MemberLvByIdReq mlreq = new MemberLvByIdReq();
			mlreq.setMember_lv_id(lv_id);
			MemberLv lv =memberLvServ.getMemberLvById(mlreq).getMemberLv();
			if(lv!=null){
				double discount = lv.getDiscount()/100.00;
				price = product.getPrice()*discount;
				if(gp!=null){
					price = gp.getPrice();
				}
			}
			
			if(adjunctList!=null &&adjunctList.size()>0){
				String name = "";
				for(AdjunctItem adj:adjunctList){
					Double memberPrice = null;
					Double adjprice =product.getPrice();
					Product adjproduct =proudctServ.get(adj.getProductid());
					MemberLvPriceReq lvpreq = new MemberLvPriceReq();
					lvpreq.setProduct_id(adjproduct.getProduct_id());
					lvpreq.setLvid(lv_id);
					lvpreq.setGoods_id(adjproduct.getGoods_id());
					Map gmap = memberLVSpecServ.getMemberLvPrice(lvpreq).getMemberLVSpecMap();
					if(gmap!=null){
						memberPrice = Double.valueOf(gmap.get("price").toString());
					}
					if(memberPrice!=null)
						adjprice = memberPrice;
					Map adjGroup = goodsAdjunctServ.getAdjunct(adj.getAdjunct_id());
					String type = (String)adjGroup.get("set_price");  //折扣方式
					Double discount =((BigDecimal)adjGroup.get("price")).doubleValue();      //折扣价格
					if("discount".equals(type)){ //打折方式
						adjprice=CurrencyUtil.mul(adjprice, discount);
					}
					if("minus".equals(type)){ //直接减价
						adjprice= CurrencyUtil.sub(adjprice, discount);
					}
					adjprice=CurrencyUtil.mul(adj.getNum(), adjprice);
					price = CurrencyUtil.add(price, adjprice);
					adj.setCoupPrice(adjprice);
					adj.setPrice(adjproduct.getPrice());
					name+="+"+product.getName()+"("+ adj.getNum() +")";
				}
				orderOuter.setGoods_name(name);
			}
			double order_amount = price*num;
			orderOuter.setOrder_amount(String.valueOf(order_amount));
		}else{
			orderOuter.setOrder_amount(orderOuter.getP_order_amount());
		}
		if(adjunctList!=null && adjunctList.size()>0){
			orderOuter.setAddon(JSONArray.fromObject(adjunctList).toString());
		}
		orderOuter.setCerti_type(certi_type);
		orderOuter.setOrder_id(order_id);
		orderOuter.setCreate_time(DBTUtil.current());
		orderOuter.setGoods_id(goods_id);
		orderOuter.setGoods_name(goods_name);
		orderOuter.setGoods_num(goods_num);
		orderOuter.setBatch_id(batchId);
		Object obj = inMap.get("warehousePurorder");
		if(obj !=null)
			orderOuter.setWarehousePurorder((WarehousePurorder) obj);
		orderOuter.setImport_state(Consts.IMPORT_STATE_0);
		//orderOuter.setOrder_id(Systemt);
		//orderOuter.setHouse_id((String) inMap.get("house_id"));
		if(StringUtil.isEmpty(orderOuter.getRemark())){
			orderOuter.setRemark((String) inMap.get("remark"));
		}
		if(StringUtil.isEmpty(orderOuter.getShip_name())){
			orderOuter.setShip_name((String) inMap.get("ship_name"));
		}
		return orderOuter;
	}
	
	/**
	 * 保存外系统订单信息
	 * @param map
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<AttrInst> assembleOuterInstAttr(Map inMap,OrderOuter orderOuter) throws Exception{
		
		List<TplAttr> tLists = tplInstManager.getAttrsByCol(inMap);
		List<AttrInst> outAttrInst = new ArrayList<AttrInst>();
		for (int i = 0; i < tLists.size(); i++) {
			 AttrInst attrInst = new AttrInst();
			 TplAttr tplAttr = tLists.get(i);
			 AttrDef attrDef = tplInstManager.getAttrDef(tplAttr.getField_attr_id());
			 attrInst.setAttr_inst_id(this.baseDaoSupport.getSequences("s_es_attr_inst"));
			 attrInst.setOrder_id(orderOuter.getOrder_id());
			 attrInst.setAttr_spec_id(attrDef.getAttr_spec_id());
			 attrInst.setField_attr_id(tplAttr.getField_attr_id());
			 attrInst.setField_name(tplAttr.getE_name());
			 attrInst.setFiled_desc(attrDef.getField_desc());
			 String o_field_name =attrDef.getO_field_name();
			 if(StringUtil.isEmpty(o_field_name))
				 o_field_name = tplAttr.getE_name();
			 attrInst.setField_value(Const.getStrValue(inMap, o_field_name));
			 attrInst.setSequ(0l);
			 attrInst.setCreate_date(DBTUtil.current());
			 outAttrInst.add(attrInst);
		}
		return outAttrInst;
	}
	
//	
//	public void saveOrderOuter(OrderOuter orderOuter) throws Exception{
//		
//		//写到线程变量
//		this.baseDaoSupport.insert("ORDER_OUTER", orderOuter);
//	}
	
	public void saveOuterAttrInst(List<AttrInst> attrInsts) throws Exception{
		//TODO 构造插入语句
		/*String sql = "insert into es_outer_attr_inst(attr_inst_id, order_id, order_item_id, inst_id,"+
			" attr_spec_id, field_attr_id, field_name, filed_desc, field_value,"+
			" field_value_desc, modify_field_value, sequ, sec_field_value,"+
			" sec_filed_value_desc, col1, col2, col3, create_date) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
 		this.baseDaoSupport.batchExecute(sql, attrInsts);*/
		if(attrInsts!=null && attrInsts.size()>0){
			for(AttrInst a:attrInsts){
				this.baseDaoSupport.insert("es_outer_attr_inst", a);
			}
		}
	}



	public ITplInstManager getTplInstManager() {
		return tplInstManager;
	}



	public void setTplInstManager(ITplInstManager tplInstManager) {
		this.tplInstManager = tplInstManager;
	}



	public ICacheUtil getCacheUtil() {
		return cacheUtil;
	}



	public void setCacheUtil(ICacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}
}