# controller

## 参数格式json
 
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
 **参数说明** 

|参数名 |类型 |是否为空 |说明 |
|:----- |:----- |:----- |-----|
|deviceId |string| 是  |用户登录设备号  |
|token |string   |是|用户登录token  |
|body |string   |是|请求参数对象json格式  |
|currentTimeMillis|string|是   |时间  |