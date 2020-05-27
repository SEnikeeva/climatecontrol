<#include "base.ftlh">

<#macro title>Uploading</#macro>

<#macro content>
   <script>
       function sendFile() {
           let formData = new FormData();
           let files = ($('#file'))[0]['files'];
           [].forEach.call(files, function (file, i, files) {
               formData.append("file", file);
           });/*
           var token = $("meta[name='_csrf']").attr("content");
           var header = $("meta[name='_csrf_header']").attr("content");*/
           $.ajax({
               type: "POST",
               /*beforeSend: function(request) {
                   request.setRequestHeader(header, token);},*/
               enctype: 'multipart/form-data',
               url: "/files",
               data: formData,
               processData: false,
               contentType: false
           })
                   .done(function (response) {
                       alert(response)
                   })
                   .fail(function () {
                       alert('Error')
                   });
       }
   </script>
    <div class="container-fluid" style="padding-top: 10px">
        <br>
        <div class="row">
            <div class="rounded col-md-12 background-login" align="center">
                <div class="text-red">
                    <br>
                    <br>

                    <br>
                    <label for="file">File</label>
                    <p><input id="file" type="file" name="file"/></p>
                    <br>
                    <button type="submit"  onclick="sendFile()" class="btn btn-danger"  value="save">Upload</button>

                </div>
            </div>
        </div>

    </div>


</#macro>

<@main/>