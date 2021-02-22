<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%@ include file="/commons/taglibs.jsp"%>
<div class="addRuleDiv">
   <a href="javascript:void(0);" style="margin-right:10px;" id="add_plan_entity_btn" action="new" plan_id="${plan_id }" class="graybtn1" >新增</a>	 
</div>  
<form id="gridform_plan_entity" class="grid" >
 <div class="grid" id="goodslist" >
	<grid:grid  from="webpage" formId="gridform_plan_entity" ajax="yes">
	<grid:header>
	    <grid:cell >关系类型</grid:cell>
	    <grid:cell >实体类型</grid:cell>
	    <grid:cell >实体标识</grid:cell> 
		<%-- <grid:cell >实体SQL</grid:cell>  --%>
	  	<grid:cell >生效时间</grid:cell> 
		<grid:cell >失效时间</grid:cell> 
		<grid:cell >状态</grid:cell>
		<grid:cell >操作</grid:cell>
	</grid:header>
  <grid:body item="ent">
  	  <grid:cell >${ent.rel_type} </grid:cell>
  	  <grid:cell >${ent.entity_type}  </grid:cell>
  	  <grid:cell>${ent.entity_id} </grid:cell>
  	  <%-- <grid:cell >${ent.entity_sql}  </grid:cell> --%>
	  <grid:cell >${ent.eff_date}  </grid:cell>
	  <grid:cell >${ent.exp_date}  </grid:cell> 
      <grid:cell>
		  <c:if test="${ent.status_cd eq '00N'}">新建</c:if>
		  <c:if test="${ent.status_cd eq '00X'}">无效</c:if>
		  <c:if test="${ent.status_cd eq '00A'}">有效</c:if>
		  <c:if test="${ent.status_cd eq '00M'}">审核中</c:if>
      </grid:cell>
      <grid:cell > 
      	<a name="edit_plan_entity_i" class="updatePayment" action="edit" rel_id="${ent.rel_id}" plan_id="${ent.plan_id}">修改</a>
      	&nbsp;|&nbsp;
      	<a name="del_plan_entity_i" class="updatePayment" rel_id="${ent.rel_id}" plan_id="${ent.plan_id}">删除</a>
      </grid:cell>
  </grid:body>   
</grid:grid>  
</div>
</form>

<div id="edit_plan_entity_dialog"></div>

<script type="text/javascript">
  $(function(){
	  Eop.Dialog.init({id:"edit_plan_entity_dialog",modal:true,title:"配置参与者",height:"400px",width:"700px"});
  }); 
</script>
