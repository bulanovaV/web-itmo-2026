<#import "commons.ftlh" as c>

<@c.page>
    <div class="currentUser">
        <div class="info">User Info</div>
        <#if curUser??>
            <p>Id: ${curUser.id}</p>
            <p>Login: ${curUser.login}</p>
            <p>Creation time: ${curUser.creationTime}</p>
        <#else>
            <p>No such user</p>
        </#if>
    </div>
</@c.page>
