package com.coral.community.controller;

import com.coral.community.service.AlphaService;
import org.aspectj.lang.annotation.AdviceName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {
    @Autowired
    private AlphaService alphaService;

    @RequestMapping("hello")
    @ResponseBody
    public String sayHello() {
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        //get request data
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());

        Enumeration<String> enumeration = request.getHeaderNames();

        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" + value);
        }
        System.out.println(request.getParameter("code"));
        // return response data
        response.setContentType("text/html;charset =utf-8");
        try (PrintWriter writer = response.getWriter();) {
            writer.write("<h1> community </h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //get meth
    // /students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    @RequestMapping(path = "/students/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "a student";
    }

    // POST
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(@RequestParam("namE") String name, @RequestParam("agE") int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }


    //return dynamic html to web(view.html)
    @RequestMapping(path = "/teacher", method = RequestMethod.GET)
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "zhang");
        mav.addObject("age", 20);
        mav.setViewName("/demo/view");
        return mav;
    }
    // better way compared above
    @RequestMapping(path = "/school",method = RequestMethod.GET)
    public String getSchool(Model model){
        model.addAttribute("name","harvard");
        model.addAttribute("age",100);
        return "/demo/view";
    }

    // response to asynchronous reqeust, visit server and get response without refreshing page
    // java object - json text -JS object
    @RequestMapping(path = "/emp",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getEmp(){
        Map<String,Object> emp = new HashMap<>();
        emp.put("name","zhang");
        emp.put("age",23);
        emp.put("salary",8000);
        return emp;
    }
    @RequestMapping(path = "/emps",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> getEmps(){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String ,Object> emp = new HashMap<>();
        emp.put("name","zhang");
        emp.put("age",23);
        emp.put("salary",8000);
        list.add(emp);
        emp = new HashMap<>();
        emp.put("name","li");
        emp.put("age",24);
        emp.put("salary",7000);
        list.add(emp);
        return list;
    }

}


