package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;

	public class RPTPanelMetric extends RPTPanelField {

		public function RPTPanelMetric() {
			//TODO: implement function
			super();
		}

		override public function getPropertiesConfig():Object {
			return {tagName: "metric", cols: 1, items: [{label: "中文名", name: "text"}, {label: "数据域名", name: "name"}, {label: "水平对齐", name: "align", type: "radiobox", data: ReportConstanst.HEADER_ALIGN}],
					data: getData()};
		}

		override public function emptyText():String {
			return "在此设定度量";
		}

		override public function restoreBorder():void {
			if (!(getParent() is RPTCrosstab)) {
				ControlUtils.setBorder(this, 1, "solid", 0xCCCCCC);
			} else
				ControlUtils.setBorder(this, 1, "solid", getBackgroundColor());
		}

		override public function acceptChild(type:String):int {
			if (["textheader", "columnheader"].indexOf(type) >= 0) {
				return RPTComponent.CHILD;
			}
			return 0;
		}

		override public function addChildData(region:String, o:Object):void {
			var n:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject("metric");
			if (o is XMLNodeObject) { //关联字段
				var o1:XMLNodeObject=XMLNodeObject(o);
				n.setAttribute("column", o1.getId());
				n.setAttribute("text", o1.getAttribute("text"));
				n.setAttribute("oldtext", o1.getAttribute("text"));
			} else
				n.setAttributes(o);
			getParent().getData().replace(this.getData(), n);
			this.setData(n);
			this.load(n);
		}
	}
}