<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">

    $(function () {

        $("#effect_date,#fail_date").datepicker();

        $("#taxes_ratio").val('${goods.taxes_ratio}');
        $("#price_div_tab").show();
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
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/goods_ecs.js"></script>
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

		<div style="display: block;" class="goods_input">
			<div class="tab-bar" style="position: relative;">
				<ul class="tab">
					<li tabid="0" class="active">
						基本信息
					</li>
					
					<c:forEach var="tab" items="${tabs}">
						<c:if test ="${tab.tab_id=='3'}">
						<li tabid="${tab.tab_id}" li_stype_code="${tab.tab_stype_code }" 
						<c:if test ="${tab.tab_stype_code!=null}"> li_type='stype' tabid='no' </c:if > >
							商品${tab.tab_name}
						</li>
						</c:if>
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
						<c:if test="${is_edit }">
						<tr>
							<th>
								商品编码：
							</th>
							<td>
								<input type="text" id="sku" name="goods.sku" style="width:200px" class="top_up_ipt"
									value="${goodsView.sku}" readonly />
							</td>
						</tr>
						</c:if>
						<tr>
							<th>
								<span class="red">*</span>商品名称：
							</th>
							<td>
								<input type="text" id="goods_name" name="goods.name" style="width:300px" class="top_up_ipt"
									required="true" value="${goodsView.name}" />
								<input type="hidden" name="goods.type" value="goods">
								<input type="hidden" id="model_code" name="goods.model_code" value="${goodsView.model_code }">
							</td>
						</tr>
						<tr>
							<th>
								商品简称：
							</th>
							<td>
								<input type="text" id="simple_name" name="goods.simple_name" style="width:300px" class="top_up_ipt"
									value="${goodsView.simple_name}" />
							</td>
						</tr>
						
						<tr>
							<th>
								市场价(元)：
							</th>
							<td>
								<input type="text" name="goods.mktprice" id="mktprice"
									style="width:200px" class="top_up_ipt" dataType="float"
									value="${goodsView.mktprice}" />
							</td>
						</tr>
						<tr>
							<th>
								<span class="red">*</span>销售价(元)：
							</th>
							<td>
								<input type="text" name="goods.price" id="price"
									style="width:200px" class="top_up_ipt" dataType="float" required="true"
									value="${goodsView.price}" />
								&nbsp;&nbsp;
								<span class="help_icon" helpid="goods_price"></span>
							</td>
						</tr>
						<tr>
							<th>
								重量(克)：
							</th>
							<td>
								<input type="text" name="goods.weight" id="weight"
									style="width:200px" class="top_up_ipt" dataType="float" value="${goodsView.weight}" />
							</td>
						</tr>
						<tr >
							<th>
								生效时间：
							</th>
							<td>
								<input type="text" name="goods.effect_date" id="effect_date"
									maxlength="60" class="dateinput top_up_ipt" dataType="date"
									value="${goods.effect_date}" readonly="readonly" />
							</td>
						</tr>

						<tr >
							<th>
								失效时间：
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
								<label>
									<input type="checkbox" value="1" name="lvidArray"
										checked="checked">
									普通会员
								</label>
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
						<tr>
							<th>
								商品包：
							</th>
							<td>
								<input type="hidden" name="tag_id" id="tag_ids"
									class="top_up_ipt"  value="${goodsView.tag_id}" />
								<input type="text" name="tag_names" id="tag_names"
									style="width:300px" class="top_up_ipt" value="${goodsView.tag_name}" readonly="readonly" />
							    <a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1"  id="selectTagBtn"><span>选择</span></a>
							</td>
						</tr>
						
						<tr>
							<th>
								总部活动、商品编码：
							</th>
							<td>
								<input type="text" name="goods.act_code" id="act_code_zb"
									style="width:200px" class="top_up_ipt" dataType="String" value="${goodsView.act_code}" />
							</td>
						</tr>
						
						<tr>
							<th>
								总部产品编码：
							</th>
							<td>
								<input type="text" name="goods.prod_code" id="prod_code_zb"
									style="width:200px" class="top_up_ipt" dataType="String" value="${goodsView.prod_code}" />
							</td>
						</tr>
						
						<tr>
							<th>
								华盛物料号：
							</th>
							<td>
								<input type="text" name="goods.matnr" id="hs_matnr"
									style="width:200px" class="top_up_ipt" dataType="String" value="${goodsView.matnr}" />
							</td>
						</tr>
						<c:if test="${is_edit==false || page_type =='view'}">
						<th>
							当前生效活动:
						</th>
						<td>
							<textarea readonly="readonly" style="width:300px" class="top_up_ipt" >${goodsView.activity_names}</textarea>
						</td>
						
						</c:if>
						<tr>
							<td>
							</td>
							<td>
								<a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1"  id="productOpenBtn"><span>添加货品</span></a>
							</td>
						</tr>
					</table>
					<table>
					
					</table>
					
					${goodsProduct }
					<div class="append_tab_div">
					</div>
					<!-- 活动元素信息 -->
					<div class="append_communityTab_div">
						<table class="form-table" style="width: 100%; float: left" id="community_tab">
							<tr>
								<td>
								</td>
								<td>
									<a href="javascript:void(0)" style="margin-left:105px;" class="graybtn1"  id="elementOpenBtn"><span>同步活动元素信息</span></a>
								</td>
							</tr>
						</table>
						<div id="elementbody" class="grid" style="width:100%;">
							<table cellspacing="0" cellpadding="0" border="0">
								<thead id="elementNodeTitle">
									<tr>
										<th style="width: 120px;">活动ID</th>
										<th style="width: auto;">活动名称</th>
										<th style="width: auto;">元素ID</th>
										<th style="width: auto;">元素名称</th>
										<th style="width: auto;">元素类型</th>
										<th style="width: auto;">终端类型</th>
										<th style="width: auto;">终端名称</th>
									</tr>
								</thead>
								<tbody id="elementNode">
									<c:forEach items="${elementList }" var="cmut">
										<tr>
											<td><input type="hidden" name="scheme_id" id="${cmut.scheme_id }" value="${cmut.scheme_id }"/>${cmut.scheme_id }</td>
											<td><input type="hidden" name="scheme_name" id="${cmut.scheme_name }" value="${cmut.scheme_name }"/>${cmut.scheme_name }</td>
											<td><input type="hidden" name="element_id" id="${cmut.element_id }" value="${cmut.element_id }"/>${cmut.element_id }</td>
											<td><input type="hidden" name="element_name" id="${cmut.element_name }" value="${cmut.element_name }"/>${cmut.element_name }</td>
											<td><input type="hidden" name="element_type" id="${cmut.element_type }" value="${cmut.element_type }"/>${cmut.element_type_n }</td>
											<td><input type="hidden" name="mobile_type" id="${cmut.mobile_type }" value="${cmut.mobile_type }"/>${cmut.mobile_type }</td>
											<td><input type="hidden" name="terminal_name" id="${cmut.terminal_name }" value="${cmut.terminal_name }"/>${cmut.terminal_name }</td>
										</tr>
									
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<!-- 商品关联小区 -->
					<div class="append_communityTab_div">
						<table class="form-table" style="width: 100%; float: left" id="community_tab">
							<tr>
								<td>
								</td>
								<td>
									<a href="javascript:void(0)" style="margin-left:105px;" class="graybtn1"  id="communityOpenBtn"><span>关联小区</span></a>
								</td>
							</tr>
						</table>
						<div id="communitybody" class="grid" style="width:65%;">
							<table cellspacing="0" cellpadding="0" border="0">
								<thead id="communityNodeTitle">
									<tr>
										<th style="width: 120px;">小区编号</th>
										<th style="width: auto;">小区名称</th>
										<th style="width: auto;">操作</th>
									</tr>
								</thead>
								<tbody id="communityNode">
									<c:forEach items="${cmutList }" var="cmut">
										<tr>
											<td><input type="hidden" name="community_code" id="${cmut.community_code }" value="${cmut.community_code }"/>${cmut.community_code }</td>
											<td><input type="hidden" name="community_name" id="${cmut.community_name }" value="${cmut.community_name }"/>${cmut.community_name }</td>
											<td><a href="javascript:;"><img  class="delete" src="${ctx}/shop/admin/images/transparent.gif" > </a></td>
										</tr>
									
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					
					
					<!-- 商品关联县分 -->
					<div class="append_countryTab_div">
						<table class="form-table" style="width: 100%; float: left" id="country_tab">
							<tr>
								<th>县分类型：</th>
								<td>
									<select id="region_type" name="region_type" dataType="short">
										<option value="">请选择县分类型</option>
										<option value="county">县区</option>
										<option value="city">地市</option>
									</select>	
									<input type="hidden" id="region_type_show" class="ipttxt" name="goodsView.region_type_show" value="${goodsView.region_type }" />
									<a href="javascript:void(0)"  class="graybtn1"  id="countryOpenBtn"><span>关联县分</span></a>
								</td>
							</tr>
						</table>
						<div id="countrybody" class="grid" style="width:65%;">
							<table cellspacing="0" cellpadding="0" border="0">
								<thead id="countryNodeTitle">
									<tr>
										<th style="width: 120px;">地市名称</th>
										<th style="width: auto;">地市编码</th>
										<th style="width: auto;">区县名称</th>
										<th style="width: auto;">区县编码</th>
										<th style="width: auto;">总部区县编号</th>
										<th style="width: auto;">操作</th>
									</tr>
								</thead>
								<tbody id="countryNode">
									<c:forEach items="${countryList }" var="country">
										<tr>
											<td><input type="hidden" name="areadef" id="${country.areadef }" value="${country.areadef }"/>${country.areadef }</td>
											<td><input type="hidden" name="areaid" id="${country.areaid }" value="${country.areaid }"/>${country.areaid }</td>
											<td><input type="hidden" name="countyname" id="${country.countyname }" value="${country.countyname }"/>${country.countyname }</td>
											<td><input type="hidden" name="countyid" id="${country.countyid }" value="${country.countyid }"/>${country.countyid }</td>
											<td><input type="hidden" name="hq_countyid" id="${country.hq_countyid }" value="${country.hq_countyid }"/>${country.hq_countyid }</td>
											<td><a href="javascript:;"><img  class="delete" src="${ctx}/shop/admin/images/transparent.gif" > </a></td>
										</tr>
									
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					<!-- 商品关联工号 -->
					<div class="append_staffidTab_div">
						<table class="form-table" style="width: 100%; float: left" id="staffid_tab">
							<tr>
								<td>
								</td>
								<td>
									<a href="javascript:void(0)" style="margin-left:105px;" class="graybtn1"  id="staffIdOpenBtn"><span>关联工号</span></a>
								</td>
							</tr>
						</table>
						<div id="staffidbody" class="grid" style="width:65%;">
							<table cellspacing="0" cellpadding="0" border="0">
								<thead id="staffNodeTitle">
									<tr>
										<th style="width: 120px;">工号</th>
										<th style="width: auto;">操作</th>
									</tr>
								</thead>
								<tbody id="staffidNode">
									<c:forEach items="${staffIdList }" var="staffid">
										<tr>
											<td><input type="hidden" name="staff_id" id="${staffid }" value="${staffid.staff_id }"/>${staffid.staff_id }</td>
											<td><a href="javascript:;"><img  class="delete" src="${ctx}/shop/admin/images/transparent.gif" > </a></td>
										</tr>
									
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					<!-- 商品关联客户 -->
					<div class="append_custIdTab_div">
						<table class="form-table" style="width: 100%; float: left" id="custId_tab">
							<tr>
								<td>
								</td>
								<td>
									<a href="javascript:void(0)" style="margin-left:105px;" class="graybtn1"  id="custIdOpenBtn"><span>关联客户</span></a>
								</td>
							</tr>
						</table>
						<div id="custidbody" class="grid" style="width:65%;">
							<table cellspacing="0" cellpadding="0" border="0">
								<thead id="custIdNodeTitle">
									<tr>
										<th style="width: 120px;">客户编号</th>
										<th style="width: auto;">操作</th>
									</tr>
								</thead>
								<tbody id="custidNode">
									<c:forEach items="${custIdList }" var="custid">
										<tr>
											<td><input type="hidden" name="cust_id" id="${custid }" value="${custid.cust_id }"/>${custid.cust_id }</td>
											<td><a href="javascript:;"><img  class="delete" src="${ctx}/shop/admin/images/transparent.gif" > </a></td>
										</tr>
									
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					<!-- 商品发展渠道 -->
					<div class="append_developTab_div">
						<table class="form-table" style="width: 100%; float: left" id="develop_tab">
							<tr>
								<td>
								</td>
								<td>
									<a href="javascript:void(0)" style="margin-left:105px;" class="graybtn1"  id="developGoodsBtn"><span>发展渠道</span></a>
								</td>
							</tr>
						</table>
						<div id="developbody" class="grid" style="width:65%;">
							<table cellspacing="0" cellpadding="0" border="0">
								<thead id="developNodeTitle">
									<tr>
										<th style="width: 120px;">发展渠道名称</th>
										<th style="width: auto;">操作</th>
									</tr>
								</thead>
								<tbody id="developNode">
									<c:forEach items="${developIdList }" var="developid">
										<tr>
											<td><input type="hidden" name="develop_id" id="${developid }" value="${developid.develop_rela_id }"/>${developid.develop_rela_id }</td>
											<td><a href="javascript:;"><img  class="delete" src="${ctx}/shop/admin/images/transparent.gif" > </a></td>
										</tr>
									
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					<!-- 商品受理渠道 -->
					<div class="append_officeTab_div">
						<table class="form-table" style="width: 100%; float: left" id="office_tab">
							<tr>
								<td>
								</td>
								<td>
									<a href="javascript:void(0)" style="margin-left:105px;" class="graybtn1"  id="officeRealBtn"><span>受理渠道</span></a>
								</td>
							</tr>
						</table>
						<div id="officebody" class="grid" style="width:65%;">
							<table cellspacing="0" cellpadding="0" border="0">
								<thead id="officeNodeTitle">
									<tr>
										<th style="width: 120px;">受理渠道名称</th>
										<th style="width: auto;">操作</th>
									</tr>
								</thead>
								<tbody id="officeNode">
									<c:forEach items="${officeIdList }" var="officeid">
										<tr>
											<td><input type="hidden" name="office_id" id="${officeid }" value="${officeid.office_rela_id }"/>${officeid.office_rela_id }</td>
											<td><a href="javascript:;"><img  class="delete" src="${ctx}/shop/admin/images/transparent.gif" > </a></td>
										</tr>
									
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					
					<div class="inputBox"  id="backDiv">
						
						<table> 
							<tr>
								<td rowspan="2" style="margin-right: 240px; text-align: center;">
								 	<a id="lastStep" formId="productForm" class="greenbtnbig" href="javascript:void(0)">返回</a>
									<a id="nextStep" formId="productForm" class="greenbtnbig" href="javascript:void(0)">下一步</a>
								</td>
							</tr>
						</table>
					</div>
					
				</div>
				<!-- 商品扩展属性标签,自定义处理 -->
				<html:goodsplugin></html:goodsplugin>
				${goodsseo}  
				${goodscomplex} ${goodsadjunct}
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
							<a id="finishStep" class="greenbtnbig" href="javascript:void(0)">确定</a>
						</td>
					</tr>
				</table>
			</div>
			
			
	</form>
</div>
<div id="listAllProducts">
<div id="productDialog"></div>
<div id="tagsDialog"></div>
</div>
<div id="listAllCommunitys">
<div id="communityDialog"></div>
</div>
<div id="listAllElements">
<div id="elementDialog"></div>
</div>
<div id="listAllCountrys">
<div id="countryDialog"></div>
</div>

<div id="listRelation">
<div id="relationDialog"></div>
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
                    //$("#commonPriceTd").append($("#price_div_tab").show());
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
            $('#brand_id').attr("disabled", "disabled");
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
            $("#backDiv").children("table").children("tbody").append('<tr><td rowspan="2" style="margin-right: 240px; text-align: center;"><input name="button" type="button" value=" 返回  " class="greenbtnbig"  style="cursor:pointer;"  onclick="history.go(-1);"/></td></tr>');
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




