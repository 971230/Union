package com.ztesoft.crm.report.flex.admin.grid {
	import com.ztesoft.crm.report.flex.admin.dialog.PropertiesDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.events.MouseEvent;

	import mx.collections.ArrayCollection;
	import mx.containers.HBox;
	import mx.containers.VBox;
	import mx.controls.Button;
	import mx.controls.DataGrid;

	public class DataGridEx extends VBox {
		public function DataGridEx() {
			//TODO: implement function
			super();

		}

		private var cols:Array=[];
		private var fields:Array=[];
		private var dataGrid:DataGrid;

		public function addColumn(o:Object):void {
			if (ObjectUtil.isEmpty(o))
				return;
			if (ObjectUtil.isEmpty(o.width))
				o["width"]=1;

			if (o.hide != "form" && o.hide != "all") {
				this.fields.push(o);
			}
			if (o.hide != "all" && o.hide != "grid")
				cols.push(new DataGridColumnEx(o));
		}

		public function addField(o:Object):void {
			this.fields.push(o);
		}

		private var buttonDisplay:Boolean=true;

		public function setButtonDisplay(b:Boolean):void {
			this.buttonDisplay=b;
		}

		private var title:String="";

		public function setTitle(s:String):void {
			this.title=s;
		}

		private var data1:XMLNodeObject=null;

		public function load(d:XMLNodeObject):void {
			this.dataGrid.dataProvider=d.children();
			this.data1=d;
		}

		private var dataTagName:String;

		public function setDataTagName(s:String):void {
			this.dataTagName=s;
		}




		public function render():void {

			this.dataGrid=new DataGrid();
			this.addChild(this.dataGrid);
			this.dataGrid.percentHeight=100;
			this.dataGrid.percentWidth=100;

			if (this.buttonDisplay) {
				var buttons:HBox=new HBox();
				buttons.percentWidth=100;
				addChild(buttons);
				var b:Button=new Button();
				b.label="添加";
				b.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
					var o:Object={cols: 1, tagName: dataTagName, items: fields};
					var dlg:PropertiesDialog=new PropertiesDialog();
					if (data1 == null)
						return;
					dlg.setTitle("创建" + title);
					dlg.setCallback(function(d:Object):void {
						var arr:Array=ArrayCollection(dataGrid.dataProvider).source;
						arr.push(d);
						dataGrid.dataProvider=arr;
						dataGrid.selectedIndex=arr.length - 1;
					});
					dlg.open(dataGrid, o);
				});
				buttons.addChild(b);
				//
				b=new Button();
				b.label="修改";
				b.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {

					var item:XMLNodeObject=XMLNodeObject(dataGrid.selectedItem);
					if (item == null)
						return;
					var o:Object={cols: 1, tagName: dataTagName, items: fields, data: item};
					var dlg:PropertiesDialog=new PropertiesDialog();
					dlg.setTitle("修改" + title);
					dlg.setCallback(function(d:Object):void {

						item.setAttributes(XMLNodeObject(d).attributes());

						item.setContent(XMLNodeObject(d).getContent());
						var arr:Array=ArrayCollection(dataGrid.dataProvider).source;

						dataGrid.dataProvider=arr;
						dataGrid.selectedItem=item;
					});
					dlg.open(dataGrid, o);
				});
				buttons.addChild(b);
				//
				b=new Button();
				b.label="删除";
				b.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
					var arr:Array=ArrayCollection(dataGrid.dataProvider).source;
					if (dataGrid.selectedIndex < 0)
						return;
					if (dataGrid.selectedIndex >= arr.length) {
						return;
					}

					arr.splice(dataGrid.selectedIndex, 1);
					dataGrid.dataProvider=arr;
				});
				buttons.addChild(b);

			}
			this.dataGrid.columns=cols;
			cols=null;
		}
	}
}