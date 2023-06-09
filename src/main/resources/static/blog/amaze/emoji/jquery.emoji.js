!function(a, b, c) {
    function d(b, c) {
        switch (this.$content = a(b),
        this.options = c,
        this.index = emoji_index,
        c.animation) {
        case "none":
            this.showFunc = "show",
            this.hideFunc = "hide",
            this.toggleFunc = "toggle";
            break;
        case "slide":
            this.showFunc = "slideDown",
            this.hideFunc = "slideUp",
            this.toggleFunc = "slideToggle";
            break;
        case "fade":
            this.showFunc = "fadeIn",
            this.hideFunc = "fadeOut",
            this.toggleFunc = "fadeToggle";
            break;
        default:
            this.showFunc = "fadeIn",
            this.hideFunc = "fadeOut",
            this.toggleFunc = "fadeToggle"
        }
        this._init()
    }
    function e(b) {
        return emoji_index++,
        this.each(function() {
            var c = a(this)
              , e = c.data("plugin_" + f + emoji_index)
              , h = a.extend({}, g, c.data(), "object" == typeof b && b);
            e || c.data("plugin_" + f + emoji_index, e = new d(this,h)),
            "string" == typeof b && e[b]()
        })
    }
    var f = "emoji"
      , g = {
        showTab: !0,
        animation: "fade",
        icons: []
    };
    b.emoji_index = 0,
    d.prototype = {
        _init: function() {
            var b, d, e, f, g, h = this, i = this.options.button, j = h.index;
            j=$(".emoji_container").length+1;
            i || (b = '<input type="image" class="emoji_btn" id="emoji_btn_' + j + '" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAAZBAMAAAA2x5hQAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAkUExURUxpcfTGAPTGAPTGAPTGAPTGAPTGAPTGAPTGAPTGAPTGAPTGAOfx6yUAAAALdFJOUwAzbVQOoYrzwdwkAoU+0gAAAM1JREFUGNN9kK0PQWEUxl8fM24iCYopwi0muuVuzGyKwATFZpJIU01RUG/RBMnHxfz+Oef9uNM84d1+23nO+zxHKVG2WWupRJkdcAwtpCK0lpbqWE01pB0QayonREMoIp7AawQrWSgGGb4pn6dSeSh68FAVXqHqy3wKrkJiDGDTg3dnp//w+WnwlwIOJauF+C7sXRVfdha4O4oIJfTbtdSxs2uqhs585A0ko8iLTMEcDE1n65A+29pYAlr72nz9dKu7GuNTcsL2fDQzB/wCPVJ69nZGb3gAAAAASUVORK5CYII="/>',
            d = this.$content.offset().top + this.$content.outerHeight() + 10,
            e = this.$content.offset().left + 2,
            a(b).appendTo(a("body")),
            a("#emoji_btn_" + j).css({
                top: d + "px",
                left: e + "px"
            }),
            i = "#emoji_btn_" + j);
            var k = this.options.showTab
              , l = this.options.icons
              , m = l.length;
            if (0 === m)
                return alert("Missing icons config!"),
                !1;
            for (var n, o, p, q, r, s, t, u, v, w, x, y = '<div class="emoji_container" id="emoji_container_' + j + '">', z = '<div class="emoji_content">', A = '<div class="emoji_tab" style="' + (1 !== m || k ? "" : "display:none;") + '"><div class="emoji_tab_prev"></div><div class="emoji_tab_list"><ul>', B = 0; m > B; B++)
                if (o = l[B].name || "group" + (B + 1),
                p = l[B].path,
                q = l[B].maxNum,
                r = l[B].excludeNums,
                s = l[B].file || ".jpg",
                t = l[B].placeholder || "#em" + (B + 1) + "_{alias}#",
                u = l[B].alias,
                v = l[B].title,
                w = 0,
                p && q) {
                    n = '<div id="emoji' + B + '" class="emoji_icons" style="' + (0 === B ? "" : "display:none;") + '"><ul>';
                    for (var C = 1; q >= C; C++)
                        if (!(r && r.indexOf(C) >= 0)) {
                            if (u) {
                                if ("object" != typeof u) {
                                    alert("Error config about alias!");
                                    break
                                }
                                x = t.replace(new RegExp("{alias}","gi"), u[C].toString())
                            } else
                                x = t.replace(new RegExp("{alias}","gi"), C.toString());
                            n += '<li><a data-emoji_code="' + x + '" data-index="' + w + '" title="' + (v && v[C] ? v[C] : "") + '"><img src="' + p + C + s + '"/></a></li>',
                            w++
                        }
                    n += "</ul></div>",
                    z += n,
                    A += '<li data-emoji_tab="emoji' + B + '" class="' + (0 === B ? "selected" : "") + '" title="' + o + '">' + o + "</li>"
                } else
                    alert("The " + B + " index of icon groups has error config!");
            z += "</div>",
            A += '</ul></div><div class="emoji_tab_next"></div></div>';
            var D = '<div class="emoji_preview"><img/></div>';
            y += z,
            y += A,
            y += D,
            a(y).appendTo(a("body")),
            f = a(i).offset().top + a(i).outerHeight()+10,
            g = a(i).offset().left-30,
            a("#emoji_container_" + j).css({
                top: f + "px",
                left: g + "px"
            }),
            a("#emoji_container_" + j + " .emoji_content").mCustomScrollbar({
                theme: "minimal-dark",
                scrollbarPosition: "inside",
                mouseWheel: {
                    scrollAmount: 275
                }
            });
           $(".emoji_container").hide();
            var E = m % 8 === 0 ? parseInt(m / 8) : parseInt(m / 8) + 1
              , F = 1;
            var clickIndex=0;
            a(c).on({
                click: function(b) {
                   if(j != $(".emoji_container").length){
						return;
					}
					//加入 解决无法关闭的问题
					if($("#emoji_container_" + j ).css("display")=='block'){
                      $("#emoji_container_" + j ).hide();
					}
                    var c, d, e, f, g = b.target, k = h.$content[0];
                    g === a(i)[0] ? (a("#emoji_container_" + j)[h.toggleFunc](),
                    h.$content.focus()) : a(g).parents("#emoji_container_" + j).length > 0 ? (c = a(g).data("emoji_code") || a(g).parent().data("emoji_code"),
                    d = a(g).data("emoji_tab"),
                    c ? ("DIV" === k.nodeName ? (e = a("#emoji_container_" + j + ' a[data-emoji_code="' + c + '"] img').attr("src"),
                    f = '<img class="emoji_icon" src="' + e + '"/>',
                    h._insertAtCursor(k, f, !1)) : h._insertAtCursor(k, c),
                    h.hide()) : d ? a(g).hasClass("selected") || (a("#emoji_container_" + j + " .emoji_icons").hide(),
                    a("#emoji_container_" + j + " #" + d).show(),
                    a(g).addClass("selected").siblings().removeClass("selected")) : a(g).hasClass("emoji_tab_prev") ? F > 1 && (a("#emoji_container_" + j + " .emoji_tab_list ul").css("margin-left", "-503" * (F - 2) + "px"),
                    F--) : a(g).hasClass("emoji_tab_next") && E > F && (a("#emoji_container_" + j + " .emoji_tab_list ul").css("margin-left", "-503" * F + "px"),
                    F++),
                    h.$content.focus()) : a("#emoji_container_" + j + ":visible").length > 0 && (h.hide(),
                    h.$content.focus());
                }
            }),
            a("#emoji_container_" + j + " .emoji_icons a").mouseenter(function() {
                var b = a(this).data("index");
                parseInt(b / 5) % 2 === 0 ? a("#emoji_container_" + j + " .emoji_preview").css({
                    left: "auto",
                    right: 0
                }) : a("#emoji_container_" + j + " .emoji_preview").css({
                    left: 0,
                    right: "auto"
                });
                var c = a(this).find("img").attr("src");
                a("#emoji_container_" + j + " .emoji_preview img").attr("src", c).parent().show()
            }),
            a("#emoji_container_" + j + " .emoji_icons a").mouseleave(function() {
                a("#emoji_container_" + j + " .emoji_preview img").removeAttr("src").parent().hide()
            })
        },
        _insertAtCursor: function(a, d, e) {
            var f, g;
            if ("DIV" === a.nodeName) {
                if (a.focus(),
                b.getSelection) {
                    if (f = b.getSelection(),
                    f.getRangeAt && f.rangeCount) {
                        g = f.getRangeAt(0),
                        g.deleteContents();
                        var h = c.createElement("div");
                        h.innerHTML = d;
                        for (var i, j, k = c.createDocumentFragment(); i = h.firstChild; )
                            j = k.appendChild(i);
                        var l = k.firstChild;
                        g.insertNode(k),
                        j && (g = g.cloneRange(),
                        g.setStartAfter(j),
                        e ? g.setStartBefore(l) : g.collapse(!0),
                        f.removeAllRanges(),
                        f.addRange(g))
                    }
                } else if ((f = c.selection) && "Control" !== f.type) {
                    var m = f.createRange();
                    m.collapse(!0),
                    f.createRange().pasteHTML(html),
                    e && (g = f.createRange(),
                    g.setEndPoint("StartToStart", m),
                    g.select())
                }
            } else if (c.selection)
                a.focus(),
                f = c.selection.createRange(),
                f.text = d,
                f.select();
            else if (a.selectionStart || 0 === a.selectionStart) {
                var n = a.selectionStart
                  , o = a.selectionEnd
                  , p = a.scrollTop;
                a.value = a.value.substring(0, n) + d + a.value.substring(o, a.value.length),
                p > 0 && (a.scrollTop = p),
                a.focus(),
                a.selectionStart = n + d.length,
                a.selectionEnd = n + d.length
            } else
                a.value += d,
                a.focus()
        },
        show: function() {
            a("#emoji_container_" + this.index)[this.showFunc]()
        },
        hide: function() {
            a("#emoji_container_" + this.index)[this.hideFunc]()
        },
        toggle: function() {
            a("#emoji_container_" + this.index)[this.toggleFunc]()
        }
    },
    a.fn[f] = e,
    a.fn[f].Constructor = d
}(jQuery, window, document),
function(a, b, c) {
    function d(b, c) {
        this.$content = a(b),
        this.options = c,
        this._init()
    }
    function e(b) {
        return this.each(function() {
            var c = a(this)
              , e = c.data("plugin_" + f)
              , h = a.extend({}, g, c.data(), "object" == typeof b && b);
            e || c.data("plugin_" + f, e = new d(this,h)),
            "string" == typeof b && e[b]()
        })
    }
    var f = "emojiParse"
      , g = {
        icons: []
    };
    d.prototype = {
        _init: function() {
            var a, b, c, d, e, f, g = this, h = this.options.icons, i = h.length, j = {};
            if (i > 0)
                for (var k = 0; i > k; k++)
                    if (a = h[k].path,
                    b = h[k].file || ".jpg",
                    c = h[k].placeholder,
                    d = h[k].alias,
                    a)
                        if (d) {
                            for (var l in d)
                                d.hasOwnProperty(l) && (j[d[l]] = l);
                            e = c.replace(new RegExp("{alias}","gi"), "([\\s\\S]+?)"),
                            f = new RegExp(e,"gm"),
                            g.$content.html(g.$content.html().replace(f, function(c, d) {
                                var e = j[d];
                                return e ? '<img class="emoji_icon" src="' + a + e + b + '"/>' : c
                            }))
                        } else
                            e = c.replace(new RegExp("{alias}","gi"), "(\\d+?)"),
                            g.$content.html(g.$content.html().replace(new RegExp(e,"gm"), '<img class="emoji_icon" src="' + a + "$1" + b + '"/>'));
                    else
                        alert("Path not config!")
        }
    },
    a.fn[f] = e,
    a.fn[f].Constructor = d
}(jQuery, window, document);
