<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/inc/page-config.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/inc/page-meta.jsp" %>
    <title><fmt:message bundle="${i18n}" key="application.brand"/></title>
    <style>
        body {
        }
    </style>
</head>
<body>
<div class="login-container">
    <div class="page-header">
        <h1 class="text-center">
            <fmt:message bundle="${i18n}" key="application.brand"/>
        </h1>
    </div>
    <div class="page-section">
        <div class="alert alert-success">
            <fmt:message bundle="${commoni18n}" key="signup.finish"/>
        </div>
    </div>
    <hr>
    <div class="page-section">
        <a href="<c:url value="/"/>" class="btn btn-block">
            <fmt:message bundle="${commoni18n}" key="application.ok"/>
        </a>
    </div>
</div>
<%@include file="/WEB-INF/views/inc/scripts.jsp" %>
<script>
$(function(){
});
</script>
</body>
</html>
