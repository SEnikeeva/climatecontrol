<#include "base.ftlh">

<#macro title>longlong message</#macro>
<#macro content>
 <div class="container-fluid" style="padding-top: 100px">
    <br>
    <div class="row">
        <div class="rounded col-md-12 background-login" align="center">
            <div class="text-red">
                <H2>
                    <@spring.message "admin"/>
                </H2>

                    <script type="text/javascript">
                        function waitmsg() {
                            console.log('call waitmsg()');
                            var xhr = new XMLHttpRequest();
                            xhr.open("GET", '/api/admin/get', true);
                            xhr.setRequestHeader("Content-type", "application/json; charset=utf-8");
                            xhr.onreadystatechange = function () {
                                if ((xhr.status == 200) && (xhr.readyState == 4)) {
                                    if (xhr.response != null) {
                                        var resp = JSON.parse(xhr.response);
                                        var tbl = document.getElementById("msgs");

                                        //for(var i=countrow; i>0; i--) {
                                        //	tbl.deleteRow(i);
                                        //}

                                        for (i = 0; i < resp.length; i++) {
                                            var row = tbl.insertRow();
                                            var cell0 = row.insertCell(0);
                                            cell0.innerHTML = resp[i]['userid'];
                                            var cell1 = row.insertCell(1);
                                            cell1.innerHTML = new Date().toLocaleString();
                                            var cell2 = row.insertCell(2);
                                            cell2.innerHTML = resp[i]['msg'];
                                        }
                                    }
                                    console.log('waiting');
                                    waitmsg();
                                }
                            }
                            xhr.send();
                        }

                        document.addEventListener('DOMContentLoaded', waitmsg());

                        function sendmsg(uid, user) {
                            console.log('sendmsg');
                            // var uid = document.getElementById("userid").value;
                            var msg = document.getElementById("msg").value;
                            console.log('uid='+uid+', msg='+msg );
                            var body = '{"userid":"'+uid+'","msg":"'+msg+'"}';

                            var xhr = new XMLHttpRequest();
                            xhr.open("POST", '/api/admin/send/'+user, true);
                            xhr.setRequestHeader("Content-type", "application/json; charset=utf-8");
                            xhr.onreadystatechange = function () {
                                if ((xhr.status == 200) && (xhr.readyState == 4)) {
                                    // var rep = JSON.parse(xhr.response);
                                    var tbl = document.getElementById("msgs");
                                    var resp = JSON.parse(xhr.response);
                                    var row = tbl.insertRow();
                                    var cell0 = row.insertCell(0);
                                    cell0.innerHTML = resp['userid'];
                                    var cell1 = row.insertCell(1);
                                    cell1.innerHTML = new Date().toLocaleString();
                                    var cell2 = row.insertCell(2);
                                    cell2.innerHTML = resp['msg'];
                                }
                            }
                            xhr.send(body);
                        }
                    </script>

                    <div style="width:30%"><label for="msg"></label><input type="text" id="msg"/></div>
                         <br>

                    <button class="btn btn-danger" onclick="sendmsg('${userId}',  '${user}')"><@spring.message "send.message"/></button>

                    <h3><@spring.message "chat"/></h3>


                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    <div>
                        <table id="msgs">
                            <tr>
                                <td><@spring.message "sender"/></td>
                                <td><@spring.message "time"/>  </td>
                                <td><@spring.message "message"/></td>
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
                </div>
            </div>
        <a href="/chats" class="already"><@spring.message "chats"/></a>
        <div class="col"></div>

    </div>
</#macro>
<@main/>
