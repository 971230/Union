package com.ztesoft.crm.report.flex {
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.events.ContextMenuEvent;
	import flash.events.MouseEvent;
	import flash.external.ExternalInterface;
	import flash.ui.ContextMenu;
	import flash.ui.ContextMenuItem;
	import flash.xml.XMLDocument;
	import flash.xml.XMLNode;
	import flash.xml.XMLNodeType;

	import mx.charts.AreaChart;
	import mx.charts.AxisRenderer;
	import mx.charts.CategoryAxis;
	import mx.charts.ChartItem;
	import mx.charts.ColumnChart;
	import mx.charts.GridLines;
	import mx.charts.HitData;
	import mx.charts.Legend;
	import mx.charts.LineChart;
	import mx.charts.LinearAxis;
	import mx.charts.chartClasses.CartesianChart;
	import mx.charts.chartClasses.ChartBase;
	import mx.charts.chartClasses.IAxis;
	import mx.charts.chartClasses.Series;
	import mx.charts.renderers.CircleItemRenderer;
	import mx.charts.series.AreaSeries;
	import mx.charts.series.ColumnSeries;
	import mx.charts.series.LineSeries;
	import mx.charts.styles.HaloDefaults;
	import mx.collections.ArrayCollection;
	import mx.containers.BoxDirection;
	import mx.containers.ControlBar;
	import mx.containers.HBox;
	import mx.containers.VBox;
	import mx.controls.Image;
	import mx.controls.Label;
	import mx.controls.Text;
	import mx.core.Application;
	import mx.core.ClassFactory;
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	import mx.graphics.Stroke;
	import mx.styles.CSSStyleDeclaration;

	public class ChartApplication extends Application {

		public function ChartApplication() {
			//TODO: implement function
			super();
			this.app=this;
			this.addEventListener(FlexEvent.CREATION_COMPLETE, function(e:FlexEvent):void {
				init();
				flash.external.ExternalInterface.addCallback("loadChart", loadChart);
				flash.external.ExternalInterface.addCallback("refresh", refresh);
				flash.external.ExternalInterface.addCallback("reload", reload);
				flash.external.ExternalInterface.addCallback("showChartProperties", showChartProperties);
				if (app.parameters.autoLoad == "true" || app.parameters.autoLoad == true) {
					app.loadChart(this.lastParams);
				}
			});
			this.verticalScrollPolicy="off";
			this.horizontalScrollPolicy="off";
		}
		private var app:ChartApplication;
		private var reportUrl:String;
		private var baseParameters:String;

		private function initChartStyle():void {
			var css:Array=["ColumnChart", "LineChart"];
			var chartCss:CSSStyleDeclaration=null;
			var len:int=css.length;
			if (ObjectUtil.isTrue(this.parameters.axisCn)) {
				for (var i:int=0; i < len; i++) {
					chartCss=HaloDefaults.createSelector(css[i]);
					chartCss.setStyle("fontFamily", null);
					chartCss.setStyle("font-family", null);
				}
			}

		}

		private function init():void {
			initChartStyle();
			try {
				this.reportUrl=decodeURI(this.parameters.url);
				var arr:Array=new Array();
				this.parameters.url;
				this.configObject={axisLeft: [], axisRight: []};
				if (!ObjectUtil.isEmpty(this.parameters.left)) {
					this.configObject["left"]=this.parameters.left;
				}
				if (!ObjectUtil.isEmpty(this.parameters.right)) {
					this.configObject["right"]=this.parameters.right;
				}
				if (!ObjectUtil.isEmpty(this.parameters.axisLeft)) {
					this.configObject.axisLeft=this.parameters.axisLeft.split(",");
					this.configObject.axisLeft["count"]=this.configObject.axisLeft.length;
				}
				if (!ObjectUtil.isEmpty(this.parameters.axisRight)) {
					this.configObject.axisRight=this.parameters.axisRight.split(",");
					this.configObject.axisRight["count"]=this.configObject.axisRight.length;
				}
				for (var n:String in this.parameters) {
					if (n == "url")
						continue;
					if (this.parameters[n] == "null")
						continue;
					if (ObjectUtil.isEmpty(this.parameters[n]))
						continue;
					arr.push(n + "=" + encodeURI(this.parameters[n]));
				}
				this.baseParameters=arr.join("&");
				//this.loadChart("");
				this.box=new VBox();
				this.box.percentWidth=100;
				this.box.percentHeight=100;
				this.box.setStyle("horizontalAlign", "center");
				this.box.setStyle("verticalGap", 0);
				this.box.setStyle("horizontalGap", 0);
				this.addChild(this.box);
				this.box.contextMenu=this.createToolbar();
			} catch (ex:Error) {
				alert(ex.message);
			}
		}

		private function alert(str:String):void {
			flash.external.ExternalInterface.call("alert", str);
		}
		private var config:XMLNode;
		private var type:String="line";
		private var configXML:String;

		private function doLayout():void {
			this.box.removeAllChildren();
			try {
				this.type=this.config.attributes.type;
				this.layoutChart();
			} catch (e:Error) {
				alert(e.message);
			}
		}
		private var box:VBox;

		public function refresh(args:String):void {
			this.init();
			this.loadChart(args);
		}
		private var configObject:Object;

		private function createChart(asers:Object):ChartBase {
			var chart:ChartBase=null;
			var chartType:String=this.getSeriesType(null);
			switch (chartType) {
				case "column":
					chart=new ColumnChart();
					break;
				case "area":
					chart=new AreaChart();
					break;
				default:
					chart=new LineChart();
					LineChart(chart).seriesFilters=[];
					break;
			}
			if (chart is CartesianChart) {
				chart.addEventListener(FlexEvent.CREATION_COMPLETE, function(e:FlexEvent):void {
					CategoryAxis(CartesianChart(e.currentTarget).horizontalAxis).padding=0.3;
				});
				var cc:CartesianChart=CartesianChart(chart);
				if (!ObjectUtil.isEmpty(asers.axisLeft)) {
					cc.verticalAxis=asers.axisLeft;
					//cc.verticalAxisRenderers=crea
					if (!ObjectUtil.isEmpty(this.config.attributes.leftAxisTitle)) {
						LinearAxis(cc.verticalAxis).title=this.config.attributes.leftAxisTitle;
					}
				}
				cc.verticalAxisRenderers=createAxisRenderer(0, cc.verticalAxis);
				if (!ObjectUtil.isEmpty(asers.axisRight)) {
					cc.secondVerticalAxis=asers.axisRight;
					if (!ObjectUtil.isEmpty(this.config.attributes.leftAxisTitle)) {
						LinearAxis(cc.secondVerticalAxis).title=this.config.attributes.leftAxisTitle;
					}
					cc.secondVerticalAxisRenderer=AxisRenderer(createAxisRenderer(0, cc.secondVerticalAxis)[0]);
				}
				if (cc.verticalAxis != null)
					LinearAxis(cc.verticalAxis).baseAtZero=false;
				if (cc.secondVerticalAxis != null)
					LinearAxis(cc.secondVerticalAxis).baseAtZero=false;
				cc.horizontalAxis=asers.axis;
				cc.horizontalAxisRenderers=createAxisRenderer((!ObjectUtil.isTrue(this.parameters.axisCn)) ? 45 : 0, cc.horizontalAxis);
				//
				var gl:GridLines=new GridLines();
				gl.setStyle("horizontalStroke", new Stroke(0xCCCCCC, 1, 0.6));
				gl.setStyle("horizontalFill", 0xFFFFFF);
				gl.setStyle("horizontalAlternateFill", 0xF0F0F0);
				cc.backgroundElements=[gl];
				cc.setStyle("backgroundColor", 0xCCCCCC);
			}
			chart.dataTipFunction=function(value:HitData):String {
				var o:Object=value.item;
				var item:ChartItem=value.chartItem;
				var field:String=item.element["yField"];
				var tpl:Array=[item.element["displayName"], "\n", axisLabel, ":", o[axisField], "\n值:", o[field]];
				return tpl.join("");
			}
			chart.showDataTips=true;
			chart.cacheAsBitmap=true;
			chart.series=asers.seriers;
			return chart;
		}

		private function createAxisRenderer(labelRotation:int, axis:IAxis):Array {
			var ar:AxisRenderer=new AxisRenderer();
			ar.axis=axis;
			ar.setStyle("tickLength", 1);
			if (labelRotation != 0)
				ar.setStyle("labelRotation", 45);
			ar.setStyle("axisTitleStyleName", "axisTitleStyle");
			return [ar];
		}

		private function createAxisSeries():Object {
			var o:Object={seriers: []};
			var nodes:Array=this.config.childNodes;
			var len:int=nodes.length;
			for (var i:int=0; i < len; i++) {
				var n:XMLNode=nodes[i];
				var attrs:Object=n.attributes;
				if (n.nodeName == "series") {
					if (attrs.region == "right") {
						if (ObjectUtil.isEmpty(o.axisRight))
							o["axisRight"]=new LinearAxis();
						o.seriers.push(createSeries(getSeriesType(attrs.region), attrs, o.axisRight, o.seriers.length));
					} else {
						if (ObjectUtil.isEmpty(o.axisLeft)) {
							o["axisLeft"]=new LinearAxis();
						}
						o.seriers.push(createSeries(getSeriesType(attrs.region), attrs, o.axisLeft, o.seriers.length));
					}
					continue;
				}
				if (n.nodeName == "axis" && ObjectUtil.isEmpty(o.axis)) {
					var ca1:CategoryAxis=new CategoryAxis();
					ca1.categoryField=attrs.field;
					ca1.padding=0.2;
					this.axisLabel=attrs.displayName;
					this.axisField=attrs.field;
					if (!ObjectUtil.isEmpty(attrs.title))
						ca1.title=attrs.title;
					this.fields.push(attrs.field);
					o["axis"]=ca1;
				}
			}
			return o;
		}

		private function getSeriesType(region:String):String {
			if (region == "right") {
				return this.configObject[region];
			}
			if (this.configObject && (this.configObject.axisLeft.count < 1) && (this.configObject.axisRight.count < 1)) {
				return this.type;
			}
			return (this.configObject && this.configObject["left"]) ? this.configObject["left"] : this.type;
		}
		private var axisLabel:String;
		private var axisField:String;

		private function createSeries(type:String, attrs:Object, la:LinearAxis, i:int):Object {
			var s:Series=null;
			var tpl:Array=[(attrs.region == "right") ? "--右轴(" : "--左轴(", "", ")"];
			switch (type) {
				case "column":
					var cs:ColumnSeries=new ColumnSeries();
					cs.yField=attrs.field;
					tpl[1]=attrs.displayName
					cs.displayName=tpl.join("");
					if (!ObjectUtil.isEmpty(la))
						cs.verticalAxis=la;
					s=cs;
					break;
				case "area":
					var as1:AreaSeries=new AreaSeries();
					as1.yField=attrs.field;
					tpl[1]=attrs.displayName
					as1.displayName=tpl.join("");
					if (!ObjectUtil.isEmpty(la))
						as1.verticalAxis=la;
					s=as1;
					break;
				default:
					var ls:LineSeries=new LineSeries();
					if (!ObjectUtil.isEmpty(la))
						ls.verticalAxis=la;
					ls.yField=attrs.field;
					ls.setStyle("form", "curve");
					ls.setStyle("radius", 5);
					ls.setStyle("adjustedRadius", 2);
					ls.setStyle("itemRenderer", new ClassFactory(CircleItemRenderer));
					ls.setStyle("lineStroke", new Stroke(ChartDefaults.defaultColors[i], 1));
					tpl[1]=attrs.displayName
					ls.displayName=tpl.join("");
					s=ls;
					break;
			}
			this.fields.push(attrs.field);
			return s;
		}

		private function toArray(nodes:Array, nms:Array):ArrayCollection {
			var arr:ArrayCollection=new ArrayCollection();
			for (var i:int=0; i < nodes.length; i++) {
				var n:XMLNode=nodes[i] as XMLNode;
				if (n.nodeType != XMLNodeType.ELEMENT_NODE)
					continue;
				var att:Object=(nodes[i] as XMLNode).attributes;
				var o:*={};
				for (var j:int=0; j < nms.length; j++) {
					o[nms[j]]=att[nms[j]] as String;
				}
				arr.addItem(o);
			}
			return arr;
		}

		private function setFont(ui:UIComponent):void {
			ui.setStyle("fontSize", "12");
			ui.setStyle("fontFamily", "宋体,Times New Roman");
		}
		private var chartTitle:Text;

		private function layoutChart():void {
			var p:HBox=new HBox();
			p.percentWidth=98;
			p.percentHeight=100;
			p.setStyle("horizontalGap", 0);
			p.setStyle("verticalGap", 0);
			chr=createChart(createAxisSeries());
			chr.percentHeight=100;
			chr.percentWidth=100;
			chr.contextMenu=this.createToolbar();
			//chartDiv.verticalScrollPolicy="auto";
			if (!ObjectUtil.isEmpty(this.config.attributes.title)) {
				this.chartTitle=new Text();
				this.box.addChild(this.chartTitle);
				this.chartTitle.text=this.config.attributes.title;
				this.chartTitle.setStyle("fontAntiAliasType", "advanced");
				this.chartTitle.setStyle("fontFamily", "Verdana");
				this.chartTitle.setStyle("fontSize", "20");
				this.chartTitle.setStyle("fontWeight", "bold");
			}
			p.addChild(chr);
			//
			var vBox:VBox=new VBox();
			var cbar:ControlBar=createToolbar1();
			if (cbar != null) {
				ControlUtils.setPadding(cbar, 2, 2, 2, 2);
				vBox.addChild(cbar);
			}
			var leng:Legend=new Legend();
			leng.dataProvider=chr;
			//leng.legendItemClass=BigFontLegendItem;
			leng.setStyle("itemRenderer", null);
			leng.contextMenu=this.createToolbar();
			leng.setStyle("horizontalGap", 0);
			leng.setStyle("verticalGap", 1);
			ControlUtils.setPadding(leng, 2, 2, 2, 2);
			vBox.addChild(leng);
			p.addChild(vBox);
			vBox.percentHeight=100;
			this.box.addChild(p);
			this.verticalScrollPolicy="auto";
			//this.caption=new Label();
			//this.caption.text="测试";
			//this.box.addChild(this.caption);
		}
		private var caption:Label;
		private var zoomed:Boolean=false;

		private function showChartProperties():void {
			var o:Object=flash.external.ExternalInterface.call("Report.panel.showChartProperties", {xml: app.config.toString(), data: app.configObject});
			if (ObjectUtil.isEmpty(o))
				return;
			if (app.configObject == o)
				return;
			app.configObject=o;
			app.loadChart(app.lastParams);
		}
		[Embed(source="assets/set1.png")]
		[Bindable]
		public var icon1:Class;
		[Embed(source="assets/pie.png")]
		[Bindable]
		public var icon2:Class;
		[Embed(source="assets/zoom.png")]
		[Bindable]
		public var icon3:Class;

		private function createToolbar1():ControlBar {
			if (this.parameters.contextMenu == "false" || this.parameters.contextMenu == false)
				return null;
			var bar:ControlBar=new ControlBar();
			bar.direction=BoxDirection.HORIZONTAL;
			var b:Image=null;
			b=new Image();
			b.toolTip="图例配置";
			b.source=icon1;
			bar.addChild(b);
			b.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
				showChartProperties();
			});
			b=new Image();
			b.toolTip="报表放大";
			b.source=icon3;
			bar.addChild(b);
			b.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
				flash.external.ExternalInterface.call("Report.panel.zoomOut", app.url, app.baseParameters + "&" + app.lastParams + "&" + app.getConfigParameters());
			});
			b=new Image();
			b.toolTip="占比查看";
			b.source=icon2;
			bar.addChild(b);
			b.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
				flash.external.ExternalInterface.call("Report.panel.showPie", app.config.toString(), app.dataXML.toString());
			});
			//alert("" + menu.customItems.length);
			return bar;
		}

		private function createToolbar():ContextMenu {
			if (this.parameters.contextMenu == "false" || this.parameters.contextMenu == false)
				return null;
			var menu:ContextMenu=new ContextMenu();
			menu.builtInItems.print=false;
			menu.hideBuiltInItems();
			var b:ContextMenuItem=null;
			b=new ContextMenuItem("图例配置");
			menu.customItems.push(b);
			b.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, function(e:ContextMenuEvent):void {
				showChartProperties();
			});
			b=new ContextMenuItem("报表放大");
			menu.customItems.push(b);
			b.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, function(e:ContextMenuEvent):void {
				flash.external.ExternalInterface.call("Report.panel.zoomOut", app.url, app.baseParameters + "&" + app.lastParams + "&" + app.getConfigParameters());
			});
			b=new ContextMenuItem("占比查看");
			menu.customItems.push(b);
			b.addEventListener(ContextMenuEvent.MENU_ITEM_SELECT, function(e:ContextMenuEvent):void {
				flash.external.ExternalInterface.call("Report.panel.showPie", app.config.toString(), app.dataXML.toString());
			});
			//alert("" + menu.customItems.length);
			return menu;
		}
		private var chr:ChartBase;
		private var fields:Array=new Array();

		private function urlEncode(o:Object):String {
			var arr:Array=new Array();
			for (var n:String in o) {
				arr.push(n)
				arr.push("=");
				arr.push(o[n]);
			}
			return arr.join("\n");
		}
		private var seriers:Object={};

		private function toArray1(data:XMLNode):ArrayCollection {
			this.dataXML=data;
			var arr:ArrayCollection=toArray(data.childNodes, fields);
			return arr;
		}
		private var lastParams:String;
		private var dataXML:XMLNode;

		public function reload():void {
			this.loadChart(this.lastParams);
		}

		private function getConfigParameters():String {
			var arr=[];
			if (this.configObject) {
				if (this.configObject.left) {
					arr.push("left=" + this.configObject.left);
				}
				if (this.configObject.right) {
					arr.push("right=" + this.configObject.right);
				}
				if (this.configObject.axisLeft) {
					var al:Array=[];
					for (var i:int=0; i < this.configObject.axisLeft.count; i++) {
						al.push(this.configObject.axisLeft[i]);
					}
					arr.push("axisLeft=" + al.join(","));
				}
				if (this.configObject.axisRight) {
					var ar:Array=[];
					for (var n:int=0; n < this.configObject.axisRight.count; n++) {
						ar.push(this.configObject.axisRight[n]);
					}
					arr.push("axisRight=" + ar.join(","));
				}
			}
			return arr.join("&");
		}

		public function loadChart(args:String):void {
			this.lastParams=args;
			if (args == null) {
				args="";
			}
			args=encodeURI(args);
			args=args + "&" + this.baseParameters;
			if (this.configObject) {
				if (this.configObject.axisLeft) {
					for (var i:int=0; i < this.configObject.axisLeft.count; i++) {
						args=args + "&prepareSeriesId=" + this.configObject.axisLeft[i];
					}
				}
				if (this.configObject.axisRight) {
					for (var n:int=0; n < this.configObject.axisRight.count; n++) {
						args=args + "&prepareSeriesId=" + this.configObject.axisRight[n];
					}
				}
			}
			//flash.external.ExternalInterface.call("window.open",(this.reportUrl + "?" + args));
			var report:ChartApplication=this;
			new URLConnection(function(str:String):void {
				var xml:XMLDocument=new XMLDocument();
				xml.ignoreWhite=true;
				xml.parseXML(str);
				report.config=xml.firstChild.firstChild;
				report.doLayout();
				var arr:ArrayCollection=toArray1(xml.firstChild.lastChild);
				chr.dataProvider=arr;
				if (chr is CartesianChart) {
					CategoryAxis(CartesianChart(chr).horizontalAxis).dataProvider=arr;
				}

				flash.external.ExternalInterface.call("Report.mask.hide", parameters.reportPanelId);
			}).open(this.reportUrl + "?" + args, null);
		}
	}
}