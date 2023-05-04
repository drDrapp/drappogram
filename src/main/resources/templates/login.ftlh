<#import "parts/commonPart.ftlh" as c>
<#import "parts/loginPart.ftlh" as l>

<@c.page>
    <#if message??>
        <div class="alert alert-danger" role="alert">
            ${message!}
        </div>
    </#if>
    <@l.login "/login" false/>
</@c.page>