package com.atguigu.crud.test;

import com.atguigu.crud.bean.Employee;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/*
 * 〈一句话功能简述〉<br>
 * 〈〉
 * @author haiqingSun
 * @create 2018/7/28
 * @since 1.0.0
 * 使用spring测试模块提供的测试请求功能，测试curd请求的正确性
 * spring4测试的时候需要servlet3.0的支持
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
//2、@Configuration 使用这个注解，指定spring的配置文件的位置
@ContextConfiguration(locations = {"classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
public class MvcTest {

    //传入springmvc的ioc
    @Autowired
    WebApplicationContext context;
    //虚拟mvc请求，获取到处理结果
    MockMvc mockMvc;
    @Before
    public void initMokcMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception {
        //模拟请求拿到返回值
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "1")).andReturn();

        //请求成功以后，请求域中会有pageInfo：我们取出pageInfo进行验证
        MockHttpServletRequest request = result.getRequest();

        PageInfo pi = (PageInfo) request.getAttribute("pageInfo");

        System.out.println("当前的页面： "+ pi.getPageNum());
        System.out.println("总页码： "+ pi.getPages());
        System.out.println("总记录数： "+ pi.getTotal());
        System.out.println("在页面需要连续显示的页码123： ");
        int[] nums =pi.getNavigatepageNums();
        for (int i : nums) {
            System.out.println(" " + i);
        }

        //获取员工的数据
        List<Employee> list = pi.getList();
        for (Employee employee : list) {
            System.out.println("ID , "+employee.getEmpId()+"==>Name: "+employee.getEmpName());
        }
    }
}

