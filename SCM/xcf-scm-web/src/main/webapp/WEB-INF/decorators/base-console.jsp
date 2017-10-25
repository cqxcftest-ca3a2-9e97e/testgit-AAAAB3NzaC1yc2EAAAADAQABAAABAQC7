
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title><decorator:title /></title>
	<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta content="telephone=no" name="format-detection" />
	<link rel="stylesheet" type="text/css" href="${staticFileRoot}/css/common.css">
<%--	<link rel="stylesheet" type="text/css" href="${staticFileRoot}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${staticFileRoot}/css/date.css">--%>
	<link rel="stylesheet" type="text/css" href="${staticFileRoot}/css/bootstrap.min.css"/>

	<script src="${staticFileRoot}/js/jquery-1.8.2.min.js"></script>
	<link rel="stylesheet" type="text/css" href="${staticFileRoot}/js/plugin/jquery-ui/jquery-ui.min.css" />
	<script src="${staticFileRoot}/js/plugin/jquery-ui/jquery-ui.min.js"></script>
	<!--[ifltIE9]>
	<script type="text/javascript" src="${staticFileRoot}/js/lib/html5shiv.js"></script>
	<![endif]-->
	<script>
		/*window.alert = function(){};
		alert = function (msg) {
			FORTE.util.alert(msg);
		};
		window.confirm=function(msg){};
		confirm = function(msg,func){
			FORTE.util.confirm(msg,func);
		}*/
	</script>
</head>
<body>
<div class="all">
	<!--头部-->
	<div class="header clearfix">
		<div class="hader-content">
			<div class="fl"><span class="f_icon_s f_icon_s1"></span></div>
		</div>
	</div>
	<!--中间内容-->
	<div class="content page-height clearfix">
		<div class="frame-content">
			<decorator:body />
		</div>
	</div>
</div>
</body>
</html>