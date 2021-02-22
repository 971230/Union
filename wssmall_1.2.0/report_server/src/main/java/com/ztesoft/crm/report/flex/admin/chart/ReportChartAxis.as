package com.ztesoft.crm.report.flex.admin.chart {
	import com.ztesoft.crm.report.flex.admin.dialog.ModelColumnDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;
	import flash.events.MouseEvent;
	import mx.controls.Label;

	public class ReportChartAxis extends ReportPanelField {

		public function ReportChartAxis() {
			//TODO: implement function
			super();
			this.l=new Label();
			this.doubleClickEnabled=true;
			this.addChild(l);
			this.addEventListener(MouseEvent.DOUBLE_CLICK, releatSeries);
		}
		private var l:Label;

		public function releatSeries(e:MouseEvent):void {
			var a:ReportChartAxis=ReportChartAxis(e.currentTarget);
			var report:XMLNodeObject=XMLNodeObject(a.parentApplication.reportData);
			var model:XMLNodeObject=report.getChildById(a.getModel());
			new ModelColumnDialog().open(a, model, "设定字段", function(o:XMLNodeObject):void {
					var n:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject();
					n.setTagName("axis");
					n.setAttribute("column", o.getAttribute("id"));
					n.setAttribute("text", o.getAttribute("text"));
					l.text=n.getAttribute("text");
					setNode(n);
				});
		}

		override public function draw():void {
			var n:XMLNodeObject=getPanel().getChild("axis");
			if (!ObjectUtil.isEmpty(n)) {
				this.l.text=n.getAttribute("text");
			}
		}
	}
}