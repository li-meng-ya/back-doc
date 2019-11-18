# Junit单元测试
# 小技巧
- 由于一个项目中我们会写很多很多测试类，而测试类上面是需要以下几个注解的，每建一个类都去补注解，太麻烦，我们就在这个类中加上注解，其他测试类直接继承这个类就好了
# 基础使用
```
@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {
    @Autowired
    private EntFileService entFileService;
    //@Ignore("not ready yet")
    @Test
    public void testGetEntFileById(){
        Assert.assertSame("企业数量有误",500,entFileService.getCount());
    }
}
```
如上，直接@Autowired引入你想测试的类就好，然后继承基类，测试方法上面要加@Test注解。

然后，第一个测试方法：我想测试一下企业数量是不是600，参数意义：

第一个参数：如果测试不通过，会抛出此消息，此参数可不要；

第二个参数：我预期的值，我这里希望他查出来的结果是600；

第三个参数：是实际的结果，就是我们调用方法返回的结果；

# 运行测试的方法 

1.选中方法，右键，然后run 。。。；

2.点击方法前的小标；

# 打包测试

项目开发完后，我们写了很多个测试用例类，我不能每个类都点击进去，然后慢慢执行，SpringBoot提供了打包测试的方式：我们用一个类，把所有的测试类整理进去，
然后直接运行这个类，所有的测试类都会执行。
## 打包使用的注解
```
@Suite.SuiteClasses({EntFileTest.class,EntFileTest2.class})
```
## 完整打包代码
```
package com.alibaba;
 
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
 
/**
 * Created by lightClouds917
 * Date 2018/2/2
 * Description:打包测试
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({EntFileTest.class,EntFileTest2.class})
public class TestSuits {
 
    //不用写代码，只需要注解即可
}
```
- controller，service，dao等，省略，就是普通方法，普通接口

# mvc 模拟http请求
## 部分代码
```
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
 
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class Test {

    private final static Log log = LogFactory.get();
 
    protected MockMvc mockMvc;
 
    @Autowired
    protected WebApplicationContext wac;
 
    @Before()  //这个方法在每个方法执行之前都会执行一遍
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
    }
 
    @Test
    public void getAllCategoryTest() throws Exception {
        String responseString = mockMvc.perform(
                get("/categories/getAllCategory")    //请求的url,请求的方法是get
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)  //数据的格式
　　　　　　　　　　　　　　 .param("pcode","root")         //添加参数
        ).andExpect(status().isOk())    //返回的状态是200
                .andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString();   //将相应的数据转换为字符串
        System.out.println("--------返回的json = " + responseString);
    }
 
}
```
- perform：执行一个RequestBuilder请求，会自动执行SpringMVC的流程并映射到相应的控制器执行处理；
- get:声明发送一个get请求的方法。MockHttpServletRequestBuilder get(String urlTemplate, Object... urlVariables)：根据uri模板和uri变量值得到一个GET请求方式的。另外提供了其他的请求的方法，如：post、put、delete等。
- param：添加request的参数，如上面发送请求的时候带上了了pcode = root的参数。假如使用需要发送json数据格式的时将不能使用这种方式，可见后面被@ResponseBody注解参数的解决方法
- andExpect：添加ResultMatcher验证规则，验证控制器执行完成后结果是否正确（对返回的数据进行的判断）；
- andDo：添加ResultHandler结果处理器，比如调试时打印结果到控制台（对返回的数据进行的判断）；
- andReturn：最后返回相应的MvcResult；然后进行自定义验证/进行下一步的异步处理（对返回的数据进行的判断）；

# 参考文档
- [Spring Boot---(11)SpringBoot使用Junit单元测试](https://blog.csdn.net/weixin_39800144/article/details/79241620)
- [使用MockMvc进行Junit单元测试](https://blog.csdn.net/zezezuiaiya/article/details/81938441)