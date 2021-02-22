<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/check_acct_list.js"></script>
<form method="post" id='checkAcctconfig_query_form' action='checkAcct!listAcct.do'>
	<div class="searchformDiv">
		<table width="100%" cellspacing="0" cellpadding="0" border="0">
			<tbody>
				<tr>
					<th style="width: 120px;">
						调度名称：&nbsp;&nbsp;
					</th>
					<td>
						<input type="text" class="ipttxt" style="width: 95px;"
							name="checkAcctConfig.system_name" value="${checkAcctConfig.system_name}"
							class="searchipt" />
					<td>
					<th style="width: 120px;"> 
						对账商户名称：&nbsp;&nbsp;
					</th>
					<td>
						<input type="text" class="ipttxt" style="width: 95px;"
							name="checkAcctConfig.banknmae" value="${checkAcctConfig.banknmae}"
							class="searchipt" />
					</td>
					<th style="width: 120px;">
						对账商户工号：&nbsp;&nbsp;
					</th>
					<td>
						<input type="text" class="ipttxt" style="width: 95px;"
							name="checkAcctConfig.a_shop_no" value="${checkAcctConfig.a_shop_no}"
							class="searchipt" />
					</td>
					<td>
						<input type="submit" style="margin-right: 10px;" class="comBtn"
							value="搜&nbsp;索" id="submitButton" name="button">
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="comBtnDiv">
		<a style="margin-right: 10px;" class="graybtn1"
			id='addCheckAcct' href="checkAcct!addAcct.do"><span>添加</span>
		</a>
	</div>
</form>

<div id="acctList">
	<div class="grid">
		<form method="POST" id="acct_list_form">
			<grid:grid from="webpage" ajax="yes">
				<grid:header>
					<grid:cell >选择</grid:cell>
					<grid:cell >调度id</grid:cell>
					<grid:cell >调度名称</grid:cell>
					<grid:cell >FTP地址</grid:cell>
					<grid:cell >FTP端口</grid:cell>
					<grid:cell >对账商户名称</grid:cell>
					<grid:cell >对账商户工号</grid:cell>
					<grid:cell >对账文件名</grid:cell>
					<grid:cell >操作</grid:cell>
				</grid:header>
				<grid:body item="checkAcctconfig">
					<grid:cell>
						<input type="radio" name="checkAcctconfig_checkbox" />
					</grid:cell>
					<grid:cell>${checkAcctconfig.system_id}</grid:cell>
					<grid:cell>${checkAcctconfig.system_name}</grid:cell>
					<grid:cell>${checkAcctconfig.up_ftp_addr}</grid:cell>
					<grid:cell>${checkAcctconfig.up_ftp_port}</grid:cell>
					<grid:cell>${checkAcctconfig.banknmae}</grid:cell>
					<grid:cell>${checkAcctconfig.a_shop_no}</grid:cell>
					<grid:cell>${checkAcctconfig.a_shop_no}.${checkAcctconfig.a_file_tail}</grid:cell>
					<grid:cell>
						<a href="checkAcct!addAcct.do?checkAcctConfig.system_id=${checkAcctconfig.system_id}" class="editCheckAcct">修改</a>|
						<a href="javascript:void(0);" class="delCheckAcct" attr="${checkAcctconfig.system_id}">删除</a>
					</grid:cell>
				</grid:body>
			</grid:grid>
		</form>
	</div>
</div>
<div id="del_acct_config"></div>
