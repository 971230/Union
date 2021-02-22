<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
	.progressContainer {
		float: left;
		padding-left: 10px;
		width: 50px;
	}
	
	.proWrapper {
		border: solid 1px #BBDDE5;
		width: 50px;
		height: 50px;
		float: left;
		margin: 5px 5px 5px 5px;
	}
	
	.uploadImg {
		width: 50px;
		height: 50px;
	}
	
	.progressState {
		margin-top: 15px;
		height: 20px;
		background-color: #E8F2FE;
	}
	
	.progressText {
		margin-top: -18px;
		padding-left: 30px
	}
	
	.imgPrivew {
		background: #F7F6F6;
		border: 2px solid #5CA647;
		margin-bottom: 10px;
		padding: 2px;
		width: 280px;
		height: 250px;
		text-align: center;
	}
	
	.deleteBtn {
		letter-spacing: 25px;
		width: 50px;
		height: 25px;
		background-image: url('${ctx}/themes/default/images/icon_drop.gif');
		background-repeat: no-repeat;
		background-position: top center;
	}
	
	* html .deleteBtn {
		display: block;
	}
	
	ul,li {
		list-style: none;
	}
	
	.input_panel {
		float: left
	}
	
	.input_panel ul {
		margin: 1px;
		width: 100%;
		float: left;
	}
	
	.input_panel ul li {
		float: left;
		border: 1px solid #EFEFEF;
	}
	
	.input_panel ul li.text {
		background-color: #F8FAFC;
		width: 150px;
		text-align: right;
		line-height: 25px;
	}
	
	.input_panel ul li.input {
		border-left: 0;
		padding-left: 5px;
		width: 350px;
		line-height: 25px;
	}
	
	.input_panel ul li.adj_input {
		border-left: 0;
		padding-left: 5px;
		width: 450px;
		line-height: 25px;
	}
	
	.input_panel .input_text {
		width: 200px
	}
	
	.groupname {
		font-size: 14px
	}
	
	.albumbox table tr {
		border-bottom: 0;
	}
	
	#fourth_step .resig_table_div table th{ width:170px; text-align:right; padding-top:8px; padding-bottom:8px; line-height:24px;}
	
	
	</style>
<div class="input">
 <form  class="validate" method="post" action="" id='addResouresform' validate="true">
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
							<span class='red'>*</span>生产人员:
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
							<span class='red'>*</span>管理人员:
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
							<span class='red'>*</span>研发人员:
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
							<span class='red'>*</span>服务支持人员:
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
							<span class='red'>*</span>市场及销售人员:
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
	<div class="submitlist" align="center">
	 <table>
		 <tr>
		  <th> &nbsp;</th>
		 	<td >
		 	<input type="hidden" name="supplier_id" value="${supplier_id }">
		 	
	           <input  type="button"  id="saveSupplierResouresBtn" value=" 确定 " class="submitBtn" name='submitBtn'/>
	           <input name="reset" type="reset"  value=" 重置 " class="submitBtn"/>
		   </td>
		</tr>
	 </table>
	</div>   
   </form>
 </div>
 <script type="text/javascript"> 
$(function (){
	  
	  $("#addResouresform").validate();
      $("#saveSupplierResouresBtn").click(function() {
			var url = ctx+ "/shop/admin/supplier!saveAddResources.do?ajax=yes";
			Cmp.ajaxSubmit('addResouresform', '', url, {}, function(responseText){
			
					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 0) {
						 
						alert(responseText.message);
						SupplierDetail.showresourcesTab();
					}
					ResourcesAdd.page_close();
			},'json');
		})
		
  })
</script>
