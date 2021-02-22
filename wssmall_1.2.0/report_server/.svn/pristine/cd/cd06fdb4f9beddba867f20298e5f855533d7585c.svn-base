package com.ztesoft.crm.report.flex.admin.elements {
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	public class XMLNodeObjectFatory {

		public function XMLNodeObjectFatory() {
		//TODO: implement function
		}
		private static var index:int=0;
		private static var PREFFIX:String="el";
		public static var textMap:Object={};

		public static function reset():void {
			index=0;
			textMap={};
		}

		public static function setId(id:String):void {
			if (ObjectUtil.isEmpty(id)) {
				return ;
			}
			var a:String=id.replace(PREFFIX, "");
			var i:int=ObjectUtil.parseInt1(a, 0);
			if (i == 0)
				XMLNodeObjectFatory.index++;
			else {
				if (XMLNodeObjectFatory.index < i)
					XMLNodeObjectFatory.index=i;
			}
		}

		public static function getNextId():String {
			XMLNodeObjectFatory.index=XMLNodeObjectFatory.index + 1;
			return PREFFIX + XMLNodeObjectFatory.index;
		}

		public static function createXMLNodeObject(... args):XMLNodeObject {
			var n:XMLNodeObject=new XMLNodeObject();
			n.setAttribute("id", XMLNodeObjectFatory.getNextId());
			if (args != null) {
				if (args is String) {
					n.setTagName(args as String);
				}
				if (args is Array) {
					if (args.length > 0)
						n.setTagName(args[0]);
					if (args.length > 1)
						n.setAttributes(args[1]);
				}
			}
			return n;
		}
	}
}