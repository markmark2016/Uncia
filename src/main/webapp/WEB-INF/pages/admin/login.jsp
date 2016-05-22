<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">

<%@ include file="../common/header.jsp" %>

<body>

<%--<div class="container">
    <form action="${loginProcessingUrl}" method="post">
        <fieldset>
            <legend>Please Login dddd</legend>
            <!-- use param.error assuming FormLoginConfigurer#failureUrl contains the query parameter error -->
            <c:if test="${param.error != null}">
                <div>
                    Failed to login.
                    <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                        Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                    </c:if>
                </div>
            </c:if>
            <!-- the configured LogoutConfigurer#logoutSuccessUrl is /login?logout and contains the query param logout -->
            <c:if test="${param.logout != null}">
                <div>
                    You have been logged out.
                </div>
            </c:if>
            <p>
                <label for="username">Username</label>
                <input type="text" id="username" name="username"/>
            </p>
            <p>
                <label for="password">Password</label>
                <input type="password" id="password" name="password"/>
            </p>
            <!-- if using RememberMeConfigurer make sure remember-me matches RememberMeConfigurer#rememberMeParameter -->
            <p>
                <label for="remember-me">Remember Me?</label>
                <input type="checkbox" id="remember-me" name="remember-me"/>
            </p>
            <div>
                <button type="submit" class="btn">Log in</button>
            </div>
            <input type="hidden" name="${_csrf.parameterName}"
                   value="${_csrf.token}"/>
        </fieldset>
    </form>
</div>--%>
<div class="container-fluid login-container">
    <section class="container login-form">
        <section>
            <form action="${loginProcessingUrl}" method="post" role="login">
                <img src="<%=staticPath%>/common/img/login_head_logo.png" alt="" class="img-responsive"/>
                <div class="form-group">
                    <input type="text" name="username" required class="form-control" placeholder="用户名"/>
                </div>
                <div class="from-group">
                    <input type="password" name="password" required class="form-control" placeholder="密码"/>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>
                </div>
                <c:if test="${param.error != null}">
                    <div class="from-group">
                        <div class="alert alert-danger" role="alert">
                            登录失败,
                            <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                                失败原因: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                            </c:if>
                        </div>
                    </div>
                </c:if>
                <button type="submit" name="go" class="btn btn-primary btn-block">登录</button>
            </form>
        </section>
    </section>

</div>


<%@ include file="../common/footer.jsp" %>

</body>
</html>
