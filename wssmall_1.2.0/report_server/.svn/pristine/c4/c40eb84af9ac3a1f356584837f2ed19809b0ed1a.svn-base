package com.ztesoft.crm.report.flex.admin.view.editor {
	import com.ztesoft.crm.report.flex.admin.dialog.ModalDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;
	import flash.events.MouseEvent;
	import mx.collections.ArrayCollection;
	import mx.containers.HBox;
	import mx.containers.VBox;
	import mx.controls.Button;
	import mx.controls.DataGrid;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.core.UIComponent;

	public class ModelJoinDialog extends ModalDialog {

		public function ModelJoinDialog() {
			//TODO: implement function
			super();
			setTitle("设定关联关系");
		}
		private var grid:DataGrid;
		private var from:DataGrid;
		private var join:DataGrid;
		private var buttons:VBox;

		override public function createContent():UIComponent {
			var v:VBox=new VBox();
			var h:HBox=new HBox();
			this.grid=new DataGrid();
			v.addChild(h);
			h.percentHeight=50;
			h.percentWidth=100;
			createJoinFrom(h);
			/****/
			var cols:Array=[];
			var dc:DataGridColumn=null;
			dc=new DataGridColumn("关联表字段(JOIN)");
			dc.dataField="columnLabel";
			dc.width=6;
			cols.push(dc);
			dc=new DataGridColumn("操作符");
			dc.dataField="operator";
			dc.width=1;
			cols.push(dc);
			dc=new DataGridColumn("被关联表字段(FROM)");
			dc.dataField="joinColumnLabel";
			dc.width=6;
			cols.push(dc);
			this.grid.columns=cols;
			this.grid.percentWidth=100;
			this.grid.percentHeight=50;
			this.grid.dataProvider=[];
			v.addChild(this.grid);
			return v;
		}

		private function createColumn(name:String, header:String):DataGridColumn {
			var dc:DataGridColumn=new DataGridColumn(header);
			dc.dataField=name;
			return dc;
		}

		private function createJoinFrom(h:HBox):void {
			this.join=new DataGrid();
			this.from=new DataGrid();
			this.buttons=new VBox();
			h.addChild(this.join);
			h.addChild(this.buttons);
			h.addChild(this.from);
			this.join.columns=[createColumn("text", "关联字段(JOIN)"), createColumn("dataType",
				"数据类型")];
			this.from.columns=[createColumn("text", "被关联字段(FROM)"), createColumn("dataType",
				"数据类型")];
			this.join.percentHeight=100;
			this.join.percentWidth=50;
			this.from.percentHeight=100;
			this.from.percentWidth=50;
			this.buttons.percentHeight=100;
			this.buttons.width=100;
			createAButtons();
		}

		private function createOperator(text:String, data:String):Button {
			var b:Button=new Button();
			b.percentWidth=90;
			b.label=text;
			b.data=data;
			b.addEventListener(MouseEvent.CLICK, buttonClick);
			return b;
		}

		private function createAButtons():void {
			this.buttons.setStyle("horizontalAlign", "center");
			this.buttons.setStyle("verticalAlign", "middle");
			this.buttons.addChild(createOperator("大于", ">"));
			this.buttons.addChild(createOperator("大于等于", ">="));
			this.buttons.addChild(createOperator("小于", "<"));
			this.buttons.addChild(createOperator("小于等于", "<="));
			this.buttons.addChild(createOperator("等于", "="));
			this.buttons.addChild(createOperator("不等于", "<>"));
		}

		private function buttonClick(e:MouseEvent):void {
			var arr:Array=ArrayCollection(this.grid.dataProvider).source;
			var n:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject("on");
			var f:Object=this.from.selectedItem;
			var j:Object=this.join.selectedItem;
			if (j == null || f == null)
				return ;
			n.setAttribute("column", j.name);
			n.setAttribute("columnLabel", j.text);
			n.setAttribute("joinColumn", f.name);
			n.setAttribute("joinColumnLabel", f.text);
			n.setAttribute("operator", Button(e.currentTarget).data as String);
			arr.push(n.attributes());
			joinData.add(n);
			this.grid.dataProvider=arr;
		}
		private var joinData:XMLNodeObject;

		override public function getData():Object {
			return joinData;
		}

		override public function load(data:Object):void {
			this.from.dataProvider=XMLNodeObject(data.from).getChildrenAttributes("column",
				false);
			this.join.dataProvider=XMLNodeObject(data.join).getChildrenAttributes("column",
				false);
			if (!ObjectUtil.isEmpty(data.on)) {
				this.grid.dataProvider=XMLNodeObject(data.on).getChildrenAttributes("on",
					false);
				joinData=XMLNodeObject(data.on);
			}
			else {
				joinData=XMLNodeObjectFatory.createXMLNodeObject("join");
				joinData.setAttribute("joinTable", XMLNodeObject(data.from).getAttribute("id"));
				joinData.setAttribute("onTable", XMLNodeObject(data.join).getAttribute("id"));
				joinData.setAttribute("type", "join");
			}
		}
	}
}