<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
.tableform {
	background: none repeat scroll 0 0 #EFEFEF;
	border-color: #DDDDDD #BEC6CE #BEC6CE #DDDDDD;
	border-style: solid;
	border-width: 1px;
	margin: 10px;
	padding: 5px;
}

.division {
	background: none repeat scroll 0 0 #FFFFFF;
	border-color: #CCCCCC #BEC6CE #BEC6CE #CCCCCC;
	border-style: solid;
	border-width: 1px 2px 2px 1px;
	line-height: 150%;
	margin: 10px;
	padding: 5px;
	white-space: normal;
}

h4 {
	font-size: 1.2em;
	font-weight: bold;
	line-height: 1.25;
}

h1,h2,h3,h4,h5,h6 {
	clear: both;
	color: #111111;
	margin: 0.5em 0;
}
</style>
<div class="rightDiv">
 <div class="input" id="addPP">
<form class="validate" validate="true" name="addProcessMid" method="post" action="" id='addProcessMids' >
		<div class="tableform">
			<h4>
				配置中间数据
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0"
					width="100%" style="text-align: left;">
					<tbody>
							 <tr style="display:none;">
								<th>
									业务方案编码：
								</th>
								<td colspan="2">
									 <input type="text" style="width: 300px;" class="ipttxt" id="mid_data_code"  name="mid_data_code" value="${mid_data_code}" required="true" autocomplete="on" dataType="string">
								</td>
							</tr>
							<tr>
								<th>
									Fact的Java类型：
								</th>
								<td>
									<input type="text" style="width: 300px;" class=" ipttxt" id="ctrl_val" fact_java_class="fact_java_class" name="fact_java_class" value="${fact_java_class}" required="true" autocomplete="on" dataType="string"> 
								</td>
								<td><span>填写一个继承com.ztesoft.rule.core.module.fact.DefFact的类，如:com.OrderFact。</span> </td>
						    </tr> 
							<tr>
								<th>
									计算方式：
								</th>
								<td colspan="2">
									 <input type="radio" name="cal_type"   value="JAVA" ${(mid.cal_type==null||mid.cal_type=='JAVA')?'checked':'' }/>JAVA
									 <input type="radio" name="cal_type"   value="SQL" ${mid.cal_type=='SQL'?'checked':'' }>SQL
								</td>
							</tr>
							<tr>
								<th>
									计算逻辑：
								</th>
								<td>
									<textarea style="width: 300px;height: 80px;" cal_logic="cal_logic" value="cal_logic" name="cal_logic"></textarea>
								</td>
								<td><span>填写一个继承com.ztesoft.rule.core.ext.DefFactLoader的类，如:com.OrderLoader。</span> </td>
							</tr>
						
						<tr>
							<th>
								是否需要处理结果：
							</th>
							<td colspan="2">
							
							     <input type="radio"  name="need_process_data" value="T" ${(mid.need_process_data==null||mid.need_process_data=='T')?'checked':'' } />处理
								 <input type="radio" name="need_process_data" value="F" ${mid.need_process_data=='F'?'checked':'' } />不处理
							</td>
				       </tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="tableform" style="display: ${need_process_data!='F'?'block;':'none;'}" id="changeT">
			<h4>
				配置结果集处理
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0"
					width="100%" style="text-align: left;">
					<tbody>
						
						<tr>
							<th>
								方案计算方式：
							</th>
							<td colspan="2">
								<input type="radio" list_cal_type="list_cal_type" name="list_cal_type" value="JAVA" ${(process.list_cal_type==null||process.list_cal_type=='JAVA')?'checked':'' } />JAVA
								<input type="radio" list_cal_type="list_cal_type" name="list_cal_type" value="SQL" ${process.list_cal_type=='SQL'?'checked':'' }/>SQL
							</td>
						</tr>
							<tr >
								<th>
									方案计算逻辑：
								</th>
								<td>
									<textarea style="width: 300px;height: 80px;" name="list_cal_logic" list_cal_logic="list_cal_logic"></textarea>
								</td>
								<td><span>填写一个继承com.ztesoft.rule.core.ext.DefRuleResultProssor的类，如:com.OrderResultProssor。</span> </td>
							</tr>
						<tr>
							<th>
								规则计算方式：
							</th>
							<td colspan="2">
								<input type="radio" name="detail_cal_type"  value="JAVA"${(process.detail_cal_type==null||process.detail_cal_type=='JAVA')?'checked':'' } />JAVA
								<input type="radio" name="detail_cal_type"  value="SQL"${process.detail_cal_type=='SQL'?'checked':'' } />SQL
							</td>
						</tr>
						<tr >
							<th>
								规则计算逻辑：
							</th>
							<td>
								<textarea style="width: 300px;height: 80px;" name="detail_cal_logic" detail_cal_logic="detail_cal_logic"></textarea>
							</td>
							<td><span>填写一个继承com.ztesoft.rule.core.ext.DefRuleResultProssor的类，如:com.OrderResultProssor。</span> </td>
				       </tr>
					</tbody>
				</table>
			</div>
		</div>
		 <div class="submitlist" align="center" style="height: 100px">
			 <input type="button"  id="addSaveProcess" value=" 保存 " class="submitBtn" name='submitBtn'/>
		</div>
 	</form> 
 </div>
</div>   
<script type="text/javascript" >
$("#addSaveProcess").unbind("click").bind("click",function(){
	/* if(document.addProcessMid.mid_data_code.value.length   ==   0)   {  
  		alert("请输入有业务方案编码!");
  		document.addProcessMid.mid_data_code.focus();
  		return   false;
  	 } */
	if(document.addProcessMid.cal_logic.value.length   ==   0)   {  
  		alert("请输入计算逻辑!");
  		document.addProcessMid.cal_logic.focus();
  		return   false;
  	 }
	if(document.addProcessMid.fact_java_class.value.length   ==   0)   {  
  		alert("请输入Fact的Java类型!");
  		document.addProcessMid.fact_java_class.focus();
  		return   false;
  	 }
	if(document.addProcessMid.list_cal_logic.value.length   ==   0)   {  
  		alert("请输入清单计算逻辑!");
  		document.addProcessMid.list_cal_logic.focus();
  		return   false;
  	 }
	if(document.addProcessMid.detail_cal_logic.value.length   ==   0)   {  
  		alert("请输入详情计算逻辑!");
  		document.addProcessMid.detail_cal_logic.focus();
  		return   false;
  	 }
		var dataCode=$("input[name=mid_data_code]").val();
		var caltype=$("input[name=cal_type]:checked").val();
		var calLogic=$("textarea[name=cal_logic]").val();
		var javaClass=$("input[name=fact_java_class]").val();
		var processData=$("input[name=need_process_data]:checked").val();
		var listCalType=$("input[name=list_cal_type]:checked").val();
		var listCallogic=$("textarea[name=list_cal_logic]").val();
		var detailCalType=$("input[name=detail_cal_type]:checked").val();
		var detailCalLogic=$("textarea[name=detail_cal_logic]").val();
		
		var tr= "<tr >";
		tr += "<input type='hidden' name='dataCode' value='"+dataCode+"'>";
		tr += "<input type='hidden' name='caltype' value='"+caltype+"'>";
		tr += "<input type='hidden' name='calLogic' value='"+calLogic+"'>";
		tr += "<input type='hidden' name='javaClass' value='"+javaClass+"'>";
		tr += "<input type='hidden' name='processData' value='"+processData+"'>";
		tr += "<input type='hidden' name='listCalType' value='"+listCalType+"'>";
		tr += "<input type='hidden' name='listCallogic' value='"+listCallogic+"'>";
		tr += "<input type='hidden' name='detailCalType' value='"+detailCalType+"'>";
		tr += "<input type='hidden' name='detailCalLogic' value='"+detailCalLogic+"'>";
		
		//tr += "<td style='text-align:center'>"+dataCode+"</td>";
		tr += "<td style='text-align:center'>"+caltype+"</td>";
		tr += "<td style='text-align:center'>"+calLogic+"</td>";
		tr += "<td style='text-align:center'>"+javaClass+"</td>";
		tr += "<td style='text-align:center'>"+processData+"</td>";
		tr += "<td style='text-align:center'>"+listCalType+"</td>";
		tr += "<td style='text-align:center;display:none;'>"+listCallogic+"</td>";
		tr += "<td style='text-align:center'>"+detailCalType+"</td>";
		tr += "<td style='text-align:center;display:none;'>"+detailCalLogic+"</td>";
		
		tr += "<td style='text-align:center'><a href='javascript:void(0);' class='edit_mid_proces'>修改</a>&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' name='del_mid_process'>删除</a></td>";
		tr += "</tr>";
		$("#midListss").append(tr);
		Eop.Dialog.close("addProcessMid");
});
  $("input[name='need_process_data']").bind("click",function(){
			var val = $(this).attr("value");
			if(val=='T'){
				$("#changeT").show();
			}else{
				$("#changeT").hide();
			}
  });
</script>
