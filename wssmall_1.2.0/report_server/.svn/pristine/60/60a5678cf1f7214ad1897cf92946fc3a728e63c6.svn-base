package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;

	public class RPTPanelSeriesBox extends RPTComponent {

		public function RPTPanelSeriesBox() {
			//TODO: implement function
			super();
			restoreBorder();
		}

		override public function restoreBorder():void {
			ControlUtils.setDefaultBorder(this, null);
		}

		override public function load(n:XMLNodeObject):void {
			var arr:Array=n.getChilds("series");
			var len:int=arr.length;
			for (var i:int=0; i < len; i++) {
				body.addChild(RPTComponentFactory.getFactory().getComponent(XMLNodeObject(arr[i])));
			}
		}

		override public function acceptChild(type:String):int {
			if ("textheader,columnheader".indexOf(type) >= 0) {
				return RPTComponent.CHILD;
			}
			return 0;
		}

		override public function addChildData(region:String, o:Object):void {
			var n:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject("series");
			if (o is XMLNodeObject) { //关联字段
				var o1:XMLNodeObject=XMLNodeObject(o);
				n.setAttribute("column", o1.getId());
				n.setAttribute("text", o1.getAttribute("text"));
				n.setAttribute("oldtext", o1.getAttribute("text"));
			}
			else
				n.setAttributes(o);
			getParent().getData().add(n);
			body.addChild(RPTComponentFactory.getFactory().getComponent(n));
		}
	}
}