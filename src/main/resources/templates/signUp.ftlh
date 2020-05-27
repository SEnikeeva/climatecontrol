<#include "base.ftlh">

<#macro title>Registration</#macro>

<#macro content>

    <@spring.bind "signUpForm"/>
    <form action="/signUp" method="post">
        <div class="container-fluid" style="padding-top: 10px">
            <br>
                <div class="row">
                    <div class="rounded col-md-12 background-login" align="center">
                        <div class="text-red">
                            <span><h1><@spring.message "signUp.label.registration"/></h1></span>
                            <br>
                            <br>
                            <br>
                            <#if error?has_content>
                                <div class="reg-container">
                                    <p>Error: ${error}</p>
                                </div>
                                            <#else>
                                            </#if>


                            <div class="form-group">
                                <label for="exampleInputEmail1"><@spring.message "page.email"/><span class="required">*</span></label>
                                <#--<input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp"
                                       placeholder="<@spring.message "page.placeholder.email"/>" name="email">-->
                                <@spring.formInput "signUpForm.email" "class='form-control'"/>
                                <@spring.showErrors "<br>","error"/>
                                <#--<@spring.showErrors "<br>","error"/>-->
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword1"><@spring.message "page.password"/><span class="required">*</span></label>
                                <#--<input type="password" class="form-control" id="exampleInputPassword1"
                                       placeholder="<@spring.message "page.placeholder.password"/>"
                                       name="password">-->
                                <@spring.formPasswordInput "signUpForm.password" "class='form-control'"/>
                                <@spring.showErrors "<br>","error"/>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <br>
                            <input type="submit" class="btn btn-danger" value="<@spring.message 'page.registration.button.submit'/>"/>
                            <label>
                                <input class="checkbox" name="rememberme" value="" type="checkbox">
                                <span><@spring.message "page.remember"/></span>
                            </label>

                            <a href="/signIn" class="already"><@spring.message "page.registration.login"/></a>
                        </div>
                    </div>
                </div>

        </div>

    </form>

</#macro>

<@main/>