(function($) {

  var tabs = function(element, options) {
    this.options = options;
    this.element = $(element);
    this._initParams();
    this._renderUI();
    this._bindUI();
  };

  tabs.prototype = {

    _initParams : function(tabIndex) {
      var options = this.options;
      this.tabIndex = options.tabIndex;
	  var tabsHeader = this.element.children("UL.tabs-header");
	  var tabsBody = this.element.children("div.tabs-body");
      // 默认选中
      //var activedTab = this.element.find("UL.tabs-header>LI.active");
	  var activedTab = tabsHeader.children("LI.active");
      if (activedTab.length > 0) {
        //this.tabIndex = this.element.find("UL.tabs-header>LI").index(activedTab.get(0));
		this.tabIndex = tabsHeader.children("LI").index(activedTab.get(0));
      }
      if (tabIndex !== undefined) {
        this.tabIndex = tabIndex;
      }
      this.tabHeader = tabsHeader.children("LI:eq('" + this.tabIndex + "')");
      this.tabPanel = tabsBody.children(".tab-panel:eq(" + this.tabIndex + ")");
      var tabConf = {};
      if ($.isArray(options.tabPanel) && options.tabPanel.length > 0) {
        tabConf = options.tabPanel[this.tabIndex];
      } else {
        tabConf = {
          id : this.tabPanel.attr("id"),
          url : this.tabHeader.attr("data-url")
        };
      }
      this.tabPanel.data("conf", tabConf);
    },

    _renderUI : function() {
      this.tabBody = this.element.children("div.tabs-body");
      if (this.options.width && this.options.width != "auto") {
        this.element.width(this.options.width);
      }
      if (this.options.height && this.options.height != "auto") {
        this.element.height(this.options.height);
      }
    },

    _bindUI : function() {
      var that = this;
	  var tabsHeader = this.element.children("UL.tabs-header");
	  var tabsBody = this.element.children("div.tabs-body");
      tabsHeader.delegate("LI>A", "click", function(evt) {
        evt.preventDefault();
        var tabIndex = tabsHeader.children("LI").children("A").index($(this).get(0));
        if (tabIndex == that.tabIndex) {
          return false;
        }
        that._initParams(tabIndex);
        that._updateHeadStyle();
        that._updatePanelStyle();
      });
    },

    _updateHeadStyle : function() {
      this.element.children("UL.tabs-header").children(".active").removeClass("active");
      this.tabHeader.addClass("active");
    },

    _updatePanelStyle : function() {
      this.element.children("div.tabs-body").children(".tab-panel").hide();
      this.tabPanel.show();
      this._loadPanel();
    },

    _loadPanel : function() {
      var tabConf = this.tabPanel.data("conf") || {};
      if (tabConf.url) {
        this.tabBody.addClass("loading");
        this.tabPanel.empty();
        this.tabPanel.load(tabConf.url, function() {
          this.tabBody.removeClass("loading");
          tabConf.callback && tabConf.callback();
        });
      } else {
        tabConf.callback && tabConf.callback();
      }
    },

    show : function() {
      this._updateHeadStyle();
      this._updatePanelStyle();
    }
  };

  $.fn.forte_tab = function(option) {
    var settings = $.extend({}, $.fn.forte_tab.defaults, option);
    return this.each(function() {
      var $this = $(this);
      var data = $this.data("tabs");
      if (!data) {
        $this.data("tabs", (data = new tabs($this, settings)));
      }
      data["show"]();
    });
  };

  $.fn.forte_tab.defaults = {
    tabIndex : 0,
    width : "auto",
    height : "auto",
    tabPanel : []
  };

  $.fn.forte_tab.Constructor = tabs;
})(window.jQuery);