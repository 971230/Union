package com.ztesoft.crm.report.flex.admin {

	public interface UIControl {
		/**
		 * 设置值
		 */
		function setValue(value:Object):void;
		function getValue():Object;
		/**
		 * 设置当前控件需要的数据
		 */
		function setData(data:Object):void;
	}
}