package com.ztesoft.crm.report.flex.flow {
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import flash.events.*;
	import flash.external.*;
	import flash.text.*;
	import flash.ui.*;
	import mx.containers.Box;
	import mx.controls.Image;
	import mx.events.ResizeEvent;

	public class Node extends Box {
		private var bgImageControl:Image;
		private var textControl:TextField;

		public function Node() {
			//TODO: imple ment function
			super();
			this.addEventListener(ResizeEvent.RESIZE, firstResize);
		}

		private function firstResize(e:ResizeEvent):void {
			var n:Node=Node(e.currentTarget);
			var app:FlowContainer=FlowContainer(n.parent);
			var arr:Array=app.getNodeAllEdges(n);
			var len:int=arr.length;
			for (var i:int=0; i < len; i++) {
				Edge(arr[i]).draw();
			}
			n.removeEventListener(ResizeEvent.RESIZE, firstResize);
		}
		private var anchors:Array=[];

		public function getLineColor():uint {
			return 0x000000;
		}

		public function addAnchor(a:Anchor):void {
			this.anchors.push(a);
		}

		public function setLocation(x1:int, y1:int):void {
			this.x=x1;
			this.y=y1;
			for (var i:Number=0; i < this.anchors.length; i++) {
				var an:Anchor=this.anchors[i];
				an.change(parent as FlowContainer);
			}
		}

		public function getData():Object {
			return null;
		}

		public function containPoint(x:int, y:int, c:FlowContainer):Boolean {
			return this.getBounds(c).contains(x, y);
		}
	}
}