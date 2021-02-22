package com.ztesoft.crm.report.flex {
	import com.ztesoft.crm.report.flex.utils.ControlUtils;

	import flash.external.ExternalInterface;
	import flash.xml.XMLDocument;
	import flash.xml.XMLNode;

	import mx.charts.Legend;
	import mx.charts.PieChart;
	import mx.charts.series.PieSeries;
	import mx.containers.VBox;
	import mx.core.Application;
	import mx.events.FlexEvent;

	public class PieApplication extends Application {

		public function PieApplication() {
			//TODO: implement function
			super();
			this.addEventListener(FlexEvent.CREATION_COMPLETE, function(e:FlexEvent):void {
				ExternalInterface.addCallback("load", load);
			});
		}
		private var chart:PieChart=null;

		private function init():void {
			var v:VBox=new VBox();
			v.x=0;
			v.y=0;
			v.percentHeight=100;
			v.percentWidth=100;
			addChild(v);
			chart=new PieChart();
			chart.percentHeight=100;
			chart.percentWidth=100;
			chart.showDataTips=true;
			chart.cacheAsBitmap=true;
			v.addChild(chart);



		}

		public function load(seriers:Object, axis:String, data:String, range:String):void {
			if (this.chart == null) {
				this.init();
			}
			var ps:PieSeries=new PieSeries();
			var field:String=seriers.field;
			var name:String=seriers.displayName;
			ps.field=seriers.field;
			ps.displayName=seriers.displayName;
			ps.setStyle("labelPosition", "callout");
			ps.labelFunction=function(... args):String {
				return name + "\n" + args[0][field] + "(" + (args[3] as Number).toFixed(1) + "%)";
			};
			chart.series=[ps];
			chart.dataProvider=toArray(data, axis, field, range);
		}

		private function toArray(data:String, axis:String, field:String, range:String):Array {
			var xml:XMLDocument=new XMLDocument();
			xml.ignoreWhite=true;
			xml.parseXML(data);
			var root:XMLNode=xml.firstChild;
			var nodes:Array=root.childNodes;
			var rangeArr:Array=range.split(",");
			var dataArr:Array=[];
			for (var i:int=0; i < nodes.length; i++) {
				var n:XMLNode=nodes[i];
				var attrs:Object=n.attributes;
				if (range.indexOf(attrs[axis]) < 0)
					continue;
				var o:Object={};
				o[field]=attrs[field];
				o[axis]=attrs[axis];
				dataArr.push(o);
			}
			return dataArr;
		}
	}
}