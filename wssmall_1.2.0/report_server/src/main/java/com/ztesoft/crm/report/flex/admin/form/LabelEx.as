package com.ztesoft.crm.report.flex.admin.form {
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import mx.controls.Text;

	public class LabelEx extends Text implements Field {

		public function LabelEx() {
			//TODO: implement function
			super();
		}
		private var name1:String;

		public function setName(name:String):void {
			//TODO: implement function
			this.name1=name;
		}

		public function setValue(value:String):void {
			//TODO: implement function
			if (ObjectUtil.isEmpty(value))
				return;
			this.htmlText=value;
		}

		public function getName():String {
			//TODO: implement function
			return name1;
		}

		public function getValue():String {
			//TODO: implement function
			return "";
		}

		public function setObserver(o:FieldObserver):void {
			//TODO: implement function
		}

		public function setData(o:Object):void {
			//TODO: implement function
			this.text="注意：" + (o as String);
		}

		public function getObserver():FieldObserver {
			//TODO: implement function
			return null;
		}

		public function addChangeListener(e:Function):void {
		}
	}
}