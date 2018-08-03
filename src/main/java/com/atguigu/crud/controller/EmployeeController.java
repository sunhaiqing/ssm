package com.atguigu.crud.controller;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.Msg;
import com.atguigu.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/*
 * 〈一句话功能简述〉<br>
 * 〈处理员工的增删改查〉
 * @author haiqingSun
 * @create 2018/7/28
 * @since 1.0.0
 */
@Controller
public class EmployeeController {

    /*
     这个是service层
     主要是对dao层的数据在次进行处理 再次封装数据
     这个信息是dao层的数据（来自数据库）
     */

    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/emps")
    //这个注解，直接将数据格式化成json格式的数据
    //@RequestBody 这个需要导入jackson包
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue = "1")Integer pn, Model model){
        PageHelper.startPage(pn,5);
        List<Employee> emps = employeeService.getAll();
        PageInfo page = new PageInfo(emps,5);
        return Msg.success().add("pageInfo",page);
    }



    /*
     RequestMapping 接收前端的请求
     (controller 主要是处理 来自前端的请求以及返回数据)
     查询员工数据 （分页查询）
     */
   // @RequestMapping("/emps")
    public String getEmps(@RequestParam(value="pn",defaultValue = "1")Integer pn, Model model){

        //查询所有的员工，这个不是一个分页的查询
        //引入PageHelper分页插件
        //在查询之前只需要调用，传入页码以及分页每页的大小
        PageHelper.startPage(pn,5);
        //startPage后面紧跟的这个查询，就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo包装查询后的结果，只需要将pageIfo交给页面就行了。
        //封装了详细的分页信息，包括我们查询出来的数据
        //传入连续显示的页数
        PageInfo page = new PageInfo(emps,5);
        model.addAttribute("pageInfo",page);
        return "list";
    }
}

