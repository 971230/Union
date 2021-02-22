<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/commons/taglibs.jsp" %>
<title></title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/core/admin/auth/checktree.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/easyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/publics/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/core/admin/user/orgUser/orgtree.js"></script>

<style type="text/css">

.tab_ul{
    white-space: nowrap;
    list-style-type: none;
    -webkit-margin-before: 0px;
    -webkit-margin-after: 0px;
    -webkit-margin-start: 0px;
    -webkit-margin-end: 0px;
    -webkit-padding-start: 0px;
    width: 450px;
}

.tab_li{
	border: 1px solid #cdcdcd;
    background: #f8f8f8;
    padding: 2px 6px;
    color: #444;
    float: left;
    width: 200px;
    border-top-left-radius:10px;
    border-top-right-radius:10px;
    text-align: center;
    cursor: pointer;
    list-style: none;
}

.tab_li_select{
	background: #c3c3c3;
}

.red_mark{
	color: red;
}

</style>

</head>
<body class="easyui-layout" style="height:100%;" border="0">
<div region="center" style='border:0px;'>
	<div class="leftPanel" style='width:450px;'>
		<div>
	       	<ul class="tab_ul">
	       		<li id="org_tree_li" class="tab_li tab_li_select" onclick="doTabSelect('1');"><span style="font-size: 130%;text-align: center;">组织树</span></li>
	       		<li id="org_search_li" class="tab_li" onclick="doTabSelect('2');"><span style="font-size: 130%;text-align: center;">组织查询</span></li>
	       	</ul>
		</div>
		
		<div id="div_org_tree">
			<table id="org_list" url="<%=request.getContextPath() %>/core/admin/user/userAdmin!getCurrOrg.do?ajax=yes" idField="party_id" treeField="org_name" style="width:430px;height:700px;border: 1px solid #99BBE8;" >
		        <thead>
		        <tr>
		            <th  field="party_id" rowspan="2" align="center" hidden="true"></th> 
		            <th field="org_name" rowspan="2"  align="left" width="330">组织名称</th>
					<th field="org_type_name"  align="left" width="100">组织类型</th>
		        </tr>
		        </thead>
		    </table>
		</div>
	    <div id="div_org_search" style="display: none;width: 430px;height:700px;border: 1px solid #99BBE8;">
			<iframe id ="searchOrgFrame" style='width:100%;height:100%;border-width:0px;' src="<%=request.getContextPath() %>/core/admin/org/orgAdmin!searchOrg.do"></iframe>
		</div>
    </div>
</div>
</body>


<script type="text/javascript">

</script>

</html>

