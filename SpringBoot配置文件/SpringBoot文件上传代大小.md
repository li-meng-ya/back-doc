#  问题
在做文件上传的时候出现的一个问题，百度后说的上传的文件太大，
而springboot自带Tomcat默认限制大小是1M，
这就需要我们去修改默认配置了，破解上传文件1Mb的禁锢

错误信息如下(关键):

```
org.apache.tomcat.util.http.fileupload.FileUploadBase$SizeLimitExceededException: 
the request was rejected because its size (110862330) exceeds the configured maximum (31457280)
```

#  解决办法

(主要是修改application.yml对应的配置):

```
spring:
  http:
    multipart:
      enabled: true
      max-file-size: 50MB 
      max-request-size: 50MB
      
```
- maxFileSize 单个数据大小 
- maxRequestSize 是总数据大小(同时上传多个文件)

如果觉得50MB不够的话，可以往上调。

# 参考资料
1. [springboot文件上传设置上传大小](https://www.cnblogs.com/youcong/p/11568282.html)
2. [Spring Boot上传文件 org.apache.tomcat.util.http.fileupload.FileUploadBase$FileSizeLimitExceededException](https://blog.csdn.net/weixin_38306434/article/details/89633328)



