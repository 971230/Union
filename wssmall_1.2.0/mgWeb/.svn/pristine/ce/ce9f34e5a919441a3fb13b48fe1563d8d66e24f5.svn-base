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
<style type="text/css">
.contentPanel{ position:relative; padding-left:350px;}
.contentPanel .leftPanel{ width:350px; padding:2px; position:absolute; left:0px; top:0px;}
.contentPanel .rightPanel{ min-height:600px;}
body {
    background: url("../images/mainbg.gif") repeat-y scroll 0 0 rgba(0, 0, 0, 0);
    font-family: Arial,Helvetica,sans-serif;
   
}
</style>
<script type="text/javascript">
$(this.body).css("min-width","")
</script>

</head>
<body class="easyui-layout">

<div region="center" >
	
    <div id="leftPanle" class="contentPanel" >
        <div class="leftPanel">
           <table id="org_list" url="orgAdmin!getCurrOrg.do?ajax=yes" idField="party_id" treeField="org_name" style="height:580px; width:350px">
		        <thead>
		        <tr>
		            <th field="org_name" rowspan="2"  align="center" width="200">组织名称</th>
		            <th field="org_type_name"  align="center" width="150">组织类型</th>
		        </tr>
		        </thead>
		    </table>
        </div>
	        <div  class='rightPanel'>
	           <div style="height:380px;" id="partListDiv">
	               <iframe id ="partListIframe" style='width:100%;height:100%;' src="<%=request.getContextPath() %>/core/admin/org/orgAdmin!partList.do?parent_party_id="></iframe>
	           </div>
		       <div class="comBtnDiv">
						<a id="btn_add" style="margin-right:10px;" class="graybtn1"><span>添加下级组织</span></a>
						<a id="btn_mod" style="margin-right:10px;" class="graybtn1"><span>修改</span></a>
						<a id="btn_del" style="margin-right:10px;" class="graybtn1"><span>删除</span></a>
			   </div>
	           <div class="user_msg_table" style="margin-left:2px" id="user_detail_table">
	              <form method ="post" id="user_detail_form">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tbody>
							<tr>
							  <th>上级组织</th>
				    	      <td>
				    	      	<input type="hidden" id="parent_party_id" />
				    	      	<input type="text" class="ipttxt"  disabled="true"  id="parent_org_name"  />
				    	      </td>
				    	      <th>统一渠道编码</th>
				  	      	   <td><input type="text"  disabled="true" id="union_org_code" class="ipttxt">
				  	           </td>
				  	           
				    	   	  <th>组织编码</th>
				    	   	  <td><input type="text" class="ipttxt" disabled="true" id="org_code" /></td>
				    	      
							</tr>
							<tr>
					    	      <th>组织名称</th>
					    	      <td>
					    	      	<input type="hidden" id="party_id" />
					    	      	<input type="text" class="ipttxt"   disabled="true"  id="org_name" />
				                  </td>
				                  
					    	   	  <th>组织类型</th>
					    	   	  <td>
					    	   	    <!-- <select class="ipttxt" style="width:150px" ><option>--请选择--</option></select> -->
					    	   	    <input class="ipttxt" disabled="true" id ="org_type_name"> 
					    	   	  </td>
					    	   	  <th>组织简介</th>
					    	   	  <td>
					    	   	    <input type="text" class="ipttxt"  id="org_content"  disabled="true"  id="org_name" />
					    	   	  </td>
					    	   	  
					  	     </tr>
					  	     <tr>
					  	          <th>本地网</th>
				                  <td id="showLan">
				                   <select id="lan_sel" style="width:145px"  disabled="true" class="ipttxt"></select>
				                   <!-- <input type="text" class="ipttxt"   disabled="true"  id="lan_name" /> -->
					               <!-- <input type="hidden" id ="lan_id">  -->
				                   </td>
					    	      <th>辖区</th>
					    	      <td>
					    	        <select id="region_sel" style="width:145px"  disabled="true" class="ipttxt"</select>
					    	      	<!-- <input type="text" class="ipttxt" disabled="true"  id="lan_region"/> -->
				                  </td>
					    	   	  <th>区域</th>
					    	   	  <td>
					    	   	    <input type="text" class="ipttxt"   disabled="true"  id="lan_area" />
					    	   	  </td>
					    	   	 
					  	     </tr>
					  	     <tr>
					  	      
					  	     </tr>
					    </tbody>
			         </table>
			         </form>
			   </div>
	          
	  	       <div class="searchformDiv" style="border:0">
	  	             <a id="btn_confirm" style="margin-left:40%" class="graybtn1"><span>确定</span></a>
		             <a id="btn_cancel"  style="margin-left:5px;" class="graybtn1"><span>取消</span></a>
	  	       </div>
	  	     
        </div>
    </div>
   
</div>

</body>
</html>
<script type="text/javascript" src="js/orgtree.js"></script>