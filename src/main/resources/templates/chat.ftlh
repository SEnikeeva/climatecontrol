<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script src="/static/js/chat.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

</head>

<body <#--onload="sendMessage('${pageId}','${userId}','Login')"-->>
<h1>Chat id - ${pageId}</h1>
<div>
    <input id="message" placeholder="Your message">
    <button onclick="sendMessage('${pageId}', ${userId},
            $('#message').val())">SEND
    </button>
</div>
<script type="application/javascript">
    receiveMessage('${pageId}', '${userId}');
</script>
<div>
    <table id="messages">
        <tr>
            <td>User</td>
            <td>Time</td>
            <td>Message</td>
        </tr>
        <#list history as message>
            <tr>
                <td>${message.sender.id}</td>
                <td>${message.time}</td>
                <td>${message.message}</td>
            </tr>
        </#list>
    </table>
</div>
</body>
</html>