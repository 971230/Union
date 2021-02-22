<link href="css/admin.css" type="text/css" rel="stylesheet">
</link>
<script src="script/Table.js" language="javascript">
	
</script>
<script src="script/Admin.js" language="javascript">
	
</script>
<script language="javascript">
	document.onReady = function() {
		new Table($("datasource_td"), {
			url : toURL("/console/ConfigBean/listDataSources.do"),
			id : "datasource_table",
			headers : [ {
				text : "名称",
				name : "name",
				tpl : "<a href='edit_datasource.jsp?{1}'>{0}</a>"
			}, {
				text : "描述",
				name : "content"
			}, {
				text : "&nbsp;",
				name : "operator",
				tpl:"<img></img><img></img>"
			} ]
		});
	}
</script>
<table cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td nowrap valign="top">
		<table>
			<tr class="field">
				<td>保存存储目录:</td>
				<td><input type="text" xtype="text" name="reportSaveDir"
					id="f1"></td>

			</tr>
			<tr>
				<td colspan="2">这个目录的相对目录是当前应用程序安装目录的上级目录。</td>
			</tr>
			<tr class="field rowsplit">
				<td>静态数据接口:</td>
				<td><input type="text" xtype="text" name="staticDataGetter"
					id="f2"></td>
			</tr>
			<tr>
				<td colspan="2">由于每个系统获取静态数据时需要进行权限控制，因此单独一个接口来进行二次开发和配置。</td>
			</tr>


		</table>
		<br>
		<a href="">创建一个新的数据源</a><br>
		<br>

		<table cellspacing="0" cellpadding="0"
			border="0">
			<tr>
				<td nowrap valign="top" id="datasource_td"></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>
		<button xtype="">保存</button>
		</td>
	</tr>
</table>

