<html>
<#include "../common/header.ftl">

<body >
    <div class="container">
        <div class="row">
            <div class="col-md-3 column">
                <form  role="form" method="post" action="/ordering-system/user/login">
                    <div class="form-group">
                        <label for="username">用户名</label>
                        <input type="text" class="form-control" name="username"/>
                    </div>
                    <div class="form-group">
                        <label for="telephone">电话</label>
                        <input type="text" class="form-control" name="telephone"/>
                    </div>
                    <div class="form-group">
                        <label for="password">密码</label>
                        <input type="password" class="form-control" name="password"/>
                    </div>
                    <button type="submit" class="btn btn-info">Submit</button>
                </form>
            </div>
        </div>
    </div>

</body>
</html>

