<#include "base.ftl"/>

<#macro content>
    <div class="my-container">
        <div class="reg-container">
            <p>${message}</p>
        </div>
        <a href="/profile">Go to profile</a>
        <a href="/login">Go to Login page</a>
    </div>

</#macro>
<#macro title>
    <title>Confirmation</title>
</#macro>
<@display_page />
