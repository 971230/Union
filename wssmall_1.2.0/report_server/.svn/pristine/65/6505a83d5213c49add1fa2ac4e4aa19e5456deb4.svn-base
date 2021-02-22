package com.ztesoft.crm.report.flex.admin.form {
	import mx.core.ClassFactory;

	public class FieldClassFactory extends ClassFactory {
		private var cfg:Object;

		public function FieldClassFactory(generator:Class=null, cfg:Object=null) {
			//TODO: implement function
			super(generator);
			this.cfg=cfg;
		}

		override public function newInstance():* {
			var field:Field=Field(super.newInstance());

			if (cfg != null)
				field.setData(cfg.data);
			return field;
		}
	}
}