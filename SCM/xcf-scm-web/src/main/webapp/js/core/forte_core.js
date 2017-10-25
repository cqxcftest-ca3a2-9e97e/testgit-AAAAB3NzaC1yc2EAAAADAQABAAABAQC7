(function(win) {
  if (typeof win.FORTE === "undefined") {
    FORTE = {};
    FORTE.global = {};
    String.prototype.trim = function() {
      return this.replace(/^\s+|\s+$/g, "");
    };
    // 日期格式化
    Date.prototype.Format = function(fmt) {
      var o = {
        // 月份
        "m+" : this.getMonth() + 1,
        // 日
        "d+" : this.getDate(),
        // 小时
        "h+" : this.getHours(),
        // 分
        "i+" : this.getMinutes(),
        // 秒
        "s+" : this.getSeconds(),
        // 季度
        "q+" : Math.floor((this.getMonth() + 3) / 3),
        // 毫秒
        "S" : this.getMilliseconds()
      };
      if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
      for ( var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
      return fmt;
    };
  }
  var forte = {

    config : function(options) {
      FORTE.global.staticFileVersion = options.staticFileVersion;
      FORTE.global.contextPath = options.contextPath;
      FORTE.global.pageSize = options.pageSize;
    },

    isNull : function(value) {
      return value == null || typeof (value) == "undefined";
    },

    isEmpty : function(value) {
      return value === "" || value === null || typeof (value) === "undefined";
    },

    isType : function(type) {
      return function(obj) {
        return {}.toString.call(obj) == "[object " + type + "]";
      };
    },

    isArray : function(obj) {
      return this.isType("Array")(obj);
    },

    // 公共的ajax请求
    ajax : function(url, options) {
      var successFN = function(response) {
        var isErr = response.IsError;
        if (!isErr) {
          options.callback && options.callback(response);
        } else {// 系统异常
          alert(response.Message);
          FORTE.util.stopLoading();
        }
      };
      var data = options.data;
      if (options.type == "get") {
        data = $.param(data);
      }
      $.ajax({
        type : options && options.type || "GET",
        url : url,
        data : data,
        dataType : "json",
        success : successFN,
        pagination : false,
        cache : false,
        error : function(xhr, err) {
          var status = xhr.status;
          if (status != "0") {
            // alert("系统异常，请联系管理员!");
          }
          FORTE.util.stopLoading();
        }
      });
    },

    // 加载模板文件
    loadTpl : function(viewURL) {
      var reqURL = FORTE.global.contextPath + "/common/tpl";
      var res = $.ajax({
        url : reqURL,
        data : "url=" + viewURL,
        async : false
      });
      return res.responseText;
    }
  };
  FORTE = $.extend(FORTE, forte);
})(this);