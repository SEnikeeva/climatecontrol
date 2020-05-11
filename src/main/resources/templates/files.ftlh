<#include "base.ftlh">

<#macro title>Uploading</#macro>

<#macro content>
    <form action="/files" method="post" enctype="multipart/form-data">
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
                        <button type="submit" class="btn btn-danger"  value="save">Upload</button>

                    </div>
                </div>
            </div>

        </div>

    </form>
</#macro>

<@main/>