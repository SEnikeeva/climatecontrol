<#include "base.ftlh">

<#macro title>Confirmation</#macro>

<#macro content>
     <form action="/confirm" method="post">
      <div class="container-fluid" style="padding-top: 10px">
          <br>
          <div class="row">
              <div class="rounded col-md-12 background-login" align="center">
                  <div class="text-red">
                      <span><h1>Confirmation</h1></span>
                      <br>
                      <br>
                      <br>
                      <div class="form-group">
                          <label for="inputUsername">Confirmation code<span class="required"></span></label>
                          <input type="text" class="form-control" id="inputUsername" placeholder="Enter your code"
                                 name="code">
                      </div>
                      <br>
                      <button type="submit" class="btn btn-danger"  value="save">Confirm</button>
                  </div>
              </div>
          </div>
      </div>
     </form>
</#macro>

<@main/>