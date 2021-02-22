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
		}
		if (typeof a == "object") {
			this.opts = a
		}
	},
	load : function(b) {
		Eop.Help.close();
		if (Eop.onRedirect) {
			if (!Eop.onRedirect()) {
				return false
			}
			Eop.onRedirect = undefined
		}
		var c = b.attr("target");
		var a = b.attr("href");
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
				this.loadUrlInFrm(a);
				return false
			}
			if (c == "_blank") {
				return true
			}
			if (c == "_top") {
				return true
			}
			this.loadUrlInFrm(a)
		} else {
			this.loadUrlInFrm(a)
		}
		return false
	},
	loadUrlInFrm : function(a) {
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
			var c = '<div  class="dialog" style="display:none;"><div class="dialog_box">';
			c += '<div class="head">';
			c += '<div class="title">' + this.opts.title + "</div>";
			c += '<span class="closeBtn"></span>';
			c += "</div>";
			c += '<div class="body dialogContent"></div>';
			c += "</div>";
			a.dialog = $(c);
			a.dialog.appendTo("body");
			a.dialog.attr("id", "dlg_" + a.opts.id)
		} else {
			a.dialog = $("#dlg_" + a.opts.id)
		}
		a.dialog.css("width", a.opts.width);
		a.dialog.find(".dialogContent").empty().append($("#" + a.opts.id));
		a.dialog.jqDrag(".head").jqm(this.opts).jqmAddClose(".closeBtn")
	},
	open : function(a) {
		if (a) {
			$("#dlg_" + a).jqmShow()
		} else {
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
	alert(2)
		$("input[type='file']")
				.after("&nbsp;<input type='button' value='清空'/>").next("input")
				.click(function() {
							var d = (navigator.appVersion.indexOf("MSIE") != -1);
							var a = (navigator.userAgent.indexOf("Firefox") != -1);
							if (d) {
								var b = $(this).prev("input").attr("name");
								var c = b.cloneNode(false);
								c.onchange = b.onchange;
								b.parentNode.replaceChild(c, b)
							} else {
								$(this).prev("input").attr("value", "")
							}
						})
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
					e = e || window.event;
					d.jqm.close(this._jqm, e)
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
		throw "Dimensions: jQuery collection is empty"
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
					
					callBack && callBack(result); // 回调函数
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
(function(a) {
	a.fn.gridAjaxPager = function(b) {
		return this.each(function() {
					d(a(this))
				});
		function d(e, f) {
			var f = e.parent();
			e.find("li>a").unbind(".click").bind("click", function() {
						c(a(this).attr("pageno"), f)
					});
			e.find("a.selected").unbind("click")
		}
		function c(e, g) {
			var f = b;
			f = f + "page=" + e;
			a.ajax({
						url : f,
						success : function(h) {
							g.empty().append(a(h).find(".gridbody").children());
							d(g.children(".page"))
						},
						error : function() {
							alert("加载页面出错:(")
						}
					})
		}
	}
})(jQuery);
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
		$.ajax({
					url : "../core/admin/widgetCache!getState.do?ajax=yes",
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
					.html("关闭缓存")
		} else {
			a.btn.removeClass("cache_open").addClass("cache_close")
					.html("开启缓存")
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
		$.ajax({
					url : "../core/admin/widgetCache!open.do?ajax=yes",
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
		$.ajax({
					url : "../core/admin/widgetCache!close.do?ajax=yes",
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
			if (mainpage) {
				Eop.Cache.init()
			}
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
					e = e || window.event;
					d.jqm.close(this._jqm, e)
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
		throw "Dimensions: jQuery collection is empty"
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
	deletePost : function(c, d) {
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
(function(a) {
	a.fn.gridAjaxPager = function(b) {
		return this.each(function() {
					d(a(this))
				});
		function d(e, f) {
			var f = e.parent();
			e.find("li>a").unbind(".click").bind("click", function() {
						c(a(this).attr("pageno"), f)
					});
			e.find("a.selected").unbind("click")
		}
		function c(e, g) {
			var f = b;
			f = f + "page=" + e;
			a.ajax({
						url : f,
						success : function(h) {
							g.empty().append(a(h).find(".gridbody").children());
							d(g.children(".page"))
						},
						error : function() {
							alert("加载页面出错:(")
						}
					})
		}
	}
})(jQuery);
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
		$.ajax({
					url : "../core/admin/widgetCache!getState.do?ajax=yes",
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
					.html("关闭缓存")
		} else {
			a.btn.removeClass("cache_open").addClass("cache_close")
					.html("开启缓存")
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
		$.ajax({
					url : "../core/admin/widgetCache!open.do?ajax=yes",
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
		$.ajax({
					url : "../core/admin/widgetCache!close.do?ajax=yes",
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
			if (mainpage) {
				Eop.Cache.init()
			}
		});