<link href="css/admin.css" type="text/css" rel="stylesheet">
</link>
<script src="script/Table.js" language="javascript">
	
</script>
<script src="script/Admin.js" language="javascript">
	
</script>
<script language="javascript">
var tpl=["<object ",
         "codebase=\"http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab\" ",
         "id=\"chart1\" classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" ",
         "style=\"width:100%;height:100%;margin:0px;\">",
         "<param value=\"transparent\" name=\"wmode\"></param>",
         "<param value=\"high\" name=\"quality\"></param>",
         "<param value=\"#FFFFFF\" name=\"bgcolor\"></param>",
         "<param value=\"always\" name=\"allowScriptAccess\"></param>",
         "<param value=\"{0}?ajax={1}&xml={3}&{2}\" name=\"movie\"></param>",
         "<embed width=\"100%\" wmode=\"transparent\" ",
         "type=\"application/x-shockwave-flash\" ",
         "src=\"{0}?ajax={1}&xml={3}&{2}\" pluginspage=\"http://www.adobe.com/go/getflashplayer\" ",
         "height=\"100%\" allowScriptAccess=\"always\" align=\"middle\" ",
         "play=\"true\" bgcolor=\"#FFFFFF\" loop=\"false\" name=\"chart1\" ",
         "quality=\"high\"></embed>","</object>"];

var args=[window.toURL("reportAdmin.swf"),window.toURL("/console"),Report.getRequestPatameters(),window.toURL("")];
Element.extend(document.body);
document.body.update(Report.format(tpl.join(""),args));
</script>