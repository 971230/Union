package com.ztesoft.crm.report.flex.admin.form {
	import flash.events.Event;

	public class FieldFactory {

		public function FieldFactory() {
			//TODO: implement function
		}

		public static function createField(type:String):Field {
			switch (type) {
				case "combo":
					var cb:ComboBoxEx=new ComboBoxEx();
					cb.addEventListener(Event.CHANGE, function(e:Event):void {
						var t:Field=Field(e.currentTarget);
						if (t.getObserver() != null)
							t.getObserver().notify(t.getName(), t.getValue());
					});
					return cb;
				case "textarea":
					var ta:TextAreaEx=new TextAreaEx();
					ta.addEventListener(Event.CHANGE, function(e:Event):void {
						var t:Field=Field(e.currentTarget);
						if (t.getObserver() != null)
							t.getObserver().notify(t.getName(), t.getValue());
					});
					return ta;
				case "htmeditor":
					var th:RichTextEditorEx=new RichTextEditorEx();
					th.addEventListener(Event.CHANGE, function(e:Event):void {
						var t:Field=Field(e.currentTarget);
						if (t.getObserver() != null)
							t.getObserver().notify(t.getName(), t.getValue());
					});
					return th;
				case "readOnly":
					var tr:TextInputEx=new TextInputEx();
					tr.editable=false;
					return tr;
				case "button":
					var tb:ButtonEx=new ButtonEx();
					return tb;
				case "checkbox":
					var tc:CheckBoxEx=new CheckBoxEx();
					return tc;
				case "radiobox":
					var trb:RadioBoxEx=new RadioBoxEx();
					return trb;
				case "label":
					var tlb:LabelEx=new LabelEx();
					return tlb;
				case "reporttree":
					var tree:ReportTreeEx=new ReportTreeEx();
					return tree;
				default:
					var ti:TextInputEx=new TextInputEx();
					ti.addEventListener(Event.CHANGE, function(e:Event):void {
						var t:Field=Field(e.currentTarget);
						if (t.getObserver() != null)
							t.getObserver().notify(t.getName(), t.getValue());
					});
					return ti;
			}
		}
	}
}