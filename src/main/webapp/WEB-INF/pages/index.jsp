<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">

<%@ include file="common/header.jsp" %>

<body>

{{markController.message}}

<div ui-view></div>


<%@ include file="common/footer.jsp" %>

<script src="<%=basePath%><%=staticPath%>/common/lib/requirejs/js/require.js"
        data-main="<%=basePath%><%=staticPath%>/main.js"></script>

</body>
</html>
