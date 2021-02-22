<%@ page contentType="text/html; charset=utf-8" language="java" pageEncoding="utf-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath() %>/auth/checktree.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/public/easyui/themes/icon.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/public/common/ress/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/public/easyui/locale/easyui-lang-zh_CN.js"></script>
<head>
</head>
<body class="easyui-layout" style="height:100%;">
<div region="center" style="padding:2px;height:100%;">
	
    <table id="org_list" url="<%=request.getContextPath() %>/shop/admin/task!getCurrOrg.do?ajax=yes" idField="party_id" treeField="org_name" style="width:212%;height:520px;">
        <thead>
	        <tr>
	           <!--  <th  field="party_id" rowspan="2" width="100" align="center" hidden>组织ID</th>  -->
	            <th field="org_name" width="208%" align="left">组织名称</th>
	            <th field="party_state" hidden="true">party_state</th>
	            <th field="is_area" hidden="true">is_area</th>
	            <th field="lan_id" hidden="true">lan_id</th>
	            <th field="region_id" hidden="true">region_id</th>
	        </tr>
        </thead>
    </table>
    
</div>
</body>

<script type="text/javascript" src="<%=request.getContextPath() %>/shop/admin/task/js/orgtree.js"></script>