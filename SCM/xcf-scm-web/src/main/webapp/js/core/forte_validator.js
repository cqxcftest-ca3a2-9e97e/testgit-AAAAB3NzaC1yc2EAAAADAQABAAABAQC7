(function(ns) {

  var rules = {
    "required" : {
      error : "不可以为空",
      validate : function(value) {
        return !!value;
      }
    },
    "pattern" : {
      error : "不符合要求",
      validate : function(value, attr) {
        new RegExp(attr).test(value);
      }
    },
    "email" : {
      error : "邮箱地址格式不合法",
      validate : function(value) {
        if (value != null && value != '') {
          return /^(?:\w+\.?\w?\-?)*\w+@(?:\w+\.)+\w+$/.test(value);
        }
        return true;
      }
    },

    "number" : {
      error : "必须是数字",
      validate : function(value) {
        if (value != null && value != '') {
          return /^([+-]?)\d*\.?\d+$/.test(value);
        }
        return true;
      }
    },
    
    "positiveInt" : {
      error : "必须是正整数",
      validate : function(value) {
        if (value != null && value != '') {
          return /^\d*$/.test(value);
        }
        return true;
      }
    },
   
    "mobile" : {
      error : "手机号码格式不合法",
      validate : function(value) {
        if (value != null && value != '') {
          return /^0?\d{9,11}$/.test(value);
        }
        return true;
      }
    },
    "date" : {
      error : "日期格式不合法",
      validate : function(value) {
        if (value != null && value != '') {
          return /^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\2(?:29))$/
              .test(value);
        }
        return true;
      }
    },
    "telephone" : {
      error : "电话号码码格式不合法",
      validate : function(value) {
        return true;
      }
    },
    "postcode" : {
      error : "邮政编码格式不合法",
      validate : function(value) {
        if (value != null && value != '') {
        return /^[\d]{6}$/.test(value);}
        return true;
      }
    },
    "url" : {
      error : "URL地址格式不合法",
      validate : function(value) {
        return true;
      }
    },
    "idCard" : {
      error : "身份证号码不对",
      validate : function(value) {
        return /^(\d{6})(18|19|20)?(\d{2})([01]\d)([0123]\d)(\d{3})(\d|X)?$/.test(value);
      }
    },
    "max" : {
      error : "",
      validate : function(value) {
        return true;
      }
    },
    "min" : {
      error : "",
      validate : function(value) {
        return true;
      }
    },
    "equal-field" : {
      error : "",
      validate : function(value) {
        return true;
      }
    },
    "longitude" : {
      error : "经度格式不合法,必须是0-180之间的数值,小数位数最多为6位",
      validate : function(value) { 
        var flag = true;
        var patt =/^[+]?\d+(\.\d{1,6})?$/;
        if (value != null && value != '') {
          flag = patt.test(value);
        }
        if(flag){
          if(value>180 || value<0){
            flag=false;
          }
        }
        return flag;
      }
    },
    "latitude" : {
      error : "纬度格式不合法,必须是0-90之间的数值,小数位数最多为6位",
      validate : function(value) { 
        var flag = true;
        var patt =/^[+]?\d+(\.\d{1,6})?$/;
        if (value != null && value != '') {
          flag = patt.test(value);
        }
        if(flag){
          if(value>90 || value<0){
            flag=false;
          }
        }
        return flag;
      }
    }
  };

  var bindEvt = function(form) {
    var formEL =$(form);
    if (formEL.data("bindEvt")) {
      return false;
    }
    $(form+" input[validator] ,"+form+" select[validator],"+form+" radio[validator],"+form+" checkbox[validator],"+form+" textArea[validator]").each(function() {
      var target = $(this);
      var type = target.prop("tagName").toLowerCase(), event = 'blur';
      switch (type) {
      case "select":
        event = 'change';
        break;
      case "radio":
        event = 'click change';
        break;
      case "checkbox":
        event = 'click change';
        break;
      default:
        event = 'blur';
      }
      var field = new Field(target);
      field.addEvt(event);
    });
    
    $(form+" input[trim]").each(function() {
      var value = $.trim($(this).val());
      $(this).val(value);
    });
    
    formEL.data("bindEvt", true);
  };

  function Field(target) {
    this.target = target;
    this.validator = this.target.attr("validator");
  }

  Field.prototype = {
    test : function(options) {
      var that = this, ret = true;
      $.each(this.validator.split(" "), function(idx, val) {
        // 如果元素处于hidden状态，并且系统不校验隐藏的元素，直接pass
        if (that.target.is(":hidden") && options.ignoreHide) {
          return false;
        }
        var passed = rules[val].validate(that.target.val() || that.target.attr("data-value"));
        if (!passed) {
          if (!that.target.hasClass("invalidInput")) {
            that.target.addClass("invalidInput");
            that.target.parent().append("<div class='invalidText'>" + rules[val].error + "</div>");
          } else {
            that.target.parent().find(".invalidText").remove();
            that.target.parent().append("<div class='invalidText'>" + rules[val].error + "</div>");
          }
          ret = false;
          return false;
        } else {
          that.target.removeClass("invalidInput");
          that.target.parent().find(".invalidText").remove();
        }
      });
      return ret;
    },

    addEvt : function(evt) {
      var that = this;
      that.target.on(evt, function() {
        that.test();
      });
    }
  };

  /**
   * 表单校验;
   * 
   * @param options:{ignoreHide:false}
   *          ignoreHide:忽略隐藏的元素
   */
  ns.checkForm = function(form, options) {
    var formEL = $(form);
    $(form +" input[trim]").each(function(index, element) {
      $(this).on('change',function(){
        var value = $.trim($(this).val());
        $(this).val(value);
      });
    });
    
    // 屏蔽掉html5的校验
    formEL.attr("novalidate", "novalidate");
    // 隐藏的元素也需要校验
    options = options || {
      ignoreHide : false
    };

    // 绑定校验的事件
    bindEvt(form);
    var ret = true;
    $(form+" input[validator] ,"+form+" select[validator],"+form+" radio[validator],"+form+" checkbox[validator],"+form+" textArea[validator]").each(function() {
      var target = $(this);
      if (target.attr("validator")) {
        var field = new Field(target);
        var passed = field.test(options);
        ret = ret && passed;
      }
    });
    $(form+" .addrSelector").each(function() {
      var target = $(this);
      if (target.attr("validator")) {
        var field = new Field(target);
        var passed = field.test(options);
        ret = ret && passed;
      }
    });
    return ret;
  };

  /**
   * 重置form表单时，去掉一些校验失败的信息
   */
  ns.resetForm = function(form) {
    var formEL = $(form);
    // 清除掉之前的校验信息
    formEL.find(".invalidInput").each(function() {
      var target = $(this);
      target.removeClass("invalidInput");
      target.parent().find(".invalidText").remove();
    });
    formEL.get(0).reset();
  };
})(FORTE);