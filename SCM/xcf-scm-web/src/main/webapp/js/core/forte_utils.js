(function(ns) {
  ns.util = {
    // 获取选中checkbox对象的数组
    getSelectedItemsObj : function(name) {
      var ret = [];
      $("input[name='" + name + "']:checked").each(function() {
        var target = $(this);
        if (target.hasClass("checkAll")) {
          return;
        }
        ret.push(target);
      });
      return ret;
    },
    // 获取选中checkbox值的数组
    getSelectedItems : function(name) {
      var ret = [];
      $("input[name='" + name + "']:checked").each(function() {
        var target = $(this);
        if (target.hasClass("checkAll")) {
          return;
        }
        var value = target.val();
        if (value) {
          ret.push(value);
        }
      });
      return ret;
    },// 初始化日期组件
    initDatepicker : function(picker) {
      $(picker).each(function() {
        var datepicker = $(this);
        var readonly = $(this).attr("data-readonly") || "true";
        if (readonly == "true") {
          datepicker.attr("readonly", "readonly");
        }

        var format = datepicker.attr("data-format") || "yyyy-mm-dd";
        var startDate = "";
        if (datepicker.attr("data-date")) {
          if (datepicker.attr("data-date") == "today") {
            startDate = new Date().Format(format);
            // 默认输入当前日期
            //$(this).val(startDate);
          } else {
            startDate = datepicker.attr("data-date");
          }
          datepicker.attr("data-date", startDate);
          //$(this).val(startDate);
        }
        var mode = 2;
        var startView = 2;
        if ("yyyy-mm-dd hh:ii" == format) {
          mode = 0;
        }
        if ("hh:ii" == format) {
            mode = 0;
            startView = 0;
        }
        
        if ("yyyy-mm" == format) {
            mode = 3;
            startView = 3;
        }
        datepicker.datetimepicker({
          language : 'zh-CN',
          autoclose : true,
          format : format,
          startView:startView,
          minView : mode,
          todayBtn : true,
          forceParse : false,
          // todayHighlight : true,
          startDate : startDate
        }).on("changeDate", function(ev) {
          var resetBtn = $(this).next("span.cleardate");
          // 激活重置日期的按钮
          if (resetBtn.length > 0 && !resetBtn.data("resetBtn")) {
            resetBtn.data("attchedEvt", true);
            resetBtn.bind("click", function() {
              /*var startDate = datepicker.attr("data-date");
              if (!startDate) {
                startDate = "";
              }*/
              datepicker.val("");
            });
          }
          // 清空日期为空的校验信息
          datepicker.removeClass("invalidInput");
          datepicker.parent().find(".invalidText").remove();
        });
        
        datepicker.next("span.cleardate").on("click",function(){
        	datepicker.val("");
        });
      });
    },
    // checkbox 全选&反选
    initCheckAll : function(selector) {
      $(selector).on("click", function() {
        var target = $(this);
        var name = target.attr("name");
        $("input[type='checkbox'][name='" + name + "']").prop("checked", target.prop("checked"));
      });
    },
    // 初始化只能输入数字的控件
    initNumeric : function(selector) {
      $(selector).numeric();
    },

    // 显示页面loading
    startLoading : function() {
      var loadingEL = null;
      var fixZIndex = function() {
        var modal = $(".modal:not(.hide)");
        if (modal.length > 0) {
          var zIndex = modal.css("z-index");
          loadingEL.css("zIndex", zIndex + 1);
        } else {
          loadingEL.css("zIndex", 100);
        }
      };
      if ($("#loadingMask").length == 0) {
        loadingEL = $("<div id='loadingMask' class='modal-backdrop loading'></div>");
        fixZIndex();
        loadingEL.appendTo(document.body);
      } else {
        loadingEL = $("#loadingMask");
        fixZIndex();
        loadingEL.removeClass("hide");
      }
      ns.global.loading = loadingEL;
    },
    // 隐藏页面loading
    stopLoading : function() {
      $(".loading:not(.hide)").addClass("hide");
    },
    // 显示包装过的弹出框
    alert : function(msg) {
      ns.use([ "forte_modal" ], function() {
        $.forte_alert(msg);
      });
    },
    // 显示包装过的提示框
    confirm : function(msg, confirmFN) {
      ns.use([ "forte_modal" ], function() {
        $.forte_confirm(msg, confirmFN);
      });
    },

    /**
     * 初始化 选择框
     * 
     * @param selectEL
     *          comboBox el
     * @param data
     *          option data list
     * @param options
     *          {emptyLabel:'',value:'',label:'',autoSelected:false,
     *          change:function(val){}}
     */
    initComboBox : function(selectEL, data, options) {
      data = data || [];
      var emptyLabel = options.emptyLabel || "全部";
      var value = options.value, label = options.label;
      var htmlContent = [ "<option value=''>" + emptyLabel + "</option>" ];
      for (var i = 0, len = data.length; i < len; i++) {
        var labelStr = data[i][label];
        if (typeof label == "function") {
          labelStr = label(data[i]);
        }
        htmlContent.push("<option value='" + data[i][value] + "'>" + labelStr + "</option>");
      }
      selectEL.empty().append(htmlContent.join(" "));
      if (options.autoSelected) {
        // 自动选中第一个
        selectEL.find("option[value!='']:eq(0)").attr("selected", true);
      }
      // 绑定选中事件
      if (typeof options.change == "function") {
        selectEL.bind("change", function() {
          options.change(selectEL.val());
        });
      }
    }
  };
})(FORTE);