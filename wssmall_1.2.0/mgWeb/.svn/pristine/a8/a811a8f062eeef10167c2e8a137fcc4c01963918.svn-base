<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/grid.tld" prefix="grid" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script>var ctx ='${ctx}';</script>

 <div class="goodsList" id="listProducts">
	<form action="${ctx }/shop/admin/esearchSpec!list.do" method="post" id="search_form">
		<div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
			     <tbody>
				    <tr>
					    <th>搜索编码：</th>
					    <td width="160px">
						   <input type="text"  name="sdqInner.search_code" id="cres" value='${sdqInner.search_code}' style="width: 148px;" maxlength="60" class="ipttxt" /> 
						</td>
						<th>搜索id：</th>
					    <td width="160px">
						   <input type="text"  name="sdqInner.search_id" id="search_id" value='${sdqInner.search_id}' style="width: 148px;" maxlength="60" class="ipttxt" /> 
						</td>
						<th>搜索名称：</th>
					    <td width="160px">
						   <input type="text"  name="sdqInner.search_name" id="search_name" value='${sdqInner.search_name}' style="width: 148px;" maxlength="60" class="ipttxt" /> 
						</td>
					   	<td>
						 	<input class="comBtn" type="button" onclick="search_spec(this);" id="search_btn" value="搜索" style="margin-right:5px;"/>
						</td>
				   </tr>
			  	</tbody>
		  	</table>
    	</div>  	
    	<div class="comBtnDiv">
	  	     <input class="comBtn" type="button" id="add_btn" value="添加" style="margin-right:10px;"
				onclick="to_add();" />
		</div>	
	</form>	
        <form  id="form_tc" class="grid">
            <grid:grid from="webpage" formId ="search_form">
                <grid:header>
                	<grid:cell width="100px">搜索ID</grid:cell>
                    <grid:cell>搜索编码</grid:cell>
                    <grid:cell width="260px">搜索名称</grid:cell>
                    <grid:cell width="130px">解析报文</grid:cell>
                    <grid:cell width="120px">开关标志</grid:cell>
                    <grid:cell width="100px">操作</grid:cell>
                </grid:header>
                <grid:body item="esearchSpecs">
               		<grid:cell>${esearchSpecs.search_id}</grid:cell>
                    <grid:cell>${esearchSpecs.search_code}</grid:cell>
                    <grid:cell>${esearchSpecs.search_name}</grid:cell>
                    <grid:cell>
                    	<c:if test="${esearchSpecs.search_field == '0'}">出参报文</c:if>
                    	<c:if test="${esearchSpecs.search_field == '1'}">入参报文</c:if>
                    </grid:cell>
                    <grid:cell>
						<c:if test="${esearchSpecs.flag == '0'}">开</c:if>
                    	<c:if test="${esearchSpecs.flag == '1'}">关</c:if>
					</grid:cell>
                    <grid:cell>
                    	<a href="${ctx }/shop/admin/esearchSpec!toUpdate.do?sdqInner.search_code=${esearchSpecs.search_code}&sdqInner.search_id=${esearchSpecs.search_id}">修改</a>
                    </grid:cell>
                </grid:body>
            </grid:grid>
            </br>
        </form>
</div>
<script type="text/javascript">
	//搜索按钮点击促发方法
	function search_spec(ele){
		$(ele).closest("form").submit();
	}
	//添加按钮点击事件
	function to_add(){
		window.location.href = ctx + '/shop/admin/esearchSpec!toAdd.do';
	}
</script>