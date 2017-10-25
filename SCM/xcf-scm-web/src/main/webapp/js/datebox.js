var logic = function( currentDateTime ) {
	if (currentDateTime==null) {
		return false;
	} else{
		if (currentDateTime.getDay() == 6) {
			this.setOptions({minTime: '11:00'});
		} else{
			this.setOptions({minTime: '8:00'});
		}
	}
};
$('#datetimepicker1').datetimepicker({
	onChangeDateTime:logic,
	onShow:logic,
	lang:'ch',
	format: 'Y/m/d', //格式化日期
	timepicker:false,    //关闭时间选项
	yearStart:2015,     //设置最小年份
	yearEnd:2099,        //设置最大年份
	todayButton:true    //关闭选择今天按钮
});
$('#datetimepicker2').datetimepicker({
	onChangeDateTime:logic,
	onShow:logic,
	lang:'ch',
	format: 'Y/m/d', //格式化日期
	timepicker:false,    //关闭时间选项
	yearStart:2015,     //设置最小年份
	yearEnd:2099,        //设置最大年份
	todayButton:true    //关闭选择今天按钮
});