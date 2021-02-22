<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/supplier.js"></script>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
<div>
	<form action="supplier!list_check.do" method="post" id="suppListForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<th>
							<select class="ipttxt" id="supplier_list_sec" name="supplier_list_sec">
								<option value="0">卖家账号</option>
								<option value="1">企业名称</option>
								<option value="2">EMAIL账号</option>
								<option value="3">QQ账号</option>
								<option value="4">用户名</option>
								<option value="5">手机号码</option>
							</select>
						</th>
						<th>企业名称：</th>
						<td>
							<input type="text" class="ipttxt" style="width: 90px"
								name="company_name" value="${company_name }" class="searchipt" />
						</td>
						
						<th>邮箱：</th>
						<td>
							<input type="text" class="ipttxt" style="width: 90px"
								name="email" value="${email }" class="searchipt" />
						</td>
						
						<th>username：</th>
						<td>
							<input type="text" class="ipttxt" style="width: 90px"
								name="user_name" value="${user_name }" class="searchipt" />
						</td>
						<th>QQ：</th>
						<td>
							<input type="text" class="ipttxt" style="width: 90px"
								name="qq" value="${qq }" class="searchipt" />
						</td>
						<th>beginTime：</th>
						<td>
							<input type="text" class="ipttxt" style="width: 90px"
								name="beginTime" value="${beginTime }" class="searchipt" />
						</td>
						<th>endTime：</th>
						<td>
							<input type="text" class="ipttxt" style="width: 90px"
								name="endTime" value="${endTime }" class="searchipt" />
						</td>
						
						
						<td>
							<input type="submit" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" id="button" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" excel="yes">
			<grid:header>
				<grid:cell>
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell sort="company_name">公司名称</grid:cell>
				<grid:cell>电子邮件</grid:cell>
				<grid:cell>QQ号</grid:cell>
				<grid:cell>注册人姓名</grid:cell>
				<grid:cell>性别</grid:cell>
				<grid:cell>联系电话</grid:cell>
				<grid:cell>状态</grid:cell>
				<grid:cell>创建时间</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="checkbox" name="id" userid="${obj.user.userid }"
						value="${obj.supplier_id}" supplier_state="${obj.state }" />
				</grid:cell>
				<grid:cell>
					<a title="查看"
						href="supplier!detail.do?flag=view&supplier_state=${obj.supplier_state }&supplier_id=${obj.supplier_id }&supplier_type=${obj.supplier_type }">${obj.company_name}</a>
				</grid:cell>
				<grid:cell>${obj.email} </grid:cell>
				<grid:cell>${obj.qq} </grid:cell>
				<grid:cell>${obj.user_name} </grid:cell>
				<grid:cell>
					<c:if test="${obj.sex eq '1'}">
     		男
     	</c:if>
					<c:if test="${obj.sex eq '2'}">
     		女
     	</c:if>
				</grid:cell>
				<grid:cell>${obj.phone}</grid:cell>
				<grid:cell>
					${obj.state}
				</grid:cell>
				<grid:cell>
					<html:dateformat pattern="yyyy-MM-dd" d_time="${obj.register_time}"></html:dateformat>
				</grid:cell>
				<grid:cell>
					<c:if test="${adminUser.founder==1}">
						<!--<c:if
							test="${obj.state == '0' or obj.state == '-1' }">
							
						</c:if>-->
						<a title="审核" href="supplier!set_check.do?supplier_id=${obj.supplier_id }">审核</a>
					</c:if>
				</grid:cell>
			</grid:body>

		</grid:grid>

	</form>
	<div id="showDlg"></div>
	<div style="clear: both; padding-top: 5px;"></div>
</div>
<script type="text/javascript">
$(function(){
    $("#supplier_state option[value='${supplier_state}']").attr("selected", true);
    $("#supplier_state").change(function (){
       $("#button").trigger("click")
    });
    
    if(${is_administrator}==0){
    	$(".comBtnDiv").hide();
    }
    
	Supplier.init();
	
});
</script>


