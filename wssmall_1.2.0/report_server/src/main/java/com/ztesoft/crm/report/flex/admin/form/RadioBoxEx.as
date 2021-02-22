package com.ztesoft.crm.report.flex.admin.form {
	import flash.events.Event;

	import mx.containers.HBox;
	import mx.controls.RadioButton;
	import mx.controls.RadioButtonGroup;

	public class RadioBoxEx extends HBox implements Field {

		public function RadioBoxEx() {
			//TODO: implement function
			super();
			this.group=new RadioButtonGroup();
		}
		private var group:RadioButtonGroup;
		private var name1:String;

		public function setName(name:String):void {
			//TODO: implement function
			this.name1=name;
		}

		public function setValue(value:String):void {
			//TODO: implement function
			if (value == null)
				return;
			var len:int=this.checkboxs.length;
			var dd:Array=value.split(",");
			for (var i:int=0; i < len; i++) {
				var check:RadioButton=RadioButton(this.checkboxs[i]);
				if (dd.indexOf(check.data) >= 0) {
					check.selected=true;
				}
			}
		}

		public function getName():String {
			//TODO: implement function
			return this.name1;
		}

		public function getValue():String {
			//TODO: implement function
			var len:int=this.checkboxs.length;
			for (var i:int=0; i < len; i++) {
				var check:RadioButton=RadioButton(this.checkboxs[i]);
				if (check.selected == true) {
					return check.data as String;
				}
			}
			return "";
		}
		private var observer:FieldObserver=null;

		public function setObserver(o:FieldObserver):void {
			//TODO: implement function
			this.observer=o;
		}
		private var checkboxs:Array=[];

		public function setData(o:Object):void {
			//TODO: implement function
			var arr:Array=o as Array;
			var len:int=arr.length;
			for (var i:int=0; i < len; i++) {
				var check:RadioButton=new RadioButton();
				var item:Object=arr[i];
				check.label=item.label;
				check.data=item.value;
				this.addChild(check);
				this.checkboxs.push(check);
				check.group=this.group;
				check.addEventListener(Event.CHANGE, function(e:Event):void {
					var c:RadioButton=RadioButton(e.currentTarget);
					var f:Field=Field(c.parent);
					if (f.getObserver() != null)
						f.getObserver().notify(f.getName(), f.getValue());
				});
			}
		}

		public function getObserver():FieldObserver {
			//TODO: implement function
			return this.observer;
		}

		public function addChangeListener(e:Function):void {
		}
	}
}