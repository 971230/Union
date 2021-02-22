package com.ztesoft.net.action.backend;

import java.util.List;
import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.framework.util.DownLoadUtil;
import com.ztesoft.net.mall.core.model.CommunityActivity;
import com.ztesoft.net.service.ICommunityManager;


/**
 * BaseAction.java
 * @author cc
 *
 * 2013-9-28下午3:58:09
 */
public class CommunityAction extends WWAction{

	private ICommunityManager communityManager;
	private CommunityActivity activity;
	private String fileName;//文件名称
	private File file;//文件
	private String goods_name;//商品名称
	private String listParams;//参数List集合
	private String mapParams;//参数Map集合
	
	
	
	public String getMapParams() {
		return mapParams;
	}

	public void setMapParams(String mapParams) {
		this.mapParams = mapParams;
	}

	public String getListParams() {
		return listParams;
	}

	public void setListParams(String listParams) {
		this.listParams = listParams;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public CommunityActivity getActivity() {
		return activity;
	}

	public void setActivity(CommunityActivity activity) {
		this.activity = activity;
	}

	public ICommunityManager getCommunityManager() {
		return communityManager;
	}

	public void setCommunityManager(ICommunityManager communityManager) {
		this.communityManager = communityManager;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * 获取小区信息列表
	 * @return
	 */
	public String list() {
		if(activity == null){
			activity = new CommunityActivity();
		}
		this.webpage = communityManager.list(activity,this.getPage(), this.getPageSize());
		return "list";
	}

	/**
	 * 【小区数据录入】Excel模板下载
	 */
	public void downLoadExcel() throws Exception {
		String fileName="Excel_Model.xlsx";
		String filePath=ServletActionContext.getServletContext().getRealPath("/WEB-INF/excel/"+fileName);
		HttpServletResponse response=ServletActionContext.getResponse();
		String fileType="xlsx";
		DownLoadUtil.downLoadFile(filePath, response, fileName, fileType);
	 }
	
	/**
	 * 导入excel表中的小区数据
	 * @return
	 */
	public String importExcel(){
		Map<String, String> result = communityManager.saveBatchImportCommunity(file, fileName);
		if("1".equals(result.get("result_code"))){
			super.json = "{result:1,message:'"+result.get("result_message")+"'}";
		}else if("0".equals(result.get("result_code"))){
			super.json = "{result:0,message:'"+result.get("result_message")+"'}";
		}else{
			super.json = "{result:-1,message:'"+result.get("result_message").substring(16, result.get("result_message").length())+"'}";
		}
		return super.JSON_MESSAGE;
	}
	
	/**
	 * 跳转到商品关联小区页面,加载商品与小区关系
	 * @return
	 */
	public String toGoodsCommunity(){
		if(activity == null){
			activity = new CommunityActivity();
		}
		this.webpage = communityManager.GoodsCommunityList(activity,this.getPage(), this.getPageSize());
		return "to_goods_community_real";
	}
	
	/**
	 * 商品关联页面点击添加商品弹出商品选择页面
	 * @return
	 */
	public String getGoodsList(){
		this.webpage = communityManager.getGoodsList(goods_name,this.getPage(), this.getPageSize());
		return "to_goods";
	}
	
	/**
	 * 添加商品与小区关系
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String add_commounity_goods_real() {
		//json = com.alibaba.fastjson.JSONArray.
		JSONArray jsonArray = JSONArray.fromObject(listParams);
		List<Map<String,String>> listofjson = (List) JSONArray.toCollection(jsonArray, Map.class);
		String rsp_s = null;
		String errmessage = "添加失败！";
		try {
			rsp_s = communityManager.add_commounity_goods_real(listofjson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errmessage = e.getMessage().toString();
			errmessage = errmessage.substring(16, errmessage.length());
		} finally {
			if (StringUtils.isBlank(rsp_s)) {
				json = "{'result':1,'message':'" + errmessage + "'}";
			} else {
				json = "{'result':0, 'message':'" + rsp_s + "'}";
			}
		}
		return JSON_MESSAGE;
	}
	
	/**
	 * 删除商品与小区关系
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String del_commounity_goods_real() {
		 JSONObject jsonObject = JSONObject.fromObject(mapParams);
	     Map<String, Object> mapJson = JSONObject.fromObject(jsonObject);
		String rsp_s = null;
		String errmessage = "删除失败！";
		try {
			rsp_s = communityManager.del_commounity_goods_real(mapJson);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errmessage = e.getMessage().toString();
			errmessage = errmessage.substring(16, errmessage.length());
		} finally {
			if (StringUtils.isBlank(rsp_s)) {
				json = "{'result':1,'message':'" + errmessage + "'}";
			} else {
				json = "{'result':0, 'message':'" + rsp_s + "'}";
			}
		}
		return JSON_MESSAGE;
	}
	
}
