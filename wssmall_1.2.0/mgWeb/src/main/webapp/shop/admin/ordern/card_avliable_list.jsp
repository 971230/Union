<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<form  method="post" id='card_query_form' action='card!list_avaible.do'>
 <input type="hidden" id="orderId" name="orderId" value="${orderId}" />
 <input type="hidden" id="from_page" name="from_page" value="${from_page}" />
 <input type="hidden" id="card.type_code" name="card.type_code" value="${card.type_code}" />
	<div class="searchformDiv">
    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
    	    <tbody><tr>
    	      <th>卡号:&nbsp;&nbsp;</th>
    	      <td><input type="text" class="ipttxt"  style="width: 150px" name="card.card_code" value="${card.card_code}" class="searchipt" /></td>
    	      <th>批次号:</th>
    	      <td><input type="text" class="ipttxt"  style="width: 150px" name="card.batch_id" value="${card.batch_id}" class="searchipt" /></td>
    	      <td>	<input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索" type="submit"   id="button" name="button"></td>
  	      </tr>
  	    </tbody>
  	    </table>
   	</div>
</form>

<div id="cardList">
	<jsp:include page="import_card_list.jsp"></jsp:include>
</div>