<#import "./common.ftlh" as c>

<#macro arrow user s>
    <#if user?has_content>
        <a href="/user?handle=${user.handle}">${s}</a>
    <#else>
        <span class="arrow">${s}</span>
    </#if>
</#macro>

<@c.page>
    <#if curUser??>
        <div class="user">
            <img src="/img/avatar.png">
            <div>
                <h2><@c.userlink user=curUser nameOnly=(nameOnly! == "true")/></h2>
                <ul>
                    <li>id: ${curUser.id}</li>
                    <li>handle: ${curUser.handle}</li>
                    <li>name: ${curUser.name}</li>
                </ul>
            </div>
        </div>

        <div class="user-nav">
            <@arrow leftUser!"", "←"/>
            <@arrow rightUser!"", "→"/>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
