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
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>活动类型</th>
                            <th>活动描述</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list supports as support>
                        <tr>
                            <td>${support.type}</td>
                            <td>${support.description}</td>
                            <td><a href="/ordering-system/manage/supports/index?type=${support.type}">修改</a></td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>