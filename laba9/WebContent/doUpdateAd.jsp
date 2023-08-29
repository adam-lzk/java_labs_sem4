<%@page language="java" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@taglib prefix="ad" uri="http://bsu.rfe.java.teacher.tag/ad"%>

<fmt:requestEncoding value="UTF-8" />

<c:remove var="adData" />

<c:choose>
	
	<c:when test="${param.id>0}">
		
		<ad:getAds id="${param.id}" var="ad" />
		
		<c:set var="adData" scope="session" value="${ad}" />
	</c:when>
	<c:otherwise>
		
		<jsp:useBean id="adData" class="bsu.rfe.java.teacher.entity.Ad"
			scope="session" />
	</c:otherwise>
</c:choose>

<jsp:setProperty name="adData" property="subject" />
<jsp:setProperty name="adData" property="body" />

<ad:updateAd ad="${adData}" />

<c:choose>
	<c:when test="${sessionScope.errorMessage==null}">
		
		<c:remove var="adData" scope="session" />
		
		<c:redirect url="/cabinet.jsp" />
	</c:when>
	<c:otherwise>
		
		<c:redirect url="/updateAd.jsp">
			
			<c:param name="id" value="${adData.id}" />
		</c:redirect>
	</c:otherwise>
</c:choose>
