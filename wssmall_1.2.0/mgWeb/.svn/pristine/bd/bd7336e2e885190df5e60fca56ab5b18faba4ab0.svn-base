<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/public/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" media="screen" href="auth/checktree.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/public/easyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/public/common/ress/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/public/easyui/locale/easyui-lang-zh_CN.js"></script>


</head>
<body class="easyui-layout">	
<div region="center" style="padding:2px;height:700px;">
	 <div class="searchformDiv">
		<a id="btn_add" style="margin-right:10px;" class="graybtn1"><span>添加</span></a>
		<a id="btn_mod" style="margin-right:10px;" class="graybtn1"><span>修改</span></a>
		<a id="btn_del" style="margin-right:10px;" class="graybtn1"><span>删除</span></a>
		
	</div>
	<div>
    <table id="org_list" url="goodsOrg!getCurrOrg.do?ajax=yes" idField="party_id" treeField="org_name" style="height:300px;">
        <thead>
        <tr>
            <th field="party_id" width="100" align="center" hidden="true">发布区域ID</th>
             
            <th field="org_name" rowspan="2" width="300" align="center">销售组织名称</th>
            <th field="org_code" width="150" align="center">销售组织编码</th>
           
            <th field="lan_name" width="100" align="center" >平台名称</th>
            <th field="city_name" width="100" align="center" >商城名称</th>
            <th field="parent_party_id" width="100" align="center" hidden="true">上级组织ID</th>
            <th field="parent_org_name" width="100" align="center" hidden="true">上级销售组织名称</th>
            <th field="org_type_id" width="100" align="center" hidden="true">组织类型ID</th>
            
            <th field="org_type_name" width="100" align="center" hidden="true">发布区域类型</th>
           
            <th field="status_cd" width="80" align="center" >状态</th>
           
            <th field="org_content" width="220" align="center" hidden="true">发布区域简介</th>
            <th field="union_org_code" width="150" align="center" hidden="true">统一渠道编码</th>
        </tr>
        </thead>
    </table>
    </div>
    <div class="searchformDiv" >
    	<table width="100%" cellspacing="0" cellpadding="0" border="0" >
    	   <tr>
    	   	  <th>销售组织编码</th>
    	   	  <td><input type="text" class="ipttxt" style="width:200px;" disabled="true" id="org_code" class="searchipt"/></td>
    	      
    	      <th>销售组织名称</th>
    	      <td>
    	      	<input type="hidden" id="party_id" />
    	      	<input type="text" class="ipttxt"  class="searchipt" disabled="true" style="width:200px;" id="org_name" /> </td>
    	   	  
    	   </tr>
    	   <tr>
    	   	  <th>发布区域类型</th>
    	      <td>
    	      		<html:selectdict appen_options="<option value=''>--全部--</option>" style="width:200px;" id="org_type_id" name="org_type_id" attr_code="DC_ORG_TYPE"></html:selectdict>
			  </td>
			  <th>上级发布区域</th>
    	      <td>
    	      	<input type="hidden" id="parent_party_id" />
    	      	<input type="text" class="ipttxt" style="width:200px;" disabled="true"  id="parent_org_name" class="searchipt" /></td>
			 <!--   <th>统一渠道编码</th>
  	      	<td colspan="2"><input type="text" style="width:200px;" disabled="true" id="union_org_code" class="ipttxt"></td>
  	      -->
  	      </tr>
  	     <!-- 
  	        <tr>
    	   	  <th>地市</th>
    	      <td>
    	      		<input type="text" style="width:200px;" disabled="true" id="lan_name" class="ipttxt">
			  </td>
			  <th>区县</th>
  	      	<td colspan="2"><input type="text" style="width:200px;" disabled="true" id="city_name" class="ipttxt"></td>
  	      </tr>
  	      -->
  	      <tr>
  	      	  <th>发布区域简介</th>
			  <td colspan="3">
			  	<input type="text" class="ipttxt" disabled="true" id="org_content" style="width:400px;" />
			  </td>
		  </tr>
		  
  	    </table>
    </div>
    <div class="searchformDiv" align="center">
	    <a id="btn_confirm" style="align:right;" class="graybtn1"><span>确定</span></a>
		<a id="btn_cancel"  style="margin-right:50px;" class="graybtn1"><span>取消</span></a>
	</div>
</div>

</body>
</html>
<script type="text/javascript" src="js/goodstree.js"></script>