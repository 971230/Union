package com.ztesoft.net.action;

import java.io.File;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;







import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ztesoft.common.util.SequenceTools;
import com.ztesoft.form.util.StringUtil;
import com.ztesoft.net.framework.action.WWAction;
import com.ztesoft.net.model.EsearchExpInstSolution;
import com.ztesoft.net.param.inner.EsearchCatalogSolutionInner;
import com.ztesoft.net.param.inner.EsearchExpInstSolutionInner;
import com.ztesoft.net.param.outer.EsearchExpInstSolutionOuter;
import com.ztesoft.net.service.IExpConfigManager;

/**
 * 解决方案管理
 * @author Lin.xuerui
 */

public class SolutionAction extends WWAction {

	private static final long serialVersionUID = -2574930813542065056L;
	
	private IExpConfigManager expConfigManager;
	private EsearchCatalogSolutionInner solutionInner;
	private List<EsearchExpInstSolution> returnList;
	private String solution_id;
	private File fileName;//上传文件
	private String fileNameFileName;
	private String uploadFlag; //文件是否上传
	private String uploadPath; //文件上传路径
	private String uploadValue; //区分是新增长传 1    修改上传 0
	private String newView;
	private String returnView;
	private String addValue;
	private String rel_obj_id;//关联实例id
	public String uploadFile(){
		try{
			fileNameFileName = URLDecoder.decode(fileNameFileName,"UTF-8");
			String fileType = fileNameFileName.substring(fileNameFileName.lastIndexOf(".")+1,fileNameFileName.length());
			if(fileType.equals("doc") || fileType.equals("txt") || fileType.equals("docx")  ){
				params.put("fileName", SequenceTools.getdefualt22PrimaryKey() + "." + fileType);
				//上传文件
				String[] rtnmsg = this.expConfigManager.uploadFile(fileName, params);
				if(rtnmsg[0].equals("success")){
					uploadFlag = "上传文件成功！";
					uploadPath = rtnmsg[1];
				}else{
					uploadFlag = "上传文件失败！！";
					uploadPath = rtnmsg[1];
				}
			}else{
				uploadFlag = "请选择 （.doc / .txt / .docx） 格式文件上传！！";
			}
		}catch(Exception e){
			e.printStackTrace();
			this.json = "{'errorCode':1, 'errorDesc':'上传文件失败！'}";
			uploadFlag = "上传文件失败！！";
		}
		String rt = "toUpload";
		//修改页面
		if(!addValue.equals("1")){
			EsearchExpInstSolutionInner inner = new EsearchExpInstSolutionInner();
			inner.setSolution_id(solution_id);
			EsearchExpInstSolutionOuter solutionOuterSign = this.expConfigManager.queryEsearchExpInstSolutionById(inner);
			returnList = solutionOuterSign.getList();
			
			uploadValue = "0";
			returnView = "0";  //上传更新
			rt = "update";
		}
		return rt;
	}
	
	public String fileError(){
		return "fileError";
	}
	
	public String list(){
		if(page == 0){
			page = 1;
		}
		if(pageSize == 0){
			pageSize = 10;
		}
		EsearchExpInstSolutionInner inner = new EsearchExpInstSolutionInner();
		inner.setPageIndex(page);
		inner.setPageSize(pageSize);
		if(null != solutionInner){
			inner.setSolution_desc(solutionInner.getSolution_desc());
			inner.setSolution_name(solutionInner.getSolution_name());
			inner.setSolution_type(solutionInner.getSolution_type());
		}
		EsearchExpInstSolutionOuter solutionOuter = this.expConfigManager.queryEsearchExpInstSolution(inner);
		List<Map> ss = solutionOuter.getPage().getResult();
		for(int i=0;i<ss.size();i++){
			Map nn = ss.get(i);
			int ll = nn.get("solution_desc").toString().length();
			if(ll > 40){
				nn.put("solution_desc", (nn.get("solution_desc").toString().substring(0,40)+"..."));
			}
		}
		webpage = solutionOuter.getPage();
		return "index";
	}

	public String select(){
		if(page == 0){
			page = 1;
		}
		if(pageSize == 0){
			pageSize = 10;
		}
		EsearchExpInstSolutionInner inner = new EsearchExpInstSolutionInner();
		inner.setSolution_id(solution_id);
		inner.setPageIndex(page);
		inner.setPageSize(pageSize);
		EsearchExpInstSolutionOuter solutionOuter = this.expConfigManager.queryEsearchExpInstSolution(inner);
		List<Map> ss = solutionOuter.getPage().getResult();
		for(int i=0;i<ss.size();i++){
			Map nn = ss.get(i);
			int ll = nn.get("solution_desc").toString().length();
			if(ll > 40){
				nn.put("solution_desc", (nn.get("solution_desc").toString().substring(0,40)+"..."));
			}
		}
		webpage = solutionOuter.getPage();
		return "index";
	}
	
	@SuppressWarnings("static-access")
	public String del(){
		try{
			EsearchCatalogSolutionInner delInner = new EsearchCatalogSolutionInner();
			delInner.setSolution_id(solution_id);
			this.expConfigManager.deleteEsearchCatalogSolution(delInner);
			this.json = "{'errorCode':0, 'errorDesc':'删除成功！'}";
		}catch(Exception e){
			this.json = "{'errorCode':1, 'errorDesc':'删除失败！'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	@SuppressWarnings("static-access")
	public String add(){
		try{
			//校验sql的解决方案中的SQL
			Map map = this.validateSolution(solutionInner);
			if("1".equals(map.get("errorCode"))){
				this.json = "{'errorCode':1, 'errorDesc':'"+map.get("errorDesc")+"'}";
				return WWAction.JSON_MESSAGE;
			}
			
			this.expConfigManager.addEsearchCatalogSolution(solutionInner);
			this.json = "{'errorCode':0, 'errorDesc':'新增成功！'}";
		}catch(Exception e){
			this.json = "{'errorCode':1, 'errorDesc':'新增失败！'}";
		}
		return WWAction.JSON_MESSAGE;
	}

	
	@SuppressWarnings("static-access")
	public String update(){
		try{
			//校验sql的解决方案中的SQL
			Map map = this.validateSolution(solutionInner);
			if("1".equals(map.get("errorCode"))){
				this.json = "{'errorCode':1, 'errorDesc':'"+map.get("errorDesc")+"'}";
				return WWAction.JSON_MESSAGE;
			}
			
			this.expConfigManager.updateEsearchCatalogSolution(solutionInner);
			this.json = "{'errorCode':0, 'errorDesc':'修改成功！'}";
		}catch(Exception e){
			this.json = "{'errorCode':1, 'errorDesc':'修改失败！'}";
		}
		return WWAction.JSON_MESSAGE;
	}
	
	
	//校验sql的解决方案中的SQL语句
	private Map validateSolution(EsearchCatalogSolutionInner inner){
		Map map = new HashMap();
		if("SQL".equals(inner.getSolution_type())){
			if(null != inner.getSolution_value()){
				Matcher m = Pattern.compile("^update.*|^UPDATE.*|^INSERT.*|^insert.*").matcher(inner.getSolution_value());
				if(!m.find()){
					map.put("errorCode", "1");
					map.put("errorDesc", "请输入更新或插入语句！");
					return map;
				}
				if(inner.getSolution_value().indexOf("join") > 0 || inner.getSolution_value().indexOf("JOIN") > 0){
					map.put("errorCode", "1");
					map.put("errorDesc", "sql语句中不能用JOIN!");
					return map;
				}
			}
		}
		map.put("errorCode", "0");
		map.put("errorDesc", "校验通过！");
		
		return map;
	}
	
	public String toUpdate(){
		try{
			EsearchExpInstSolutionInner inner = new EsearchExpInstSolutionInner();
			inner.setSolution_id(solution_id);
			EsearchExpInstSolutionOuter solutionOuterSign = this.expConfigManager.queryEsearchExpInstSolutionById(inner);
			returnList = solutionOuterSign.getList();
			uploadValue = "0";
			if(newView.equals("1")){ //页面点击的修改
				returnView = newView;
			}else{
				returnView = "0";  //上传更新
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return "update";
		
	}
	
	public String view(){
		try{
			EsearchExpInstSolutionInner inner = new EsearchExpInstSolutionInner();
			inner.setSolution_id(solution_id);
			EsearchExpInstSolutionOuter solutionOuterSign = this.expConfigManager.queryEsearchExpInstSolutionById(inner);
			returnList = solutionOuterSign.getList();
			/*for(int i=0;i<returnList.size();i++){
				EsearchExpInstSolution sfs = returnList.get(i);
				if(sfs.getSolution_type().equals("DOC")){
					String value = sfs.getSolution_value();
					sfs.setSolution_value(value.substring(value.lastIndexOf("\\")+1,value.length()));
				}
			}*/
			EsearchExpInstSolution sfs = returnList.get(0);
			if(sfs.getSolution_type().equals("DOC")){
				String value = sfs.getSolution_value();
				sfs.setSolution_value(value.substring(value.lastIndexOf("\\")+1,value.length()));
			}
			//异常单页面调用处理
			if(!StringUtil.isEmpty(rel_obj_id)){
				if("URL".equals(sfs.getSolution_type()) || "DEFAULT".equals(sfs.getSolution_type())){
//					String url = BeanUtils.urlAddToken(sfs.getSolution_value(), ManagerUtils.getAdminUser().getUsername());
					String url = sfs.getSolution_value().replace("{rel_obj_id}", rel_obj_id);
					sfs.setSolution_value(url);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return "view";
	}
	
	
	public String toAdd(){
		uploadFlag = "";
		return "toAdd";
	}
	
	public IExpConfigManager getExpConfigManager() {
		return expConfigManager;
	}

	public void setExpConfigManager(IExpConfigManager expConfigManager) {
		this.expConfigManager = expConfigManager;
	}

	public EsearchCatalogSolutionInner getSolutionInner() {
		return solutionInner;
	}

	public void setSolutionInner(EsearchCatalogSolutionInner solutionInner) {
		this.solutionInner = solutionInner;
	}

	public List<EsearchExpInstSolution> getReturnList() {
		return returnList;
	}

	public void setReturnList(List<EsearchExpInstSolution> returnList) {
		this.returnList = returnList;
	}

	public String getSolution_id() {
		return solution_id;
	}

	public void setSolution_id(String solution_id) {
		this.solution_id = solution_id;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getUploadValue() {
		return uploadValue;
	}

	public void setUploadValue(String uploadValue) {
		this.uploadValue = uploadValue;
	}

	public String getUploadFlag() {
		return uploadFlag;
	}

	public void setUploadFlag(String uploadFlag) {
		this.uploadFlag = uploadFlag;
	}

	public String getFileNameFileName() {
		return fileNameFileName;
	}

	public void setFileNameFileName(String fileNameFileName) {
		this.fileNameFileName = fileNameFileName;
	}
    public File getFileName() {
		return fileName;
	}

	public void setFileName(File fileName) {
		this.fileName = fileName;
	}
	
	private Map<String, String> params = new HashMap<String, String>(0);
	
	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public String getReturnView() {
		return returnView;
	}

	public void setReturnView(String returnView) {
		this.returnView = returnView;
	}
	
	public String getNewView() {
		return newView;
	}

	public void setNewView(String newView) {
		this.newView = newView;
	}

	public String getAddValue() {
		return addValue;
	}

	public void setAddValue(String addValue) {
		this.addValue = addValue;
	}

	public String getRel_obj_id() {
		return rel_obj_id;
	}

	public void setRel_obj_id(String rel_obj_id) {
		this.rel_obj_id = rel_obj_id;
	}
	
	
}
