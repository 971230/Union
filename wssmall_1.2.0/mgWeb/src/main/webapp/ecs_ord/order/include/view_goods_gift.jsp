<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<% String order_is_his = (String)request.getAttribute("order_is_his");
   pageContext.setAttribute("order_is_his_page_flag", order_is_his);
%>
<!-- 礼品信息开始 -->
<c:if test="${giftInfoList!=null&&giftInfoSize>0}">
<div class="grid_n_cont_sub">
 	<h3>礼品（多个记录）：</h3>
 	<div class="netWarp">
     	<a href="#" class="icon_close">展开</a>
     	<c:forEach var = "gift" items="${giftInfoList}" varStatus="i_gift">
     	    <div class="goodTit">礼品${i_gift.index+1}<a href="#" class="icon_del"></a></div>
      	<div class="goodCon">
          	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="goodTable">
            		<tr>
              		<th>赠品类型：</th>
              		<td>
              		  <html:selectdict  name="gift.goods_type" curr_val="${gift.goods_type}" appen_options="<option value=''>---请选择---</option>"   attr_code="DC_PRODUCT_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
                      </td>
             			<th>赠品编码：</th>
              		<td><input name="gift.gift_id" disabled="disabled" type="text" class="ipt_new" value="${gift.gift_id}" style="width:200px;" /></td>
              		<th>赠品名称：</th>
              		<td><input name="gift.goods_name"  disabled="disabled" type="text" class="ipt_new" value="${gift.goods_name}" style="width:200px;" /></td>
            		</tr>
            		<tr>
              		<th>赠品面值：</th>
              		<td><input name="gift.gift_value"  disabled="disabled" type="text" class="ipt_new" value="${gift.gift_value}" style="width:200px;" /></td>
              		<th>赠品面值单位：</th>
              		<td>
                      	 <html:selectdict  name="gift.gift_unit" curr_val="${gift.gift_unit}" appen_options="<option value=''>---请选择---</option>"   disabled="disabled"   attr_code="DIC_GIFT_UNIT" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
                		</td>
                		
              		<th>赠品数量：</th>
              		<td><input name="gift.sku_num"  disabled="disabled" type="text" class="ipt_new"  value="${gift.sku_num}" style="width:200px;" /></td>
            		</tr>
            		<tr>
              		<th>赠品描述：</th>
              		<td colspan="5"><textarea name="gift.gift_desc"   disabled="disabled" cols="30" rows="5">${gift.gift_desc}</textarea></td>
            		</tr>
            		<tr>
              		<th>赠品品牌：</th>
              		<td>
              		    
                      	<select name="gift.gift_brand"  disabled="disabled" style="width:200px;">
                      		<option value="">--请选择--</option>
                				<c:forEach var="giftBrand" items="${giftBrandList}">
                				   <option <c:if test="${gift.gift_brand!=null && gift.gift_brand==giftBrand.brand_code}">selected="selected"</c:if> value="${giftBrand.brand_code}">${giftBrand.name}</option>
                				</c:forEach>
              			</select>
              			
                      </td>
              		<th>赠品型号：</th>
              		<td>
                      	<select name="gift.gift_model"  disabled="disabled"  style="width:200px;">
                				<option value="1"></option>
              			</select>
                      </td>
              		<th>赠品颜色：</th>
              		<td><input name="gift.gift_color" value="${gift.gift_color}" type="text" class="ipt_new"  disabled="disabled" id="textfield" style="width:200px;" /></td>
            		</tr>
            		<tr>
              		<th>赠品机型：</th>
              		<td>
                      	<input name="gift.gift_sku"  disabled="disabled" value="${gift.gift_sku }" class="ipt_new" style="width:200px;"/>
                      </td>
              		<th>是否需要加工：</th>
              		<td>
              		<html:selectdict  disabled="disabled"  name="gift.is_process" curr_val="${gift.is_process}"    attr_code="DIC_IS_PROCESS" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
              		</td>
              		<th>加工类型：</th>
              		<td>
                      	<html:selectdict  disabled="disabled" appen_options="<option value=''>---请选择---</option>"  name="gift.process_type" curr_val="${gift.process_type}"    attr_code="DIC_PROCESS_TYPE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;"></html:selectdict>
                      </td>
            		</tr>
            		<tr>
              		<th>加工内容：</th>
              		<td><input name="gift.process_desc"  disabled="disabled"type="text" class="ipt_new" value="${gift.process_desc}" style="width:200px;" /></td>
              		<th>&nbsp;</th>
              		<td>&nbsp;</td>
              		<th>&nbsp;</th>
              		<td>&nbsp;</td>
            		</tr>
          	</table>
    		</div>
     	</c:forEach>                        	
   	   </div>
   	 </div>
   	</c:if>
<!-- 礼品信息结束 -->