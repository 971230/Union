
<style>
<!--
.table {
	width: 100%;
	height: 100%;
	border: 0px;
}

.table td {
	border-left: 1px solid #FFFFFF;
	border-top: 1px solid #FFFFFF;
	border-right: 1px solid #C0C0C0;
	border-bottom: 1px solid #C0C0C0;
	white-space: nowrap;
	padding: 2px;
}

.table table td {
	border: 0px;
}
-->
</style>
<script language="javascript">

	var DATAPROVIDER_OP = [ {
		value : ">",
		text : "大于"
	}, {
		value : ">=",
		text : "大于等于"
	}, {
		value : "<",
		text : "小于"
	}, {
		value : "<=",
		text : "小于等于"
	}, {
		value : "==",
		text : "等于"
	}, {
		value : "!=",
		text : "不等于"
	} ];

	var DATAPROVIDER_RELAT = [ {
		value : "&&",
		text : "并且"
	}, {
		value : "||",
		text : "或者"
	} ];

	var selectColor=false;
	function doSelectColor(e) {
		var el = (window.event) ? window.event.srcElement : e.target;
		var cp = new ColorPalette();
		selectColor=true;
		cp.show(el);
		cp.addListener( function(color) {
			var el1 = $("styleValue");
			el1.style.backgroundColor = color;
			el1.setAttribute("bgColorEx",color);
			el1 = null;
			selectColor=false;
		});
	}

	var r = 1;
	function addCond(d) {
		var tab = $("prompt_cond");
		var row = tab.insertRow(-1);
		r++;
		var o = (d) ? d : {
			operator : "",
			value : "",
			relatType : ""
		};

		row.insertCell(-1).innerHTML = Report
				.format(
						'<input size="12" xtype="combo" initValue="{1}" name="operator" id="operator{0}">',
						[ "" + r, o.operator ]);
		row.insertCell(-1).innerHTML = Report
				.format(
						'<input size="6" xtype="text" value="{1}" name="value" id="value{0}">',
						[ "" + r, o.value ]);
		row.insertCell(-1).innerHTML = Report
				.format(
						'<input style="width: 60px" initValue="{1}" xtype="combo" name="relat_type" id="relat_type{0}">',
						[ "" + r, o.relat_type ]);
		row.insertCell(-1).innerHTML = "<td><button onclick='addCond()'>添加</button><button onclick='delCond(event);'>删除</button></td>";

		var inps = Element.select(row, "INPUT").concat(
				Element.select(row, "BUTTON"));

		Report.initInput(inps);

		new ComboBox(inps[0].id).setDataProvider(DATAPROVIDER_OP);
		new ComboBox(inps[2].id).setDataProvider(DATAPROVIDER_RELAT);
		new ComboBox(inps[0].id).setValue(o.operator);
		new ComboBox(inps[2].id).setValue(o.relatType);
		Field.getField(inps[1].id).setValue(o.value);
		relayoutCond();

	}

	function delCond(e) {

		var el = Event.element(e);

		var row = el.parentNode.parentNode;
		var tab = $("prompt_cond");

		tab.deleteRow(row.rowIndex);
		relayoutCond();
	}

	function relayoutCond() {
		var tab = $("prompt_cond");
		var rows = tab.rows;
		var first = Element.select(rows[0], "BUTTON");
		rows[rows.length - 1].cells[2].firstChild.style.display = "none";
		if (rows.length == 1) {
			first[0].style.display = "";
			first[1].style.display = "none";
			return;
		}

		var last = Element.select(rows[rows.length - 1], "BUTTON");
		var pre = Element.select(rows[rows.length - 2], "BUTTON");

		pre[0].style.display = "none";
		pre[1].style.display = "";

		last[0].style.display = "";
		last[1].style.display = "";
		//

		rows[rows.length - 2].cells[2].firstChild.style.display = "";

	}

	function closePrompt() {

		window.returnValue = "123";

		window.close();

	}

	function getPrompt() {
		var ptab = $("prompt_config");
		var o = {
			columnName : "",
			columnText : "",
			styleName : "color",
			styleValue : "",
			matchers : []
		};
		var cell = ptab.rows[1].cells[2];
		var el = Element.select(cell, '[name="column"]')[0];
		var field = new ComboBox(el.id);
		o.columnName = field.getValue();
		if (Report.isEmpty(o.columnName)) {
			new Bubble().show(el, "没有选择对象进行设置！");
			return null;
		}
		o.columnText = field.getText();
		o.styleValue = $("styleValue").getAttribute("bgColorEx");
		
		if (Report.isEmpty(o.styleValue)) {
			new Bubble().show($("styleValue"), "没有选择颜色！");
			return null;
		}

		//读取比较条件
		var rows = $("prompt_cond").rows;
		for ( var i = 0; i < rows.length; i++) {
			var row = rows[i];
			var op = Element.select(row, '[name="operator"]')[0];
			var vl = Element.select(row, '[name="value"]')[0];
			var rt = Element.select(row, '[name="relat_type"]')[0];

			var oo = {
				operator : "",
				value : "",
				relatType : ""
			};

			oo.operator = new ComboBox(op.id).getValue();
			oo.relatType = new ComboBox(rt.id).getValue();
			oo.value = vl.value;

			if (Report.isEmpty(oo.operator)) {
				new Bubble().show(op, "没有选择操作符！");
				return null;
			}
			if (Report.isEmpty(oo.value)) {
				new Bubble().show(vl, "没有填值！");
				return null;
			}

			if (i < ((rows.length - 1)) && Report.isEmpty(oo.relatType)) {
				new Bubble().show(rt, "没有设定条件关系！");
				return null;
			}

			o.matchers.push(oo);
		}
		return o;
	}

	function getSelectedPrompt(){
		var tab = $("prompt_list");
		var inx = tab.selectIndex;
		if (typeof (inx) == "undefined" || inx == null)
			return null;
		return tab.rows[inx];
	}

	function addPrompt(d) {
		var o =(d)?d:getPrompt();
		if (!o)
			return;

		var row = $("prompt_list").insertRow(-1);

		var option = row.insertCell(-1);
		option.innerHTML = o.columnText;
		option["data"] = o;
		option.setAttribute("type","prompt_config");
		Element.observe(option, "click", function(evt) {
			var cell = Event.element(evt);
			
			selectPromptList(cell.parentNode.rowIndex);
		});
		setPrompt( {
			columnName : "",
			columnText : "",
			styleValue : "red",
			matchers : [ {
				operator : "",
				value : "",
				relatType : ""
			} ]
		});
	}

	function selectPromptList(index){
		var tab = $("prompt_list");
		if (typeof (tab.selectIndex) != "undefined"
				&& tab.selectIndex != null && tab.selectIndex >= 0) {
			modPrompt();
			Element.removeClassName(tab.rows[tab.selectIndex],
					"table-row-body-bg-over");
		}
		
		if(index<0){
			tab.selectIndex=-1;
			setPrompt({columnName:"",columnText:"",styleValue:"red",matchers:[null]});
			setPromptListSelected(false);
			return;
		}
		var cell=tab.rows[index].cells[0];
		setPrompt(cell.data);
		Element.addClassName(cell.parentNode, "table-row-body-bg-over");
		tab["selectIndex"] = index;
		setPromptListSelected(true);
		tab = null;
		cell = null;
	}

	function setPromptListSelected(b){
		$("btn_add_prompt").disabled=(b);
		$("btn_del_prompt").disabled=(!b);
		$("btn_mod_prompt").disabled=(!b);
	}

	function setPrompt(d) {

		new ComboBox("column").setFieldValue(d.columnName, d.columnText);
		$("styleValue").style.backgroundColor = d.styleValue;
		$("styleValue").setAttribute("bgColorEx",d.styleValue);
		var tab = $("prompt_cond");
		for ( var i = tab.rows.length - 1; i >= 0; i--) {
			tab.deleteRow(i);
		}

		for ( var n = 0; n < d.matchers.length; n++) {
			addCond(d.matchers[n]);
		}
	}

	function delPrompt() {
		var tab = $("prompt_list");
		var inx = tab.selectIndex;
		if (typeof (inx) == "undefined" || inx == null)
			return;
		var nextInx = (inx > 0) ? (inx - 1) : 0;
		if (inx >= 0)
			tab.deleteRow(inx);
		if (nextInx >= tab.rows.length) {
			tab.selectIndex = null;
			setPrompt( {
				columnName : "",
				columnText : "",
				styleValue : "red",
				matchers : [ {
					operator : "",
					value : "",
					relatType : ""
				} ]
			});
			return;
		} else {
			tab.selectIndex = nextInx;
			Element.addClassName(tab.rows[nextInx], "table-row-body-bg-over");
			setPrompt(tab.rows[nextInx].cells[0].data);
		}

	}


	function modPrompt(){
		var row=getSelectedPrompt();
		if(row){
			row.cells[0].data=getPrompt();
		}
	}

	function savePrompt(cb){
		var rows=$("prompt_list").rows;
		var arr=[];
		for(var i=0;i<rows.length;i++){
			arr.push(rows[i].cells[0].data);
		}
		

		var xml=new W3CXMLDocument({prompt:arr,report:window.dialogArguments.report});
		
		xml.send(toURL("save.ew"),cb);
	}


	function queryPrompt(){
		modPrompt();
	
		savePrompt(function(d){
			 window.parent.window.Report.reload();
             window.close();
			});
	}

	function initQueryPrompt(){
		Report.doGet(toURL("get.ew"),{report:window.dialogArguments.report},function(d){

            if(d && Object.isArray(d)){
                  for(var i=0;i<d.length;i++){
                      addPrompt(d[i]);
                  }
              }
		});
	}


	

	
</script>

<table class="table" id="prompt_config" cellpadding="0" cellspacing="1"
	border="0">
	<tr>
		<td nowrap align="center">已配置预警对象</td>
		<td colspan="2" align="center" style="width: 100%">预警设置</td>
	</tr>
	<tr>
		<td rowspan="5" style="height: 100%;vertical-align: top;"><div style="height: 100%;width:200px;border:1px groove #C0C0C0"><table id="prompt_list" style="width:100%;cursor:pointer"></div></table></td>
		<td>设置对象：</td>
		<td style="width: 100%"><input xtype="combo" name="column"
			id="column"></td>
	</tr>
	<tr>
		<td style="height: 90%; vertical-align: top; padding-top: 4px">设置条件：</td>
		<td style="vertical-align: top">
		<table cellpadding="0" cellspacing="0" id="prompt_cond">
			<tr>
				<td><input size="12" xtype="combo" name="operator"
					id="operator1"></td>
				<td><input size="6" xtype="text" name="value" id="value1"></td>
				<td><input style="width: 60px" xtype="combo" name="relat_type"
					id="relat_type1"></td>
				<td>
				<button onclick='addCond();'>添加</button>
				<button onclick='delCond(event);'>删除</button>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>设置颜色：</td>
		<td>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td>选择显示颜色：</td>
				<td><span id="styleValue" bgColorEx="red" 
					style="background-color: red; width: 30px; line-height: 20px; display: block; overflow-x: hidden"
					>&nbsp;</span></td>
				<td><img src="../images/color.png"
					onclick='doSelectColor(event);' style="cursor: pointer"></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td>预览信息：</td>
		<td><pre>&nbsp;</pre></td>
	</tr>
	<tr>
		<td colspan="2">
		<button id="btn_add_prompt" onclick='addPrompt();'>添加</button>
		<button id="btn_del_prompt" disabled onclick='delPrompt();'>删除</button>
		<button id="btn_mod_prompt" disabled onclick='modPrompt();'>修改</button>
		<button id="btn_save_prompt" onclick='queryPrompt();'>保存</button>

		</td>
	</tr>
</table>

<script language="javascript">
	window["onReady"] = function() {
		Report.onDocumentReady();
		new ComboBox("column").setDataProvider(window.dialogArguments.columns);
		new ComboBox("operator1").setDataProvider(DATAPROVIDER_OP);
		new ComboBox("relat_type1").setDataProvider(DATAPROVIDER_RELAT);
		relayoutCond();
		initQueryPrompt();

		Element.observe(document.body, "click", function(evt) {
              var el=Event.element(evt);
              
              if($("prompt_list").selectIndex<0) return;
              if(selectColor==true) return;
           
              if("SPAN,BUTTON,INPUT,IMAGE".indexOf(el.tagName)>=0) return;
              
              if(el.getAttribute("type")=="prompt_config") return;
              
              if(el.className && el.className.indexOf("rpt-")>=0) return;
              
              selectPromptList(-1);
            
		});
		
	}


	
</script>