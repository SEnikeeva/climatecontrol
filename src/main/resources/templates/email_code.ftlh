<#include "base.ftlh">
<#macro content>
    <div class="text-red text-center" style="background-color: #e83e8c;">
        <h1> Hi, ${user.email}!</h1>
        <br>
        <br>
        <h5 >
            This is your confirmation code for sumb's app:
        </h5>
        <h5>
            ${user.confirmCode}
        </h5>
        <br>
        <h5 style="background-color: #e8da58">
            and that letter was albert Einstein
        </h5>

    </div>
</#macro>

<@main/>