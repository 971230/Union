<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>

<style>
#tagspan{
	position: absolute;
	display:none;
}
#searchcbox{float:left}
#searchcbox div{float:left;margin-left:10px}
</style>
<form id="serchform" align="center">

<div class="grid" style = "height:150px;">
 
  <div class ='gridbody'>
    <table>
        <c:choose>
	       <c:when test="${!empty orgList}">
	         <tr>
	            <td colspan="2" style="text-align:center;color:red">该商品发布到了如下组织，确定要停用吗？</td>
	         </tr>
	         <tr>
		         <td>
			       <c:forEach var = "org" items='${orgList}' varStatus="status">
			         <c:if test="${status.count>1}"><span>|</span></c:if> 
			         <span>${org.org_name}</span>
			       </c:forEach>
		
			     </td>
			   </tr>  
	        </c:when>
	        <c:otherwise>
		       <tr>
		          <td colspan="2" style="text-align:center;color:red;font-size:14px;">您确定要停用吗？</td>
		       </tr>
	        </c:otherwise>
        </c:choose>

    </table>
   </div>
 
</div>
<div class="submitlist" align="center">
  <input name="btn" class="comBtn" type="button" id="goods_down_check_btn" value=" 确    定   "  />
  <input name="btn" class="comBtn" type="button" id="goods_down_cance_btn"  value=" 取   消  "  style="margin-left:10px;"  />
</div>	
</form>