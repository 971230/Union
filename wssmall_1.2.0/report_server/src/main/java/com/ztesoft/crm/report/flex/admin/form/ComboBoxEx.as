package com.ztesoft.crm.report.flex.admin.form {
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import mx.controls.ComboBox;
	import mx.events.ListEvent;

	public class ComboBoxEx extends ComboBox implements Field {

		public function ComboBoxEx() {
			//TODO: implement function
			super();
			this.labelField="label";
		}


		public function setName(name:String):void {
			//TODO: implement function
			this.name=name;
		}

		private var values:Array;

		public function setValue(value:String):void {
			//TODO: implement function
			var arr:Array=this.values;
			var len:int=arr.length;
			this.selectedIndex=-1;
			for (var i:int=0; i < len; i++) {
				if (arr[i] == null)
					continue;
				if (arr[i].value == value) {
					this.selectedIndex=i;
				}
			}
		}

		public function getName():String {
			//TODO: implement function
			return this.name;
		}

		public function getValue():String {
			//TODO: implement function
			if (ObjectUtil.isEmpty(this.selectedItem))
				return "";
			return this.selectedItem.value;
		}

		override public function get data():Object {
			return getValue();
		}

		override public function set data(value:Object):void {
			if (value == null)
				return;

			this.setValue(value as String);
		}

		override public function get value():Object {
			return this.getValue();
		}

		public function setData(o:Object):void {
			var arr:Array=[{label: "", value: ""}].concat(o as Array);
			this.dataProvider=arr
			this.values=arr;
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
			this.addEventListener(ListEvent.CHANGE, function(ev:ListEvent):void {
				e(ev);
			});
		}

	}
}