<#import "parts/commonPart.ftlh" as c>
<@c.page>
    ${myvars!}
    <h5>hello,
        <#if dgUserRoles??>
            <#if dgUserRoles?seq_contains("ADMIN")>master</#if>
        </#if>
        <#if dgUserName??>${dgUserName}<#else>guest</#if>!
    </h5>
    <div>welcome to drappogram</div>
</@c.page>
