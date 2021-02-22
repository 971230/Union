<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>


<style>

.tableform {
background:none repeat scroll 0 0 #EFEFEF;
border-color:#DDDDDD #BEC6CE #BEC6CE #DDDDDD;
border-style:solid;
border-width:1px;
margin:10px;
padding:5px;
}


.division  {
background:none repeat scroll 0 0 #FFFFFF;
border-color:#CCCCCC #BEC6CE #BEC6CE #CCCCCC;
border-style:solid;
border-width:1px 2px 2px 1px;
line-height:150%;
margin:10px;
padding:5px;
white-space:normal;
}

h4  {
font-size:1.2em;
font-weight:bold;
line-height:1.25;
}
h1, h2, h3, h4, h5, h6 {
clear:both;
color:#111111;
margin:0.5em 0;
}

</style>
<div class="input" >
<form id="order_st_form"  action="" method="post"  class="validate">
<div class="tableform">
<h4>选择会员</h4>
<div class="division">
<table class="form-table"  cellspacing="0" cellpadding="0" border="0" width="100%">
	<tbody>
	
		<tr>
			<th>请选择：</th>
			<td>
			 <input type="button" id="member_select_btn" class="graybtn1" value="选择" />  
			 <input type="hidden" value="" name="member_id" />
			 <span id="has_select_member"></span>
			</td>
		</tr>
		<tr>
			<th>会员等级：</th>
			<td id="member_lv_td_sp">
			</td>
		</tr>
	</tbody>
</table>
</div>
</div>

<div class="tableform">
<h4>选择商品：
	<a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1" name="addBtn" id="add_goods_btn"><span>添加</span></a>
	<a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1" name="clearBtn" id="add_clear_btn"><span>清空</span></a>
</h4>
<div class="division">
<table class="form-table"  cellspacing="0" cellpadding="0" border="0" width="100%" style="text-align: left;">
	<tbody>
	<tr>
	   	<td style="width: 5px;"></td>
	   	<td>商品名称</td>
	   	<td style="width: 80px;">单价</td>
	   	<td style="width: 80px;">数量</td>
	   	<td>操作</td>
	   </tr>
	</tbody>   
	<tbody id="goodstbody">
	   <c:forEach items="${cartList }" var="c">
	   		<tr><td><input type='hidden' name='cart_id' value='${c.id }' /></td>
			<td>${c.name }</td>
			<td>${c.coupPrice }</td>
			<td><input name='numArray' cartid='${c.id }' onblur="updateItemNum(this);" value='${c.num }' size='6' /></td>
			<td><input type='button' name='delgoods' class='graybtn1' cartid="${c.id }" value='删除' /></td>
			</tr>
	   </c:forEach>
	</tbody>
</table>
</div>
</div>


<div class="tableform">
<h4>支付方式</h4>
<div class="division">
<table class="form-table"  cellspacing="0" cellpadding="0" border="0" width="100%">
	<tbody>
	   <c:forEach items="${paymentList}" var="pay">
	   	<tr>
			<th style="width:20px"><input type="radio" name="payment_id" value="${pay.id }" /></th>
			<td>${pay.name }</td>
		</tr>
	   </c:forEach>
	</tbody>
</table>
</div>
</div>

<div class="tableform">
<h4>配送地址</h4>
<div class="division">
<table class="form-table"  cellspacing="0" cellpadding="0" border="0" width="100%">
	<tbody>
		<tr>
	   		<th style="width: 80px;">所在地区：</th>
	   		<td>
	   			省：<select name="province_id">
					<c:forEach items="${provinces }" var="region" >
						<option value="${region.region_id }|${region.local_name }">${region.local_name }</option>
					</c:forEach>
					</select>
				&nbsp;市：<span id="city_select" >
						  	<select name="city_id" style="width: 80px;">
						  		<c:forEach items="${citys }" var="region" >
									<option value="${region.region_id }|${region.local_name }">${region.local_name }</option>
								</c:forEach>
						  	</select>
						  </span>
				&nbsp;区：<span id="region_select" >
							<select name="region_id" style="width: 80px;">
								<c:forEach items="${regions }" var="region" >
									<option value="${region.region_id }|${region.local_name }">${region.local_name }</option>
								</c:forEach>
						  	</select>
						  </span>
	   		</td>
	   	</tr>
	   	<tr>
	   		<th>收货人：</th>
	   		<td><input type="text" name="shipName" /></td>
	   	</tr>
	   	<tr>
	   		<th>手机号码：</th>
	   		<td><input type="text" name="shipMobile" /></td>
	   	</tr>
	   	<tr>
	   		<th>邮政编码：</th>
	   		<td><input type="text" name="shipZip" /></td>
	   	</tr>
	   	<tr>
	   		<th>详细地址：</th>
	   		<td>
	   			<textarea name="shipAddr" rows="4" cols="50"></textarea>
	   		</td>
	   	</tr>
	</tbody>
</table>
</div>
</div>

<div class="tableform">
<h4>配送方式</h4>
<div class="division">
<table class="form-table"  cellspacing="0" cellpadding="0" border="0" width="100%">
	<tbody id="dlytypetbody">
		
	</tbody>
</table>
</div>
</div>

<div class="tableform">
<h4>订单总额</h4>
<div class="division" id="order_total_div">

</div>
</div>

<div class="tableform">
<h4>备注</h4>
<textarea vtype="textarea" required="true" rows="6" cols="60" name="remark" type="textarea"></textarea>
<br>
</div>
	<div class="submitlist" align="center">
	 <table>
	 <tr><td >
	   <input  type="button" id="order_st_btn" value=" 提交订单   " class="submitBtn" />
	   </td>
	   </tr>
	 </table>
	</div>
</form>

</div>

<!-- 新表单提交 -->
<form id="order_st_form_new"  action="" method="post"  class="validate">
	<input style="display: none;"  type="button" id="order_st_btn_new" value=" 提交订单新   " class="submitBtn" />
</form>
<!-- 选择商品对话框 -->
<div id="goodsselector" >
</div>

<!-- 选择商品对话框 -->
<div id="memberselector" >
</div>


<script type="text/javascript">
function updateItemNum(obj){
	var num = $(obj).val();
	var reg = /^\d+$/;
	if(!num && !reg.test(num)){
		alert("请输入正确的数字");
	}else{
		if(parseInt(num)<=0){
			$(obj).val(1);
			alert("数量不能小于0");
			return ;
		}
		var memberid = $("input[name='member_id']").val();
		var lvid = $("input[name='member_lv_id']:checked").val();
		var cartid = $(obj).attr("cartid");
		$.ajax({url : "accept!updateCartNum.do?ajax=yes",
			type : "POST",
			data:{cartId:cartid,num:num,member_id:memberid,member_lv_id:lvid},
			dataType : 'json',
			success : function(data) {
				if(data.status==0){
					alert(data.msg);
				}else{
					alert(data.msg);
				}
				
			},error : function(a,b) {
				alert(a+b);
			}
		});
	}
}

function cartTr(item){
	var tr = "<tr><td><input type='hidden' name='cart_id' value='"+item.cartid+"' /></td>"+
		"<td>"+item.productName+"</td><td>"+item.price+"</td><td><input name='numArray' cartid='"+item.cartid+"' onblur='updateItemNum(this);' value='1' size='6' /></td>"+
		"<td><input type='button' name='delgoods' class='graybtn1' cartid='"+item.cartid+"' value='删除' /></td></tr>";
	return tr;
}
var Selector={
	init:function(){
		var self = this;
		
		//表单一
		$("#order_st_btn").bind("click",function(){
			var url = "accept!createOrder.do?ajax=yes";
			$.Loading.show('正在响应您的请求，请稍侯...');
			var options = {
				type : "post",
				url : url,
				dataType : "json",
				success : function(result) {
					alert(result.msg);
					$.Loading.hide();
				},
				error : function(e,b) {
					$.Loading.hide();
					alert("操作失败，请重试"+b);
				}
			};
			$("#order_st_form").ajaxSubmit(options);
		});
		
		//新测试表单
		$("#order_st_btn_new").bind("click",function(){
			var url = "accept!createOrderNew.do?ajax=yes";
			$.Loading.show('正在响应您的请求，请稍侯...');
			var options = {
				type : "post",
				url : url,
				dataType : "json",
				success : function(result) {
					alert(result.msg);
					$.Loading.hide();
				},
				error : function(e,b) {
					$.Loading.hide();
					alert("操作失败，请重试"+b);
				}
			};
			$("#order_st_form_new").ajaxSubmit(options);
		});
		
		$("input[name='dlytype_id']").live("click",self.getOrderTotal);
		$("#member_select_btn").bind("click",self.memberSearch);
		$("#add_goods_btn").bind("click",self.goodsSearch);
		$("select[name='province_id']").bind("change",function(){
			var pid = $(this).val().split("\|")[0];
			var url = "accept!qryCity.do?ajax=yes&parentid="+pid;
			$("#city_select").load(url,function(){
				Selector.bindCityLoad();
			});
		});
		
		self.bindCityLoad();
		
		$("input[name='delgoods']").live("click",function(){
			var self = this;
			var memberid = $("input[name='member_id']").val();
			var lvid = $("input[name='member_lv_id']:checked").val();
			var idArray = [];
			idArray.push($(this).attr("cartid"));
			$.ajax({url : "accept!delCart.do?ajax=yes",
				type : "POST",
				data:{cartidArray:idArray,member_id:memberid,member_lv_id:lvid},
				dataType : 'json',
				success : function(data) {
					if(data.status==0){
						$(self).parents("tr").remove();
					}else{
						alert(data.msg);
					}
					
				},error : function(a,b) {
					alert(a+b);
				}
			});
		});
		
		$("#add_clear_btn").bind("click",function(){
			var memberid = $("input[name='member_id']").val();
			var lvid = $("input[name='member_lv_id']:checked").val();
			$.ajax({url : "accept!clear.do?ajax=yes",
				type : "POST",
				data:{member_id:memberid,member_lv_id:lvid},
				dataType : 'json',
				success : function(data) {
					if(data.status==0){
						$("#goodstbody").empty();
					}else{
						alert(data.msg);
					}
					
				},error : function(a,b) {
					alert(a+b);
				}
			});
		});
		
		//$("input[name='numArray']").blur(self.updateNum);
		
	},bindCityLoad:function(){
		$("select[name='city_id']").unbind("change").bind("change",function(){
			var pid = $(this).val().split("\|")[0];
			var url = "accept!qryRegion.do?ajax=yes&parentid="+pid;
			$("#region_select").load(url,function(){
				$("select[name='region_id']").unbind("change").bind("change",Selector.showDlytype);
				Selector.showDlytype();
			});
		});
	},
	showDlytype:function(){
		var regionid = $("select[name='region_id']").val().split("\|")[0];
		var memberid = $("input[name='member_id']").val();
		var url = "accept!qryDlyType.do?ajax=yes&regionid="+regionid+"&member_id="+memberid;
		$("#dlytypetbody").load(url);
	},
	updateNum:function(){
		var num = $(this).val();
		alert(num);
		var reg = /^\d+$/;
		alert(reg.test(num));
		if(!num && !reg.test(num)){
			alert("请输入正确的数字");
		}
	},
	memberSearch:function(){
		var url = "accept!qryMember.do?ajax=yes";
		var uobj = $("#uname");
		var uname = "";
		if(uobj)uname = uobj.val();
		if(uname)url += "&uname="+uname;
		$("#memberselector").load(url,function(responseText){
			Eop.Dialog.open("memberselector");
			$("#member_search_btn").bind("click",Selector.memberSearch);
			$("#member_confirm_btn").bind("click",Selector.showMemberAndLv);
		});
	},
	showMemberAndLv:function(){
		var memberinfo = $("input[name='memberid']:checked").val();
		if(!memberinfo){
			alert("请选择一个会员");
			return ;
		}
		var minfs = memberinfo.split("\|");
		$("#has_select_member").html("&nbsp;已选择账号："+minfs[1]);
		$("input[name='member_id']").val(minfs[0]);
		var lvurl = "accept!qryMemberLv.do?ajax=yes&member_id="+minfs[0];
		$("#member_lv_td_sp").load(lvurl);
		Eop.Dialog.close("memberselector");
		//$("#goodstbody").empty().load("accept!listCart.do?ajax=yes&member_id="+minfs[0]);
		$("#goodstbody").empty();
		$.ajax({url : "accept!listCart.do?ajax=yes",
			type : "POST",
			data:{member_id:minfs[0]},
			dataType : 'json',
			success : function(data) {
				if(data.status==0){
					$.each(data.carts,function(idx,item){
						var tr = cartTr(item);
						$(tr).find("input[name='numArray']").blur(self.updateNum);
						$("#goodstbody").append(tr);
					});
					var ctid = $("select[name='city_id']").val().split("\|")[0];;
					var url = "accept!qryRegion.do?ajax=yes&parentid="+ctid;
					$("#region_select").load(url,function(){
						Selector.showDlytype();
					});
				}
			},error : function(a,b) {
				alert(a+b);
			}
		});
	},
	goodsSearch:function(){
		var url = "accept!qryGoods.do?ajax=yes";
		var lvid = $("input[name='member_lv_id']:checked").val();
		if(!lvid){
			alert("请选择会员");
			return ;
		}
		var gobj = $("input[name='goodsName']");
		var goodsName = "";
		if(gobj)goodsName = gobj.val();
		if(!goodsName)goodsName="";
		$("#goodsselector").load(url,{'member_lv_id':lvid,'goodsName':goodsName},function(responseText){
			Eop.Dialog.open("goodsselector");
			$("#goodssearchBtn").bind("click",Selector.goodsSearch);
			$("input[name='goods_confirm_btn']").bind("click",Selector.goodsSelect);
		});
	},
	goodsSelect:function(){
		var goods = $("input[name='product_id']:checked");
		var idArray = [];
		if(goods && goods.length>0){
			goods.each(function(idx,item){
				idArray.push(item.value);
			});
		}
		var memberid = $("input[name='member_id']").val();
		var lvid = $("input[name='member_lv_id']:checked").val();
		$.ajax({url : "accept!addCart.do?ajax=yes",
			type : "POST",
			data:{productidArray:idArray,member_id:memberid,member_lv_id:lvid},
			dataType : 'json',
			success : function(data) {
				if(data.status==0){
					$.each(data.carts,function(idx,item){
						var tr = cartTr(item);
						$(tr).find("input[name='numArray']").blur(self.updateNum);
						$("#goodstbody").append(tr);
					});
				}else{
					alert(data.msg);
				}
				
			},error : function(a,b) {
				alert(a+b);
			}
		});
		Eop.Dialog.close("goodsselector");
	},getOrderTotal:function(){
		var dlytype_id = $(this).val();
		var regionid = $("select[name='region_id']").val().split("\|")[0];
		var memberid = $("input[name='member_id']").val();
		//alert(dlytype_id+" "+regionid+" "+memberid);
		$("#order_total_div").load("accept!getOrderTotal.do?ajax=yes",{dlytype_id:dlytype_id,regionid:regionid,member_id:memberid});
	}
};

$(function(){
	Eop.Dialog.init({id:"goodsselector",modal:false,title:"选择商品", height:"600px",width:"700px"});
	Eop.Dialog.init({id:"memberselector",modal:false,title:"选择会员", height:"600px",width:"700px"});
	Selector.init();
});


</script>
