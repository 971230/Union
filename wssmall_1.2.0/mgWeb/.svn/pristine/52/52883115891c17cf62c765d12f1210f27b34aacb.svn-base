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
<form class="validate" validate="true" method="post" action="" id='editProcessMidsee' >
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
									<input type="text" style="width: 300px;" class=" ipttxt" id="mid_data_code" class="mid_data_code" name="mid_data_code" value="${mid_data_code}" required="true" autocomplete="on" dataType="string">   
								</td>
							</tr> 
							<tr>
							<th>
								Fact的Java类型：
							</th>
							<td>
								<input type="text" style="width: 300px;" class=" ipttxt" id="fact_java_class" fact_java_class="fact_java_class" name="fact_java_class" value="${fact_java_class}" required="true" autocomplete="on" dataType="string">   
							</td>
							<td><span>填写一个继承com.ztesoft.rule.core.module.fact.DefFact的类，如:com.OrderFact。</span> </td>
						</tr>
							<tr>
								<th>
									计算方式：
								</th>
								<td colspan="2">
									 <input type="radio" id="cal_type" cal_type="cal_type" name="cal_type" value="JAVA" ${(cal_type==null||cal_type=='JAVA')?'checked':'' } />JAVA
									 <input type="radio" id="cal_type" cal_type="cal_type" name="cal_type" value="SQL" ${cal_type=='SQL'?'checked':'' }/>SQL
								</td>
							</tr>
							<tr >
								<th>
									计算逻辑：
								</th>
								<td>
								  <textarea style="width: 300px;height: 80px;" id="cal_logic" cal_logic="cal_logic" name="cal_logic">${cal_logic }</textarea> 
								</td>
								<td><span>填写一个继承com.ztesoft.rule.core.ext.DefFactLoader的类，如:com.OrderLoader。</span> </td>
							</tr>
						
						<tr>
							<th>
								是否需要处理结果：
							</th>
							<td>
							     <input type="radio" need_process_data="need_process_data" name="need_process_data" value="T" ${(need_process_data==null||need_process_data=='T')?'checked':'' } />处理
								 <input type="radio" need_process_data="need_process_data" name="need_process_data" value="F" ${need_process_data=='F'?'checked':'' }/>不处理
							</td>
				       </tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="tableform" style="display: ${need_process_data!='F'?'block;':'none;'}" id="changedT">
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
								<input type="radio" list_cal_type="list_cal_type" name="list_cal_type" value="JAVA" ${(list_cal_type==null||list_cal_type=='JAVA')?'checked':'' } />JAVA
								<input type="radio" list_cal_type="list_cal_type" name="list_cal_type" value="SQL" ${list_cal_type=='SQL'?'checked':'' }/>SQL
							</td>
						</tr>
						<tr>
							<th>
								方案计算逻辑：
							</th>
							<td>
								<textarea style="width: 300px;height: 80px;" id="list_cal_logic" list_cal_logic="list_cal_logic" name="list_cal_logic">${list_cal_logic }</textarea> 
							</td>
							<td><span>填写一个继承com.ztesoft.rule.core.ext.DefRuleResultProssor的类，如:com.OrderResultProssor。</span> </td>
						</tr>
						<tr>
							<th>
								规则计算方式：
							</th>
							<td colspan="2">
								<input type="radio" detail_cal_type="detail_cal_type" name="detail_cal_type" value="JAVA" ${(detail_cal_type==null||detail_cal_type=='JAVA')?'checked':'' } />JAVA
								<input type="radio" detail_cal_type="detail_cal_type" name="detail_cal_type" value="SQL" ${detail_cal_type=='SQL'?'checked':'' }/>SQL 
							</td>
						</tr>
						<tr>
							<th>
								规则计算逻辑：
							</th>
							<td>
								<textarea style="width: 300px;height: 80px;" id="detail_cal_logic" detail_cal_logic="detail_cal_logic" name="detail_cal_logic">${detail_cal_logic }</textarea>  
							</td>
							<td><span>填写一个继承com.ztesoft.rule.core.ext.DefRuleResultProssor的类，如:com.OrderResultProssor。</span> </td>
				       </tr>
					</tbody>
				</table>
			</div>
		</div>
		 <div class="submitlist" align="center" style="height: 100px">
			 <input type="button"  id="editSaveProcessed" value=" 保存 " class="submitBtn" />
		</div>
 	</form> 
 </div>
</div>   
<script type="text/javascript" >
$("input[name='need_process_data']").bind("click",function(){
	var val = $(this).attr("value");
	if(val=='T'){
		$("#changedT").show();
		
	}else{
		//F
		$("#changedT").hide();
	}
}); 
</script>
