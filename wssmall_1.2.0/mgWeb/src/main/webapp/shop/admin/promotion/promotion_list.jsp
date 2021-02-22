<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/Promotion.js"></script>
<div class="grid">
	<div class="comBtnDiv">
	
		<ul>
			<li><a href="promotion!select_plugin.do?activityid=${activityid }" style="margin-right:10px;" class="graybtn1">添加</a></li>
			<li><a href="javascript:;" id="delBtn" style="margin-right:10px;" class="graybtn1">删除</a></li>
		</ul>
		
		<div style="clear:both"></div>
	</div>
<form method="POST" id="promotion_list_form">
<grid:grid  from="pmtList">

	<grid:header>
	<grid:cell width="50px"><input type="checkbox" id="toggleChk" /></grid:cell>
	<grid:cell  >规则描述</grid:cell> 
	<grid:cell   width="150px">超始时间</grid:cell> 
	<grid:cell   width="150px">截止时间</grid:cell> 
	<grid:cell  width="100px">操作</grid:cell> 
	</grid:header>

  <grid:body item="pmt">
  		<grid:cell><input type="checkbox" name="pmtidArray" value="${pmt.pmt_id }" pmt_id="${pmt.pmt_id }"/></grid:cell>
  		<grid:cell>${pmt.pmt_describe}</grid:cell>  
        <grid:cell>${fn:substring(pmt.pmt_time_begin , 0 , 10)} </grid:cell>
        <grid:cell>${fn:substring(pmt.pmt_time_end , 0 , 10)}</grid:cell>
        <grid:cell><a href="promotion!select_plugin.do?activityid=${activityid }&pmtid=${pmt.pmt_id }">
        <img class="modify"  src="images/transparent.gif"></a> 
        <span class='tdsper'></span>
        <a href="promotion!show.do?activityid=${activityid}&pmtid=${pmt.pmt_id }&pluginid=${pmt.pmts_id}">查看详细</a>
        </grid:cell> 
  </grid:body>
  
</grid:grid>  
</form>	
<div style="clear:both;padding-top:5px;"></div>
</div>
<script type="text/javascript">
$(function(){
	Promotion.opation("idChkName","pmtidArray");
	Promotion.init();
});
</script>
