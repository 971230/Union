<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/accnbr_list.js"></script>
<div class="warm_reminder">
       	   <p class="title"><i class="warmico"></i>温馨提示</p>
           <p>2、用户申请调拨号码段为：${begin_nbr}-${end_nbr}</p>
           <p>3、用户申请号码总数为：<span class='red'>${order.goods_num}</span>（个）</p>
</div>
<form  method="post" id='accnbr_query_form' action='contract!list.do'>
 	<input type="hidden" id="orderId" name="orderId" value="${orderId}" />
	<div class="searchformDiv">
    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
    	    <tbody>
    	    <tr>
    	    	
  	     	</tr>
  	    </tbody>
  	    </table>
   	</div>
	<div class="comBtnDiv">
	   <a  style="margin-right:10px;" class="graybtn1" id='accnbr_tansfer_a'><span>号码调拨</span></a>
	</div>
</form>