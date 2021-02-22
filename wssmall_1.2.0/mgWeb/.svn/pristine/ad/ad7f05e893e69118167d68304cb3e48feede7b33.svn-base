<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/LimitBuy.js"></script>
<script type="text/javascript" src="js/Selector.js"></script>

<style>
#ruletable input{width:100px;}
</style>
<div class="input" >

<c:if test="${isEdit}" >
<form method="post" action="limitBuy!saveEdit.do" class="validate"  onsubmit="javascript:return checkgoods();" enctype="multipart/form-data">
<input type="hidden" name="limitBuy.id" value="${limitBuy.id }" />
</c:if>
<c:if test="${!isEdit}" >
<form method="post" action="limitBuy!saveAdd.do" class="validate"  onsubmit="javascript:return checkgoods();" enctype="multipart/form-data">
</c:if>


<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
	<tr>
		<th><label class="text">标题：</label></th>
		<td>
		<input type="text" class="ipttxt"  name="limitBuy.name" value="${limitBuy.name}" dataType="string" required="true"  style="width:300px" />
		</td>
	</tr>

	
	
	<tr>
		<th><label class="text">开始时间：</label></th>
		<td>
	 <c:if test="${isEdit}" >
		<input type="text" class="dateinput ipttxt" name="start_time" dataType="date" required="true" readonly class="dateinput" value="<html:dateformat pattern="yyyy-MM-dd" d_time="${limitBuy.start_time}"/>" />

	</c:if>	
	 <c:if test="${!isEdit}" >
		<input type="text" class="dateinput ipttxt" name="start_time" dataType="date" required="true" readonly class="dateinput"/>
	</c:if>		
		<select  class="ipttxt"  name="start_hour" id="start_hour">
		<%for( int i=0;i<24;i++ ){%>
			<option value="<%=i %>"><%=i %></option>
		<%} %>
		</select>时
		 </td>
	</tr>
	
	<tr>
		<th><label class="text">结束时间：</label></th>
		<td>
		 <c:if test="${isEdit}" >
		<input type="text" class="dateinput ipttxt" name="end_time" dataType="date" required="true" readonly class="dateinput" value="<html:dateformat pattern="yyyy-MM-dd" d_time="${limitBuy.end_time}"/>"/>
		</c:if>	
 		<c:if test="${!isEdit}" >
		<input type="text" class="dateinput ipttxt" name="end_time" dataType="date" required="true" readonly class="dateinput"/>
		</c:if>	
		
		<select  class="ipttxt"  name="end_hour" id="end_hour">
		<%for( int i=0;i<24;i++ ){%>
			<option value="<%=i %>"><%=i %></option>
		<%} %>
		</select>
			时
		 </td>
	</tr>
	
	<tr>
		<th><label class="text">图片：</label></th>
		<td>
	 		<input type="file" name="imgFile" >
		</td>
	</tr>
	
	<c:if test="${limitBuy.img!=null &&limitBuy.img!=''}">
	<tr>
		<th></th>
		<td>
		<img src="${limitBuy.image }" />
		</td>
	</tr>
	</c:if>
	
	<tr>
		<th><label class="text">是否首页显示：</label></th>
		<td>
	 		<input type="radio" id="index_0" name="limitBuy.is_index" value="0" checked=true />否&nbsp;
	 		<input type="radio" id="index_1" name="limitBuy.is_index" value="1" />是&nbsp;
		</td>
	</tr>
	<tr>
		<th><label class="text">展示类别：</label></th>
		<td>
			<input type="radio" id="show_time_type_0" name="limitBuy.show_time_type" value="0" checked onclick="checkShowTime()"/>准时展示&nbsp;
			<input type="radio" id="show_time_type_1" name="limitBuy.show_time_type" value="1" onclick="checkShowTime()"/>提前展示&nbsp;
			天数<input type="text" class="ipttxt" id="show_day" name="limitBuy.show_day" readonly dataType="int" maxlength="4" style="width:40px" value="${limitBuy.show_day }"/>
		</td>
	</tr>
</table>

<div style="clear:both;padding-top:5px;"></div>


<div class="grid" style="width:75%">
	<div class="" style="margin-left: 80px;margin-bottom: 10px">
		<a href="javascript:;" class="graybtn1" id="addGoodsBtn">增加商品</a>
	</div>
 
 <table id="goodstable">
 <tr>
 <th>商品名称</th>
 <th>商品价格</th>
 <th>限购数量</th>
 <th>购物车限购数量&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
 <th>已购买数量</th>
 <th>操作&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
 </tr>
 <c:forEach items="${limitBuy.goodsList}" var="goods">
 	<tr>
 		<td>${goods.name }</td>
 		<td>
 			<input type="text" class="ipttxt"  name="price" value="${goods.price }" style="width: 80px;text-align: center;"/> <input type="hidden" name="goodsid" value="${goods.goodsid }"  />
 		</td>
 		<td>
 			<input type="text" class="ipttxt"  name="num" value="${goods.num }" style="width: 80px;text-align: center;"/>
 		</td>
 		<td>
 			<input type="text" class="ipttxt"  name="cart_num" value="${goods.cart_num }" style="width: 80px;text-align: center;"/>
 		</td>
 		<td>
 			<input type="text" class="ipttxt"  name="buy_num" value="${goods.buy_num }" style="width: 80px;text-align: center;" readonly/>
 		</td>
 		<td>
 			<a href="javascript:;" ><img class="delete" src="images/transparent.gif" ></a>
 		</td>
 	</tr>
</c:forEach> 	
 </table>
</div>


<div class="submitlist" align="center">
<table style="margin-left: 50px;">
	<tr>
		<td><input type="submit" value=" 确    定   " class="submitBtn" /></td>
	</tr>
</table>
</div>
</form>
</div>

<table id="temp_table" style="display:none">
 	<tr>
 		<td class="goodsname">
 		</td>
 		
 		<td>
 			<input type="text" class="ipttxt" name="price" style="width: 80px;text-align: center;"/> <input type="hidden" name="goodsid"  />
 		</td>
 		<td>
 			<input type="text" class="ipttxt" name="num" style="width: 80px;text-align: center;" />
 		</td>
 		<td>
 			<input type="text" class="ipttxt" name="cart_num" style="width: 80px;text-align: center;"/>
 		</td>
 		<td>
 			<input type="text" class="ipttxt" name="buy_num" style="width: 80px;text-align: center;"readonly/>
 		</td>
 		
 		<td>
 		<a  href="javascript:;" ><img class="delete" src="images/transparent.gif" ></a>
 		</td>
 	</tr>
</table>

<div id="goods_dlg"></div>
<script type="text/javascript">
function checkgoods(){
	if( $("#goodstable tr").size()==1 ){
		alert("请选择商品");
		$("#addGoodsBtn").click();
		return false;
	}
	if($("#show_time_type_1").attr("checked") && $("#show_day").val()==""){
		alert("请填写提前展示天数");
		$("#show_day").focus();
		return false;
	}else if($("#show_time_type_0").attr("checked")){
		$("#show_day").val("");
	}
	if($("#show_day").val()!="" && !checkNum($("#show_day").val())){
		alert("请正确填写提前展示天数");
		$("#show_day").focus();
		return false;
	}
	if(!checkGoods()){
		return false;
	}
	return true;
}

	$(function() {
		$("form.validate").validate();

	 	<c:if test="${isEdit}" >
			 $("#start_hour").val(${start_hour});
			 $("#end_hour").val(${end_hour});
			 $("#index_${limitBuy.is_index}").attr("checked",true);
			 $("#show_time_type_${limitBuy.show_time_type}").attr("checked",true);
			 if(${limitBuy.show_time_type}==1){
				 $("#show_day").attr("readonly",false);
			 }
	 	</c:if>
	});

function checkShowTime(){
	if($("#show_time_type_0").attr("checked")){
		$("#show_day").val("");
		$("#show_day").attr("readonly",true);
	}else if($("#show_time_type_1").attr("checked")){
		$("#show_day").attr("readonly",false);
		$("#show_day").focus();
	}
}

function checkGoods(){
	var prices = $("input[name=price]");
	for(var i=0;i<prices.length-1;i++){//减一是因为有一行同名的空行
		if(!checkFloat($(prices[i]).val())){
			alert("请正确填写商品价格");
			$(prices[i]).focus();
			return false;
		}
	}
	var nums = $("input[name=num]");
	for(var i=0;i<nums.length-1;i++){//减一是因为有一行同名的空行
		if(!checkNum($(nums[i]).val())){
			alert("请正确填写商品限购数量");
			$(nums[i]).focus();
			return false;
		}
	}
	var cart_nums = $("input[name=cart_num]");
	for(var i=0;i<cart_nums.length-1;i++){//减一是因为有一行同名的空行
		if(!checkNum($(cart_nums[i]).val())){
			alert("请正确填写购物车限购数量");
			$(cart_nums[i]).focus();
			return false;
		}
	}
	
	return true;
}

function checkNum(num){//判断是否为正整数
	var ex = /^\d+$/;
	return ex.test(num);
}

function checkFloat(num){//判断是否为正浮点数
	var ex = /^\d+(\.\d+)?$/;
	return ex.test(num);
}
</script>