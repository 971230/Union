<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="cms/js/show_part_staff_add.js"></script>
 <c:if test="${flag eq 'add' or flag eq 'edit'}">
<div class="comBtnDiv">
		<a id="addStaffBtn" style="margin-right:10px;cursor: pointer;" class="graybtn1" ><span>添加</span></a>
		<a href="javascript:;" id="delStaffBtn" style="margin-right:10px;" class="graybtn1"><span>删除</span></a>
</div>
</c:if>
<form id="gridform" class="grid">
<div class="grid">
<grid:grid  from="partStafList">

	<grid:header>
	<grid:cell ><input type="checkbox"  id="toggleChk" /></grid:cell> 
	<grid:cell >编号</grid:cell> 
	<grid:cell >姓名</grid:cell> 
	<grid:cell >性别</grid:cell>
	<grid:cell >联系电话</grid:cell> 
	<grid:cell >MAC地址</grid:cell>
	<grid:cell >操作</grid:cell>
	<!-- 
	<grid:cell >失效时间</grid:cell> 
	<grid:cell >失效时间</grid:cell> 
	 -->
	</grid:header>

  <grid:body item="obj">
 <grid:cell><input type="checkbox" name="id" value="${obj.staff_code}" /></grid:cell>
        <grid:cell>${obj.staff_code}</grid:cell>
        <grid:cell>${obj.staff_name } </grid:cell>
        <grid:cell>${obj.sex}</grid:cell>
        <grid:cell>${obj.phone_no }</grid:cell>  
        <grid:cell>${obj.mac}</grid:cell>
        <grid:cell>
        	<a href="javascript:void(0);" partner_id="${obj.partner_id}" staff_code="${obj.staff_code}" name="editStaff" title="编辑"> 修改	</a>
        </grid:cell>
        <!-- 
        <grid:cell>
           <html:dateformat pattern="yyyy-MM-dd" d_time="${obj.eff_date }" />
        </grid:cell>
        <grid:cell > 
           <html:dateformat pattern="yyyy-MM-dd" d_time="${obj.exp_date }" />
        </grid:cell>
         -->
  </grid:body>  
  
</grid:grid>
<input type="hidden"  name="is_edit" value="${is_edit }">
<input type="hidden" name="partner_id" value="${partner_id }">
</form>
</div>
<div id="addStaffDlg"></div>
 <script type="text/javascript">
$(function(){
	Partner.init();
});
</script>