package com.ztesoft.crm.report.flex.flow {
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import flash.display.Graphics;
	import flash.display.Sprite;
	import flash.events.*;
	import flash.geom.Point;
	import flash.ui.*;
	import mx.core.UIComponent;

	public class Edge extends UIComponent {
		private var app:FlowContainer;
		private var sourceNode:String;
		private var targetNode:String;
		public var lineColor:uint;
		private var lineWidth:int;

		public function Edge(app1:FlowContainer, s:Node, t:Node) {
			//TODO: implement function
			super();
			this.app=app1;
			contextMenu=new ContextMenu();
			this.uid=Edge.getUuid(s, t);
			this.sourceNode=s.uid;
			this.targetNode=t.uid;
			var source:Node=s as Node;
			var target:Node=t as Node;
			source.addAnchor(new Anchor(this.uid));
			target.addAnchor(new Anchor(this.uid));
			this.lineColor=s.getLineColor();
			this.draw();
			this.doubleClickEnabled=true;
			var lineDoubleClick:Function=app.getEdgeDoubleClick();
			addEventListener(MouseEvent.DOUBLE_CLICK, function(e:MouseEvent):void {
					if (app.isDrawEdge())
						return ;
					if (lineDoubleClick != null) {
						lineDoubleClick(Edge(e.currentTarget), source, target);
					}
				});
			addEventListener(MouseEvent.MOUSE_OVER, function(e:MouseEvent):void {
					if (app.isDrawEdge())
						return ;
					redraw(0x000080, 4);
				});
			addEventListener(MouseEvent.MOUSE_OUT, function(e:MouseEvent):void {
					if (app.isDrawEdge())
						return ;
					redraw(lineColor, 2);
				});
			ControlUtils.setDefaultBorder(this, null);
		}

		public static function getUuid(s:Node, e:Node):String {
			return s.uid + "_" + e.uid;
		}
		private var edgeData:Object;

		public function setData(d:Object):void {
			this.edgeData=d;
		}

		public function getData():Object {
			return this.edgeData;
		}

		public function getTargetNode():String {
			return this.targetNode;
		}

		public function getSourceNode():String {
			return this.sourceNode;
		}

		private function redraw(color:uint, width:int):void {
			var source:Node=app.getChildWithUid(this.sourceNode)as Node;
			var target:Node=app.getChildWithUid(this.targetNode)as Node;
			var start:Point=null;
			var end:Point=null;
			var r1:Rectangle=new Rectangle(source.x, source.y, source.width, source.height);
			var r2:Rectangle=new Rectangle(target.x, target.y, target.width, target.height);
			var p:Point=r1.position(r2);
			switch (p.x) {
				case Rectangle.LEFT:
					start=new Point(r1.x - 1, (r1.y + (r1.height / 2)));
					break;
				case Rectangle.BOTTOM:
					start=new Point(r1.x + (r1.width / 2), r1.y + r1.height - 6);
					break;
				case Rectangle.TOP:
					start=new Point(r1.x + (r1.width / 2), r1.y - 1);
					break;
				default:
					start=new Point(r1.x + r1.width + 1, r1.y + (r1.height / 2));
					break;
			}
			switch (p.y) {
				case Rectangle.LEFT:
					end=new Point(r2.x - 1, (r2.y + (r2.height / 2)));
					break;
				case Rectangle.BOTTOM:
					end=new Point(r2.x + (r2.width / 2), r2.y + r2.height - 6);
					break;
				case Rectangle.TOP:
					end=new Point(r2.x + (r2.width / 2), r2.y - 1);
					break;
				default:
					end=new Point(r2.x + r2.width + 1, r2.y + (r2.height / 2));
					break;
			}
			var sp:Sprite=(this.numChildren > 0) ? this.getChildAt(0)as Sprite :
				new Sprite();
			var gc:Graphics=sp.graphics;
			gc.clear();
			gc.lineStyle(width, color, 1);
			gc.moveTo(start.x, start.y);
			gc.beginFill(0xFFFFFF);
			gc.lineTo(end.x, end.y);
			this.drawArrow(gc, start, end);
			gc.endFill();
			if (this.numChildren == 0)
				addChild(sp);
		/****/
		}

		public function draw():void {
			redraw(lineColor, 2);
			if (app.getEdgeDoubleClick() != null && app.isDrawEdge()) {
				app.getEdgeDoubleClick()(this, app.getNode(this.sourceNode), app.getNode(this.targetNode));
			}
		}

		private function drawArrow(gc:Graphics, start:Point, end:Point):void {
			var startX:Number=start.x;
			var startY:Number=start.y;
			var endX:Number=end.x;
			var endY:Number=end.y;
			var arrowLength:Number=6;
			var arrowAngle:Number=Math.PI / 6;
			var lineAngle:Number;
			if (endX - startX != 0)
				lineAngle=Math.atan((endY - startY) / (endX - startX));
			else {
				if (endY - startY < 0)
					lineAngle=Math.PI / 2;
				else
					lineAngle=3 * Math.PI / 2;
			}
			if (endY - startY >= 0 && endX - startX <= 0) {
				lineAngle=lineAngle + Math.PI;
			}
			else if (endY - startY <= 0 && endX - startX <= 0) {
				lineAngle=lineAngle + Math.PI;
			}
			var angleC:Number=arrowAngle;
			var rimA:Number=arrowLength;
			var rimB:Number=Math.pow(Math.pow(endY - startY, 2) + Math.pow(endX -
				startX, 2), 1 / 2);
			var rimC:Number=Math.pow(Math.pow(rimA, 2) + Math.pow(rimB, 2) - 2 *
				rimA * rimB * Math.cos(angleC), 1 / 2);
			var angleA:Number=Math.acos((rimB - rimA * Math.cos(angleC)) / rimC);
			var leftArrowAngle:Number=lineAngle + angleA;
			var rightArrowAngle:Number=lineAngle - angleA;
			var leftArrowX:Number=startX + rimC * Math.cos(leftArrowAngle);
			var leftArrowY:Number=startY + rimC * Math.sin(leftArrowAngle);
			var rightArrowX:Number=startX + rimC * Math.cos(rightArrowAngle);
			var rightArrowY:Number=startY + rimC * Math.sin(rightArrowAngle);
			with (gc) {
				moveTo(end.x, end.y);
				lineTo(leftArrowX, leftArrowY);
				moveTo(end.x, end.y);
				lineTo(rightArrowX, rightArrowY);
			}
		}
	}
}