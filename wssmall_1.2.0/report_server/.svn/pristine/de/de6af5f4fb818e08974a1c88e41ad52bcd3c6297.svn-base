package com.ztesoft.crm.report.flex.admin.form {
	import com.ztesoft.crm.report.flex.utils.ControlUtils;

	import mx.controls.TextInput;

	public class TextInputEx extends TextInput implements Field {

		public function TextInputEx() {
			//TODO: implement function
			super();
			ControlUtils.setDefaultBorder(this, null);
		}

		public function setName(name:String):void {
			//TODO: implement function
			this.name=name;
		}

		override public function get data():Object {
			return getValue();
		}

		public function setValue(value:String):void {
			//TODO: implement function
			this.text=value;
		}

		public function getName():String {
			return this.name;
		}

		override public function set data(value:Object):void {
			if (value == null)
				return;
			this.setValue(value as String);
		}

		public function getValue():String {
			return this.text;
		}

		public function setData(o:Object):void {
		}
		private var observer:FieldObserver;

		public function setObserver(o:FieldObserver):void {
			//TODO: implement function
			this.observer=o;
		}

		public function getObserver():FieldObserver {
			return this.observer;
		}

		public function addChangeListener(e:Function):void {
		}
	}
}