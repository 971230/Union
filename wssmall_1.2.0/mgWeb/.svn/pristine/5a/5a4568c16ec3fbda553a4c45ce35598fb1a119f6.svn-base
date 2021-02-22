<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/GoodsType.js"></script>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>

					<li id="" class="selected">
						<span class="word">类型修改</span><span class="bg"></span>
					</li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<div class="main-div">
		<form class="validate" method="post" action="type!step2.do"
			name="theForm" id="theForm">
			<input type="hidden" name="is_edit" value="${is_edit }" />
			<input type="hidden" name="goodsType.type_id"
				value="${goodsType.type_id }" />
			<input type="hidden" id="goodstypecode"
				value="${goodsType.type_code}" />
			<table cellspacing="1" cellpadding="3" width="100%"
				class="form-table">
				<tr>
					<td class="label" style="width: 190px;">
						<label style="padding-left: 5px;" class="text">
							类型名称
						</label>
					</td>
					<td>
						<input type="text" class="ipttxt" name="goodsType.name" id="name"
							maxlength="60" value="${goodsType.name }" dataType="string"
							required="true" />

						<input type="hidden" class="ipttxt" name="goodsType.type_code"
							id="type_code" maxlength="60" value="${goodsType.type_code}"
							dataType="string" required="true" />

						<input type="hidden" class="ipttxt" name="goodsType.have_price"
							id="have_price" maxlength="60" value="${goodsType.have_price}"
							dataType="string" required="true" />

						<input type="hidden" class="ipttxt" name="goodsType.have_stock"
							id="have_stock" maxlength="60" value="${goodsType.have_stock}"
							dataType="string" required="true" />
						<input type="hidden" id="type_type" value="${goodsType.type}" />
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 190px;">
						<label style="padding-left: 5px;" required="true" class="text">
							分类
					</td>
					<td>
						<input type="radio" name="goodsType.type" value="goods" <c:if test="${goodsType.type=='goods' }">checked</c:if> />
						商品&nbsp;&nbsp;
						<input type="radio" name="goodsType.type" value="product" <c:if test="${goodsType.type=='product' }">checked</c:if>  />
						货品 &nbsp;&nbsp;
						<span class="help_icon" helpid="goodsType.type"></span>	
					</td>
				</tr>
				<!-- 
				<tr>
					<td class="label" style="width: 190px;">
						<label style="padding-left: 5px;" required="true" class="text">
							类型分类：
					</td>
					<td>
						<html:selectdict id="goodsType" curr_val=""
							name="goodsType.type_code" attr_code="dc_card_type"
							style="width:153px;"></html:selectdict>
					</td>
					</td>
				</tr>
				 -->
				 <c:if test="${source_from eq 'ECS'}">
				<tr style="display:none;">
					<td class="label" style="width: 190px;">
						<label style="padding-left: 5px;" class="text">
							此类型下商品是否拥有属性
						</label>
					</td>
					<td>
						<input type="radio" name="goodsType.have_prop" value="1"
							<c:if test="${goodsType.have_prop==1 }">checked</c:if> />
						是&nbsp;&nbsp;
						<input type="radio" name="goodsType.have_prop" value="0"
							<c:if test="${goodsType.have_prop==0 }">checked</c:if> />
						否 &nbsp;&nbsp;
						<span class="help_icon" helpid="type_have_prop"></span>
					</td>
				</tr>
				</c:if>
				 <c:if test="${source_from ne 'ECS'}">
				<tr>
					<td class="label" style="width: 190px;">
						<label style="padding-left: 5px;" class="text">
							此类型下商品是否拥有属性
						</label>
					</td>
					<td>
						<input type="radio" name="goodsType.have_prop" value="1"
							<c:if test="${goodsType.have_prop==1 }">checked</c:if> />
						是&nbsp;&nbsp;
						<input type="radio" name="goodsType.have_prop" value="0"
							<c:if test="${goodsType.have_prop==0 }">checked</c:if> />
						否 &nbsp;&nbsp;
						<span class="help_icon" helpid="type_have_prop"></span>
					</td>
				</tr>
				</c:if>

				<tr>
					<td class="label" style="width: 190px;">
						<label style="padding-left: 5px;" class="text">
							此类型下商品是否拥有参数
						</label>
					</td>
					<td>
						<input type="radio" name="goodsType.have_parm" value="1"
							<c:if test="${goodsType.have_parm==1 }">checked</c:if> />
						是&nbsp;&nbsp;
						<input type="radio" name="goodsType.have_parm" value="0"
							<c:if test="${goodsType.have_parm==0 }">checked</c:if> />
						否 &nbsp;&nbsp;
						<span class="help_icon" helpid="type_have_parm"></span>
					</td>
				</tr>

				<tr>
					<td class="label" style="width: 190px;">
						<label style="padding-left: 5px;" class="text">
							此类型是否关联品牌
						</label>
					</td>
					<td>
						<input type="radio" name="goodsType.join_brand" value="1"
							<c:if test="${goodsType.join_brand==1 }">checked</c:if> />
						是&nbsp;&nbsp;
						<input type="radio" name="goodsType.join_brand" value="0"
							<c:if test="${goodsType.join_brand==0 }">checked</c:if> />
						否 &nbsp;&nbsp;
						<span class="help_icon" helpid="type_join_brand"></span>
					</td>
				</tr>
				<!--
				
				<tr>
					<td class="label" style="width: 190px;">
						<label style="padding-left: 5px;" class="text">
							此类型是否拥有价格
						</label>
					</td>
					<td>
						<input type="radio" name="goodsType.have_price" value="1"
							<c:if test="${goodsType.have_price==1 }">checked</c:if> />
						是&nbsp;&nbsp;
						<input type="radio" name="goodsType.have_price" value="0"
							<c:if test="${goodsType.have_price==0 }">checked</c:if> />
						否 &nbsp;&nbsp;
						<span class="help_icon" helpid="type_have_price"></span>
					</td>
				</tr>
				  -->
				 <!-- 
				<tr>
					<td class="label" style="width: 190px;">
						<label style="padding-left: 5px;" class="text">
							此类型是否拥有库存
						</label>
					</td>
					<td>
						<input type="radio" name="goodsType.have_stock" value="1"
							<c:if test="${goodsType.have_stock==1 }">checked</c:if> />
						是&nbsp;&nbsp;
						<input type="radio" name="goodsType.have_stock" value="0"
							<c:if test="${goodsType.have_stock==0 }">checked</c:if> />
						否 &nbsp;&nbsp;
						<span class="help_icon" helpid="type_have_stock"></span>
					</td>
				</tr>
				 -->
			</table>
			<div class="submitlist" align="center">
				<table>
					<tr>
						<td>
							<input type="button" style="margin-left: 240px" value=" 下一步   "
								class="submitBtn" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript">
$(function(){
	$("form.validate").validate();
	GoodsType.intChkNameEvent();
	$('#goodsType').val($('#goodstypecode').val());
	$("input[value='"+$("#type_type").val()+"']").attr("checked","checked");
});
</script>
