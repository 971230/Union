package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;

	import mx.core.ClassFactory;

	public class RPTComponentFactory {

		public function RPTComponentFactory() {
			//TODO: implement function
			component["box"]=RPTBox;
			component["chart"]=RPTPanelChart;
			component["simplechart"]=RPTPanelSimpleChart;
			component["crosstab"]=RPTCrosstab;
			component["listtab"]=RPTListtab;
			component["form"]=RPTForm;
			component["x"]=RPTPanelX;
			component["y"]=RPTPanelY;
			component["axis"]=RPTPanelAxis;
			component["metric"]=RPTPanelMetric;
			component["field"]=RPTFormField;
			component["header"]=RPTPanelHeader;
			component["row"]=RPTPanelRow;
			component["series"]=RPTPanelSeries;
			component["prepareSeries"]=RPTPanelPrepareSeries;
		}
		private var component:Object={};
		private static var factory:RPTComponentFactory=new RPTComponentFactory();

		public static function getFactory():RPTComponentFactory {
			return factory;
		}

		public function getComponent(... args):RPTComponent {
			var className:Class=null;
			var n:XMLNodeObject=null;
			if (args is XMLNodeObject) {
				n=XMLNodeObject(args);
			} else {
				if (args is Array) {
					n=XMLNodeObject(args[0]);
					className=args[1] as Class;
				}
			}
			if (className == null) {
				if (n.getTagName() == "panel") {
					className=component[n.getAttribute("type")];
				} else {
					className=component[n.getTagName()];
				}
				if (n.getTagName() == "box" && n.getAttribute("css") == "rpt-title") {
					className=RPTTitle;
				}
			}
			if (n == null)
				return null;
			var rpt:RPTComponent=RPTComponent(new ClassFactory(className).newInstance());
			rpt.render();
			rpt.setData(n);
			rpt.load(n);
			if (n.error == true) {
				rpt.setErrorStyle();
			}
			return rpt;
		}
	}
}