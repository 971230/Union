package com.ztesoft.crm.report.flex.admin {
	import com.ztesoft.crm.report.flex.admin.grid.DataGridEx;

	public class ReportDimensionMap extends DataGridEx {
		public function ReportDimensionMap() {
			//TODO: implement function
			super();
			label="静态数据";
			setDataTagName("dimension");
			//init();
		}



		public function init():void {

			this.addColumn({name: "text", label: "名称"});
			this.addColumn({name: "name", label: "编码"});
			this.addColumn({name: "dataSource", label: "数据源", type: "combo", data: ReportConstanst.DATASOURCE});
			this.addColumn({name: "content", type: "textarea", height: 300, label: "脚本", width: 5});

			this.render();
		}



	}
}