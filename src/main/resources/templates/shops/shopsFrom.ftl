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
                    <form role="form" method="post" action="/ordering-system/manage/shops/save">
                        <div class="form-group">
                            <label>商家名字</label>
                            <input name="name" type="text" class="form-control" value="${(shops.name)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>最低销售价格</label>
                            <input name="minPrice" type="text" class="form-control" value="${(shops.minPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>商店头像</label>
                            <input name="avatar" type="text" class="form-control" value="${(shops.avatar)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>外卖配送时间</label>
                            <input name="deliveryTime" type="number" class="form-control" value="${(shops.deliveryTime)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>外卖配送价格</label>
                            <input name="deliveryPrice" type="number" class="form-control" value="${(shops.deliveryPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>营业时间</label>
                            <input name="openingHours" type="text" class="form-control" value="${(shops.openingHours)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>商店地址</label>
                            <input name="address" type="text" class="form-control" value="${(shops.address)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>商店描述</label>
                            <input name="description" type="text" class="form-control" value="${(shops.description)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>商店公告</label>
                            <input name="bulletin" type="text" class="form-control" value="${(shops.bulletin)!''}"/>
                        </div>
                        <input hidden type="number" name="id" value="${(shops.id)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>