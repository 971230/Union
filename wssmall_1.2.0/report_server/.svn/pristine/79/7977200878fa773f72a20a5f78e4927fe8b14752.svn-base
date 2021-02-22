package com.ztesoft.crm.report.flex.admin {
	import com.ztesoft.crm.report.flex.URLConnection;
	import com.ztesoft.crm.report.flex.admin.dialog.FormDialog;
	import com.ztesoft.crm.report.flex.admin.elements.Report;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.events.ContextMenuEvent;
	import flash.events.MouseEvent;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuItem;

	import mx.containers.TabNavigator;
	import mx.controls.Label;
	import mx.controls.List;
	import mx.core.Application;
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	import mx.events.ListEvent;

	public class ReportApplication extends Application {

		public function ReportApplication() {
			//TODO: implement function
			super();
			var app:ReportApplication=this;
			this.addEventListener(FlexEvent.CREATION_COMPLETE, function(e:FlexEvent):void {
				/**初始化数据源**/
				init();
				URLConnection.doPostJSON(app.ajax + "/ConfigBean/listDataSources.do", null, function(o:Object):void {
					var arr:Array=o.data as Array;
					var len:int=arr.length;
					ReportConstanst.DATASOURCE=[];
					for (var i:int=0; i < len; i++) {
						var ds:Object=arr[i];
						ReportConstanst.DATASOURCE.push({label: ds.content.replace("<br>", "\n"), value: ds.name});
					}
					init2();
				});

			});
		}
		public var models:List;
		public var explorer:ReportExplorer;
		public var reportData:Report;
		public var modeleditor:ModelsEditor;
		public var title:Label;
		public var reportparameters:ReportParameters;
		public var reportview:ReportView;
		public var ajax:String;
		public var preViewPreffixURL:String;
		public var maintabs:TabNavigator;
		public var reportinterceptors:ReportInterceptor;
		public var dynamicCondition:ModelDynamicCondition;
		public var reportdimensionmap:ReportDimensionMap;

		public function ajaxURL(url:String, param:Object):String {
			var u:Array=[];
			u.push(ajax);
			if (url.indexOf("/") != 0)
				u.push("/");
			u.push(url);
			if (param != null) {
				if (url.indexOf("?") < 0)
					u.push("?");
				if (!ObjectUtil.isEmpty(param)) {
					for (var n:String in param) {
						if (ObjectUtil.isEmpty(param[n]))
							continue;
						var v:String="" + param[n];
						v=encodeURIComponent(v);
						u.push("&", n, "=", v);
					}
				}
			}
			return u.join("");
		}

		private function selectModel():void {
			var item:XMLNodeObject=XMLNodeObject(models.selectedItem);
			//MessageBox.alert("dd");
			if (ObjectUtil.isEmpty(item))
				return;
			modeleditor.load(item);
			dynamicCondition.setModel(item);
		}

		private function init():void {

			this.ajax=this.parameters.ajax;
			this.preViewPreffixURL=this.parameters.xml;
			this.explorer.setAjax(this.parameters.ajax);
			this.explorer.preffixURL=this.preViewPreffixURL;

			this.explorer.addEventListener(MouseEvent.DOUBLE_CLICK, function(e:MouseEvent):void {
				var tree:ReportExplorer=ReportExplorer(e.currentTarget);
				var item:Object=tree.selectedItem;
				var report:String=item.value;
				if (ObjectUtil.isTrue(item.leaf)) {
					load(report);
				}
			});
			this.models.addEventListener(ListEvent.CHANGE, function(e:ListEvent):void {
				selectModel();
			});

		}

		private function init2():void {
			initModels();
			reportdimensionmap.init();
			this.explorer.load();
		}
		public var reportFile:String;

		private function load(str:String):void {
			var url:String=this.parameters.xml + "/" + str;
			if (this.reportData != null && this.reportData.getName() == str)
				return;
			this.reportFile=str;
			this.reportData=new Report(str);
			var app:ReportApplication=this;

			new URLConnection(function(str1:String):void {
				XMLNodeObjectFatory.reset()
				if (str1 == "") {
					app.reportData.add(XMLNodeObjectFatory.createXMLNodeObject("parameters"));
					app.reportData.add(XMLNodeObjectFatory.createXMLNodeObject("models"));
					app.reportData.add(XMLNodeObjectFatory.createXMLNodeObject("view"));
					app.reportData.add(XMLNodeObjectFatory.createXMLNodeObject("interceptors"));
					app.reportData.add(new XMLNodeObject("dimensionMap"));
				} else
					app.reportData.parseXML(str1);
				if (app.reportData.getChild("interceptors") == null)
					app.reportData.add(new XMLNodeObject("interceptors"));
				if (app.reportData.getChild("dimensionMap") == null) {
					app.reportData.add(new XMLNodeObject("dimensionMap"));
				}
				app.modeleditor.clear();
				app.models.dataProvider=app.reportData.getChilds("model");
				app.models.selectedIndex=0;
				selectModel();
				app.title.text=ObjectUtil.nvl(app.reportData.getAttribute("title"), str) as String;
				//;
				app.reportparameters.load(app.reportData.getChild("parameters"));
				app.reportinterceptors.load(app.reportData.getChild("interceptors"));
				app.reportdimensionmap.load(app.reportData.getChild("dimensionMap"));
				//MessageBox.alert(app.reportview);
				if (app.reportview != null)
					app.reportview.load(app.reportData.getChild("view"));
			}).open(url, {replyEncoding: "utf-8"});
		}

		private function initModels():void {
			var menu:ContextMenu=new ContextMenu();
			menu.builtInItems.print=false;
			menu.hideBuiltInItems();
			menu.customItems=[];
			var item:ContextMenuItem=new ContextMenuItem("添加模型");
			menu.customItems.push(item);
			item.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, function(e:ContextMenuEvent):void {
				var ms:XMLNodeObject=reportData.getChild("models");
				new FormDialog().open(UIComponent(e.contextMenuOwner), {cols: 1, tagName: "model", items: [{label: "模型名称", name: "text"}, {label: "数据源", name: "dataSource", type: "combo", data: ReportConstanst.
									DATASOURCE}]}, "添加模型", function(d:XMLNodeObject):void {
					ms.add(d);
					models.dataProvider=ms.getChilds("model");
					models.selectedIndex=ms.getChilds("model").length - 1;
					selectModel();
				});
			});
			//
			item=new ContextMenuItem("删除模型");
			menu.customItems.push(item);
			item.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, function(e:ContextMenuEvent):void {
				var ms:XMLNodeObject=reportData.getChild("models");
				var n:XMLNodeObject=XMLNodeObject(models.selectedItem);
				if (n != null) {
					ms.removeChildById(n.getId());
					models.dataProvider=ms.getChilds("model");
				}
			});
			this.models.contextMenu=menu;
			this.models.labelFunction=function(d:Object):String {
				var x:XMLNodeObject=XMLNodeObject(d);
				var t:String=x.getAttribute("text");
				if (ObjectUtil.isEmpty(t)) {
					t="模型(ID:" + x.getAttribute("id") + ")";
					x.setAttribute("text", t);
				}
				return t;
			}
		}
	}
}