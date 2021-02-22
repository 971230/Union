package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.dialog.FormDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;
	import flash.events.MouseEvent;
	import mx.containers.Box;
	import mx.controls.Label;

	public class RPTPanelField extends RPTComponent {

		public function RPTPanelField() {
			//TODO: implement function
			super();
			ControlUtils.setBorder(this, 1, "solid", 0xCCCCCC);
			this.percentWidth=100;
			this.doubleClickEnabled=true;
			this.addEventListener(MouseEvent.DOUBLE_CLICK, editProperty);
		}

		override public function restoreBorder():void {
			ControlUtils.setDefaultBorder(this, null);
		}
		private var textInput:Label;

		override public function load(n:XMLNodeObject):void {
			if (ObjectUtil.isEmpty(n))
				this.textInput.text=emptyText();
			else
				this.textInput.text=n.getAttribute("text");
			this.setData(n);
		}

		public function emptyText():String {
			return "";
		}

		override protected function renderBody(body:Box):void {
			this.textInput=new Label();
			this.textInput.percentWidth=100;
			this.textInput.percentHeight=100;
			this.textInput.text=emptyText();
			body.addChild(this.textInput);
		}

		override public function change(node:XMLNodeObject):void {
			this.load(node);
		}

		/**
		 * 关联字段
		 *
		 **/
		private function editProperty(e:MouseEvent):void {
			var f:RPTPanelField=RPTPanelField(e.currentTarget);
			var parent:XMLNodeObject=f.getParent().getData();
			if (!f.isNullData() && !f.getData().isEmptyAttribute("column")) {
				return ;
			}
			new FormDialog().open(this, getPropertiesConfig(), "编辑", function(d:XMLNodeObject):void {
					d.setAttribute("text", d.getAttribute("displayName"));
					if (f.isNullData()) {
						f.setData(d);
						parent.add(d);
					}
					else {
						f.getData().setAttributes(d.getAttributes());
					}
				});
		}
	}
}