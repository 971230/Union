<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
$(function(){
	//初始化选择按钮
	$("#selectTagBtn1").click(function(){
		ProductPerAddEdit.showSelectTags();
	});
	$("#confirmProdPerBtn").click(function(){
		var product_name=$("#per_product_name").val();
		var goods_sn=$("#per_goods_sn").val();
		var dc_goods_color=$("#per_goods_color").find("option:selected").val();
		var goods_color=$("#per_goods_color").find("option:selected").text();
		var input_prod_id=$("#input_prod_id").val();
		if(product_name==""){
			alert("请填写货品名称");
			return;
		}
		if(goods_sn==""){
			alert("请填写条形码");
			return;
		}
		if(dc_goods_color==""){
			alert("请选择颜色");
			return;
		}
		var cat_id = $("#cat_id").val();
		//定制机需要校验唯一性---zengxianlian
		var postData = "";
    	if("690002000"==cat_id){
			postData="typeCode=10000&sn="+goods_sn;
			$.ajax({
				   url:"goods!checkSaveAdd.do",
				   type:"POST",
				   //dataType:"json",
				   dataType:"html",
				   data :postData,
				   success:function(reply){
					   reply=reply.substring(reply.indexOf("<body >")+7,reply.indexOf("</body>"));
					   	var json= eval("(" + reply+")");
					   if(1==json.result){
						   var jsonData={};
					    	 jsonData.goods_name=product_name;
					    	 jsonData.goods_sn=goods_sn;
					    	 jsonData.dc_goods_color=dc_goods_color;
					    	 jsonData.goods_color=goods_color; 
					    	 jsonData.input_prod_id=input_prod_id;
					    	 ProductPerAddEdit.addConfigProduct(jsonData);
					    	 Eop.Dialog.close("addProdPerConfig_dialog");
					   }else{
						   alert(json.message);
						   return;
					   }
				   },
				   error:function(reply){
					   alert("出错了");
				   }
			   });
		}else{
			var jsonData={};
	    	 jsonData.goods_name=product_name;
	    	 jsonData.goods_sn=goods_sn;
	    	 jsonData.dc_goods_color=dc_goods_color;
	    	 jsonData.goods_color=goods_color; 
	    	 jsonData.input_prod_id=input_prod_id;
	    	 ProductPerAddEdit.addConfigProduct(jsonData);
	    	 Eop.Dialog.close("addProdPerConfig_dialog");
		} 
	});
	$("#cancleProdPerBtn").click(function(){
		Eop.Dialog.close("addProdPerConfig_dialog");
	});
});
</script>
<div class="input">
	<table class="form-table" style="width: 100%; float: left">
		<tr>
			<th><span class="red">*</span>货品名称：</th>
			<td><input type="text" id="per_product_name"  name="productName"
				style="width: 300px;" class="top_up_ipt" required="true"/> <input type="hidden" name="input_prod_id" id="input_prod_id"></td>
		</tr>
		<tr>
			<th><span class="red">*</span>条形码：</th>
			<td id="machine_code_td">
								<input type="text" id="per_goods_sn" style="width:200px;" class="top_up_ipt" value="${goodsView.sn}" <c:if test="${cat_id =='690002000'}">readonly</c:if>/>
								&nbsp;&nbsp;
								<span class="help_icon" helpid="per_goods_sn"></span>&nbsp;&nbsp;
								<a href="javascript:void(0)" style="margin-right:10px; <c:if test="${cat_id =='690001000'}">display:none</c:if>" class="graybtn1"  id="selectTagBtn1"><span>选择</span></a>
			</td>
		</tr>
		<tr>
			<th><span class="red">*</span>颜色：</th>
			<td>
			<select name="paramvalues" style="width:160px;" id="per_goods_color">
									<option value="">--请选择--</option>
									<c:forEach items="${goodsParam.dropdownValues }" var="dropdown">
									    <option value="${dropdown.value}" >${dropdown.value_desc}</option>
									</c:forEach>
								</select>
			</td>
		</tr>
		<tr>
		<td colspan="2"><input type="hidden"/></td>
		</tr>
		<tr>
		<td colspan="2"><input type="hidden"/></td>
		</tr>
		<tr>
		<td colspan="2"><input type="hidden"/></td>
		</tr>
	</table>
</div>
<div style="clear: both; padding-top: 5px;"></div>
<div style="text-align:center;">
<a href="javascript:void(0);" style="margin-right:10px;" class="graybtn1" <c:if test="${is_edit =='updatePer'}">id="confirmProdPerBtn1"</c:if><c:if test="${is_edit !='updatePer'}">id="confirmProdPerBtn"</c:if>><span>确定</span></a>
<a href="javascript:void(0);" style="margin-right:10px;" class="graybtn1" id="cancleProdPerBtn"><span>取消</span></a>
</div>
