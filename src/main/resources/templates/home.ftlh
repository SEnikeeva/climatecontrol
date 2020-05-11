<#include "base.ftlh">

<#macro title>Home</#macro>

<#macro content>
    <div class="container-fluid" style="padding-top: 10px">
          <br>
          <div class="row">
              <div class="rounded col-md-12 background-login" align="center">
                     <form action="/signIn" method="get">
                         <div class="text-center">
                             <button type="submit" class="btn btn-danger text-center">Войти</button>
                         </div>
                     </form>
                    <form action="/signUp" method="get">
                        <div class="text-center">
                            <button type="submit" class="btn btn-danger text-center">Зарегистрироваться</button>
                        </div>
                    </form>
              </div>
          </div>
    </div>
</#macro>

<@main/>