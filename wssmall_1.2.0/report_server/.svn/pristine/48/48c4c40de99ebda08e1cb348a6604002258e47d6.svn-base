package com.ztesoft.crm.report.flex.admin.elements {
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	public class ModelSQLGenerator {

		public function ModelSQLGenerator() {
		//TODO: implement function
		}

		public function toSQL(m:XMLNodeObject):String {
			var arr:Array=[];
			var joins:Array=m.getChilds("join");
			var tables:Array=m.getChilds("table");
			var len:int=joins.length;
			var i:int=0;
			var start:int=0;
			var end:int=0;
			if (len == 0)
				return toSingleTable(m.getChild("table"));
			for (i=0; i < len; i++) {
				var j:XMLNodeObject=XMLNodeObject(joins[i]);
				var joinTable:String=j.getAttribute("joinTable");
				var onTable:String=j.getAttribute("onTable");
				start=arr.indexOf(joinTable);
				end=arr.indexOf(onTable);
				if (end < 0) {
					arr.push(onTable);
					end=arr.length - 1;
				}
				if (start < 0) {
					arr.splice(end, 0, joinTable);
					continue;
				}
				else {
					if (start > end) {
						arr.splice(start, 1);
						arr.splice(end, 0, joinTable);
					}
				}
			}
			var out:Array=[];
			var ons:Array=[];
			len=arr.length;
			for (i=0; i < len; i++) {
				out.push(arr[i] + ".*");
				ons.push(getTable(m, joins, arr[i]));
			}
			return "select " + out.join(",") + " from " + ons.join("");
		}

		private function getTable(m:XMLNodeObject, joins:Array, id:String):String {
			var tab:XMLNodeObject=m.getChildById(id);
			var len:int=joins.length;
			var str:String="";
			if (ObjectUtil.isEmpty(tab.getAttribute("name"))) {
				var s:XMLNodeObject=tab.getChild("sql");
				if (s != null)
					str="(" + s.getContent() + ")";
			}
			else
				str=tab.getAttribute("name");
			str=str + " as " + id;
			var ons:Array=[];
			for (var i:int=0; i < len; i++) {
				var j:XMLNodeObject=XMLNodeObject(joins[i]);
				var joinTable:String=j.getAttribute("joinTable");
				var onTable:String=j.getAttribute("onTable");
				if (onTable == id) {
					var ll:int=j.children().length;
					str=" " + j.getAttribute("type") + " " + str;
					for (var n:int=0; n < ll; n++) {
						var oo:XMLNodeObject=XMLNodeObject(j.children()[n]);
						ons.push(onTable + "." + oo.getAttribute("column") + oo.getAttribute("operator") +
							joinTable + "." + oo.getAttribute("joinColumn"));
					}
					break;
				}
			}
			if (ons.length != 0) {
				str=str + " on " + ons.join(" and ");
			}
			return str;
		}

		private function toSingleTable(n:XMLNodeObject):String {
			if (ObjectUtil.isEmpty(n.getAttribute("name"))) {
				var s:XMLNodeObject=n.getChild("sql");
				if (s == null)
					return "";
				return s.getContent();
			}
			else
				return "select * from " + n.getAttribute("name");
		}
	}
}