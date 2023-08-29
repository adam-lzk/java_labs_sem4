<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib prefix="my" tagdir="/WEB-INF/tags"%>

<%@taglib prefix="ad" uri="http://bsu.rfe.java.teacher.tag/ad"%>

<c:choose>
	<c:when test="${param.id==null || param.id==0}">
		
		<c:set var="title" scope="page" value="Создание" />
	</c:when>
	<c:otherwise>
		
		<c:set var="title" scope="page" value="Редактирование" />
		
		<c:if test="${sessionScope.errorMessage == null}">
			
			<ad:getAds id="${param.id}" var="ad" />
			
			<c:set var="adData" scope="session" value="${ad}" />
		</c:if>
	</c:otherwise>
</c:choose>
<!DOCTYPE html>
<html>
<head>
<title><c:out value="${title}" />Объявления</title>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
</head>
<body>
	
	<jsp:include page="/static/header.jsp"></jsp:include>
		
	<my:layout1Column>
		<h1>
			<c:out value="${title}" />Объявления</h1>
		
		<my:errorMessage />
		
		<form action="/ad/doUpdateAd.jsp" method="post">
			
			<c:if test="${param.id>0}">
				<input type="hidden" name="id" value="${param.id}">
			</c:if>
			<table><%-- body="0" cellspacing="0" cellpadding="10" width="90%">--%>
				<tr>
					<td>Заголовок:</td>
					
					<td><input type="text" name="subject"
						value="${sessionScope.adData.subject}" style="width: 90%"></td>
				</tr>
				<tr>
					<td>Текст:</td>
					
					<td><textarea name="body" rows="10" style="width: 90%">
							<c:out value="${sessionScope.adData.body}" />
						</textarea></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" value="Сохранить"> 
					<input type="button"
						value="Отменить"
						onclick="window.location='<c:url value="/cabinet.jsp" />';">
					</td>
				</tr>
			</table>
		</form>
	</my:layout1Column>
	<%@ include file="/static/footer.jsp"%>
</body>
</html>
