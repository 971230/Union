<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="cms/js/Tab.js"></script>
<script type="text/javascript" src="cms/js/partner.js"></script>
<style>
.distributorL{ width:65%; float:left;}
.distributorR{ width:35%; float:left;}
.noborder{
  border-top-style: none;
  border-right-style: none;
  border-left-style: none;
  border-bottom-style: none;
  }
</style>
<div style="display: block;height:700px;" class="partner_detail">
	<div class="tab-bar" style="position: relative;">
	<ul class="tab">
		<li id="base" class="active">基本资料</li>
<!-- 		<li id="shop">他的网店信息</li>  -->
		<li id="staff">行业用户</li>
		
	</ul>
	</div>
	<div class="tab-page">
		<div id="baseTab"></div>
		<!-- <div id="shopTab"></div> -->
		<div id="staffTab"></div>
	</div>
	
</div>

 <script>

$(function(){
  
  PartnerDetail.init('${partner_id}',${is_edit},'${flag}','${state}',${sequ});
});
</script>