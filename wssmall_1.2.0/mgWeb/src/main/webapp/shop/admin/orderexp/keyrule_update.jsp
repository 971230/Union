<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>

<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>

					<li id="show_click_2" class="selected"><span class="word">关键字抽取规则修改</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>

<div class="input">
	<form action="javascript:void(0)" class="validate" method="post"
		name="theForm" id="theForm" >
		<input type="hidden" name="ajax" value="yes"/>
		<input type="hidden" name="inner.key_id" value="${keyRule.key_rule_id }"/>
		<table class="form-table">
			<tr>
				<th><label class="text"><span class='red'>*</span>搜索id：</label></th>
				<td><input type="text" class="ipttxt"  value = "<c:out value="${keyRule.search_id }"/>" name = "inner.search_id" id="search_id" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>抽取顺序：</label></th>
				<td><input type="text" class="ipttxt"  value = "<c:out value="${keyRule.seq }"/>" name = "inner.seq" id="seq" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>解析表达式：</label></th>
				<td><input type="text" class="ipttxt"  value = "<c:out value="${keyRule.express }"/>" name = "inner.express" id="express" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>处理类：</label></th>
				<td><input type="text" class="ipttxt"  value = "<c:out value="${keyRule.hander }"/>" name = "inner.hander" id="hander" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>开始位置：</label></th>
				<td><input type="text" class="ipttxt"  value = "<c:out value="${keyRule.begin_index }"/>" name = "inner.begin_index" id="begin_index" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>结束位置：</label></th>
				<td><input type="text" class="ipttxt"  value = "<c:out value="${keyRule.end_index }"/>" name = "inner.end_index" id="end_index" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>开始字符：</label></th>
				<td><input type="text" class="ipttxt"  value = "<c:out value="${keyRule.begin_word }"/>" name = "inner.begin_word" id="begin_word" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>结束字符：</label></th>
				<td><input type="text" class="ipttxt"  value = "<c:out value="${keyRule.end_word }"/>" name = "inner.end_word" id="end_word" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>关键字标识：</label></th>
				<td>
					<select name="inner.key_word_type" class="ipttxt">
						<option value="succ" <c:if test="${keyRule.key_word_type == 'succ' }">selected</c:if> >成功</option>
						<option value="fail" <c:if test="${keyRule.key_word_type == 'fail' }">selected</c:if> >失败</option>
						<option value="filter" <c:if test="${keyRule.key_word_type == 'filter' }">selected</c:if> >过滤</option>
					</select>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>匹配字符：</label></th>
				<td><input type="text" class="ipttxt"  value = "<c:out value="${keyRule.match_word }"/>" name = "inner.match_word" id="match_word" required="true" /></td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>截取字符：</label></th>
				<td><input type="text" class="ipttxt"  value = "<c:out value="${keyRule.cut_word }"/>" name = "inner.cut_word" id="cut_word" required="true" /></td>
			</tr>
			<tr>
				<td colspan="2" style="color:gray;">
					说明：<br/>
					1.抽取规则先按抽取顺序正序排序，若一条抽取规则抽取到关键字则抽取成功，后面的抽取规则不再执行<br/><br/>
					2.每条抽取规则，先按匹配字符抽取（抽到马上返回），再按起始字符和结束字符抽取（抽到马上返回），<br/>
					再按起始下标和结束下标抽取（抽到马上返回）。注意：在按起始字符和结束字符抽取时，若有解析表达式，<br/>
					则在起始字符和结束字符抽取后会再用解析表达式过滤<br/><br/>
					3.如果是业务异常，则直接用解析表达式抽取
				</td>
			</tr>
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td>
						<input type="button" id="btn" value="确  定 " class="submitBtn" onclick="submit_data();" />
						<input type="button" id="btn" value="返  回 " class="submitBtn" onclick="window.location.href='${ctx}/shop/admin/keyrule!list.do'" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script>
	function submit_data(){
		if($("#search_id").val() == ""){
			alert("请输入搜索id");
			return false;
		}
		if($("#seq").val() == ""){
			alert("请输入抽取顺序");
			return false;
		}
		if($("#seq").val() != ""){
			var seq = $("#seq").val();
			var reg = new RegExp("^[0-9]*$");
			if(!reg.test(seq)){
				alert("抽取顺序 请输入数字!");
				return false;
			}
		}
		if($("#begin_index").val() != ""){
			var begin_index = $("#begin_index").val();
			var reg = new RegExp("^[0-9]*$");
			if(!reg.test(begin_index)){
				alert("开始位置 请输入数字!");
				return false;
			}
		}
		if($("#end_index").val() != ""){
			var end_index = $("#end_index").val();
			var reg = new RegExp("^[0-9]*$");
			if(!reg.test(begin_index)){
				alert("结束位置 请输入数字!");
				return false;
			}
		}
		if($("#key_word_type").val() == ""){
			alert("请选择关键字标识");
			return false;
		}
		
		var json_data = $("#theForm").serialize();
		$.ajax({
			 type: "POST",
			 url: "keyrule!update.do",
			 data: json_data,
			 dataType:"json",
			 success: function(result){
				 
				 if (result.result == '0') {
					 window.location.href = ctx + '/shop/admin/keyrule!list.do';
			     } else {
			    	 alert(result.message);
			     }
			 },
			 error:function(){
				 alert("操作失败，请重试");
			 }
		});
	}

</script>