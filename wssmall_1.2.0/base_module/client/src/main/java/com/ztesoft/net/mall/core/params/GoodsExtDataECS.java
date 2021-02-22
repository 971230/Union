package com.ztesoft.net.mall.core.params;

/**
 * 产品包信息-联通ECS本地
 * @author zou.qh
 * 
 */
public class GoodsExtDataECS {
	//产品包信息-联通ECS本地
	private String goods_id;
	private String type;
	private String act_code;  //产品包合约编码p_code
	private String prod_code;  //产品包SN
	private String[] ecs_sns;  //货品SN
	private String[] typeids;  //货品类型ID
	private String[] z_goods_ids;  //货品goods_id
	
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAct_code() {
		return act_code;
	}
	public void setAct_code(String act_code) {
		this.act_code = act_code;
	}
	public String getProd_code() {
		return prod_code;
	}
	public void setProd_code(String prod_code) {
		this.prod_code = prod_code;
	}
	public String[] getEcs_sns() {
		return ecs_sns;
	}
	public void setEcs_sns(String[] ecs_sns) {
		this.ecs_sns = ecs_sns;
	}
	public String[] getTypeids() {
		return typeids;
	}
	public void setTypeids(String[] typeids) {
		this.typeids = typeids;
	}
	public String[] getZ_goods_ids() {
		return z_goods_ids;
	}
	public void setZ_goods_ids(String[] z_goods_ids) {
		this.z_goods_ids = z_goods_ids;
	}
}
