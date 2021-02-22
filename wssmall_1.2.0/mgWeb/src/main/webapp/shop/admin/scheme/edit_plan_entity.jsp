<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/commons/taglibs.jsp"%>
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
<div class="input">
<form method="post" id="plan_entity_edit_from"  >
<input type="hidden" name="action" value="${action }"/>
<input name="entity.rel_id" type="hidden" value="${entity.rel_id}">
<input name="entity.plan_id" type="hidden" value="${entity.plan_id}">
		<div class="tableform">
			<h4>
				编辑方案参与者
			</h4>
			<div class="division">
				<table class="form-table" cellspacing="0" cellpadding="0" border="0"
					width="100%" style="text-align: left;">
					<tbody>
						<tr>
							<th>
								关系类型(表字段)：
							</th>
							<td id="member_lv_td_sp">
								 <input type="text" class=" ipttxt" id="rule_type" name="entity.rel_type" value="${entity.rel_type}" required="true" autocomplete="on" dataType="string">
							</td>
						</tr>
						<tr>
							<th>
								实体类型(表名)：
							</th>
							<td>
								<input type="text" class=" ipttxt" id="entity_type" name="entity.entity_type" value="${entity.entity_type}" required="true" autocomplete="on" dataType="string">
							</td>
						</tr>
						<tr>
							<th>
								实体标识(条件值)：
							</th>
							<td>
								<input type="text" class=" ipttxt" id="entity_id" name="entity.entity_id" value="${entity.entity_id}" required="true" autocomplete="on" dataType="string">
							</td>
						</tr>
						<tr>
							<th>
								实体SQL：
							</th>
							<td>
							   <textarea style="width: 60%;height: 60px;" id="entity_sql" class=" ipttxt" name="entity.entity_sql" required="true" autocomplete="on" dataType="string">${entity.entity_sql}</textarea> 
							</td>
						</tr>
						
						<tr>
							<th>
								生效时间：
							</th>
							<td>
								 <input type="text"  name="entity.eff_date" id="eff_date"
									value='${entity.eff_date }'
									readonly="readonly"
									maxlength="60" class="dateinput ipttxt"  dataType="date"/>   
							</td>
						</tr>
						<tr>
							<th>
								失效时间：
							</th>
							<td>
								<input type="text"  name="entity.exp_date" id="exp_date"
									value='${entity.exp_date }'
									readonly="readonly"
									maxlength="60" class="dateinput ipttxt"  dataType="date"/> 
							</td>
						</tr>
						<tr>
							<th>
								状态：
							</th>
							<td>
								 <select class="ipttxt" name="entity.status_cd" style="width: 168px;height: 25px" required="true" >
									<option value="00A" ${(entity.status_cd=='00A' || entity.status_cd=='')?'selected':'' }>有效</option>
									<option value="00N" ${entity.status_cd=='00N'?'selected':'' }>新建</option>
									<option value="00X" ${entity.status_cd=='00X'?'selected':'' }>无效</option>
									<option value="00M" ${entity.status_cd=='00M'?'selected':'' }>审核中</option>
								</select>   
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		 <div class="submitlist" align="center" style="height: 100px">
			 <input type="button"  id="plan_entity_edit_save_btn" value=" 保存 " class="submitBtn" name='submitBtn'/>
		</div>
 	</form> 
 </div>

<script type="text/javascript">
$(document).ready(function() {
	//$("input[dataType=date]").datepicker('option', 'changeYear', true); 
	$("input[dataType=date]").datepicker({dateFormat: 'yy-mm-dd',changeMonth:true,changeYear:true});
	//$("input[dataType=date]").datetimepicker();
});
</script>