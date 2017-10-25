/*导航切换*/
function menu_show(){
	$(".menuone").click(function(){

		var menuchange = $(this).find("span").hasClass("up");

		if(menuchange){

			$(".menuone").find("span").removeClass("down");
			$(".menuone").find("span").addClass("up");
			 $(this).find("span").removeClass("up");
			 $(this).find("span").addClass("down");
			 $(".submenu").slideUp("fast");
			 $(this).next().slideDown("fast");
		}else{
			 $(this).find("span").removeClass("down");
			 $(this).find("span").addClass("up");
			 $(this).next().slideUp("fast");
		}
		//$(".submenu").slideUp("fast");
		// var menuchange = $(this).find("span").hasClass("up");
		// var menu = $(this).next();
		// if(menu.css("display")=="none"){
		// menu.slideDown("fast");
		// }else{
		// menu.slideUp("fast");
		// }
	});
    $(".expmenu .submenu a").click(function(){
    		$(".expmenu .submenu a").removeClass("submenu-click");
    		$(this).addClass("submenu-click");
    	});
    //$(".submenu-click").parent().css("display","block");
}
//select 下拉框样式
function selectstyle(){ 
	$(".js_select").click(function(){ 
		var ul = $(".js_rowinput ul"); 
		if(ul.css("display")=="none"){ 
		ul.slideDown("fast"); 
		}else{ 
		ul.slideUp("fast"); 
		} 
	}); 
	$(".f_icon_s4").click(function(){ 
		var ul = $(".js_rowinput ul"); 
		if(ul.css("display")=="none"){ 
		ul.slideDown("fast"); 
		}else{ 
		ul.slideUp("fast"); 
		} 
	}); 
	$(".js_rowinput ul li a").click(function(){ 
		var txt = $(this).text(); 
		$(".js_select").val(txt); 
		var value = $(this).attr("rel"); 
		$(".js_rowinput ul").hide(); 
	}); 

}
//五角星的显示
function star_five(){
	// var index = $('.starlist').attr('id');
	// var length = $(".starlist").length();
	// for (var i = 0; i < length; i++) {
	// 	$(".starlist").eq(index).addClass("star-five-change");
	// };
	
	$(".starlistself span").click(function(){
		$(this).parent().find("span").addClass("star-five-change");
		$(this).nextAll().removeClass("star-five-change");
	});
	$(".starlistself span").hover(function(){
		$(this).parent().find("span").addClass("star-five-change");
		$(this).nextAll().removeClass("star-five-change");
	},
	function(){
		$(this).parent().find("span").removeClass("star-five-change");
	}
	);
}
//屏幕高度
//function pageheight(){
//	var pageheight = $(window).height()-45;
//	$(".page-height").css("height",pageheight);
//}
// function pageheight(){
// 	var minheight = $(window).height()-165-45;
// 	var pageheight = $(".frame-content").height();
// 	$(".menubox").css("min-height",minheight);
// 	$(".frame-content").css("min-height",minheight);
// 	$(".page-height").css("min-height",minheight);
// 	$(".page-height").css("height",pageheight);
// }
//checkbox点击
function checkbox(){
	/*$('.chklist').hcheckbox();*/
}


/*弹出框效果*/
function boxshow(){
	$(".js_password").click(function(){
		$(".js_password_box").show();	
	});
	$(".js_password_close").click(function(){
		$(".js_password_box").hide();
	});
	/*$(".js_role_choice").click(function(){
		$(".js_role_box").show();
	});*/
	$(".js_role_close").click(function(){
		$(".js_role_box").hide();
	});
	$(".js_room_choice").click(function(){
		$(".js_room_box").show();
	});
	$(".js_room_close").click(function(){
		$(".js_room_box").hide();
	});
	$(".js_exit_show").click(function(){
		$(".js_exit_box").show();
	});
	$(".js_exit_close").click(function(){
		$(".js_exit_box").hide();
	});
	$(".js_repair_close").click(function(){
		$(".js_repair_box").hide();
	});
	
	
	$(".js_repair_show").click(function(){
		$(".js_repair_box").show();
	});
	$(".js_reply_show").click(function(){
		$(".js_reply_box").show();
	});
	$(".js_reply_close").click(function(){
		$(".js_reply_box").hide();
	});




	$(".js_confirm_close").click(function(){
		$(".js_confirm_box").hide();
	});
	$(".js_dispatch_show").click(function(){
		$(".js_dispatch_box").show();
	});
	$(".js_dispatch_close").click(function(){
		$(".js_dispatch_box").hide();
	});
	$(".js_visit_show").click(function(){
		$(".js_visit_box").show();
	});
	$(".js_visit_close").click(function(){
		$(".js_visit_box").hide();
	});
	$(".js_receipt_show").click(function(){
		$(".js_receipt_box").show();
	});
	$(".js_receipt_close").click(function(){
		$(".js_receipt_box").hide();
	});
	$(".js_uesr_show").mousemove(function(){
			$(".js_uesr_exit").show();
	});
	$(".js_uesr_show").mouseout(function(){
			$(".js_uesr_exit").hide();
	});
	$(".js_room_show").click(function(){
    		$(".js_room_box").show();
    	});
    	$(".js_room_close").click(function(){
    		$(".js_room_box").hide();
    	});
}
/*删除默认样式*/
function show_confirm(){
	/*var r=confirm("确认删除吗？");
	if (r==true)
	  {
	  
	  }
	else
	  {
	 	$(".box_style").hide();
	  }*/
}
/*删除列表*/
function del_role(){
	$(".choice-del").click(function(){
		$(this).parent().hide();
	});
	

}
/*维修人员管理与维修项目管理呈现的交替*/
function repair(){
	$("#postit li").click(function(){
		var index=$("#postit li").index(this);
		if (index==0) {
			$("#postit li").eq(0).removeClass("postit-child").addClass("postit-select");
			$("#postit li").eq(1).removeClass("postit-select").addClass("postit-child");
			$("#manage").show();
			$("#personnel").hide();

		} else{
			$("#postit li").eq(0).removeClass("postit-select").addClass("postit-child");
			$("#postit li").eq(1).removeClass("postit-child").addClass("postit-select");
			$("#personnel").show();
			$("#manage").hide();
		}

	})
}
/*个人住宅与室外维修的显示与隐藏*/
function repairtoggle(){
	var flag=true;
	var flag1=true;
	$("#house_1").click(function(){
		
		if (flag) {
			flag=false;
			$("#repair_1").children("li").hide();
			$(this).attr({src:"img/up.jpg"}); 
		} else{
			flag=true;
			$("#repair_1").children("li").show();
			$(this).attr({src:"img/dowm.jpg"});
		}
	});
	$("#house_2").click(function(){
		if (flag1) {
			flag1=false;
			$("#repair_2").children("li").hide();
			$("#house_2").attr({src:"img/up.jpg"}); 
		} else{
			flag1=true;
			$("#repair_2").children("li").show();
			$("#house_2").attr({src:"img/dowm.jpg"});
		}
	});
}
function box(level){
	//level总层数减1
	for (var j = 1; j < level-1; j++) {
		$('.'+'level'+j).click(function(){
		var this_tree = $(this).parent().attr('id');
		var checkbox = $('#'+this_tree+' input');
		console.log(checkbox[0].checked);
		if (checkbox[0].checked == true) {
			for (var i = 1; i < checkbox.length; i++) {
				checkbox[i].checked = true;
			};	
		}else{
			for (var i = 1; i < checkbox.length; i++) {
				checkbox[i].checked = false;
			};
		}
		});
	};
	
}

/*button置灰效果*/
function js_butdisabled(){
	$(".js_butdisabled").click(function(){
		$('.js_butdisabled').attr('disabled',"true");
		$('.js_butdisabled').removeClass("but1").addClass("but_disabled");
	});
	

}
/*分类管理页面分类的选中与移除*/
function classifylist(){
	/*$ (".js_classify").live ("click", function ()
	{
		if ($(this).hasClass("classify-on")) {
			$(this).removeClass("classify-on");
		} else{
			$(this).addClass("classify-on");
		}

	});*/
	$ ("#classfyList li").live ("click", function ()
	{	$ ("#classfySelected li").removeClass("classify-on");
		if ($(this).hasClass("classify-on")) {
			$(this).removeClass("classify-on");
		} else{
			$(this).addClass("classify-on");
		}

	});
	$ ("#classfySelected li").live ("click", function ()
	{	$ ("#classfyList li").removeClass("classify-on");
		if ($(this).hasClass("classify-on")) {
			$(this).removeClass("classify-on");
		} else{
			$(this).addClass("classify-on");
		}

	});


	$(".classify-add").click(function(){
		var num=0;
		addClassfy(num);
		function addClassfy(num){
			if ($("#classfyList li").eq(num).hasClass("classify-on")) {
				//$("#classfySelected").append("<li class='js_classify'>"+$("#classfyList li").eq(num).html()+"</li>");
				$("#classfySelected").append($("#classfyList li").eq(num));
				//$("#classfyList li").eq(num).remove();
				$("#classfySelected > li").removeClass("classify-on");
				addClassfy(num);
			}else{
				num++;
				if (num==$("#classfyList li").length) {
					return true;
				}else{
					addClassfy(num);
				}
			}
		}

	});
	$(".classify-remove").click(function(){
		var num=0;
		removeClassfy(num);
		function removeClassfy(num){
			if ($("#classfySelected li").eq(num).hasClass("classify-on")) {
				//$("#classfyList").append("<li class='js_classify'>"+$("#classfySelected li").eq(num).html()+"</li>");
				$("#classfyList").append($("#classfySelected li").eq(num));
				//$("#classfySelected li").eq(num).remove();
				$("#classfyList > li").removeClass("classify-on");
				removeClassfy(num);

			}else{
				num++;
				if (num==$("#classfySelected li").length) {
					return true;
				}else{
					removeClassfy(num);
				}
			}
		}
	});
}
function box(level){
	//level总层数减1
	for (var j = 1; j < level-1; j++) {
		$('.'+'level'+j).click(function(){
			var this_tree = $(this).parent().attr('id');
			var checkbox = $('#'+this_tree+' input');
			console.log(checkbox[0].checked);
			if (checkbox[0].checked == true) {
				for (var i = 1; i < checkbox.length; i++) {
					checkbox[i].checked = true;
				};
			}else{
				for (var i = 1; i < checkbox.length; i++) {
					checkbox[i].checked = false;
				};
			}
		});
	};

}

/*input[type=radio]*/
function inputRadio(){
	$(function() {
		$('label[type="radio"]').click(function(){
			var radioId = $(this).attr('name');
			$('label[type="radio"]').removeAttr('class');$(this).attr('class', 'checked');
			$('input[type="radio"]').removeAttr('checked');$('#' + radioId).attr('checked', 'checked');
		});
	});
}
/*button置灰效果*/
function js_butdisabled(){
	$(".js_butdisabled").click(function(){
		$('.js_butdisabled').attr('disabled',"true");
		$('.js_butdisabled').removeClass("but1").addClass("but_disabled");
	});
}

function disabledback(){
	$(".js_butdisabled").click(function(){
		$('.js_butdisabled').removeAttr("disabled");
		$('.js_butdisabled').removeClass("but_disabled").addClass("but1");
	});

}
$(document).ready(function(){
	js_butdisabled();
	repair();
	repairtoggle();
	menu_show();
	selectstyle();
	star_five();
	boxshow();
	del_role();
	classifylist();
	inputRadio();
	checkbox();
});