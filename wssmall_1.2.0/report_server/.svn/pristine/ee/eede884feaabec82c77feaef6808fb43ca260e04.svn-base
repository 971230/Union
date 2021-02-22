package com.ztesoft.crm.report.flex.utils {
	import com.ztesoft.crm.report.flex.message.MessageBox;


	public class ObjectUtil {

		public function ObjectUtil() {
			//TODO: implement function
		}

		public static function isEmpty(o:Object):Boolean {
			if (typeof(o) == "undefined")
				return true;
			if (o == null)
				return true;
			if (o is String && String(o) == "")
				return true;
			return false;
		}

		public static function isTrue(s:Object):Boolean {
			if (ObjectUtil.isEmpty(s))
				return false;
			if (s is String)
				return s == "true";
			if (s is Boolean)
				return s as Boolean;
			return false;
		}

		public static function isFalse(s:Object):Boolean {
			if (ObjectUtil.isEmpty(s))
				return false;
			if (s is String)
				return s == "false";
			if (s is Boolean)
				return !(s as Boolean);
			return false;
		}

		public static function nvl(o:Object, df:Object):Object {
			if (ObjectUtil.isEmpty(o))
				return df;
			return o;
		}

		public static function parseInt1(o:String, df:int):int {
			if (ObjectUtil.isEmpty(o))
				return df;
			return parseInt(o, 0);
		}


		public static function format(str:String, o:Object):String {
			var name:RegExp=/[{][a-zA-Z0-9_]+[}]/gi;
			return str.replace(name, function():String {
				var n:String=arguments[0];
				n=n.substr(1, n.length - 2);

				return ObjectUtil.isEmpty(o[n]) ? "" : o[n];
			});
		}
	}
}