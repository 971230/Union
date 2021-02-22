<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>



<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>var ctx ='${ctx}';</script>
 <div class="goodsList" id="listVirtual">
<form action="javascript:;" method="post" name="choiceVirform">
		<div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
		     <tbody>
		    <tr>
			    <th>虚拟仓名称:</th>
			    <td>
				   <input type="text" name="house_name" value='${virtualWarehouse.house_name}' style="width: 188px;" maxlength="60" class="ipttxt" /> 
				</td>
				<th>
					虚拟仓类型：
				</th>
				<td>
					<select  name="attr_inline_type" class="ipttxt"  curr_val="${virtualWarehouse.attr_inline_type }" >
						<option value="-1" <c:if test="${virtualWarehouse.attr_inline_type == -1 }">selected</c:if>>--请选择--</option>
						<option value="0" <c:if test="${virtualWarehouse.attr_inline_type == 0 }">selected</c:if>>独享</option>
						<option value="1" <c:if test="${virtualWarehouse.attr_inline_type == 1 }">selected</c:if>>共享</option>
					</select>
					<input type="hidden"
						name="attr_inline_type" value="${attr_inline_type }" />
				</td>
			<th></th> 
		   	<td style="text-align:right;">
			 <input class="comBtn" type="submit" name="searchTerminalBtn" id="serVirtualButton" value="搜索" style="margin-right:5px;"/>
			</td>
		   </tr>
		  </tbody>
		  </table>
    	</div>  	
</form>	
</form> 
        <form  id="form_tc" class="grid">
            <grid:grid from="webpage" ajax="yes" formId ="gridform">
                <grid:header>
                    <grid:cell width="50px"><input  name="check" type="hidden" id="toggleChk" ></grid:cell>
                    <grid:cell width="20%">虚拟仓名称</grid:cell>
                    <grid:cell width="10%">类型</grid:cell>
                    <grid:cell width="60%">对应商城</grid:cell>
                </grid:header>
                <grid:body item="virtual">
                    <grid:cell><input type="radio"  name="house_id" value="${virtual.house_id }" 
                       house_id="${virtual.house_id }" id="names" house_name="${virtual.house_name }" 
                       org_id_str="${virtual.org_id_str }" org_name_str="${virtual.org_name_str }"
                       attr_inline_type="${virtual.attr_inline_type }"/></grid:cell>
                    <grid:cell>${virtual.house_name}</grid:cell>
                    <grid:cell>
						<c:if test="${virtual.attr_inline_type=='0'}">独享</c:if>
						<c:if test="${virtual.attr_inline_type=='1'}">共享</c:if>
                    </grid:cell>
                    <grid:cell>${virtual.org_name_str}</grid:cell>
                </grid:body>
            </grid:grid>
            </br></br>
	         <div style="text-align:center;margin-top:3px;"> 
	             <input name="btn" type="button" id="selVirtualBtn" value="确定"  class="graybtn1" />
	         </div>
        </form>

</div>
<script type="text/javascript">


</script>

