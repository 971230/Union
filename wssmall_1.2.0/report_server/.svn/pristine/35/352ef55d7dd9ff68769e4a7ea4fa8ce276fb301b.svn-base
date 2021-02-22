package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	public class RPTPanelAxis extends RPTPanelField {

		public function RPTPanelAxis() {
			//TODO: implement function
			super();
		}

		override public function getPropertiesConfig():Object {
			return {tagName: "axis", cols: 1, items: [{label: "中文名", name: "text"}, {label: "数据域名", name: "name"}]};
		}

		override public function emptyText():String {
			return "在此设定X坐标轴";
		}

		override public function acceptChild(type:String):int {
			if (["textheader", "columnheader"].indexOf(type) >= 0) {
				return RPTComponent.CHILD;
			}
			if ("cncolumnheader" == type && !isNullData() && !getData().isEmptyAttribute("column")) {
				return RPTComponent.CHILD;
			}
			return 0;
		}

		override public function addChildData(region:String, o:Object):void {
			var n:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject("axis");
			if (region != "cncolumnheader") {

				if (o is XMLNodeObject) { //关联字段
					var o1:XMLNodeObject=XMLNodeObject(o);
					n.setAttribute("column", o1.getId());
					n.setAttribute("text", o1.getAttribute("text"));
					n.setAttribute("oldtext", o1.getAttribute("text"));
				} else
					n.setAttributes(o);

			} else {
				var o2:XMLNodeObject=XMLNodeObject(o);
				var dd:Object={text: getData().getAttribute("oldtext") + "(" + o2.getAttribute("text") + ")", labelColumn: o2.getId()};
				n.setAttributes(dd);
			}
			getParent().getData().replace(this.getData(), n);

			load(n);
		}

		override public function restoreBorder():void {
			ControlUtils.setDefaultBorder(this, null);
		}
	}
}