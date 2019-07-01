<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>
    Hello ${name}!
    <br>
    
    <table border="1px">
        <tr>
            <td>序号</td>
            <td>姓名</td>
            <td>年龄</td>
            <td>钱包</td>
            <td>生日</td>
        </tr>
        <#list stus as stu>
            <tr>
                <td>${stu_index}</td>
                <td>${stu.name}</td>
                <td>${stu.age}</td>
                <td>${stu.mondy}</td>
                <td>birthday</td>
            </tr>
        </#list>
    </table>
    <br>
    读取Map<br>
    使用"[]"格式:<br>
    姓名:${stuMap["stu1"].name}<br>
    年龄:${stuMap["stu1"].age}<br>

    使用".key"方式:<br>
    姓名:${stuMap.stu2.name}<br>
    年龄:${stuMap.stu2.age}<br>
    <br>
    
    使用list遍历key方式:<br>
    <#list stuMap?keys as k>
        姓名:${stuMap[k].name}<br>
        年龄:${stuMap[k].age}<br>
    </#list>
    <br>
    if指令:<br>
    <table>
        <#list stus as stu>
            <tr>
                <td>${stu_index}</td>
                <td <#if stu.name == '小明'>style="background: aqua;"</#if>>${stu.name}</td>
                <td <#if (stu.age > 18)>style="background: aqua;"</#if>>${stu.age}</td>
                <td <#if stu.mondy gt 100>style="background: aqua;"</#if>>${stu.mondy}</td>
                <td>birthday</td>
            </tr>
        </#list>
    </table>
    <br>
    c函数:<br>
    ${point?c}
    <br>
    <#assign text="{'bank':'工商银行','account':'10101920201920212'}" />
    <#assign data=text?eval />
    开户行：${data.bank}  账号：${data.account}
</body>
</html>