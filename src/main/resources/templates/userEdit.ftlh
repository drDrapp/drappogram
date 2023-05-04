<#import "parts/commonPart.ftlh" as c>

<@c.page>
    User editor
    <form action="/user" method="post">
        <label>Firstname
            <input type="text" name="firstName" value="${dgUser.firstName!}">
        </label>
        <label>Lastname
            <input type="text" name="lastName" value="${dgUser.lastName!}">
        </label>
        <#list allRoles as role>
            <div>
                <label><input type="checkbox" name="${role}" ${dgUser.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <input type="hidden" value="${dgUser.id}" name="userId">
        <button type="submit">Save</button>
    </form>
</@c.page>