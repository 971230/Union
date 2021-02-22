<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listProduct">
<form action="" method="post" id="choiceProform">
		<div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
		     <tbody>
		    <tr>
		    <th>货品名称:</th>
		    <td>
			   <input type="text"  name="name" value='${name}' style="width: 288px;" maxlength="60" class="ipttxt" /> 
			</td>
			<th></th> 
		   	<td style="text-align:right;">
			 <input class="comBtn" type="button" name="searchTerminalBtn" id="serProductButton" value="搜索" style="margin-right:5px;"/>
			</td>
		   </tr>
		  </tbody>
		  </table>
    	</div>  	
</form>	
        <form  id="form_tc" class="grid">
            <grid:grid from="webpage" ajax="yes" formId ="choiceProform">
                <grid:header>
                    <grid:cell width="50px"><input  name="check" type="hidden" id="toggleChk" ></grid:cell>
                    <grid:cell>货品编码</grid:cell>
                    <grid:cell>货品名称</grid:cell>
                </grid:header>
                <grid:body item="goods">
                    <grid:cell><input type="radio"  name="goodsId" value="${goods.product_id }" pid="${goods.product_id }"
                       gid="${goods.goods_id }" id="names" goods_name="${goods.name }" sn="${goods.sn }"/></grid:cell>
                    <grid:cell>${goods.sku}</grid:cell>
                    <grid:cell>${goods.name}</grid:cell>
                </grid:body>
            </grid:grid>
            </br></br>
	         <div style="text-align:center;margin-top:3px;"> 
	             <input name="btn" type="button" id="selProductBtn" value="确定"  class="graybtn1" />
	         </div>
        </form>

</div>
<script type="text/javascript">
</script>

