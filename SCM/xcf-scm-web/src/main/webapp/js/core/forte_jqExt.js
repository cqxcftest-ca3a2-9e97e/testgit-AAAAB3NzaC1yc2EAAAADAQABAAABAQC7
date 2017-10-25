/**
 * 扩展jq库
 * 
 * @param $
 */
(function(ns, $) {

  // close ajax load cache
  $.ajaxSetup({
    cache : false
  });
  /**
   * 将form中的输入值返回成一个json对象
   */
  $.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
      if (o[this.name] !== undefined) {
        if (!o[this.name].push) {
          o[this.name] = [ o[this.name] ];
        }
        if (!ns.isEmpty(this.value)) {
          o[this.name].push($.trim(this.value));
        }
      } else {
        if (!ns.isEmpty(this.value)) {
          o[this.name] = $.trim(this.value);
        }
      }
    });
    return o;
  };

  /**
   * 将json数据填充到form表单中
   */
  $.fn.loadJSON = function(data) {
    var form = this;
    $.each(data, function(key, value) {
      var inputEL = form.find("[name='" + key + "']");
      if (inputEL.length > 0) {
        var type = inputEL.attr("type") || inputEL.prop("tagName").toLowerCase();
        switch (type) {
        case "text":
        case "hidden":
        case "textarea":
          inputEL.val(value);
          break;
        case "select":
          inputEL.find("option[value='" + value + "']").attr("selected", true);
          // inputEL.empty();
          // $(value).each(function(idx, item) {
          // var val = item, name = item;
          // if (typeof item === "object") {
          // val = item.value;
          // name = item.name;
          // }
          // inputEL.append("<option value='" + val + "'>" + name +
          // "</option>");
          // });
          // // 默认选中第一个
          // inputEL.find("option:first").attr("selected", true);
          break;
        case "checkbox":
          $(value).each(function(idx, item) {
            inputEL.each(function() {
              var inputVal = $(this).val();
              if (inputVal == item) {
                $(this).attr("checked", true);
              }
            });
          });
          break;
        case "radio":
          inputEL.each(function() {
            if ($(this).attr('value') == value) {
              $(this).attr("checked", true);
            }
          });
          break;
        }
      }
    });
  };

  /**
   * 限制输入框只能输入数字
   * 
   * @example $("#amount").numeric()
   */
  $.fn.numeric = function(options) {
    options = options || {
      // 允许小数点
      dot : true,
      // 允许负数
      negative : false
    };
    this.bind("keydown", function(e) {
      var code = e.which || e.keyCode;
      var keys = new Array(8, 9, 35, 36, 37, 38, 39, 40, 46);
      if (e.shiftKey) {
        return false;
      }
      var _indexOf = function(array, value) {
        for (var i = 0; i < array.length; i++) {
          if (value == array[i]) {
            return i;
          }
        }
        return -1;
      };
      var idx = _indexOf(keys, code);
      if (idx != -1) {
        return true;
      }

      if ((code >= 48 && code <= 57) || (code >= 96 && code <= 105) || (options.dot && code == 190)
          || (options.negative && code == 189)) {
        if (options.dot) {
          if (ns.isEmpty(this.value) && code == 190) {
            return false;
          }
          if (this.value.split(".").length == 2 && code == 190) {
            return false;
          }
        }
        if (options.negative) {
          if (this.value.indexOf("-") == 0 && code == 189) {
            return false;
          }
        }
      } else {
        return false;
      }
    });
    this.bind("blur", function(e) {
      if (this.value.lastIndexOf(".") == (this.value.length - 1)) {
        this.value = this.value.substr(0, this.value.length - 1);
      } else if (isNaN(this.value)) {
        this.value = "";
      }
    });
  };
})(FORTE, jQuery);