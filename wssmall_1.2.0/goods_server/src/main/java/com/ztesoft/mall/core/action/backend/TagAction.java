package com.ztesoft.mall.core.action.backend;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.mall.core.model.Tag;
import com.ztesoft.net.mall.core.service.ITagManager;
import com.ztesoft.net.mall.core.utils.ManagerUtils;

/**
 * 标签action
 * @author kingapex
 * 2010-7-14上午11:54:15
 */
/**
 * @author Wcl
 *
 */
public class TagAction extends WWAction {
	
	private ITagManager tagManager;
	private Tag tag;
	private Tag classTag;
	private String[] tag_ids;
	private String tag_name;
	private String tag_id;
	private String choose_tag_type;
	private String owner_type;
	private String tag_status;
	private String tag_code;
	private String tag_desc;
	private String tag_type; 
	private String tag_value;
	private String listFormActionVal;
	private String esgoodscos;
	public String checkJoinGoods(){

		if(this.tagManager.checkJoinGoods(tag_ids)){
			this.json="{result:1}";
		}else{
			this.json="{result:0}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String checkname(){
		if( this.tagManager.checkname(tag.getTag_name(), tag.getTag_id()) ){
			this.json="{result:1}";
		}else{
			this.json="{result:0}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	public String add(){
		
		return "add";
	}
	
	public String edit(){
		 tag = this.tagManager.getById(tag_id);
		return "edit";
	}
	
	
	//添加标签
	public String saveAdd(){
		this.tagManager.add(tag);
		this.msgs.add("标签添加成功");
		this.urls.put("标签列表", "tag!list.do");
		return WWAction.MESSAGE;
	}
	
	
	//保存修改
	public String saveEdit(){
		
		this.tagManager.update(tag);
 
		this.msgs.add("标签修改成功");
		this.urls.put("标签列表", "tag!list.do");
		
		return WWAction.MESSAGE;
	}
	
	public String delete(){
		
	 	this.tagManager.delete(tag_ids);
		json = "{'result':0,'message':'删除成功'}";
		
		return WWAction.JSON_MESSAGE	;	
	}
	
	public String list(){
		this.webpage = this.tagManager.list(this.getPage(), this.getPageSize());
		return "list";
	}

	public String goodlist(){
		this.webpage = this.tagManager.goodlist(this.getPage(), this.getPageSize());
		return "goodlist";
	}
	
	public String shoplist(){
		this.webpage = this.tagManager.shoplist(this.getPage(), this.getPageSize());
		return "shoplist";
	}
	
	//添加标签组
	public String addTagList() {
		//展示标签列表
		Tag newTag = new Tag();
		//商品标签添加标签组
		if(choose_tag_type.equals("tag_good")) {
			newTag.setCat_type("tag_good");
			this.webpage = this.tagManager.showTag("es_goods_tag", this.getPage(), this.getPageSize());
		} 
		
		//营销标签添加标签组
		if(choose_tag_type.equals("tag_shop")) {
			newTag.setCat_type("tag_shop");
			this.webpage = this.tagManager.showTag("es_sale_tag", this.getPage(), this.getPageSize());

		}
		classTag = newTag;
		return "addTaglist";
	}
	
	//添加标签
	public String addTag() {
		logger.info(classTag);
		//展示标签组的列表
		Tag newTag = new Tag();
		if(choose_tag_type.equals("tag_good")) {
			newTag.setCat_type("tag_good");

			this.webpage = this.tagManager.showTagList("es_goods_tag", this.getPage(), this.getPageSize());

		}
				
		//添加营销标签
		if(choose_tag_type.equals("tag_shop")) {
			newTag.setCat_type("tag_shop");
			this.webpage = this.tagManager.showTagList("es_sale_tag", this.getPage(), this.getPageSize());

		}
		
		classTag = newTag;
		return "addTag";
	}
	
	//搜索 
	public String search() {
		logger.info("标签名称："+tag_name);
		logger.info("标签类型："+tag_type);
		
		if(choose_tag_type.equals("tag_good")) {
			this.webpage = this.tagManager.searchTag("es_goods_tag", this.getPage(), this.getPageSize(), tag_name, tag_type);
			return "goodlist";
		}
		
		if(choose_tag_type.equals("tag_shop")) {
			this.webpage =this.tagManager.searchTag("es_sale_tag", this.getPage(), this.getPageSize(), tag_name, tag_type);
			return "shoplist";
		}
		this.msgs.add("搜索内容有误，找不到页面");
		return WWAction.MESSAGE;
	}
	
	//保存
	public String saveTag() {		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		
		String time = sdf.format(new Date());
		
		Tag insertTag = new Tag();
		insertTag.setTag_id(time);
		insertTag.setTag_code(tag_code);
		insertTag.setTag_name(tag_name);
		insertTag.setTag_desc(tag_desc);
		insertTag.setTag_status(tag_status);
		insertTag.setTag_type(tag_type);
		insertTag.setTag_value(tag_value);
		insertTag.setSource_from(ManagerUtils.getSourceFrom());
		try {
			if(choose_tag_type.equals("tag_good")) {
				//判断标签编码是否存在在商品标签库中
				if(this.tagManager.countTagByCode("es_goods_tag", tag_code) != 0) {
					
					this.msgs.add("输入的编码已存在");
					this.urls.put("", "tag!goodlist.do");
					
					return WWAction.MESSAGE;
				}
				
				
				//向商品标签库插入数据
				this.tagManager.insertTag("es_goods_tag",insertTag);
				this.msgs.add("添加成功");
				this.urls.put("", "tag!goodlist.do");
			}
			
			if(choose_tag_type.equals("tag_shop")) {
				//判断标签编码是否存在在营销标签库中
				this.tagManager.countTagByCode("es_sale_tag", tag_code);
				//向营销标签库中插入数据
				this.tagManager.insertTag("es_sale_tag",insertTag);
				this.msgs.add("添加成功");
				this.urls.put("", "tag!shoplist.do");
			}
		} catch (Exception e) {
			// TODO: handle exception
			this.msgs.add(e.getMessage());
		}

		return WWAction.MESSAGE;
	}
	
	//失效
	public String invalid() {
		logger.info(tag_ids);
		try {
			if(choose_tag_type.equals("tag_good")) {
				this.tagManager.invaliTag("es_goods_tag", tag_ids);
				json = "{'result':'0','message':'已成功失效'}";
			}
			
			if(choose_tag_type.equals("tag_shop")) {
				this.tagManager.invaliTag("es_sale_tag", tag_ids);
				json = "{'result':'0','message':'已成功失效'}";
			}
			this.msgs.add("失效成功");
		} catch (Exception e) {
			// TODO: handle exception
			json = "{'result':'-1','message':'失效失败'}";
		}
		
		return WWAction.JSON_MESSAGE;
	}
	
	
	//删除
	public String deleteTag() {
		logger.info(tag_code);
		logger.info(choose_tag_type);
				
		if(choose_tag_type.equals("tag_good")) {
			//商品标签删除
			this.tagManager.deleteTag("es_goods_tag", tag_code);
			this.urls.put("", "tag!goodlist.do");
			this.msgs.add("删除成功");
			
			return WWAction.MESSAGE;
		}
		
		if(choose_tag_type.equals("tag_shop")) {
			//营销标签删除
			this.tagManager.deleteTag("es_sale_tag", tag_code);
			this.urls.put("", "tag!shoplist.do");
			this.msgs.add("删除成功");
			
			return WWAction.MESSAGE;
		}
		this.msgs.add("删除失败");
		return WWAction.MESSAGE;
	}
	 
	//编辑标签
	public String editTag() {
		if(choose_tag_type.equals("tag_good")) {
			this.tag = this.tagManager.getByCode("es_goods_tag",tag_id);
			this.tag.setCat_type("tag_good");
		}
		
		if(choose_tag_type.equals("tag_shop")) {
			this.tag = this.tagManager.getByCode("es_sale_tag",tag_id);
			this.tag.setCat_type("tag_shop");
		}
	
		return "editTag";
	}
	
	//更新标签
	public String updateTag() {
		logger.info(tag_id);
		Map<String,Object> tagMap = new HashMap<String,Object>();
		
		tagMap.put("tag_code", tag_code);
		tagMap.put("tag_name", tag_name);
		tagMap.put("tag_status", tag_status);
		tagMap.put("tag_type", tag_type);
		tagMap.put("tag_desc", tag_desc);
		
		
		if(choose_tag_type.equals("tag_good")) {
			tagMap.put("tag_value", tag_value);
			this.tagManager.updateTagById("es_goods_tag", tag_id, tagMap);
			this.urls.put("", "tag!goodlist.do");
			this.msgs.add("修改成功");
		}
		
		if(choose_tag_type.equals("tag_shop")) {
			this.tagManager.updateTagById("es_sale_tag", tag_id, tagMap);
			this.urls.put("", "tag!shoplist.do");
			this.msgs.add("修改成功");
		}
		
		return WWAction.MESSAGE;
	}
	
	//查询树
	public String qryTree(){
		List list = tagManager.qryTree();
		this.json = JSON.toJSONString(list);
		return JSON_MESSAGE;
	}
		
	public String tagPubtree() {
		logger.info(this.esgoodscos);
		this.listFormActionVal="esgoodsco!tagLiberacion.do?ajax=yes";
		
		return "tagPubtree";
	}
		
	public String tagSync() {
		if(tag_ids == null || tag_ids.length == 0) {
			json = "{'result':-1,'ids':0}";
		}
		StringBuffer sb = new StringBuffer();
		for (String tag_id : tag_ids) {
			sb.append(tag_id+",");
		}
		String ids = sb.substring(0, sb.length()-1);

		json = "{'result':'1','ids':'"+ids+"'}";	
		return WWAction.JSON_MESSAGE;
	}
	
	public ITagManager getTagManager() {
		return tagManager;
	}

	public void setTagManager(ITagManager tagManager) {
		this.tagManager = tagManager;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag; 
	}

	public String[] getTag_ids() {
		return tag_ids;
	}

	public void setTag_ids(String[] tag_ids) {
		this.tag_ids = tag_ids;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

	public String getChoose_tag_type() {
		return choose_tag_type;
	}

	public void setChoose_tag_type(String choose_tag_type) {
		this.choose_tag_type = choose_tag_type;
	}

	public String getOwner_type() {
		return owner_type;
	}

	public void setOwner_type(String owner_type) {
		this.owner_type = owner_type;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public String getTag_status() {
		return tag_status;
	}

	public void setTag_status(String tag_status) {
		this.tag_status = tag_status;
	}

	public String getTag_code() {
		return tag_code;
	}

	public void setTag_code(String tag_code) {
		this.tag_code = tag_code;
	}

	public String getTag_desc() {
		return tag_desc;
	}

	public void setTag_desc(String tag_desc) {
		this.tag_desc = tag_desc;
	}

	public String getTag_type() {
		return tag_type;
	}

	public void setTag_type(String tag_type) {
		this.tag_type = tag_type;
	}

	public Tag getClassTag() {
		return classTag;
	}

	public void setClassTag(Tag classTag) {
		this.classTag = classTag;
	}

	public String getTag_value() {
		return tag_value;
	}

	public void setTag_value(String tag_value) {
		this.tag_value = tag_value;
	}

	public String getListFormActionVal() {
		return listFormActionVal;
	}

	public void setListFormActionVal(String listFormActionVal) {
		this.listFormActionVal = listFormActionVal;
	}

	public String getEsgoodscos() {
		return esgoodscos;
	}

	public void setEsgoodscos(String esgoodscos) {
		this.esgoodscos = esgoodscos;
	}
}
