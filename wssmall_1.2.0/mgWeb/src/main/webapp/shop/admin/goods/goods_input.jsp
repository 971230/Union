<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">

    $(function () {

        $("#effect_date,#fail_date").datepicker();

        $("#taxes_ratio").val('${goods.taxes_ratio}');
        $("[class='tab'] li").bind("click", function () {
            if ($(this).attr("tabid") == 0) {
                $("#publish_table_0").hide();
            } else {
                $("#publish_table_0").show();
            }
        })

        //设置规格隐藏 add by wui
       // $("[tabid='6']").hide();
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
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/goods.js"></script>

<script type="text/javascript" src="${ctx}/shop/admin/goods/js/select_goods_supplier.js"></script>
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/select_goods_agreement.js"></script>
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/control_value.js"></script>

<div class="input">
	<form method="post" name="theForm" action="${actionName}" id="theForm"
		class="validate">

		<c:if test="${is_edit }">
			<input type="hidden" name="goods.goods_id"
				value="${goodsView.goods_id }" />
			<input type="hidden" name="goods_id_e" value="${goodsView.goods_id }" />
		</c:if>

		<div style="display: block;" class="goods_input">
			<div class="tab-bar" style="position: relative;">
				<ul class="tab">
					<li tabid="0" class="active">
						基本信息
					</li>
					<c:forEach var="tab" items="${tabs}">
						<li tabid="${tab.tab_id}" li_stype_code="${tab.tab_stype_code }"
						<c:if test ="${tab.tab_stype_code!=null}"> li_type='stype' tabid='no' </c:if >
							style='<c:if test ="${tab.tab_stype_code!=null and goodsView.stype_code!=tab.tab_stype_code}"> display: none; </c:if >'>
							${tab.tab_name}
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="tab-page">
				${goodstype} ${album}

				<div class='clear'></div>
				<div tabid="tab_0" class="tab-panel"
					style='border: 0px solid red; <# if founder =="1">top : 210px; <# else >top: 190px; </# if > position : absolute; z-index: 8;'>
					<table class="form-table" style="width: 100%; float: left"
						id='base_table'>

						<tr>
							<th>
								<input type="hidden" id="adjunct_goods_type_input"
									value="${goodsView.goods_type}">
								<span class="red">*</span>产品名称：
							</th>
							<td>
								<input type="text" name="goods.name" id="goods_name"
									class="top_up_ipt" required="true" value="${goodsView.name }" />

							</td>
						</tr>
						<tr>
							<th>

								产品简称：
							</th>
							<td>
								<input type="text" name="goods.simple_name" id="simple_name"
									class="top_up_ipt" value="${goodsView.simple_name }" />

							</td>
						</tr>
						<tr>
							<th>
								<span class="red">*</span>产品编号：
							</th>
							<td>
								<input type="text" name="goods.sn" class="top_up_ipt"
									required="true" value="${goodsView.sn}" />

							</td>
						</tr>
						<tr>
							<th>
								<span class="red">*</span>市场价(元)：
							</th>
							<td>
								<input type="text" name="goods.mktprice" id="mktprice"
									class="top_up_ipt" dataType="float" required="true"
									value="${goodsView.mktprice}" />
							</td>
						</tr>
						<tr>
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
						<tr>
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
						<tr>
							<td colspan=3 id='commonPriceTd'>
							</td>
						</tr>

						<tr>
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

						<tr id='storeTr'>
							<th>
								库存(件)：
							</th>
							<td>
								<input type="text" name="goods.store" id="store"
									class="top_up_ipt" dataType="int" value="${goodsView.store}" />
							</td>
						</tr>
						<tr>
							<th>
								重量(克)：
							</th>
							<td>
								<input type="text" name="goods.weight" id="weight"
									class="top_up_ipt" dataType="float" value="${goodsView.weight}" />
							</td>
						</tr>
						<tr>
							<th>
								积分：
							</th>
							<td>
								<input type="text" name="goods.point" id="point"
									class="top_up_ipt" dataType="float" value="${goodsView.point}" />
							</td>
						</tr>
						<!-- 
						<tr>
							<th>
								<span class="red">*</span>是否需要确认：
							</th>
							<td>
								<input type="radio" name="goodsRules.insure"
									<c:if test="${goodsRules.insure != 'no' }"> checked="checked" </c:if>
									value="yes" />
								是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="goodsRules.insure"
									<c:if test="${goodsRules.insure == 'no' }"> checked="checked" </c:if>
									value="no" />
								否
							</td>
						</tr>
						<tr>
							<th>
								<span class="red">*</span>是否需要支付：
							</th>
							<td>
								<input type="radio" name="goodsRules.pay"
									<c:if test="${goodsRules.pay != 'no' }"> checked="checked" </c:if>
									value="yes" />
								是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="goodsRules.pay"
									<c:if test="${goodsRules.pay == 'no' }"> checked="checked" </c:if>
									value="no" />
								否
							</td>
						</tr>
						<tr>
							<th>
								<span class="red">*</span>是否需要受理：
							</th>
							<td>
								<input type="radio" name="goodsRules.accept"
									<c:if test="${goodsRules.accept != 'no' }"> checked="checked" </c:if>
									value="yes" />
								是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="goodsRules.accept"
									<c:if test="${goodsRules.accept == 'no' }"> checked="checked" </c:if>
									value="no" />
								否
							</td>
						</tr>
						<tr>
							<th>
								<span class="red">*</span>是否需要发货：
							</th>
							<td>
								<input type="radio" name="goodsRules.delivery"
									<c:if test="${goodsRules.delivery != 'no' }"> checked="checked" </c:if>
									value="yes" />
								是&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" name="goodsRules.delivery"
									<c:if test="${goodsRules.delivery == 'no' }"> checked="checked" </c:if>
									value="no" />
								否
							</td>
						</tr>
						 -->
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
					<div class="append_tab_div">
					</div>
					<div class="inputBox">
						<div class="textareaBox">
							<textarea class="textareaClass" name="goods.intro" id='intro'
								cols="100" rows="8" style="width: 98%; height: 300px;">
					            <c:out value="${goodsView.intro}" escapeXml="false"></c:out>
					        </textarea>
						</div>
						${goodstag}
						<table>
							<tr>
								<td rowspan="2" style="margin-right: 240px; text-align: right;">

									<a class="greenbtnbig" href="javascript:void(0)">确定发布</a>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<!-- 商品扩展属性标签,自定义处理 -->
				<html:goodsplugin></html:goodsplugin>

				${goodsseo}  
				${goodscomplex} ${goodsadjunct} ${goodsspec} ${goods_price}
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
						<td rowspan="2" style="margin-right: 240px; text-align: right;">
							<a class="greenbtnbig" href="javascript:void(0)">确定发布</a>
						</td>
					</tr>
				</table>
			</div>
	</form>
</div>

<div id="brand_dialog">
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

<script>
	 $(function () {
        //初始化商品会员价
        var goods_id = '${goods.goods_id}';
        var datas = "";
        if (goods_id)
            datas = "goodsid=" + goods_id;
        //add by wui 隐藏价格 tab页面
        $("li[tabid='1']").hide();
		$.ajax({
            url: ctx + '/shop/admin/memberPrice!getLvPriceJson.do?ajax=yes',
            data: datas,
            dataType: 'json',
            success: function (result) {
                if (result.result == 1) {
                    var priceData = result.priceData;
                    var table = $('#body1_price');
                    $.each(priceData,function(i,price){
							var tpl = $('#price_data_tpl').clone() ;
							tpl.attr('id'  ,'') ;
							tpl.show() ;
							$('td[name="price.name"]' ,tpl ).html(price.name) ;
							$('input[name="price.lvid"]' ,tpl ).val(price.lv_id) ;
							$('input[name="price.lvPrice"]' ,tpl ).val(price.lv_price).attr("lv_id",price.lv_id) ;
							tpl.attr("attr_type","tr_attr_price");
							var options = "";
							$.each(result.discountData,function(i,dd){
								options += "<option value='"+dd.pkey+"'";
								if(dd.pkey==price.lv_discount){
									options += " selected='selected' ";
								}
								options += ">"+dd.pname+"</option>"
							});
							$('select' ,tpl ).html(options) ;
							table.append(tpl) ;
					});
					
                    //add by wui
                    $("#commonPriceTd").append($("#price_div_tab").show());
                    $("[name='price.lvid'][value='0']").closest("tr").find("input[type='text']").bind("blur", function () {
                        $("#price").val($(this).val())
                    })
                    
                    $("[name='price.lvid'][value='0']").closest("tr").find("input[type='text']").bind("blur",function(){
					$("#price").val($(this).val())
					
				});
				
				$("[name='price.lv_discount']").bind("change",function(){
					var mkprice = $("input[name='goods.mktprice']").val();
					if(!mkprice)
						mkprice = 0;
					var dd = $(this).val();
					$(this).closest("tr").find("input[name='price.lvPrice']").val((mkprice*dd).toFixed(2));
				});
				
				$("input[name='goods.mktprice']").bind("blur",function(){
					changePrice();
				});
				
				function changePrice(){
					var lvprices = $("input[name='price.lvPrice']");
					var mkprice = $("input[name='goods.mktprice']").val();
					if(!mkprice)
						mkprice = 0;
					$.each(lvprices,function(idx,item){
						var dd = $(item).closest("tr").find("select").val();
						if(dd!=-1){
							$(item).val((mkprice*dd).toFixed(2));
						}
					});
				};
				
                }
                else {
                    alert("生成会员价格出错：" + result.message);
                }
            },
            error: function () {
                alert("生成会员价格出错");
            }
        });
        

    })
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
            //$('input[type="text" class="ipttxt" ]').attr("disabled","disabled");
            $('input[type="text"]').attr("disabled", "disabled");
            $('input[type="checkbox"]').attr("disabled", "disabled");
            $('input[type="radio"]').attr("disabled", "disabled");
            $('#charge_type').attr("disabled", "disabled");
            $('#type_id').val($('#goodsTypeValue').val());
            $('#cat_id').val($('#goodsCatIdValue').val());
            //删除确定按键
            $('.greenbtnbig').parent().parent().remove();
            //隐藏图片预览下的删除按键
            $('.deleteBtn').hide();
            $("a[class='deleteBtn']").hide();
            $('input[type="button"]').hide();
            $("a").hide();
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
        $("[tabid='10']").hide();
    </script>
</c:if>




