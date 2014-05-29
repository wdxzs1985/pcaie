<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/WEB-INF/views/inc/page-config.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/inc/page-meta.jsp" %>
    <title><fmt:message bundle="${i18n}" key="admin.account.edit"/></title>
</head>
<body>
<%@include file="/WEB-INF/views/inc/page-nav.jsp" %>
<div class="page-sidebar">
<%@include file="/WEB-INF/views/inc/page-sidebar-menu.jsp" %>
</div>
<div class="page-content">
    <div class="page-header">
        <h1><fmt:message bundle="${i18n}" key="admin.account.edit"/></h1>
    </div>
    <div class="row">
        <div class="col-xs-6 col-xs-offset-2">
            <div class="alert alert-block alert-success">
                <ul>
                    <c:if test="${!empty message }">
                        <li><c:out value="${message}" /></li>
                    </c:if>
                    <li><fmt:message bundle="${i18n}" key="admin.account.password.message"/></li>
                </ul>
            </div>
        </div>
    </div>
    
    <form class="form-horizontal" action="<c:url value="/admin/account/${userBean.id}"/>" method="POST">
      <div class="form-group <c:if test="${!empty mailError }">has-error</c:if>" >
        <label class="control-label col-xs-2" for="inputMail">
            <fmt:message bundle="${bean}" key="UserBean.mail"/>
        </label>
        <div class="col-xs-6">
          <input type="text" id="inputMail" name="mail"
                 class="form-control"
                 value="<c:out value="${userBean.mail}"/>"
                 placeholder="<fmt:message bundle="${bean}" key="UserBean.mail"/>">
          <span class="help-block"><c:out value="${mailError}" /></span>
        </div>
      </div>
      <div class="form-group <c:if test="${!empty nameError }">has-error</c:if>" >
        <label class="control-label col-xs-2" for="inputName">
            <fmt:message bundle="${bean}" key="UserBean.name"/>
        </label>
        <div class="col-xs-6">
          <input type="text" id="inputName" name="name"
                 class="form-control"
                 value="<c:out value="${userBean.name}"/>"
                 placeholder="<fmt:message bundle="${bean}" key="UserBean.name"/>">
          <span class="help-block"><c:out value="${nameError}" /></span>
        </div>
      </div>
      <div class="form-group <c:if test="${!empty passwordError }">has-error</c:if>" >
        <label class="control-label col-xs-2" for="inputPassword">
            <fmt:message bundle="${bean}" key="UserBean.password"/>
        </label>
        <div class="col-xs-6">
          <input type="password" id="inputPassword" name="password"
                 class="form-control"
                 placeholder="<fmt:message bundle="${bean}" key="UserBean.password"/>">
          <span class="help-block"><c:out value="${passwordError}" /></span>
        </div>
      </div>
      <div class="form-group <c:if test="${!empty password2Error }">has-error</c:if>" >
        <label class="control-label col-xs-2" for="inputPassword2">
            <fmt:message bundle="${bean}" key="UserBean.password2"/>
        </label>
        <div class="col-xs-6">
          <input type="password" id="inputPassword2" name="password2"
                 class="form-control"
                 placeholder="<fmt:message bundle="${bean}" key="UserBean.password2"/>">
          <span class="help-block"><c:out value="${password2Error}" /></span>
        </div>
      </div>
      <div class="form-group" >
        <label class="control-label col-xs-2" for="radioRoleNormal">
            <fmt:message bundle="${bean}" key="UserBean.role"/>
        </label>
        <div class="col-xs-6">
            <label class="radio-inline">
                <input type="radio" id="radioRoleNormal" name="role" value="0" <c:if test="${userBean.role ne '1' }">checked</c:if>> 
                <fmt:message bundle="${i18n}" key="user.role.normal"/>
            </label>
            <label class="radio-inline">
                <input type="radio" id="radioRoleAdmin" name="role" value="1" <c:if test="${userBean.role eq '1' }">checked</c:if>> 
                <fmt:message bundle="${i18n}" key="user.role.admin"/>
            </label>
        </div>
      </div>
      <div class="form-group">
        <div class="col-xs-6 col-xs-offset-2">
          <button type="submit" class="btn btn-primary">
            <span class="glyphicon glyphicon-ok"></span>&nbsp;
            <fmt:message bundle="${i18n}" key="button.save"/>
          </button>
          <a href="<c:url value="/admin/account"/>" class="btn btn-default">
            <span class="glyphicon glyphicon-arrow-left"></span>&nbsp;
            <span><fmt:message bundle="${i18n}" key="button.back"/></span>
          </a>
        </div>
      </div>
    </form>
</div>
<%@include file="/WEB-INF/views/inc/scripts.jsp" %>
</body>
</html>
