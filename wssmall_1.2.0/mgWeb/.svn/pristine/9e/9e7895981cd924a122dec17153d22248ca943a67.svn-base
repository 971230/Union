<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> 
<%@ include file="/commons/taglibs.jsp"%>
<form action="schemeAction!schemeList.do" method="post" id="serviceForm">
<div class="searchformDiv" id="searchformDiv1">
<table class="form-table"> 
	<tr>
		<th style="width: 110px;">业务方案编码：</th>
		<td>
				<input type="text"  name="plan_code" id="cres" value='${plan_code}' style="width: 148px;"
				maxlength="60" class="ipttxt" /> 
		</td>
		<th style="width: 110px;">业务方案名称：</th>
		<td>
				<input type="text"  name="plan_name" id="cres" value='${plan_name}' style="width: 148px;"
				maxlength="60" class="ipttxt" /> 
		</td>
		<!-- <th width="80">状态：</th>
		<td>
			<select id="status_cd" class="ipttxt" name="status_cd" style="width: 154px;height: 25px">
			    <option value="" >------请选择----</option>
				<option value="00N">新建</option>
				<option value="00X">无效</option>
				<option value="00A">有效</option>
				<option value="00M">审核中</option>
				
			</select>
			</td> -->
		<th></th> 
	   	<td>
		 <input class="comBtn" type="submit" name="searchBtn" id="serButton" value="搜索" style="margin-right:5px;"/>
		</td>
   </tr>
</table>
</div>  	
</form> 
<div class="addRuleDiv">
   <a href="schemeAction!addRule.do" style="margin-right:10px;" name="addPaymentss" class="graybtn1" >新增</a>	 
</div>  
 <div class="grid" id="goodslist" >
	<grid:grid  from="webpage">
	<grid:header>
	    <grid:cell >业务方案编码</grid:cell>
	    <grid:cell >业务方案名称</grid:cell>
	    <grid:cell >业务方案类型</grid:cell> 
		<grid:cell >执行时间</grid:cell> 
	  	<%-- <grid:cell >创建时间</grid:cell>   --%>
		<grid:cell >创建人</grid:cell>
		<grid:cell >开始时间</grid:cell>
		<grid:cell >结束时间</grid:cell>
		<%-- <grid:cell >启动控制方式</grid:cell> 
		<grid:cell >启动控制取值</grid:cell>  --%>
		<grid:cell >状态</grid:cell>
		<grid:cell >操作</grid:cell>
	</grid:header>
  <grid:body item="plan">
  	  <grid:cell >${plan.plan_code} </grid:cell>
  	  <grid:cell >${plan.plan_name} </grid:cell>
  	  <grid:cell>${plan.service_offer_name}</grid:cell>
  	  <grid:cell >${plan.exec_time} </grid:cell>
	  <%-- <grid:cell >${plan.create_date} </grid:cell> --%>
	  <grid:cell >${plan.create_user} </grid:cell> 
	  <grid:cell >${plan.eff_date} </grid:cell>
	  <grid:cell >${plan.exp_date} </grid:cell>
	  
	  <%-- <grid:cell >${plan.ctrl_type}</grid:cell>
      <grid:cell >${plan.js_ctrl_val}</grid:cell>  --%>
      <grid:cell>
		  <c:if test="${plan.status_cd eq '00N'}">新建</c:if>
		  <c:if test="${plan.status_cd eq '00X'}">无效</c:if>
		  <c:if test="${plan.status_cd eq '00A'}">有效</c:if>
		  <c:if test="${plan.status_cd eq '00M'}">审核中</c:if>
      </grid:cell>
      <grid:cell > 
      	<a href="schemeAction!updateSchemeRule.do?plan_id=${plan.plan_id }" name="addBtn2" id="editPayments" class="updatePayment" value="${plan.plan_id}">修改</a>
      	&nbsp;|&nbsp;
      	<a  name="edit_plan_entity" class="updatePayment" plan_id="${plan.plan_id}">配置参与者</a>
      </grid:cell>
  </grid:body>   
</grid:grid>  
</div>

<div id="edit_plan_entity_list_dialog"></div>

<script type="text/javascript">
  var PlanEntity = {
  	  init:function(){
  		  var self = this;
	      Eop.Dialog.init({id:"edit_plan_entity_list_dialog",modal:true,title:"配置参与者",height:"400px",width:"900px"});
	      
	      $('a[name="edit_plan_entity"]').bind("click",self.queryPlanEntitys);
  	  },queryPlanEntitys:function(otherBtn,in_plan_id){
  		  var plan_id = $(this).attr("plan_id");
  		  if(otherBtn==1)
  			  plan_id=in_plan_id;
  		  var url = ctx+"/shop/admin/schemeAction!planEtList.do?ajax=yes&plan_id="+plan_id;
	  	  $("#edit_plan_entity_list_dialog").load(url,function(responseText){
	  		  if(otherBtn!=1)
	  		  	 Eop.Dialog.open("edit_plan_entity_list_dialog");
			  $("#add_plan_entity_btn").bind("click",PlanEntity.showAddEntityDlg);
			  $("a[name=edit_plan_entity_i]").bind("click",PlanEntity.showAddEntityDlg);
			  $("a[name=del_plan_entity_i]").bind("click",PlanEntity.deleteEntity);
		  }); 
  	  },deleteEntity:function(){
  		    if(!window.confirm("确定删除？"))
  		    	return ;
  		    var rel_id = $(this).attr("rel_id");
  		    var plan_id = $(this).attr("plan_id");
	  		var url = ctx+"/shop/admin/schemeAction!deletePlanEntityRel.do?ajax=yes&rel_id="+rel_id+"&plan_id="+plan_id;
	  		$.ajax({
	  			url:url,
	  			type:"post",
	  			dataType:"json",
	  			success:function(data){
	  				if(data.status==0){
						PlanEntity.queryPlanEntitys(1,data.plan_id);
					}
	  			},error:function(a,b){
	  				alert("失败");
	  			}
	  		});
  	  },showAddEntityDlg:function(){
  		  var action = $(this).attr("action");
  		  var rel_id = "";
  		  var plan_id = $(this).attr("plan_id");
  		  if("edit"==action)
  			 rel_id = $(this).attr("rel_id");
  		  var url = ctx+"/shop/admin/schemeAction!showEntityEditDialog.do?ajax=yes&rel_id="+rel_id+"&action="+action;
  		  $("#edit_plan_entity_dialog").load(url,{"entity.plan_id":plan_id},function(responseText){
			 Eop.Dialog.open("edit_plan_entity_dialog");
			 $("#plan_entity_edit_save_btn").bind("click",PlanEntity.savePlanEntityRel);
		  }); 
  	  },savePlanEntityRel:function(){
  		   var rule_type = $("#rule_type").val();
  		   var entity_type = $("#entity_type").val();
  		   var entity_id = $("#entity_id").val();
  		   var entity_sql = $("#entity_sql").val();
  		   var eff_date = $("#eff_date").val();
  		   var exp_date = $("#exp_date").val();
  		   if(!eff_date || !exp_date){
  			   alert("请填写有效期");
  			   return ;
  		   }
  		   if((!rule_type || !entity_type || !entity_id) && !entity_sql){
  			   if(!rule_type){
  				 alert("关系类型(表字段)");
  				 return ;
  			   }
  			 	if(!entity_type){
  				 alert("实体类型(表名)");
  				 return ;
  			   }
  				if(!entity_id){
 				 alert("实体标识(条件值)");
 				 return ;
 			   }
  			   return ;
  		   }
  		   var url = ctx+"/shop/admin/schemeAction!savePlanEntityRel.do?ajax=yes";
  		   var queryString = $("#plan_entity_edit_from [name]").fieldSerialize();
  		   $.Loading.show("正在提交。请稍候...");
  		   $.post(url, queryString,function(data){
  			 	alert(data.msg);
				if(data.status==0){
					Eop.Dialog.close("edit_plan_entity_dialog");
					PlanEntity.queryPlanEntitys(1,data.plan_id);
				}
				$.Loading.hide();
  		   },"json");
  		    /* return ;
  		    $.Loading.show("正在提交。请稍候...");
  		    var options = {
  		    	url:url,
  		    	type:"post",
  		    	dataType:"json",
  		    	success:function(data){
  		    		alert(data.msg);
  					if(data.status==0){
  						Eop.Dialog.close("edit_plan_entity_dialog");
  						PlanEntity.queryPlanEntitys(1,data.plan_id);
  					}
  					$.Loading.hide();
  		    	},error:function(a,b){
  		    		alert("失败");
  		    	}
  		    };
  		    $("#plan_entity_edit_from").ajaxSubmit(options); */
  	  }
  };
  $(function(){
    $("#status_cd option[value='${status_cd}']").attr("selected", true);
    PlanEntity.init();
  }); 
</script>
