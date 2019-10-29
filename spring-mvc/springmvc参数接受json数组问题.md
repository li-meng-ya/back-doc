# Table of Contents

* [现象](#现象)
* [解决过程](#解决过程)
* [代码](#代码)
* [参考资料](#参考资料)


# 现象
由于要记录设备在维修时要使用的备件和数量，前端传来一个json数组，后端要对这个json数组进行处理，结果数组一直接收不到

# 解决过程
1. 加注释
2. 用List+map。
3. 用字符串转jsonArray。
# 代码
```
@RequestMapping(value = "/pushPart",method = RequestMethod.POST)
    public String pushPart(@RequestParam("numbers") String numbers, @RequestParam(value = "num",required = false) @RequestBody  String num)  {
        JSONArray jsonArray = JSONArray.parseArray(num);//将字符串解析成数组
        JSONObject jSONObject = jsonArray.getJSONObject(0);//获取object对象
        Log log = LogFactory.get();
        log.debug("{}" , jSONObject);
```
# 参考资料
- [SpringMVC利用json接收复杂对象和数组](https://blog.csdn.net/qq_42131246/article/details/83105221)
> SpringMVC接收复杂参数必须要在参数前加@RequestBody
- [springmvc接收json数据的4种方式](https://blog.csdn.net/weixin_39220472/article/details/80725574)
> 这里提供的是说,List+map接收。
