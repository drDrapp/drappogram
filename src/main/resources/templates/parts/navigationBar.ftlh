<#include "security.ftlh">
<#import "loginPart.ftlh" as l>
<nav class="navbar navbar-expand-lg bg-body-tertiary bg-dark" data-bs-theme="dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">drappogram</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/main">Messages</a>
                </li>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link" href="/user">User list</a>
                    </li>
                </#if>
            </ul>
        </div>
        <#if (isLogin)>
            <div class="navbar-text ms-3">${name}</div>
            <div class="navbar-text ms-3">
                <@l.logout />
            </div>
        <#else>
            <a href="/login" class="navbar-text ms-3"> LOGIN </a>
            <a href="/registration" class="navbar-text ms-3"> REGISTER </a>
        </#if>
    </div>
</nav>