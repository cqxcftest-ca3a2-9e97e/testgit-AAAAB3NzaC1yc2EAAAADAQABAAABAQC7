(function(ns) {
  var moduleArray = {

    "template" : {
      "js" : "../lib/art-template.min.js"
    },
    "forte_modal" : {
      "js" : "forte_modal.js"
    },
    "forte_datagrid" : {
      "js" : "forte_datagrid.js",
      "dependencies" : [ "template" ]
    },
    "forte_dropdown" : {
      "js" : "forte_dropdown.js"
    },
    "forte_validator" : {
      "js" : "forte_validator.js"
    },
    "forte_date" : {
      "js" : "forte_date.js",
      "dependencies" : [ "datepicker" ]
    },
    "forte_jqExt" : {
      "js" : "forte_jqExt.js"
    },
    "forte_utils" : {
      "js" : "forte_utils.js"
    },
    "forte_tree" : {
      "js" : "forte_tree.js",
      "dependencies" : [ "tree" ]
    },
    "forte_tab" : {
      "js" : "forte_tab.js"
    },
	"mmGrid" : {
	      "js" : "../plugins/mmGrid.js"
	 },
    "jquery-ui" :{
      "js" : "../plugin/jquery-ui/jquery-ui.min.js",
      "css" : "../plugin/jquery-ui/jquery-ui.min.css"
    }
  };

  var baseURL = "";

  ns.initJsModule = function() {
    var scripts = document.getElementsByTagName("script");
    for (var i = 0; i < scripts.length; i++) {
      var src = scripts[i].src;
      if (!src)
        continue;
      var m = src.match(/forte_loader\.js(\W|$)/i);
      if (m) {
        baseURL = src.substring(0, m.index);
        var idx = src.indexOf("?");
        if (idx > 0) {
          fileVersion = src.substring(idx + 1);
        }
        break;
      }
    }
  },

  ns.use = function(modules, callback) {
    var cssArray = [], jsArray = [], mm = [];
    if (typeof modules === "function") {
      var callback = modules;
      seajs.use([], function() {
        callback(ns);
      });
      return false;
    }
    if (!ns.isArray(modules) && typeof callback === "function") {
      modules = [ modules ];
    }

    function add(name) {
      if (!moduleArray[name])
        return;
      if ($.inArray(name, mm) == -1) {
        mm.push(name);
      }
      var d = moduleArray[name]['dependencies'];
      if (d) {
        for (var i = 0; i < d.length; i++) {
          add(d[i]);
        }
      }
    }

    for (var i = 0, len = modules.length; i < len; i++) {
      add(modules[i]);
    }
    // 插入公共的js模块
    mm.unshift("forte_jqExt", "forte_utils");
    for (var i = 0, len = mm.length; i < len; i++) {
      var moduleName = mm[i], moudleObj = moduleArray[moduleName];
      if (moudleObj["js"]) {
        var jsFiles = moudleObj["js"];
        if (ns.isArray(jsFiles)) {
          for (var j = 0, len = jsFiles.length; j < len; j++) {
            jsArray.push(baseURL + jsFiles[j]);
          }
        } else {
          jsArray.push(baseURL + jsFiles);
        }
      }
      if (moudleObj["css"]) {
        cssArray.push(baseURL + moudleObj["css"]);
      }
    }
    seajs.use(cssArray.concat(jsArray), function() {
      callback(ns);
    });
  };
  ns.initJsModule();
})(FORTE);