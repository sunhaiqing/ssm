<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>员工列表</title>
    <meta charset="UTF-8"/>
    <%--
        .setAttribute 这个的使用 maven 引入jsp-api 包
    --%>
    <%
        pageContext.setAttribute("APP_PATH",request.getContextPath());
    %>
    <%--
        web路径：
            1、不以/开始的相对路径，找资源，是以当前资源的路径为基准的，经常容易出问题
            2、以/开始的相对路经，找资源，是服务器的路径为基准的（http://localhost:3306）：
                    因此需要加上项目的名称 /crud
                    (http://localhost:3306/crud)
                    (${APP_PATH}/ == /crud)
    --%>
    <link rel="stylesheet" href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css">
    <script src="${APP_PATH}/static/js/jquery.min.js"></script>
    <script src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">

    <%--标题--%>
    <div class="row">
        <div class="col-md-12">
            <h1>SSM-CRUD</h1>
        </div>
    </div>
    <%--按钮--%>
    <div class="row">
        <div class="col-md-4 col-md-offset-8">
            <button class="btn btn-primary">新增</button>
            <button class="btn btn-danger">删除</button>
        </div>
    </div>
    <%--显示表格数据--%>
    <div class="row">
        <div class="col-md-12">
            <table class="table table-hover" id="emps_table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>empName</th>
                        <th>gender</th>
                        <th>email</th>
                        <th>deptName</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>

                </tbody>

            </table>
        </div>
    </div>
    <%--显示分页信息--%>
    <div class="row">
        <%--分页文字信息--%>
        <div class="col-md-6" id="page_info_area">

        </div>
        <%--分页条信息--%>
        <div class="col-md-6" id="page_nav_area">

        </div>
    </div>
</div>
<script>
    $(function () {
        $.ajax({
            url:"${APP_PATH}/emps",
            data:"pn=1",
            type:"get",
            success:function (result) {
                console.log(result)
                build_emps_table(result);
                build_page_nav(result);
                build_page_info(result);
            }
        })
    })

    function build_emps_table(result) {
        var emps = result.extend.pageInfo.list;
        $.each(emps,function (index, item) {
            var empIdTd = $("<td></td>").append(item.empId)
            var empNameTd = $("<td></td>").append(item.empName)
            var gender = item.gender == "M"?"男":"女"
            var genderTd = $("<td></td>").append(gender)
            var emailTd = $("<td></td>").append(item.email)
            var deptNameTd = $("<td></td>").append(item.department.deptName)
            var editBtn = $("<button></button>")
                .addClass("btn btn-primary btn-sm")
                .append($("<span></span>").addClass("glyphicon glyphicon-pencil"))
                .append("编辑")

            var delBtn = $("<button></button>")
                .addClass("btn btn-danger btn-sm")
                .append($("<span></span>").addClass("glyphicon glyphicon-trash"))
                .append("删除")

            var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn)
            $("<tr></tr>").append(empIdTd)
                .append(empNameTd)
                .append(genderTd)
                .append(emailTd)
                .append(deptNameTd)
                .append(btnTd)
                .appendTo("#emps_table tbody");
        })
    }
    //解析显示分页信息
    function build_page_info(result) {
        $("#page_info_area").append(
            "当前"+result.extend.pageInfo.pageNum+"页，" +
            "总"+result.extend.pageInfo.pages+"页," +
            "总"+result.extend.pageInfo.total+"记录")
    }
    //解析显示分页条的,点击分页要能去下一页
    function build_page_nav(result) {
        var ul = $("<ul></ul>").addClass("pagination")
        var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"))
        var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"))
        var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"))
        var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"))
        ul.append(firstPageLi).append(prePageLi)
        $.each(result.extend.pageInfo.navigatepageNums,function (index, item) {

            var numLi = $("<li></li>").append($("<a></a>").append(item))
            if(result.extend.pageInfo.pageNum == item){
                numLi.addClass("active")
            }
            ul.append(numLi)

        })
        ul.append(nextPageLi).append(lastPageLi)

        var navEle = $("<nav></nav>").append(ul)

        navEle.appendTo("#page_nav_area")
    }
</script>
</body>
</html>
