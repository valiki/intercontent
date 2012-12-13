<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
final Enumeration attributeNames = request.getAttributeNames();
while(attributeNames.hasMoreElements()){
    String el = (String)attributeNames.nextElement();
    pageContext.setAttribute(el.replaceAll("\\.", "_"), request.getAttribute(el));  
}
%>
<html>
<head>
<style>
	*{
	font-family: Tahoma, Arial, Helvetica, sans-serif;
	}
</style>
<title>
Error ${javax_servlet_error_message}
</title>
</head>
<body style="margin:auto;text-align:center;">
<h1>An error has occured</h1>
<h2>Error code ${javax_servlet_error_status_code}</h2>
<h2>request URI ${javax_servlet_error_request_uri}</h2>
<h2>Servlet name ${javax_servlet_error_servlet_name}</h2>
<h2>Message ${javax_servlet_error_message}</h2>
<h2>Exception type ${javax_servlet_error_exception_type}</h2>
<h2>Exception ${javax_servlet_error_exception}</h2>
<h3>Stack trace:</h3>
<div style="font-size:18px;">
<c:forEach items="${javax_servlet_error_exception.stackTrace}" var="stackTraceElement">
	<div style="padding-top:2px; ">
	${stackTraceElement}
	</div>
</c:forEach>
</div>
</body>
</html>