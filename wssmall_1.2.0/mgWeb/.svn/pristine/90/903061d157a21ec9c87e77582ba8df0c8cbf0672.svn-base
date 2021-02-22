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
.contentPanel{ position:relative; padding-left:260px;}
.contentPanel .leftPanel{ width:260px; padding:2px; position:absolute; left:0px; top:0px;}
.contentPanel .rightPanel{ min-height:600px;}

.tab_li{
	border: 1px solid #cdcdcd;
    background: #f8f8f8;
    padding: 2px 6px;
    color: #444;
    float: left;
    width: 210px;
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

<script type="text/javascript">
$(this.body).css("min-width","")
</script>

</head>
<body class="easyui-layout" style='border:0px;'>

<div region="center" style='border:0px;'>
	
    <div class="contentPanel"  style='padding-left:460px;'>
    	<!-- 组织树 -->
        <div class="leftPanel" style='width:460px;'>
        	<div>
	        	<ul style='white-space: nowrap'>
	        		<li id="org_tree_li" class="tab_li tab_li_select" onclick="doTabSelect('1');"><span style="font-size: 130%;text-align: center;">组织树</span></li>
	        		<li id="org_search_li" class="tab_li" onclick="doTabSelect('2');"><span style="font-size: 130%;text-align: center;">组织查询</span></li>
	        	</ul>
        	</div>
        
        	<div id="div_org_tree">
        		<table id="org_list" url="orgAdmin!getCurrOrg.do?ajax=yes" idField="party_id" treeField="org_name" style="height:800px; width:450px;">
			        <thead>
			        <tr>
			            <th field="org_name" rowspan="2"  align="left" width="350">组织名称</th>
			            <th field="org_type_name"  align="left" width="100">组织类型</th>
			        </tr>
			        </thead>
			    </table>
        	</div>
        	<div id="div_org_search" style="display: none;border: 1px solid #99BBE8;margin-top: 30px;width: 450px;height:800px;">
        		<iframe id ="searchOrgFrame" style='width:100%;height:100%;' src="<%=request.getContextPath() %>/core/admin/org/orgAdmin!searchOrg.do"></iframe>
        	</div>
           
        </div>
        
        <!-- 组织信息 -->
        <div  class='rightPanel' style="margin-top: 10px;">
	       <div class="comBtnDiv">
					<a id="btn_add" style="margin-right:10px;cursor: pointer;" class="graybtn1"><span>添加下级组织</span></a>
					<a id="btn_mod" style="margin-right:10px;cursor: pointer;" class="graybtn1"><span>修改</span></a>
					<a id="btn_del" style="margin-right:10px;cursor: pointer;" class="graybtn1"><span>删除</span></a>
		   </div>
		   
           <div class="input" style="margin-left:2px" id="div_org_info">
              <form method ="post" id="from_org_info">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tbody>
						<tr height="50px;">
				  	     	<td style="text-align: center;font-size: 140%;" colspan="6">
				  	     		<span>基本信息</span>
				  	     	</td>
						 </tr>
						
						<tr>
							<th>组织编码</th>
			    	   	  	<td>
			    	   	  		<input type="text" class="ipttxt" disabled="true" id="party_id" style='width:120px' />
			    	   	  	</td>
			    	   	  	
			    	   	  	<th>组织名称</th>
							<td>
								<input type="hidden" id="org_code" />
				    	      	<input type="text" class="ipttxt"   disabled="true"  id="org_name"  style='width:120px'/>
				    	      	<span class="red_mark">*</span>
							</td>
							
							<th>组织类型</th>
							<td>
								<input type="hidden" id="org_level" />
				    	   	    <select id="org_type_id" style='width:125px' disabled="true" class="ipttxt"></select>
				    	   	    <span class="red_mark">*</span>
							</td>
						</tr>
						<tr>
							<th>组织简介</th>
							<td>
				    	   	    <input type="text" class="ipttxt"  id="org_content"  disabled="true" style='width:120px'/>
							</td>
							
							<th>上级组织编号</th>
							<td>
				    	   	    <input type="text" class="ipttxt"  id="parent_party_id"  disabled="true" style='width:120px'/>
				    	   	    <span class="red_mark">*</span>
							</td>
							
			                <th>上级组织名称</th>
			    	      	<td>
			    	      		<input type="text" class="ipttxt"  disabled="true"  id="parent_org_name" style='width:120px' />
			    	      		<span class="red_mark">*</span>
			    	      	</td>
				  	     </tr>
				  	     <tr>
				  	     	<th>统一渠道编码</th>
			  	      	   	<td>
			  	      	   		<input type="text"  disabled="true" id="union_org_code" class="ipttxt" style='width:120px'>
			  	           	</td>
			  	           	
							<th>本地网</th>
							<td id="showLan">
								<select id="lan_sel" style='width:125px'  disabled="true" class="ipttxt"></select>
								<span class="red_mark">*</span>
							</td>
							
							<th>辖区</th>
							<td>
								<select id="region_sel" style='width:125px' disabled="true" class="ipttxt"></select>
								<span class="red_mark">*</span>
							</td>
				  	     </tr>
				  	     <tr>
				  	     	<th>区域</th>
							<td>
				    	   	    <input type="text" class="ipttxt"   disabled="true"  id="lan_area" style='width:120px'/>
							</td>
							
				  	        <th>经营渠道类型</th>
				  	        <td>
				  	        	<select id="channel_type" style="width:125px" disabled="true" class="ipttxt"></select>
				  	        	<span class="red_mark">*</span>
				  	        </td>
				  	        
				  	        <th>经营渠道</th>
				  	        <td>
				  	        	<select id="channel_type_id" style="width:125px" disabled="true" class="ipttxt"></select>
				  	        	<span class="red_mark">*</span>
				  	        </td>
				  	     </tr>
				  	     
						 
					 	 <tr height="50px;" class="tr_syn_info">
				  	     	<td style="text-align: center;font-size: 140%;" colspan="6">
				  	     		<span>同步信息</span>
				  	     	</td>
						 </tr>
						 <tr class="tr_syn_info">
						 	<th>总部编码</th>
			    	      	<td>
			    	      		<input type="text" class="ipttxt" id="hq_dept_id" disabled="true" style='width:120px' />
			    	      	</td>
							
							<th>省内编码</th>
							<td>
				    	   	    <input type="text" class="ipttxt" id="dept_id" disabled="true" style='width:120px'/>
							</td>
							
			                <th>名称</th>
							<td>
				    	   	    <input type="text" class="ipttxt" id="dept_name" disabled="true" style='width:120px'/>
							</td>
				  	     </tr>
				  	     
				  	     <tr class="tr_syn_info">
							
							<th>等级编码</th>
							<td>
				    	   	    <input type="text" class="ipttxt" id="dept_lvl" disabled="true" style='width:120px'/>
							</td>
							
			                <th>地市编码</th>
			    	      	<td>
			    	      		<input type="text" class="ipttxt" id="areaid" disabled="true" style='width:120px' />
			    	      	</td>
			    	      	
			    	      	<th>县区编码</th>
							<td>
				    	   	    <input type="text" class="ipttxt" id="countyid" disabled="true" style='width:120px'/>
							</td>
				  	     </tr>
				  	     
				  	     <tr class="tr_syn_info">
							<th>上级组织总部编码</th>
							<td>
				    	   	    <input type="text" class="ipttxt" id="zb_admin_depart_code" disabled="true" style='width:120px'/>
							</td>
							
			                <th>渠道类型</th>
			    	      	<td>
			    	      		<input type="text" class="ipttxt" id="syn_channel_type" disabled="true" style='width:120px' />
			    	      	</td>
			    	      	
			    	      	<th>渠道子类型</th>
							<td>
				    	   	    <input type="text" class="ipttxt" id="syn_sub_type" disabled="true" style='width:120px'/>
							</td>
				  	     </tr>
				  	     
				  	     <tr class="tr_syn_info">
							<th>省内六级分类</th>
							<td>
				    	   	    <input type="text" class="ipttxt" id="type_id" disabled="true" style='width:120px'/>
							</td>
							
			                <th>总部渠道类型</th>
			    	      	<td>
			    	      		<input type="text" class="ipttxt" id="chnl_kind_id" disabled="true" style='width:120px' />
			    	      	</td>
				  	     </tr>
				  	     
				  	     <tr class="tr_syn_info">
				  	     	<th>同步方式</th>
							<td>
				    	   	    <select id="is_syn" style='width:125px' disabled="true" class="ipttxt">
				    	   	    	<option value="0">非同步组织</option>
				    	   	    	<option value="1">新增同步</option>
				    	   	    	<option value="2">合并同步</option>
				    	   	    </select>
							</td>
							
				  	     	<th>最后同步时间</th>
							<td>
				    	   	    <input type="text" class="ipttxt" id="syn_date" disabled="true" style='width:120px'/>
							</td>
				  	     </tr>
				  	     
						 
				    </tbody>
		         </table>
		         </form>
		   </div>
          
  	       <div class="searchformDiv" style="border:0;display: none;">
  	             <a id="btn_confirm" style="margin-left:40%;cursor: pointer;" class="graybtn1"><span>确定</span></a>
	             <a id="btn_cancel"  style="margin-left:5px;cursor: pointer;" class="graybtn1"><span>取消</span></a>
  	       </div>
	  	     
        </div>
    </div>
   
</div>

</body>
</html>
<script type="text/javascript" src="js/orgtree.js"></script>
<script type="text/javascript">
var chl_type_id =""; 
$(function(){
	$("#channel_type").attr("disabled",true);
});		
</script>

