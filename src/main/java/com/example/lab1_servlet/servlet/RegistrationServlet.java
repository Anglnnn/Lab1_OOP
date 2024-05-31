package com.example.lab1_servlet.servlet;

import com.example.lab1_servlet.dto.UserDTO;
import com.example.lab1_servlet.service.RegistrationControllerService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(displayName = "RegistrUser", urlPatterns = {"/reg"})
public class RegistrationServlet extends HttpServlet {
    private final RegistrationControllerService registrationControllerService;

    public RegistrationServlet(RegistrationControllerService registrationControllerService){
        this.registrationControllerService = registrationControllerService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("resources/templates/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(req.getParameter("name"));
        userDTO.setLogin(req.getParameter("login"));
        userDTO.setPassword(req.getParameter("password"));
        userDTO.setIsBlacklist(false);
        registrationControllerService.save(userDTO);
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
    }
}
