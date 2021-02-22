package com.ztesoft.crm.report.flex.flow {
	import flash.events.MouseEvent;
	import flash.external.ExternalInterface;
	import flash.geom.Point;
	import mx.core.DragSource;
	import mx.core.UIComponent;
	import mx.events.DragEvent;
	import mx.managers.DragManager;

	public class DND {

		public function DND() {
		//TODO: implement function
		}

		/**
		 * 开始拖动
		 *
		 **/
		public function dragStart(e:MouseEvent):void {
			var flow:FlowContainer=e.currentTarget as FlowContainer;
			var p:Point=new Point(flow.mouseX, flow.mouseY);
			var node:Node=flow.getNodeAt(p.x, p.y);
			//var node:Node=flow.getNodeAt(flow.mouseX, flow.mouseY);
			if (node == null)
				return ;
			var ds:DragSource=new DragSource();
			var data:Object={"x":p.x, "y":p.y, dragEdge:flow.isDrawEdge(), feedback:DragManager.MOVE, 'index':flow.getChildIndex(node)};
			if (!flow.isDrawEdge()) {
				data.feedback=DragManager.MOVE;
				ds.addData(data, 'formatString'); //设置一个标号format  
				//if (!flow.isDrawEdge()) { //不是画线
				//flow.alert(flow.mouseX + "=" + flow.mouseY);
				DragManager.doDrag(node, ds, e);
				flow.setFocusUIComponent(node);
			}
			else {
				flow.setMousePoint(p);
				flow.rebuildEdgeGhost();
			}
		//} else {
		//flow.alert(flow.mouseX + "=" + flow.mouseY);
		//DragManager.doDrag(null, ds, e);
		//}
		}

		//当拖进去时  
		public function dragEnter(e:DragEvent):void {
			var flow:FlowContainer=e.currentTarget as FlowContainer;
			if (flow.isDrawEdge())
				return ;
			if (e.dragSource.hasFormat('formatString')) //如果标号为format则接受拖来的物体  
			{
				try {
					var o:Object=e.dragSource.dataForFormat('formatString');
					var tg:UIComponent=e.target as UIComponent;
					if (o.feedback != null)
						DragManager.showFeedback(o.feedback);
					DragManager.acceptDragDrop(UIComponent(tg));
				}
				catch (e:Error) {
					flash.external.ExternalInterface.call("alert", e.message);
				}
			}
		}

		//当拖完成时  
		public function dragDrop(e:DragEvent):void {
			var myData:Object=e.dragSource.dataForFormat('formatString');
			var iu:FlowContainer=e.currentTarget as FlowContainer;
			var p:Point=iu.localToContent(new Point(iu.mouseX, iu.mouseY));
			if (iu.isDrawEdge())
				return ;
			if (myData.createNode == true) {
				var u:FlowContainer=iu;
				u.addChild(myData.node);
				myData.node.setLocation(p.x, p.y);
			}
			else {
				var btn:Node=iu.getChildAt(myData.index)as Node;
				if (!iu.isDrawEdge()) {
					btn.setLocation(btn.x + p.x - myData.x, btn.y + p.y - myData.y);
				}
			}
			myData=null;
		}

		public function onDrawEdge(e:MouseEvent):void {
			var flow:FlowContainer=e.currentTarget as FlowContainer;
			if (flow.isDrawEdge() && flow.getMousePoint() != null) {
				var start:Node=flow.getNodeAt(flow.getMousePoint().x, flow.getMousePoint().y);
				var end:Point=flow.localToContent(new Point(flow.mouseX, flow.mouseY));
				flow.getEdgeGhost().draw(flow.localToContent(flow.getMousePoint()), end, start.getLineColor());
				end=null;
			}
			flow=null;
		}

		public function finishDrawEdge(e:MouseEvent):void {
			var flow:FlowContainer=e.currentTarget as FlowContainer;
			var p:Point=flow.localToContent(new Point(flow.mouseX, flow.mouseY));
			if (flow.isDrawEdge() && flow.getMousePoint() != null) {
				var start:Node=flow.getNodeAt(flow.getMousePoint().x, flow.getMousePoint().y);
				var end:Node=flow.getNodeAt(flow.mouseX, flow.mouseY);
				if (start != null && end != null) {
					if (start != end)
						flow.addEdge(start, end);
				}
			}
			var edg:EdgeGhost=flow.getEdgeGhost();
			if (edg)
				flow.getEdgeGhost().clear();
			flow.setMousePoint(null);
		//flow.stopDrawEdge();
		}
	}
}