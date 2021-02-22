package com.ztesoft.crm.report.flex.admin.dialog {
	import com.ztesoft.crm.report.flex.admin.ReportApplication;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;
	import flash.events.MouseEvent;
	import mx.containers.TitleWindow;
	import mx.controls.Tree;
	import mx.core.UIComponent;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;

	public class ModelColumnDialog {

		public function ModelColumnDialog() {
		//TODO: implement function
		}

		public function open(parent:UIComponent, model:XMLNodeObject, title:String,
			callback:Function):Object {
			var win:TitleWindow=new TitleWindow();
			var form:Tree=new Tree();
			form.percentHeight=100;
			form.percentWidth=100;
			form.showRoot=true;
			form.labelFunction=function(node:Object):String {
				if (node == null)
					return "";
				var n:XMLNodeObject=XMLNodeObject(node);
				var d:String=n.getAttribute("dimension");
				if (n.getTagName() == "column") {
					if (ObjectUtil.isTrue(d))
						d="(维度)";
					else
						d="(指标)";
				}
				else
					d=""
				return n.getAttribute("text") + d;
			}
			form.dataDescriptor=new ModelColumnTreeDataDescriptor();
			form.dataProvider=(!model) ? [] : model.children();
			win.showCloseButton=true;
			win.title=title;
			win.addChild(form);
			win.width=480;
			win.alpha=1;
			win.height=320;
			win.addEventListener(CloseEvent.CLOSE, function(e:CloseEvent):void {
					PopUpManager.removePopUp(TitleWindow(e.currentTarget));
				});
			form.doubleClickEnabled=true;
			form.addEventListener(MouseEvent.DOUBLE_CLICK, function(e:MouseEvent):void {
					var tree:Tree=Tree(e.currentTarget);
					var win:TitleWindow=TitleWindow(tree.parent);
					var o:XMLNodeObject=XMLNodeObject(tree.selectedItem);
					if (!ObjectUtil.isEmpty(o) && !ObjectUtil.isEmpty(callback) &&
						o.getTagName() == "column") {
						callback(o);
					}
					PopUpManager.removePopUp(win);
				});
			PopUpManager.addPopUp(win, ReportApplication(parent.parentApplication),
				true);
			PopUpManager.centerPopUp(win);
			return null;
		}
	}
}