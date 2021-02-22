package com.ztesoft.crm.report.flex.admin.widgets {
	import com.ztesoft.crm.report.flex.admin.UIControl;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectObserver;
	import com.ztesoft.crm.report.flex.admin.view.editor.ModelTableEditorDialog;
	import com.ztesoft.crm.report.flex.admin.view.editor.ModelTableSQLEditorDialog;
	import com.ztesoft.crm.report.flex.flow.Edge;
	import com.ztesoft.crm.report.flex.flow.FlowContainer;
	import com.ztesoft.crm.report.flex.flow.Node;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import mx.controls.DataGrid;
	import mx.controls.Label;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.events.FlexEvent;
	import mx.events.ResizeEvent;

	public class Table extends Node implements UIControl, XMLNodeObjectObserver {
		private const borderColor:String="0x0080c0";
		private const borderStyle:String="solid";
		private const borderThickness:String="1";

		public function Table() {
			//TODO: implement function
			super();
			this.tableName=new Label();
			this.tableName.percentWidth=100;
			this.tableName.text="我的垃圾表";
			this.tableName.setStyle("textAlign", "center");
			addChild(this.tableName);
			this.setStyle("backgroundColor", "0xd5eaff");
			this.setStyle("borderThickness", this.borderThickness);
			this.setStyle("borderStyle", this.borderStyle);
			this.setStyle("borderColor", this.borderColor);
			//
			this.table=new DataGrid();
			this.table.percentHeight=100;
			this.table.percentWidth=100;
			this.addChild(this.table);
			this.table.setStyle("borderSides", "top");
			this.table.setStyle("borderThickness", this.borderThickness);
			this.table.setStyle("borderStyle", this.borderStyle);
			this.table.setStyle("borderColor", this.borderColor);
			this.horizontalScrollPolicy="off";
			this.verticalScrollPolicy="off";
			/****/
			this.table.columns=[new DataGridColumn("text"), new DataGridColumn("name"),
				new DataGridColumn("dataType")];
			this.table.showHeaders=false;
			/****/
			var tab:Table=this;
			this.doubleClickEnabled=true;
			this.addEventListener(MouseEvent.DOUBLE_CLICK, function(e:MouseEvent):void {
					if (tab.node.isEmptyAttribute("name")) {
						new ModelTableSQLEditorDialog().open(tab, tab.node);
					}
					else
						new ModelTableEditorDialog().open(tab, tab.node);
				});
		}
		private var tableName:Label;
		private var offset1:Point;
		private var table:DataGrid;

		public function set offset(p:Point):void {
			this.offset1=p;
		}

		public function get offset():Point {
			return this.offset1;
		}

		override public function getLineColor():uint {
			return 0x0080c0;
		}

		/**
		 * 设置值
		 */
		public function setValue(value:Object):void {
		}

		public function getValue():Object {
			return null;
		}
		private var node:XMLNodeObject;

		override public function setLocation(x1:int, y1:int):void {
			if (this.node != null) {
				this.node.setAttribute("x", "" + x1);
				this.node.setAttribute("y", "" + y1);
			}
			super.setLocation(x1, y1);
		}

		/**
		 * 设置当前控件需要的数据
		 */
		public function setData(data:Object):void {
			this.node=XMLNodeObject(data);
			this.tableName.text=this.node.getAttribute("text");
			this.loadColumn(this.node);
			this.node.setObserver(this);
		}

		override public function getData():Object {
			return this.node;
		}

		private function loadColumn(node1:XMLNodeObject):void {
			var arr:Array=node1.getChilds("column");
			var rows:Array=[];
			var len:int=arr.length;
			for (var i:int=0; i < len; i++) {
				rows.push(XMLNodeObject(arr[i]).getAttributes());
			}
			this.table.dataProvider=rows;
		}

		override public function containPoint(x:int, y:int, c:FlowContainer):Boolean {
			return this.tableName.getBounds(c).contains(x, y);
		}

		public function notifyAppend(parent:XMLNodeObject, node:XMLNodeObject):void {
			this.loadColumn(parent);
		}

		public function notifyDelete(parent:XMLNodeObject, node:XMLNodeObject):void {
			this.loadColumn(parent);
		}

		public function change(node1:XMLNodeObject):void {
			this.tableName.text=node1.getAttribute("text");
			this.loadColumn(node1);
		}

		public function propertyChange(node1:XMLNodeObject, name:String):void {
			if (name == "text")
				this.tableName.text=node1.getAttribute("text");
		}
	}
}