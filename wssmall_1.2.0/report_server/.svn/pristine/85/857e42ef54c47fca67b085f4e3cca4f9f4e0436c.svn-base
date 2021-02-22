package com.ztesoft.crm.report.flex.admin {
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.view.widgets.RPTComponent;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	public class ReportChecker {
		private var app:ReportApplication;

		public function ReportChecker(app:ReportApplication) {
			//TODO: implement function
			this.app=app;
		}
		private var report:XMLNodeObject;
		private var models:XMLNodeObject;
		private var panel:XMLNodeObject;
		private var model:XMLNodeObject;

		public function check(n:XMLNodeObject):Boolean {
			this.report=n;
			this.models=n.getChild("models");
			if (this.checkPanel(n.getChilds("panel")) == false) {
				return false;
			}
			if (this.checkModels(models.getChilds("model")) == false) {
				return false;
			}
			return true;
		}

		private function checkModels(arr:Array):Boolean {
			var len:int=arr.length;
			for (var i:int=0; i < len; i++) {
				var p:XMLNodeObject=XMLNodeObject(arr[i]);
				var tabs:int=p.getChilds("table").length;
				var joins:Array=p.getChilds("join");
				if (tabs == 0) {
					return returnError("模型[" + p.getAttribute("text") + "]没有配置任何表!",
						0, p);
				}
				if (tabs == 1 && joins.length > 0) {
					return returnError("模型[" + p.getAttribute("text") + "]只有一个表，但是多出配置了Join关系!",
						0, p);
				}
				if (tabs > 1 && joins.length != (tabs - 1)) {
					return returnError("模型[" + p.getAttribute("text") + "]配置了多个表，不是所有表都关联其他表!",
						0, p);
				}
				tabs=joins.length;
				for (var n:int=0; n < tabs; n++) {
					var jo:XMLNodeObject=XMLNodeObject(joins[n]);
					var jt:XMLNodeObject=p.getChildById(jo.getAttribute("joinTable"));
					var ot:XMLNodeObject=p.getChildById(jo.getAttribute("onTable"));
					if (jo.children().length < 1) {
						return returnError("表[" + jt.getAttribute("text") + "]和表[" +
							ot.getAttribute("text") + "]之间没有配置任何关联字段!", 0, p);
					}
				}
			}
			return true;
		}

		private function checkPanel(arr:Array):Boolean {
			var len:int=arr.length;
			for (var i:int=0; i < len; i++) {
				var p:XMLNodeObject=XMLNodeObject(arr[i]);
				if (p.isEmptyAttribute("model"))
					continue;
				if (this.models.getChildById(p.getAttribute("model")) == null) {
					return returnError("红色背景的Panel设定的模型在当前报表里面找不到！", 1, p);
				}
				panel=p;
				model=models.getChildById(p.getAttribute("model"));
				if (this.checkField(p.getChilds("field")) == false) {
					return false;
				}
				if (this.checkHeader(p.getChilds("header")) == false) {
					return false;
				}
				if (this.checkMetirc(p.getChild("metric")) == false) {
					return false;
				}
				if (this.checkAxis(p.getChild("axis")) == false) {
					return false;
				}
				if (this.checkPrepareSeries(p.getChilds("series")) == false) {
					return false;
				}
				if (this.checkPrepareSeries(p.getChilds("prepareSeries")) == false) {
					return false;
				}
			}
			return true;
		}

		private function checkField(arr:Array):Boolean {
			var len:int=arr.length;
			for (var i:int=0; i < len; i++) {
				var p:XMLNodeObject=XMLNodeObject(arr[i]);
				if (p.isEmptyAttribute("name")) {
					return returnError("表单域的英文名必须写", 1, p);
				}
				var tp:String=p.getAttribute("type");
				if ("combo,checkbox,radiobox".indexOf(tp) >= 0 && p.isEmptyAttribute("attrCode")) {
					return returnError("表单域是" + tp + "类型的，必须定义静态编码！", 1, p);
				}
			}
			return true;
		}

		private function checkHeader(arr:Array):Boolean {
			var len:int=arr.length;
			for (var i:int=0; i < len; i++) {
				var p:XMLNodeObject=XMLNodeObject(arr[i]);
				if (p.isEmptyAttribute("name") && p.isEmptyAttribute("ref")) {
					return returnError("表头英文名字和关联字段都未配置，至少配置一个。", 1, p);
				}
				var r:XMLNodeObject=p.getParent("row");
				var m:XMLNodeObject=(r == null) ? null : models.getChildById(r.getAttribute("model"));
				if (m == null)
					m=this.model;
				if (m == null) {
					return returnError("表头所在的面板或者行都未定义模型！", 1, p);
				}
				if (!p.isEmptyAttribute("ref") && m.getChildById(p.getAttribute("ref")) ==
					null) {
					return returnError("表头关联的字段，并不在面板或者行定义的模型下面！", 1, p);
				}
				if (!p.isEmptyAttribute("labelRef") && m.getChildById(p.getAttribute("ref")) ==
					null) {
					return returnError("表头关联的中文字段，并不在面板或者行定义的模型下面！", 1, p);
				}
			}
			return true;
		}

		private function checkMetirc(p:XMLNodeObject):Boolean {
			if (p == null)
				return true;
			if (p.isEmptyAttribute("name") && p.isEmptyAttribute("column")) {
				return returnError("度量英文名字和关联字段都未配置，至少配置一个。", 1, p);
			}
			var m:XMLNodeObject=model;
			if (m == null) {
				return returnError("度量所在的面板未定义模型！", 1, p);
			}
			var c:XMLNodeObject=m.getChildById(p.getAttribute("column"));
			if (!p.isEmptyAttribute("column") && c == null) {
				return returnError("度量关联的字段，并不在面板定义的模型下面！", 1, p);
			}
			if (c != null && ObjectUtil.isTrue(c.getAttribute("dimension"))) {
				return returnError("度量关联的字段不能是维度字段！", 1, p);
			}
			return true;
		}

		private function checkAxis(p:XMLNodeObject):Boolean {
			if (p == null)
				return true;
			if (p.isEmptyAttribute("name") && p.isEmptyAttribute("column")) {
				return returnError("x轴英文名字和关联字段都未配置，至少配置一个。", 1, p);
			}
			var m:XMLNodeObject=model;
			if (m == null) {
				return returnError("x轴所在的面板未定义模型！", 1, p);
			}
			var c:XMLNodeObject=m.getChildById(p.getAttribute("column"));
			if (!p.isEmptyAttribute("column") && c == null) {
				return returnError("x轴关联的字段，并不在面板定义的模型下面！", 1, p);
			}
			if (c != null && !ObjectUtil.isTrue(c.getAttribute("dimension"))) {
				return returnError("x轴关联的字段必须是唯独字段！", 1, p);
			}
			c=m.getChildById(p.getAttribute("labelColumn"));
			if (!p.isEmptyAttribute("labelColumn") && c == null) {
				return returnError("x轴关联的中文字段，并不在面板定义的模型下面！", 1, p);
			}
			return true;
		}

		private function checkSeries(arr:Array):Boolean {
			var len:int=arr.length;
			for (var i:int=0; i < len; i++) {
				var p:XMLNodeObject=XMLNodeObject(arr[i]);
				if (p.isEmptyAttribute("name") && p.isEmptyAttribute("column")) {
					return returnError("序列英文名字和关联字段都未配置，至少配置一个。", 1, p);
				}
				var m:XMLNodeObject=model;
				if (m == null) {
					return returnError("序列所在的面板未定义模型！", 1, p);
				}
				var c:XMLNodeObject=m.getChildById(p.getAttribute("column"));
				if (!p.isEmptyAttribute("column") && c == null) {
					return returnError("序列关联的字段，并不在面板定义的模型下面！", 1, p);
				}
				c=m.getChildById(p.getAttribute("labelColumn"));
				if (!p.isEmptyAttribute("labelColumn") && c == null) {
					return returnError("序列关联的中文字段，并不在面板定义的模型下面！", 1, p);
				}
			}
			return true;
		}

		private function checkPrepareSeries(arr:Array):Boolean {
			var len:int=arr.length;
			for (var i:int=0; i < len; i++) {
				var p:XMLNodeObject=XMLNodeObject(arr[i]);
				if (p.isEmptyAttribute("name") && p.isEmptyAttribute("column")) {
					return returnError("双轴序列英文名字和关联字段都未配置，至少配置一个。", 1, p);
				}
				var m:XMLNodeObject=model;
				if (m == null) {
					return returnError("双轴序列所在的面板未定义模型！", 1, p);
				}
				var c:XMLNodeObject=m.getChildById(p.getAttribute("column"));
				if (!p.isEmptyAttribute("column") && c == null) {
					return returnError("双轴序列关联的字段，并不在面板定义的模型下面！", 1, p);
				}
				c=m.getChildById(p.getAttribute("labelColumn"));
				if (!p.isEmptyAttribute("labelColumn") && c == null) {
					return returnError("双轴序列关联的中文字段，并不在面板定义的模型下面！", 1, p);
				}
			}
			return true;
		}

		private function returnError(msg:String, inx:int, o:XMLNodeObject):Boolean {
			o.error=true;
			app.maintabs.selectedIndex=inx;
			if (o.getObserver() != null && (o.getObserver()is RPTComponent)) {
				RPTComponent(o.getObserver()).setErrorStyle();
			}
			MessageBox.alert(msg);
			return false;
		}
	}
}