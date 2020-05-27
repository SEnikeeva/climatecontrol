<#include "base.ftlh">

<#macro title>chats</#macro>
<#macro content>
    <form action="/chats" method="post" accept-charset="UTF-8">
        <div class="container-fluid" style="padding-top: 100px">
            <br>
            <#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">-->
            <div class="row">
                <div class="rounded col-md-12 background-login" align="center">
                    <input type="submit" class="btn btn-danger text-center" value="create new chat">
                </div>

            </div>
        </div>
    </form>
            <hr>
<#--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">-->

            <#list chats! as chat>
                <#if chat?has_content>
                <a href="/chat/${chat.user_id.id!}">${chat.user_id.id!}</a>
                <br>
                <#else>
                </#if>
            </#list>



</#macro>
<@main/>