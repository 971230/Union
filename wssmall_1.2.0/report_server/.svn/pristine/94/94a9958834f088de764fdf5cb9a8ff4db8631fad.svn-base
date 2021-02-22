package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;

	public class RPTPanelSeries extends RPTPanelField {

		public function RPTPanelSeries() {
			//TODO: implement function
			super();
			restoreBorder();
		}

		override public function acceptChild(type:String):int {
			if ("cncolumnheader" == type && !isNullData() && !getData().isEmptyAttribute("column")) {
				return RPTComponent.CHILD;
			}
			return 0;
		}

		override public function addChildData(region:String, o:Object):void {
			var o2:XMLNodeObject=XMLNodeObject(o);
			var dd:Object={text:getData().getAttribute("oldtext") + "(" + o2.getAttribute("text") + ")", labelRef:o2.getId()};
			getData().setAttributes(dd);
		}

		override public function restoreBorder():void {
			ControlUtils.setBorder(this, 1, "solid", 0xffffff);
		}
	}
}