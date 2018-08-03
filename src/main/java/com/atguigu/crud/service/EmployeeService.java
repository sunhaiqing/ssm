package com.atguigu.crud.service;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * 〈一句话功能简述〉<br>
 * 〈〉
 * @author haiqingSun
 * @create 2018/7/28
 * @since 1.0.0
 */
@Service
public class EmployeeService {

    /*
     service层要是想对数据进行处理就得
     调用dao层的方法，来获取数据
     */
    @Autowired
    EmployeeMapper employeeMapper;

    //查询所有的员工
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }
}

