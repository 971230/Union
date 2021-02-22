<link href="css/admin.css" type="text/css" rel="stylesheet">
</link>
<script src="script/Table.js" language="javascript">
	
</script>
<script src="script/Admin.js" language="javascript">
	
</script>
<script language="javascript">
   document.onReady=function(){
	   var o=Report.getRequestPatameters(true);
	   $("value").value=o.value;
   }


   function goback(e){
	   window.location.href="xmlreport.jsp?"+Report.getRequestPatameters();
   }
</script>
<form  action="VersionBean/upload.do" method="post" enctype="multipart/form-data">
<input type="text" name="report_dir" id="value">
<table style="margin: 2px" cellspacing="0" cellpadding="0" border="0">
	<tr>
		<td nowrap>目录名称：</td>
		<td><input type="file" name="report_file" id="f1"></td>
	</tr>
	<tr>
		<td nowrap colspan="2">
		<button type="submit">确定</button>
		<button xtype="button" onclick="goback(event);">返回</button>
		</td>
	</tr>
</table>
</form>
