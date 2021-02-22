var Eop = Eop || {};
var Ztp = Ztp || {};


$.ajaxSetup({
			cache : false
		});
var basePath = "";
Ztp.AUI = {
	opts : undefined,
	init : function(a) {
		if (typeof a == "string") {
			this.opts = {};
			this.opts.wrapper = $(a)
			this.opts.rightWrapper = $(a)
		}
		if (typeof a == "object") {
			this.opts = a
		}
	},
	load : function(b,d) {
		/**
		 * 主页面样式 加载时重新给 在加载不需要左侧菜单的main_frame_none_menu.jsp中 取消掉了这个样式
		 */
		var imgUrl = app_path + "/mgWebthemes"+theme_source_from+"/default/images/mainbg.gif"
		$(".mainContent").css("background","url("+imgUrl+") repeat-y");
		$("body").css("background","url("+imgUrl+") repeat-y");
		
		try{
			if(BackendUi.backUrl.length ==0){
			 	$("#back_a").length>0 && $("#back_a").hide();
			}
		}catch(e){}
		Eop.Help.close();
		if (Eop.onRedirect) {
			if (!Eop.onRedirect()) {
				return false
			}
			Eop.onRedirect = undefined
		}
		
		var iframeall = b.attr("iframeall");
		var c = b.attr("target");
		var a = b.attr("href");
		var id = b.attr("app_menu_id");
		if (iframeall == "yes" && !d && c != "noneMenu") {
			this.loadUrlInFrmAll(b);
			return false
		}
		
		if (c) {
			
			if (c == "_self") {
				location.href = a;
				return true
			}
			if (c == "ajax") {
				a = a.replace("http://" + location.hostname, "");
				a = a.replace(":" + location.port, "");
				this.loadUrl(a);
				return false
			}
			if (c == "iframe") {
				this.loadUrlInFrm(a,id);
				return false
			}
			
			if(c == "all"){
				this.loadUrlInFrmAll(b);
				return false
			}
			
			if (c == "dwr") {
				this.loadUrlInFrmDwr(a);
				return false
			}
			
			if(c == "noneMenu"){
				this.loadUrlInFrmNMenu(b);
				return false
			}
			
			if (c == "_blank") {
				return true
			}
			if (c == "_top") {
				return true
			}
			this.loadUrlInFrm(a,id)
		} else {
			this.loadUrlInFrm(a,id)
		}
		return false
	},
	loadUrlInFrm : function(a,id) {
		try{
		$("#right_content > :not(iframe)").remove();//$(".mainContent").find(".main_all_iframe").hide();
			$("#right_content").find(".auto_frame").hide();
		var appMenu = $("#right_content").find(".auto_frame[app_menu_id='"+id+"']");
		if(appMenu.size()>0){
			appMenu.get(0).style.display = "block";
		}else{
			var topHeight ;
			try {
				topHeight = window.top.document.documentElement.clientHeight;
			} catch(e) {
				topHeight =$(window).height();
			}
			var height;
			var bottomHeight = 0;
//			if($(".title_new",window.top.document).length>0){
//				bottomHeight = 30;
//			}
			//计算顶部菜单高度，不同环境顶部不同
			var top_menu = 0;
			if(theme_source_from == "ECS"){
				top_menu = 100;
			}else{
				top_menu = 140;
			}
			height = topHeight - bottomHeight - top_menu;
			
			if($('.topContent').html()!=null){
				if(!$('.topContent', parent.document).is(":visible")){
					if($('.toolbar-box', parent.document).is(":visible")){
						height=topHeight-($(".toolbar-box", parent.document).height()+$(".topBar", parent.document).height()+$(".gridSper", parent.document).height()+$(".title_new", parent.document).height());
					}else{
						height=topHeight-($(".topBar", parent.document).height()+$(".gridSper", parent.document).height()+$(".title_new", parent.document).height());
					}
				}else{
					height=topHeight-bottomHeight-$('.topContent', parent.document).height()-$('.top2', parent.document).height()-$('.topBar', parent.document).height()-$('.gridSper', parent.document).height();
				}
			}else{
				var parentDocument;
				try{
					parentDocument = parent.parent.document;
				}catch(e){
					parentDocument = $(document).context;
				}
				if(!$('.topContent', parentDocument).is(":visible")){
					if($('.toolbar-box', parentDocument).is(":visible")){
						height=topHeight-($(".toolbar-box", parentDocument).height()+$(".topBar", parentDocument).height()+$(".gridSper", parentDocument).height()+$(".title_new", parentDocument).height());
					}else{
						height=topHeight-($(".topBar", parentDocument).height()+$(".gridSper", parentDocument).height()+$(".title_new", parentDocument).height());
					}
				}else{
					height=topHeight-bottomHeight-$('.topContent', parentDocument).height()-$('.top2', parentDocument).height()-$('.topBar', parentDocument).height()-$('.gridSper', parentDocument).height();
				}
			}
			
			var frame = $("<iframe app_menu_id='"+id+"' class='auto_frame' width='100%' height='100%'  frameborder='0' ></iframe>");
			frame.css("height",height+"px");
			frame.appendTo($("#right_content")).load(function() {});
			//缓存处理
			if(a.indexOf("?")>-1)
				 a +="&version="+new Date();
			else
				 a +="?version="+new Date();
			frame.attr("src", a);
		}
		
		}catch(e){}
	},
	loadUrlInFrmAll : function(b) {
		try{
			
		var a = b.attr("href");
		
		this.opts.wrapper.find(".main_all_iframe").hide();
		//浏览器窗口栏高度
		var topHeight = window.top.document.documentElement.clientHeight;
		var height;
		var bottomHeight = 0;
		if($(".title_new",window.top.document).length>0){
			bottomHeight = 30;
		}
		//计算顶部菜单高度，不同环境顶部不同
		var top_menu = 0;
		if(theme_source_from == "ECS"){
			top_menu = 95;
		}else{
			top_menu = 140;
		}
		height = topHeight - bottomHeight - top_menu;
		//add "if-else" by liqingyi
		if(!$('.topContent', parent.document).is(":visible")){
			if($('.toolbar-box', parent.document).is(":visible")){
				height=topHeight-($(".toolbar-box", parent.document).height()+$(".topBar", parent.document).height()+$(".gridSper", parent.document).height()+$(".title_new", parent.document).height());
			}else{
				height=topHeight-($(".topBar", parent.document).height()+$(".gridSper", parent.document).height()+$(".title_new", parent.document).height());
			}
		}else{
			height=topHeight-bottomHeight-$('.topContent', parent.document).height()-$('.top2', parent.document).height()-$('.topBar', parent.document).height()-$('.gridSper', parent.document).height();
		}
		this.opts.wrapper.height(height+3);
		
		var app_menu_id = b.attr("app_menu_id");
		if(this.opts.wrapper.find(".main_all_iframe[app_menu_id='"+app_menu_id+"']").length > 0){
			this.opts.wrapper.find(".main_all_iframe[app_menu_id='"+app_menu_id+"']").remove();
		}
		
		this.opts.frm = $("<iframe  app_menu_id='"+app_menu_id+"' class='main_all_iframe' width='100%' height='100%' frameborder='0' ></iframe>");
		this.opts.frm.appendTo(this.opts.wrapper);
		//缓存处理
		
		if(a.indexOf("?")>-1)
			a +="&version="+new Date();
		else
			a +="?version="+new Date();
		
		a+="&iframeall=yes";
		this.opts.frm.attr("src", a);
		}catch(e){}
	},
	loadUrlInFrmNMenu:function(b){
		try{
			
			//调整样式，不显示左侧菜单的，去掉底部横线
			$(".mainContent").css("background","none");
			$("body").css("background","none");
			
			var a = b.attr("href");
			this.opts.wrapper.find(".main_all_iframe").hide();
			
			var app_menu_id = b.attr("app_menu_id");
			if(this.opts.wrapper.find(".main_all_iframe[app_menu_id='"+app_menu_id+"']").length > 0){
				this.opts.wrapper.find(".main_all_iframe[app_menu_id='"+app_menu_id+"']").remove();
			}
			
			
			var frame = $("<iframe  app_menu_id='"+app_menu_id+"' class='main_all_iframe' width='100%' height='100%' frameborder='0' ></iframe>");
			frame.appendTo(this.opts.wrapper).load(function(){});
			//缓存处理
			
			if(a.indexOf("?")>-1)
				 a +="&version="+new Date();
			else
				 a +="?version="+new Date();
			
			a += "&noneMenu=yes";
			frame.attr("src", a);
			}catch(e){}
	},
	loadUrlInFrmByUrl : function(url,wrapper) {
		
		try{
			$.Loading.show("正在加载所需内容，请稍侯...");
			
			wrapper.empty();
			var frm = $("<iframe id='flow_frame' width='100%' height='100%' frameborder='0' style='' ></iframe>");
			frm.appendTo(wrapper).load(function() {
				$.Loading.hide()
			});
			//缓存处理
			if(url.indexOf("?")>-1)
				 url +="&version="+new Date();
			else
				 url +="?version="+new Date();
			
			frm.attr("src", url);
		}catch(e){}
	},
	loadUrlInFrmDwr : function(a) { //后端dwr改造
		$.Loading.show("正在加载所需内容，请稍侯...");
		this.opts.wrapper.empty();
		this.opts.frm = $("<iframe id='auto_created_frame' width='100%' height='100%' frameborder='0' ></iframe>");
		this.opts.frm.appendTo(this.opts.wrapper).load(function() {
					$.Loading.hide()
				});
		this.opts.frm.attr("src", a)
	}, 
	loadUrl : function(c, a) {
		$.Loading.show("正在加载所需内容，请稍侯...");
		basePath = c.substring(0, c.lastIndexOf("/") + 1);
		var b = this;
		$.ajax({
					type : "GET",
					url : c,
					data : "ajax=yes&rmd=" + new Date().getTime(),
					dataType : "html",
					success : function(d) {
						b.opts.wrapper.empty().append($(d));
						if (a) {
							a()
						}
						b.requestProxy();
						$.Loading.hide();
						Eop.Help.init()
					},
					error : function(d) {
						$.Loading.hide()
					}
				})
	},
	requestProxy : function() {
		var b = this;
		this.opts.wrapper.find("a").click(function() {
					if ($(this).attr("target") == "_blank") {
						return true
					}
					if ($(this).attr("target") == "_top") {
						return true
					}
					var c = $(this).attr("href");
					c = c.substring(c.lastIndexOf("/") + 1, c.length);
					if (c != "javascript:;") {
						loadUrl(c);
						return false
					}
					return true
				});
		var a = this.opts.wrapper.find("form");
		a.append("<input type='hidden' name='ajax' value='yes' />");
		a.submit(function() {
					if ("false" == $(this).attr("validate")) {
						return false
					}
					var d = $(this).attr("method");
					if (!d) {
						d = "POST"
					}
					$.Loading.show("正在提交数据...");
					var c = {
						url : basePath + a.attr("action"),
						type : d,
						dataType : "html",
						success : function(e) {
							b.opts.wrapper.html(e);
							b.requestProxy();
							Eop.onRedirect = undefined;
							$.Loading.hide();
							$.Loading.text = undefined
						},
						error : function(f) {
							$.Loading.hide()
						}
					};
					$(this).ajaxSubmit(c);
					return false
				})
	}
};
Eop.Dialog = {
	defaults : {
		modal : true
	},
	init : function(b) {
		this.opts = $.extend({}, this.defaults, b);
		var a = this;
		if ($("#dlg_" + a.opts.id).size() == 0) {
			var c = '<div  class="dialogIf" style="display:none;"><div class="dialog_box">';
			c += '<div class="head">';
			c += '<div class="title">' + this.opts.title + "</div>";
			c += '<span class="closeBtn"></span>';
			c += "</div>";
			c += '<div class="body dialogContent"></div>';
			c += '<div class="head" style="height:3px;background:#CAD9E6;cursor:move;"></div>';
			c += "</div>";
			c += "</div>";
			a.dialog = $(c);
			a.dialog.appendTo("body");
			a.dialog.attr("id", "dlg_" + a.opts.id)
		} else {
			a.dialog = $("#dlg_" + a.opts.id)
		}
		a.dialog.css("width", a.opts.width);
		
		var temp = $("#" + a.opts.id);
		
		a.dialog.find(".dialogContent").empty().append(temp);
		a.dialog.jqDrag(".head").jqm(this.opts).jqmAddClose(".closeBtn")
	},
	open : function(a) {
		
		var t = document.documentElement.scrollTop || document.body.scrollTop; 
		
		if (a) {
			$("#dlg_" + a).css("margin-top",t+50);
			$("#dlg_" + a).jqmShow()
			
		} else {
			$("#dlg_" + a).css("margin-top",t+50);
			this.dialog.jqmShow()
		}
	},
	close : function(a) {
		if (a) {
			$("#dlg_" + a).jqmHide()
		} else {
			this.dialog.jqmHide()
		}
	}
};
function loadScript(a) {
	$.ajax({
				type : "GET",
				url : basePath + a,
				dataType : "script"
			})
}
function loadUrl(a) {
	a = basePath + a;
	Ztp.AUI.loadUrl(a)
}
Eop.Help = {
	init : function() {
		$("#HelpClose ").click(function() {
					$("#HelpCtn").hide()
				});
		$(".help_icon").click(function(b) {
			var a = $(this).attr("helpid");
			var c = b.pageY - 22;
			$("#HelpCtn").css("top", c).css("left", b.pageX + 10).show();
			$("#HelpBody").html("正在加载...");
			$("#HelpBody").load(app_path
					+ "/core/admin/help.do?ajax=yes&helpid=" + a)
		})
	},
	close : function() {
		$("#HelpCtn").hide()
	}
};
Eop.InputFile = {
	init : function() {
	
		$("input[type='file']").each(function(){
			var tmp = $(this).next() ;
			if(tmp.val() == '清空')
				return ;
			$(this).after("&nbsp;<input type='button' value='清空'/>").next("input")
				.click(function() {
							var d = (navigator.appVersion.indexOf("MSIE") != -1);
							var a = (navigator.userAgent.indexOf("Firefox") != -1);
							if (d) {
//								var b = $(this).prev("input").attr("name");
//								var c = b.cloneNode(false);
//								c.onchange = b.onchange;
//								b.parentNode.replaceChild(c, b)
								
								//add by chen.lijun 上面的方法在IE下有问题
								var obj = $(this).prev("input");
								obj.select();
							    document.execCommand("delete");
							} else {
								$(this).prev("input").attr("value", "")
							}
						})
		}) ;
				
	}
};
$(function() {
			Eop.Help.init();
			Eop.InputFile.init()
		});
(function(d) {
	d.fn.jqm = function(f) {
		var e = {
			overlay : 50,
			overlayClass : "jqmOverlay",
			closeClass : "jqmClose",
			trigger : ".jqModal",
			ajax : o,
			ajaxText : "",
			target : o,
			modal : o,
			toTop : o,
			onShow : o,
			onHide : o,
			onLoad : o
		};
		return this.each(function() {
					if (this._jqm) {
						return n[this._jqm].c = d.extend({}, n[this._jqm].c, f)
					}
					p++;
					this._jqm = p;
					n[p] = {
						c : d.extend(e, d.jqm.params, f),
						a : o,
						w : d(this).addClass("jqmID" + p),
						s : p
					};
					if (e.trigger) {
						d(this).jqmAddTrigger(e.trigger)
					}
				})
	};
	d.fn.jqmAddClose = function(f) {
		return l(this, f, "jqmHide")
	};
	d.fn.jqmAddTrigger = function(f) {
		return l(this, f, "jqmShow")
	};
	d.fn.jqmShow = function(e) {
		return this.each(function() {
					e = e || window.event;
					d.jqm.open(this._jqm, e)
				})
	};
	d.fn.jqmHide = function(e) {
		return this.each(function() {
					if(typeof(e)!="undefined" && e!=null){
						if($(e).is(":visible"))
							d.jqm.close(this._jqm, e);
					}else{
						e = window.event;
						d.jqm.close(this._jqm, e);
					}
				})
	};
	d.jqm = {
		hash : {},
		open : function(B, A) {
			var m = n[B], q = m.c, i = "." + q.closeClass, v = (parseInt(m.w
					.css("z-index"))), v = (v > 0) ? v : 3000, f = d("<div></div>")
					.css({
								height : "100%",
								width : "100%",
								position : "fixed",
								left : 0,
								top : 0,
								"z-index" : v - 1,
								opacity : q.overlay / 100
							});
			if (m.a) {
				return o
			}
			m.t = A;
			m.a = true;
			m.w.css("z-index", v);
			if (q.modal) {
				if (!a[0]) {
					k("bind")
				}
				a.push(B)
			} else {
				if (q.overlay > 0) {
					m.w.jqmAddClose(f)
				} else {
					f = o
				}
			}
			m.o = (f) ? f.addClass(q.overlayClass).prependTo("body") : o;
			if (c) {
				d("html,body").css({
							height : "100%",
							width : "100%"
						});
				if (f) {
					f = f.css({
								position : "absolute"
							})[0];
					for (var w in {
						Top : 1,
						Left : 1
					}) {
						f.style.setExpression(w.toLowerCase(),
								"(_=(document.documentElement.scroll" + w
										+ " || document.body.scroll" + w
										+ "))+'px'")
					}
				}
			}
			if (q.ajax) {
				var e = q.target || m.w, x = q.ajax, e = (typeof e == "string")
						? d(e, m.w)
						: d(e), x = (x.substr(0, 1) == "@") ? d(A).attr(x
						.substring(1)) : x;
				e.html(q.ajaxText).load(x, function() {
							if (q.onLoad) {
								q.onLoad.call(this, m)
							}
							if (i) {
								m.w.jqmAddClose(d(i, m.w))
							}
							j(m)
						})
			} else {
				if (i) {
					m.w.jqmAddClose(d(i, m.w))
				}
			}
			if (q.toTop && m.o) {
				m.w.before('<span id="jqmP' + m.w[0]._jqm + '"></span>')
						.insertAfter(m.o)
			}
			(q.onShow) ? q.onShow(m) : m.w.show();
			j(m);
			return o
		},
		close : function(f) {
			var e = n[f];
			if (!e.a) {
				return o
			}
			e.a = o;
			if (a[0]) {
				a.pop();
				if (!a[0]) {
					k("unbind")
				}
			}
			if (e.c.toTop && e.o) {
				d("#jqmP" + e.w[0]._jqm).after(e.w).remove()
			}
			if (e.c.onHide) {
				e.c.onHide(e)
			} else {
				e.w.hide();
				if (e.o) {
					e.o.remove()
				}
			}
			return o
		},
		params : {}
	};
	var p = 0, n = d.jqm.hash, a = [], c = d.browser.msie
			&& (d.browser.version == "6.0"), o = false, g = d('<iframe src="javascript:false;document.write(\'\');" class="jqm"></iframe>')
			.css({
						opacity : 0
					}), j = function(e) {
		if (c) {
			if (e.o) {
				e.o.html('<p style="width:100%;height:100%"/>').prepend(g)
			} else {
				if (!d("iframe.jqm", e.w)[0]) {
					e.w.prepend(g)
				}
			}
		}
		h(e)
	}, h = function(f) {
		try {
			d(":input:visible", f.w)[0].focus()
		} catch (e) {
		}
	}, k = function(e) {
		d()[e]("keypress", b)[e]("keydown", b)[e]("mousedown", b)
	}, b = function(m) {
		var f = n[a[a.length - 1]], i = (!d(m.target).parents(".jqmID" + f.s)[0]);
		if (i) {
			h(f)
		}
		return !i
	}, l = function(e, f, i) {
		return e.each(function() {
					var m = this._jqm;
					d(f).each(function() {
								if (!this[i]) {
									this[i] = [];
									d(this).click(function() {
												for (var q in {
													jqmShow : 1,
													jqmHide : 1
												}) {
													for (var r in this[q]) {
														if (n[this[q][r]]) {
															n[this[q][r]].w[q](this)
														}
													}
												}
												return o
											})
								}
								this[i].push(m)
							})
				})
	}
})(jQuery);
(function(e) {
	e.fn.jqDrag = function(f) {
		return b(this, f, "d")
	};
	e.fn.jqResize = function(f) {
		return b(this, f, "r")
	};
	e.jqDnR = {
		dnr : {},
		e : 0,
		drag : function(f) {
			if (g.k == "d") {
				d.css({
							left : g.X + f.pageX - g.pX,
							top : g.Y + f.pageY - g.pY
						})
			} else {
				d.css({
							width : Math.max(f.pageX - g.pX + g.W, 0),
							height : Math.max(f.pageY - g.pY + g.H, 0)
						})
			}
			return false
		},
		stop : function() {
			d.css("opacity", g.o);
			e().unbind("mousemove", a.drag).unbind("mouseup", a.stop)
		}
	};
	var a = e.jqDnR, g = a.dnr, d = a.e, b = function(j, i, f) {
		return j.each(function() {
					i = (i) ? e(i, j) : j;
					i.bind("mousedown", {
								e : j,
								k : f
							}, function(h) {
								var m = h.data, l = {};
								d = m.e;
								if (d.css("position") != "relative") {
									try {
										d.position(l)
									} catch (k) {
									}
								}
								g = {
									X : l.left || c("left") || 0,
									Y : l.top || c("top") || 0,
									W : c("width") || d[0].scrollWidth || 0,
									H : c("height") || d[0].scrollHeight || 0,
									pX : h.pageX,
									pY : h.pageY,
									k : m.k,
									o : d.css("opacity")
								};
								d.css({
											opacity : 0.8
										});
								e().mousemove(e.jqDnR.drag)
										.mouseup(e.jqDnR.stop);
								return false
							})
				})
	}, c = function(f) {
		return parseInt(d.css(f)) || false
	}
})(jQuery);
(function(g) {
	var a = g.fn.height, e = g.fn.width;
	g.fn.extend({
		height : function() {
			if (!this[0]) {
				d()
			}
			if (this[0] == window) {
				if (g.browser.opera
						|| (g.browser.safari && parseInt(g.browser.version) > 520)) {
					return self.innerHeight
							- ((g(document).height() > self.innerHeight)
									? b()
									: 0)
				} else {
					if (g.browser.safari) {
						return self.innerHeight
					} else {
						return g.boxModel
								&& document.documentElement.clientHeight
								|| document.body.clientHeight
					}
				}
			}
			if (this[0] == document) {
				return Math
						.max(
								(g.boxModel
										&& document.documentElement.scrollHeight || document.body.scrollHeight),
								document.body.offsetHeight)
			}
			return a.apply(this, arguments)
		},
		width : function() {
			if (!this[0]) {
				d()
			}
			if (this[0] == window) {
				if (g.browser.opera
						|| (g.browser.safari && parseInt(g.browser.version) > 520)) {
					return self.innerWidth
							- ((g(document).width() > self.innerWidth)
									? b()
									: 0)
				} else {
					if (g.browser.safari) {
						return self.innerWidth
					} else {
						return g.boxModel
								&& document.documentElement.clientWidth
								|| document.body.clientWidth
					}
				}
			}
			if (this[0] == document) {
				if (g.browser.mozilla) {
					var j = self.pageXOffset;
					self.scrollTo(99999999, self.pageYOffset);
					var i = self.pageXOffset;
					self.scrollTo(j, self.pageYOffset);
					return document.body.offsetWidth + i
				} else {
					return Math
							.max(
									((g.boxModel && !g.browser.safari)
											&& document.documentElement.scrollWidth || document.body.scrollWidth),
									document.body.offsetWidth)
				}
			}
			return e.apply(this, arguments)
		},
		innerHeight : function() {
			if (!this[0]) {
				d()
			}
			return this[0] == window || this[0] == document
					? this.height()
					: this.is(":visible") ? this[0].offsetHeight
							- c(this, "borderTopWidth")
							- c(this, "borderBottomWidth") : this.height()
							+ c(this, "paddingTop") + c(this, "paddingBottom")
		},
		innerWidth : function() {
			if (!this[0]) {
				d()
			}
			return this[0] == window || this[0] == document
					? this.width()
					: this.is(":visible") ? this[0].offsetWidth
							- c(this, "borderLeftWidth")
							- c(this, "borderRightWidth") : this.width()
							+ c(this, "paddingLeft") + c(this, "paddingRight")
		},
		outerHeight : function(i) {
			if (!this[0]) {
				d()
			}
			i = g.extend({
						margin : false
					}, i || {});
			return this[0] == window || this[0] == document
					? this.height()
					: this.is(":visible") ? this[0].offsetHeight
							+ (i.margin ? (c(this, "marginTop") + c(this,
									"marginBottom")) : 0) : this.height()
							+ c(this, "borderTopWidth")
							+ c(this, "borderBottomWidth")
							+ c(this, "paddingTop")
							+ c(this, "paddingBottom")
							+ (i.margin ? (c(this, "marginTop") + c(this,
									"marginBottom")) : 0)
		},
		outerWidth : function(i) {
			if (!this[0]) {
				d()
			}
			i = g.extend({
						margin : false
					}, i || {});
			return this[0] == window || this[0] == document
					? this.width()
					: this.is(":visible") ? this[0].offsetWidth
							+ (i.margin ? (c(this, "marginLeft") + c(this,
									"marginRight")) : 0) : this.width()
							+ c(this, "borderLeftWidth")
							+ c(this, "borderRightWidth")
							+ c(this, "paddingLeft")
							+ c(this, "paddingRight")
							+ (i.margin ? (c(this, "marginLeft") + c(this,
									"marginRight")) : 0)
		},
		scrollLeft : function(i) {
			if (!this[0]) {
				d()
			}
			if (i != undefined) {
				return this.each(function() {
							if (this == window || this == document) {
								window.scrollTo(i, g(window).scrollTop())
							} else {
								this.scrollLeft = i
							}
						})
			}
			if (this[0] == window || this[0] == document) {
				return self.pageXOffset || g.boxModel
						&& document.documentElement.scrollLeft
						|| document.body.scrollLeft
			}
			return this[0].scrollLeft
		},
		scrollTop : function(i) {
			if (!this[0]) {
				d()
			}
			if (i != undefined) {
				return this.each(function() {
							if (this == window || this == document) {
								window.scrollTo(g(window).scrollLeft(), i)
							} else {
								this.scrollTop = i
							}
						})
			}
			if (this[0] == window || this[0] == document) {
				return self.pageYOffset || g.boxModel
						&& document.documentElement.scrollTop
						|| document.body.scrollTop
			}
			return this[0].scrollTop
		},
		position : function(i) {
			return this.offset({
						margin : false,
						scroll : false,
						relativeTo : this.offsetParent()
					}, i)
		},
		offset : function(j, p) {
			if (!this[0]) {
				d()
			}
			var o = 0, n = 0, z = 0, s = 0, A = this[0], m = this[0], l, i, w = g
					.css(A, "position"), v = g.browser.mozilla, q = g.browser.msie, u = g.browser.opera, C = g.browser.safari, k = g.browser.safari
					&& parseInt(g.browser.version) > 520, r = false, t = false, j = g
					.extend({
								margin : true,
								border : false,
								padding : false,
								scroll : true,
								lite : false,
								relativeTo : document.body
							}, j || {});
			if (j.lite) {
				return this.offsetLite(j, p)
			}
			if (j.relativeTo.jquery) {
				j.relativeTo = j.relativeTo[0]
			}
			if (A.tagName == "BODY") {
				o = A.offsetLeft;
				n = A.offsetTop;
				if (v) {
					o += c(A, "marginLeft") + (c(A, "borderLeftWidth") * 2);
					n += c(A, "marginTop") + (c(A, "borderTopWidth") * 2)
				} else {
					if (u) {
						o += c(A, "marginLeft");
						n += c(A, "marginTop")
					} else {
						if ((q && jQuery.boxModel)) {
							o += c(A, "borderLeftWidth");
							n += c(A, "borderTopWidth")
						} else {
							if (k) {
								o += c(A, "marginLeft")
										+ c(A, "borderLeftWidth");
								n += c(A, "marginTop") + c(A, "borderTopWidth")
							}
						}
					}
				}
			} else {
				do {
					i = g.css(m, "position");
					o += m.offsetLeft;
					n += m.offsetTop;
					if ((v && !m.tagName.match(/^t[d|h]$/i)) || q || k) {
						o += c(m, "borderLeftWidth");
						n += c(m, "borderTopWidth");
						if (v && i == "absolute") {
							r = true
						}
						if (q && i == "relative") {
							t = true
						}
					}
					l = m.offsetParent || document.body;
					if (j.scroll || v) {
						do {
							if (j.scroll) {
								z += m.scrollLeft;
								s += m.scrollTop
							}
							if (u
									&& (g.css(m, "display") || "")
											.match(/table-row|inline/)) {
								z = z
										- ((m.scrollLeft == m.offsetLeft)
												? m.scrollLeft
												: 0);
								s = s
										- ((m.scrollTop == m.offsetTop)
												? m.scrollTop
												: 0)
							}
							if (v && m != A
									&& g.css(m, "overflow") != "visible") {
								o += c(m, "borderLeftWidth");
								n += c(m, "borderTopWidth")
							}
							m = m.parentNode
						} while (m != l)
					}
					m = l;
					if (m == j.relativeTo
							&& !(m.tagName == "BODY" || m.tagName == "HTML")) {
						if (v && m != A && g.css(m, "overflow") != "visible") {
							o += c(m, "borderLeftWidth");
							n += c(m, "borderTopWidth")
						}
						if (((C && !k) || u) && i != "static") {
							o -= c(l, "borderLeftWidth");
							n -= c(l, "borderTopWidth")
						}
						break
					}
					if (m.tagName == "BODY" || m.tagName == "HTML") {
						if (((C && !k) || (q && g.boxModel)) && w != "absolute"
								&& w != "fixed") {
							o += c(m, "marginLeft");
							n += c(m, "marginTop")
						}
						if (k || (v && !r && w != "fixed")
								|| (q && w == "static" && !t)) {
							o += c(m, "borderLeftWidth");
							n += c(m, "borderTopWidth")
						}
						break
					}
				} while (m)
			}
			var B = h(A, j, o, n, z, s);
			if (p) {
				g.extend(p, B);
				return this
			} else {
				return B
			}
		},
		offsetLite : function(q, l) {
			if (!this[0]) {
				d()
			}
			var n = 0, m = 0, k = 0, p = 0, o = this[0], j, q = g.extend({
						margin : true,
						border : false,
						padding : false,
						scroll : true,
						relativeTo : document.body
					}, q || {});
			if (q.relativeTo.jquery) {
				q.relativeTo = q.relativeTo[0]
			}
			do {
				n += o.offsetLeft;
				m += o.offsetTop;
				j = o.offsetParent || document.body;
				if (q.scroll) {
					do {
						k += o.scrollLeft;
						p += o.scrollTop;
						o = o.parentNode
					} while (o != j)
				}
				o = j
			} while (o && o.tagName != "BODY" && o.tagName != "HTML"
					&& o != q.relativeTo);
			var i = h(this[0], q, n, m, k, p);
			if (l) {
				g.extend(l, i);
				return this
			} else {
				return i
			}
		},
		offsetParent : function() {
			if (!this[0]) {
				d()
			}
			var i = this[0].offsetParent;
			while (i
					&& (i.tagName != "BODY" && g.css(i, "position") == "static")) {
				i = i.offsetParent
			}
			return g(i)
		}
	});
	var d = function() {
		//throw "Dimensions: jQuery collection is empty"
	};
	var c = function(i, j) {
		return parseInt(g.css(i.jquery ? i[0] : i, j)) || 0
	};
	var h = function(m, l, j, n, i, k) {
		if (!l.margin) {
			j -= c(m, "marginLeft");
			n -= c(m, "marginTop")
		}
		if (l.border
				&& ((g.browser.safari && parseInt(g.browser.version) < 520) || g.browser.opera)) {
			j += c(m, "borderLeftWidth");
			n += c(m, "borderTopWidth")
		} else {
			if (!l.border
					&& !((g.browser.safari && parseInt(g.browser.version) < 520) || g.browser.opera)) {
				j -= c(m, "borderLeftWidth");
				n -= c(m, "borderTopWidth")
			}
		}
		if (l.padding) {
			j += c(m, "paddingLeft");
			n += c(m, "paddingTop")
		}
		if (l.scroll
				&& (!g.browser.opera || m.offsetLeft != m.scrollLeft
						&& m.offsetTop != m.scrollLeft)) {
			i -= m.scrollLeft;
			k -= m.scrollTop
		}
		return l.scroll ? {
			top : n - k,
			left : j - i,
			scrollTop : k,
			scrollLeft : i
		} : {
			top : n,
			left : j
		}
	};
	var f = 0;
	var b = function() {
		if (!f) {
			var i = g("<div>").css({
						width : 100,
						height : 100,
						overflow : "auto",
						position : "absolute",
						top : -1000,
						left : -1000
					}).appendTo("body");
			f = 100 - i.append("<div>").find("div").css({
						width : "100%",
						height : 200
					}).width();
			i.remove()
		}
		return f
	}
})(jQuery);
var Eop = Eop || {};
Eop.Grid = {
	defauts : {
		idChkName : "id",
		toggleChkId : "toggleChk"
	},
	opation : function(a, b) {
		if (typeof(a) == "object") {
			this.defauts = $.extend({}, this.defauts, a)
		} else {
			if (typeof(a) == "string") {
				this.defauts[a] = b
			}
		}
	},
	deletePost : function(c, d,callBack) {
		var a = this;
		c = c.indexOf("?") >= 0 ? c + "&" : c + "?";
		c += "ajax=yes";
		var b = {
			url : c,
			type : "POST",
			dataType : "json",
			success : function(e) {
				$.Loading.hide();
				if (e.result == 0) {
					a.deleteRows();
					if (d) {
						alert(d)
					}
					callBack && callBack(e); // 回调函数
				} else {
					alert(e.message)
				}
			},
			error : function(f) {
				$.Loading.hide();
				alert("出现错误 ，请重试")
			}
		};
		$("form").ajaxSubmit(b)
	},
	deleteRows : function() {
		$("input[name=" + this.defauts.idChkName + "]").each(function() {
					var a = $(this);
					if (a.attr("checked")) {
						a.parents("tr").remove()
					}
				})
	},
	checkIdSeled : function() {
		var a = false;
		$("input[name=" + this.defauts.idChkName + "]").each(function() {
					if ($(this).attr("checked")) {
						a = true;
						return
					}
				});
		return a
	},
	toggleSelected : function(a) {
		$("input[name=" + this.defauts.idChkName + "]").attr("checked", a)
	}
};
(function($) {
    $.fn.gridAjaxPager = function(options) {
    	return this.each(function(){
    		bindEvent($(this));
    	});
    	
    	/**
    	 * 绑定分页事件
    	 */
    	function bindEvent(pager,grid){
    		var grid = pager.parent();
    		 pager.find("li[name='gotopage']>a").unbind(".click").bind("click",function(){
    			 var pgObj = jQuery("li[name=setpagesize_li]>input[name=pageSize]");
    			 var pageSize = pgObj.val();
    			 var reg = /\d+$/;
    			 if(!reg.test(pageSize))pageSize = 10;
				 load($(this).attr("pageno"),grid,pageSize);
			 }); 
    		 pager.find("li>input").unbind("change").bind("change",function(){
    			 var pageSize = $(this).val();
    			 var reg = /\d+$/;
    			 if(!reg.test(pageSize))pageSize = 10;
    			 load($(this).attr("pageno"),grid,pageSize);
    		 }); 
    		 pager.find("li[name=setpagesize_li]>a[name=spsize]").unbind("click").bind("click",function(){
    			 var pageSize = $(this).attr("p_size");
    			 var reg = /\d+$/;
    			 if(!reg.test(pageSize))pageSize = 10;
    			 load($(this).attr("page_no"),grid,pageSize);
    		 }); 
    		 pager.find("a.selected").unbind("click");
    	}
    	
    	/**
    	 * 点击分页的加载事件
    	 */
    	function load(pageNo,grid,pageSize){
    		var url = options;
    		url=url+"page="+pageNo;
    		if(pageSize)url += "&pageSize="+pageSize;
    		$.ajax({
    			url:url,
    			success:function(html){
    				grid.empty().append( $(html).find(".gridbody").children() );
    				bindEvent(grid.children(".page"));
    			},
    			error:function(){
    				alert("加载页面出错:(");
    			}
    		});
    	}
    	
    };
})(jQuery);


/**
 * 异步分页jquery插件 -- http POST only 
 */
(function($) {
    $.fn.gridPostPager = function(options) {
    	
    	
    	return this.each(function(){
    		bindEvent($(this));
    	});
    	
    	/**
    	 * 绑定分页事件
    	 */
    	function bindEvent(pager,grid){
    		var grid = pager.parent();
    		 pager.find("li[name='gotopage']>a").unbind(".click").bind("click",function(){
    				 loadForm($(this).attr("pageno"),grid);
			 }); 
    		 pager.find("li>input").unbind("change").bind("change",function(){
    			 var pageSize = $(this).val();
    			 var reg = /\d+$/;
    			 if(!reg.test(pageSize))pageSize = 10;
    			 $(this).val(pageSize);
				 loadForm($(this).attr("pageno"),grid);
    		 });
    		 pager.find("li[name=setpagesize_li]>a[name=spsize]").unbind("click").bind("click",function(){
    			 var pageSize = $(this).attr("p_size");
    			 var reg = /\d+$/;
    			 if(!reg.test(pageSize))pageSize = 10;
    			 pager.find("li>input").val(pageSize);
				 loadForm($(this).attr("page_no"),grid);
    		 }); 
    		 pager.find("a.selected").unbind("click");
    	}
    	
    	function loadForm(pageNo, grid) {
    		var $form;
    		if (options.formId == "") {
    			window.console && console.log("没有设置formId");
    			$form = guessForm();
    		} else {
    			$form = $("#" + options.formId);
    		}
    		if (options.ajax == "yes") {
    			lordFormByAjax($form, pageNo, grid);
    		} else {
    			simpleQueryForm($form, pageNo);
    		}
    	}
    	
    	function lordFormByAjax($form, pageNo, grid) {
    		var param = {page: pageNo};
    		var psiseObj =  $form.find("input[name='pageSize']");
    		if(!psiseObj || psiseObj.length<1){
    			var pge_apze = jQuery("li[name=setpagesize_li]>input[name=pageSize]").val();
    			var reg = /\d+$/;
    			if(!reg.test(pge_apze))pge_apze = 10;
    			param.pageSize=pge_apze;
    		}
			var s_options = {
					data : param,
	    			success:function(html){
	    				grid.empty().append( $(html).find(".gridbody").children() );
	    				bindEvent(grid.children(".page"));
	    			},
	    			error:function(a,b){
	    				alert("加载页面出错:("+b);
	    				return ;
	    			}
			};
			
			var override_url; 
			if (options.action != "") {
				override_url = options.action;
			} else {
				override_url = options.url; //根据request URL设置提交action，不使用form自己的action，因为可能用js重写了action
			}
			override_url = override_url.replace(/\?\&/, '?');
			override_url = override_url.replace(/\&\&/g, '&');
			//var reg2=new RegExp("pageSize=(\\d+)","i");
			//override_url=override_url.replace(reg2,"");
			//alert(override_url);
			window.console && console.log("=== lordFormByAjax form action is ===" + override_url);
			s_options = jQuery.extend( s_options, {url:override_url} );
			
			$form.ajaxSubmit(s_options); 
    	}
    	
    	function simpleQueryForm($form, pageNo) {
			var override_url; 
			if (options.action != "") {
				override_url = options.action;
			} else {
				override_url = options.url;  //根据request URL设置提交action，不使用form自己的action，因为可能用js重写了action
			}
			if(override_url.indexOf("?")>-1) 
				override_url +="&page=" + pageNo; //可能以?&page=1结束
			else
				override_url +="?page=" + pageNo;
			override_url = override_url.replace(/\?\&/, '?');
			window.console && console.log("=== simpleQueryForm form action ===" + override_url);
			var pageSizeObj = $($form[0]).find("input[name=pageSize]");
			var page = $(".page>ul>li>input[name=pageSize]");
			var pSize = page.val();
			var reg = /\d+$/;
			if(!reg.test(pSize))pSize = 10;
			page.val(pSize);
			if(!pageSizeObj || pageSizeObj.length==0){
				$($form[0]).append("<input type='hidden' name='pageSize' value='"+pSize+"' />");
			}else{
				pageSizeObj.val(pSize);
			}
			$form[0].setAttribute("action", override_url); // 使用DOM操作。在ie使用attr会有问题，jQuery阻止了修改属性 有时候出现。
			$form.submit();
    	}
    	
    	function guessForm() {
    		window.console && console.log("======= guess form begin =======");
    		var $forms = $("form");
    		var size = $forms.size(); 
    		window.console && console.log("======= form size =======" + size);
    		if (size == 1) {
    			return $forms;
    		} 
    		if (size == 0) {
    			alert("该页面并没有表单");
    			throw "该页面并没有表单";
    		}
    		window.console && console.log("======= more than one form in the page  =======" + size);
    		var url = options.url;
    		window.console && console.log("======= the request url =======" + url);
    		var regexp = /[a-zA-Z0-9]+[^\/]*\!{0,1}[^\/]*\.do/;
    		var guessAction = url.match(regexp);
    		if (guessAction != null) {
    			guessAction = guessAction.toString();
    		};
    		window.console && console.log("======= guessAction =======" + guessAction);
    		var match_pos = -1;
    		for (var i=0; i<size; i++) {
    			var f = $forms.get(i); 
    			var action = f.getAttribute("action");
    			if (action && action != "") {
    				var f_action = action.match(regexp);
    				if (f_action != null && f_action.toString() == guessAction) {
    					match_pos = i;
    					window.console && console.log("======= one form match the guessAction, the pos is =======" + match_pos);
    					break;
    				}
    			}
    		}
    		window.console && console.log("======= guess form end =======");
    		if (match_pos == -1) {
    			alert("该页面有多个表单，请在grid:grid标签指明formId参数");
    			throw "该页面有多个表单，请在grid:grid标签指明formId参数";
    		}
    		return $($forms.get(match_pos));
    	}
    	
    };
})(jQuery);

var GridExcel  = {
		export_file : function (options) {
			if ($(".gridbody div.page").length == 0) {
				$("div.excel input").attr("title", "本页没有数据"); 
				$("div.excel input").attr("disabled", "disabled"); //没有数据可以导出 设置按钮为disabled
				return;
			}
			if ($("#hidden_export_excel_form").length > 0 ) {
				window.console && console.log("======= remove the temp form  =======");
				$("#hidden_export_excel_form").remove();
			}
			
			var temp_form = "<form id='hidden_export_excel_form' action='" + options.url + "' method='post'>"
						 + "<input type='hidden' name='currentPage' value='" + options.currentPage + "'/>"
						 + "<input type='hidden' name='totalPage' value='" + options.totalPage + "'/>"
						 + "<input type='hidden' name='page' value='1'/>";
			var data = options.params;
			for (var name in data) {
				if (name != 'page')
					temp_form += "<input type='hidden' name='" + name + "' value='" + data[name] + "'/>";
			}
			temp_form += "</form>";
			window.console && console.log("======= append the temp form to the body  =======" + temp_form);
			$("body").append(temp_form);  //防止嵌套form append在body的最后面
			
			if (options.totalPage == 'no' && options.params.page)
					$("#hidden_export_excel_form [name='page']").val(options.params.page) ; //导出当前页
			
			$("#hidden_export_excel_form").submit();
		}
};


var Tab = function(a) {
	this.currentIndex = 0, selectedIndex = 0, this.init = function(c) {
		this.id = c;
		var b = this;
		$(this.id + " .tab>li").click(function() {
					var d = $(this);
					b.toggle(d)
				})
	};
	this.init(a);
	this.toggle = function(b) {
		this.toggleTab(b);
		this.toggleBody()
	};
	this.toggleTab = function(d) {
		var b = this;
		var c = 0;
		$(this.id + " .tab>li").each(function() {
					var e = $(this);
					if (e.attr("class") == "active") {
						b.currentIndex = c;
						e.removeClass("active")
					}
					if (this == d.get(0)) {
						b.selectedIndex = c;
						e.addClass("active")
					}
					c++
				})
	};
	this.toggleBody = function() {
		var b = this;
		var c = 0;
		$(this.id + " .tab-page>div").each(function() {
					var d = $(this);
					if (c == b.currentIndex) {
						d.hide()
					}
					if (c == b.selectedIndex) {
						d.show()
					}
					c++
				})
	}
};
var SWFUpload;
if (SWFUpload == undefined) {
	SWFUpload = function(a) {
		this.initSWFUpload(a)
	}
}
SWFUpload.prototype.initSWFUpload = function(b) {
	try {
		this.customSettings = {};
		this.settings = b;
		this.eventQueue = [];
		this.movieName = "SWFUpload_" + SWFUpload.movieCount++;
		this.movieElement = null;
		SWFUpload.instances[this.movieName] = this;
		this.initSettings();
		this.loadFlash();
		this.displayDebugInfo()
	} catch (a) {
		delete SWFUpload.instances[this.movieName];
		throw a
	}
};
SWFUpload.instances = {};
SWFUpload.movieCount = 0;
SWFUpload.version = "2.2.0 Beta 3";
SWFUpload.QUEUE_ERROR = {
	QUEUE_LIMIT_EXCEEDED : -100,
	FILE_EXCEEDS_SIZE_LIMIT : -110,
	ZERO_BYTE_FILE : -120,
	INVALID_FILETYPE : -130
};
SWFUpload.UPLOAD_ERROR = {
	HTTP_ERROR : -200,
	MISSING_UPLOAD_URL : -210,
	IO_ERROR : -220,
	SECURITY_ERROR : -230,
	UPLOAD_LIMIT_EXCEEDED : -240,
	UPLOAD_FAILED : -250,
	SPECIFIED_FILE_ID_NOT_FOUND : -260,
	FILE_VALIDATION_FAILED : -270,
	FILE_CANCELLED : -280,
	UPLOAD_STOPPED : -290
};
SWFUpload.FILE_STATUS = {
	QUEUED : -1,
	IN_PROGRESS : -2,
	ERROR : -3,
	COMPLETE : -4,
	CANCELLED : -5
};
SWFUpload.BUTTON_ACTION = {
	SELECT_FILE : -100,
	SELECT_FILES : -110,
	START_UPLOAD : -120
};
SWFUpload.CURSOR = {
	ARROW : -1,
	HAND : -2
};
SWFUpload.WINDOW_MODE = {
	WINDOW : "window",
	TRANSPARENT : "transparent",
	OPAQUE : "opaque"
};
SWFUpload.prototype.initSettings = function() {
	this.ensureDefault = function(b, a) {
		this.settings[b] = (this.settings[b] == undefined)
				? a
				: this.settings[b]
	};
	this.ensureDefault("upload_url", "");
	this.ensureDefault("file_post_name", "Filedata");
	this.ensureDefault("post_params", {});
	this.ensureDefault("use_query_string", false);
	this.ensureDefault("requeue_on_error", false);
	this.ensureDefault("http_success", []);
	this.ensureDefault("file_types", "*.*");
	this.ensureDefault("file_types_description", "All Files");
	this.ensureDefault("file_size_limit", 0);
	this.ensureDefault("file_upload_limit", 0);
	this.ensureDefault("file_queue_limit", 0);
	this.ensureDefault("flash_url", "swfupload.swf");
	this.ensureDefault("prevent_swf_caching", true);
	this.ensureDefault("button_image_url", "");
	this.ensureDefault("button_width", 1);
	this.ensureDefault("button_height", 1);
	this.ensureDefault("button_text", "");
	this.ensureDefault("button_text_style", "color: #000000; font-size: 16pt;");
	this.ensureDefault("button_text_top_padding", 0);
	this.ensureDefault("button_text_left_padding", 0);
	this.ensureDefault("button_action", SWFUpload.BUTTON_ACTION.SELECT_FILES);
	this.ensureDefault("button_disabled", false);
	this.ensureDefault("button_placeholder_id", null);
	this.ensureDefault("button_cursor", SWFUpload.CURSOR.ARROW);
	this.ensureDefault("button_window_mode", SWFUpload.WINDOW_MODE.WINDOW);
	this.ensureDefault("debug", false);
	this.settings.debug_enabled = this.settings.debug;
	this.settings.return_upload_start_handler = this.returnUploadStart;
	this.ensureDefault("swfupload_loaded_handler", null);
	this.ensureDefault("file_dialog_start_handler", null);
	this.ensureDefault("file_queued_handler", null);
	this.ensureDefault("file_queue_error_handler", null);
	this.ensureDefault("file_dialog_complete_handler", null);
	this.ensureDefault("upload_start_handler", null);
	this.ensureDefault("upload_progress_handler", null);
	this.ensureDefault("upload_error_handler", null);
	this.ensureDefault("upload_success_handler", null);
	this.ensureDefault("upload_complete_handler", null);
	this.ensureDefault("debug_handler", this.debugMessage);
	this.ensureDefault("custom_settings", {});
	this.customSettings = this.settings.custom_settings;
	if (this.settings.prevent_swf_caching) {
		this.settings.flash_url = this.settings.flash_url + "?swfuploadrnd="
				+ Math.floor(Math.random() * 999999999)
	}
	delete this.ensureDefault
};
SWFUpload.prototype.loadFlash = function() {
	if (this.settings.button_placeholder_id !== "") {
		this.replaceWithFlash()
	} else {
		this.appendFlash()
	}
};
SWFUpload.prototype.appendFlash = function() {
	var b, a;
	if (document.getElementById(this.movieName) !== null) {
		throw "ID " + this.movieName
				+ " is already in use. The Flash Object could not be added"
	}
	b = document.getElementsByTagName("body")[0];
	if (b == undefined) {
		throw "Could not find the 'body' element."
	}
	a = document.createElement("div");
	a.style.width = "1px";
	a.style.height = "1px";
	a.style.overflow = "hidden";
	b.appendChild(a);
	a.innerHTML = this.getFlashHTML();
	if (window[this.movieName] == undefined) {
		window[this.movieName] = this.getMovieElement()
	}
};
SWFUpload.prototype.replaceWithFlash = function() {
	var a, b;
	if (document.getElementById(this.movieName) !== null) {
		throw "ID " + this.movieName
				+ " is already in use. The Flash Object could not be added"
	}
	a = document.getElementById(this.settings.button_placeholder_id);
	if (a == undefined) {
		throw "Could not find the placeholder element."
	}
	b = document.createElement("div");
	b.innerHTML = this.getFlashHTML();
	a.parentNode.replaceChild(b.firstChild, a);
	if (window[this.movieName] == undefined) {
		window[this.movieName] = this.getMovieElement()
	}
};
SWFUpload.prototype.getFlashHTML = function() {
	return ['<object id="', this.movieName,
			'" type="application/x-shockwave-flash" data="',
			this.settings.flash_url, '" width="', this.settings.button_width,
			'" height="', this.settings.button_height, '" class="swfupload">',
			'<param name="wmode" value="', this.settings.button_window_mode,
			'" />', '<param name="movie" value="', this.settings.flash_url,
			'" />', '<param name="quality" value="high" />',
			'<param name="menu" value="false" />',
			'<param name="allowScriptAccess" value="always" />',
			'<param name="flashvars" value="' + this.getFlashVars() + '" />',
			"</object>"].join("")
};
SWFUpload.prototype.getFlashVars = function() {
	var b = this.buildParamString();
	var a = this.settings.http_success.join(",");
	return ["movieName=", encodeURIComponent(this.movieName),
			"&amp;uploadURL=", encodeURIComponent(this.settings.upload_url),
			"&amp;useQueryString=",
			encodeURIComponent(this.settings.use_query_string),
			"&amp;requeueOnError=",
			encodeURIComponent(this.settings.requeue_on_error),
			"&amp;httpSuccess=", encodeURIComponent(a), "&amp;params=",
			encodeURIComponent(b), "&amp;filePostName=",
			encodeURIComponent(this.settings.file_post_name),
			"&amp;fileTypes=", encodeURIComponent(this.settings.file_types),
			"&amp;fileTypesDescription=",
			encodeURIComponent(this.settings.file_types_description),
			"&amp;fileSizeLimit=",
			encodeURIComponent(this.settings.file_size_limit),
			"&amp;fileUploadLimit=",
			encodeURIComponent(this.settings.file_upload_limit),
			"&amp;fileQueueLimit=",
			encodeURIComponent(this.settings.file_queue_limit),
			"&amp;debugEnabled=",
			encodeURIComponent(this.settings.debug_enabled),
			"&amp;buttonImageURL=",
			encodeURIComponent(this.settings.button_image_url),
			"&amp;buttonWidth=",
			encodeURIComponent(this.settings.button_width),
			"&amp;buttonHeight=",
			encodeURIComponent(this.settings.button_height),
			"&amp;buttonText=", encodeURIComponent(this.settings.button_text),
			"&amp;buttonTextTopPadding=",
			encodeURIComponent(this.settings.button_text_top_padding),
			"&amp;buttonTextLeftPadding=",
			encodeURIComponent(this.settings.button_text_left_padding),
			"&amp;buttonTextStyle=",
			encodeURIComponent(this.settings.button_text_style),
			"&amp;buttonAction=",
			encodeURIComponent(this.settings.button_action),
			"&amp;buttonDisabled=",
			encodeURIComponent(this.settings.button_disabled),
			"&amp;buttonCursor=",
			encodeURIComponent(this.settings.button_cursor)].join("")
};
SWFUpload.prototype.getMovieElement = function() {
	if (this.movieElement == undefined) {
		this.movieElement = document.getElementById(this.movieName)
	}
	if (this.movieElement === null) {
		throw "Could not find Flash element"
	}
	return this.movieElement
};
SWFUpload.prototype.buildParamString = function() {
	var c = this.settings.post_params;
	var b = [];
	if (typeof(c) === "object") {
		for (var a in c) {
			if (c.hasOwnProperty(a)) {
				b.push(encodeURIComponent(a.toString()) + "="
						+ encodeURIComponent(c[a].toString()))
			}
		}
	}
	return b.join("&amp;")
};
SWFUpload.prototype.destroy = function() {
	try {
		this.cancelUpload(null, false);
		var a = null;
		a = this.getMovieElement();
		if (a) {
			for (var c in a) {
				try {
					if (typeof(a[c]) === "function") {
						a[c] = null
					}
				} catch (d) {
				}
			}
			try {
				a.parentNode.removeChild(a)
			} catch (b) {
			}
		}
		window[this.movieName] = null;
		SWFUpload.instances[this.movieName] = null;
		delete SWFUpload.instances[this.movieName];
		this.movieElement = null;
		this.settings = null;
		this.customSettings = null;
		this.eventQueue = null;
		this.movieName = null;
		return true
	} catch (d) {
		return false
	}
};
SWFUpload.prototype.displayDebugInfo = function() {
	this.debug([
			"---SWFUpload Instance Info---\n",
			"Version: ",
			SWFUpload.version,
			"\n",
			"Movie Name: ",
			this.movieName,
			"\n",
			"Settings:\n",
			"\t",
			"upload_url:               ",
			this.settings.upload_url,
			"\n",
			"\t",
			"flash_url:                ",
			this.settings.flash_url,
			"\n",
			"\t",
			"use_query_string:         ",
			this.settings.use_query_string.toString(),
			"\n",
			"\t",
			"requeue_on_error:         ",
			this.settings.requeue_on_error.toString(),
			"\n",
			"\t",
			"http_success:             ",
			this.settings.http_success.join(", "),
			"\n",
			"\t",
			"file_post_name:           ",
			this.settings.file_post_name,
			"\n",
			"\t",
			"post_params:              ",
			this.settings.post_params.toString(),
			"\n",
			"\t",
			"file_types:               ",
			this.settings.file_types,
			"\n",
			"\t",
			"file_types_description:   ",
			this.settings.file_types_description,
			"\n",
			"\t",
			"file_size_limit:          ",
			this.settings.file_size_limit,
			"\n",
			"\t",
			"file_upload_limit:        ",
			this.settings.file_upload_limit,
			"\n",
			"\t",
			"file_queue_limit:         ",
			this.settings.file_queue_limit,
			"\n",
			"\t",
			"debug:                    ",
			this.settings.debug.toString(),
			"\n",
			"\t",
			"prevent_swf_caching:      ",
			this.settings.prevent_swf_caching.toString(),
			"\n",
			"\t",
			"button_placeholder_id:    ",
			this.settings.button_placeholder_id.toString(),
			"\n",
			"\t",
			"button_image_url:         ",
			this.settings.button_image_url.toString(),
			"\n",
			"\t",
			"button_width:             ",
			this.settings.button_width.toString(),
			"\n",
			"\t",
			"button_height:            ",
			this.settings.button_height.toString(),
			"\n",
			"\t",
			"button_text:              ",
			this.settings.button_text.toString(),
			"\n",
			"\t",
			"button_text_style:        ",
			this.settings.button_text_style.toString(),
			"\n",
			"\t",
			"button_text_top_padding:  ",
			this.settings.button_text_top_padding.toString(),
			"\n",
			"\t",
			"button_text_left_padding: ",
			this.settings.button_text_left_padding.toString(),
			"\n",
			"\t",
			"button_action:            ",
			this.settings.button_action.toString(),
			"\n",
			"\t",
			"button_disabled:          ",
			this.settings.button_disabled.toString(),
			"\n",
			"\t",
			"custom_settings:          ",
			this.settings.custom_settings.toString(),
			"\n",
			"Event Handlers:\n",
			"\t",
			"swfupload_loaded_handler assigned:  ",
			(typeof this.settings.swfupload_loaded_handler === "function")
					.toString(),
			"\n",
			"\t",
			"file_dialog_start_handler assigned: ",
			(typeof this.settings.file_dialog_start_handler === "function")
					.toString(),
			"\n",
			"\t",
			"file_queued_handler assigned:       ",
			(typeof this.settings.file_queued_handler === "function")
					.toString(),
			"\n",
			"\t",
			"file_queue_error_handler assigned:  ",
			(typeof this.settings.file_queue_error_handler === "function")
					.toString(),
			"\n",
			"\t",
			"upload_start_handler assigned:      ",
			(typeof this.settings.upload_start_handler === "function")
					.toString(),
			"\n",
			"\t",
			"upload_progress_handler assigned:   ",
			(typeof this.settings.upload_progress_handler === "function")
					.toString(),
			"\n",
			"\t",
			"upload_error_handler assigned:      ",
			(typeof this.settings.upload_error_handler === "function")
					.toString(),
			"\n",
			"\t",
			"upload_success_handler assigned:    ",
			(typeof this.settings.upload_success_handler === "function")
					.toString(),
			"\n",
			"\t",
			"upload_complete_handler assigned:   ",
			(typeof this.settings.upload_complete_handler === "function")
					.toString(), "\n", "\t",
			"debug_handler assigned:             ",
			(typeof this.settings.debug_handler === "function").toString(),
			"\n"].join(""))
};
SWFUpload.prototype.addSetting = function(b, c, a) {
	if (c == undefined) {
		return (this.settings[b] = a)
	} else {
		return (this.settings[b] = c)
	}
};
SWFUpload.prototype.getSetting = function(a) {
	if (this.settings[a] != undefined) {
		return this.settings[a]
	}
	return ""
};
SWFUpload.prototype.callFlash = function(functionName, argumentArray) {
	argumentArray = argumentArray || [];
	var movieElement = this.getMovieElement();
	var returnValue, returnString;
	try {
		returnString = movieElement.CallFunction('<invoke name="'
				+ functionName + '" returntype="javascript">'
				+ __flash__argumentsToXML(argumentArray, 0) + "</invoke>");
		returnValue = eval(returnString)
	} catch (ex) {
		throw "Call to " + functionName + " failed"
	}
	if (returnValue != undefined && typeof returnValue.post === "object") {
		returnValue = this.unescapeFilePostParams(returnValue)
	}
	return returnValue
};
SWFUpload.prototype.selectFile = function() {
	this.callFlash("SelectFile")
};
SWFUpload.prototype.selectFiles = function() {
	this.callFlash("SelectFiles")
};
SWFUpload.prototype.startUpload = function(a) {
	this.callFlash("StartUpload", [a])
};
SWFUpload.prototype.cancelUpload = function(a, b) {
	if (b !== false) {
		b = true
	}
	this.callFlash("CancelUpload", [a, b])
};
SWFUpload.prototype.stopUpload = function() {
	this.callFlash("StopUpload")
};
SWFUpload.prototype.getStats = function() {
	return this.callFlash("GetStats")
};
SWFUpload.prototype.setStats = function(a) {
	this.callFlash("SetStats", [a])
};
SWFUpload.prototype.getFile = function(a) {
	if (typeof(a) === "number") {
		return this.callFlash("GetFileByIndex", [a])
	} else {
		return this.callFlash("GetFile", [a])
	}
};
SWFUpload.prototype.addFileParam = function(a, b, c) {
	return this.callFlash("AddFileParam", [a, b, c])
};
SWFUpload.prototype.removeFileParam = function(a, b) {
	this.callFlash("RemoveFileParam", [a, b])
};
SWFUpload.prototype.setUploadURL = function(a) {
	this.settings.upload_url = a.toString();
	this.callFlash("SetUploadURL", [a])
};
SWFUpload.prototype.setPostParams = function(a) {
	this.settings.post_params = a;
	this.callFlash("SetPostParams", [a])
};
SWFUpload.prototype.addPostParam = function(a, b) {
	this.settings.post_params[a] = b;
	this.callFlash("SetPostParams", [this.settings.post_params])
};
SWFUpload.prototype.removePostParam = function(a) {
	delete this.settings.post_params[a];
	this.callFlash("SetPostParams", [this.settings.post_params])
};
SWFUpload.prototype.setFileTypes = function(a, b) {
	this.settings.file_types = a;
	this.settings.file_types_description = b;
	this.callFlash("SetFileTypes", [a, b])
};
SWFUpload.prototype.setFileSizeLimit = function(a) {
	this.settings.file_size_limit = a;
	this.callFlash("SetFileSizeLimit", [a])
};
SWFUpload.prototype.setFileUploadLimit = function(a) {
	this.settings.file_upload_limit = a;
	this.callFlash("SetFileUploadLimit", [a])
};
SWFUpload.prototype.setFileQueueLimit = function(a) {
	this.settings.file_queue_limit = a;
	this.callFlash("SetFileQueueLimit", [a])
};
SWFUpload.prototype.setFilePostName = function(a) {
	this.settings.file_post_name = a;
	this.callFlash("SetFilePostName", [a])
};
SWFUpload.prototype.setUseQueryString = function(a) {
	this.settings.use_query_string = a;
	this.callFlash("SetUseQueryString", [a])
};
SWFUpload.prototype.setRequeueOnError = function(a) {
	this.settings.requeue_on_error = a;
	this.callFlash("SetRequeueOnError", [a])
};
SWFUpload.prototype.setHTTPSuccess = function(a) {
	if (typeof a === "string") {
		a = a.replace(" ", "").split(",")
	}
	this.settings.http_success = a;
	this.callFlash("SetHTTPSuccess", [a])
};
SWFUpload.prototype.setDebugEnabled = function(a) {
	this.settings.debug_enabled = a;
	this.callFlash("SetDebugEnabled", [a])
};
SWFUpload.prototype.setButtonImageURL = function(a) {
	if (a == undefined) {
		a = ""
	}
	this.settings.button_image_url = a;
	this.callFlash("SetButtonImageURL", [a])
};
SWFUpload.prototype.setButtonDimensions = function(c, a) {
	this.settings.button_width = c;
	this.settings.button_height = a;
	var b = this.getMovieElement();
	if (b != undefined) {
		b.style.width = c + "px";
		b.style.height = a + "px"
	}
	this.callFlash("SetButtonDimensions", [c, a])
};
SWFUpload.prototype.setButtonText = function(a) {
	this.settings.button_text = a;
	this.callFlash("SetButtonText", [a])
};
SWFUpload.prototype.setButtonTextPadding = function(b, a) {
	this.settings.button_text_top_padding = a;
	this.settings.button_text_left_padding = b;
	this.callFlash("SetButtonTextPadding", [b, a])
};
SWFUpload.prototype.setButtonTextStyle = function(a) {
	this.settings.button_text_style = a;
	this.callFlash("SetButtonTextStyle", [a])
};
SWFUpload.prototype.setButtonDisabled = function(a) {
	this.settings.button_disabled = a;
	this.callFlash("SetButtonDisabled", [a])
};
SWFUpload.prototype.setButtonAction = function(a) {
	this.settings.button_action = a;
	this.callFlash("SetButtonAction", [a])
};
SWFUpload.prototype.setButtonCursor = function(a) {
	this.settings.button_cursor = a;
	this.callFlash("SetButtonCursor", [a])
};
SWFUpload.prototype.queueEvent = function(b, c) {
	if (c == undefined) {
		c = []
	} else {
		if (!(c instanceof Array)) {
			c = [c]
		}
	}
	var a = this;
	if (typeof this.settings[b] === "function") {
		this.eventQueue.push(function() {
					this.settings[b].apply(this, c)
				});
		setTimeout(function() {
					a.executeNextEvent()
				}, 0)
	} else {
		if (this.settings[b] !== null) {
			throw "Event handler " + b + " is unknown or is not a function"
		}
	}
};
SWFUpload.prototype.executeNextEvent = function() {
	var a = this.eventQueue ? this.eventQueue.shift() : null;
	if (typeof(a) === "function") {
		a.apply(this)
	}
};
SWFUpload.prototype.unescapeFilePostParams = function(c) {
	var e = /[$]([0-9a-f]{4})/i;
	var f = {};
	var d;
	if (c != undefined) {
		for (var a in c.post) {
			if (c.post.hasOwnProperty(a)) {
				d = a;
				var b;
				while ((b = e.exec(d)) !== null) {
					d = d.replace(b[0], String.fromCharCode(parseInt("0x"
											+ b[1], 16)))
				}
				f[d] = c.post[a]
			}
		}
		c.post = f
	}
	return c
};
SWFUpload.prototype.flashReady = function() {
	var a = this.getMovieElement();
	if (typeof(a.CallFunction) === "unknown") {
		this
				.debug("Removing Flash functions hooks (this should only run in IE and should prevent memory leaks)");
		for (var c in a) {
			try {
				if (typeof(a[c]) === "function") {
					a[c] = null
				}
			} catch (b) {
			}
		}
	}
	this.queueEvent("swfupload_loaded_handler")
};
SWFUpload.prototype.fileDialogStart = function() {
	this.queueEvent("file_dialog_start_handler")
};
SWFUpload.prototype.fileQueued = function(a) {
	a = this.unescapeFilePostParams(a);
	this.queueEvent("file_queued_handler", a)
};
SWFUpload.prototype.fileQueueError = function(a, c, b) {
	a = this.unescapeFilePostParams(a);
	this.queueEvent("file_queue_error_handler", [a, c, b])
};
SWFUpload.prototype.fileDialogComplete = function(a, b) {
	this.queueEvent("file_dialog_complete_handler", [a, b])
};
SWFUpload.prototype.uploadStart = function(a) {
	a = this.unescapeFilePostParams(a);
	this.queueEvent("return_upload_start_handler", a)
};
SWFUpload.prototype.returnUploadStart = function(a) {
	var b;
	if (typeof this.settings.upload_start_handler === "function") {
		a = this.unescapeFilePostParams(a);
		b = this.settings.upload_start_handler.call(this, a)
	} else {
		if (this.settings.upload_start_handler != undefined) {
			throw "upload_start_handler must be a function"
		}
	}
	if (b === undefined) {
		b = true
	}
	b = !!b;
	this.callFlash("ReturnUploadStart", [b])
};
SWFUpload.prototype.uploadProgress = function(a, c, b) {
	a = this.unescapeFilePostParams(a);
	this.queueEvent("upload_progress_handler", [a, c, b])
};
SWFUpload.prototype.uploadError = function(a, c, b) {
	a = this.unescapeFilePostParams(a);
	this.queueEvent("upload_error_handler", [a, c, b])
};
SWFUpload.prototype.uploadSuccess = function(b, a) {
	b = this.unescapeFilePostParams(b);
	this.queueEvent("upload_success_handler", [b, a])
};
SWFUpload.prototype.uploadComplete = function(a) {
	a = this.unescapeFilePostParams(a);
	this.queueEvent("upload_complete_handler", a)
};
SWFUpload.prototype.debug = function(a) {
	this.queueEvent("debug_handler", a)
};
SWFUpload.prototype.debugMessage = function(c) {
	if (this.settings.debug) {
		var a, d = [];
		if (typeof c === "object" && typeof c.name === "string"
				&& typeof c.message === "string") {
			for (var b in c) {
				if (c.hasOwnProperty(b)) {
					d.push(b + ": " + c[b])
				}
			}
			a = d.join("\n") || "";
			d = a.split("\n");
			a = "EXCEPTION: " + d.join("\nEXCEPTION: ");
			SWFUpload.Console.writeLine(a)
		} else {
			SWFUpload.Console.writeLine(c)
		}
	}
};
SWFUpload.Console = {};
SWFUpload.Console.writeLine = function(d) {
	var b, a;
	try {
		b = document.getElementById("SWFUpload_Console");
		if (!b) {
			a = document.createElement("form");
			document.getElementsByTagName("body")[0].appendChild(a);
			b = document.createElement("textarea");
			b.id = "SWFUpload_Console";
			b.style.fontFamily = "monospace";
			b.setAttribute("wrap", "off");
			b.wrap = "off";
			b.style.overflow = "auto";
			b.style.width = "700px";
			b.style.height = "350px";
			b.style.margin = "5px";
			a.appendChild(b)
		}
		b.value += d + "\n";
		b.scrollTop = b.scrollHeight - b.clientHeight
	} catch (c) {
		alert("Exception: " + c.name + " Message: " + c.message)
	}
};
var SWFUpload;
if (typeof(SWFUpload) === "function") {
	SWFUpload.queue = {};
	SWFUpload.prototype.initSettings = (function(a) {
		return function() {
			if (typeof(a) === "function") {
				a.call(this)
			}
			this.customSettings.queue_cancelled_flag = false;
			this.customSettings.queue_upload_count = 0;
			this.settings.user_upload_complete_handler = this.settings.upload_complete_handler;
			this.settings.user_upload_start_handler = this.settings.upload_start_handler;
			this.settings.upload_complete_handler = SWFUpload.queue.uploadCompleteHandler;
			this.settings.upload_start_handler = SWFUpload.queue.uploadStartHandler;
			this.settings.queue_complete_handler = this.settings.queue_complete_handler
					|| null
		}
	})(SWFUpload.prototype.initSettings);
	SWFUpload.prototype.startUpload = function(a) {
		this.customSettings.queue_cancelled_flag = false;
		this.callFlash("StartUpload", [a])
	};
	SWFUpload.prototype.cancelQueue = function() {
		this.customSettings.queue_cancelled_flag = true;
		this.stopUpload();
		var a = this.getStats();
		while (a.files_queued > 0) {
			this.cancelUpload();
			a = this.getStats()
		}
	};
	SWFUpload.queue.uploadStartHandler = function(a) {
		var b;
		if (typeof(this.customSettings.user_upload_start_handler) === "function") {
			b = this.customSettings.user_upload_start_handler.call(this, a)
		}
		b = (b === false) ? false : true;
		this.customSettings.queue_cancelled_flag = !b;
		return b
	};
	SWFUpload.queue.uploadCompleteHandler = function(b) {
		var c = this.settings.user_upload_complete_handler;
		var d;
		if (b.filestatus === SWFUpload.FILE_STATUS.COMPLETE) {
			this.customSettings.queue_upload_count++
		}
		if (typeof(c) === "function") {
			d = (c.call(this, b) === false) ? false : true
		} else {
			d = true
		}
		if (d) {
			var a = this.getStats();
			if (a.files_queued > 0
					&& this.customSettings.queue_cancelled_flag === false) {
				this.startUpload()
			} else {
				if (this.customSettings.queue_cancelled_flag === false) {
					this.queueEvent("queue_complete_handler",
							[this.customSettings.queue_upload_count]);
					this.customSettings.queue_upload_count = 0
				} else {
					this.customSettings.queue_cancelled_flag = false;
					this.customSettings.queue_upload_count = 0
				}
			}
		}
	}
}
var Eop = Eop || {};
Eop.Point = {
	init : function() {
		this.initdialog();
		this.checkcanget()
	},
	initdialog : function() {
		$("body")
				.append("<div id='pointget'><div>您本月免费的1000积分尚未领取<br><a href='javascript:;' id='getPointBtn'>点击此处领取积分</a></div></div>");
		Eop.Dialog.init({
					id : "pointget",
					modal : true,
					title : "领取积分",
					width : "300px",
					height : "85px"
				})
	},
	checkcanget : function() {
		var a = this;
		alert("ok");
		$.ajax({
					url : "../core/admin/site!cktpoint.do?ajax=yes",
					type : "get",
					dataType : "json",
					success : function(b) {
						if (b.result == 1) {
							a.show()
						}
					},
					error : function() {
						alert("出错了:(")
					}
				})
	},
	show : function() {
		var a = this;
		$("#getPointBtn").click(function() {
					a.getpoint()
				});
		Eop.Dialog.open("pointget")
	},
	getpoint : function() {
		var a = this;
		$.ajax({
					url : "../core/admin/site!getpoint.do?ajax=yes",
					type : "get",
					dataType : "json",
					success : function(b) {
						if (b.result == 1) {
							Eop.Dialog.close("pointget");
							alert("免费1000积分领取成功，您在下个月仍可登录后台领取免费积分。")
						} else {
							alert(b.message)
						}
					},
					error : function() {
						alert("出错了:(")
					}
				})
	}
};
var VersionChecker = {
	init : function() {
		var a = this;
		$("#check_new_btn").click(function() {
					a.check()
				})
	},
	check : function(b) {
		var a = this;
		if (!b) {
			$.Loading.show("正在检查新版本，请稍候...")
		}
		$.ajax({
			url : "../core/admin/update!checkNewVersion.do?ajax=yes",
			dataType : "json",
			success : function(c) {
				$.Loading.hide();
				if (c.haveNewVersion) {
					a.version = c.newVersion;
					if (b) {
						if ("skip" == $.cookie("ver_" + a.version)) {
							return
						}
					}
					var d = "<div id='version_box'>";
					d += "<div style='font-size:14px'>检测到新版本<b>" + c.newVersion
							+ "</b>，功能更新：</div>";
					d += "<div style='height:200px;overflow:auto;margin-top:15px;margin-left:10px'>"
							+ a.createLogHtml(c.updateLogList) + "</div>";
					d += "<div style='text-align:center'><input style='margin-left:10px;height:30px' type='button' id='ver_upd_btn' value='立刻升级' /><input type='button'  style='margin-left:10px;height:30px' id='ver_wait_btn' value='以后再说' /><input type='button'  style='margin-left:10px;height:30px' id='ver_skip_btn' value='不再提醒此版本' /></div>";
					$("body").append(d);
					Eop.Dialog.init({
								id : "version_box",
								modal : true,
								title : "软件升级",
								width : "500px",
								height : "400px"
							});
					Eop.Dialog.open("version_box");
					$("#ver_upd_btn").click(function() {
								a.update()
							});
					$("#ver_wait_btn").click(function() {
								Eop.Dialog.close("version_box")
							});
					$("#ver_skip_btn").click(function() {
								a.skipVersion()
							})
				} else {
					if (!b) {
						alert("您的" + c.productid + "已经是最新版")
					}
				}
			},
			error : function() {
				$.Loading.hide()
			}
		})
	},
	createLogHtml : function(b) {
		var a = $("<ul></ul>");
		$.each(b, function(d, e) {
					if (e.logList.length > 0) {
						var c = $("<li class='app'>" + e.appId
								+ "<ul ></ul></li>");
						$.each(e.logList, function(g, f) {
									c.children("ul").append("<li class='log'>"
											+ f + "</li>")
								});
						a.append(c)
					}
				});
		return a.html()
	},
	skipVersion : function() {
		var b = new Date();
		b.setTime(b.getTime() + (360 * 24 * 60 * 60 * 1000));
		var a = {
			path : "/",
			expires : b
		};
		$.cookie("ver_" + this.version, "skip", a);
		Eop.Dialog.close("version_box")
	},
	update : function() {
		var a = this;
		Eop.Dialog.close("version_box");
		$.Loading.show("正在升级，可能需要花费一些时间，请稍候...");
		$.ajax({
					url : "../core/admin/update!update.do?ajax=yes",
					dataType : "json",
					success : function(b) {
						if (b.result == 0) {
							alert("升级失败:" + b.message);
							$.Loading.hide()
						} else {
							$.Loading.hide();
							alert("您的mall已经成功升级至" + a.version + ",需要重起应用生效。")
						}
					},
					error : function() {
						alert("升级出错:(")
					}
				})
	}
};
$(function() {
		});
Eop.Cache = {
	init : function() {
		this.btn = $("#cache_btn");
		this.checkState()
	},
	checkState : function() {
		var a = this;
		if( typeof(ctx) == "undefined")
			ctx =""
		var url = ctx+"/core/admin/widgetCache!getState.do?ajax=yes";
		$.ajax({
					url : url,
					dataType : "json",
					success : function(b) {
						if (b.result == 1) {
							a.setBtnState(b.state)
						}
					},
					error : function() {
					}
				})
	},
	setBtnState : function(b) {
		var a = this;
		
		if (b == "open") {
			a.btn.removeClass("cache_close").addClass("cache_open")
					.val("关闭缓存")
		} else {
			a.btn.removeClass("cache_open").addClass("cache_close").val("开启缓存")
		}
		a.bindEvent()
	},
	bindEvent : function() {
		var a = this;
		$("#cache_btn.cache_close").unbind("click").bind("click", function() {
					a.openCache()
				});
		$("#cache_btn.cache_open").unbind("click").bind("click", function() {
					a.closeCache()
				})
	},
	openCache : function() {
		var a = this;
		if( typeof(ctx) == "undefined")
			ctx ="/wssmall"
		var url = ctx+"/core/admin/widgetCache!open.do?ajax=yes";
		$.ajax({
					url : url,
					dataType : "json",
					success : function(b) {
						if (b.result == 1) {
							a.setBtnState("open");
							alert("缓存已开启，您发布的数据将会有一定延迟时间才会显示。")
						}
					},
					error : function() {
						alert("抱歉,开启缓存失败")
					}
				})
	},
	closeCache : function() {
		var a = this;
		if( typeof(ctx) == "undefined")
			ctx ="/wssmall"
		var url = ctx+"/core/admin/widgetCache!close.do?ajax=yes";
		$.ajax({
					url : url,
					dataType : "json",
					success : function(b) {
						if (b.result == 1) {
							a.setBtnState("close");
							alert("缓存已关闭。")
						}
					},
					error : function() {
						alert("抱歉,关闭缓存失败")
					}
				})
	}
};
$(function() {
	//debugger;
	if (mainpage) {
		Eop.Cache.init()
	}
	//iframe高度自动计算
	//浏览器窗口栏高度
	var topHeight ;
	try {
		topHeight = window.top.document.documentElement.clientHeight;
	} catch(e) {
		topHeight =$(window).height();
	}
	var height;
	var bottomHeight = 0;
	var  titleNewlength = 0;
	try{
		titleNewlength = $(".title_new",window.top.document).length;
	}catch(e){
		titleNewlength = $(".title_new",$(document)).length;
	}
	if(titleNewlength>0){
		bottomHeight = 30;
	}
	//计算顶部菜单高度，不同环境顶部不同
	var top_menu = 0;
	if(theme_source_from == "ECS"){
		top_menu = 100;
	}else{
		top_menu = 145;
	}
	height = topHeight - bottomHeight - top_menu;
	//add "if-else" by liqingyi
	if($('.topContent').html()!=null){
		if(!$('.topContent', parent.document).is(":visible")){
			if($('.toolbar-box', parent.document).is(":visible")){
				height=topHeight-($(".toolbar-box", parent.document).height()+$(".topBar", parent.document).height()+$(".gridSper", parent.document).height()+$(".title_new", parent.document).height());
			}else{
				height=topHeight-($(".topBar", parent.document).height()+$(".gridSper", parent.document).height()+$(".title_new", parent.document).height());
			}
		}else{
			height=topHeight-bottomHeight-$('.topContent', parent.document).height()-$('.top2', parent.document).height()-$('.topBar', parent.document).height()-$('.gridSper', parent.document).height();
		}
	}else{
		var parentDocument;
		try{
			parentDocument = parent.parent.document;
		}catch(e){
			parentDocument = $(document).context;
		}
		if(!$('.topContent', parentDocument).is(":visible")){
			if($('.toolbar-box', parentDocument).is(":visible")){
				height=topHeight-($(".toolbar-box", parentDocument).height()+$(".topBar", parentDocument).height()+$(".gridSper", parentDocument).height()+$(".title_new", parentDocument).height());
			}else{
				height=topHeight-($(".topBar", parentDocument).height()+$(".gridSper", parentDocument).height()+$(".title_new", parentDocument).height());
			}
		}else{
			height=topHeight-bottomHeight-$('.topContent', parentDocument).height()-$('.top2', parentDocument).height()-$('.topBar', parentDocument).height()-$('.gridSper', parentDocument).height();
		}
	}
	height-=6;//右边iframe内容中的框架有些偏差，故……
	
	//连连科技，特殊处理
	/*if(parent.theme_source_from == 'LLKJ_AGENT'){
		var topMenu = $('.mainDiv', window.parent.document).height();
		var topBar = $('.titlenew', window.parent.document).height();
		height = topHeight - topMenu - topBar;
	}*/
	var iframeDocument ;
	try{
		iframeDocument =$("body [id='right_content'] iframe",window.parent.document);
	}catch(e){
		iframeDocument =$("body [id='right_content'] iframe",$(window).parent());
	}
	
	if(typeof(theme_source_from) =="undefined" || theme_source_from !='CMS'){
	//非CMS则计算高度,CMS基于互联网风格研发
		(iframeDocument.length>0) &&iframeDocument.height(height);
	}	
	else{
		(iframeDocument.length>0) &&iframeDocument.height("1400px");
	}
	bindGridSelectEvent(); //绑定tr事件
	
	//后台导航栏伸缩 add by liqingyi
	mainMenuManage();
	if($("div.topContent").is(":visible")){
		var toolbarHeight=$(".toolbar-box").height();
		//5秒后自动隐藏菜单，展示菜单操作按钮
		setTimeout(function(){
			$("#hideAndShowMenuBtn").show();//显示菜单按钮
			$("div.topContent").slideUp("slow");
			//$("div.topContent").hide();
			//根据cms在线编辑是否可见
			if($(".toolbar-box").is(":visible")){
				$("div.top2").css("marginTop",toolbarHeight+"px");
			}
			$("#hideAndShowMenuBtn").attr("title","显示菜单");
			$("#leftMenuDiv").css("height",($("#leftMenuDiv").height()+toolbarHeight)+"px");
			reloadHeight(false);
		},200);
	}
	//检测窗口尺寸变化，自动调整可视区域 add by liqingyi
	$(window).resize(function() {
		reloadHeight(true);
	});

});

function mainMenuManage(){
	if(theme_source_from!="LLKJ"){
		$("#hideAndShowMenuBtn").unbind("click").bind("click",function(){
			reloadHeight(false);
		});
	}
}
function reloadHeight(flag){
	var toolbarHeight=$(".toolbar-box").height();//cms编辑栏高度
	var topMenuHeight=$("div.topContent").height();//顶部菜单高度
	var topBarHeight=$(".topBar").height();//导航菜单高度
	var bottomHeight=$(".gridSper").height()+$(".title_new").height();//底部固定高度
	if(toolbarHeight==null&&topMenuHeight==null&&topBarHeight==null&&bottomHeight==null){
		return;
	}
	if(flag){
		if($("div.topContent").is(":visible")){
			showMenuLqy(toolbarHeight,topMenuHeight,topBarHeight,bottomHeight);
		}else{
			hideMenuLqy(toolbarHeight,topMenuHeight,topBarHeight,bottomHeight);
		}
	}else{
		if($("div.topContent").is(":visible")){
			hideMenuLqy(toolbarHeight,topMenuHeight,topBarHeight,bottomHeight);
		}else{
			showMenuLqy(toolbarHeight,topMenuHeight,topBarHeight,bottomHeight);
		}
	}
	
}
function showMenuLqy(toolbarHeight,topMenuHeight,topBarHeight,bottomHeight){
	$("#hideAndShowMenuBtn").attr("title","隐藏菜单");
	//$("div.topContent").slideDown();
	$("div.topContent").show();
	$("div.top2").css("marginTop",0+"px");
	if($(".toolbar-box").is(":visible")){
		var bodyHeight=window.top.document.documentElement.clientHeight-topMenuHeight-topBarHeight-bottomHeight;
		$(".mainContent").css("height",bodyHeight+"px")
		$(".main_all_iframe:visible").contents().find("body").find("div.leftDiv").height(bodyHeight-2);
		$(".main_all_iframe:visible").contents().find("body").find("div#right_content").height(bodyHeight-2);
		$(".main_all_iframe:visible").contents().find("body").find("div#right_content").find("#auto_created_frame").css("height",bodyHeight-6+"px");
	}else{
		var bodyHeight;
		try{
			 bodyHeight=window.top.document.documentElement.clientHeight-topMenuHeight-topBarHeight-bottomHeight;
		}catch(e){
			 bodyHeight=$(window).height()-topBarHeight-bottomHeight;
		}
	//	var bodyHeight=window.top.document.documentElement.clientHeight-topMenuHeight-topBarHeight-bottomHeight;
		$(".mainContent").css("height",bodyHeight+"px")
		$(".main_all_iframe:visible").contents().find("body").find("div.leftDiv").height(bodyHeight-2);
		$(".main_all_iframe:visible").contents().find("body").find("div#right_content").height(bodyHeight-2);
		$(".main_all_iframe:visible").contents().find("body").find("div#right_content").find("#auto_created_frame").css("height",bodyHeight-6+"px");
	}
}
function hideMenuLqy(toolbarHeight,topMenuHeight,topBarHeight,bottomHeight){
	$("#hideAndShowMenuBtn").attr("title","显示菜单");
	//$("div.topContent").slideUp();
	$("div.topContent").hide();
	if($(".toolbar-box").is(":visible")){
		var bodyHeight=window.top.document.documentElement.clientHeight-toolbarHeight-topBarHeight-bottomHeight;
		$("div.top2").css("marginTop",toolbarHeight+"px");
		$(".mainContent").css("height",bodyHeight+"px")
		$(".main_all_iframe:visible").contents().find("body").find("div.leftDiv").height(bodyHeight-2);
		$(".main_all_iframe:visible").contents().find("body").find("div#right_content").height(bodyHeight-2);
		$(".main_all_iframe:visible").contents().find("body").find("div#right_content").find("#auto_created_frame").css("height",bodyHeight-6+"px");
	}else{
		var bodyHeight;
		try{
			 bodyHeight=window.top.document.documentElement.clientHeight-topBarHeight-bottomHeight;
		}catch(e){
			 bodyHeight=$(window).height()-topBarHeight-bottomHeight;
		}
		$("div.top2").css("marginTop",0+"px");
		$(".mainContent").css("height",bodyHeight+"px")
		$(".main_all_iframe:visible").contents().find("body").find("div.leftDiv").height(bodyHeight-2);
		$(".main_all_iframe:visible").contents().find("body").find("div#right_content").height(bodyHeight-2);
		$(".main_all_iframe:visible").contents().find("body").find("div#right_content").find("#auto_created_frame").css("height",bodyHeight-6+"px");
	}
}


function bindGridSelectEvent(){
   if(typeof(app_key) =="undefined" ||  app_key !="wssmall_cmsline")
    { //addby wui此处代码有BUG
		$(".gridbody tbody tr").live("click",function(){
	    	
	    	var box = $(this).find("input[type='checkbox'],input[type='radio']");
	    	if($(box).attr("type") == "radio"){
	    		$(this).siblings("tr").removeClass("grid-table-row-selected");
	    	}
	    	var isChecked =  box.is(":checked");  //选中框是否选中
	    	var isSelected = $(this).attr("isSelected"); //当前行是否选中，对于那些没有使用checkbox,radio的表格，则用这个属性判断是否选中
	    	
	//    	alert("lineClick");
	//    	alert($(box).attr("type") == "radio" || $(box).attr("type") == "checkbox");
	//    	alert(isChecked);
	//    	alert(isSelected);
	    	
	    	if($(box).attr("type") == "radio" || $(box).attr("type") == "checkbox"){ //有选择框
	    		
	        	if(isChecked){
	        		$(this).removeClass("grid-table-row-selected");
	        		$(this).attr("isSelected",false);
	        	}else{
	        		$(this).addClass("grid-table-row-selected");
	        	    $(this).attr("isSelected",true);
	        	}
	    	}else{ //无选择框
	    		if(isSelected && isSelected!="false"){
	        		$(this).removeClass("grid-table-row-selected");
	        		$(this).attr("isSelected",false);
	        	}else{
	        		$(this).addClass("grid-table-row-selected");
	        	    $(this).attr("isSelected",true);
	        	}
	    	}
	
	    	box.attr("checked",!isChecked);
		});
		
	    //多选框点击是会选中，行也选中了，所以最后是未选中，所以添加一个选中事件，最终一行还是选中了！
		$(".gridbody tbody tr").find("input[type='checkbox']").live("click",function(){
	    	$(this).attr("checked",!$(this).is(":checked"));
	    });
	    //单选框点击是会选中，行也选中了，所以最后是未选中，所以添加一个选中事件，最终一行还是选中了！
		$(".gridbody tbody tr").find("input[type='radio']").live("click",function(){
	    	$(this).attr("checked",!$(this).is(":checked"));
	    });
	    
	    //全选
		$(".gridbody thead").find("input[type='checkbox']").live("click",function(){
	    	var checkBox = $(this);
	    	var isChecked = checkBox.is(":checked");
	    	var grid = $(this).closest(".grid");
	    	if(!isChecked){
	    		grid.find("tbody tr").each(function(){
	        		$(this).removeClass("grid-table-row-selected");
	        	});
	    	}else{
	    		grid.find("tbody tr").each(function(){
	        		$(this).addClass("grid-table-row-selected");
	        	});
	    	}
			grid.find("tbody input[type='checkbox']").each(function(){
	    		$(this).attr("checked",isChecked);
	    		
	    	});
		});
	}
}


$.fn.extend({
	overlay: function(ops,id){
	 if($.browser.mozilla){ //火狐浏览器
			var ops = $.extend({
					position: 'fixed', top: 0, left: 0,
					width: '100%',height: '100%',
					opacity: 0.2, background: 'black', zIndex: 99
				}, ops),
			id = id || 'overlay';
			if( $.fn.ie6 ) ops = $.extend(ops, {
				position: 'absolute',
				width: Math.max($(window).width(),$(document.body).width()),
				height: Math.max($(window).height(),$(document.body).height()) });
			return $('<div class="overlay" id="'+id+'"/>').appendTo(document.body).css(ops);
	 }else
	 	this.addMask(id);
	},
	addMask:function(id){
	  var maskDiv$ = $('<div class="overlay" id="'+id+'"/>');
	  var maskDiv = maskDiv$[0];
	  $("body").append(maskDiv$);
      maskDiv.style.position = "absolute";
      maskDiv.style.zIndex = "99";
      maskDiv.style.left = "0px";
      maskDiv.style.top = "0px";
      maskDiv.style.width = "100%";
      var height ="100%";
      try{var height = document.documentElement.scrollHeight;}catch(e){height = "100%";}
	  maskDiv.style.height = height;
      maskDiv.style.background = "black";
      maskDiv.style.filter = "alpha(opacity=25)";
      maskDiv.style.opacity = "0.25";
      return maskDiv$;
	},
	p_position: function(ops){
		if(ops.position =="bottom"){
			$(this).css({"bottom":"0px","right":"0px","position":"absolute","z-index":"9999","position":"fixed"});
			var me = this;
			window.setTimeout(function(){
				$('#' + ops.id).fadeOut('slow', function() {
					$('.close',me).trigger("click");
				});
			},3000);
			return;
		}
		var ops = $.extend({
				fixx: 0,
				fixy: 0
			}, ops),
			mod = (this.css("position")=="fixed") ? 0 : 1,
			t = $(document).scrollTop()*mod,
			l = $(document).scrollLeft()*mod,
			mt = t;

		l += ($(window).width() - this.width()) / 2;
		t += ($(window).height() - this.height()) / 2;
		
		l += ops.fixx;
		t = Math.max(t, mt)+ops.fixy;
		
		if(t<0) t = 0;
		if(l<0) l = 0;
		
		return this.css({top: t, left: l});
	},
	dragdrop:function(ops,callback) {
        if(typeof(ops)=='function')callback=ops;
        var ops = $.extend({
            }, ops),handle=ops.handle ? $(ops.handle, this) : this,
            flag =false,_o={left:0,top:0},self=this;
        this.css('position','absolute');
        function pos(e){
            if (flag) {self.css( {left : e.pageX - _o.left + 'px',top : e.pageY - _o.top + 'px'});}
        }
        handle.mousedown(function(e){
            flag = true;
            self.css('z-index',parseInt(new Date().getTime()/1000));
            var offset = self.offset();
            _o = { left: e.pageX - offset.left, top: e.pageY - offset.top };
            $(document).mousemove(pos);
        }).mouseup(function(e){
            pos(e);
            flag = false;
            $(document).unbind('mousemove');
            if(callback)callback.apply(this,[self]);
        }).css('cursor','move');
        return self;
    },
    checkInt:function()
	{
		var obj =$(this);
		var re = /^[1-9]\d*$/;
	    if (!re.test(obj.val()))
	    {	
	    	
	        return false;
	    }
	    return true;
	},
	ie6fix: function(flag){
		return ($.fn.ie6)?this.css('visibility',flag?'hidden':'visible'):this;
	}
	
});
/*重写Alert方法*/
//$(function(){
//	window.alert = function(txt)
//	{
//		
//		var shield = document.createElement("DIV");
//		shield.name = "shield";
//		shield.style.background ="#000";
//		shield.style.position = "absolute";
//	/*	shield.style.filter = "alpha(opacity=0)";*/
//		shield.style.zIndex = "99999";
//		
//		var t = document.documentElement.scrollTop || document.body.scrollTop; 
//        var screenWidth  = document.body.clientWidth; 
//		var marginLeftWidth = ( screenWidth -300)/2;
//		
//		var strHtml ="";
//	
//		strHtml = '<div class="alertPop" style="margin-top:'+(t+150)+'px;margin-left:'+ marginLeftWidth+'px;">'+
//			        '<a href="javascript:void(0)" onclick=\"doOk($(this))\" class="alertClose">关闭</a>'+
//		            '<div class="alertTxt">'+txt+'</div>'+
//		            '<div class="alertBtnDiv"><a href="javascript:void(0);" onclick=\"doOk($(this))\" class="alertBtn">确定</a></div>'+
//		            '</div>';
//		shield.innerHTML = strHtml;
//		var first=document.body.firstChild; //得到第一个元素
//		document.body.insertBefore(shield,first); 
//
//		this.doOk = function(curr){
//			curr.closest("[name='shield']").remove();
//		}
//
//	}
//});
/**
 * 异步加载分页总数信息jquery插件
 */
(function($) {
    $.fn.gridAjaxPageCount = function(options) {
    	var cachedUrl = options;    		
		var url = "/core/admin/pageInfo!info.do?ajax=yes";
		
		$.ajax({
				url : url,
				dataType: "json",
				data:options,
				success : function(data) {
					$(".grid #grid_page_count").html(data.pageCount);
					$(".grid #grid_total_count").html(data.totalCount);
					//$(".grid #grid_page_body_string").html(decodeURIComponent(data.bodyString.replace(/\+/g, '%20')).replace('&','&amp;'));
					$(".grid #grid_page_without_count").hide();
					$(".grid #grid_page_with_count").show();
					
					if(data.pageCount == options.page) {
						$(".grid #grid_next_page").hide();
					}
				},
				error : function(b) {
					alert("解析出错")
				}
			})
    };
})(jQuery);
