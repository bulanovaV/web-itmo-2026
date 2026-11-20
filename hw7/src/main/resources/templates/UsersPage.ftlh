
<#import "commons.ftlh" as c>

<@c.page>
    <#assign isAdmin = user?? && user.admin>

    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <#if isAdmin>
                    <th>Status</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <#if users?size == 0>
                <tr class="noData">
                    <td colspan="${isAdmin?then(4, 3)}">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                    <tr>
                        <td class="id">${user.id}</td>
                        <td class="login">${user.login}</td>
                        <td class="creationTime">${user.creationTime}</td>

                        <#if isAdmin>
                            <td>
                                <form method="post" action="/users/status">
                                    <input type="hidden" name="id" value="${user.id}"/>

                                    <select name="disabled" onchange="this.form.submit()">
                                        <option value="false" <#if !user.disabled>selected</#if>>
                                            Enabled
                                        </option>
                                        <option value="true" <#if user.disabled>selected</#if>>
                                            Disabled
                                        </option>
                                    </select>
                                </form>
                            </td>
                        </#if>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
