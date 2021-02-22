package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;

	public class RPTListtab extends RPTCrosstab {

		public function RPTListtab() {
			//TODO: implement function
			super();
			this.hideY();
		}

		override public function getPropertiesConfig():Object {
			var o:Object=super.getPropertiesConfig();
			o.items.push({label:"是否分页", name:"page", type:"combo", data:ReportConstanst.TRUE_OR_FALSE});
			o.items.push({label:"分页大小", name:"pageSize"});
			return o;
		}
	}
}