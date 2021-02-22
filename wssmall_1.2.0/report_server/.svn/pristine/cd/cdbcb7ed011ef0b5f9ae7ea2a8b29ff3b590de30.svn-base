package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import mx.containers.Box;
	import mx.controls.Label;

	public class RPTTitle extends RPTComponent {

		public function RPTTitle() {
			//TODO: implement function
			super();
		}
		private var labelTitle:Label;

		override protected function renderBody(body:Box):void {
			labelTitle=new Label();
			body.addChild(labelTitle);
			labelTitle.percentHeight=100;
			labelTitle.percentWidth=100;
		}

		override public function getPropertiesConfig():Object {
			return {cols:1, items:[{name:"content", type:"textarea", height:300}]};
		}

		override public function change(node:XMLNodeObject):void {
			this.labelTitle.htmlText=node.getContent();
		}

		override public function load(n:XMLNodeObject):void {
			setData(n);
			change(n);
		}
	}
}