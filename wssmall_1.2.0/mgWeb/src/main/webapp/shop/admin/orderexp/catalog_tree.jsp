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


</head>
<body class="easyui-layout" style="height:100%;" border="0">
<div region="center" >
	
    <table catalog_name="${catalog_name }" flag="${flag }" catalog_code="${catalog_code }" id="catalog_list" url="<%=request.getContextPath() %>/shop/admin/esearchCatalog!qryCatalogTreeList.do?ajax=yes" idField="catalog_id" treeField="catalog_name" style="width:205;height:520px;" border=0>
        <thead>
        <tr>
            <th field="catalog_id" rowspan="2" align="center" hidden="true"></th> 
            <th field="catalog_name" width="200" align="left">归类管理</th>
        </tr>
        </thead>
    </table>
    
</div>
</body>
</html>
<script type="text/javascript" src="<%=request.getContextPath()%>/shop/admin/orderexp/js/catalog_tree.js"></script>