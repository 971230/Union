<link href="css/admin.css" type="text/css" rel="stylesheet">
</link>
<script src="script/Table.js" language="javascript">
</script>

<table>
	<tr class="field">
		<td>名称:</td>
		<td><input type="text" xtype="text" name="reportSaveDir"></td>

	</tr>
	<tr>
		<td colspan="2">这个目录的相对目录是当前应用程序安装目录的上级目录。</td>
	</tr>
	<tr class="field rowsplit">
		<td>类型:</td>
		<td><input type="text" xtype="text" name="staticDataGetter"></td>
	</tr>
	<tr>
		<td colspan="2">由于每个系统获取静态数据时需要进行权限控制，因此单独一个接口来进行二次开发和配置。</td>
	</tr>
</table>