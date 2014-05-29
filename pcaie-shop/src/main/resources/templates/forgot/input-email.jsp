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
        <c:if test="${!empty error }">
            <div class="alert alert-error"><c:out value="${error }" /></div>
        </c:if>
        <form action="<c:url value="/forgot"/>" method="post" class="">
            <div class="control-group <c:if test="${!empty emailError }">error</c:if>">
                <label class="control-label" for="inputEmail">
                    <fmt:message bundle="${commonbean}" key="UserBean.email"/>
                </label>
                <div class="controls">
                    <input type="text" id="inputEmail" name="email" class="input-block-level"
                        placeholder="<fmt:message bundle="${commonbean}" key="UserBean.email"/>">
                    <c:if test="${!empty emailError }">
                        <span class="help-inline"><c:out value="${emailError }" /></span>
                    </c:if>
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <button type="submit" class="btn btn-block">
                        <fmt:message bundle="${commoni18n}" key="application.submit"/>
                    </button>
                </div>
            </div>
        </form>
    </div>
    <hr>
    <div class="page-section">
        <a href="<c:url value="/login"/>" class="btn btn-link">
            <fmt:message bundle="${commoni18n}" key="application.goBack"/>
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
