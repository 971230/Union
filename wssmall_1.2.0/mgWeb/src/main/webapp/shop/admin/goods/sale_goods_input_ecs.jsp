<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@ include file="/commons/taglibs.jsp"%>

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
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/sale_goods_input_ecs.js"></script>

<div class="input">
	<form method="post" name="productForm" action="${actionName }" id="productForm"
		class="validate">
		
		<c:if test="${is_edit }">
			<input type="hidden" name="saleGoods.sale_gid" value="${saleGoodsView.sale_gid }" />
			<input type="hidden" name="goods_id_e" value="${saleGoodsView.sale_gid }" />
		</c:if>

		<div style="display: block;" class="goods_input">
			<div class="tab-bar" style="position: relative;">
				<ul class="tab">
					<li tabid="0" class="active">
						触点信息
					</li>
					<li tabid="1" >
						商品信息
					</li>
					<li tabid="2" >
						标签信息
					</li>
					<li tabid="3" >
						基本信息
					</li>
					<li tabid="4" >
						图片信息
					</li>
					<li tabid="5" >
						描述信息
					</li>
					
				</ul>
			</div>
			<div class="tab-page">
				
				<div class='clear'></div>
				<div tabid="tab_0" class="tab-panel" height="200%">
					<table style="border-collapse:separate; border-spacing:30px;">
						<tr >
							<th><label class="text">活动地市：</label></th>
					     	<td>
							             
							         <script type="text/javascript">
					                	$(function(){   
					                		$("#region_ivp,#region_a").bind("click",function(){
					                    		$("#region_dv").show();
					                    	});
					                		$("#region_checkall").bind("click",function(){
					                			if(this.checked){
					                				$("input[name=region]").attr("checked","checked");
					                    			$("#region_ivp").val($("input[id=region_checkall]").attr("c_name"));
					                    			$("#region_hivp").val(this.value);
					                			}else{
					                				$("input[name=region]").removeAttr("checked");
					                    			$("#region_ivp").val("");
					                    			$("#region_hivp").val("");
					                			}
					                		});
					                		$("#region_cancel1,#region_cancel2").bind("click",function(){
					                			$("#region_dv").hide();
					                		});
					                		$("input[name=region]").bind("click",function(){
					                			selectRegions();
					                		});
					                		calculateRegion();
					                	});
					                	function selectRegions(){
					            			var regions = $("input[name=region]:checked");
					            			var regionsAll = $("input[name=region]");
					            			if(regions.length>=regionsAll.length){
					            				$("#region_checkall").attr("checked","checked");
					                			$("#region_ivp").val($("input[id=region_checkall]").attr("c_name"));
					                			$("#region_hivp").val($("input[id=region_checkall]").attr("value"));
					            				alert("所有地市都已经选中！");
					            			}else{
					            				$("#region_checkall").removeAttr("checked");
					                			var regionNames = "";
					                			var regionIds = "";
					                			regions.each(function(idx,item){
					                				var name = $(item).attr("c_name");
					                				var rid = $(item).attr("value");
					                				regionNames += name+",";
					                				regionIds += rid+",";
					                			});
					                			if(regionIds.length>1){
					                				regionIds = regionIds.substr(0,regionIds.length-1);
					                				regionNames = regionNames.substr(0,regionNames.length-1);
					                			}
					                			$("#region_ivp").val(regionNames);
					                			$("#region_hivp").val(regionIds);
					            			}
					            		}
					                	
					                	function calculateRegion()
					                	{
					                		var regionids="${saleGoodsView.publish_city}";
					                		if(null==regionids || undefined == regionids)
					                			return;
					                		
					                		if("440000"==regionids||"330000"==regionids||"410000"==regionids)
					                		{
					                			$("#region_checkall").attr("checked","checked");
					                			$("li[name='region_li'] input[type='checkbox']").attr("checked","checked");
					                			$("#region_ivp").val("全省");
					                			return;
					                		}
					                		
					                		var regionnames=new Array();
					                		var lis=$("li[name='region_li'] input[type='checkbox']");
					                		for(var i=0;i<lis.length;++i)
					                		{                			
					                			if(regionids.search($(lis.get(i)).val())>-1)
					                			{               
					                				regionnames.push($(lis.get(i)).attr("c_name"));
					                				$(lis.get(i)).attr("checked","checked");
					                			}
					                		}                    	
					                		$("#region_ivp").val(regionnames.join(","));
					                	}
					                </script>
					                	<span class="selBox" style="width:120px;">
					                    	<input type="text" name="saleGoods.regionName" id="region_ivp" value="${saleGoodsView.regionName }" class="ipt" readonly="readonly" />
					                    	<input type="hidden" name="saleGoods.publish_city" value="${saleGoodsView.publish_city  }" id="region_hivp" />
					                    	<a href="javascript:void(0);" class="selArr" id="region_a"></a>
					                        <div class="selOp" style="display:none;" id="region_dv">
					                        	<div class="allSel">
					                            	<label><input type="checkbox" id="region_checkall" value="440000" c_name="全省">全省</label>
					                                <label><a href="javascript:void(0);" class="aCancel" id="region_cancel1">关闭</a></label>
					                                <label><a href="javascript:void(0);" class="aClear" id="region_cancel2"></a></label>
					                            </div>
					                            <div class="listItem">
					                            		<c:forEach items="${regionList }" var="pt">
					                            			<li name="region_li"><input type="checkbox" name="region" value="${pt.value }" c_name="${pt.value_desc }"><span name="region_span">${pt.value_desc }</span></li>
					                            		</c:forEach>
					                                </ul>
					                            </div>
					                        </div>
					                    </span>
							</td>
					     </tr>
					     
					     <tr>
							<th>渠道类型：</th>
							<td>
								<select id="channel_type" name="saleGoods.channel_type" required="true" dataType="short">
									<option value="">请选择渠道类型</option>
									<option value="0">全部</option>
									<option value="1">社会渠道</option>
									<option value="2">营业渠道</option>
									<option value="3">集客渠道</option>
								</select>	
								<input type="hidden" id="channel_type_id" class="ipttxt" name="saleGoods.channel_type_id" value="${saleGoodsView.channel_type }" />
							</td>
					     </tr>
					     
					</table>
					
					
					
				</div>
				
				<div tabid="tab_1" class="tab-panel" >
					<table >
						<tr>
							<th>打包类型：</th>
							<td colspan="3">
								<td>
									<select id="goods_type" name="saleGoods.goods_type" required="true" dataType="short">
										<option value="">请选择商品类型</option>
										<c:forEach var="types" items="${goodsTypeList}">
											<option value="${types.type_id}" >${types.type_name}</option>
										</c:forEach>
									</select>	
									<input type="hidden" id="type_id" class="ipttxt" name="saleGoods.type_id" value="${saleGoodsView.package_type }" />
									<a href="javascript:void(0)" style="margin-left:105px;" class="graybtn1"  id="addGoodsBtn"><span>添加商品</span></a>
								</td>
							</td>
						</tr>
						
					</table>
					<div id="goodsbody" class="grid" >
							<table cellspacing="0" cellpadding="0" border="0">
								<thead id="goodsNodeTitle">
									<tr>
										<th style="width: auto;">商品SKU</th>
										<th style="width: auto;">商品名称</th>
										<th style="width: auto;">商品小类</th>
										<th style="width: auto;">操作</th>
									</tr>
								</thead>
								<tbody id="goodsNode">
									<c:forEach items="${goodsListRelSale }" var="goods">
										<tr>
											<td><input type="hidden" name="sku" id="${goods.sku }" value="${goods.sku }"/>${goods.sku }</td>
											<td><input type="hidden" name="goodsName" id="${goods.name }" value="${goods.name }"/>${goods.name }</td>
											<td><input type="hidden" name="cat_name" id="${goods.cat_name }" value="${goods.cat_name }"/>${goods.cat_name }</td>
											<td><a href="javascript:;"><img  class="delete" src="${ctx}/shop/admin/images/transparent.gif" > </a></td>
										</tr>
									
									</c:forEach>
								</tbody>
							</table>
						</div>
					
					
					
				</div>
				
				<div tabid="tab_2" class="tab-panel" >
					<table >
						<tr>
							<th>商品标签>></th>
							<td>
								<a href="javascript:void(0)" style="margin-left:105px;" class="graybtn1"  id="addGoodsTagBtn"><span>添加</span></a>
							</td>
						</tr>
						
					</table>
					<div id="goodsTagbody" class="grid" style="margin-bottom:50px;">
							<table cellspacing="0" cellpadding="0" border="0">
								<thead id="goodsTagNodeTitle">
									<tr>
										<th style="width: auto;">标签编码</th>
										<th style="width: auto;">标签名称</th>
										<th style="width: auto;">标签顺序</th>
										<th style="width: auto;">操作</th>
									</tr>
								</thead>
								<tbody id="goodsTagNode">
									<c:forEach items="${goodsTagList }" var="goodsTag">
										<tr>
											<td><input type="hidden" name="tag_code" id="${goodsTag.tag_code }" value="${goodsTag.tag_code }"/>${goodsTag.tag_code }</td>
											<td><input type="hidden" name="tag_name" id="${goodsTag.tag_name }" value="${goodsTag.tag_name }"/>${goodsTag.tag_name }</td>
											<td><input type="text" style="width:30px; height:100%" name="sort" id="${goodsTag.sort }" value="${goodsTag.sort }"/></td>
											<td><a href="javascript:;"><img  class="delete" src="${ctx}/shop/admin/images/transparent.gif" > </a></td>
										</tr>
									
									</c:forEach>
								</tbody>
							</table>
						</div>
					
					
					<table >
						<tr>
							<th>营销标签>></th>
							<td>
								<a href="javascript:void(0)" style="margin-left:105px;" class="graybtn1"  id="addSaleTagBtn"><span>添加</span></a>
							</td>
						</tr>
					</table>
					<div id="saleTagbody" class="grid" >
							
							<table cellspacing="0" cellpadding="0" border="0">
								<thead id="saleTagNodeTitle">
									<tr>
										<th style="width: auto;">标签编码</th>
										<th style="width: auto;">标签名称</th>
										<th style="width: auto;">操作</th>
									</tr>
								</thead>
								<tbody id="saleTagNode">
									<c:forEach items="${saleTagList }" var="saleTag">
										<tr>
											<td><input type="hidden" name="sale_tag_code" id="${saleTag.tag_code }" value="${saleTag.tag_code }"/>${saleTag.tag_code }</td>
											<td><input type="hidden" name="sale_tag_name" id="${saleTag.tag_name }" value="${saleTag.tag_name }"/>${saleTag.tag_name }</td>
											<td><a href="javascript:;"><img  class="delete" src="${ctx}/shop/admin/images/transparent.gif" > </a></td>
										</tr>
									
									</c:forEach>
								</tbody>
							</table>
						</div>	
					
					
					
				</div>
				
				<div tabid="tab_3" class="tab-panel" >
					<table >
						<tr>
							<th>商品名称：</th>
							<td>
								<input type="text" id="goods_name" name="saleGoods.sale_gname" style="width:300px" class="top_up_ipt"
									required="true" value="${saleGoodsView.sale_gname}" />
							</td>
						</tr>
						<tr>
							<th>宣传标题：</th>
							<td>
								<input type="text" id="public_title" name="saleGoods.public_title" style="width:300px" class="top_up_ipt"
									 value="${saleGoodsView.public_title}" />
							</td>
						</tr>
						<tr>
							<th>商品卖点1：</th>
							<td>
								<input type="text" id="selling_point1" name="saleGoods.selling_point1" style="width:300px" class="top_up_ipt"
									 value="${saleGoodsView.selling_point1}" />
							</td>
						</tr>
						<tr>
							<th>商品卖点2：</th>
							<td>
								<input type="text" id="selling_point2" name="saleGoods.selling_point2" style="width:300px" class="top_up_ipt"
									 value="${saleGoodsView.selling_point2}" />
							</td>
						</tr>
						<tr>
							<th>商品卖点3：</th>
							<td>
								<input type="text" id="selling_point3" name="saleGoods.selling_point3" style="width:300px" class="top_up_ipt"
									 value="${saleGoodsView.selling_point3}" />
							</td>
						</tr>
					</table>
					
					
					
				</div>
				
				<div tabid="tab_4" class="tab-panel" >
					
					${album} 
					
					
				</div>
				
				<div tabid="tab_5" class="tab-panel" >
					<table >
						<tr>
							<th>描述信息：</th>
							<td colspan="5" >
								<textarea name="saleGoods.intro" id="intro" style="width:600px" rows="4" cols="103" >${saleGoodsView.intro}</textarea>	
							</td>
						</tr>
					</table>
					
					
					
				</div>
				
				<div class="inputBox"  id="backDiv">
					<table > 
						<tr>
							<td rowspan="2" style="margin-right: 240px; text-align: center;">
								<a id="lastStep" formId="productForm" class="greenbtnbig" href="javascript:void(0)">返回</a>
							 	<a id="preStep" formId="productForm" class="greenbtnbig" href="javascript:void(0)">上一步</a>
								<a id="nextStep" formId="productForm" class="greenbtnbig" href="javascript:void(0)">下一步</a>
								<a id="finishStep" class="greenbtnbig" href="javascript:void(0)">保存</a>
							</td>
						</tr>
					</table>
				</div>
				
			</div>
			
			
	</form>
</div>
<div id="activity_orgs_dialog">
</div>
<div id="goodsList">
<div id="goodsDialog"></div>
</div>
<div id="tagList">
<div id="tagDialog"></div>
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





