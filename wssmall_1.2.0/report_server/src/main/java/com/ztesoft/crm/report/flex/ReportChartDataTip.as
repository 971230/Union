package com.ztesoft.crm.report.flex {
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import mx.charts.chartClasses.DataTip;

	public class ReportChartDataTip extends DataTip {

		public function ReportChartDataTip() {
			//TODO: implement function
			super();
		}

		override public function set data(value:Object):void {
			var arr:Array=[];
			for (var n:String in value) {
				arr.push(n + "=" + value[n] + "&");
			}
			super.data=arr.join("");
		}
	}
}