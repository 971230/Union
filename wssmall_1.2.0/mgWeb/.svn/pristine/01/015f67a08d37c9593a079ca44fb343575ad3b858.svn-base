<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/WEB-INF/html.tld" prefix="html" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/taglibs.jsp"%>
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/core/admin/auth/checktree.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/easyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/ress/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
$(this.body).css("min-width","")
</script>
</head>
<body class="easyui-layout">

<div region="center"  style="padding:2px;height:900px;">
    

     <table id="region_list" url="commonRegionAction!getRootRegion.do?ajax=yes" idField="region_id" id="sss" treeField="region_name"  style="height:300px;">
		        <thead>
		        <tr>
		            <th field="region_name"  align="center" >区域名称</th>
		            <th field="region_desc"  align="center" >区域描述</th>
		            <th field="region_code"  align="center" >区域编码</th>
		            <script >
		            var width = (document.body.clientWidth-10)/3;
		            $("#region_list tr th").attr("width",width);
		            </script>
		        </tr>
		        </thead>
	 </table>
	 <div class="searchformDiv">
		<a id="btn_add" style="margin-right:10px;" class="graybtn1"><span>添加下级区域</span></a>
		<a id="btn_mod" style="margin-right:10px;" class="graybtn1"><span>修改</span></a>
		<a id="btn_del" style="margin-right:10px;" class="graybtn1"><span>删除</span></a>
	 </div>
	 <div class="searchformDiv">
    	<table width="100%" cellspacing="0" cellpadding="0" border="0" id="sss">
    	   <tr>
    	      <th>上级区域</th>
    	      <td>
    	      	<input type="hidden" id="parent_region_id" />
    	      	<input type="text" class="ipttxt" style="width:200px;" disabled="true"  id="parent_region_name" class="searchipt" />
    	      </td>
    	   
    	      <th>区域名称</th>
    	      <td>
    	      	<input type="hidden" id="region_id" />
    	      	<input type="text" class="ipttxt"  class="searchipt" disabled="true" style="width:200px;" id="region_name" /> 
    	      </td>
    	   	 
    	   	  <th>区域编码</th>
    	   	  <td><input type="text" class="ipttxt" style="width:200px;" disabled="true" id="region_code" class="searchipt"/></td>
    	      
    	       </tr>
    	   <tr>
    	   	  <th>区域类型</th>
    	      <td>
    	         <input type="hidden" id="region_level">
    	         <input type="text" style="width:200px;" disabled="true" id="region_level_desc" class="ipttxt">
    	      </td>
			  <th>区域描述</th>
  	      	<td colspan="2"><input type="text" style="width:200px;" disabled="true" id="region_desc" class="ipttxt"></td>
  	     
		  
  	    </table>
	    </div>
	    <div class="searchformDiv" align="center" id="searchformDiv">
		    <a id="btn_confirm" style="align:right;" class="graybtn1"><span>确定</span></a>
			<a id="btn_cancel"  style="margin-right:50px;" class="graybtn1"><span>取消</span></a>
		</div>
</div>
	   
</div>

</body>
</html>

<script type="text/javascript" src="../region/js/region_tree.js"></script>