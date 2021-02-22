<link href="css/admin.css" type="text/css" rel="stylesheet">
</link>
<script src="script/Table.js" language="javascript">
	
</script>
<script src="script/Admin.js" language="javascript">
	
</script>
<script language="javascript">
	var tree = null;
	var report = null;
	var reporthistory=null;
	var param=null;
	function treeNodeClick(data, node) {
		param=data;
		if (Report.isTrue(data.leaf)) {
			$("reportdir").style.display = "none";
			$("reportxml").style.display = "";
			reporthistory.reload(data);
		} else {
			$("reportdir").style.display = "";
			$("reportxml").style.display = "none";
			report.reload(data);
		}

	}

	document.onReady = function() {
		tree = new Tree( {
			url : window.toURL("/console/ReportDirectoryTreeBean/list.do"),
			folder : true,
			renderTo : "consoletree",
			check : false,
			line : true,
			reader : function(o) {
				return o.data;
			},
			nodeClick : treeNodeClick
		});
		report = new Table($("reportlist"), {
			url : toURL("/console/ReportDirectoryTreeBean/listReport.do"),
			id : "reportlist_table",
			headers : [ {
				text : "名称",
				name : "text"
			}, {
				text : "报表路径",
				name : "value",
				tpl:"<a href='editreport.jsp?{1}'>{0}</a>"
			} ],
			autoLoad : false
		});


		reporthistory=new Table($("reporthistory"), {
			url : toURL("/console/VersionBean/getUpdateHistory.do"),
			id : "reporthistory_table",
			headers : [ {
				text : "操作",
				name : "type"
			}, {
				text : "操作员",
				name : "operator"
			} ,{
				text : "时间",
				name : "date"
			} ]
		});

	
	}

	function newdir(e){
		var url="newdir.jsp?"+Object.toQueryString(param);
		window.location.href=url;
	}

	function newreport(e){
		var url="uploadreport.jsp?"+Object.toQueryString(param);
		window.location.href=url;
	}

	function viewreport(e){
		window.open(window.toURL("/"+param.value.replace(".xml","")));
	}

	function loadreport(e){
var o={report_path:"/"+param.value.replace(".xml","")};
		   Report.doGet(window.toURL("/console/VersionBean/update.do"),o,function(){
	              alert("报表更新成功");
			   });
	}

	function treeresize(e){
		var div=$("consoletree");
		var el=Event.element(e);
		if(el.clientHeight>0)
		div.style.height=el.clientHeight-2;
	}
</script>
<table style="height: 99%; margin: 2px" cellspacing="0" cellpadding="0"
	border="0">
	<tr>
		<td nowrap valign="top" onresize="treeresize(event);">
		<div id="consoletree"
			style="height:570; width: 300px; overflow-x: auto;overflow-y: auto;border: 1px groove #C0C0C0;"></div>
		</td>
		<td valign="top" style="padding-left: 5px;">
		<div id="reportdir" style="display: none"><a href="javascript:" onclick="newdir(event);">创建一个目录</a><br>
		<a href="javascript:" onclick="newreport(event);">上传一个新报表</a><br>
		<div id="reportlist"></div>
		<div id="newdirdiv" style="display:none">目录名称：<input xtype="text" name="dirname"
			id="f1"></div>
		</div>
		<div id="reportxml" style="display: none">
		<a href="javascript:" onclick="loadreport(event);">刷新报表</a><br>
		<a href="javascript:" onclick="viewreport(event);">预览报表</a>
		<div id="reporthistory">
		</div>
		</div>
		</td>
	</tr>
</table>
