<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<script type="text/javascript">

    $(function () {

        $("#effect_date,#fail_date").datepicker();

        $("#taxes_ratio").val('${goods.taxes_ratio}');
        
    });

</script>

<style>
.more_div {
	background: url(${context}/images/line.gif) center top no-repeat;
	text-align: center;
}

.more_btn {
	background: url(${context}/images/more_btn.gif) no-repeat;
	width: 142px;
	height: 21px;
	display: inline-block;
	color: #444;
	font-size: 12px;
	line-height: 21px;
}

.more_open {
	background: url(${context}/images/more_open.gif) no-repeat;
	width: 15px;
	height: 15px;
	display: inline-block;
	margin-right: 5px;
	vertical-align: middle;
}

.more_close {
	background: url(${context}/images/more_close.gif) no-repeat;
	width: 15px;
	height: 15px;
	display: inline-block;
	margin-right: 5px;
	vertical-align: middle;
}

.input_text {
	width: 160px;
}

ul.tab li {
	min-width: 70px;
}
</style>
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/products_ecs.js"></script>
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/select_goods_supplier.js"></script>
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/select_goods_agreement.js"></script>
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/control_value.js"></script>

<div class="input">
	<form method="post" name="productForm" action="${actionName }" id="productForm"
		class="validate">

		<c:if test="${is_edit }">
			<input type="hidden" name="goods.goods_id"
				value="${goodsView.goods_id }" />
			<input type="hidden" name="goods_id_e" value="${goodsView.goods_id }" />
		</c:if>

		<div style="display: block;" class="goods_input" id="productTabli">
			<div class="tab-bar" style="position: relative;">
				<ul class="tab">
					<li tabid="0" class="active">
						基本信息
					</li>
					
					<c:forEach var="tab" items="${tabs}">
						<c:if test ="${tab.tab_id=='3'}">
						<li tabid="${tab.tab_id}"
						<c:if test ="${tab.tab_stype_code!=null}"> li_type='stype' tabid='no' </c:if > >
							货品${tab.tab_name}
						</li>
						</c:if>
					</c:forEach>
					<li tabid='15' style="display:none;">合约套餐关系配置</li>
				</ul>
			</div>
			<div class="tab-page">
				${goodstype} ${album}  
				
				<div class='clear'></div>
				<div tabid="tab_0" class="tab-panel">
					<table class="form-table" style="width: 100%; float: left"
						id='base_table'>

						<tr>
							<th>
								<input type="hidden" id="adjunct_goods_type_input"
									value="${goodsView.goods_type}">
								<span class="red">*</span>货品型号：
							</th>
							<td id="model_code_td">
								<select name="goods.model_code" style="width:200px;" class="ipttxt1" id="model_code"  required="true">
									<option value="">
										请选择货品型号
									</option>
									
								</select>
								&nbsp;&nbsp;
								<span class="help_icon" helpid="model_code"></span>&nbsp;&nbsp;
								
							</td>
						</tr>
						
						<c:if test="${is_edit}">
						<tr id="sku_tr" style="<c:if test="${goods.have_spec==1}">display:none;</c:if>">
							<th>
								SKU：
							</th>
							<td>
								<input type="text" name="goods.sku" id="sku" required="true"
									style="width:200px;" class="top_up_ipt" value="${goodsView.sku}" readonly />
								<input type="hidden" name="goods.product_id" id="product_id" required="true"
									style="width:200px;" class="top_up_ipt" value="${goodsView.product_id}" readonly />
							</td>
						</tr>
						</c:if>
						 <%-- <tr id="colorTr" style="<c:if test="${goods.have_spec==1}">display:none;</c:if>">
							<th>
								颜色：
							</th>
							<td>
								<select id="color" name="color" <c:if test="${page_type=='view' }">disabled="true"</c:if> >
									<option value="">--请选择--</option>
									<c:forEach items="${colorList}" var="color">
										<option value="${color.value }" <c:if test="${color.value==goodsView.color }">selected="true"</c:if>>${color.value_desc }</option>
									</c:forEach>
								</select>
							</td>
						</tr>   --%>
						<tr>
							<th>
								<span class="red">*</span>货品名称：
							</th>
							<td>
								<input type="text" id="goods_name" name="goods.name" style="width:300px;" class="top_up_ipt"
									required="true" value="${goodsView.name}" />
								<input type="hidden" name="goods.type" value="product">
							</td>
						</tr>
						<tr id="snTr" style="<c:if test="${goods.have_spec==1}">display:none;</c:if>">
							<th>
								条形码：
							</th>
							<td id="machine_code_td">
								<input type="text" id="goods_sn" name="goods.sn" style="width:200px;" class="top_up_ipt"
									value="${goodsView.sn}" readonly  <c:if test="${is_edit }">readonly</c:if>/>
								&nbsp;&nbsp;
								<span class="help_icon" helpid="goods_sn"></span>&nbsp;&nbsp;
								<a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1"  id="selectTagBtn"><span>选择</span></a>
							</td>
						</tr>
						<tr style="display:none;">
							<th>
								<span class="red">*</span>市场价(元)：
							</th>
							<td>
								<input type="text" name="goods.mktprice" id="mktprice"
									class="top_up_ipt" dataType="float" required="true"
									value="${goodsView.mktprice}" />
							</td>
						</tr>
						<tr style="display:none;">
							<th>
								<span class="red">*</span>销售价(元)：
							</th>
							<td>
								<input type="text" name="goods.price" id="price"
									class="top_up_ipt" dataType="float" required="true"
									value="${goodsView.price}" />
								&nbsp;&nbsp;
								<span class="help_icon" helpid="goods_price"></span>
							</td>
						</tr>
						<tr style='display: none;'>
							<th>
								<span class="red">*</span>生效时间：
							</th>
							<td>
								<input type="text" name="goods.effect_date" id="effect_date"
									maxlength="60" class="dateinput top_up_ipt" dataType="date"
									value="${goods.effect_date}" readonly="readonly" />
							</td>
						</tr>

						<tr style='display: none;'>
							<th>
								<span class="red">*</span>失效时间：
							</th>
							<td>
								<input type="text" name="goods.fail_date" id="fail_date"
									maxlength="60" class="dateinput top_up_ipt" dataType="date"
									value="${goods.fail_date}" readonly="readonly" />
							</td>
						</tr>
						<tr style="display:none;">
							<th>
								<span class="red">*</span>售卖对象：
							</th>
							<td>
								<c:forEach items="${lvList}" var="lv">
									<label>
										<input type="checkbox" value="${lv.lv_id }" name="lvidArray"
											<c:if test="${lv.selected == '00A'}">checked="checked"</c:if>>
										${lv.name }
									</label>
								</c:forEach>
							</td>
						</tr>
						<tr style="display:none;">
							<td colspan=3 id='commonPriceTd'>
							</td>
						</tr>

						<tr style="display:none;">
							<th>
								<span class="red">*</span>是否上架销售：
							</th>
							<td>
								<!-- || (goods.market_enable != 1 && goods.market_enable != 0) -->
								<input type="radio" name="goods.market_enable"
									<c:if test="${goods.market_enable == 1 || goods.market_enable=='' || goods.market_enable==null}"> checked="checked" </c:if>
									value="1" class="market_enable" />
								是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="goods.market_enable"
									<c:if test="${goods.market_enable ==0}"> checked="checked" </c:if>
									value="0" class="market_enable" />
								否
							</td>
						</tr>

						<tr style="display:none;" id='storeTr1'>
							<th>
								库存(件)：
							</th>
							<td>
								<input type="text" name="goods.store" id="store"
									class="top_up_ipt" dataType="int" value="${goodsView.store}" />
							</td>
						</tr>
						<tr style="display:none;">
							<th>
								重量(克)：
							</th>
							<td>
								<input type="text" name="goods.weight" id="weight"
									class="top_up_ipt" dataType="float" value="${goodsView.weight}" />
							</td>
						</tr>
						<tr style="display:none;">
							<th>
								积分：
							</th>
							<td>
								<input type="text" name="goods.point" id="point"
									class="top_up_ipt" dataType="float" value="${goodsView.point}" />
							</td>
						</tr>
						<tr style="display: none;">
							<th>
								<span class="red">*</span>装箱量：
							</th>
							<td>
								<html:selectdict curr_val="${goodsView.unit}" name="goods.unit"
									id="unit" attr_code="es_goods_unit"></html:selectdict>
							</td>
						</tr>

						<tr style="display: none;">
							<th>
								最低下单量(箱)：
							</th>
							<td>
								<input type="text" name="goods.min_num" id="min_num"
									class="top_up_ipt" dataType="int" value="${goodsView.min_num}" />
							</td>
						</tr>
					</table>
					<table>
					
					</table>
					${goodsspec }
					<div class="append_tab_div">
					</div>
					<!-- <div class="inputBox">
						<div class="textareaBox">
							<textarea class="textareaClass" name="goods.intro" id='intro'
								cols="100" rows="3" style="width: 98%; height: 200px;">
					            <c:out value="${goodsView.intro}" escapeXml="false"></c:out>
					        </textarea>
						</div>
						${goodstag}
						<table> 
							<tr>
								<td rowspan="2" style="margin-right: 240px; text-align: right;">

									<a id="nextStep" name="nextStep" formId="productForm" class="greenbtnbig" href="javascript:void(0)">下一步</a>
								</td>
							</tr>
						</table>
					</div> -->
					<div style="text-align: center;padding-top:30px;">
						<a id="lastStep" name="lastStep" formId="productForm" class="greenbtnbig" href="javascript:void(0)">返回</a>
						<a id="nextStep" name="nextStep" formId="productForm" class="greenbtnbig" href="javascript:void(0)">下一步</a>
					</div>
				</div>
				${contractOffer }
				<!-- 商品扩展属性标签,自定义处理 -->
				<html:goodsplugin></html:goodsplugin>

				${goodsseo}  
				${goodscomplex} ${goodsadjunct}  ${goods_price}
				${xianGuoGoodsPlugin}
				<input type="hidden" id="adminUserLanid"
					value="${adminUser.lan_id }" />
				<input type="hidden" id="adminUserid" value="${adminUser.userid }" />
				<input type="hidden" id="goodsStaff_no"
					value="${goodsView.staff_no }" />
				<input type="hidden" id="goodsTypeValue"
					value="${goodsType.type_id}" />
				<input type="hidden" name="goods.search_key" id="search_key"
									class="top_up_ipt" value="${goodsView.search_key}" />
				<table id='publish_table_0' style='display: none;'>
					<tr>
						<td rowspan="2" style="margin-right: 240px; text-align: center;">
							<a id="preStep" formId="productForm" class="greenbtnbig" href="javascript:void(0)">上一步</a>
							<a id="" name="nextStep" formId="productForm" class="greenbtnbig" href="javascript:void(0)">下一步</a>
							<a id="finishStep" class="greenbtnbig" href="javascript:void(0)">确定</a>
							<a href="javascript:void(0)" style="margin-right:10px; display: none" class="greenbtnbig" formId="productForm" id="batchProdBtn"><span>批量配置多个货品</span></a>
						</td>
					</tr>
				</table>
			</div>
	</form>
</div>
<div id="offerListDialog"></div>
<div id="brand_dialog">
<div id="tagsDialog"></div>
<div id="batchProdAddBtn_dialog"></div>
</div>
<div id="nowLoginUserType" style="display: none;"><%=ManagerUtils.getcurrType()%>
</div>
<link rel="stylesheet" href="<%=request.getContextPath()%>/shop/admin/css/order.css" type="text/css">
<style type="text/css">
.division {
	border-width: 0px;
	background: none;
}
</style>
<script type="text/javascript">
var sn = '${goodsView.sn}';
$(function(){
	$("#batchProdBtn").bind("click",function(){
		var cat_id = $("#cat_id").val();
    	 var goods_color=$("#dc_goods_color").find("option:selected").val(); 
    	 if(goods_color==null){
    		 alert("请选择货品颜色！");
    		 return ;
    	 }
		//定制机需要校验唯一性---zengxianlian
		var postData = "";
		if("690002000"==cat_id){
			var goodsSn=$("#goods_sn").val();
			postData="typeCode=10000&sn="+goodsSn;
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
						   var url = "goods!showProdConfigList.do?ajax=yes";
							$("#batchProdAddBtn_dialog").load(url, function (responseText) {
									Eop.Dialog.open("batchProdAddBtn_dialog");
							});
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
			 var url = "goods!showProdConfigList.do?ajax=yes";
				$("#batchProdAddBtn_dialog").load(url, function (responseText) {
						Eop.Dialog.open("batchProdAddBtn_dialog");
				});
		} 
	});
	Eop.Dialog.init({id:"batchProdAddBtn_dialog",modal:true,title:"批量货品个性信息配置",width:"1000px"});
});
</script>
<c:if test="${page_type=='view'}">
	<script type="text/javascript">

        $(function () {

            // add by wui
            $("#uploadProgress").hide();

            //查看商品信息隐藏不必要的
            $('#up').parent().hide();//删除上传按钮
            $('#cat_id').attr("disabled", "disabled");//禁用下拉列表框
            $('#type_id').attr("disabled", "disabled");
            $('#model_code').attr("disabled", "disabled");
            $('#brand_id').attr("disabled", "disabled");
            //$('input[type="text" class="ipttxt" ]').attr("disabled","disabled");
            $('input[type="text"]').attr("disabled", "disabled");
            $('input[type="checkbox"]').attr("disabled", "disabled");
            $('input[type="radio"]').attr("disabled", "disabled");
            $('#charge_type').attr("disabled", "disabled");
            $('#type_id').val($('#goodsTypeValue').val());
            $('#cat_id').val($('#goodsCatIdValue').val());
            //删除确定按键
            //$('.greenbtnbig').parent().parent().remove();
            //隐藏图片预览下的删除按键
            $('.deleteBtn').hide();
            $("a[class='deleteBtn']").hide();
            $('input[type="button"]').hide();
            $("a").hide();
            $("#lastStep").show();//显示返回按钮
        });

    </script>
</c:if>
<c:if test="${is_edit==true}">

	<script type="text/javascript">
        $("[tabid='10']").show();
        $(function () {
            $("#effect_date").val($("#effect_date").val().substring(0, 10));
            $("#fail_date").val($("#fail_date").val().substring(0, 10));

            //$("[tabid='no']").hide();
            //$("[li_type='stype']").hide();
        });
    </script>
</c:if>
<c:if test="${is_edit==false}">
	<script type="text/javascript">
		$(function(){
			$("[tabid='10']").hide();
			$("#cat_id").change(function(){
				var val = $(this).val();
				$("#goods_sn").val("");
				//手机跟手机的子类型才需要选择条形码,其他是手动输入条形码
				if("10000"==val ||"690002000"==val){
					$("#goods_sn").attr("readonly","readonly");
					$("#selectTagBtn").show();
				}else{
					$("#goods_sn").removeAttr("readonly");
					$("#goods_sn").val(sn);
					$("#selectTagBtn").hide();
				}
			});
		});
    </script>
</c:if>




