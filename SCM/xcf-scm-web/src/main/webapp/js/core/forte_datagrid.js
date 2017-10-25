(function($) {

  // 纵向滚动条的宽度
  var scrollbarWidth = 0;
  function getScrollbarWidth() {
    if (scrollbarWidth)
      return scrollbarWidth;
    var div = $('<div style="width:50px;height:50px;overflow:hidden;position:absolute;top:-200px;left:-200px;"><div style="height:100px;"></div></div>');
    $('body').append(div);
    var w1 = $('div', div).innerWidth();
    div.css('overflow-y', 'auto');
    var w2 = $('div', div).innerWidth();
    $(div).remove();
    scrollbarWidth = (w1 - w2);
    return scrollbarWidth;
  }

  scrollbarWidth = getScrollbarWidth();
  /**
   * 数据列表组件
   * 
   */
  var DataGrid = function(element, options) {
    this.options = options;
    this.$element = $(element);
    this.renderUI();
    this.bindUI();
    if (this.options.autoLoad) {
      this.loadData();
    }
    if (this.options.showPage) {
      this.initPagination();
    }
    this.dataList = [];

    // 将grid对象传入到模板中，用于处理 基表code的转换
    template.helper("grid", this.$element);
  };

  DataGrid.prototype = {

    renderUI : function() {
      if (this.options.multiSelecte) {
        var checkBox = this.$element.find("thead tr>th>input");
        checkBox.addClass("checkAll");
        checkBox.removeClass("radio");
        checkBox.removeAttr("disabled");
      } else {
        var checkBox = this.$element.find("thead tr>th>input");
        checkBox.addClass("radio");
        checkBox.removeClass("checkAll");
        checkBox.attr("disabled", "disabled");
      }
      if (this.options.scrollable) {// 支持纵向滚动条
        if (this.options.width == "100%") {
          this.options.width = this.$element.outerWidth() - scrollbarWidth;
        }
        var tb = this.$element.addClass("tablescroll_body");
        var wrapper = null;
        if (this.$element.parent().hasClass("tablescroll_wrapper")) {
          wrapper = tb.parent();
        } else {
          wrapper = $('<div class="tablescroll_wrapper"></div>').insertBefore(tb).append(tb);
        }
        wrapper.wrap("<div class='tablescroll'></div>");
        var width = this.options.width;
        wrapper.css({
          width : width + scrollbarWidth + "px",
          height : "auto",
          overflow : "auto"
        });
        tb.css('width', width + 'px');
        var thead_tr_first = $('thead tr:first', tb);
        var colsOption = [];
        $('th, td', thead_tr_first).each(function(i) {
          var w = $(this).width();
          colsOption.push({
            width : w
          });
          $('th:eq(' + i + '), td:eq(' + i + ')', thead_tr_first).css('width', w + 'px');
        });
        var tbh = $('<table class="tablescroll_head table" cellspacing="0"></table>').insertBefore(wrapper).prepend(
            $('thead', tb));
        tbh.css("width", width);

        this.tb = tb;
        this.wrapper = wrapper;
        this.tbh = tbh;
        this.colsOption = colsOption;
      }

    },

    bindUI : function() {
      var that = this;
      if (this.options.multiSelecte) {
        // 全选
        this.$element.find("input.checkAll").on("click", function() {
          var target = $(this);
          var checkName = target.attr("name");
          $("input[name='" + checkName + "']:gt(0)").prop("checked", target.prop('checked'));
          if (target.prop('checked')) {
            target.closest(".table").find("tr").addClass("selected");
          } else {
            target.closest(".table").find("tr").removeClass("selected");
          }
        });
      } else {
        // 单选
        this.$element.find("input.radio").prop("disabled", "disabled");
      }
      // 选中高亮
      this.$element.delegate("tr input[type='checkbox']", "click", function() {
        var checkbox = that.$element.find("thead th input[type='checkbox']");
        if (checkbox.hasClass("radio")) {
          $(this).closest("tr").toggleClass("selected");
          var siblingsTR = $(this).closest("tr").siblings("tr");
          siblingsTR.find(":input:checked").prop("checked", false).closest("tr").removeClass("selected");
        } else {
          $(this).closest("tr").toggleClass("selected");
        }
      });
      // 点击表格title，隐藏&显示表格
      this.$element.find("caption").on("click", function() {
        that.$element.find("tbody").toggle();
      });
    },

    /**
     * 初始化分页导航
     */
    initPagination : function() {
      var pagination = new Pagination(this.$element, $.proxy(
          this.loadData, this));
      this.$element.data("pagination", pagination);
    },

    widthWrapper : function() {
      if (this.options.width == "100%") {
        return false;
      }
      if (this.options.scrollable) {
        // TODO,同时支持 横向和纵向滚动条
      } else {
    	alert(!this.$element.data("wrapped"));
        if (!this.$element.data("wrapped")) {
          // 给table增加宽度的wrapper
          this.$element.css("width", this.options.width + "px");
          this.$element.wrap("<div style='overflow:auto;'><div>");
          this.$element.data("wrapped", true);
          // 当table的宽度超出时，限制分页栏的宽度
          if (this.$element.data("pagination").paginationEL.closest("div.modal-body").length > 0) {
            var centerWidth = this.$element.data("pagination").paginationEL.closest("div.modal-body").width();
            if (this.options.width < centerWidth) {
              this.$element.data("pagination").paginationEL.css("width", this.options.width);
            }
          } else {
            var centerWidth = $("div.mainContent").width();
            if (this.options.width < centerWidth) {
              this.$element.data("pagination").paginationEL.css("width", this.options.width);
            }
          }
        }
      }
    },

    // ajax请求回来之后，填充数据列表
    fillData : function(data) {
      var that = this;
      var list = this.dataList = data.dataList || data.list || data.pageList || [];
      // 填充数据
      if (list.length > 0) {
        var htmlContent = template.render(this.options.tpl, data);
        this.$element.find("tbody").html(htmlContent);
      } else {
        this.emptyData();
      }
      // 转换基表中的code
      $.each(this.options.ref, function(idx, refObj) {
        var refType = refObj.type;
        var colIndex = refObj.index;
        that.$element.find("tbody tr").each(function() {
          var row = $(this);
          var refCol = row.find("td:eq(" + colIndex + ")");
          var code = refCol.html(); 
          var refTypeObj = that.$element.data(refType);
          if(typeof refTypeObj != "undefined"){
    		  $.each(refTypeObj, function(idx, item) {
                  if (item.paramCode == code) {
                      refCol.html(item.paramDesc);
                  }else if(item.paramCode == "CONVERT_MILLISECOND"){
                	  if(typeof code != "undefined" && code != ""){
                		  refCol.html(new Date(parseInt(code)).Format('yyyy-mm-dd hh:ii:ss'));
                	  }else{
                		  refCol.html("");
                	  }
                  }
              });
    	  }          
        });
      });
      // 显示分页导航
      if (this.options.showPage) {
        if(this.$element.data("pagination")!=null){
          this.$element.data("pagination").refresh(data.totalRecords, data.totalPage, data.currPage);
        }
      }
      // 处理自定义的宽度
      this.widthWrapper();
      // 处理纵向的滚动条
      if (this.options.scrollable) {
        var flush = false;
        var thead_tr_first = this.tbh.find('thead tr:first');
        var tbody_tr_first = this.tb.find('tbody tr:first');
        var width = this.options.width;
        if (this.tb.outerHeight() > this.options.height) {
          this.tbh.css("width", width + scrollbarWidth);
          this.wrapper.css({
            width : width + scrollbarWidth,
            "height" : this.options.height
          });
          flush = true;
        } else {
          this.wrapper.css({
            width : width,
            "height" : "auto"
          });
          this.tbh.css("width", width);
          this.tbh.find("tr:last th:last").width("-17");
        }
        $(this.colsOption).each(function(i, item) {
          var w = item.width;
          $('th:eq(' + i + '), td:eq(' + i + ')', tbody_tr_first).css('width', w + 'px');
          if (flush) {
            $('th:last', thead_tr_first).css('width', (w + scrollbarWidth) + 'px');
          }
        });
        this.tb.find("tbody tr:last td").css("border-bottom", "0");
      }
    },

    emptyData : function() {
      var cols = this.$element.find("thead th").length;
      this.$element.find("tbody").html(
          "<tr style='height:40px;'><td colspan='" + cols + "' align='center' class='empty'>没有数据</td></tr>");
    },

    /**
     * 获取datagrid中的数据
     * 
     * @param params
     *          filter:过滤函数
     * @returns {Array}
     */
    getData : function(params) {
      var filter = params && params["filter"];
      if (!filter) {
        return this.dataList;
      } else {
        var ret = [], list = this.dataList;
        for (var i = 0, len = list.length; i < len; i++) {
          if (typeof filter === "function" && filter(list[i])) {
            ret.push(list[i]);
          }
        }
        return ret;
      }
    },

    // ajax请求数据
    loadData : function(params) {
      var data = this.options.params;
      if (params) {
        data = $.extend({}, data, params);
        if (params.url) {
          this.options.url = params.url;
        }
      }
      var that = this;
      this.startLoading();
      $.ajax({
        url : this.options.url,
        data : $.param(data),
        dataType : "json",
        cache : false,
        success : function(response) {
          if (response.isError) {
            alert(response.message);
          } else {
            that.fillData(response);
          }
          // 数据加载完成之后，触发事件
          that.$element.trigger("afterLoadData");
          that.stopLoading();
        },
        error : function() {
          that.stopLoading();
        }
      });
    },

    // 重新load数据
    reload : function(params) {
      this.loadData(params);
      this.options.params = $.extend({}, $.fn.forte_datagrid.defaults.params, params);
    },

    clean : function() {
      this.dataList = [];
      this.emptyData();
      this.$element.data("pagination").paginationEL.addClass("hide");
    },

    // 显示加载数据的loading
    startLoading : function() {
      this.loadingId = this.$element.attr("id") + "-loading";
      var tableTitleHeight = this.$element.find("caption").outerHeight(true) || 0;
      // 设置loading图标的位置
      var setLoadingPos = function(scope) {
        var pos = scope.$element.position();
        scope.loadingEL.css({
          width : Math.max(scope.$element.width(), 200),
          height : 40,
          top : pos.top + 40 + tableTitleHeight,
          left : pos.left
        });
      };
      if ($("#" + this.loadingId).length == 0) {
        this.loadingEL = $("<div id='" + this.loadingId
            + "' class='loading table-loading'><span class='table-loading-text'>数据加载中...</span></div>");
        this.loadingEL.insertBefore(this.$element);
        // Tips:如果table还没有显示时，position的取值不对,所以这里用一个定时器去处理
        if (this.$element.is(":visible")) {
          setLoadingPos(this);
        } else {
          var that = this;
          var timer = setInterval(function() {
            if (that.$element.is(":visible")) {
              setLoadingPos(that);
              if (timer) {
                clearInterval(timer);
              }
            }
          }, 100);
        }
      } else {
        this.loadingEL.removeClass("hide");
      }
    },

    // 关闭加载数据的loading
    stopLoading : function() {
      this.loadingEL.addClass("hide");
    }
  };

  /**
   * 分页类
   */
  var Pagination = function(listEL, loadDataFN) {
    //this.paginationEL = $("#paginationNew");
	if(listEL.parent().find(".pagination").size()>0){
		this.paginationEL = listEL.parent().find(".pagination");
	}else{
		this.paginationEL = $("#paginationNew");
	}
	
    this.loadDataFN = loadDataFN;
    // 显示数据
    this.paginationEL.find(".totalCount").html(this.totalCount);
    this.paginationEL.find(".currentPage").html(this.currentPage);
    this.paginationEL.find(".totalPage").html(this.totalPage);
    // 绑定事件
    this.paginationEL.find(".firstPage").on("click", $.proxy(this.firstPage, this));
    this.paginationEL.find(".lastPage").on("click", $.proxy(this.lastPage, this));
    this.paginationEL.find(".prevPage").on("click", $.proxy(this.prevPage, this));
    this.paginationEL.find(".nextPage").on("click", $.proxy(this.nextPage, this));
    this.paginationEL.find(".goto").on("click", $.proxy(this.gotoPage, this));
  };

  Pagination.prototype = {
    firstPage : function() {
      if (this.totalCount > 0 && this.currentPage != 1) {
        this.currentPage = 1;
        this.loadDataFN({
          currPage : this.currentPage
        });
      }
    },

    nextPage : function() {
      if (this.totalCount > 0 && this.currentPage < this.totalPage) {
        this.currentPage = this.currentPage + 1;
        this.loadDataFN({
          currPage : this.currentPage
        });
      }
    },

    prevPage : function() {
      if (this.totalCount > 0 && this.currentPage > 1) {
        this.currentPage = this.currentPage - 1;
        this.loadDataFN({
          currPage : this.currentPage
        });
      }
    },

    lastPage : function() {
      if (this.totalCount > 0 && this.currentPage < this.totalPage) {
        this.currentPage = this.totalPage;
        this.loadDataFN({
          currPage : this.currentPage
        });
      }
    },

    gotoPage : function() {
      var pageVal = parseInt(this.paginationEL.find(".page").val());
      if (isNaN(pageVal) || pageVal > this.totalPage || pageVal < 1) {
        alert("输入的页码不正确!");
        this.paginationEL.find(".page").val("");
      } else {
        this.currentPage = pageVal;
        this.loadDataFN({
          currPage : this.currentPage
        });
      }
    },

    refreshUI : function() {
      this.paginationEL.find(".totalCount").html(this.totalCount);
      this.paginationEL.find(".currentPage").html(this.currentPage);
      this.paginationEL.find(".totalPage").html(this.totalPage);
      this.paginationEL.find(".page").val("");
    },

    refresh : function(totalCount, totalPage, currentPage) {
      if (totalCount == 0) {
        this.paginationEL.addClass("hide");
      } else {
        this.paginationEL.removeClass("hide");
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        if (this.totalCount == 0) {
          this.totalPage = 0;
          this.currentPage = 0;
        }
        this.refreshUI();
      }
    }
  };

  $.fn.forte_datagrid = function(option) {
    var $this = $(this);
    var grid = $this.data("grid");
    if (!grid) {
      var options = $.extend({}, $.fn.forte_datagrid.defaults, option);
      options.params = $.extend({}, $.fn.forte_datagrid.defaults.params, option.params);
      grid = new DataGrid(this, options);
      $this.data("grid", grid);
    } else {
      if (typeof option === "string") {
        return grid[option](Array.prototype.slice.call(arguments, 1)[0]);
      } else {
        grid.options.params = $.extend({}, $.fn.forte_datagrid.defaults.params, option.params);
        grid.loadData();
      }
    }
    return $this;
  };  

  $.fn.forte_datagrid.defaults = {
    // js模板
    tpl : "tpl-list",
    // 查询参数
    params : {
      currPage : 1,
      pageSize : 10
    },
    // 是否多选
    multiSelecte : false,
    // 自动加载
    autoLoad : true,
    // 数据请求URL
    url : "",
    height : 100,
    // 当设置的宽度大于父节点时，出现横向的滚动条
    width : "100%",
    // 是否显示分页
    showPage : true,
    // 是否支持纵向滚动
    scrollable : false,
    // 基表数据引用
    ref : []
  };
  
  $.fn.forte_datagrid.Constructor = DataGrid;
})(jQuery);