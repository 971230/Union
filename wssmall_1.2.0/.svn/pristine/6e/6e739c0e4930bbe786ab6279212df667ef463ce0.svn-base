package com.ztesoft.common.valueobject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * dhtmlXtree树形节点
 * @author liupeidawn
 *
 */
public class TreeItem implements java.io.Serializable{


/*	必要属性有:
		text - 结点显示的标签
		id - 结点id 

		可选属性有:
		tooltip - 鼠标放在结点上提示的信息
		im0 - 没有子结点的结点显示的图片(将会从setImagePath(url)方法指定的路径去获取图片)
		im1 - 包含子结点的结点展开时显示的图片
		im2 - 包含子结点的结点关闭时显示的图片
		aCo1 - 没有选中的结点的颜色
		sCol - 选中的结点的颜色
		select - 在加载时选择此结点(可以为任意值)
		style - 结点文本风格
		open - 展开此结点(可以为任意值)
		call - 选择时调用函数(可以为任意值)
		checked - 如果存在的话,选择此结点的多选框(可以为任意值)
		child - 指定结点是否有子结点(1:有,0:无)
		imheight - 图标的高度
		imwidth - 图标的宽度
		topoffset - 设置结点和上层结点间的偏移量
		radio - 如果非空 则此结点的子结点会有单选按钮 */ 
	private String text=""; 
	
	private String id="";
	
	private String tooltip="";
	
	private String im0="";
	
	private String im1="";
	
	private String im2="";
	
	private String aCo1="";
	
	private String sCol="";
	
	private String select="";
	
	private String style="";
	
	private String open="";
	
	private String call="";
	
	private String checked="";
	
	private String imheight="";
	
	private String imwidth="";
	
	private String topoffset="";
	
	private String radio=""; 
	
	private List  userDatas=new ArrayList(); 
	
	public void loadUserDataFromMap(Map map){
		if(map!=null){
			Iterator it = map.entrySet().iterator();
			while(it.hasNext()){
				Entry entry = (Entry) it.next();
				String key = (String) entry.getKey();
				String value  = (String) entry.getValue();
				userDatas.add(new TreeUserData(key,value));
			}
		} 
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getIm0() {
		return im0;
	}

	public void setIm0(String im0) {
		this.im0 = im0;
	}

	public String getIm1() {
		return im1;
	}

	public void setIm1(String im1) {
		this.im1 = im1;
	}

	public String getIm2() {
		return im2;
	}

	public void setIm2(String im2) {
		this.im2 = im2;
	}

	public String getACo1() {
		return aCo1;
	}

	public void setACo1(String co1) {
		aCo1 = co1;
	}

	public String getSCol() {
		return sCol;
	}

	public void setSCol(String col) {
		sCol = col;
	}

	public String getSelect() {
		return select;
	}

	public void setSelect(String select) {
		this.select = select;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getImheight() {
		return imheight;
	}

	public void setImheight(String imheight) {
		this.imheight = imheight;
	}

	public String getImwidth() {
		return imwidth;
	}

	public void setImwidth(String imwidth) {
		this.imwidth = imwidth;
	}

	public String getTopoffset() {
		return topoffset;
	}

	public void setTopoffset(String topoffset) {
		this.topoffset = topoffset;
	}

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
	}

	public List getUserDatas() {
		return userDatas;
	}

	public void setUserDatas(List userDatas) {
		this.userDatas = userDatas;
	} 

	
	
}
