package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;

	public class RPTPanelX extends RPTComponent {

		public function RPTPanelX() {
			//TODO: implement function
			super();
			horizontal();
			setSides("right");
			percentHeight=100;
			percentWidth=100;
			this.setStyle("backgroundColor", 0xE0E0E0);
			this.toolTip="表格x轴";
			ControlUtils.setDefaultBorder(this.body, "right");
			setMinSize(110, 60);
		}

		override public function getBackgroundColor():uint {
			return 0xE0E0E0;
		}

		override public function restoreBorder():void {
			ControlUtils.setBorder(this, 1, "solid", 0xE0E0E0);
		}
		private var listtab:Boolean=true;

		public function setListtab(b:Boolean):void {
			this.listtab=b;
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

		override public function notifyAppend(parent:XMLNodeObject, node:XMLNodeObject):void {
			addHeader(node);
		}

		private function addHeader(o:XMLNodeObject):void {
			var header:RPTPanelHeader=RPTPanelHeader(RPTComponentFactory.getFactory().getComponent(o));
			body.addChild(header);
			if (body.numChildren > 0)
				ControlUtils.setDefaultBorder(header, "left");
			header.setListtab(this.listtab);
		}

		override public function load(n:XMLNodeObject):void {
			var len:int=n.children().length;
			for (var i:int=0; i < len; i++) {
				var o:XMLNodeObject=XMLNodeObject(n.children()[i]);
				addHeader(o);
			}
			setData(n);
		}
	}
}