package com.ztesoft.crm.report.flex.flow {
	import flash.geom.Point;

	public class Rectangle {

		public static function newRectangle(a:flash.geom.Rectangle):Rectangle {
			return new Rectangle(a.x, a.y, a.width, a.height);
		}

		public function Rectangle(x1:Number, y1:Number, w1:Number, h1:Number) {
			//TODO: implement function
			this.x=x1;
			this.y=y1;
			this.width=w1;
			this.height=h1;
		}
		public var x:Number;
		public var y:Number;
		public var width:Number;
		public var height:Number;
		public static var LEFT:Number=1;
		public static var TOP:Number=2;
		public static var RIGHT:Number=3;
		public static var BOTTOM:Number=4;

		public function toString():String {
			return "{" + x + "," + y + "," + width + "," + height + "}";
		}

		public function position(r:Rectangle):Point {
			var x1:Number=this.x - r.x;
			var y1:Number=this.y - r.y;
			var d:Number=(x1 == 0) ? 90 : ((Math.atan2(y1, x1) * 180) / Math.PI);
			if (d < 0)
				d=360 + d;
			if (d <= 45)
				return new Point(Rectangle.LEFT, Rectangle.RIGHT);
			if (d <= 90)
				return new Point(Rectangle.TOP, Rectangle.BOTTOM);
			if (d <= 135)
				return new Point(Rectangle.TOP, Rectangle.BOTTOM);
			if (d <= 180)
				return new Point(Rectangle.RIGHT, Rectangle.LEFT);
			if (d <= 225)
				return new Point(Rectangle.RIGHT, Rectangle.LEFT);
			if (d <= 270)
				return new Point(Rectangle.BOTTOM, Rectangle.TOP);
			if (d <= 315)
				return new Point(Rectangle.BOTTOM, Rectangle.TOP);
			return new Point(Rectangle.LEFT, Rectangle.RIGHT);
		}
	}
}