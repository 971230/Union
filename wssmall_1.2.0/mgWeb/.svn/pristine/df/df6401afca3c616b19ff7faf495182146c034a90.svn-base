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
	<form class="validate" method="post" action="" id='editWareBaseform'
		validate="true">
		<div >
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
					<tr>
						<th>
							<span class='red'>*</span>仓库名称：
						</th>
						<td>
							<input maxlength="50" name="warehouse.house_name" id="house_name"
								type="text" value="${warehouse.house_name}" dataType="string" class="resigterIpt"
								autocomplete="on" required="true"   />
							<span id="house_name_message">仓库在系统中的中文名称标识</span>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>仓库编号：
						</th>
						<td>
							<input maxlength="50" type="text" id="house_code" name="warehouse.house_code"
								value="${warehouse.house_code }" dataType="string" class="resigterIpt _x_ipt"
								autocomplete="on" required="true" />
							<span id="house_code_message">仓库在系统中的编号</span>
						</td>
					<tr>
					<tr>
						<th>
							仓库属性：
						</th>
						<td>
							<input type="radio" name="warehouse.attr_inline_type" <c:if test="${warehouse.attr_inline_type eq 'T' }">checked</c:if> checked value="T" dataType="" />
							线上
							<input type="radio" name="warehouse.attr_inline_type" <c:if test="${warehouse.attr_inline_type eq 'F' }">checked</c:if>  value="F" dataType="" />
							线下
							<span class='red'> 线上仓库的库存系统会和前端店铺保持同步，线下仓库的库存则不会影响前端店铺的可售库存。</span>
						</td>
					</tr>
					<tr>
						<th>
							发货属性：
						</th>
						<td>
							<input type="radio" name="warehouse.deliver_goods_attr" <c:if test="${warehouse.deliver_goods_attr eq 'T' }">checked</c:if> checked value="T" dataType="" />
							发货仓库
							<input type="radio" name="warehouse.deliver_goods_attr" <c:if test="${warehouse.deliver_goods_attr eq 'F' }">checked</c:if>  value="F" dataType="" />
							备货仓库
							<span class='red'>备货仓库可以库存共享，也可以不共享，但是不具备发货功能</span>
						</td>
					</tr>
					<tr>
						<th>
							仓库归属：
						</th>
						<td>
							<input type="radio" name="warehouse.attribution" <c:if test="${warehouse.attribution eq 'T' }">checked</c:if> checked value="T" dataType="" />
							自建仓库
							<input type="radio" name="warehouse.attribution" <c:if test="${warehouse.attribution eq 'F' }">checked</c:if>  value="F" dataType="" />
							第三方仓库
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>联系人姓名：
						</th>
						<td>
							<input maxlength="20" id="warehouse.contact_name" name="warehouse.contact_name"
								dataType="string" type="text" value="${warehouse.contact_name }"
								class="resigterIpt _x_ipt" autocomplete="on" required="true" />
							<span>仓库负责人的姓名</span>
						</td>
					</tr>
					<tr>
						<th>
							性别：
						</th>
						<td>
							<input type="radio" name="warehouse.sex" <c:if test="${warehouse.sex eq '1' }">checked</c:if> checked value="1" dataType="" />
							男
							<input type="radio" name="warehouse.sex" <c:if test="${warehouse.sex eq '2' }">checked</c:if>  value="2" dataType="" />
							女
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>地址：
						</th>
						<td>
							<input maxlength="50" id="warehouse.address" name="warehouse.address" value="${warehouse.address }"
								type="text" dataType="string" class="resigterIpt _x_ipt"
								autocomplete="on" required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>邮编：
						</th>
						<td>
							<input maxlength="50" id="warehouse.zip_code" name="warehouse.zip_code" value="${warehouse.zip_code }"
								type="text" dataType="string" class="resigterIpt _x_ipt"
								autocomplete="on" required="true" />
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>电话：
						</th>
						<td>
							<input maxlength="50" id="warehouse.telephone" name="warehouse.telephone"
								type="text" value="${warehouse.telephone }" dataType="string"
								class="resigterIpt _x_ipt" autocomplete="on" required="true"/>
							<span>仓库的固定电话</span>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>手机：
						</th>
						<td>
							<input maxlength="50" id="warehouse.phone_num" name="warehouse.phone_num"
								type="text" value="${warehouse.phone_num }" dataType="string"
								class="resigterIpt _x_ipt" autocomplete="on" required="true"/>
							<span> 仓库负责人的移动联系方式</span>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>阀值：
						</th>
						<td>
							<input maxlength="50" id="warehouse.threshold" name="warehouse.threshold"
								type="text" value="${warehouse.threshold }" dataType="string"
								class="resigterIpt _x_ipt" autocomplete="on" required="true"/>
							<span>订单确认界面用来标识当前操作库存状态：无货、紧张、有货 </span>
						</td>
					</tr>
					<tr>
						<th>
							备注：
						</th>
						<td>
							<textarea cols="80" rows="6" name="warehouse.remarks" value="${warehouse.remarks }" style="width: 437px; height: 92px;">${warehouse.remarks }</textarea>
						</td>
					</tr>
					<tr>
						<th>
							权重：
						</th>
						<td>
							<input maxlength="50" id="warehouse.weight" name="warehouse.weight"
								type="text" value="${warehouse.weight }" dataType="int"
								class="resigterIpt _x_ipt" />
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
		 	 <input type="hidden" id="is_edit" name="warehouse.house_id" value="${warehouse.house_id}">
		 	 <input type="hidden" id="is_edit" name="is_edit"value="${is_edit }">
		 	 <input type="hidden" id="flag" name="flag" value="${flag }">
	         <input id="ediWareBaseBtn" type="button"   value=" 保存 " class="submitBtn"/>
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
	  
	  $("#editWareBaseform").validate();
      $("#ediWareBaseBtn").click(function() {
            var  url = ctx+ "/shop/admin/warehouseAction!edit.do?ajax=yes";
			Cmp.ajaxSubmit('editWareBaseform', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
					}
					if (responseText.result == 0) {
					       //修改
						    alert(responseText.message);
						    $('#editPartnerBaseBtn').attr('disabled',"true");
							window.location=app_path+'/shop/admin/warehouseAction!search.do';	
					}
						
			},'json');
		})
		
	$("#house_name").blur(function() {
		var house_name = $.trim($('#house_name').val());
		house_name = encodeURI(encodeURI(house_name, true), true);
		url = ctx
				+ "/shop/admin/warehouseAction!isExits.do?house_name="
				+ house_name + "&ajax=yes";
		if (house_name.length == 0) {
			return false;
		}
		load.asSubmit(url, {}, 'json', function(responseText) {

					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 2) {
						// 已存在
						$("#house_name_message").html(responseText.message);
						$('#house_name').val("");
					}
					
					if (responseText.result == 0) {
						// 不存在
						$("#house_name_message").html(responseText.message);
					}

				});
	});

	$("#house_code").blur(function() {
		var house_code = $.trim($('#house_code').val());
		url = ctx
				+ "/shop/admin/warehouseAction!isExits.do?house_code="
				+ house_code + "&ajax=yes";
		if (house_code.length == 0) {
			return false;
		}
		load.asSubmit(url, {}, 'json', function(responseText) {

					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 2) {
						// 已存在
						$("#house_code_message").html(responseText.message);
						$('#house_code').val("");
					}
					
					if (responseText.result == 0) {
						// 不存在
						$("#house_code_message").html(responseText.message);
					}
				});
	});
	
  })
</script>