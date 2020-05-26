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

         <@content/>


    </body>
    </html>
</#macro>