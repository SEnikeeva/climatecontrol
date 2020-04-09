<#macro content>
</#macro>

<#macro title></#macro>

<#macro main>
    <html>
    <head>
        <meta charset="utf-8">
        <meta name="view-port">
        <link rel='stylesheet' href='/static/css/bootstrap.min.css'>
        <script src="/static/js/bootstrap.min.js"></script>
        <script src="/static/js/jquery-3.3.1.min.js"></script>
        <script src="/static/js/popper.min.js"></script>
    </head>
    <body>

         <@content/>


    </body>
    </html>
</#macro>