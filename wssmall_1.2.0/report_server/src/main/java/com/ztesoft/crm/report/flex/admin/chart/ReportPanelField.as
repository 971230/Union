package com.ztesoft.crm.report.flex.admin.chart {
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;
	import mx.containers.VBox;

	public class ReportPanelField extends VBox {

		public function ReportPanelField() {
			//TODO: implement function
			super();
		}
		private var panel:XMLNodeObject;
		private var model:String;
		private var node:XMLNodeObject;

		public final function setNode(n:XMLNodeObject):void {
			if (!ObjectUtil.isEmpty(this.node)) {
				panel.removeChildById(node.getId());
			}
			panel.add(n);
		}

		public final function getModel():String {
			return this.model;
		}

		public final function getPanel():XMLNodeObject {
			return this.panel;
		}

		public final function load(n:XMLNodeObject):void {
			this.panel=n;
			this.model=this.panel.getAttribute("model");
			this.draw();
		}

		public function draw():void {
		}
	}
}