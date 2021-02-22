package com.ztesoft.crm.report.flex.monitor {
	import com.adobe.serialization.json.JSON;
	import com.ztesoft.crm.report.flex.URLConnection;
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.dialog.SourceDialog;
	import com.ztesoft.crm.report.flex.admin.form.XMLNodeForm;
	import com.ztesoft.crm.report.flex.admin.grid.DataGridColumnEx;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.events.MouseEvent;
	import flash.xml.XMLDocument;
	import flash.xml.XMLNode;
	import flash.xml.XMLNodeType;

	import mx.collections.ArrayCollection;
	import mx.containers.HBox;
	import mx.containers.VBox;
	import mx.controls.Button;
	import mx.controls.DataGrid;
	import mx.controls.Tree;
	import mx.core.Application;
	import mx.core.ContextualClassFactory;
	import mx.events.FlexEvent;

	public class ReportMonitorApplication extends Application {
		public function ReportMonitorApplication() {
			//TODO: implement function
			super();
			addEventListener(FlexEvent.CREATION_COMPLETE, function(e:FlexEvent):void {
				init();
			});


		}

		public var systemproperties:VBox;
		public var datasource:VBox;
		public var reportmonitor:HBox;

		private var dsGrid:DataGrid;
		private var reportTree:Tree;
		private var sysVars:XMLNodeForm;
		private var monitorGrid:DataGrid;

		public var ajax:String;

		private function init():void {
			layoutSystemProperties();
			layoutDataSource();
			layoutReportMonitor();
			this.ajax=parameters.ajax;
			load();
		}

		private function load():void {
			URLConnection.doGet(this.parameters.xml + "/report_server.xml", null, function(s:String):void {
				var xml:XMLDocument=new XMLDocument();
				xml.ignoreWhite=true;
				xml.parseXML(s);
				var root:XMLNode=xml.firstChild;
				var childs:Array=root.childNodes;
				var len:int=childs.length;
				for (var i:int=0; i < len; i++) {
					var n:XMLNode=childs[i];
					if (n.nodeName == "datasource-map") {
						dsGrid.dataProvider=n.childNodes;
						continue;
					}

					if (n.nodeName == "e-variables") {
						loadSystemProperties(n);
						continue;
					}
				}

			});
			refreshMonitor();


		}

		private function clearAll(e:MouseEvent=null):void {
			URLConnection.doPostJSON(parameters.ajax + "/MonitorBean/clearReportLog.do", null, function(d:Object):void {
				if (!ObjectUtil.isTrue(d.error)) {
					refreshMonitor();
				}
			});
		}

		private function clearReportLog(e:MouseEvent=null):void {
			var item:Object=reportTree.selectedItem;
			if (item == null)
				return;
			if (!ObjectUtil.isTrue(item.leaf)) {
				return;
			}
			URLConnection.doPostJSON(parameters.ajax + "/MonitorBean/clearReportLog.do", {path: item.value}, function(d:Object):void {
				if (!ObjectUtil.isTrue(d.error)) {
					refreshMonitor();
				}
			});
		}

		private function refreshMonitor(e:MouseEvent=null):void {

			reportTree.dataProvider=[];
			URLConnection.doPostJSON(parameters.ajax + "/MonitorBean/getReportTree.do", null, function(d:Object):void {
				if (d.data != null) {
					reportTree.dataProvider=d.data;
					reportTree.expandItem(null, true, true, false, null);
				}
			});
			monitorGrid.dataProvider=[];
		}

		private function loadSystemProperties(n:XMLNode):void {
			var childs:Array=n.childNodes;
			var len:int=childs.length;
			var o:Object={};
			for (var i:int=0; i < len; i++) {
				var n:XMLNode=childs[i];
				o[n.attributes["name"]]=n.attributes["value"];
			}

			this.sysVars.load(o);
		}



		private function refreshReportLog(e:MouseEvent):void {
			var item:Object=reportTree.selectedItem;
			if (item == null)
				return;
			if (!ObjectUtil.isTrue(item.leaf)) {
				return;
			}

			URLConnection.doGet(parameters.ajax + "/MonitorBean/getMonitorHeap.do", {path: item.value}, function(d:String):void {
				var xml:XMLDocument=new XMLDocument();
				xml.ignoreWhite=true;
				xml.parseXML(d);
				monitorGrid.dataProvider=xml.firstChild.childNodes;
			});
		}


		private function layoutReportMonitor():void {

			var app:ReportMonitorApplication=this;
			reportTree=new Tree();
			reportTree.percentHeight=100;
			reportTree.width=500;
			reportTree.labelField="text";
			reportmonitor.addChild(reportTree);

			reportTree.addEventListener(MouseEvent.CLICK, refreshReportLog);
			//
			var div:VBox=new VBox();
			div.percentHeight=100;
			div.percentWidth=100;

			reportmonitor.addChild(div);

			monitorGrid=new DataGrid();
			monitorGrid.percentHeight=100;
			monitorGrid.percentWidth=100;
			monitorGrid.columns=[new DataGridColumnEx({label: "开始时间", name: "startTime"}), new DataGridColumnEx({label: "结束时间", name: "endTime"}), new DataGridColumnEx({label: "耗时(毫秒)", name: "elapsedTime"}),
				new DataGridColumnEx({label: "出错？", name: "error", data: ReportConstanst.TRUE_OR_FALSE})];
			monitorGrid.itemRenderer=new ContextualClassFactory(MonitorGridItemRenderer);
			//MessageBox.alert(monitorGrid.itemRenderer);
			div.addChild(monitorGrid);
//

			var buttons:HBox=new HBox();

			buttons.percentWidth=100;
			div.addChild(buttons);
			buttons.addChild(createButton("全部清空", clearAll));
			buttons.addChild(createButton("选中清空", clearReportLog));
			buttons.addChild(createButton("全部刷新", refreshMonitor));
			buttons.addChild(createButton("日志刷新", refreshReportLog));
			buttons.addChild(createButton("查看脚本", function(e:MouseEvent):void {
				var item:XMLNode=XMLNode(monitorGrid.selectedItem);
				if (item == null) {
					MessageBox.alert("请选择一条日志查看");
					return;
				}

				var dlg:SourceDialog=new SourceDialog();
				dlg.setTitle("脚本");
				if (item.firstChild.firstChild != null)
					dlg.open(app, item.firstChild.firstChild.nodeValue);
			}));
			buttons.addChild(createButton("查看执行日志", function(e:MouseEvent):void {
				var item:XMLNode=XMLNode(monitorGrid.selectedItem);
				if (item == null) {
					MessageBox.alert("请选择一条日志查看");
					return;
				}

				var dlg:SourceDialog=new SourceDialog();
				dlg.setTitle("执行日志");
				dlg.open(app, item.lastChild.firstChild.nodeValue);
			}));
			//;
			buttons.setStyle("horizontalAlign", "center");
		}

		private function layoutDataSource():void {
			var div:HBox=new HBox();
			div.percentHeight=100;
			div.percentWidth=100;

			dsGrid=new DataGrid();
			dsGrid.percentHeight=100;
			dsGrid.width=500;
			dsGrid.columns=[new DataGridColumnEx({label: "名称", name: "name"}), new DataGridColumnEx({label: "说明", name: "content"})];

			div.addChild(dsGrid);
			//
			var items:Array=[];
			items.push({label: "名称", name: "name"});

			items.push({label: "JndiName", name: "jndiName"});
			items.push({label: "URL", name: "url"});
			items.push({label: "用户名", name: "username"});

			items.push({label: "密码", name: "password"});
			items.push({label: "数据库类型", name: "type", type: "combo", data: ReportConstanst.DATABASE_TYPE});
			items.push({label: "描述", name: "content", type: "textarea", height: 200});
			var form:XMLNodeForm=new XMLNodeForm({cols: 1, items: items, labelWidth: 120});
			form.percentHeight=100;
			form.percentWidth=100;

			div.addChild(form);
			datasource.addChild(div);
//

			var buttons:HBox=new HBox();

			buttons.percentWidth=100;
			datasource.addChild(buttons);
			buttons.addChild(createButton("增加", function(e:MouseEvent):void {
				var item:XMLNode=new XMLNode(XMLNodeType.ELEMENT_NODE, "datasource");
				item.attributes=form.getValues().attributes();
				item.nodeName="datasource";
				var items:Array=ArrayCollection(dsGrid.dataProvider).source;
				items.push(item);
				dsGrid.dataProvider=items;
			}));
			buttons.addChild(createButton("修改", function(e:MouseEvent):void {
				var item:XMLNode=XMLNode(dsGrid.selectedItem);
				if (item == null) {
					return;
				}
				item.attributes=form.getValues().attributes();
				dsGrid.dataProvider=ArrayCollection(dsGrid.dataProvider).source;
			}));
			buttons.addChild(createButton("保存", function(e:MouseEvent):void {
				var data:Array=[];
				var items:Array=ArrayCollection(dsGrid.dataProvider).source;
				var len:int=items.length;
				for (var i:int=0; i < len; i++) {
					var o:Object=XMLNode(items[i]).attributes;
					o["tagName"]=XMLNode(items[i]).nodeName;
					if (!o.content)
						o["content"]=XMLNode(items[i]).firstChild.nodeValue;
					data.push(o);


				}
				URLConnection.doPostByXML(parameters.ajax + "/ConfigBean/saveDataSources.do", {"datasource": data}, function(d:String):void {
					var o:Object=JSON.decode(d, true);
					if (ObjectUtil.isEmpty(o.data)) {
						MessageBox.alert("保存数据源定义成功");
					} else {
						MessageBox.alert(o.data);
					}
				});
			}));
			buttons.addChild(createButton("应用", applyConfig));
			buttons.setStyle("horizontalAlign", "center");

			dsGrid.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
				var item:XMLNode=XMLNode(dsGrid.selectedItem);
				if (item == null) {
					return;
				}
				if (!item.attributes.content)
					item.attributes["content"]=item.firstChild.nodeValue;
				form.load(item.attributes);
			});
		}


		private function layoutSystemProperties():void {
			var items:Array=[];
			items.push({label: "保存存储目录", name: "reportSaveDir"});
			items.push({label: "是否缓存报表配置", name: "cacheReport", type: "radiobox", data: ReportConstanst.TRUE_OR_FALSE});
			items.push({label: "静态数据接口", name: "staticDataGetter", type: "textarea", height: 200});
			items.push({label: "是否开启监控", name: "enableMonitor", type: "radiobox", data: ReportConstanst.TRUE_OR_FALSE});

			items.push({label: "应用服务器编码", name: "systemEncoding", type: "combo", data: ReportConstanst.ENCODING_TYPE});
			items.push({label: "数据库编码", name: "databaseEncoding", type: "combo", data: ReportConstanst.ENCODING_TYPE});
			items.push({label: "导出Excel编码", name: "excelEncoding", type: "combo", data: ReportConstanst.ENCODING_TYPE});
			items.push({label: "报表配置文件编码", name: "configEncoding", type: "combo", data: ReportConstanst.ENCODING_TYPE});
			items.push({label: "默认主题", name: "defaultTheme", type: "combo", data: ReportConstanst.THEME_TYPE});

			sysVars=new XMLNodeForm({cols: 1, items: items, labelWidth: 120});
			sysVars.width=700;

			systemproperties.addChild(sysVars);

			//
			var buttons:HBox=new HBox();

			buttons.width=700;
			systemproperties.addChild(buttons);
			buttons.addChild(createButton("保存", function(e:MouseEvent):void {
				URLConnection.doPostJSON(parameters.ajax + "/ConfigBean/saveSystemVariables.do", sysVars.getValues().attributes(), function(o:Object):void {
					if (ObjectUtil.isEmpty(o.data)) {
						MessageBox.alert("保存系统参数定义成功");
					} else {
						MessageBox.alert(o.data);
					}
				});
			}));
			buttons.addChild(createButton("应用", applyConfig));
			buttons.setStyle("horizontalAlign", "center");
		}

		private function applyConfig(e:MouseEvent):void {
			URLConnection.doPostJSON(parameters.ajax + "/ConfigBean/apply.do", null, function(o:Object):void {
				if (ObjectUtil.isEmpty(o.data)) {
					MessageBox.alert("应用系统配置成功");
				} else {
					MessageBox.alert(o.data);
				}
			});
		}


		private function createButton(text:String, handler:Function):Button {
			var butt:Button=new Button();
			butt.label=text;
			if (handler != null)
				butt.addEventListener(MouseEvent.CLICK, handler);
			return butt;
		}

	}
}