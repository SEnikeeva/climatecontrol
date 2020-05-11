<#include "base.ftlh">

<#macro title>Registration</#macro>

<#macro content>
    <form action="/signUp" method="post">
        <div class="container-fluid" style="padding-top: 10px">
            <br>
                <div class="row">
                    <div class="rounded col-md-12 background-login" align="center">
                        <div class="text-red">
                            <span><h1>Регистрация</h1></span>
                            <br>
                            <br>
                            <br>
                            <div class="form-group">
                                <label for="inputUsername">Email<span class="required">*</span></label>
                                <input type="text" class="form-control" id="inputUsername" placeholder="Enter username"
                                       name="email">
                            </div>
                            <div class="form-group">
                                <label for="inputPasswordFirst">Password<span class="required">*</span></label>
                                <input type="password" class="form-control" id="inputPasswordFirst" placeholder="Password"
                                       name="password">
                            </div>
                            <br>
                            <button type="submit" class="btn btn-danger"  value="save">Register</button>
                            <label>
                                <input class="checkbox" name="rememberme" value="" type="checkbox">
                                <span>Remember me</span>
                            </label>

                            <a href="/signIn" class="already">You already have an account? Login here.</a>
                        </div>
                    </div>
                </div>

        </div>

    </form>

</#macro>

<@main/>