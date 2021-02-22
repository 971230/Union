package com.ztesoft.crm.report.flex.admin.dialog {
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.admin.grid.DataGridColumnEx;
	import com.ztesoft.crm.report.flex.message.MessageBox;

	import flash.events.MouseEvent;

	import mx.collections.ArrayCollection;
	import mx.containers.HDividedBox;
	import mx.controls.Button;
	import mx.controls.DataGrid;
	import mx.controls.Tree;
	import mx.core.UIComponent;
	import mx.events.FlexEvent;


	public class ModalDynamicConditionDialog extends ModalDialog {
		public function ModalDynamicConditionDialog() {
			//TODO: implement function
			super();
			setTitle("动态条件编辑--------(结束值只有在操作符为[在...之间]时有效)");
		}

		private var tree:Tree;
		private var grid:DataGrid;

		override public function createContent():UIComponent {
			var div:HDividedBox=new HDividedBox();


			this.tree=new Tree();
			div.addChild(tree);

			this.tree.percentHeight=100;
			this.tree.width=200;

			grid=new DataGrid();
			div.addChild(this.grid);
			this.grid.percentHeight=100;
			this.grid.percentWidth=100;
			this.grid.dataProvider=[];
			this.grid.editable=true;

			this.tree.labelFunction=function(o:Object):String {
				return o.text;
			}

			this.tree.addEventListener(FlexEvent.CREATION_COMPLETE, function(e:FlexEvent):void {
				if (ArrayCollection(tree.dataProvider).source.length < 1)
					return;
				var o:Object=ArrayCollection(tree.dataProvider).getItemAt(0);
				tree.expandItem(o, true, true, false, null);

			});

			//
			var arr:Array=[];
			arr.push(new DataGridColumnEx({name: "tableLabel", label: "表名", width: 1}));
			arr.push(new DataGridColumnEx({name: "columnLabel", label: "字段名", width: 1}));
			arr.push(new DataGridColumnEx({name: "operator", label: "操作符", width: 2, editable: true, data: ReportConstanst.OPERATOR}));
			arr.push(new DataGridColumnEx({name: "startValue", label: "开始值", width: 2, editable: true}));
			arr.push(new DataGridColumnEx({name: "endValue", label: "结束值", width: 2, editable: true}));

			this.grid.columns=arr;



			return div;

		}


		override public function load(data:Object):void {
			if (data.conditions != null) {
				conditions=XMLNodeObject(data.conditions);
			} else {
				conditions=XMLNodeObjectFatory.createXMLNodeObject("conditions");
			}
			this.tree.dataProvider=data.tree;
			this.grid.dataProvider=conditions.getChildrenAttributes("condition", true);
		}

		private var conditions:XMLNodeObject;

		override public function getData():Object {
			return conditions;
		}

		override public function createButtons():Array {
			var arr:Array=[];
			var b:Button=null;
			//
			b=new Button();
			b.label="添加";
			b.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
				var item:Object=tree.selectedItem;
				if (item.table == true) {
					MessageBox.alert("请选择字段添加!");
					return;
				}
				var parent:Object=tree.getParentItem(item);

				var condition:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject("condition");
				condition.setAttribute("tableId", parent.id);
				condition.setAttribute("tableLabel", parent.text);
				condition.setAttribute("columnCode", item.name);
				condition.setAttribute("columnLabel", item.text);
				conditions.add(condition);
				grid.dataProvider=conditions.getChildrenAttributes("condition", true);

			});
			arr.push(b);
			//
			b=new Button();
			b.label="删除";
			b.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
				var item:Object=grid.selectedItem;
				if (item == null) {
					MessageBox.alert("请选择字段删除!");
					return;
				}

				conditions.removeChildById(item.id);
				grid.dataProvider=conditions.getChildrenAttributes("condition", true);
			});
			arr.push(b);

			return arr;
		}
	}
}