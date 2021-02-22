package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import mx.containers.Box;
	import mx.core.UIComponent;

	public class RPTPanelRow extends RPTComponent {

		public function RPTPanelRow() {
			//TODO: implement function
			super();
			this.percentHeight=100;
			this.percentWidth=100;
			this.restoreBorder();
			this.setSides("right");
			this.setStyle("backgroundColor", getBackgroundColor());
			this.body.verticalScrollPolicy="off";
			ControlUtils.setDefaultBorder(UIComponent(UIComponent(body.parent).parent),
				"bottom");
			this.toolTip="表格行";
		}

		override public function getBackgroundColor():uint {
			return 0xE0E0E0;
		}

		override public function restoreBorder():void {
			ControlUtils.setBorder(this, 1, "solid", 0xE0E0E0);
		}

		override public function acceptChild(type:String):int {
			if ("textheader,columnheader".indexOf(type) >= 0) {
				return RPTComponent.CHILD;
			}
			return 0;
		}

		override public function addChildData(region:String, o:Object):void {
			var n:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject("header");
			if (o is XMLNodeObject) { //关联字段
				var o1:XMLNodeObject=XMLNodeObject(o);
				n.setAttribute("ref", o1.getId());
				n.setAttribute("text", o1.getAttribute("text"));
				n.setAttribute("oldtext", o1.getAttribute("text"));
			}
			else
				n.setAttributes(o);
			getData().add(n);
		}

		override public function notifyDelete(parent:XMLNodeObject, node:XMLNodeObject):void {
			body.removeChild(RPTComponent(node.getObserver()));
			if (this.numChildren == 0) {
				render();
			}
			node.release();
		}

		private function addHeader(node:XMLNodeObject):void {
			var h:RPTPanelHeader=RPTPanelHeader(RPTComponentFactory.getFactory().getComponent(node));
			h.horizontal();
			if (body.numChildren > 0) {
				ControlUtils.setDefaultBorder(h, "top");
			}
			body.addChild(h);
		}

		override protected function renderBody(body:Box):void {
			this.setMinSize(100, 52);
		}

		override public function notifyAppend(parent:XMLNodeObject, node:XMLNodeObject):void {
			addHeader(node);
		}

		override public function load(n:XMLNodeObject):void {
			var len:int=n.children().length;
			for (var i:int=0; i < len; i++) {
				var o:XMLNodeObject=XMLNodeObject(n.children()[i]);
				addHeader(o);
			}
		}
	}
}