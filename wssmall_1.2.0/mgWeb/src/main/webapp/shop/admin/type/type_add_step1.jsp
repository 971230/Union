<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/GoodsType.js"></script>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>

					<li id="" class="selected">
						<span class="word">添加类型</span><span class="bg"></span>
					</li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<div class="main-div">
		<form class="validate" method="POST" action="type!step2.do"
			validate="true">
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
							maxlength="60" value="" dataType="string" required="true" />
						<input type="hidden" class="ipttxt" name="goodsType.type_code" id="type_code"
							maxlength="60" value="BASE_CODE" dataType="string" required="true" />
							
						<input type="hidden" class="ipttxt" name="goodsType.have_price" id="have_price"
								maxlength="60" value="1" dataType="string" required="true" />
						
						<input type="hidden" class="ipttxt" name="goodsType.have_stock" id="have_stock"
								maxlength="60" value="1" dataType="string" required="true" />	
								
					</td>
				</tr>
				<tr>
					<td class="label" style="width: 190px;">
						<label style="padding-left: 5px;" required="true" class="text">
							分类
					</td>
					<td>
						<input type="radio" name="goodsType.type" value="goods" checked/>
						商品&nbsp;&nbsp;
						<input type="radio" name="goodsType.type" value="product"  />
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
						
						<html:selectdict curr_val="" name="goodsType.type_code"
							attr_code="dc_card_type" style="width:153px;"></html:selectdict>
							
					</td>
				</tr>
				 -->
				<c:if test="${source_from ne 'ECS'}">
				<tr>
					<td class="label" style="width: 190px;">
						<label style="padding-left: 5px;" class="text">
							此类型下商品是否拥有属性
						</label>
					</td>
					<td>
						<input type="radio" name="goodsType.have_prop" value="1" />
						是&nbsp;&nbsp;
						<input type="radio" name="goodsType.have_prop" value="0" checked />
						否 &nbsp;&nbsp;
						<span class="help_icon" helpid="type_have_prop"></span>
					</td>
				</tr>
				</c:if>
				<c:if test="${source_from eq 'ECS'}">
				<tr style="display:none;">
					<td class="label" style="width: 190px;">
						<label style="padding-left: 5px;" class="text">
							此类型下商品是否拥有属性
						</label>
					</td>
					<td>
						<input type="radio" name="goodsType.have_prop" value="1" />
						是&nbsp;&nbsp;
						<input type="radio" name="goodsType.have_prop" value="0" checked />
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
						<input type="radio" name="goodsType.have_parm" value="1" />
						是&nbsp;&nbsp;
						<input type="radio" name="goodsType.have_parm" value="0" checked />
						否 &nbsp;&nbsp;
						<span class="help_icon" helpid="type_have_parm"></span>
					</td>
				</tr>

				     <tr>  
				       <td class="label" style="width:190px;"><label style="padding-left:5px;" class="text">此类型是否关联品牌</label></td>
				       <td><input type="radio" name="goodsType.join_brand" value="1"  />
				       是&nbsp;&nbsp;
				         <input type="radio" name="goodsType.join_brand" value="0" checked />
				       否 &nbsp;&nbsp;<span class="help_icon" helpid="type_join_brand"></span></td>
			      </tr>	
			     <!-- 
			     
			     
				<tr>
					<td class="label" style="width: 190px;">
						<label style="padding-left: 5px;" class="text">
							此类型是否拥有价格
						</label>
					</td>
					<td>
						<input type="radio" name="goodsType.have_price" value="1" />
						是&nbsp;&nbsp;
						<input type="radio" name="goodsType.have_price" value="0" checked />
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
						<input type="radio" name="goodsType.have_stock" value="1" />
						是&nbsp;&nbsp;
						<input type="radio" name="goodsType.have_stock" value="0" checked />
						否 &nbsp;&nbsp;
						<span class="help_icon" helpid="type_have_stock"></span>
					</td>
				</tr>
				-->
			</table>
			<br />
			<div class="submitlist" align="center">
				<table>
					<tr>
						<td>
							<input style="margin-left: 240px" type="button" value=" 下一步   "
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
});
</script>