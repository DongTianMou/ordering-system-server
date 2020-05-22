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
                    <form role="form" method="post" action="/ordering-system/manage/paymentType/save">
                        <div class="form-group">
                            <label>支付类型名字</label>
                            <input name="typeName" type="text" class="form-control" value="${(paymentType.typeName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>前台回调地址</label>
                            <input name="frontUrl" type="text" class="form-control" value="${(paymentType.frontUrl)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>后台回调地址</label>
                            <input name="backUrl" type="text" class="form-control" value="${(paymentType.backUrl)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>商家id</label>
                            <input name="merchantId" type="number" class="form-control" value="${(paymentType.merchantId)!''}"/>
                        </div>
                        <input hidden type="number" name="typeId" value="${(paymentType.typeId)!''}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>