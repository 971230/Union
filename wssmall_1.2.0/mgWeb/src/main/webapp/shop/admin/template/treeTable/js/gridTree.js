var NodeManager = jQuery.extend({
	init: function(){
		
	},
	add_node : function(param){
		
	}
}, GridEdit);


//測試demo
$(function(){
	var title = [ '本月', '上月', '本月日均', '月变动率', '去年本月', '同比增长', '本年累计' ];
	var title_code = [ 'name1', 'name2', 'name3', 'name4', 'name5', 'name6', 'name7' ];
	var titleWidth = [ '30%', '10%', '10%', '10%', '10%', '10%', '10%' ];
	var event = ['', '', '', 'NodeManager.field_edit', '', '', ''];
	var testtree = new TreeBase('aimDiv', '100%', titleWidth, title, title_code, event);
	var trobj = new TreeObj();
	
	trobj.tdValue = {'name1':'value1','name2':'value2',
			'name3':'value3','name4':'value4',
			'name5':'value5','name6':'value6',
			'name7':'value7'};
	
	trobj.tdAlt = "超链接提示信息";
	// 超链接地址
	trobj.tdLink = "http://www.baidu.com";
	// 文字的颜色,默认黑色
	trobj.tdColor = "#000000";
	// 节点id
	trobj.trId = "1";
	// 父节点id
	trobj.trPid = "-0";
	var list = [];
	list.push(trobj);
	
	var trobj11 = new TreeObj();
	trobj11.tdValue = {'name1':'value11','name2':'value21',
		'name3':'value31','name4':'value41',
		'name5':'value51','name6':'value61',
		'name7':'value71'};

	trobj11.tdAlt = "超链接提示信息";
	// 超链接地址
	trobj11.tdLink = "http://www.baidu.com";
	// 文字的颜色,默认黑色
	trobj11.tdColor = "#000000";
	// 节点id
	trobj11.trId = "4";
	// 父节点id
	trobj11.trPid = "1";
	
	list.push(trobj11);
	
	var trobj111 = new TreeObj();
	trobj111.tdValue = {'name1':'value111','name2':'value211',
		'name3':'value311','name4':'value411',
		'name5':'value511','name6':'value611',
		'name7':'value711'};

	trobj111.tdAlt = "超链接提示信息";
	// 超链接地址
	trobj111.tdLink = "http://www.baidu.com";
	// 文字的颜色,默认黑色
	trobj111.tdColor = "#000000";
	// 节点id
	trobj111.trId = "5";
	// 父节点id
	trobj111.trPid = "4";
	
	list.push(trobj111);
	
	
	var trobj2 = new TreeObj();
	trobj2.tdValue = {'name1':'value1112','name2':'value2112',
			'name3':'value3112','name4':'value4112',
			'name5':'value5112','name6':'value6112',
			'name7':'value7112'};

	trobj2.tdAlt = "超链接提示信息";
	// 超链接地址
	trobj2.tdLink = "http://www.baidu.com";
	// 文字的颜色,默认黑色
	trobj2.tdColor = "#000000";
	// 节点id
	trobj2.trId = "2";
	// 父节点id
	trobj2.trPid = "-0";
	list.push(trobj2);

	var trobj3 = new TreeObj();
	trobj3.tdValue = {'name1':'value11121','name2':'value21121',
			'name3':'value31121','name4':'value41121',
			'name5':'value51121','name6':'value61121',
			'name7':'value71121'};

	trobj3.tdAlt = "超链接提示信息";
	// 超链接地址
	trobj3.tdLink = "http://www.baidu.com";
	// 文字的颜色,默认黑色
	trobj3.tdColor = "#000000";
	// 节点id
	trobj3.trId = "3";
	// 父节点id
	trobj3.trPid = "-0";
	list.push(trobj3);
	testtree.addRows(list);
	
});
