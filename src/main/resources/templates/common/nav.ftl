<nav class="navbar navbar-inverse navbar-fixed-top" id="sidebar-wrapper" role="navigation">
    <ul class="nav sidebar-nav">
        <li class="sidebar-brand">
            <a href="#">
                后端管理系统
            </a>
        </li>
        <li>
            <a href="/ordering-system/manage/order/list"><i class="fa fa-fw fa-list-alt"></i> 订单列表</a>
        </li>
        <li class="dropdown open">
            <a href="/ordering-system/manage/product/list" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                <i class="fa fa-fw fa-plus"></i> 商品列表 <span class="caret"></span></a>
            <a href="/ordering-system/manage/category/list" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                <i class="fa fa-fw fa-plus"></i> 类目列表 <span class="caret"></span></a>
            <a href="/ordering-system/manage/paymentType/list" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                <i class="fa fa-fw fa-plus"></i> 支付类型列表 <span class="caret"></span></a>
            <a href="/ordering-system/manage/supports/list" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                <i class="fa fa-fw fa-plus"></i> 商家活动列表 <span class="caret"></span></a>
            <a href="/ordering-system/manage/shops/info" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="true">
                <i class="fa fa-fw fa-plus"></i> 商家信息 <span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
                <li class="dropdown-header">操作</li>
                <li><a href="/ordering-system/manage/product/index">新增商品</a></li>
                <li><a href="/ordering-system/manage/category/index">新增类目</a></li>
                <li><a href="/ordering-system/manage/paymentType/index">新增支付类型</a></li>
                <li><a href="/ordering-system/manage/supports/index">新增活动</a></li>
                <li><a href="/ordering-system/manage/shops/index">新增商家信息</a></li>
            </ul>
        </li>

        <li>
            <a href="/ordering-system/user/logout"><i class="fa fa-fw fa-list-alt"></i> 登出</a>
        </li>
    </ul>
</nav>