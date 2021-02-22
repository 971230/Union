<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid"%>
<style>

  .clickClass{
     background:#f7f7f7
  }
  
</style>

<body style="min-width: 150px;">
	<form method="post" id="orgSearchform" action='<%=request.getContextPath()%>/core/admin/org/orgAdmin!searchOrg.do?'>
		<div class="searchformDiv"  >
			<table>
				<tr>
					<th>组织编号：</th>
					<td><input type="text" class="ipttxt"  name="party_id"  value="${party_id}"/></td>					
				</tr>
				
				<tr>
					<th>组织名称：</th>
					<td>
						<input type="text" class="ipttxt"  name="org_name"  value="${org_name}"/>
					</td>
				</tr>
				
				<tr>
					<th>总部编码：</th>
					<td>
						<input type="text" class="ipttxt"  name="hq_dept_id"  value="${hq_dept_id}"/>
					</td>
				</tr>
				
				<tr>
					<th>省内编码：</th>
					<td>
						<input type="text" class="ipttxt"  name="dept_id"  value="${dept_id}"/>
					</td>
				</tr>
				
				<tr>
					<th></th>
					<td>
				    	<input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
					</td>
				</tr>
			</table>
		</div>
	</form>

	<div class="grid" >
		<form method="POST"  id="orgInfoForm" >
			<grid:grid from="webpage"  formId="orgSearchform" ajax="yes">
				<grid:header>
					<grid:cell width="25%">组织编码</grid:cell> 
					<grid:cell width="50%">组织名称</grid:cell> 
					<grid:cell width="25%">组织类型</grid:cell> 
				</grid:header>
				
				<grid:body item="obj">
					<grid:cell>${obj.party_id}</grid:cell>
					<grid:cell>${obj.org_name}</grid:cell>
					<grid:cell>${obj.org_type_name}</grid:cell>
					<input type='hidden' id="orgInfo_${obj.party_id}" name ="valueObj" party_id="${obj.party_id}" parent_org_name="${obj.parent_org_name}" union_org_code="${obj.union_org_code}"  
						org_code ="${obj.org_code}" org_name="${obj.org_name}" org_type_name="${obj.org_type_name}" org_content= "${obj.org_content}" 
						channel_type="${obj.channel_type}" parent_channel_type="${obj.parent_channel_type}" 
						dept_id="${obj.dept_id}" hq_dept_id="${obj.hq_dept_id}" dept_name="${obj.dept_name}"
						dept_lvl="${obj.dept_lvl}" areaid="${obj.areaid}" countyid="${obj.countyid}"
						zb_admin_depart_code="${obj.zb_admin_depart_code}" syn_channel_type="${obj.syn_channel_type}" syn_sub_type="${obj.syn_sub_type}"
						type_id="${obj.type_id}" chnl_kind_id="${obj.chnl_kind_id}" is_syn="${obj.is_syn}" 
						syn_date="${obj.syn_date}" parent_party_id="${obj.parent_party_id}" parent_org_name="${obj.parent_org_name}"
						lan_id="${obj.lan_id}" lan_name="${obj.lan_name}" region_id="${obj.region_id}"
						city_name="${obj.city_name}" org_type_id="${obj.org_type_id}" org_level="${obj.org_level}">
				</grid:body>  
			</grid:grid>
		</form>
	</div>
</body>

<script type="text/javascript">
         
$(".grid table tr").live("click",function(){
	$(".grid table tr").attr("class","");
	$(this).attr("class","clickClass");
	var obj = $(".clickClass [name='valueObj']");
	
	var id = "orgInfo_" + obj.attr("party_id");
	
	var attArr = document.getElementById(id).attributes;
	
	var orgInfo = {};
	
	for(var i in attArr){
		orgInfo[attArr[i].name] = attArr[i].nodeValue;
	}
	
	//调用父页面JS方法
	if(parent.showOrgDetail)
		parent.showOrgDetail(orgInfo);
	
});

function doQuery(){
	var form = document.getElementById('orgSearchform');
	//再次修改input内容

	form.submit();
}

</script>

