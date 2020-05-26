<#import "base.ftl" as B>
<@B.html>
    <@B.head "Chat"></@B.head>
    <@B.body>
        <form action="/chats" method="post" accept-charset="UTF-8">
            <div class="container-fluid" style="padding-top: 100px">
                <br>
                <div class="row">
                    <div class="rounded col-md-12 background-login" align="center">
                        <input type="text" class="form-control" name="name" placeholder="chat name">
                        <input type="submit" class="btn btn-danger text-center" value="create new chat">
                    </div>
                </div>
            </div>
        </form>
        <hr>

        <#list chats! as chat>
            <a href="/chat/${chat.user_id.id}">${chat.user_id.id}</a>
            <br>
        </#list>
    </@B.body>
</@B.html>