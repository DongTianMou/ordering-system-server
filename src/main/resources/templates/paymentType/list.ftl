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
                            <th>支付类型id</th>
                            <th>支付类型名字</th>
                            <th>前台回调地址</th>
                            <th>后台回调地址</th>
                            <th>商户id</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list paymentTypes as paymentType>
                        <tr>
                            <td>${paymentType.typeId}</td>
                            <td>${paymentType.typeName}</td>
                            <td>${paymentType.frontUrl}</td>
                            <td>${paymentType.backUrl}</td>
                            <td>${paymentType.merchantId}</td>
                            <td>${paymentType.create_time}</td>
                            <td>${paymentType.update_time}</td>
                            <td><a href="/ordering-system/manage/paymentType/index?typeId=${paymentType.typeId}">修改</a></td>
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