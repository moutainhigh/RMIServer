package com.hgsoft.servlet.action;



import com.hgsoft.servlet.serviceInterface.ITestService;
import com.hgsoft.utils.SpringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;



/**
 * Created by zsx on 2017/10/12 0012.
 */

public class TestServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ITestService testService = SpringUtil.context.getBean("testRmi", ITestService.class);
        boolean flag = false;
        flag = testService.testSql();
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if(flag)
                out.print("0");
            else
                response.setStatus(500);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
