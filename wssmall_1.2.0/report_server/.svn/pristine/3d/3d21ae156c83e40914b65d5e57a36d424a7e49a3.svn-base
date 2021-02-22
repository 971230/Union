package com.ztesoft.crm.report.flex.admin.view.editor {
	import com.adobe.serialization.json.JSON;
	import com.ztesoft.crm.report.flex.URLConnection;
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.dialog.ModalDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.admin.form.ComboBoxEx;
	import com.ztesoft.crm.report.flex.admin.form.XMLNodeForm;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.events.MouseEvent;

	import mx.containers.HBox;
	import mx.controls.Button;
	import mx.controls.DataGrid;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.core.UIComponent;

	public class ModelTableSQLEditorDialog extends ModalDialog {

		public function ModelTableSQLEditorDialog() {
			//TODO: implement function
			super();
			setTitle("编辑表信息");
			setSize(1024, 540);
			setCallback(function():void {
				if (tableNode.getObserver() != null) {
					tableNode.getObserver().change(tableNode);
				}
			});
		}

		override public function getData():Object {
			var tab:XMLNodeObject=form.getValues();
			var sql:String=tab.removeAttribute("sql");
			var sqlNode:XMLNodeObject=this.tableNode.getChild("sql");
			if (sqlNode == null) {
				sqlNode=new XMLNodeObject();
				sqlNode.setTagName("sql");
				this.tableNode.add(sqlNode);
			}
			sqlNode.setContent(sql);
			this.tableNode.setAttributes(tab.attributes());
			return this.tableNode;
		}
		private var form:XMLNodeForm=null;
		private var grid:DataGrid=null;

		override public function createContent():UIComponent {
			var v:HBox=new HBox();
			form=createForm();
			form.width=500;
			form.percentHeight=100;
			ControlUtils.setPadding(form, 0, 3, 9, 3);
			ControlUtils.setDefaultBorder(form, null);
			v.addChild(form);
			createGrid();
			v.addChild(this.grid);
			this.grid.percentWidth=100;
			this.grid.percentHeight=100;
			this.grid.editable=true;
			return v;
		}

		public function createForm():XMLNodeForm {
			return new XMLNodeForm({cols: 1, labelWidth: 60, labelAlign: "top", items: [{label: "表中文名", name: "text"}, {label: "脚本", name: "sql", height: 380, type: "textarea"}]});
		}
		private var dataSource:String;
		private var tableNode:XMLNodeObject;

		override public function load(data:Object):void {
			var n:XMLNodeObject=XMLNodeObject(data);
			var s:XMLNodeObject=n.getChild("sql");
			this.tableNode=n;
			this.dataSource=n.parent.getAttribute("dataSource");
			var o:Object=n.getAttributes();
			if (s != null)
				o["sql"]=s.getContent();
			form.load(o);
			this.loadColumn();
		}

		private function loadColumn():void {
			var arr:Array=this.tableNode.getChilds("column");
			var len:int=arr.length;
			var cos:Array=[];
			for (var i:int=0; i < len; i++) {
				cos.push(XMLNodeObject(arr[i]).attributes());
			}
			this.grid.dataProvider=cos;
		}

		override public function createButtons():Array {
			var b:Button=new Button();
			b.label="获取表字段";
			b.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
				var param:Object=form.getValues().getAttributes();
				var reg:RegExp=new RegExp("\\{\\w+\\}", "g");
				var a:Object=reg.exec(param.sql);
				while (a != null) {
					var group:String=String(a[0]);
					reg.lastIndex=a.index + group.length;
					var label:String=group.replace(/\{|\}/g, "");
					param[label]="-1";
					a=reg.exec(a.input);
				}
				param["dataSource"]=dataSource;
				new URLConnection(function(str:String):void {
					var d:Object=JSON.decode(str, true);
					var cols:Array=tableNode.getChildAttributes("column", "name");
					if (!ObjectUtil.isEmpty(d.data)) {
						var len:int=d.data.length;
						for (var i:int=0; i < len; i++) {
							var c:Object=d.data[i];
							if (cols.indexOf(c.name) < 0) {
								var c1:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject();
								c1.setTagName("column");
								c1.setAttributes(c);
								tableNode.add(c1);
							}
						}
						loadColumn();
					}
				}).doPostByXML(form.parentApplication.ajaxURL("/ReportConfigBean/getTableColumns.do", null), param);
			});
			/****/
			return [b];
		}

		private function getDimension():Array {
			var arr:Array=[];
			var dm:XMLNodeObject=getWindow().parentApplication.reportData.getChild("dimensionMap");

			var len:int=dm.children().length;
			for (var i:int=0; i < len; i++) {
				var o:XMLNodeObject=XMLNodeObject(dm.children()[i]);
				arr.push({value: o.getAttribute("name"), label: o.getAttribute("name")});
			}
			return arr;
		}

		private function createGrid():void {
			this.grid=new DataGrid();

			var columns:Array=[];
			var dc:DataGridColumn=null;
			dc=new DataGridColumn("字段名");
			dc.dataField="name";
			dc.editable=false;
			columns.push(dc);
			dc=new DataGridColumn("字段中文名");
			dc.dataField="text";
			dc.editable=true;
			columns.push(dc);
			dc=new DataGridColumn("字段类型");
			dc.dataField="dataType";
			dc.itemEditor=new CustomEditorFactory(ComboBoxEx, ReportConstanst.FIELD_DATATYPE);
			dc.editorDataField="value";
			columns.push(dc);
			dc=new DataGridColumn("汇总函数");
			dc.dataField="function";
			dc.itemEditor=new CustomEditorFactory(ComboBoxEx, ReportConstanst.SQL_FUNCTION);
			dc.editorDataField="value";
			columns.push(dc);
			dc=new DataGridColumn("维度表");
			dc.dataField="dimensionName";
			dc.itemEditor=new CustomEditorFactory(ComboBoxEx, getDimension());
			dc.editorDataField="value";
			columns.push(dc);
			dc=new DataGridColumn("是否维度?");
			dc.dataField="dimension";
			dc.itemEditor=new CustomEditorFactory(ComboBoxEx, ReportConstanst.TRUE_OR_FALSE);
			dc.editorDataField="value";
			columns.push(dc);
			dc=new DataGridColumn("排序类型");
			dc.dataField="orderBy";
			dc.itemEditor=new CustomEditorFactory(ComboBoxEx, ReportConstanst.SQL_ORDER);
			dc.editorDataField="value";
			columns.push(dc);
			this.grid.columns=columns;
		}
	}
}