package com.ztesoft.crm.report.flex.flow {
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import flash.display.DisplayObject;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import mx.core.LayoutContainer;
	import mx.core.UIComponent;
	import mx.events.DragEvent;

	public class FlowContainer extends LayoutContainer {

		public function FlowContainer() {
			//TODO: implement function
			super();
			addEventListener(DragEvent.DRAG_DROP, new DND().dragDrop);
			addEventListener(MouseEvent.MOUSE_DOWN, new DND().dragStart);
			addEventListener(DragEvent.DRAG_ENTER, new DND().dragEnter);
			addEventListener(MouseEvent.MOUSE_UP, new DND().finishDrawEdge);
			addEventListener(MouseEvent.MOUSE_MOVE, new DND().onDrawEdge);
			addEventListener(KeyboardEvent.KEY_UP, deleteEvent);
		}
		private var currNode:UIComponent;
		private var deleteChildEvent:Function;

		public function setDeleteChildEvent(f:Function):void {
			this.deleteChildEvent=f;
		}

		private function deleteEvent(e:KeyboardEvent):void {
			if (e.charCode != 127)
				return ;
			if (this.currNode != null) {
				if (this.currNode is Edge) {
					var edge:Edge=Edge(this.currNode);
					this.removeChild(edge);
					if (this.deleteChildEvent != null) {
						this.deleteChildEvent(this, edge.getData());
					}
				}
				if (this.currNode is Node) {
					var n:Node=Node(this.currNode);
					var arr:Array=getNodeAllEdges(n);
					var len:int=arr.length;
					for (var i:int=0; i < len; i++) {
						var edge1:Edge=Edge(arr[i]);
						this.removeChild(edge1);
						if (this.deleteChildEvent != null) {
							this.deleteChildEvent(this, edge1.getData());
						}
					}
					this.removeChild(n);
					if (this.deleteChildEvent != null) {
						this.deleteChildEvent(this, n.getData());
					}
				}
			}
			setFocusUIComponent(null);
		}

		public function setFocusUIComponent(e:UIComponent):void {
			if (this.currNode != null && this.currNode is Node) {
				ControlUtils.setBorder(this.currNode, 1, 'solid', Node(this.currNode).getLineColor());
			}
			this.currNode=e;
			if (e != null && e is Node) {
				ControlUtils.setBorder(this.currNode, 2, 'solid', 0x000080);
			}
			this.setFocus();
		}
		private var edgeDoubleClick:Function;

		public function setEdgeDoubleClick(f:Function):void {
			this.edgeDoubleClick=f;
		}

		public function getEdgeDoubleClick():Function {
			return this.edgeDoubleClick;
		}

		public function addEdge(start:Node, end:Node):Edge {
			if (this.getChildWithUid(Edge.getUuid(start, end)) == null) {
				var ed:Edge=new Edge(this, start, end);
				addChild(ed);
				return ed;
			}
			return null;
		}

		public function getNode(id:String):Node {
			for (var i:int=0; i < this.numChildren; i++) {
				var o:UIComponent=this.getChildAt(i)as UIComponent;
				if (o is Node) {
					if (Node(o).uid == id)
						return Node(o);
				}
			}
			return null;
		}

		public function getNodeEdges(n:Node):Array {
			var arr:Array=[];
			for (var i:int=0; i < this.numChildren; i++) {
				var o:UIComponent=this.getChildAt(i)as UIComponent;
				if (o is Edge) {
					var edge:Edge=Edge(o);
					if (edge.getTargetNode() == n.uid) {
						arr.push(o);
					}
				}
			}
			return arr;
		}

		public function getNodeAllEdges(n:Node):Array {
			var arr:Array=[];
			for (var i:int=0; i < this.numChildren; i++) {
				var o:UIComponent=this.getChildAt(i)as UIComponent;
				if (o is Edge) {
					var edge:Edge=Edge(o);
					if (edge.getTargetNode() == n.uid || edge.getSourceNode() ==
						n.uid) {
						arr.push(o);
					}
				}
			}
			return arr;
		}

		public function getChildWithUid(uuid:String):UIComponent {
			for (var i:int=0; i < this.numChildren; i++) {
				var o:UIComponent=this.getChildAt(i)as UIComponent;
				if (o.uid == uuid)
					return o;
			}
			return null;
		}
		private var edgeGhost:EdgeGhost;

		public final function getEdgeGhost():EdgeGhost {
			return this.edgeGhost;
		}

		public final function rebuildEdgeGhost():void {
			if (this.edgeGhost != null) {
				this.removeChild(this.edgeGhost);
				this.edgeGhost=null;
			}
			this.edgeGhost=new EdgeGhost();
			addChild(this.edgeGhost);
		}
		private var bEdge:Boolean=false;

		public final function startDrawEdge():void {
			this.bEdge=true;
		}

		public final function stopDrawEdge():void {
			this.bEdge=false;
		}

		public final function isDrawEdge():Boolean {
			return this.bEdge;
		}
		private var mousePoint:Point=null;

		public final function setMousePoint(a:Point):void {
			this.mousePoint=a;
		}

		public final function getMousePoint():Point {
			return this.mousePoint;
		}

		public final function getNodeAt(x:int, y:int):Node {
			//var p:Point=this.localToGlobal(new Point(x, y));
			var p:Point=new Point(x, y);
			for (var i:int=this.numChildren - 1; i >= 0; i--) {
				var o:DisplayObject=this.getChildAt(i);
				if (o is Node) {
					var n:Node=o as Node;
					if (!this.isDrawEdge()) {
						if (n.containPoint(x, y, this))
							return n;
					}
					else {
						if (n.getBounds(this).contains(x, y)) {
							return n;
						}
					}
				}
			}
			return null;
		}
	}
}