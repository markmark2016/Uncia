<%@ page import="org.springframework.security.core.userdetails.User" %>
<%@ page import="com.markeveryday.security.LoginHelper" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%
    String basePath = request.getContextPath();
    if (!basePath.endsWith("/")) {
        basePath += "/";
    }
    User user = LoginHelper.getLoginUser();
    String staticPath = "resources/dev";
%>

<script>
    var MarkMark = MarkMark || {};
    MarkMark.constants = MarkMark.constants || {};
    MarkMark.constants.currentUser = "<%=user%>";
    MarkMark.constants.CONTEXT = "<%=basePath%>";
    MarkMark.constants.STATIC_PATH = "<%=staticPath%>";
</script>