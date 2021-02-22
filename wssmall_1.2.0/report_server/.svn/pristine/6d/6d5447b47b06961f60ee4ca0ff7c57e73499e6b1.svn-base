package com.ztesoft.crm.report.flex.flow {
	import flash.geom.Point;
	import mx.core.UIComponent;

	public class Anchor {

		public function Anchor(uuid:String) {
			//TODO: implement function
			this.edgeUuid=uuid;
		}
		private var edgeUuid:String;

		public function change(app:FlowContainer):void {
			var edg:Edge=app.getChildWithUid(this.edgeUuid)as Edge;
			edg.draw();
		}
	}
}