<#include "base.ftlh">

<#macro title>Sumbelya login</#macro>

<#macro content>
    <#if info?has_content>
        <div class="reg-container">
            <p>info: ${info}</p>
        </div>
    <#else>
    </#if>
    <form action="/signIn" method="post">
        <div class="container-fluid" style="padding-top: 100px">
            <br>
            <div class="row">
                <div class="rounded col-md-12 background-login" align="center">
                    <div class="text-red">
                        <span><h1><@spring.message "page.signIn.label.login"/></h1></span>
                        <span><@spring.message "signIn.improve"/></span>
                        <br>
                        <br>
                        <br>
                        <#--<input type="hidden" name="${_csrf.parameterName}" value= "${_csrf.token}"/>-->
                        <div class="form-group">
                            <label for="exampleInputEmail1"><@spring.message "page.email"/><span class="required">*</span></label>
                            <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"
                                   placeholder="<@spring.message "page.placeholder.email"/>" name="email">
                        </div>
                        <div class="form-group">
                            <label for="exampleInputPassword1"><@spring.message "page.password"/><span class="required">*</span></label>
                            <input type="password" class="form-control" id="exampleInputPassword1"
                                   placeholder="<@spring.message "page.placeholder.password"/>"
                                   name="password">
                        </div>
                        <div class="form-group form-check">
                            <input type="checkbox" class="form-check-input" id="exampleCheck1" name="remember-me">
                            <label class="form-check-label" for="exampleCheck1"><@spring.message "page.remember"/></label>
                        </div>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <input type="submit" class="btn btn-danger" value="<@spring.message 'page.login.button.submit'/>"/>
                    <a href="/signUp" class="already"><@spring.message "signUp.label.registration"/></a>
                </div>
                <div class="col"></div>


            </div>
    </form>
</#macro>

<@main/>