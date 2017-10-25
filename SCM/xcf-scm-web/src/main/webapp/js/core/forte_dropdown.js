(function(ns) {

  var Dropdown = function(element) {
    this.$element = $(element);
    this.$toggle = this.$element.find(".dropdown-toggle");
    this.dropdown = this.$element.find(".dropdown-menu");
    this.$toggle.on("click", $.proxy(this.toggle, this));
  };

  Dropdown.prototype = {

    constructor : Dropdown,

    toggle : function() {
      var isActive = this.$element.hasClass("open");
      clearMenus();
      if (!isActive) {
        var height = this.$toggle.outerHeight(true);
        this.dropdown.css({
          top : height,
          left : 0
        });
        this.dropdown.removeClass("hide");
        this.$element.addClass("open");
      } else {
        this.dropdown.addClass("hide");
        this.$element.removeClass("open");
      }
    }
  };

  $.fn.forte_dropdown = function(option) {
    return this.each(function() {
      var $this = $(this), data = $this.data("dropdown");
      if (!data) {
        $this.data("dropdown", (data = new Dropdown(this)));
      }
      if (typeof option == "string") {
        data[option].call($this);
      }
    });
  };

  function clearMenus() {
    $(".dropdown .dropdown-menu").each(function() {
      var dropdown = $(this);
      dropdown.parent().removeClass("open");
      dropdown.addClass("hide");
    });
  }

  $(document).on("click", function(e) {
    var target = $(e.target);
    if (target.hasClass("dropdown-toggle") || $.contains(target, ".dropdown-toggle")) {
    } else {
      clearMenus();
    }
  });

})(FORTE);