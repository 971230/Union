package com.ztesoft.crm.report.flex.admin {
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectObserver;
	import com.ztesoft.crm.report.flex.admin.view.editor.ModelJoinDialog;
	import com.ztesoft.crm.report.flex.admin.view.editor.ModelTableEditorDialog;
	import com.ztesoft.crm.report.flex.admin.view.editor.ModelTableSQLEditorDialog;
	import com.ztesoft.crm.report.flex.admin.widgets.Table;
	import com.ztesoft.crm.report.flex.flow.Edge;
	import com.ztesoft.crm.report.flex.flow.FlowContainer;
	import com.ztesoft.crm.report.flex.flow.Node;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;
	import flash.events.ContextMenuEvent;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuItem;
	import mx.core.UIComponent;

	public class ModelsEditor extends FlowContainer implements UIControl, XMLNodeObjectObserver {

		private function getChildByPoint(x:int, y:int):Object {
			for (var i:int=this.numChildren - 1; i >= 0; i--) {
				var e:UIComponent=this.getChildAt(i)as UIComponent;
				var rect:Rectangle=new Rectangle(e.x, e.y, e.width, e.height);
				//MessageBox.alert(rect + "=" + x + "," + y);
				if (rect.contains(x, y)) {
					return e;
				}
			}
			return null;
		}

		public function ModelsEditor() {
			//TODO: implement function
			super();
			var app:ModelsEditor=this;
			setEdgeDoubleClick(function(edge:Edge, source:Node, target:Node):void {
					var dlg:ModelJoinDialog=new ModelJoinDialog();
					dlg.setCallback(function(n:Object):void {
							node.add(XMLNodeObject(n));
							edge.setData(n);
							edge.lineColor=0x41023d;
							edge.draw();
						});
					dlg.open(app, {on:edge.getData(), join:source.getData(), from:target.getData()});
					setDrawEdgeFlag(app, false);
				});
			setDeleteChildEvent(function(f:FlowContainer, o:Object):void {
					if (o == null)
						return ;
					if (!(o is XMLNodeObject))
						return ;
					ModelsEditor(f).node.removeChildById(XMLNodeObject(o).getId());
				});
			init();
		}

		private function setDrawEdgeFlag(flow:ModelsEditor, b:Boolean):void {
			if (b) {
				flow.startDrawEdge();
				ContextMenuItem(flow.contextMenu.customItems[2]).caption="取消定义表关系";
			}
			else {
				flow.stopDrawEdge();
				ContextMenuItem(flow.contextMenu.customItems[2]).caption="定义表关系";
			}
		}

		private function init():void {
			var menu:ContextMenu=new ContextMenu();
			menu.builtInItems.print=false;
			menu.builtInItems.play=false;
			menu.builtInItems.loop=false;
			menu.builtInItems.rewind=false;
			menu.hideBuiltInItems();
			var b:ContextMenuItem=null;
			b=new ContextMenuItem("选择表");
			menu.customItems.push(b);
			b.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, function(e:ContextMenuEvent):void {
					var editor:ModelsEditor=ModelsEditor(e.contextMenuOwner);
					var point:Point=editor.localToContent(new Point(editor.mouseX,
						editor.mouseY));
					//var tab:Table=new Table();
					var flow:ModelsEditor=ModelsEditor(e.contextMenuOwner);
					//editor.addChild(tab);
					var tab:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject();
					tab.setTagName("table");
					tab.setAttribute("x", "" + point.x);
					tab.setAttribute("y", "" + point.y);
					tab.parent=node;
					if (ObjectUtil.isEmpty(node)) {
						MessageBox.alert("没有选中任何模型！");
						return ;
					}
					if (node.isEmptyAttribute("dataSource")) {
						MessageBox.alert("选中的模型没有设定数据来源！");
						return ;
					}
					var dlg:ModelTableEditorDialog=new ModelTableEditorDialog();
					dlg.setCallback(function(o:Object):void {
							node.add(XMLNodeObject(tab));
						});
					dlg.open(flow, tab);
					//tab.move(point.x, point.y);
					setDrawEdgeFlag(flow, false);
				});
			b=new ContextMenuItem("从脚本创建");
			menu.customItems.push(b);
			b.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, function(e:ContextMenuEvent):void {
					var editor:ModelsEditor=ModelsEditor(e.contextMenuOwner);
					var point:Point=editor.localToContent(new Point(editor.mouseX,
						editor.mouseY));
					//var tab:Table=new Table();
					var flow:ModelsEditor=ModelsEditor(e.contextMenuOwner);
					//editor.addChild(tab);
					var tab:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject();
					tab.setTagName("table");
					tab.setAttribute("x", "" + point.x);
					tab.setAttribute("y", "" + point.y);
					tab.parent=node;
					var dlg:ModelTableSQLEditorDialog=new ModelTableSQLEditorDialog();
					dlg.setCallback(function(o:Object):void {
							node.add(XMLNodeObject(tab));
						});
					dlg.open(flow, tab);
					//tab.move(point.x, point.y);
					setDrawEdgeFlag(flow, false);
				});
			b=new ContextMenuItem("11");
			menu.customItems.push(b);
			this.stopDrawEdge();
			b.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, function(e:ContextMenuEvent):void {
					var ci:ContextMenuItem=ContextMenuItem(e.currentTarget);
					var flow:ModelsEditor=ModelsEditor(e.contextMenuOwner);
					setDrawEdgeFlag(flow, !flow.isDrawEdge());
				});
			b=new ContextMenuItem("属性", true);
			menu.customItems.push(b);
			this.stopDrawEdge();
			b.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, function(e:ContextMenuEvent):void {
					var ci:ContextMenuItem=ContextMenuItem(e.currentTarget);
					var flow:ModelsEditor=ModelsEditor(e.contextMenuOwner);
					setDrawEdgeFlag(flow, !flow.isDrawEdge());
				});
			this.contextMenu=menu;
			this.setDrawEdgeFlag(this, false);
		}

		/**
		 * 设置值
		 */
		public function setValue(value:Object):void {
		}

		public function getValue():Object {
			return null;
		}

		/**
		 * 设置当前控件需要的数据
		 */
		public function setData(data:Object):void {
		}
		private var node:XMLNodeObject;

		public function clear():void {
			this.removeAllChildren();
		}

		public function load(o:XMLNodeObject):void {
			var arr:Array=o.getChilds("table");
			var len:int=arr.length;
			this.node=o;
			var x:int=0;
			this.removeAllChildren();
			for (var i:int=0; i < len; i++) {
				var oo:XMLNodeObject=XMLNodeObject(arr[i]);
				var tab:Table=new Table();
				tab.setData(oo);
				tab.uid=oo.getId();
				addChild(tab);
				tab.setLocation(ObjectUtil.parseInt1(oo.getAttribute("x"), i * x +
					10), ObjectUtil.parseInt1(oo.getAttribute("y"), 10));
			}
			arr=o.getChilds("join");
			len=arr.length;
			for (var n:int=0; n < len; n++) {
				var oo1:XMLNodeObject=XMLNodeObject(arr[n]);
				var start:String=oo1.getAttribute("onTable");
				var target:String=oo1.getAttribute("joinTable");
				var ed:Edge=addEdge(getNode(start), getNode(target));
				if (ed != null) {
					ed.lineColor=0x41023d;
					ed.draw();
					ed.setData(oo1);
				}
			}
			this.node.setObserver(this);
		}

		public function notifyAppend(parent:XMLNodeObject, node:XMLNodeObject):void {
			if (node.getTagName() == "table") {
				var tab:Table=new Table();
				tab.setData(node);
				var x:int=parseInt(node.getAttribute("x"), 0);
				var y:int=parseInt(node.getAttribute("y"), 0);
				addChild(tab);
				tab.move(x, y);
			}
		}

		public function notifyDelete(parent:XMLNodeObject, node:XMLNodeObject):void {
		}

		public function change(node1:XMLNodeObject):void {
		}

		public function propertyChange(node1:XMLNodeObject, name:String):void {
		}
	}
}