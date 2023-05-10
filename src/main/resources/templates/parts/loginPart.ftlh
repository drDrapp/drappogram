<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="row p-2 g-3 align-items-center">
            <div class="col-auto">
                <label class="col-form-label" for="inputLogin">Логин :</label>
            </div>
            <div class="col-auto">
                <input class="form-control"
                       type="text"
                       name="login"
                       id="inputLogin"
                       placeholder="Логин"/>
            </div>
        </div>
        <div class="row p-2 g-3 align-items-center">
            <div class="col-auto">
                <label class="col-form-label" for="inputPassword">Пароль:</label>
            </div>
            <div class="col-auto">
                <input class="form-control"
                       type="password"
                       name="password"
                       id="inputPassword"
                       placeholder="Пароль"/>
            </div>
        </div>
        <div class="row p-2 g-3 align-items-center">
            <div class="row col-2 p-2">
                <button class="btn btn-warning"
                        type="submit">
                    <#if isRegisterForm>
                        Зарегистрироваться
                    <#else>
                        Войти
                    </#if>
                </button>
            </div>
        </div>
        <#if isRegisterForm><#else>
            <div class="row p-2">
                <div class="form-check">
                    <label for="remember-me">Запомнить меня</label>
                    <input class="form-check-input"
                           type="checkbox"
                           id="remember-me"
                           name="remember-me">
                </div>
            </div>
        </#if>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <button class="btn btn-secondary"
                type="submit">Выйти
        </button>
    </form>
</#macro>