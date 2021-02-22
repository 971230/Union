<link href="css/admin.css" type="text/css" rel="stylesheet">
</link>
<script src="script/Table.js" language="javascript">
	
</script>
<script src="script/Admin.js" language="javascript">
	
</script>
<script language="javascript">
   function enter(e){
	   var o=window.location.href.toQueryParams();
	   o["report_dir"]=new TextField("f1").getValue();
	   Report.doGet(window.toURL("/console/ReportDirectoryTreeBean/createDir.do"),o,function(){
              window.location.href="xmlreport.jsp?"+Object.toQueryString(o);
		   });
   }
   function goback(e){
	   window.location.href="xmlreport.jsp?"+Report.getRequestPatameters();
   }
</script>
<table style="margin: 2px" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td nowrap>目录名称：</td>
		<td><input xtype="text" name="report_dir" id="f1"></td>
	</tr>
	<tr>
		<td nowrap colspan="2">
		<button xtype="button" onclick="enter(event);">确定</button>
<button xtype="button" onclick="goback(event);">返回</button>
		</td>
	</tr>
</table>
