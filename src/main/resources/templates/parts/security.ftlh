<#if (dgUserName??)>
    <#assign
    name = dgUserName
    isAdmin = dgUserRoles?seq_contains("ADMIN")
    isLogin = true
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    isLogin = false
    >
</#if>