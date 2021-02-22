package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.utils.ControlUtils;

	public class RPTPanelY extends RPTComponent {

		public function RPTPanelY() {
			//TODO: implement function
			super();
			setSides("bottom");
			percentHeight=100;
			percentWidth=100;
			this.setStyle("backgroundColor", getBackgroundColor());
			ControlUtils.setDefaultBorder(this.body, "top");
			this.toolTip="表格y轴";
			setMinSize(100, 35);
		}

		override public function getBackgroundColor():uint {
			return 0xE0E0E0;
		}

		override public function acceptChild(type:String):int {
			if (type == "panelrow")
				return RPTComponent.CHILD;
			return 0;
		}

		override public function restoreBorder():void {
			ControlUtils.setBorder(this, 0, "solid", 0xE0E0E0);
		}
	}
}