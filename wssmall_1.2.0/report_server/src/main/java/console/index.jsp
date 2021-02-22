<link href="css/admin.css" type="text/css" rel="stylesheet">
</link>
<script src="script/Table.js" language="javascript">
	
</script>
<script src="script/Admin.js" language="javascript">
	
</script>

<script language="javascript">
	function show(event) {
		var key = "lastPanel";
		var el = Event.element(event);
		if (el.tagName == "SPAN")
			el = el.parentNode;
		var id = el.getAttribute("uid");
		if (document[key]) {
			$(document[key]).style.display = "none";
		} else {
			$("system_panel").style.display = "none";
		}
		$(id).style.display = "";
		document[key] = id;
	}

	document.onReady = function() {
		var lis = document.getElementsByTagName("LI");
		for ( var i = 0; i < lis.length; i++) {
			var e = lis[i];
			Element.extend(e);
			e.observe("click", show);
		}

	}
</script>
<table width="100%" height="100%" cellspacing="0" cellpadding="0"
	border="0">
	<tr>
		<td nowrap valign="bottom" align="left" style="height: 35px">
		<ul>
			<li uid="system_panel"><span>系统</span></li>
			<li uid="report_panel"><span>报表</span></li>
		</ul>
		</td>
	</tr>
	<tr id="system_panel">
		<td height="100%"><iframe frameborder="0" width="100%"
			height="100%" src="system.jsp"></iframe></td>
	</tr>
	<tr style="display: none" id="datasource_panel">
		<td height="100%"></td>
	</tr>
	<tr id="report_panel" style="display: none">
		<td height="100%"><iframe frameborder="0" width="100%"
			height="100%" src="xmlreport.jsp"></iframe></td>
	</tr>
</table>
