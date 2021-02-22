<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<script type="text/javascript" src="js/ParamsEdit.js"></script>
<script type="text/javascript">$(function(){Param.init();})</script>
<script type="text/javascript">

function checkInputs(){
	var groups = document.getElementsByName("groupnames");
	var params = document.getElementsByName("paramnames");
	var paramsLen = params.length;
	var groupsLen = groups.length;
	for(var i=1;i<groupsLen;i++){
		if(groups[i].value==""){
			alert("请输入参数组名称");
			return false;
		}
	}
	for(var i=1;i<paramsLen;i++){
		if(params[i].value==""){
			alert("请输入参数名称");
			return false;
		}
	}	
	return true;
}
//编辑时下拉框事件
$(function(){
	$("input[name='requiredCheckbox']").live("click",function(){
		 if($(this).attr("checked")==true){
			 $(this).next().val("YES");
		 }
		 else{
			 $(this).next().val("NO");
		 }
	});
	$("#param_div").find("table").each(function(i,data){
		$(data).find("tr:gt(0)").each(function(idx,obj){
			$(obj).find("select[name='attrvaltypes']>option[value='"+$(obj).find(".edit_attrvaltype").attr("value")+"']").attr("selected",true);
			$(obj).find("select[name='attrtypes']>option[value='"+$(obj).find(".edit_attrtype").attr("value")+"']").attr("selected",true);
		});
		
		if($(data).find("td.attrvalselect").find("select").val()=="0"){
			
			$(data).find("td.isSelect").css("visibility","hidden");
		}else{
			$(data).find("td.isSelect").css("visibility","visible");
		}
		
		$(data).find("tbody>tr>td.attrvalselect").find("select").change(function(){
			if($(this).val()=="0"){
				$(this).closest("tr").find("td.isSelect").css("visibility","hidden");
				//$(this).closest("tr").find("td.isSelect_temp").show();
			}else{
				$(this).closest("tr").find("td.isSelect").css("visibility","visible");
				//$(this).closest("tr").find("td.isSelect_temp").hide();
				
			}
		});
	});
	
	$("#param_div").find("select[name='attrvaltypes']").each(function(i,data){
		if($(data).val()=="0"){
			$(data).closest("tr").find("td.isSelect").css("visibility","hidden");
			//$(data).closest("tr").find("td.isSelect_temp").show();
		}else{
			$(data).closest("tr").find("td.isSelect").css("visibility","visible");
			//$(data).closest("tr").find("td.isSelect_temp").hide();
		}
	});
	
});


</script>

<form method="post" action="type!saveParamsEdit.do" name="paramsEditForm"
	id="paramsEditForm" class="validate">
	<input type="hidden" name="paramnum" id="paramnum" />
	<input type="hidden" name="type_id" value="${type_id }"/>
	<div style="text-align:center">
		<div class="list-div" id="listDiv" style="width:100%;text-align:left">

			<div class="toolbar">
				<a href="javascript:;" id="paramAddBtn"><input class="comBtn"
					type="button" name="" id="" value="新增参数组"
					style="margin-right:10px;outline-style:none" /></a>

				<div style="clear:both"></div>
			</div>

			<div class="input">
				<div id="param_div">
					<c:forEach var="group" items="${goodsType.paramGroups}">
						<table cellpadding="3" cellspacing="0" style="width:100%;">

							<tr class="group">
								<td style="background-color:#DDEEF2;width:96px;">参数组名:</td>
								<td style="background-color:#DDEEF2;width:93px;" colspan="3">
									<input type="text" class="ipttxt" name="groupnames" maxlength="60"
											value="${group.name }" style="width:120px;" />
									<input type="hidden" name="paramnums" value="${fn:length(group.paramList)}"/>
								</td>
								<td style="background-color:#DDEEF2;width:100px;text-align:center;">
									<span><a href="javascript:;" class="sysbtn addBtn">新增参数</a></span> 
									&nbsp;&nbsp;
									<span><a href="javascript:;"><img class="delete" src="images/transparent.gif"></a></span></td>
								<td style="background-color:#DDEEF2;">&nbsp;</td>
								<td style="background-color:#DDEEF2;">&nbsp;</td>
								<td style="background-color:#DDEEF2;">&nbsp;</td>
								<td style="background-color:#DDEEF2;">&nbsp;</td>
								<td style="background-color:#DDEEF2;">&nbsp;</td>
								<td style="background-color:#DDEEF2;">&nbsp;</td>
								<td style="background-color:#DDEEF2;">&nbsp;</td>
								<td style="background-color:#DDEEF2;">&nbsp;</td>
								<td style="background-color:#DDEEF2;">&nbsp;</td>
							</tr>

							<c:forEach var="gparam" items="${group.paramList}">
								<tr class="param">
									<td  width="116px"><input class="edit_attrtype" type="hidden"
										value="${gparam.attrtype}" /> <select name="attrtypes"
										value="${gparam.attrtype}">
											<option value="goodsparam">商品参数</option>
											<option value="relparam">关系参数</option>
									</select>
									</td>
									<td width="53px" style="text-align:right;">中文名:</td>
									<td width="124px"><input type="text" class="ipttxt paramnames"
										name="paramnames" value="${gparam.name }" /><i></i></td>
									<td width="53px" style="text-align:right;">字段名:</td>
									<td width="124px">
										<input type="text" class="ipttxt" value="${gparam.ename }" name="enames" />
									</td>
									<td width="53px" style="text-align:right;">缺省值:</td>
									<td width="120px">
										<input type="text" class="ipttxt" value="${gparam.attrdefvalue}" name="attrdefvalues" />
									</td>
									<td width="98px" class="attrvalselect" >
										<input class="edit_attrvaltype" type="hidden" value="${gparam.attrvaltype}" /> 
										<select name="attrvaltypes" value="${gparam.attrvaltype}">
											<option value="0">文本&nbsp;&nbsp;</option>
											<option value="1">下拉框</option>
										</select>
									</td>
									<td width="43px" class="isSelect"
										style="visibility:hidden;text-align:right;">取值:</td>
									<td width="140px" class="isSelect" style="visibility:hidden;">
										<input type="text" class="ipttxt" value="${gparam.attrcode}" name="attrcodes" />
									</td>
									<td width="85px;align:center;">
							       		是否必填<input type="checkbox" name="requiredCheckbox" <c:if test="${gparam.required=='YES' }">checked='checked'</c:if>>
							       			 <input type="hidden" name="required" value="${gparam.required}">
							       	</td>
									<td>
							       		<a href="javascript:;" ><img class="delete" src="images/transparent.gif" ></a>
							       	</td>
								</tr>
							</c:forEach>
						</table>
					</c:forEach>
				</div>
			</div>
			<div class="btn_box" align="center">
			 <a href="javascript:void(0);" class="blue_b" style="margin-right:5px;" id="saveParamsBtn">保存</a>
			</div>	

		</div>
	</div>
</form>

