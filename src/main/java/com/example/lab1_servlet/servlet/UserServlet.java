package com.example.lab1_servlet.servlet;

import com.example.lab1_servlet.dto.UserDTO;
import com.example.lab1_servlet.service.UserControllerService;
import com.google.gson.Gson;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/clients")
public class UserServlet extends HttpServlet {
    private UserControllerService userControllerService;
    private Gson gson = new Gson();

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        this.userControllerService = ctx.getBean(UserControllerService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<UserDTO> users = null;
        try {
            users = userControllerService.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String json = gson.toJson(users);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        UserDTO user = null;
        try {
            user = userControllerService.inBlacklistUser(login);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String json = gson.toJson(user);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        UserDTO user = null;
        try {
            user = userControllerService.unBlacklistUser(login);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String json = gson.toJson(user);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}

