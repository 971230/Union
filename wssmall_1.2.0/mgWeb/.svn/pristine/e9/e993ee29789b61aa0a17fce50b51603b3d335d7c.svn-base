<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected"><span class="word">终端详情</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<div id="brandInfo">
		<form method="post" action="terminal!save.do" id="terminalForm" class="validate"
			name="theForm" enctype="multipart/form-data">
			<table cellspacing="1" cellpadding="3" width="100%"
				class="form-table">
				<tr>
					<th><label class="text">终端名称:</label><input type="hidden" name="terminal.id" value="${terminal.id}"></th>
					<td><input type="text" class="ipttxt" name="terminal.name"
						maxlength="60" value="${terminal.name }" dataType="string" required="true" /></td>
					<th><label class="text">终端型号:</label></th>
					<td><input type="text" class="ipttxt" name="terminal.model"
						maxlength="60" value="${terminal.model }" dataType="string" required="true" /></td>
				</tr>
				<tr>
					<th><label class="text">终端品牌:</label></th>
					<td>
						<html:selectdict curr_val="${terminal.brand}"  
							style="width:155px;height:25px;" id="terminal.brand"
							name ="terminal.brand" 
							attr_code="DC_ITV_BRAND">
						</html:selectdict>
					</td>
					<th><label class="text">MAC地址:</label></th>
					<td><input type="text" class="ipttxt" name="terminal.mac"
						maxlength="60" value="${terminal.mac }" dataType="string" required="true" /></td>
				</tr>
				<tr>
					<!-- <td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td> -->
					<td colspan="4"><a style="margin-left:240px;" href="javascript:void(0);" class="searchBtn" id="terminalConfirmBtn"><span>确&nbsp;定</span></a></td>
				</tr>
			</table>

		</form>
	</div>
</div>
<script type="text/javascript">
	$(function() {
		$("form.validate").validate();
		
		$("input[name='terminal.mac']").blur(function(){
			var val = $("input[name='terminal.mac']").val();
			var reg = "[0-9A-Za-z]{2}:[0-9A-Za-z]{2}:[0-9A-Za-z]{2}:[0-9A-Za-z]{2}:[0-9A-Za-z]{2}:[0-9A-Za-z]{2}";
			if(!val.match(reg)){
				$("input[name='terminal.mac']").next("span").removeClass("note ok").addClass("error").html("MAC格式错误");
				return false;
			}
		});
		
		$("#terminalConfirmBtn").bind("click",function(){
			/* if($("#terminalForm").find("input[required='true'][value='']").length>0){
				alert("表单中有必填项，请检查！");
				return;
			}else{
				$("#terminalForm").submit();
			} */
			var val = $("input[name='terminal.mac']").val();
			var reg = "[0-9A-Za-z]{2}:[0-9A-Za-z]{2}:[0-9A-Za-z]{2}:[0-9A-Za-z]{2}:[0-9A-Za-z]{2}:[0-9A-Za-z]{2}";
			if(val.match(reg)){
				$("#terminalForm").submit();
			}else {
				$("input[name='terminal.mac']").next("span").removeClass("note").addClass("error").html("MAC格式错误");
				return false;
			}
		});
	});
</script>