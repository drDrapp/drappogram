<#import "parts/commonPart.ftlh" as c>
<@c.page>
    <h4>
        привет,
        <#if dgUserRoles??>
            <#if dgUserRoles?seq_contains("ADMIN")>
                <span class="badge text-bg-warning">мастер</span>
            </#if>
        </#if>
        <#if dgUserName??>${dgUserName}<#else>бродяга!</#if>!
    </h4>
    <h2>
        <span class="badge text-bg-secondary">welcome to drappogram</span>
    </h2>
</@c.page>
