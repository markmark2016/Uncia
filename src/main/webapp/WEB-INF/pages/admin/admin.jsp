<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">

<%@ include file="../common/header.jsp" %>

<body>

Admin page

<c:if test="${pageContext.request.userPrincipal.name != null}">
    <h2>Welcome : ${pageContext.request.userPrincipal.name} | <a href="<c:url value="/logout" />">
        Logout</a></h2>
</c:if>


<div ui-view></div>


<%@ include file="../common/footer.jsp" %>

<script src="<%=basePath%><%=staticPath%>/common/lib/requirejs/js/require.js"
        data-main="<%=basePath%><%=staticPath%>/main.js"></script>

</body>
</html>
