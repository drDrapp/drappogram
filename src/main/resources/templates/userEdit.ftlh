<#import "parts/commonPart.ftlh" as c>

<@c.page>
    <h2>
        <span class="badge text-bg-secondary">Редактирование пользователя</span>
    </h2>
    <form action="/user" method="post">
        <div class="row g-3 align-items-center">
            <div class="col-auto">
                <label for="inputFirstName">Имя</label>
            </div>
            <div class="col-auto">
                <input class="form-control"
                       type="text"
                       name="firstName"
                       id="inputFirstName"
                       value="${dgUser.firstName!}">
            </div>
        </div>
        <div class="row g-3 align-items-center">
            <div class="col-auto">
                <label for="inputLastName">Фамилия</label>
            </div>
            <div class="col-auto">
                <input class="form-control"
                       type="text"
                       name="lastName"
                       id="inputLastName"
                       value="${dgUser.lastName!}">
            </div>
        </div>
        <div class="row g-3 align-items-center">
            <div class="col-auto">
                <#list allRoles as role>
                    <div class="form-check">
                        <label><input type="checkbox"
                                      name="${role}" ${dgUser.roles?seq_contains(role)?string("checked", "")}>${role}
                        </label>
                    </div>
                </#list>
            </div>
        </div>
        <input type="hidden" value="${dgUser.id}" name="userId">
        <div class="row g-3 align-items-center">
            <div class="col-auto">
                <button class="btn btn-warning"
                        type="submit">
                    Сохранить
                </button>
            </div>
        </div>
    </form>
</@c.page>