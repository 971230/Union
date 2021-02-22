<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>



<form id="serchform" >

 <div class="searchformDiv">
   <table>
	 <tr>
	    <th>商品编号：</th><td><input type="text" class="ipttxt"  style="width:90px" id="goods_id" name="goods_id" value="${goods_id}"/></td>
	    <th>商品名称：</th><td><input type="text" class="ipttxt"  style="width:90px" id="goods_name" name="goods_name" value="${goods_name}"/></td>
	    <th></th><td> <input class="comBtn" type="button" name="searchBtn"  id="searchBtn" value="搜索" style="margin-right:10px;"/></td>
	 </tr>
	</table>
     <div style="clear:both"></div>
</div>
</form>
<form >
<div class="grid">
<grid:grid  from="webpage"  ajax="yes" formId="serchform">
 <grid:header>
		<grid:cell width="50px"> <input type="checkbox" id ="checkBoxClick"></grid:cell> 
		<grid:cell sort="username" width="180px">商品编号</grid:cell> 
		<grid:cell sort="realname" width="180px">商品名称</grid:cell>
		<grid:cell sort="realname" width="180px">商品编码</grid:cell>
	
  </grid:header>
  <grid:body item="goods" > 
  		<grid:cell> <input type="checkbox" name="goodsCheckBox" goods_id ="${goods.goods_id}" goods_name="${goods.name}" sn="${goods.sn}" /></grid:cell>
        <grid:cell>&nbsp;${goods.goods_id} </grid:cell>
        <grid:cell>&nbsp;${goods.name} </grid:cell>
        <grid:cell>&nbsp;${goods.sn} </grid:cell>
  </grid:body>
  
</grid:grid>
</div>
<div class="submitlist" align="center">
 <table>
 <tr> <th> &nbsp;</th><td >
  <input name="btn" type="button" id="qrGoodsBtn" value=" 确    定   "  class="submitBtn" />
   </td>
   </tr>
 </table>
</div>	
</form>

<script type="text/javascript">
  $(function(){
    $("#checkBoxClick").click(function(){
       
        if($("[name='goodsCheckBox']").not("input:checked").length==0){
           $("[name='goodsCheckBox']").attr("checked","");
        }else{
           $("[name='goodsCheckBox']").attr("checked","checked");
        }
       
    });
  });
</script>
