<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="formWarp">
   	<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="200" class="closeArrow"></a>基本信息<div class="dobtnDiv"></div></div>
       <div id="order_tag_200" class="formGridDiv">
       	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formGrid">
             <tr>
               <th class="red">商品ID：</th>
               <td><input type="text" class="formIpt" value="${goods.goods_id }" /></td>
               <th>SKU：</th>
               <td><input type="text" class="formIpt" value="${goods.sku }" /></td>
               <th>分类：</th>
               <td><input type="text" class="formIpt" value="${goods.type }" /></td>
               <th>颜色：</th>
               <td><input type="text" class="formIpt" value="${goods.color_name }" /></td>
             </tr>
             <tr>
               <th>品牌：</th>
               <td><input type="text" class="formIpt" value="${goods.brand_name }" /></td>
               <th class="red">商品类型：</th>
               <td><input type="text" class="formIpt" value="${goods.cat_name }" /></td>
               <th>市场价：</th>
               <td><input type="text" class="formIpt" value="${goods.mktprice }" /></td>
               <th>销售价：</th>
               <td><input type="text" class="formIpt" value="${goods.price }" /></td>
             </tr>
             <!-- <tr>
               <th>尺寸：</th>
               <td><input type="text" class="formIpt" value="---" /></td>
               <td>&nbsp;</td>
               <th>&nbsp;</th>
               <td>&nbsp;</td>
               <th>&nbsp;</th>
               <td>&nbsp;</td>
             </tr> -->
           </table>
       </div>
   </div>
   <div class="formWarp">
   	<div class="tit"><a href="javascript:void(0);" name="show_close_btn" tag_id="201" class="closeArrow"></a>商品参数
  	  <div class="dobtnDiv"></div></div>
       <div id="order_tag_201" class="formGridDiv">
       	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="formGrid">
             <tr>
               <th>购机金额：</th>
               <td><input type="text" class="formIpt" value="${goods.mobile_price }" /></td>
               <th>预存金额：</th>
               <td><input type="text" class="formIpt" value="${goods.deposit_fee }" /></td>
               <th>首月返还：</th>
               <td><input type="text" class="formIpt" value="${goods.order_return }" /></td>
               <th>分月返还：</th>
               <td><input type="text" class="formIpt" value="${goods.mon_return }" /></td>
             </tr>
             <tr>
               <th>总送费金额：</th>
               <td><input type="text" class="formIpt" value="${goods.all_give }" /></td>
               <th class="red">机型编码：</th>
               <td><input type="text" class="formIpt" value="${goods.model_code} " /></td>
               <th>&nbsp;</th>
               <td>&nbsp;</td>
               <th>&nbsp;</th>
               <td>&nbsp;</td>
             </tr>
           </table>
       </div>
   </div>