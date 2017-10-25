(function(ns) {
  var date = {

    /**
     * 
     * 字符串转Date类型
     * 
     */
    toDate : function(value) {
      if (this.isDateTime(value)) {
        var year, month, day, hour, minute, second;
        var dateStr = value;
        var timeStr = "";
        var tmp;
        hour = 0;
        minute = 0;
        second = 0;
        if (value.length >= 10 && value.indexOf(" ") > 0) {
          tmp = value.split(" ");
          dateStr = tmp[0];
          timeStr = tmp[1];
        }
        if (/^[\d]{4}[^0-9]$/.test(dateStr.substring(0, 5))) {// 日期有分割符
          separator = dateStr.substring(4, 5);
          tmp = dateStr.split(separator);
          year = parseInt(tmp[0], 10);
          month = parseInt(tmp[1], 10);
          day = parseInt(tmp[2], 10);
        } else {
          year = parseInt(dateStr.substring(0, 4), 10);
          month = parseInt(dateStr.substring(4, 6), 10);
          day = parseInt(dateStr.substring(6, 8), 10);
        }
        if (timeStr.indexOf(":") > 0) {
          tmp = timeStr.split(":");
          if (tmp.length >= 3) {
            hour = parseInt(tmp[0], 10);
            minute = parseInt(tmp[1], 10);
            second = parseInt(tmp[2], 10);
          } else if (tmp.length >= 2) {
            hour = parseInt(tmp[0], 10);
            minute = parseInt(tmp[1], 10);
          } else if (tmp.length >= 1) {
            hour = parseInt(tmp[0], 10);
          }
        } else {
          if (timeStr.length >= 6) {
            hour = parseInt(timeStr.substring(0, 2), 10);
            minute = parseInt(timeStr.substring(2, 4), 10);
            second = parseInt(timeStr.substring(4, 6), 10);
          } else if (timeStr.length >= 4) {
            hour = parseInt(timeStr.substring(0, 2), 10);
            minute = parseInt(timeStr.substring(2, 4), 10);
          } else if (timeStr.length < 3 && timeStr.length > 1) {
            hour = parseInt(timeStr, 10);
          }
        }
        return new Date(year, month - 1, day, hour, minute, second);
      } else {
        alert("不是合法的日期格式，不能转换成日期对象！");
        return null;
      }
    },

    /**
     * 
     * 2个日期比较大小，
     * 
     * 如果date1大于date2则返回1
     * 
     * 如果date1等于date2则返回0
     * 
     * 如果date1小于date2则返回-1
     * 
     * endDate不大于startDate, 则返回true,否则返回false。
     * 
     */
    compareDate : function(date1, date2) {
      if (date1.constructor != Date) {
        date1 = this.toDate(date1);
      }
      if (date2.constructor != Date) {
        date2 = this.toDate(date2);
      }
      if (date1.getTime() > date2.getTime()) {
        return 1;
      } else if (date1.getTime() < date2.getTime()) {
        return -1;
      } else {
        return 0;
      }
    },
    
    betweenDate : function(date1, date2) {
        if (date1.constructor != Date) {
          date1 = this.toDate(date1);
        }
        if (date2.constructor != Date) {
          date2 = this.toDate(date2);
        }
        var dates = Math.abs(date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24);
        return dates;
      },

    /*
     * 函数功能：判断传入参数是否为日期时间格式：yyyy-mm-dd或yyyy/mm/dd或yyyyMMdd(HH:mm:ss或HHmmss)
     * 
     * 日期和时间用空格分开
     * 
     * 如果是，则返回一个对应的日期对象
     * 
     * 如果否，则返回false
     * 
     */
    isDateTime : function(value) {
      var dateStr, timeStr;
      value = value.trim(value);
      if (value.indexOf(" ") > 0) {
        tmp = value.split(" ");
        dateStr = tmp[0];
        timeStr = tmp[1];
      } else {
        dateStr = value;
        timeStr = "";
      }
      if (dateStr.length < 8) {
        return false;
      }
      var year = "2013", month = "01", day = "01";
      var flag = true;
      if (/^[\d]{4}[\/]((0?[1-9])|(1[0-2]))[\/]((0?[1-9])|([1-2][\d])|(3[0-1]))$/.test(dateStr)
          || /^[\d]{4}[\-]((0?[1-9])|(1[0-2]))[\-]((0?[1-9])|([1-2][\d])|(3[0-1]))$/.test(dateStr)
          || /^[\d]{4}[.]((0?[1-9])|(1[0-2]))[.]((0?[1-9])|([1-2][\d])|(3[0-1]))$/.test(dateStr)) {
        separator = dateStr.substring(4, 5);
        tmp = dateStr.split(separator);
        year = parseInt(tmp[0], 10);
        month = parseInt(tmp[1], 10);
        day = parseInt(tmp[2], 10);
      } else if (/^[\d]{4}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][\d])|(3[0-1]))$/.test(dateStr)) {
        year = parseInt(dateStr.substring(0, 4), 10);
        month = parseInt(dateStr.substring(4, 6), 10);
        day = parseInt(dateStr.substring(6, 8), 10);
      } else {
        flag = false;
      }
      if (!flag)
        return false;
      if (timeStr != "") {
        if (!(/^(([0-1]?[0-9])|(2[0-3]))(:([0-5]?[0-9])(:([0-5]?[0-9]))?)?$/.test(timeStr) || /^(([0-1][0-9])|(2[0-3]))([0-5][0-9])([0-5][0-9])$/
            .test(timeStr))) {
          return false;
        }
      }
      if (month == 2) {
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
          if (day > 29) {
            return false;
          }
        } else {
          if (day > 28) {
            return false;
          }
        }
      } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
        if (day > 31) {
          return false;
        }
      } else if (month == 4 || month == 6 || month == 9 || month == 11) {
        if (day > 30) {
          return false;
        }
      }
      return true;
    },
    /**
     * 计算两个日期的间隔天数（yyyy-MM-dd）
     * 
     * startDate 开始日期
     * 
     * endDate 结束日期
     */
    calDateBetween : function(startDate, endDate) {
		var startTime = new Date(Date.parse(startDate.replace(/-/g, "-"))).getTime();
		var endTime = new Date(Date.parse(endDate.replace(/-/g, "-"))).getTime();
		var dates = Math.abs((startTime - endTime)) / (1000 * 60 * 60 * 24);
		return dates;
	}
  };
  ns.date = date;
})(FORTE);