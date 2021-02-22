package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import mx.containers.Box;
	import mx.containers.VBox;
	import mx.controls.Image;

	public class RPTPanelSimpleChart extends RPTComponent {
		[Embed(source="assets/chart.jpg")]
		[Bindable]
		public var chartbackground:Class;

		public function RPTPanelSimpleChart() {
			//TODO: implement function
			super();
			super.horizontal();
			this.percentWidth=100;
			this.setStyle("paddingLeft", 5);
			this.setStyle("paddingBottom", 5);
		}
		private var panel:XMLNodeObject;
		private var series:RPTPanelSeriesBox;


		override public function load(n:XMLNodeObject):void {
			this.panel=n;
			this.axis.load(n.getChild("axis"));
			this.metric.load(n.getChild("metric"));
			this.series.load(n);

		}
		public var metric:RPTPanelMetric;
		public var axis:RPTPanelAxis;

		override protected function renderBody(body:Box):void {
			body.setStyle("horizontalGap", 5);
			createColumn1();
			createColumn3();
		}

		private function createColumn1():void {
			var column1:VBox=new VBox();
			column1.width=240;
			column1.percentHeight=100;
			column1.addChild(RPTComponentUtils.createLabel("默认度量(y轴)：", null));
			metric=new RPTPanelMetric();
			metric.render();
			column1.addChild(metric);
			var img:Image=new Image();
			img.width=240;
			img.height=140;
			img.source=chartbackground;
			column1.addChild(img);
			column1.addChild(RPTComponentUtils.createLabel("类别(x轴)：", null));
			axis=new RPTPanelAxis();
			axis.render();
			column1.addChild(axis);
			body.addChild(column1);
		}



		private function createColumn3():void {
			var column:VBox=new VBox();
			column.width=160;
			column.percentHeight=100;
			column.addChild(RPTComponentUtils.createLabel("序列：", null));
			series=new RPTPanelSeriesBox();
			column.addChild(series);
			series.percentWidth=100;
			series.height=222;
			body.addChild(column);
		}

		override public function notifyAppend(parent:XMLNodeObject, node:XMLNodeObject):void {
		}

		override public function change(node:XMLNodeObject):void {
		}

		override public function notifyDelete(parent:XMLNodeObject, node:XMLNodeObject):void {
			switch (node.getTagName()) {
				case "series":
					this.series.removeBodyChild(RPTComponent(node.getObserver()));
					break;

				case "metric":
					this.metric.load(null);
					break;
				case "axis":
					this.axis.load(null);
					break;
				default:
					break;
			}
		}

		override public function getPropertiesConfig():Object {
			var arr:Array=getData().getReport().getChilds("model");
			var len:int=arr.length;
			var d:Array=[];
			for (var i:int=0; i < len; i++) {
				var o:XMLNodeObject=XMLNodeObject(arr[i]);
				var l:String=o.getAttribute("text");
				if (ObjectUtil.isEmpty(l))
					l=o.getId();
				d.push({value: o.getId(), label: l});
			}
			return {cols: 1, items: [{label: "关联模型", name: "model", type: "combo", data: d}, {label: "标题", name: "title"}, {label: "横轴中文", name: "axisCn", type: "combo", data: ReportConstanst.TRUE_OR_FALSE},
						{label: "图表类型", name: "defaultChartType", type: "combo", data: ReportConstanst.CHART_TYPE}, {label: "标注位置", name: "labelPosition", type: "combo", data: ReportConstanst.CHART_LABEL_POSITION},
						{label: "图例格式", name: "legendFormat", type: "textarea", height: 60}, {label: "标注格式", name: "labelFormat", type: "textarea", height: 60}]};
		}
	}
}