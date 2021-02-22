<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="${staticserver }/js/admin/Tab.js"></script>
<script type="text/javascript" src="js/supplier.js"></script>
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
<div style="display: block; height: 700px;" class="partner_detail">
	<div class="tab-bar" style="position: relative;">
		<ul class="tab">
			<li id="base" class="active">
				基本资料
			</li>
			<li id="company">
				公司信息
			</li>
			<li id="staff">
				他的员工
			</li>
			<li id="account">
				账户信息
			</li>
			<li id="agent">
				代理原厂商信息
			</li>
			<li id="certificate">
				认证证书信息
			</li>
			<li id="resources">
				人力资源信息
			</li>
		</ul>
	</div>
	<div class="tab-page">
		<div id="baseTab"></div>
		<div id="companyTab"></div>
		<div id="staffTab"></div>
		<div id="accountTab"></div>
		<div id="agentTab"></div>
		<div id="certificateTab"></div>
		<div id="resourcesTab"></div>
	</div>

</div>

<script>

$(function(){
  if(${supplier_type}=='2'){
  	$("#staff").hide();
  }
  SupplierDetail.init('${supplier_id}','${is_edit}','${flag}','${state}');
});
</script>