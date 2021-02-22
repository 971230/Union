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
	<form action="supplier!list.do" method="post" id="suppListForm">
		<div class="searchformDiv">
			<table width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>

						<th>
							公司名称:
						</th>
						<td>
							<input type="text" class="ipttxt" style="width: 90px"
								name="company_name" value="${company_name }" class="searchipt" />
						</td>
						<th>
							注册人:
						</th>
						<td>
							<input type="hidden" name="is_administrator"
								value="${is_administrator }" />
							<input type="text" class="ipttxt" class="searchipt"
								style="width: 120px" name="user_name" value="${user_name }" />
						</td>
						<th>
							联系电话:
						</th>
						<td>
							<input type="text" class="ipttxt" class="searchipt"
								style="width: 120px" name="phone" value="${phone }" />
						</td>
						<th>
							状态:
						</th>
						<td>
							<select class="ipttxt" id="supplier_state" name="supplier_state">
								<option value="all">
									全部
								</option>
								<option value="1">
									正常
								</option>
								<option value="2">
									已注销
								</option>
								<option value="0">
									待审核
								</option>
								<!--  add by wui只查询有效的状态，处理中的不需要加上
	    	          <option value="0">申请中</option>
	    	          <option value="-1">申请不通过</option>
	    	          <option value="-2">变更中</option>
	    	          <option value="-3">变更审核不通过</option>
	    	           -->
							</select>
						</td>
						<td>
							<input type="submit" style="margin-right: 10px;" class="comBtn"
								value="搜&nbsp;索" id="button" name="button">
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="comBtnDiv">
			
			<c:if test="${supplier_type=='1'}">
				<a href="javascript:;" id="addSupplierBtn"
					style="margin-right: 10px;" class="graybtn1"><span>添加</span>
				</a>
			</c:if>
			<c:if test="${supplier_state=='1' or supplier_state=='all'}">
				<a href="javascript:;" id="cancellationBtn"
					style="margin-right: 10px;" class="graybtn1"><span>注销</span>
				</a>
			</c:if>
			<a href="javascript:;" id="delSupplierBtn"
				style="margin-right: 10px;" class="graybtn1"><span>删除</span>
			</a>
		</div>
		<input type="hidden" id="supplier_type" name="supplier_type" value="${supplier_type}" />
	</form>
	<form id="gridform" class="grid">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell>
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell sort="company_name">公司名称</grid:cell>
				<grid:cell>电子邮件</grid:cell>
				<grid:cell>QQ号</grid:cell>
				<grid:cell>注册人姓名</grid:cell>
				<grid:cell>性别</grid:cell>
				<grid:cell>身份证号码</grid:cell>
				<grid:cell>联系电话</grid:cell>
				<grid:cell>状态</grid:cell>
				<grid:cell>创建时间</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>
					<input type="checkbox" name="id" userid="${obj.user.userid }"
						value="${obj.supplier_id}" supplier_state="${obj.supplier_state }" />
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
				<grid:cell>${obj.id_card}</grid:cell>
				<grid:cell>${obj.phone}</grid:cell>
				<grid:cell>
					<c:if test="${obj.supplier_state eq '0' }">待审核</c:if>
					<c:if test="${obj.supplier_state eq '1'}">正常</c:if>
					<c:if test="${obj.supplier_state eq '2' }">注销</c:if>
					<c:if test="${obj.supplier_state eq '-1' }">审核不通过</c:if>
				</grid:cell>
				<grid:cell>
					<html:dateformat pattern="yyyy-MM-dd" d_time="${obj.register_time}"></html:dateformat>
				</grid:cell>
				<grid:cell>
					<c:if test="${adminUser.founder==4 or adminUser.founder==1}">
						<c:if
							test="${obj.supplier_state == '1' or obj.supplier_state == '-1' }">
							<a title="编辑"
								href="supplier!detail.do?flag=edit&supplier_state=${obj.supplier_state }&supplier_id=${obj.supplier_id }&supplier_type=${obj.supplier_type }">
								修改 </a>
						</c:if>
					</c:if>
				</grid:cell>
			</grid:body>

		</grid:grid>

		<!-- <img  class="modify" src="${ctx }/shop/admin/images/transparent.gif"> -->
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


