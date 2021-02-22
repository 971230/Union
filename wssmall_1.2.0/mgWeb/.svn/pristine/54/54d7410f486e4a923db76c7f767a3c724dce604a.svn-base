<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/show_supplier_staff_add.js"></script>
<script type="text/javascript" src="js/show_supplier_staff_edit.js"></script>
 <c:if test="${flag eq 'add' or flag eq 'edit'}">
<div class="comBtnDiv">
		<a id="addStaffBtn" style="margin-right:10px;cursor: pointer;" class="graybtn1" ><span>添加</span></a>
		<a href="javascript:;" id="delStaffBtn" style="margin-right:10px;" class="graybtn1"><span>删除</span></a>
		<a href="javascript:;" id="editStaffBtn" style="margin-right:10px;" class="graybtn1"><span>修改</span></a>
</div>
</c:if>
<form id="gridform" class="grid">
<div class="grid">
<grid:grid  from="supplierStaffList">

	<grid:header>
	<grid:cell ><input type="checkbox"  id="toggleChk" /></grid:cell> 
	<grid:cell >账号</grid:cell> 
	<grid:cell >姓名</grid:cell> 
	<grid:cell >性别</grid:cell>
	<grid:cell >身份证号码</grid:cell> 
	<grid:cell >电子邮件</grid:cell> 
	<grid:cell >联系电话</grid:cell> 
	<grid:cell >QQ号码</grid:cell>
	<grid:cell>关联工号</grid:cell> 
	<grid:cell >注册时间</grid:cell> 
	<grid:cell >操作</grid:cell>
	</grid:header>

  <grid:body item="obj">
 <grid:cell><input type="checkbox" name="id" value="${obj.staff_id}" userid="${obj.userid}"  /></grid:cell>
        <grid:cell>${obj.account_number}</grid:cell>
        <grid:cell>${obj.user_name } </grid:cell>
        <grid:cell>
	        <c:if test="${obj.sex=='1'}">
	        	男
	        </c:if>
	        <c:if test="${obj.sex=='2'}">
	        	女
	        </c:if>
        </grid:cell>
        <grid:cell>${obj.id_card}</grid:cell>
        <grid:cell>${obj.email}</grid:cell>
        <grid:cell>${obj.phone }</grid:cell>  
        <grid:cell>${obj.qq }</grid:cell>
        <grid:cell><c:if test="${obj.userid!=null}">已关联 </c:if> <c:if test="${obj.userid==null}"><span class='red'>未关联</span></c:if> </grid:cell> 
        <grid:cell >  <html:dateformat pattern="yyyy-MM-dd" d_time="${obj.register_time }" /></grid:cell>
        <grid:cell>
		    <c:if test="${obj.userid==null}">
			  	   <a name=bindingstaff title="绑定工号" href="javascript:;" staff_id=${obj.staff_id }>绑定 </a> 
			</c:if>

			<c:if test="${obj.userid!=null}">
			  	   <a title="解绑工号" name="Unbundlingstaff" href="javascript:;" staff_id=${obj.staff_id }>解绑 </a> 
			</c:if>
	</grid:cell>
  </grid:body>  
  
</grid:grid>
<input type="hidden"  name="is_edit" value="${is_edit }">
<input type="hidden" name="supplier_id" value="${supplier_id }">
</form>
</div>
<div id="addStaffDlg"></div>
<div id="editStaffDlg"></div>
 <script type="text/javascript">
$(function(){
	Supplier.init();
	
	$("[name='bindingstaff']").click(function() {
			var staff_id= $(this).attr("staff_id");
            var  url =ctx+"/shop/supplier/supplier!bindingstaff.do?staff_id="+staff_id+ "&ajax=yes";
			
			$.Loading.show("正在绑定...");
			load.asSubmit(url, {}, 'json', function(responseText) {
					$.Loading.hide();
					if (responseText.result == 0) {
				   			//绑定成功
							alert(responseText.message);
							 SupplierDetail.showStaff();
					}
					if (responseText.result == 1) {
					        //绑定失败
						    alert(responseText.message);
							StaffEdit.open(staff_id);
					}
				});
			
		})
		
	$("[name='Unbundlingstaff']").click(function() {
			var staff_id= $(this).attr("staff_id");
            var  url =ctx+"/shop/supplier/supplier!unbundlingstaff.do?staff_id="+staff_id+ "&ajax=yes";
			
			$.Loading.show("正在解绑...");
			load.asSubmit(url, {}, 'json', function(responseText) {
					$.Loading.hide();
					if (responseText.result == 0) {
				   			//解绑成功
							alert(responseText.message);
							 SupplierDetail.showStaff();
					}
					
					if (responseText.result == 1) {
				   			//解绑失败
							alert(responseText.message);
							 SupplierDetail.showStaff();
					}
				});
			
		})
	
});
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
</script>