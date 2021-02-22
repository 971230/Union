package com.ztesoft.crm.report.flex.monitor {
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.xml.XMLNode;

	import mx.controls.dataGridClasses.DataGridItemRenderer;

	public class MonitorGridItemRenderer extends DataGridItemRenderer {
		public function MonitorGridItemRenderer() {
			//TODO: implement function
			super();
		}

		override public function set data(value:Object):void {
			super.data=value;
			if (value != null && (value is XMLNode) && ObjectUtil.isTrue((XMLNode(value).attributes.error))) {

				this.setStyle("color", "red");

			} else {
				this.setStyle("color", "0x000000");
			}
		}



	}
}