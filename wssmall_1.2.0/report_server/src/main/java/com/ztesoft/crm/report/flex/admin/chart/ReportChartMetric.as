package com.ztesoft.crm.report.flex.admin.chart {
	import com.ztesoft.crm.report.flex.admin.dialog.ModelColumnDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;
	import flash.events.MouseEvent;
	import mx.controls.Label;

	public class ReportChartMetric extends ReportPanelField {

		public function ReportChartMetric() {
			//TODO: implement function
			super();
			this.l=new Label();
			this.doubleClickEnabled=true;
			this.addChild(l);
			this.addEventListener(MouseEvent.DOUBLE_CLICK, releatSeries);
		}
		private var l:Label;

		public function releatSeries(e:MouseEvent):void {
			var a:ReportChartMetric=ReportChartMetric(e.currentTarget);
			var report:XMLNodeObject=XMLNodeObject(a.parentApplication.reportData);
			var model:XMLNodeObject=report.getChildById(a.getModel());
			new ModelColumnDialog().open(a, model, "选择字段", function(o:XMLNodeObject):void {
					var n:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject();
					n.setTagName("metric");
					n.setAttribute("column", o.getAttribute("id"));
					n.setAttribute("text", o.getAttribute("text"));
					l.text=n.getAttribute("text");
					if (ObjectUtil.isTrue(o.getAttribute("dimension"))) {
						MessageBox.alert("当前字段是维度字段，不能作为度量");
						return ;
					}
					setNode(n);
				});
		}

		override public function draw():void {
			var n:XMLNodeObject=getPanel().getChild("metrix");
			if (!ObjectUtil.isEmpty(n)) {
				this.l.text=n.getAttribute("text");
			}
		}
	}
}