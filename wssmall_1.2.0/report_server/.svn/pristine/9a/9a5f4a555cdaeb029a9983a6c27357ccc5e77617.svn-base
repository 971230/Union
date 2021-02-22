package com.ztesoft.crm.report.flex.admin.view.editor {
	import com.ztesoft.crm.report.flex.admin.form.ComboBoxEx;
	import com.ztesoft.crm.report.flex.message.MessageBox;

	import mx.core.ClassFactory;
	import mx.core.IFactory;

	public class CustomEditorFactory implements IFactory {

		private var cf:ClassFactory;
		private var dataProv:Array;

		public function CustomEditorFactory(a:Class, dataProvider:Array) {
			//TODO: implement function
			this.cf=new ClassFactory(a);
			this.dataProv=dataProvider;

		}

		public function newInstance():* {
			//TODO: implement function
			var el:Object=this.cf.newInstance();
			if (el is ComboBoxEx) {
				ComboBoxEx(el).setData(this.dataProv);
			}

			return el;

		}

	}
}