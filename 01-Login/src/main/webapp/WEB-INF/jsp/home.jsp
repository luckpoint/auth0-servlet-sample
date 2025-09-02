<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Home Page</title>
    <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="/css/jumbotron-narrow.css">
    <link rel="stylesheet" type="text/css" href="/css/home.css">
    <link rel="stylesheet" type="text/css" href="/css/jquery.growl.css"/>
    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="/js/jquery.growl.js" type="text/javascript"></script>
</head>

<body>

<div class="container">
    <div class="header clearfix">
        <nav>
            <ul class="nav nav-pills pull-right">
                <li class="active" id="home"><a href="#">Home</a></li>
                <li id="qsLogoutBtn"><a href="#">Logout</a></li>
            </ul>
        </nav>
        <h3 class="text-muted">App.com</h3>
    </div>
    <div class="jumbotron">
        <h3>Hello ${userProfile.email}!</h3>
        <c:if test="${not empty userProfile}">
            <p>Welcome back!</p>
        </c:if>
        <c:if test="${not empty tokenError}">
            <div class="alert alert-warning" role="alert">
                <strong>Token Error:</strong> ${tokenError}
            </div>
        </c:if>
    </div>
    
    <!-- User Profile Section -->
    <c:if test="${not empty userProfile}">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">User Profile Information</h3>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <c:if test="${not empty userProfile.picture}">
                        <div class="col-md-3">
                            <img src="${userProfile.picture}" alt="Profile Picture" class="img-thumbnail" style="max-width: 150px;">
                        </div>
                        </c:if>
                        <div class="col-md-9">
                            <table class="table table-striped">
                                <c:if test="${not empty userProfile.name}">
                                <tr>
                                    <td><strong>Full Name:</strong></td>
                                    <td>${userProfile.name}</td>
                                </tr>
                                </c:if>
                                <c:if test="${not empty userProfile.email}">
                                <tr>
                                    <td><strong>Email:</strong></td>
                                    <td>
                                        ${userProfile.email}
                                        <c:if test="${userProfile.email_verified}">
                                            <span class="label label-success">Verified</span>
                                        </c:if>
                                        <c:if test="${not userProfile.email_verified}">
                                            <span class="label label-warning">Not Verified</span>
                                        </c:if>
                                    </td>
                                </tr>
                                </c:if>
                                <c:if test="${not empty userProfile.sub}">
                                <tr>
                                    <td><strong>User ID:</strong></td>
                                    <td><code>${userProfile.sub}</code></td>
                                </tr>
                                </c:if>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </c:if>
    <div class="row marketing">
        <div class="col-lg-6">
            <h4>Subheading</h4>
            <p>Donec id elit non mi porta gravida at eget metus. Maecenas faucibus mollis interdum.</p>

            <h4>Subheading</h4>
            <p>Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Cras mattis consectetur purus sit amet
                fermentum.</p>
        </div>

        <div class="col-lg-6">
            <h4>Subheading</h4>
            <p>Donec id elit non mi porta gravida at eget metus. Maecenas faucibus mollis interdum.</p>

            <h4>Subheading</h4>
            <p>Morbi leo risus, porta ac consectetur ac, vestibulum at eros. Cras mattis consectetur purus sit amet
                fermentum.</p>
        </div>
    </div>

    <footer class="footer">
        <p> &copy; 2016 Company Inc</p>
    </footer>

</div>

<script type="text/javascript">
    $("#qsLogoutBtn").click(function(e) {
        e.preventDefault();
        $("#home").removeClass("active");
        $("#password-login").removeClass("active");
        $("#qsLogoutBtn").addClass("active");
        // assumes we are not part of SSO so just logout of local session
        window.location = "${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, '')}/logout";
    });
</script>

</body>
</html>
