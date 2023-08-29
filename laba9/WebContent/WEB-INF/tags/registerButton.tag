<%@ tag pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="processor" required="true" rtexprvalue="true"%>

<c:if test="${sessionScope.authUser==null}">
	<a href="${processor}">Зарегистрироваться</a>
</c:if>
