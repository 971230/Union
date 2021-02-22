<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script>
	var begin_nbr ='${cloud.begin_nbr}';
	var end_nbr ='${cloud.end_nbr}';
</script>
<script type="text/javascript" src="js/cloud_avliable_list.js"></script>
<form  method="post" id='cloud_query_form' action='cloud!list_avaible.do'>
<input type="hidden" id="hidden_lan_id" value=""/>
<input type="hidden" id="hidden_b_acc_type" value=""/>
<input type="hidden" id="hidden_e_acc_type" value=""/>
<input type="hidden" id="hidden_acc_type" value=""/>
<input type="hidden" id="hidden_sel_type" value=""/>
 <input type="hidden" name="cloud.goods_id" value="${cloud.goods_id}" />
 
	<div class="searchformDiv">
    	  <table width="100%" cellspacing="0" cellpadding="0" border="0">
    	    <tbody><tr>
    	     <c:if test="${lanList != null && fn:length(lanList) > 0 && isFromSearch != 'yes'}">
				<th>本地网：</th>
				<td>
					<select  class="ipttxt"  style="width: 100px" name="lan_id" id="lan_id">
						<c:forEach var="lan" items="${lanList}">
							<option  value="${lan.lan_id }" ${lan_id == lan.lan_id ? ' selected="selected" ' : ''}>${lan.lan_name }</option>
						</c:forEach>
				    </select>
				<td>
			  </c:if>
			    <th>号码类型:&nbsp;&nbsp;</th>
			      <td>
			      	<html:selectdict curr_val="${cloud.acc_type}" name="${cloud.acc_type}" attr_code="DC_ACC_TYPE" ></html:selectdict>
			      </td>
			    
    	      <th>起始号码:&nbsp;&nbsp;</th>
    	      <td>
    	      	<input type="text" class="ipttxt" attr='begin_nbr' name="cloud.begin_nbr"  class="searchipt" />
    	      	
    	      </td>
    	      <th>结止号码:&nbsp;&nbsp;</th>
    	      <td>
    	      	<input type="text" class="ipttxt" attr='end_nbr'  name="cloud.end_nbr"  class="searchipt" />
    	      </td>
    	      <td>	<input type="submit" style="margin-right:10px;" class="comBtn" value="搜&nbsp;索" type="submit"   id="button" name="button"></td>
  	      </tr>
  	    </tbody>
  	    </table>
   	</div>
   	
   	
</form>
<div id="cloudList">
	<jsp:include page="import_cloud_list.jsp" flush="true"></jsp:include>
</div>

