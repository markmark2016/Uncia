<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<%@ include file="../common/header.jsp" %>
<body>


<nav class="navbar navbar-default navbar-static-top mark-navbar">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">MarkMark 后台管理</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">

            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false"><c:if test="${pageContext.request.userPrincipal.name != null}">
                        ${pageContext.request.userPrincipal.name}
                    </c:if> <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li class="dropdown-header">Nav header</li>
                        <li><a href="#">Separated link</a></li>
                        <li><a href="<c:url value="/logout" />">
                            退出</a></li>
                    </ul>
                </li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>
<div id="wrapper">
    <div id="sidebar-wrapper">
        <ul class="sidebar-nav">
            <li class="sidebar-brand">
            </li>
            <li>
                <a ui-sref="admin">Dashboard</a>
            </li>
            <li>
                <a ui-sref="admin.user">用户管理</a>
            </li>
            <li>
                <a ui-sref="admin.remark">书评管理</a>
            </li>
            <li>
                <a ui-sref="admin.group">小组管理</a>
            </li>
            <li>
                <a ui-sref="admin.book">图书管理</a>
            </li>
        </ul>
    </div>

    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div ui-view></div>
        </div>
    </div>


</div>

<%@ include file="../common/footer.jsp" %>

<script src="<%=basePath%><%=staticPath%>/common/lib/requirejs/js/require.js"
        data-main="<%=basePath%><%=staticPath%>/main.js"></script>

</body>
</html>
