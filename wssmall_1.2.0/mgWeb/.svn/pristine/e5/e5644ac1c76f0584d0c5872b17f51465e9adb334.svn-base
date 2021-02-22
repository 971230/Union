<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="orderGroup/orderGroup.js"></script>
<form method="post" id="serchform" action='orderGroupAction!list.do'>
 <div class="searchformDiv">
 <table>
	<tr>
		<th>分组名称：</th>
		<td><input type="text" class="ipttxt"  name="group_name"  value="${group_name}"/></td>
		<th>分组类型：</th>
		
		<td>
		    <select class="ipttxt" id="selGroupType" name="group_type" >
	         
	          <c:forEach var="typeList" items="${groupTypeList}">
	             <option value="${typeList.pkey }">${typeList.pname}</option>
	          </c:forEach>
	       </select>
		</td>
		    <script type="text/javascript">
	           $("#selGroupType [value=${group_type}]").attr("selected",true);
	       </script>
		<th></th>
		<td>
	    <input class="comBtn" type="submit" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
		</td>
		
	</tr>
	   <div style="clear:both"></div>
</table>	
</div>
<div class="comBtnDiv">
 
	<a href="javascript:;" id="addOrderGroupBtn" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
</div>
</form>
<div class="grid" id="goodslist">

<form method="POST"  id="orderGroupForm" >
<grid:grid from="webpage"  formId="serchform">
	<grid:header>
	
		<grid:cell width="200px">分组名</grid:cell>
		<grid:cell width="200px">分组编码</grid:cell>
		<grid:cell width="200px">分组类型</grid:cell>
		<grid:cell width="150px">创建时间</grid:cell>
		<grid:cell width="200px">操作</grid:cell>
	</grid:header>
	<grid:body item="orderGroupDef">
		<grid:cell>&nbsp;${orderGroupDef.group_name}
		</grid:cell>
		<grid:cell>&nbsp;${orderGroupDef.group_code} </grid:cell>
		<grid:cell>
         <c:if test="${orderGroupDef.group_type=='confirm'}">确认组</c:if>
         <c:if test="${orderGroupDef.group_type=='delvery'}">发货组</c:if>
         <c:if test="${orderGroupDef.group_type=='pay'}">支付组</c:if>
         <c:if test="${orderGroupDef.group_type=='accept'}">受理组</c:if>
         <c:if test="${orderGroupDef.group_type=='query'}">查询组</c:if>
       
        </grid:cell>
		  <grid:cell>&nbsp;<html:dateformat pattern="yyyy-MM-dd" d_time="${orderGroupDef.create_time}"></html:dateformat></grid:cell>
		<grid:cell>&nbsp; 
			<a title ="删除" href="" group_id ="${orderGroupDef.group_id}" id="del" class="p_prted">删除</a> <span class='tdsper'></span> 
			<a title ="关联角色" href="${ctx }/shop/admin/orderGroupAction!showSelRole.do?group_id=${orderGroupDef.group_id}" group_id ="${orderGroupDef.group_id}" id="refRole" class="p_prted">关联角色</a><span class='tdsper'></span>  
		    <a title ="关联员工" href="${ctx }/shop/admin/orderGroupAction!showSelUser.do?group_id=${orderGroupDef.group_id}" group_id ="${orderGroupDef.group_id}" id="refRole" class="p_prted">关联员工</a>
		</grid:cell>
	</grid:body>
</grid:grid>
</form>
</div>
<div id="addOrderGroupDlg"></div>
