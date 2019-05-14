# controller

## 参数格式form-data
 
**请求示例**
 
   ```
     {   
        "deviceId": "value", 
        "token": "value", 
        "body": { 
            "key1": value1 ,
            "key2": value2 ,
            ...
        }, 
        "currentTimeMillis": value 
     }
   ```
 **form-data参数** 
 
 |参数名 |类型 |是否为空 |说明 |
 |:----- |:----- |:----- |-----|
 |json |String| 否  |所有参数，参考请求示例  |

**json字段说明**

|参数名 |类型 |是否为空 |说明 |
|:----- |:----- |:----- |-----|
|deviceId |String| 是  |用户登录设备号  |
|token |String   |是|用户登录token  |
|body |JSONObject   |是|请求参数对象json格式  |
|currentTimeMillis|Long|是   |时间  |