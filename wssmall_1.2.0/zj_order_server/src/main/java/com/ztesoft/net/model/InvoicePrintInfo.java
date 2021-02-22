/**
 * 
 */
package com.ztesoft.net.model;

import java.io.Serializable;

import zte.net.ecsord.params.busi.req.OrderTreeBusiRequest;

/**
 * @author ZX
 * InvoicePrintInfo.java
 * 发票打印信息
 * 2014-11-10
 */
public class InvoicePrintInfo extends AutoOrderTree implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderTreeBusiRequest orderTree;
	
	private String shouhuorenxingming;
	private String goods_type;
	private String shouhuorendianhua;
	private Float shishoujine;
	private Float yingshoujine;
	private String zhongduanchuanhao;
	private String huodongyucunkuan;
	private String waibudingdanbianhao;
	private String daxie;
	private String kaipiaoshijian;
	private Float lianghaoyucun;
	private String kaipiaoren;
	private String shangpindanbianhao;	
	private String order_is_his;	
	private String invoice_num;
	private String memo;
	
	public String getShangpindanbianhao() {
		return shangpindanbianhao;
	}
	public void setShangpindanbianhao(String shangpindanbianhao) {
		this.shangpindanbianhao = shangpindanbianhao;
	}
	public String getKaipiaoren() {
		return kaipiaoren;
	}
	public void setKaipiaoren(String kaipiaoren) {
		this.kaipiaoren = kaipiaoren;
	}
	public Float getLianghaoyucun() {
		return lianghaoyucun;
	}
	public void setLianghaoyucun(Float lianghaoyucun) {
		this.lianghaoyucun = lianghaoyucun;
	}
	public String getKaipiaoshijian() {
		return kaipiaoshijian;
	}
	public void setKaipiaoshijian(String kaipiaoshijian) {
		this.kaipiaoshijian = kaipiaoshijian;
	}
	public String getDaxie() {
		return daxie;
	}
	public void setDaxie(String daxie) {
		this.daxie = daxie;
	}
	public String getWaibudingdanbianhao() {
		return waibudingdanbianhao;
	}
	public void setWaibudingdanbianhao(String waibudingdanbianhao) {
		this.waibudingdanbianhao = waibudingdanbianhao;
	}
	@Override
	public OrderTreeBusiRequest getOrderTree() {
		return orderTree;
	}
	@Override
	public void setOrderTree(OrderTreeBusiRequest orderTree) {
		this.orderTree = orderTree;
	}
	public String getShouhuorenxingming() {
		return shouhuorenxingming;
	}
	public void setShouhuorenxingming(String shouhuorenxingming) {
		this.shouhuorenxingming = shouhuorenxingming;
	}
	public String getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}
	public String getShouhuorendianhua() {
		return shouhuorendianhua;
	}
	public void setShouhuorendianhua(String shouhuorendianhua) {
		this.shouhuorendianhua = shouhuorendianhua;
	}
	public Float getShishoujine() {
		return shishoujine;
	}
	public void setShishoujine(Float shishoujine) {
		this.shishoujine = shishoujine;
	}
	public Float getYingshoujine() {
		return yingshoujine;
	}
	public void setYingshoujine(Float yingshoujine) {
		this.yingshoujine = yingshoujine;
	}
	public String getZhongduanchuanhao() {
		return zhongduanchuanhao;
	}
	public void setZhongduanchuanhao(String zhongduanchuanhao) {
		this.zhongduanchuanhao = zhongduanchuanhao;
	}
	public String getHuodongyucunkuan() {
		return huodongyucunkuan;
	}
	public void setHuodongyucunkuan(String huodongyucunkuan) {
		this.huodongyucunkuan = huodongyucunkuan;
	}
	public String getOrder_is_his() {
		return order_is_his;
	}
	public void setOrder_is_his(String order_is_his) {
		this.order_is_his = order_is_his;
	}
	@Override
	public String getInvoice_num() {
		return invoice_num;
	}
	@Override
	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
