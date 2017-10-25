(function($, ns) {

  var zIndex = 100, maxLev = 0;

  var loadingTpl = "<div class='loading'><span class='loadingText'>数据加载中...</span></div>";
  var alertTpl = "<div class='modal alert' id='globalAlert'>" +
      "<div class='modal-header'><span class='close' title='关闭'>×</span>" +
      "<span class='title'>系统提示</span></div>" +
      "<div class='modal-body'>${msg}</div>" +
      "<div class='modal-footer'>" +
      "<button class='btn close'>关闭</button>" +
      "</div>" +
      "</div>";
  var confirmTpl = "<div class='modal alert' id='globalConfirm'>" +
      "<div class='modal-header'>" +
      "<span class='close' title='关闭'>×</span>" +
      "<span class='title'>系统确认</span>" +
      "</div>" +
      "<div class='modal-body'>${msg}</div>" +
      "<div class='modal-footer'>" +
      "<button class='btn ok'>确定</button>" +
      "<button class='btn close'>取消</button>" +
      "</div>" +
      "</div>";

  var Modal = function(element, options) {
    this.options = options;
    this.$element = $(element).delegate(".close", "click", $.proxy(this.hide, this));
    zIndex++;
  };

  Modal.prototype = {

    constructor : Modal,

    toggle : function() {
      return this[!this.isShown ? "show" : "hide"]();
    },

    show : function() {
      maxLev++;
      zIndex++;
      var that = this;
      this.isShown = true;
      this.options.remote && this.$element.find(".modal-body").append(loadingTpl);
      this.options.remote && this.$element.find(".modal-body").load(this.options.remote, function() {
        var modalBody = $(this);
        // 初始化日历控件
        if (modalBody.find(".datepicker-inputter").length > 0) {
          ns.use([ "datepicker" ], function() {
            forte.util.initDatepicker(modalBody.find(".datepicker-inputter"));
          });
        }
        // 初始化数字组件
        ns.util.initNumeric(modalBody.find(".numeric"));
        // 初始化全选组件
        ns.util.initCheckAll(modalBody.find("label.checkall input[type='checkbox']"));
      });
      this.backdrop(function() {
        that.$element.removeClass("hide");
        var modalWidth = that.$element.width();
        that.$element.css({
          "margin-left" : 0 - Math.floor(modalWidth / 2),
          "z-index" : zIndex,
          "margin-top" : (maxLev - 1) * 30
        });
        that.setBodyMaxHeight();
        that.$element.addClass("in").attr("aria-hidden", false);
      });
      if(this.options.drag){
        this.drag();
      };
      if(!this.options.backdrop){
        this.$element.on('click',function(){
          var index = 1;
          $('.modal').each(function(i,o){
            index = $(this).css('z-index') > index ? $(this).css('z-index') : index;
          });
          index++;
          $(this).css('z-index',index);
        });
      }
    },

    setBodyMaxHeight : function() {
      var winHeight = $(window).height();
      var footer = this.$element.find(".modal-footer");
      var maxBodyHeight = 400;
      if (footer.length > 0) {
        maxBodyHeight = (winHeight * 0.85 - (maxLev - 1) * 30) - this.$element.find(".modal-header").height()
            - footer.height() - 50;
      } else {
        maxBodyHeight = (winHeight * 0.85 - (maxLev - 1) * 30) - this.$element.find(".modal-header").height() - 50;
      }
      this.$element.find(".modal-body").css({
        "max-height" : Math.floor(maxBodyHeight)
      });
    },

    hide : function(e) {
      maxLev--;
      var that = this;
      this.isShown = false;
      this.$element.removeClass("in").attr("aria-hidden", true);
      this.$element.addClass("hide");
      this.backdrop(function() {
        that.removeBackdrop();
        if (that.options.remote) {
          // 清除日历组件
          if (that.$element.find(".modal-body").find(".datepicker-inputter").length > 0) {
            that.$element.find(".modal-body").find(".datepicker-inputter").datetimepicker("remove");
          }
          that.$element.find(".modal-body").empty();
        } else {
          that.options.reset && that.options.reset();
        }
      });
    },

    removeBackdrop : function() {
      this.$backdrop && this.$backdrop.remove();
      this.$backdrop = null;
    },

    drag : function(){
      var header = this.$element.find(".modal-header");
      var $div = header.parent();
      $div.bind("mousedown",function(event){
      /* 获取需要拖动节点的坐标 */
      var offset_x = parseInt($(this).css('left'));//x坐标
      var offset_y = parseInt($(this).css('top'));//y坐标
      /* 获取当前鼠标的坐标 */
      var mouse_x = event.pageX;
      var mouse_y = event.pageY;        

      /* 绑定拖动事件 */
      /* 由于拖动时，可能鼠标会移出元素，所以应该使用全局（document）元素 */
        $(document).bind("mousemove",function(ev){
          /* 计算鼠标移动了的位置 */
          var _x = ev.pageX - mouse_x;
          var _y = ev.pageY - mouse_y;
              
          /* 设置移动后的元素坐标 */
          var now_x = (offset_x + _x ) + "px";
          var now_y = (offset_y + _y ) + "px";          
          /* 改变目标元素的位置 */
          $div.css({
            top:now_y,
            left:now_x
          });
        });
      });
      /* 当鼠标左键松开，接触事件绑定 */
      $(document).bind("mouseup",function(){
        $(this).unbind("mousemove");
      });
    },
    
    backdrop : function(callback) {
      if (this.isShown && this.options.backdrop) {
        this.$backdrop = $("<div class='modal-backdrop' />").appendTo(document.body).css({
          "z-index" : zIndex - 1
        });
        this.$backdrop.addClass("in");
        if (!callback)
          return;
        callback();
      } else if (!this.isShown && this.$backdrop) {
        this.$backdrop.removeClass("in");
        callback();
      } else if (callback) {
        callback();
      }
    }
  };

  $.fn.forte_modal = function(option) {
    return this.each(function() {
      var $this = $(this);
      var options = $.extend({}, $.fn.forte_modal.defaults, $this.data(), typeof option == "object" && option);
      var data = $this.data("modal");
      if (!data) {
        $this.data("modal", (data = new Modal(this, options)));
      }
      if (typeof option == "string") {
        data[option]();
      } else if (options.show) {
        if (options.remote) {
          data.options.remote = options.remote;
        }
        data.show();
      }
    });
  };

  $.fn.forte_modal.defaults = {
    // 显示遮罩
    backdrop : true,
    // 显示弹出层
    show : true,
    //控件拖动
	drag : false,
    // 弹出层body的内容URL
    remote : ""
  };

  $.fn.forte_modal.Constructor = Modal;

  $.forte_alert = function(msg) {
    if ($("#globalAlert").length > 0) {
      $("#globalAlert").find(".modal-body").html(msg);
      $("#globalAlert").forte_modal("show");
    } else {
      $("body").append(alertTpl.replace("${msg}", msg));
      $("#globalAlert").forte_modal("show");
    }
  };

  $.forte_confirm = function(msg, confirmFN) {
	$("#globalConfirm").find("button.ok").unbind("click");
	$("#globalConfirm").find("button.ok").bind("click", function() {
	  confirmFN();
	  $("#globalConfirm").forte_modal("hide");
	});
    if ($("#globalConfirm").length > 0) {
      $("#globalConfirm").find(".modal-body").html(msg);
      $("#globalConfirm").forte_modal("show");
    } else {
      $("body").append(confirmTpl.replace("${msg}", msg));
      $("#globalConfirm").forte_modal("show");
      $("#globalConfirm").find("button.ok").bind("click", function() {
        confirmFN();
        $("#globalConfirm").forte_modal("hide");
      });
    }
  };

})(jQuery, FORTE);
