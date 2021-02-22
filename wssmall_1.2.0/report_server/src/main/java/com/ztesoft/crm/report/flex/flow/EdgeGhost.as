package com.ztesoft.crm.report.flex.flow {
	import flash.display.Graphics;
	import flash.display.Sprite;
	import flash.geom.Point;
	import mx.core.UIComponent;

	public class EdgeGhost extends UIComponent {

		public function EdgeGhost() {
			//TODO: implement function
			super();
			var sp:Sprite=new Sprite();
			this.gc=sp.graphics;
			addChild(sp);
			this.setStyle("backgroundColor", "#C0C0C0");
		}
		private var gc:Graphics;

		public function clear():void {
			gc.clear();
		}

		public function draw(start:Point, end:Point, lineColor:uint):void {
			gc.clear();
			gc.lineStyle(2, lineColor, 1);
			gc.moveTo(start.x, start.y);
			gc.beginFill(lineColor);
			gc.lineTo(end.x, end.y);
			gc.endFill();
		}
	}
}