<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

<#--边栏sidebar-->
<#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/ordering-system/manage/supports/save">
                        <div class="form-group">
                            <label>活动类型名字</label>
                            <input name="type" type="number" class="form-control" value="${(supports.type)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>活动描述</label>
                            <input name="description" type="text" class="form-control" value="${(supports.description)!''}"/>
                        </div>
                        <input hidden type="number" name="id" value="${(supports.id)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>