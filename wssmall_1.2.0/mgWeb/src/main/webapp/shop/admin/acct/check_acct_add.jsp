<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.input table th {
	width: 180px;
}
</style>
<script type="text/javascript" src="js/check_acct_add.js?d=111"></script>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>

					<li id="show_click_2" class="selected">
						<span class="word">对账配置</span><span class="bg"></span>
					</li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="input">
	<form action="javascript:void(0)" class="validate" method="post"
		name="theForm" id="theForm" enctype="multipart/form-data">
		<table class="form-table">
			<input type="hidden" id="system_id" value="${checkAcctConfig.system_id}"/>
			<tr>
				<th>
					<label class="text">
						<span class='red'>*</span>调度名称：
					</label>
				</th>
				<td>
					<input type="text" class="ipttxt" value="${checkAcctConfig.system_name }"
						name="checkAcctConfig.system_name" dataType="string"
						required="true" />
				</td>
			</tr>

			<tr>
				<th>
					<label class="text">
						<span class='red'>*</span>对账商户名称：
					</label>
				</th>
				<td>
					<input type="text" class="ipttxt" name="checkAcctConfig.banknmae"
					  value="${checkAcctConfig.banknmae }" dataType="string" required="true" />
					<span class='red'>说明：翼支付对账，则名称为翼支付</span>
				</td>
			</tr>

			<tr>
				<th>
					<label class="text">
						<span class='red'>*</span>对账商户工号：
					</label>
				</th>
				<td>
					<input type="text" class="ipttxt" name="checkAcctConfig.a_shop_no"
						value="${checkAcctConfig.a_shop_no }" required="true" />
				</td>
			</tr>

			<tr>
				<th>
					<label class="text">
						<span class='red'>*</span>对账文件后缀：
					</label>
				</th>
				<td>
					<input type="text" class="ipttxt" value="${checkAcctConfig.a_file_tail }"
						name="checkAcctConfig.a_file_tail"  required="true" />
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						<span class='red'>*</span>对账文件名：
					</label>
				</th>
				<td>
					<input type="text" class="ipttxt" value="${checkAcctConfig.a_file_name }"
						name="checkAcctConfig.a_file_name" dataType="string"
						required="true" />
					<span class='red'>说明：对账文件名格式：对账商户账号+对账文件后缀</span>
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						<span class='red'>*</span>对账文件本地存放路径：
					</label>
				</th>
				<td>
					<input type="text" class="ipttxt" value="${checkAcctConfig.a_file_local_path }" 
						name="checkAcctConfig.a_file_local_path" required="true" />
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						<span class='red'>*</span>对账文件远程存放路径：
					</label>
				</th>
				<td>
					<input type="text" class="ipttxt" value="${checkAcctConfig.a_file_remote_path }"
						name="checkAcctConfig.a_file_remote_path" required="true" />
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						<span class='red'>*</span>FTP地址：
					</label>
				</th>
				<td>
					<input class="ipttxt" name="checkAcctConfig.up_ftp_addr"
						value="${checkAcctConfig.up_ftp_addr }" dataType="string" required="true" />
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						<span class='red'>*</span>FTP端口：
					</label>
				</th>
				<td>
					<input class="ipttxt" name="checkAcctConfig.up_ftp_port"
						value="${checkAcctConfig.up_ftp_port }" dataType="string" required="true" />
				</td>
			</tr>
			<tr id="lantr">
				<th>
					<label class="text">
						<span class='red'>*</span>FTP用户名：
					</label>
				</th>
				<td>
					<input class="ipttxt" name="checkAcctConfig.up_ftp_user"
						value="${checkAcctConfig.up_ftp_user }" dataType="string" required="true" />
				</td>
			</tr>
			<tr id="roletr">
				<th>
					<label class="text">
						<span class='red'>*</span>FTP密码：
					</label>
				</th>
				<td>
					<input class="ipttxt" name="checkAcctConfig.up_ftp_passwd"
						 value="${checkAcctConfig.up_ftp_passwd }" dataType="string" required="true" />
				</td>
			</tr>

			<tr>
				<th>
					<label class="text">
						<span class='red'>*</span>生成对账文件前缀：
					</label>
				</th>
				<td>
					<input class="ipttxt" name="checkAcctConfig.bns_filehead"
						value="${checkAcctConfig.bns_filehead }" dataType="string" required="true" />
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						<span class='red'>*</span>生成对账文件后缀：
					</label>
				</th>
				<td>
					<input class="ipttxt" name="checkAcctConfig.bns_filetail"
						value="${checkAcctConfig.bns_filetail }" dataType="string" required="true" />
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						<span class='red'>*</span>生成对账文件本地存放路径：
					</label>
				</th>
				<td>
					<input class="ipttxt" name="checkAcctConfig.bns_local_file_path"
						value="${checkAcctConfig.bns_local_file_path }" dataType="string" required="true" />
				</td>
			</tr>
			<tr>
				<th>
					<label class="text">
						<span class='red'>*</span>生成对账文件远程存放路径：
					</label>
				</th>
				<td>
					<input class="ipttxt" name="checkAcctConfig.bns_remote_file_path"
						value="${checkAcctConfig.bns_remote_file_path }" dataType="string" required="true" />
				</td>
			</tr>
		</table>

		<div class="submitlist" align="center">
			<table>
				<tr>
					<th></th>
					<td>
						<input type="submit" id="saveAcct" value=" 确    定   "
							class="submitBtn" />
					</td>

				</tr>
			</table>
		</div>
	</form>
</div>
