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
                            <th>商家名字</th>
                            <th>最低销售价格</th>
                            <th>商店头像</th>
                            <th>外卖配送时间</th>
                            <th>外卖配送价格</th>
                            <th>营业时间</th>
                            <th>商店地址</th>
                            <th>商家描述</th>
                            <th>公告</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list shopsInfos as shopsInfo>
                        <tr>
                            <td>${shopsInfo.name}</td>
                            <td>${shopsInfo.minPrice}</td>
                            <td>${shopsInfo.avatar}</td>
                            <td>${shopsInfo.deliveryTime}</td>
                            <td>${shopsInfo.deliveryPrice}</td>
                            <td>${shopsInfo.openingHours}</td>
                            <td>${shopsInfo.address}</td>
                            <td>${shopsInfo.description}</td>
                            <td>${shopsInfo.bulletin}</td>
                            <td><a href="/ordering-system/manage/shops/index?id=${shopsInfo.id}">修改</a></td>
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