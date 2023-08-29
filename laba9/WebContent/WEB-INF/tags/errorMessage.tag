<%@ tag pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${sessionScope.errorMessage!=null}">
	
	<div style="padding: 10px;">
		<span style="background-color: yellow;"> <c:out
				value="${sessionScope.errorMessage}" />
		</span>
	</div>
	
	<c:remove var="errorMessage" scope="session" />
</c:if>
