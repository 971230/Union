package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.dialog.PropertiesDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectObserver;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.display.DisplayObjectContainer;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;

	import mx.containers.Box;
	import mx.containers.BoxDirection;
	import mx.containers.HBox;
	import mx.containers.VBox;
	import mx.controls.Label;

	public class RPTComponent extends Box implements XMLNodeObjectObserver {
		public static var FOCUS_BORDER_COLOR:uint=0x008080;
		public static var FOCUS_BACKGROUND_COLOR:uint=0xF0F0F0;
		public static var CHILD:int=2;
		public static var SIBLING:int=1;

		public function RPTComponent() {
			super();
			this.direction=BoxDirection.VERTICAL;
			this.top=createRegion("top");
			addChild(this.top);
			var hbox:HBox=new HBox();
			hbox.percentHeight=100;
			hbox.percentWidth=100;
			addChild(hbox);
			this.left=createRegion("left");
			hbox.addChild(this.left);
			var ab:VBox=new VBox();
			ab.percentHeight=100;
			ab.percentWidth=100;
			hbox.addChild(ab);
			this.body=new Box();
			this.body.direction=BoxDirection.VERTICAL;
			ab.addChild(this.body);
			this.body.percentHeight=100;
			this.body.percentWidth=100;
			this.right=createRegion("right");
			hbox.addChild(this.right);
			this.bottom=createRegion("bottom");
			addChild(this.bottom);
			hbox.setStyle("horizontalGap", 0);
			hbox.setStyle("verticalGap", 0);
			this.setStyle("horizontalGap", 0);
			this.setStyle("verticalGap", 0);
			body.setStyle("horizontalGap", 0);
			body.setStyle("verticalGap", 0);
			ControlUtils.setPadding(this, 0, 0, 0, 0);
			ControlUtils.setPadding(hbox, 0, 0, 0, 0);
			ControlUtils.setPadding(body, 0, 0, 0, 0);
			this.addEventListener(MouseEvent.MOUSE_OVER, new RPTDND().dragEnter);
			this.addEventListener(MouseEvent.MOUSE_MOVE, new RPTDND().dragEnter);
			this.addEventListener(MouseEvent.MOUSE_OUT, new RPTDND().dragExit);
			this.addEventListener(MouseEvent.MOUSE_UP, new RPTDND().dragDrop);
			this.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
				if (RPTDND.rpt != null) {
					RPTDND.rpt.forceFocus(false);
				} else {
					RPTDND.rpt=RPTComponent(e.currentTarget);
					RPTDND.rpt.forceFocus(true);
				}
				RPTDND.rpt.setFocus();
				e.stopImmediatePropagation();
			});
			this.addEventListener(KeyboardEvent.KEY_UP, function(e:KeyboardEvent):void {
				if (e.charCode == 127 && RPTDND.rpt != null) {
					var d:XMLNodeObject=RPTDND.rpt.getData();
					if (d == null)
						return;
					if (d.getTagName() == "x" || d.getTagName() == "y" || d.getTagName() == "view") {
						return;
					}
					d.parent.removeChildById(d.getId());
				}
				e.stopImmediatePropagation();
			});
			this.doubleClickEnabled=true;
			this.addEventListener(MouseEvent.DOUBLE_CLICK, function(e:MouseEvent):void {
				editProperties(RPTComponent(e.currentTarget));
				e.stopImmediatePropagation();
			});
			//this.contextMenu=ControlUtils.getContextMenu([{text:"编辑属性", handler:editProperties}]);
			this.body.verticalScrollPolicy="off";
			this.body.horizontalScrollPolicy="off";
		}

		public function setErrorStyle():void {
			ControlUtils.setBorder(this, 1, "solid", 0xc40808);
			this.setStyle("backgroundColor", 0xf2a8ab);
		}

		public function setLText(s:String):void {
			var l:Label=new Label();
			l.text=s;
			l.height=21;
			l.setStyle("fontWeight", "bold");
			l.setStyle("fontSize", 14);
			body.parent.addChildAt(l, 0);
			//this.lText.alpha=0;
		}

		private function editProperties(rl:RPTComponent):void {
			//var rl:RPTComponent=RPTComponent(e.contextMenuOwner);
			var p:Object=getPropertiesConfig();
			var pp:RPTComponent=this;
			while (pp != null) {
				p=pp.getPropertiesConfig();
				if (p != null)
					break;
				pp=pp.getParent();
			}
			if (p == null)
				return;
			if (ObjectUtil.isEmpty(p["data"])) {
				p["data"]=pp.getData();
			}
			var propertyDialog:PropertiesDialog=new PropertiesDialog();
			if (ObjectUtil.isEmpty(p.callback)) {

				propertyDialog.setCallback(function(d:XMLNodeObject):void {
					pp.getData().setContent(d.getContent());
					pp.getData().setAttributes(d.attributes());
					var o:XMLNodeObject=d.getChild("styleRule");
					if (o != null) {
						pp.getData().removeChildByTagName(o.getTagName());
						pp.getData().add(o);
					}
				});
				/*
				 new FormDialog().open(pp, p, "编辑属性", );*/
			} else {
				propertyDialog.setCallback(p.callback);
			}
			propertyDialog.open(pp, p);
		}

		public function setMinSize(w:int, h:int):void {
			if (h > 0)
				body.minHeight=h;
			if (w > 0)
				body.minWidth=w;
		}

		public function horizontal():void {
			this.body.direction=BoxDirection.HORIZONTAL;
		}
		protected var forceFocusFlag:Boolean=false;
		public var selectedRegion:String;

		public function getBackgroundColor():uint {
			return 0xFFFFFF;
		}

		public function setRegioBackgroundAlpha(i:int):void {
			this.left.setStyle("backgroundAlpha", i);
			this.right.setStyle("backgroundAlpha", i);
			this.top.setStyle("backgroundAlpha", i);
			this.bottom.setStyle("backgroundAlpha", i);
		}

		public function forceFocus(b:Boolean):void {
			if (this.forceFocusFlag == b)
				return;
			this.forceFocusFlag=b;
			if (b == true) {
				setStyle("backgroundColor", RPTComponent.FOCUS_BACKGROUND_COLOR);
				ControlUtils.setBorder(this, 1, "solid", RPTComponent.FOCUS_BORDER_COLOR);
			} else {
				setStyle("backgroundColor", getBackgroundColor());
				restoreBorder();
			}
		}

		public function restoreBorder():void {
			ControlUtils.setBorder(this, 1, "solid", 0xFFFFFF);
		}

		public function setData(n:XMLNodeObject):void {
			if (ObjectUtil.isEmpty(n)) {
				this.data=null;
				return;
			}
			n.setObserver(this);
			this.data=n;
		}

		public function getData():XMLNodeObject {
			return XMLNodeObject(this.data);
		}

		public function isNullData():Boolean {
			return ObjectUtil.isEmpty(this.data);
		}

		public function getParent():RPTComponent {
			var a:DisplayObjectContainer=this.parent;
			while (!ObjectUtil.isEmpty(a)) {
				if (a is RPTComponent)
					return RPTComponent(a);
				a=a.parent;
			}
			return null;
		}

		public function load(n:XMLNodeObject):void {
			var len:int=n.children().length;
			if (len > 0) {
				for (var i:int=0; i < len; i++) {
					var en:XMLNodeObject=n.children()[i];
					var rpt:RPTComponent=RPTComponentFactory.getFactory().getComponent(en);
					body.addChild(rpt);
				}
			}
			setData(n);
		}

		public final function render():void {
			this.renderBody(this.body);
		}

		protected function renderBody(body:Box):void {
		}
		protected var body:Box;
		private var sides:String="left top bottom right";
		private var left:RPTRegionLabel;
		private var right:RPTRegionLabel;
		private var bottom:RPTRegionLabel;
		private var top:RPTRegionLabel;

		public function removeBodyChild(u:RPTComponent):void {
			this.body.removeChild(u);
			u.getData().release();
		}

		public final function get leftRegion():RPTRegionLabel {
			return left;
		}

		public final function get rightRegion():RPTRegionLabel {
			return right;
		}

		public final function get bottomRegion():RPTRegionLabel {
			return bottom;
		}

		public final function get topRegion():RPTRegionLabel {
			return top;
		}

		public function acceptChild(type:String):int {
			return 0;
		}
		private var sp:int=10;

		public function setSides(s:String):void {
			if (s == null)
				s="left top bottom right";
			this.sides=s;
			this.left.width=0;
			this.right.width=0;
			this.top.height=0;
			this.bottom.height=0;
			if (s.indexOf("left") >= 0)
				this.left.width=sp;
			if (s.indexOf("right") >= 0)
				this.right.width=sp;
			if (s.indexOf("bottom") >= 0)
				this.bottom.height=sp;
			if (s.indexOf("top") >= 0)
				this.top.height=sp;
		}

		private function createRegion(region:String):RPTRegionLabel {
			var label:RPTRegionLabel=new RPTRegionLabel(this);
			label.data=region;
			var sp:int=0;
			switch (region) {
				case "top":
					label.percentWidth=100;
					label.height=sp;
					break;
				case "bottom":
					label.percentWidth=100;
					label.height=sp;
					break;
				case "left":
					label.percentHeight=100;
					label.width=sp;
					break;
				case "right":
					label.percentHeight=100;
					label.width=sp;
					break;
			}
			return label;
		}

		public function getPropertiesConfig():Object {
			return null;
		}
		public static var lastComponent:RPTComponent=null;

		public function addChildData(region:String, o:Object):void {
			if (o is XMLNodeObject) {
				var n:XMLNodeObject=XMLNodeObject(o);
				if (region == "left" || region == "top") {
					getData().insertBefore(n);
					return;
				}
				if (region == "bottom" || region == "right") {
					getData().insertAfter(n);
					return;
				}
				getData().add(n);
			}
		}

		public function notifyAppend(parent:XMLNodeObject, node:XMLNodeObject):void {
			var rc:RPTComponent=null;
			if (parent.getAttribute("type") == "form") {
				rc=RPTComponentFactory.getFactory().getComponent(node, RPTFormRow);
			} else
				rc=RPTComponentFactory.getFactory().getComponent(node);
			if (node.index < 0)
				body.addChild(rc);
			else {
				body.addChildAt(rc, node.index);
			}
		}

		public function notifyDelete(parent:XMLNodeObject, node:XMLNodeObject):void {
			body.removeChild(RPTComponent(node.getObserver()));
			node.release();
		}

		public function change(node:XMLNodeObject):void {
		}

		public function propertyChange(node:XMLNodeObject, name:String):void {
		}

		public function getModel():XMLNodeObject {
			var n:XMLNodeObject=null;
			var p:RPTComponent=this;
			while (p != null) {
				n=p.getData();
				if (n != null)
					return n.getModel();
				p=p.getParent();
			}
			return null;
		}
	}
}