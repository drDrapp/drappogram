<#import "parts/commonPart.ftlh" as c>

<@c.page>
    <h2>
        <span class="badge text-bg-secondary">Список пользователей</span>
    </h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">Логин</th>
            <th scope="col">Имя</th>
            <th scope="col">Фамилия</th>
            <th scope="col">Статус</th>
            <th scope="col">Роли</th>
            <th scope="col">Управление</th>
        </tr>
        </thead>
        <tbody>
        <#list userList as dgUser>
            <tr scope="row">
                <td>${dgUser.login}</td>
                <td>${dgUser.firstName!}</td>
                <td>${dgUser.lastName!}</td>
                <td>${dgUser.state}</td>
                <td><#list dgUser.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/${dgUser.id}">Edit</a></td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>