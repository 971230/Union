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
				text : "����",
				name : "name",
				tpl : "<a href='edit_datasource.jsp?{1}'>{0}</a>"
			}, {
				text : "����",
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
				<td>����洢Ŀ¼:</td>
				<td><input type="text" xtype="text" name="reportSaveDir"
					id="f1"></td>

			</tr>
			<tr>
				<td colspan="2">���Ŀ¼�����Ŀ¼�ǵ�ǰӦ�ó���װĿ¼���ϼ�Ŀ¼��</td>
			</tr>
			<tr class="field rowsplit">
				<td>��̬���ݽӿ�:</td>
				<td><input type="text" xtype="text" name="staticDataGetter"
					id="f2"></td>
			</tr>
			<tr>
				<td colspan="2">����ÿ��ϵͳ��ȡ��̬����ʱ��Ҫ����Ȩ�޿��ƣ���˵���һ���ӿ������ж��ο��������á�</td>
			</tr>


		</table>
		<br>
		<a href="">����һ���µ�����Դ</a><br>
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
		<button xtype="">����</button>
		</td>
	</tr>
</table>

