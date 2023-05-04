<#import "parts/commonPart.ftlh" as c>

<@c.page>
    <div>User list</div>
    <table>
        <tr>
            <th>Login</th>
            <th>Firstname</th>
            <th>Lastname</th>
            <th>State</th>
            <th>Roles</th>
            <th>Commands</th>
        </tr>
        <#list userList as dgUser>
            <tr>
                <td>${dgUser.login}</td>
                <td>${dgUser.firstName!}</td>
                <td>${dgUser.lastName!}</td>
                <td>${dgUser.state}</td>
                <td><#list dgUser.roles as role>${role}<#sep>, </#list></td>
                <td><a href="/user/${dgUser.id}">Edit</a></td>
            </tr>
        </#list>
    </table>
</@c.page>