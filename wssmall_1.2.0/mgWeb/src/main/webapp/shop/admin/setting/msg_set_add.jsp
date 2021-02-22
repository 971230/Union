<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp" %>

<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>

					<li id="show_click_2" class="selected"><span class="word">短信模板设置</span><span
						class="bg"></span></li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form action="sysSetting!msgSetSave.do" class="validate" method="post"
		name="theForm" id="theForm" enctype="multipart/form-data">
		<table class="form-table">
		    <tr>
			    <th>
				    <label class="text"><span class='red'>*</span>接收者名称：</label>
				</th>
				<td>
				    <input type="text" id="receiver" name="msgSet.receiver" dataType="String" class="ipttxt" value="${msgSet.receiver}" required="true" />
				</td>
			</tr>
			
			<tr>
			    <th>
				    <label class="text"><span class='red'>*</span>接收者号码：</label>
				</th>
				<td>
				    <input type="text" id="mobile_no" name="msgSet.mobile_no" style="width:580px;" dataType="String" 
				           title="可以输入多个号码以英文逗号“,”隔开" class="ipttxt" value="${msgSet.mobile_no}" required="true" />
				    <span>可以输入多个号码以英文逗号“,”隔开</span>
				</td>
			</tr>
			<tr>
			    <th>
				    <label class="text"><span class='red'>*</span>短信内容：</label>
				</th>
				<td>
				    <input type="text" id="msg" name="msgSet.msg" style="width:780px;" dataType="String" class="ipttxt" value="${msgSet.msg}" required="true" />
				</td>
			</tr>

		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td>
					<input type="hidden" name="msgSet.set_id" value="${msgSet.set_id}"> 
					<input type="submit" id="btn"  value=" 确  定 " class="greenbtnbig" style="cursor:pointer;" /></td>
				</tr>
			</table>
		</div>
	</form>
</div>

<script>

    $("form.validate").validate();

</script>