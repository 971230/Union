<%@page import="com.ztesoft.net.mall.core.utils.ManagerUtils"%>
<%@page import="com.ztesoft.net.mall.core.consts.EcsOrderConsts"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%  String founder = ManagerUtils.getFounder()+"";
   String is_order_view = request.getParameter("is_order_view");
%>
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_g">
   	<tbody>
   	   <tr>
           <th>动作名称</th>
           <th>动作编号</th>
           <th>状态</th>
           <th>处理结果</th>
          <%if(!EcsOrderConsts.IS_ORDER_VIEW_YES.equals(is_order_view)) {%>
          <%if("1".equals(founder)) {%>
           <th >操作</th>
          <%}}%>
     	</tr>
     	
     	<c:forEach items="${planRuleTreeList }" var="rt" varStatus="status">
     		<tr ${(status.index%2==0)?'class=double':'' }>
	           <td style="text-align: left;padding-left: ${rt.level*10}px;width:180px;">
	           		<input name="checkbox" type="checkbox" disabled="disabled">
	           		<span style="color: #2e6cab;"><c:if test="${rt.rel_type==0 }">[并行]</c:if>
	           		<c:if test="${rt.rel_type==1 }">[依赖]</c:if>
	           		<c:if test="${rt.rel_type==2 }">[互斥]</c:if>
	           		<c:if test="${rt.rel_type==3 }">[全部互斥]</c:if></span>
	           		${rt.rule_name }(${(rt.auto_exe==1)?'手动':'自动' })
	           </td>
	           <td style="width:100px;">${rt.rule_code }</td>
	           <td style="width:50px;">${rt.handlerStatus }</td>
	           <td style="width:200px;">
	           		<c:forEach items="${rt.objRuleExeLogList }" var="elo">
	           			<p>${elo.result_msg }   &nbsp;&nbsp; ${elo.create_time}</p>
	           		</c:forEach>
	           </td>
	           <%if(!EcsOrderConsts.IS_ORDER_VIEW_YES.equals(is_order_view)) {%>
	           <%if("1".equals(founder)) {%>
	           <td style="width:200px;">
	           		<a href="javascript:void(0);" name="exe_rule_btn" plan_id="${plan_id }" rule_id="${rt.rule_id }" order_id="${order_id }" exeRelyOnRule="0" class="blue_b"  style="margin-left:5px;">执行动作</a>
	           </td>
	            <%}}%>
	     	</tr>
     	</c:forEach>
   </tbody>
</table>
<script type="text/javascript">
	$(function(){
		$("a[name=exe_rule_btn]").bind("click",AutoFlow.exeRule);
	});
</script>

