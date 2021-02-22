package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import mx.containers.Box;

	public class RPTFormRow extends RPTComponent {

		public function RPTFormRow() {
			//TODO: implement function
			super();
			horizontal();
			setSides("top bottom");
			percentWidth=100;
			ControlUtils.setPadding(this, 10, 0, 10, 0);
		}

		override public function restoreBorder():void {
			ControlUtils.setBorder(this, 0, "solid", 0xFFFFFF);
		}

		override public function getPropertiesConfig():Object {
			return {cols: 1, items: [{label: "高度", name: "height"}, {label: "按钮组", name: "buttons", type: "radiobox", data: ReportConstanst.TRUE_OR_FALSE}, {label: "纵向撑满", name: "verticalFill", type: "radiobox",
							data: ReportConstanst.TRUE_OR_FALSE}, {label: "水平对齐", name: "align", type: "radiobox", data: ReportConstanst.HEADER_ALIGN}]};
		}

		override public function load(n:XMLNodeObject):void {
			if (!n.emptyChilds())
				body.removeAllChildren();
			super.load(n);
			if (getData() == null)
				this.setData(n);
		}
		private var childTypes:Array=["checkbox", "radiobox", "text", "combo", "date", "submit", "excel", "treefield", "labelfield", "popupselect", "prompt", "print", "link"];

		override public function acceptChild(type:String):int {
			if (type == "formrow")
				return RPTComponent.SIBLING;
			if (childTypes.indexOf(type) >= 0) {
				return RPTComponent.CHILD;
			}
			return 0;
		}

		override protected function renderBody(body:Box):void {
			this.body.removeAllChildren();
			this.setMinSize(0, 23);
		}
	}
}