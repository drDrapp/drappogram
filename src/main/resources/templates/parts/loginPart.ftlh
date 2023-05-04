<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="row">
            <label class="col-sm-2 col-form-label">User Name :</label>
            <div class="col-sm-6">
                <input type="text" name="login" class="form-control" placeholder="Login"/>
            </div>
        </div>
        <div class="row">
            <label class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <div class="row row col-sm-2">
            <button class="btn btn-primary" type="submit"><#if isRegisterForm>Register<#else>Sign In</#if></button>
        </div>
        <div class="row row col-sm-2">
            <#if isRegisterForm><#else>
                <label for="remember-me">
                    <input type="checkbox" id="remember-me" name="remember-me"> Запомнить меня
                </label>
            </#if>
        </div>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <button class="btn btn-primary" type="submit">Sign Out</button>
    </form>
</#macro>