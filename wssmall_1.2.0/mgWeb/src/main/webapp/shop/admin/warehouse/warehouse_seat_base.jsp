<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
<!--
	
.noborder {
    border-style: none;
}
-->
</style>
<div class="input">
	<form class="validate" method="post" action="" id='editWareSeatBaseform'
		validate="true">
		<div >
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
					<tr>
						<th>
							<span class='red'>*</span>所属仓库：
						</th>
						<td>
							<select id="house_id_btn" name="warehouseSeat.house_id" dataType="string" class="resigterIpt"
								autocomplete="on" required="true">
								<option value="">请选择</option>
								<c:forEach items="${warehouseList}" var="warehouse">
									<option value="${warehouse.house_id}" <c:if test="${warehouse.house_id eq warehouseSeat.house_id }">selected</c:if>>${warehouse.house_name}</option>
								</c:forEach>
							</select>
						</td>
					<tr>
					<tr>
						<th>
							<span class='red'>*</span>货位名称：
						</th>
						<td>
							<input maxlength="50" name="warehouseSeat.seat_name" id="seat_name_btn"
								type="text" value="${warehouseSeat.seat_name}" dataType="string" class="resigterIpt"
								autocomplete="on" required="true"   />
							<span id="seat_name_message">货位名称在系统中的中文名称标识</span>
						</td>
					</tr>
				<tbody>
			</table>
		</div>
		<c:if test="${flag eq 'add' or flag eq 'edit'}">
	<div class="submitlist" align="center"> 
	 <table align="right">
		 <tr>
		   <th> &nbsp;</th>
		 	<td>
		 	 <input type="hidden" id="warehouseSeat.seat_id" name="warehouseSeat.seat_id" value="${warehouseSeat.seat_id }">
		 	 <input type="hidden" id="is_edit" name="is_edit"value="${is_edit }">
		 	 <input type="hidden" id="flag" name="flag" value="${flag }">
	         <input id="ediWareSeatBaseBtn" type="button"   value=" 保存 " class="submitBtn"/>
		     <input name="reset" type="reset"   value=" 重置 " class="submitBtn"/>
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
/**
 * 供货商选择器
 */
var load = {
	asSubmit : function(url, params, dataType, callBack) {
		var data = jQuery.param(params);
		dataType = dataType || 'html';
		$.ajax({
					type : "post",
					url : url,
					data : data,
					dataType : dataType,
					success : function(result) {

						if (dataType == "json" && result.result == 1) {
							$.Loading.hide();
						}
						callBack(result); // 回调函数

					}
				});
	}
};
$(function (){
     if('view'=='${flag}'){
         $("input").attr("class","noborder");
         $("#up").attr("style","display:none;");
     }
     
    //修改数据保存
	  
	  $("#editWareSeatBaseform").validate();
      $("#ediWareSeatBaseBtn").click(function() {
            var  url = ctx+ "/shop/admin/warehouseSeatAction!edit.do?ajax=yes";
			Cmp.ajaxSubmit('editWareSeatBaseform', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
					}
					if (responseText.result == 0) {
					       //修改
						    alert(responseText.message);
							window.location=app_path+'/shop/admin/warehouseSeatAction!search.do';	
					}
						
			},'json');
		})
		
	$("#seat_name_btn").blur(function() {
		var seat_name = $.trim($('#seat_name_btn').val());
		var house_id = $.trim($('#house_id_btn').val());
		seat_name = encodeURI(encodeURI(seat_name, true), true);
		var url = ctx+ "/shop/admin/warehouseSeatAction!isExits.do?seat_name="+ seat_name+"&house_id="+house_id + "&ajax=yes";
		if (seat_name.length == 0) {
			return false;
		}
		
		load.asSubmit(url, {}, 'json', function(responseText) {

					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 2) {
						// 已存在
						$("#seat_name_message").html(responseText.message);
						$('#seat_name_btn').val("");
					}
					
					if (responseText.result == 0) {
						// 不存在
						$("#seat_name_message").html(responseText.message);
					}

				});
		
	});
  })
</script>