package com.nowcoder.community.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/alpha")
public class AlphaController {
    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(){
        return "Hello Spring Boot.";
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());//请求路径
        Enumeration<String> enumeration = request.getHeaderNames();//得到请求行的key
        while(enumeration.hasMoreElements()) {
            String name = enumeration.nextElement(); //当前值（key）
            String value = request.getHeader(name);//得到value
            System.out.println(name + ":" + value);
        }
        System.out.println(request.getParameter("code"));

        // 返回响应数据
        response.setContentType("text/html;charset=utf-8");//返回网页类型的文本
        PrintWriter writer = response.getWriter();
        writer.write("<h1>牛客网</h1>");//这里只进行简单输出
        writer.close();
    }

    // GET请求，用于获取某些数据
    // /students?current=1&limit=20 假设查询学生数据，第一页，每页20条
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
//    public String getStudents(int current,int limit) { //直接使用Int类型，前端控制器会自动识别匹配
//        System.out.println(current);
//        System.out.println(limit);
//        return "some students";
//    }
//    也可加上注解
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "1")  int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /student/123  查询某个学生，直接放在路径中
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    //POST请求
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    //响应HTML数据
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {   //这个对象就是向前端控制器返回的moder和view
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "张三");
        mav.addObject("age", 30);
        mav.setViewName("/demo/view");//这个view实际上指的是view.html
        return mav;
    }

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {    //前端控制器会创建一个model
        model.addAttribute("name","大学");
        model.addAttribute("age",80);
        return "/demo/view";  //这里return的是view的路径
    }

    //响应JSON数据（异步请求）
    // Java对象传给浏览器：需要转为JS对象，这时候就可以通过JSON进行转化
    // Java对象-> JSON字符串 -> JS对象等等
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp() {  //自动会将map转为JSON字符串
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","张三");
        emp.put("age",23);
        emp.put("salary",8000.00);
        return emp;
    }
}
