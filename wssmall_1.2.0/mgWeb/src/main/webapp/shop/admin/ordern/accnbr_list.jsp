<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/accnbr_list.js"></script>

<form  method="post" id='accnbr_query_form' action='contract!list.do'>
 <input type="hidden" id="orderId" name="orderId" value="${orderId}" />
 <input type="hidden" id="from_page" name="from_page" value="${from_page}" />
	<div class="searchformDiv">
    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
    	    <tbody><tr>
    	       <th>开始号码:&nbsp;&nbsp;</th>
    	      <td><input type="text" class="ipttxt"  style="width: 150px" name="accNbr.begin_nbr" value="${accNbr.begin_nbr}" class="searchipt" /></td>
    	       <th>结束号码:&nbsp;&nbsp;</th>
    	      <td><input type="text" class="ipttxt"  style="width: 150px" name="accNbr.end_nbr" value="${accNbr.end_nbr}" class="searchipt" /></td>
    	      <th>批次号:</th>
    	      <td><input type="text" class="ipttxt"  style="width: 150px" name="accNbr.batch_id" value="${accNbr.batch_id}" class="searchipt" /></td>
    	      <th>号码状态:&nbsp;&nbsp;</th>
    	      <td>
    	      		<c:if test="${from_page!='order'}">
		    	      	<html:selectdict name="accNbr.state" appen_options="<option value=''>--全部--</option>" curr_val="${accNbr.state}" attr_code="DC_NUM_STATE">
						</html:selectdict>
					</c:if>
					<c:if test="${from_page=='order'}">
		    	      	<select  class="ipttxt"  name="accNbr.state" ><option value='0'>可用</option></select>
					</c:if>
    	      </td>
    	      <td><input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索" type="submit"   id="button" name="button"></td>
  	      </tr>
  	    </tbody>
  	    </table>
   	</div>
   	<c:if test="${from_page=='order'}">
	   	<div class="comBtnDiv">
			<a  style="margin-right:10px;" class="graybtn1" id='accnbr_tansfer_a'><span>号码调拨</span></a>
		</div>
	</c:if>
	<c:if test="${adminUser.founder==0 or adminUser.founder == 1}">
		<a  style="margin-right:10px;" class="graybtn1" id='phoneNo_import_btn'><span>号码导入</span></a>
	</c:if>
	
	
	
	<div id="importDialog">
	
	</div>
</form>
<div id="accnbrList">
	<jsp:include page="import_accnbr_all_list.jsp"></jsp:include>
</div>
</div>

</div>

