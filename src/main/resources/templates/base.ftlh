<#import "lib/spring.ftl" as spring/>
<#macro content>
</#macro>

<#macro title></#macro>

<#macro main>
    <html>
    <head>
        <meta charset="utf-8">
        <meta name="view-port">
        <meta http-equiv="Cache-Control" content="no-cache">
        <link rel='stylesheet' href='/static/css/bootstrap.min.css'>
        <script src="/static/js/bootstrap.min.js"></script>
        <script src="/static/js/jquery-3.3.1.min.js"></script>
        <script src="/static/js/popper.min.js"></script>
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@9"></script>
    </head>
    <body>
    <script>
        function changeLanguage() {
            var l = '<@spring.message "header.page.language"/>'.toLowerCase();
            window.history.replaceState({}, "", window.location.protocol + "//" + window.location.host + window.location.pathname +  "?lang=" + l);
        }
    </script>

    <nav class="navbar navbar-expand-md navbar-light sticky-top">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-toggle="collapse"
                    data-target="#navbarResponsive">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarResponsive">
                <ul class="navbar-nav ml-auto">
                    <li class="navbar-item active">
                        <a href="/logout" class="text-warning nav-link"><@spring.message "link.logout"/></a>
                    </li>
                    <li class="navbar-item">
                        <a href="/signUp" class="text-warning nav-link"> <@spring.message "link.signUp"/></a>
                    </li>
                    <li class="navbar-item">
                        <a href="/signIn" class="text-warning nav-link"><@spring.message "link.login"/></a>
                    </li>
                    <li class="navbar-item">
                        <a href="/profile" class="text-warning nav-link"><@spring.message "link.profile"/></a>
                    </li>
                    <li class="navbar-item">
                        <a class="text-warning nav-link" style="background-color: #062c33" onclick="changeLanguage()" href=""><@spring.message "header.page.language"/></a>
                    </li>

                </ul>
            </div>

        </div>
    </nav>
         <@content/>
    <div class="row padding " >
        <div class="container-fluid padding">

            <div class="navbar-item text-block col-lg-6 col-md-6 col-sm-6 col-xs-12">
                <a href="#" class="text-muted btn btn-link">
                    Напишите нам
                </a>
                <br>
                <a href="#" class="text-muted btn btn-link">
                    Помощь
                </a>
            </div>
        </div>
    </div>

    </body>
    </html>
</#macro>