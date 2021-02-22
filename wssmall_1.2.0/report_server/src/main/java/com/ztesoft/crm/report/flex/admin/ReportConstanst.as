package com.ztesoft.crm.report.flex.admin {

	public class ReportConstanst {
		public static var DATASOURCE:Array=[{value: "edpdb", label: "门户生产库"}, {value: "edptest", label: "门户测试库"}];
		public static var PARAMETER_CACHE:Array=[{value: "date", label: "按日缓存"}, {value: "no", label: "实时刷新"}, {value: "always", label: "一直缓存"}];
		public static var PARAMETER_TYPE:Array=[{value: "string", label: "默认值"}, {value: "sql", label: "脚本"}, {value: "attrCode", label: "静态编码"}];
		public static var PREPARE_SERIES_REION:Array=[{value: "left", label: "左轴"}, {value: "right", label: "右轴"}];
		public static var FIELD_DATATYPE:Array=[{value: "number", label: "number"}, {value: "string", label: "string"}, {value: "date", label: "date"}, {value: "datetime", label: "datetime"}];
		public static var FORMATTYPE:Array=[{value: "number", label: "number"}, {value: "string", label: "string"}, {value: "date", label: "date"}, {value: "html", label: "html"}];
		public static var FIELD_DROPDOWN:Array=[{value: "s", label: "单选下拉"}, {value: "check", label: "多选下拉"}, {value: "tree", label: "单选下拉树"}, {value: "treecheck", label: "多选下拉树"}];
		public static var TREE_DROPDOWN:Array=[{value: "check", label: "复选"}];
		public static var FIELD_BUTTON_TYPE:Array=[{value: "submit", label: "查询按钮"}, {value: "excel", label: "导出EXCEL"}];
		public static var SQL_FUNCTION:Array=[{value: "sum", label: "sum"}, {value: "count", label: "count"}];
		public static var TRUE_OR_FALSE:Array=[{value: "true", label: "true"}, {value: "false", label: "false"}];
		public static var SQL_ORDER:Array=[{value: "desc", label: "desc"}, {value: "asc", label: "asc"}];
		public static var HEADER_ALIGN:Array=[{value: "left", label: "left"}, {value: "right", label: "right"}, {value: "center", label: "center"}];
		public static var HEADER_EVENT_TYPE:Array=[{label: "联动查询", value: "javascript:load"}, {label: "内容钻取", value: "javascript:trace"}];
		public static var HEADER_LINK_SCOPE:Array=[{label: "表头", value: "header"}, {label: "内容", value: "content"}];
		public static var REPORT_PREFFIX:String="/report";
		public static var WINDOW_TYPE:Array=[{value: "", label: "当前窗口"}, {value: "blank", label: "新窗口"}];
		public static var CHART_TYPE:Array=[{value: "column", label: "柱状图"}, {value: "line", label: "曲线图"}, {value: "pie", label: "饼图"}];

		public static var OPERATOR:Array=[{value: ">", label: "大于"}, {value: ">=", label: "大于等于"}, {value: "<", label: "小于"}, {value: "<=", label: "小于等于"}, {value: "=", label: "等于"}, {value: "in", label: "在...之中"},
			{value: "not in ", label: "在...之外"}, {value: "between", label: "在...之间"}];
		public static var ENCODING_TYPE:Array=[{value: "GBK", label: "中文(GBK)"}, {value: "US-ASCII", label: "US-ASCII"}, {value: "GB2312", label: "中文(GB2312)"}, {value: "UTF-8", label: "UTF8"}, {value: "UTF-16",
				label: "UTF16"}, {value: "ISO-8859-1", label: "ISO-8859-1"}];
		public static var THEME_TYPE:Array=[{value: "gray", label: "默认(灰色)"}, {value: "crm", label: "CRM2.0"}, {value: "channel", label: "渠道"}, {value: "ext", label: "门户(EXT暂时不用)"}];
		public static var DATABASE_TYPE:Array=[{value: "oracle", label: "ORACLE"}, {value: "sybaseiq", label: "Sybase IQ"}];

		public static var HEADER_LINK_TYPE:Array=[{value: "1", label: "事件"}, {value: "2", label: "报表链接"}, {value: "3", label: "外部链接"}];
		public static var LINK_TYPE:Array=[{value: "goback", label: "返回上页"}, {value: "click", label: "自定义事件"}];
		public static var CHART_LABEL_POSITION:Array=[{value: "inside", label: "图表内绘制标签"}, {value: "callout", label: "饼图两侧的两个垂直堆栈中绘制标签"}, {value: "outside", label: "沿饼图边界绘制标签"}, {value: "insideWithCallout",
				label: "在饼图内部或者两侧绘制标签"}];
	}
}