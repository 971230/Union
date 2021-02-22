package com.ztesoft.crm.report.flex.admin {
	import com.ztesoft.crm.report.flex.admin.dialog.FormDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.form.Field;
	import com.ztesoft.crm.report.flex.admin.form.FieldFactory;
	import flash.display.DisplayObject;
	import flash.events.ContextMenuEvent;
	import flash.events.MouseEvent;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuItem;
	import mx.containers.HBox;
	import mx.containers.VBox;
	import mx.controls.DataGrid;
	import mx.controls.Label;
	import mx.controls.dataGridClasses.DataGridColumn;

	public class ReportParameters extends VBox {

		public function ReportParameters() {
			//TODO: implement function
			super();
			var h:HBox=new HBox();
			addChild(h);
			h.percentWidth=100;
			h.height=30;
			var label:Label=new Label();
			label.text="缓存模式";
			h.addChild(label);
			cache=FieldFactory.createField("combo");
			cache.setName("cache");
			h.addChild(cache as DisplayObject);
			cache.setData(ReportConstanst.PARAMETER_CACHE);
			//
			this.table=new DataGrid();
			this.table.percentHeight=100;
			this.table.percentWidth=100;
			addChild(this.table);
			this.table.columns=getColumns();
			this.setContextMenu();
			this.doubleClickEnabled=true;
			this.addEventListener(MouseEvent.DOUBLE_CLICK, function(e:MouseEvent):void {
				var item:Object=table.selectedItem;
				if (item != null) {
					var p:XMLNodeObject=node.getChildById(item.id);
					modifyParameter(item.type, p);
				}
			});
		}
		private var cache:Field;
		private var node:XMLNodeObject;
		private var table:DataGrid;

		private function getColumns():Array {
			var arr:Array=[];
			var cm:DataGridColumn=null;
			/****/
			cm=new DataGridColumn("name");
			cm.headerText="参数名";
			cm.width=1;
			arr.push(cm);
			/****/
			cm=new DataGridColumn("type");
			cm.headerText="参数类型";
			cm.width=1;
			arr.push(cm);
			/****/
			cm=new DataGridColumn("content");
			cm.headerText="参数值";
			cm.width=4;
			arr.push(cm);
			return arr;
		}

		private function modifyParameter(type:String, p:XMLNodeObject):void {
			var items1:Array=[{label: "参数名", name: "name"}];
			switch (type) {
				case "sql":
					items1.push({label: "数据源", name: "dataSource", type: "combo",
							data: ReportConstanst.DATASOURCE});
					break;
				default:
					break;
			}
			items1.push({label: "参数值", name: "content", type: "textarea", height: 300});
			var o:Object=(p != null) ? p.getAttributes() : {};
			new FormDialog().open(this, {cols: 1, labelAlign: "top", items: items1,
					data: o}, "添加参数", function(d:XMLNodeObject):void {
				if (p == null) {
					p=new XMLNodeObject();
					node.add(p);
					p.setAttribute("type", type);
				}
				p.setTagName("parameter");
				p.setContent(d.getAttribute("content"))
				d.removeAttribute("content");
				p.setAttributes(d.attributes());
				table.dataProvider=node.getAttributesBy("parameter");
			});
		}

		private function setContextMenu():void {
			var cmenu:ContextMenu=new ContextMenu();
			cmenu.builtInItems.print=false;
			cmenu.hideBuiltInItems();
			cmenu.customItems=[];
			var item:ContextMenuItem=null;
			item=new ContextMenuItem("添加固定默认值");
			item.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, function(e:ContextMenuEvent):void {
				modifyParameter("", null);
			});
			cmenu.customItems.push(item);
			item=new ContextMenuItem("添加SQL查询默认值");
			item.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, function(e:ContextMenuEvent):void {
				modifyParameter("sql", null);
			});
			cmenu.customItems.push(item);
			item=new ContextMenuItem("添加静态编码默认值");
			item.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, function(e:ContextMenuEvent):void {
				modifyParameter("attrCode", null);
			});
			cmenu.customItems.push(item);
			item=new ContextMenuItem("添加SESSION默认值");
			item.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, function(e:ContextMenuEvent):void {
				modifyParameter("session", null);
			});
			cmenu.customItems.push(item);
			item=new ContextMenuItem("删除参数");
			item.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, function(e:ContextMenuEvent):void {
				var item:Object=table.selectedItem;
				if (item != null) {
					node.removeChildById(item.id);
				}
				table.dataProvider=node.getAttributesBy("parameter");
			});
			cmenu.customItems.push(item);
			this.table.contextMenu=cmenu;
		}

		public function load(n:XMLNodeObject):void {
			if (n == null)
				return;
			this.cache.setObserver(n);
			this.node=n;
			this.cache.setValue(n.getAttribute("cache"));
			this.table.dataProvider=this.node.getAttributesBy("parameter");
		}
	}
}