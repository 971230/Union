/**
 * TabView 配置参数
 * 
 * @return
 */
var TabOption = function() {
};
/**
 * TabView 配置参数
 */
TabOption.prototype = {
	containerId :'',// 容器ID,
	pageid :'',// pageId 页面 pageID
	cid :'',// 指定tab的id
	position :top,
	// tab位置，可为top和bottom，默认为top
	action : function(e, p) {
		 
	}
};
/**
 * Tab Item 配置参数
 * 
 * @return
 */
var TabItemOption = function() {
}
/**
 * Tab Item 配置参数
 */
TabItemOption.prototype = {
	id :'tab_',// tabId
	title :'',// tab标题
	url :'',// 该tab链接的URL
	isClosed :true
// 该tab是否可以关闭
}
/**
 * @param {}
 *            option option 可选参数 containerId tab 容器ID pageid pageId 页面 pageID
 *            cid cid tab ID
 */
function TabView(option) {
	var tab_context = {
		current :null,
		current_index :0,
		current_page :null
	};
	var op = new TabOption();
	$.extend(op, option);
	var bottom = op.position == "bottom" ? "_bottom" : "";
	this.id = op.cid;
	this.pid = op.pageid;
	this.tabs = null;
	this.tabContainer = null;
	this.ids={'page_size':0};
	var tabTemplate = '<table class="tab_item"  id="{0}" border="0" cellpadding="0" cellspacing="0"><tr>' + '<td class="tab_item1"></td>'
			+ '<td class="tab_item2 tab_title">{1}</td>' + '<td class="tab_item2"><div class="tab_close"></div></td>' + '<td class="tab_item3"></td>'
			+ '</tr></table>';
	var tabContainerTemplate = '<div class="benma_ui_tab" id="{0}"><div class="tab_hr"></div></div>';
	var page = '<iframe id="{0}" frameborder="0" width="100%" height="100%" src="{1}"></iframe>';
	if (op.position == "bottom") {
		tabTemplate = '<table class="tab_item_bottom"  id="{0}" border="0" cellpadding="0" cellspacing="0"><tr>' + '<td class="tab_item1_bottom"></td>'
				+ '<td class="tab_item2_bottom tab_title">{1}</td>' + '<td class="tab_item2_bottom"><div class="tab_close tab_close_bottom"></div></td>'
				+ '<td class="tab_item3_bottom"></td>' + '</tr></table>';
		tabContainerTemplate = '<div class="benma_ui_tab benma_ui_tab_bottom" id="{0}"><div class="tab_hr tab_hr_bottom"></div></div>';
	}
	$("#" + op.containerId).append(tabContainerTemplate.replace("{0}", this.id));
	function initTab(el) {
		var theTab = $(el);
		var tab_item1 = $(theTab).find(".tab_item1" + bottom);
		var tab_item2 = $(theTab).find(".tab_item2" + bottom);
		var tab_item3 = $(theTab).find(".tab_item3" + bottom);
		if (tab_context.current == null || tab_context.current != this) {
			$(theTab).mouseover( function() {
				tab_item1.addClass("tab_item1_mouseover" + bottom);
				tab_item2.addClass("tab_item2_mouseover" + bottom);
				tab_item3.addClass("tab_item3_mouseover" + bottom);
			}).mouseout( function() {
				tab_item1.removeClass("tab_item1_mouseover" + bottom);
				tab_item2.removeClass("tab_item2_mouseover" + bottom);
				tab_item3.removeClass("tab_item3_mouseover" + bottom);
			}).click( function() {
				if (tab_context.current != null) {
					$(tab_context.current).find(".tab_item1" + bottom).removeClass("tab_item1_selected" + bottom);
					$(tab_context.current).find(".tab_item2" + bottom).removeClass("tab_item2_selected" + bottom);
					$(tab_context.current).find(".tab_item3" + bottom).removeClass("tab_item3_selected" + bottom);
					$(tab_context.current).find(".tab_close").addClass("tab_close_noselected");
				}
				tab_item1.addClass("tab_item1_selected" + bottom);
				tab_item2.addClass("tab_item2_selected" + bottom);
				tab_item3.addClass("tab_item3_selected" + bottom);
				tab_context.current = this;
				$(tab_context.current).find(".tab_close").removeClass("tab_close_noselected");
				activate($(this).attr("id"), false);
			});
			var tab_close = $(theTab).find(".tab_close").mouseover( function() {
				$(this).addClass("tab_close_mouseover");
			}).mouseout( function() {
				$(this).removeClass("tab_close_mouseover");
			}).click( function() {
				close(theTab.attr("id"));
			});
		}
	}
	function activate(id, isAdd) {
		if (isAdd) {
			//$("#" + id).trigger("click");
		}
		if (tab_context.current_page) {
			tab_context.current_page.hide();
		}
		tab_context.current_page = $("#page_" + id);
		tab_context.current_page.show();
		op.action($("#" + id), tab_context.current_page);
	}
	function close(id) {
		var close_page = $("#page_" + id);
		var close_tab = $("#" + id);
		if ($(tab_context.current).attr("id") == close_tab.attr("id")) {
			var next = close_tab.next();
			if (next.attr("id")) {
				activate(next.attr("id"), true);
			} else {
				var pre = close_tab.prev();
				if (pre.attr("id")) {
					activate(pre.attr("id"), true);
				}
			}
		} else {
		}
		// close_page.find("iframe").remove();
		close_page.remove();
		close_tab.remove();
		delete tab.ids[id];
		tab.ids.page_size--;
	}
	this.init = function() {
		this.tabContainer = $("#" + this.id);
		this.tabs = this.tabContainer.find(".tab_item" + bottom);
		this.tabs.each( function() {
			initTab(this);
		});
	}
	this.add = function(option) {
		if(this.ids.page_size>=8){
			alert("打开栏目超过8个，请关闭部分再打开！");
			return;
		}
		var id = option.id;
		if(this.ids[id]){
			this.activate(id);
			return;
		}
		
		var op1 = new TabItemOption();
		$.extend(op1, option);
		if (op1.title.length > 10) {
			op1.title = op1.title.substring(0, 10);
		}
		if (op1.title.length < 4) {
			op1.title = "&nbsp;&nbsp;" + op1.title + "&nbsp;";
		}
		var pageHtml = page.replace("{0}", "page_" + op1.id).replace("{1}", op1.url);
		$("#" + this.pid).append(pageHtml);
		var html = tabTemplate.replace("{0}", op1.id).replace("{1}", op1.title);
		this.tabContainer.append(html);
		initTab($("#" + op1.id));
		if (!op1.isClosed) {
			$($("#" + op1.id)).find(".tab_close").hide();
		}
		this.ids[id]='1';
		this.ids.page_size++;
		// this.init();
		this.activate(op1.id);
	}
	this.update = function(option) {
		var id = option.id;
		// alert(option.url);
		$("#" + id).find(".tab_title").html(option.title);
		$("#" + id).trigger("click");
		// $("#page_" + id).find("iframe").attr("src", option.url);
		$("#page_" + id).attr("src", option.url);
		// document.getElementById()
	}
	this.activate = function(id) {
		// $("#" + id).trigger("click");
		// activate(id, true);
		$("#" + id).trigger("click");
	}
	this.close = function(id) {
		close(id);
	}
	this.init();
}
