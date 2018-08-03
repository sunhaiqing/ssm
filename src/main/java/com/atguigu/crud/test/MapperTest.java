package com.atguigu.crud.test;

import com.atguigu.crud.bean.Department;
import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.dao.DepartmentMapper;
import com.atguigu.crud.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/*
 * 〈一句话功能简述〉<br>
 * 〈测试dao层的工作〉
 * @author haiqingSun
 * @create 2018/7/28
 * @since 1.0.0
 */

//指定使用哪个class 进行注解的解析
@RunWith(SpringJUnit4ClassRunner.class)
//2、@Configuration 使用这个注解，指定spring的配置文件的位置
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {
    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    SqlSession sqlSession;
   /*
    测试DepartmentMapper
    //1、创建SpringIOC容器
    ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");

    //2、从容器中获取mapper
    ioc.getBean(DepartmentMapper.class);
    上面这样也是可以的，但是
    推荐spring的项目就可以使用spring的单元测试，
    可以自动注入我们需要的组件
    (
        1、这个功能的导入springTest的模块
        2、@Configuration 使用这个注解，指定spring的配置文件的位置
        3、直接使用autowired 组件即可 实现和原生一样的功能)
    */
    @Test
    public void testCRUD(){
//        //1、创建SpringIOC容器
//        ClassPathXmlApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
//
//        //2、从容器中获取mapper
//        ioc.getBean(DepartmentMapper.class);
        System.out.println(departmentMapper);

        //1、插入几个部门insertSelective 在DepartmentMapper.xml里面有
        departmentMapper.insertSelective(new Department(null,"开发部"));
        departmentMapper.insertSelective(new Department(null,"测试部"));

        //2、生成员工数据，测试员工插入
        employeeMapper.insertSelective(new Employee(null,"Jerry","M","Jerry@atguigu.com",1));

        //3、批量插如多个员工，使用可以执行批量操作的sqlSession
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);

        for (int i = 0; i <1000 ; i++) {
            String uid = UUID.randomUUID().toString().substring(0, 5) + i;
            mapper.insertSelective(new Employee(null,uid,"M",uid + "@atguigu.com",1));
        }
        System.out.println("批量执行完成");

    }
}

