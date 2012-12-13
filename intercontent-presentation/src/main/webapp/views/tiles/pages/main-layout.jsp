<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="secure" uri="http://www.springframework.org/security/tags" %>
<tiles:importAttribute name="title" ignore="false"/>
<html>
<head>
<title>
${title}
</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="shortcut icon" href="/favicon.ico"/>
<script type="text/javascript" src="<c:url value='/js/jquery.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/common.js'/>"></script>
<script type="text/javascript" src="<c:url value='/js/jquery.scrollTo-min.js'/>"></script>
<tiles:insertAttribute name="headerScripts"/>
<tiles:insertAttribute name="headerStyles"/>
</head>
<body style="background: #FFFFFF; margin: 0px;">
	
</body>
</html>