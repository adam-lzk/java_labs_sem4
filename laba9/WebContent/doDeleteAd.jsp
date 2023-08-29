<%@page language="java" pageEncoding="UTF-8"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib prefix="ad" uri="http://bsu.rfe.java.teacher.tag/ad"%>

<ad:getAds id="${param.id}" var="ad" />

<ad:deleteAd ad="${ad}" />

<c:redirect url="/cabinet.jsp" />
