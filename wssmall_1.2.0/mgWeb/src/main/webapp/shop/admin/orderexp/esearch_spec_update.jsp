<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>

<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>

					<li id="show_click_2" class="selected"><span class="word">修改关键字规格</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form action="" class="validate" method="post"
		name="theForm" id="theForm" >
		<input type="hidden" name="ajax" value="yes"/>
		<table class="form-table">
			<tr>
				<th><label class="text"><span class='red'></span>搜索编码：</label></th>
				<td>
					<input type="hidden" value="${esearchSpec.search_code }" name = "sdqInner.search_code" id="search_code" />
					${esearchSpec.search_code }
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>搜索id：</label></th>
				<td>
					<input type="hidden" value="${esearchSpec.search_id }" name = "sdqInner.search_id" id="search_id" />
					${esearchSpec.search_id }
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>搜索名称：</label></th>
				<td>
					<input type="text" class="ipttxt" value="${esearchSpec.search_name }" name = "sdqInner.search_name" id="search_name" />
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'></span>处理器：</label></th>
				<td>
					<input type="text" class="ipttxt" value="${esearchSpec.hander_class }" name = "sdqInner.hander_class" id="hander_class" />
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>解析报文字段：</label></th>
				<td>
					<select name="sdqInner.search_field" id="search_field">
						<option value="0" <c:if test="${esearchSpec.search_field == '0'}">selected</c:if> >出参报文</option>
						<option value="1" <c:if test="${esearchSpec.search_field == '1'}">selected</c:if> >入参报文</option>
					</select>
				</td>
			</tr>
			<tr>
				<th><label class="text"><span class='red'>*</span>开关标识：</label></th>
				<td>
					<select name="sdqInner.flag" id="flag">
						<option value="0" <c:if test="${esearchSpec.flag == '0'}">selected</c:if> >开</option>
						<option value="1" <c:if test="${esearchSpec.flag == '1'}">selected</c:if> >关</option>
					</select>
				</td>
			</tr>
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td>
						<input type="button" id="confirm_btn" value="确  定 " class="submitBtn" onclick="submit_data();" />
						<input type="button" id="back_btn" value="返  回 " class="submitBtn" onclick="window.location.href='${ctx}/shop/admin/esearchSpec!list.do'" />
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<script>
	function submit_data(){
		if($("#search_name").val() == ""){
			alert("请输入搜索名称");
			return false;
		}
		if($("#search_field").val() == ""){
			alert("请选择解析报文字段");
			return false;
		}
		if($("#flag").val() == ""){
			alert("请选择开关标识");
			return false;
		}

		var json_data = $("#theForm").serialize();
		$.ajax({
			 type: "POST",
			 url: "esearchSpec!update.do",
			 data: json_data,
			 dataType:"json",
			 success: function(result){ 
				 if (result.result == '1') {
					 alert(result.message);
					 window.location.href = ctx + '/shop/admin/esearchSpec!list.do';
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