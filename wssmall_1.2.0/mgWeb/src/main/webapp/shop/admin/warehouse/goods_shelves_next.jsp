<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
<!--
.noborder {
	border-style: none;
}

.gray_form {
	background: none repeat scroll 0 0 #EEEEEE;
	border: 1px solid #CCCCCC;
	padding: 6px 10px;
}

.division {
	line-height: normal;
	margin: 5px;
	padding: 10px;
	white-space: normal;
}
-->
</style>
<div class="input">
	<form class="validate" method="post" action="" id='goodsShelvesform'
		validate="true">
		<div >
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
					<tr>
						<th>
							<span class='red'>*</span>仓库名称：
						</th>
						<td>
							<select name="warehouseSeatGoods.house_id"  id="house_id" class="resigterIpt" dataType="string" style="width:157px;height:22px" autocomplete="on"
								required="true">
								<option value=''>------请选择------</option>
								<c:forEach items="${warehouseList}" var="warehouse">
									<option value="${warehouse.house_id}">${warehouse.house_name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>商品货位：
						</th>
						<td>
							<select name="warehouseSeatGoods.seat_id"  id="seat_id" class="resigterIpt" dataType="string" style="width:157px;height:22px" autocomplete="on"
								required="true">
								<option value=''>------请选择------</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>
							<span class='red'>*</span>商品货号：
						</th>
						<td>
							<input  size='15' name="sn"
								id="sn" type="text" value="${sn}"
								dataType="string" class="resigterIpt" autocomplete="on"
								required="true" style="width:154px;height:22px"/>
							<span id="sn_message"></span>
						</td>
					</tr>
				<tbody>
			</table>
		</div>

		<div class="submitlist" align="center">
			<table align="right">
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<input id="goodsShelvesBtn" type="button" value=" 保存 "class="submitBtn" />
						<input name="reset" type="reset" value=" 重置 " class="submitBtn" />
					</td>
				</tr>
			</table>
		</div>
		<div class="clear"></div>
	</form>
	<div id="goodsShelvesDlg"></div>
</div>

<script type="text/javascript" src="warehouse/js/goodsShelvesDalog.js"></script>
<script type="text/javascript" src="warehouse/js/goodsShelvesList.js"></script>
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
     
    //修改数据保存
	  
	  $("#goodsShelvesform").validate();
      $("#goodsShelvesBtn").click(function() {
            var  url = ctx+ "/shop/admin/warehouseSeatAction!editGoodsShelves.do?ajax=yes";
			Cmp.ajaxSubmit('goodsShelvesform', '', url, {}, function(responseText){
				   if (responseText.result == 1) {
							alert(responseText.message);
					}
					if (responseText.result == 0) {
					       //修改
						    alert(responseText.message);
							//window.location=app_path+'/shop/admin/warehouseSeatAction!search.do';	
					}
						
			},'json');
		})
		
     $("#sn").blur(function() {
		var sn = $('#sn').val();
		url = ctx
				+ "/shop/admin/warehouseSeatAction!isGoodsIdExits.do?sn="
				+ sn + "&ajax=yes";
		if (sn.length == 0) {
			return false;
		}
		load.asSubmit(url, {}, 'json', function(responseText) {

					if (responseText.result == 1) {
						alert(responseText.message);
					}
					if (responseText.result == 2) {
						// 已存在
						$("#sn_message").html(responseText.message);
						$('#sn').val("");
					}
					
					if (responseText.result == 0) {
						// 不存在
						$("#sn_message").html(responseText.message);
					}

				});
	});
	
	$("#house_id").change(function() {
		var house_id = $('#house_id').val();
		url = ctx
				+ "/shop/admin/warehouseSeatAction!findWarehouseSeatByHouseId.do?house_id="
				+ house_id + "&ajax=yes";
		if (house_id.length == 0) {
			return false;
		}
		var optionStr="";
		
		$("#seat_id").empty();
		load.asSubmit(url, {}, 'json', function(response) {
			if(response!=null){
				for(var i=0;i<response.length;i++){
					optionStr=optionStr+"<option value='"+response[i].seat_id+"'>"+response[i].seat_name+"</option>";
				}
			}
			
			$("#seat_id").append(optionStr);
		});
	});
  })
</script>