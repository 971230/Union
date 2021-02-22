<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>过滤校验列表</title>
</head>
<body>
	<form action="" method="post" id="qryFrm">
		<div class="searchformDiv">
		    <table width="100%" cellspacing="0" cellpadding="0" border="0">
	    	<tbody>
	    	    <tr>
	    	      <th>业务名称：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="biz.biz_name" id="biz_name"/> 
	    	      </td>	
	    	      <th>业务编码：</th>
	    	      <td>
	    	          <input type="text" class="ipttxt" name="biz.flow_id" id="flow_id" /> 
	    	      </td>		    	      
	    	      <th>状态：</th>
	    	      <td>
						<select id="status" class="ipttxt" name="biz.status" style="width: 155px">
						<option value="00A">有效</option>
						<option value="00X">无效</option>
						</select>
	    	      </td>

	    	      <th></th>
	    	      <td>
	    	          <input type="button" style="margin-right:10px;" class="comBtn" value="确&nbsp;定"  id="button" name="button" onclick="buttonReturn();">
	    	          <a app_menu_id="251126031" href="/core/admin/ecc/checkFilter!showCheckFilterList.do"><input type="button" style="margin-right:10px;" class="comBtn" value="返&nbsp;回"  id="back_list" name="button"></a>
	    	      </td>       
				 </tr>
	  	    </tbody>
	  	    </table>
	  	 </div>		
	  	 <div style="height:8px;"></div>
	  	<table class="form-table" style="width: 100%; float: left" id='base_table'>
	  	<c:forEach var="bizFactorCfg" items="${bizFactorCfgList}" varStatus="status">
	  		<input type="hidden" name="bizRequirementsList[${status.index}].factor_cfg_id" value="${bizFactorCfg.factor_id}" />
			<input type="hidden" name="bizRequirementsList[${status.index}].attr_code" value="${bizFactorCfg.attr_code}" />
			<input type="hidden" name="bizRequirementsList[${status.index}].z_cvalue" value="${bizFactorCfg.attr_name}" />
			<tr>
				<th>
					<label><input type="checkbox" value="" name="bizRequirementsList[${status.index}].check" onclick="if(this.checked==true){this.value='1'}else{this.value=''}"> ${bizFactorCfg.attr_name}</label>
				</th>
				<td>
					<select name="bizRequirementsList[${status.index}].opt_type">			
						<option value="!=">不等于</option>
						<option value="<">小于</option>
						<option value="<=">小于等于</option>
						<option value="==">等于</option>
						<option value=">">大于</option>
						<option value=">=">大于等于</option>
						<option value="contains">包含</option>
						<option value="matches" selected="selected">匹配</option>
						<option value="in">存在</option>
						<option value="not contains">不包含</option>
						<option value="not matches">不匹配</option>
						<option value="not in">不存在</option>												
					</select>
				</td>
				<td>
			   		<c:choose>
				           <c:when test="${bizFactorCfg.stype_code eq 'DC_IS_OR_NO'}">
							  	<c:forEach var="obj" items="${DC_IS_OR_NO}">
							  		<label><input type="checkbox" value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  	</c:forEach>
				           </c:when>
				           <c:when test="${bizFactorCfg.stype_code eq 'DC_NEW_USER_TYPE'}">
							  	<c:forEach var="obj" items="${DC_NEW_USER_TYPE}">
							  		<label><input type="checkbox" value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  	</c:forEach>
				           </c:when>
				           <c:when test="${bizFactorCfg.stype_code eq 'DC_MODE_GOODS_TYPE'}">
							  	<c:forEach var="obj" items="${DC_MODE_GOODS_TYPE}">
							  		<label><input type="checkbox" value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  	</c:forEach>
				           </c:when>
				           <c:when test="${bizFactorCfg.stype_code eq 'DC_BUSINESS_TYPE'}">
							  	<c:forEach var="obj" items="${DC_BUSINESS_TYPE}">
							  		<label><input type="checkbox" value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  	</c:forEach>
				           </c:when>
				           <c:when test="${bizFactorCfg.stype_code eq 'DC_MODE_NET_TYPE'}">
							  	<c:forEach var="obj" items="${DC_MODE_NET_TYPE}">
							  		<label><input type="checkbox" value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  	</c:forEach>
				           </c:when>
				           <c:when test="${bizFactorCfg.stype_code eq 'DC_MODE_SHIP_TYPE'}">
							  	<c:forEach var="obj" items="${DC_MODE_SHIP_TYPE}">
							  		<label><input type="checkbox" value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  	</c:forEach>
				           </c:when>
				           <c:when test="${bizFactorCfg.stype_code eq 'DIC_ORDER_ORIGIN'}">
							  	<c:forEach var="obj" items="${DIC_ORDER_ORIGIN}">
							  		<label><input type="checkbox" value="${obj.value}" name="bizRequirementsList[${status.index}].z_value"> ${obj.value_desc}</label>
							  	</c:forEach>
				           </c:when>
				           <c:otherwise>
					           
				           </c:otherwise>
			        </c:choose>
					
				</td>	
				<td>
					<select name="bizRequirementsList[${status.index}].pre_log">
						<option value="&amp;&amp;" selected="selected">AND</option>
						<option value="||">OR</option>
					</select>
				</td>			
			</tr>
		</c:forEach>
		</table>
		
	</form>
<br />
<br />
<br />
<script type="text/javascript">
function buttonReturn(){
	var biz_name=$("#biz_name").val();
	var flow_id=$("#flow_id").val();
/* 	if(biz_name==""){
		alert("业务名称不能为空！");
		return;
	}
	if(flow_id==""){
		alert("业务编码不能为空！");
		return;
	}
	var num=/^-?[1-9]\d*$/;
	if(!num.test(flow_id)){
		alert("业务编码中请输入数字！");
		return;
	} 
	var checkdata="-1";
	for(var i=0;i<6;i++){
		var check=$("input[name='bizRequirementsList["+i+"].check']").val();
		var code_value=$("input[name='bizRequirementsList["+i+"].check']").parent().text();
		if(check!=""){
			checkdata="0";
			if($("input[name='bizRequirementsList["+i+"].z_value']").is(':checked')) {
			}else{
				alert("“"+code_value+"”子项没有勾选");
			    return;
			}
		}
			
	}
	if(checkdata!="0"){
		alert("因子必须勾选一条数据");
		return;
	}*/
	
	$("#qryFrm").attr("action","/core/admin/ecc/checkFilter!checkFilterAdd.do");
	$("#qryFrm").submit();
}
</script>
</body>
</html>