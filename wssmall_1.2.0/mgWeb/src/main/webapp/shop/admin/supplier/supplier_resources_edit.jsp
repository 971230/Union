<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.distributorL {
	width: 65%;
	float: left;
}

.distributorR {
	width: 35%;
	float: left;
}

.noborder {
	border-top-style: none;
	border-right-style: none;
	border-left-style: none;
	border-bottom-style: none;
}
</style>
<div class="input">
	<form class="validate" method="post" action="" id='editResourcesform'
		validate="true">
		<div class="distributorL">
			<input type="hidden" name="supplierResources.resources_id" value="${supplierResources.resources_id }" />
			<input type="hidden" name="supplierResources.supplier_id" value="${supplierResources.supplier_id }" />
			<input type="hidden" name="supplier_id" value="${supplierResources.supplier_id }" />
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
					<tr>
						<th>
							<span class='red'>*</span>年份:
						</th>
						<td>
							<input type="text" name="supplierResources.year" id="year"
								value='<html:dateformat pattern="yyyy-MM-dd" d_time="${supplierResources.year }" />'
								maxlength="60" class="dateinput ipttxt" dataType="date"
								required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>员工总数:
						</th>
						<td>
							<input type="text" id="employees_number" name="supplierResources.employees_number"
								value="${supplierResources.employees_number }" dataType="int"
								class="resigterIpt" autocomplete="on" required="true"></input>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>生产人员数:
						</th>
						<td>
							<input type="text" id="production"
								value="${supplierResources.production }"
								name="supplierResources.production" dataType="int"
								class="resigterIpt" autocomplete="on" required="true"></input>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>管理人员数:
						</th>
						<td>
							<input type="text" id="administration"
								value="${supplierResources.administration }"
								name="supplierResources.administration" dataType="int"
								class="resigterIpt" autocomplete="on" required="true"></input>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>研发人员数:
						</th>
						<td>
							<input type="text" id="research"
								value="${supplierResources.research }"
								name="supplierResources.research" dataType="int"
								class="resigterIpt" autocomplete="on" required="true"></input>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>服务支持人员数:
						</th>
						<td>
							<input type="text" id="support"
								value="${supplierResources.support }"
								name="supplierResources.support" dataType="int"
								class="resigterIpt" autocomplete="on" required="true"></input>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>市场及销售人员数:
						</th>
						<td>
							<input type="text" id="marketing_sales"
								value="${supplierResources.marketing_sales }"
								name="supplierResources.marketing_sales" dataType="int"
								class="resigterIpt" autocomplete="on" required="true"></input>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>公司概况:
						</th>
						<td>
							<textarea id="company_desc"	name="supplierResources.company_desc" dataType="string"
									class="resigterIpt resig_txt" autocomplete="on" required="true" cols="45" rows="5" 
									style="width:80%;" value="${supplierResources.company_desc }">${supplierResources.company_desc }</textarea>
						</td>
					</tr>
				<tbody>
			</table>
		</div>
		<c:if test="${flag eq 'add' or flag eq 'edit'}">
			<div class="submitlist" align="center">
				<table align="right">
					<tr>
						<th>
							&nbsp;
						</th>
						<td>
							<input type="hidden"  name="is_edit" value="${is_edit }">
							<input type="hidden" name="supplier_id" value="${supplier_id }">
							<input id="editResourcesBtn" type="button" value=" 保存 "
								class="submitBtn" />
							<input name="reset" type="reset" value=" 重置 " class="submitBtn" />
						</td>
					</tr>
				</table>
			</div>
		</c:if>
		<div class="clear"></div>
	</form>
	<div id="auditDlg"></div>
</div>

<script type="text/javascript"> 

$(function (){
     
     	  if('view'=='${flag}'){
	         $("input").attr("class","noborder");
	         $("#up").attr("style","display:none;");
	         $('#agent_type').attr('disabled','disabled');
     	  }
   
    //添加或修改数据保存
	  var isedit=$("#is_edit").val();
	  
	  $("#editResourcesform").validate();
      $("#editResourcesBtn").click(function() {
            var  url = ctx+ "/shop/supplier/supplier!editResources.do?ajax=yes";
			Cmp.ajaxSubmit('editResourcesform', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
					}
					if (responseText.result == 0) {
					       //修改
						    alert(responseText.message);
							window.location=app_path+'/shop/admin/supplier!resourcesTab.do?is_edit=${is_edit }&supplier_id=${supplier_id }';	
					}
						
			},'json');
		})
	
  })
</script>