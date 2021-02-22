<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
<div class="goodsList" id="listBrand">
<form action="javascript:;" method="post" id="selBrandform">
	<div class="searchformDiv">
	    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	     <tbody>
		  <tr>
		    <th>品牌</th>
		    <td>
			   <input type="text"  name="name" value='${brand.name}' style="width: 188px;" maxlength="60" class="ipttxt" /> 
			</td>
			<th></th> 
		   	<td style="text-align:right;">
			   <input class="comBtn" type="submit" id="searchBrandBtn" value="搜索" style="margin-right:5px;"/>
			</td>
		   </tr>
	  </tbody>
	  </table>
   	</div>  	
</form>	
</form> 
        <form  id="form_tc" class="grid">
            <grid:grid from="webpage" ajax="yes" formId ="selBrandform">
                <grid:header>
                    <grid:cell width="50px"><input  name="check" type="hidden" id="toggleChk" ></grid:cell>
                    <grid:cell>品牌名称</grid:cell>
                </grid:header>
                <grid:body item="brand">
                    <grid:cell><input type="radio"  name="brand" value="${brand.name }" brand_code="${brand.brand_code }"/></grid:cell>
                    <grid:cell>${brand.name}</grid:cell>
                </grid:body>
            </grid:grid>
            </br></br>
	         <div style="text-align:center;margin-top:5px;"> 
	             <input name="btn" type="button" id="selBrandBtn" value="确定"  class="comBtn"  />
	         </div>
        </form>

</div>
<script type="text/javascript">
</script>

