<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<head>
	<title><decorator:title /></title>
	<meta name="viewport" content="initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta content="telephone=no" name="format-detection" />
	<link rel="stylesheet" type="text/css" href="${staticFileRoot}/css/common.css">
	<link rel="stylesheet" type="text/css" href="${staticFileRoot}/css/style.css">
	<link rel="stylesheet" type="text/css" href="${staticFileRoot}/css/date.css">
	<script src="${staticFileRoot}/js/jquery-1.8.2.min.js"></script>
	<script src="${staticFileRoot}/js/jquery.cookie.js"></script>
	<script src="${staticFileRoot}/js/jquery.md5.js"></script>
	<script src="${staticFileRoot}/js/date.js"></script>
	<script src="${staticFileRoot}/js/all.js"></script>
	<script type="text/javascript">
		var staticFileRoot = "${staticFileRoot}";
		var ctx = staticFileRoot;
	</script>
</head>
<body>
<div class="all">
	<!--头部-->
	<div class="header clearfix">
		<div class="min-content">
			<div class="fl"><span class="f_icon_s f_icon_s1"></span></div>
		</div>
	</div>
	<decorator:body />
	<div class="footer" >
		<span class="content-span">备案号：<a href="http://www.miitbeian.gov.cn" target="_bank">沪ICP备16025730号</a></span>
		&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://webscan.360.cn/index/checkwebsite/url/www.91wujia.com" style="display:none" target="_bank" name="896eadcfe156181af05da596fab929f5" >网站安全检测平台</a>
	</div>
</div>
</body>
</html>