package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import flash.events.MouseEvent;
	import mx.containers.Box;
	import mx.controls.Label;

	public class RPTRegionLabel extends Box {

		public function RPTRegionLabel(c:RPTComponent) {
			//TODO: implement function
			super();
			var label:Label=new Label();
			label.text=" ";
			label.height=5;
			addChild(label);
			this.setStyle("horizontalGap", 0);
			this.setStyle("verticalGap", 0);
			this.c=c;
		}
		private var c:RPTComponent;

		public function get component():RPTComponent {
			return this.c;
		}
	}
}