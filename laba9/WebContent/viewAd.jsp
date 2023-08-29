<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@taglib prefix="my" tagdir="/WEB-INF/tags" %>

<%@taglib prefix="ad" uri="http://bsu.rfe.java.teacher.tag/ad" %>

<ad:getAds id="${param.id}" var="ad" />
<!DOCTYPE html>
<html>
<head>

<title><c:out value="${ad.subject}" /></title>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-
8'/>
</head>
<body>
<ad:CensorTag/>

<jsp:include page="/static/header.jsp"></jsp:include>

<my:layout1Column>

<h1><c:out value="${ad.subject}" /></h1>

<div style="text-decoration: italic; font-size: 10px">
Автор:<c:out value="${ad.author.name}" />,
Последняя дата редактирования:
<fmt:formatDate pattern="hh:mm:ss dd-MM-yyyy"
value="${ad.lastModifiedDate}" />
</div>

<div style="border: 1px solid black; padding: 20px;">
<c:out value="${ad.body}" />
</div>
</my:layout1Column>

<%@ include file="/static/footer.jsp" %>
</body>
</html>