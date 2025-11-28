/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.student.controller;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;
import jakarta.servlet.http.Cookie;

@WebServlet("/theme")
public class ThemeController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String theme = request.getParameter("mode");
        
        // Validate theme value (security: prevent injection)
        if ("light".equals(theme) || "dark".equals(theme)) {
            // Save preference in cookie for 1 year
            Cookie themeCookie = new Cookie("user_theme", theme);
            themeCookie.setMaxAge(365 * 24 * 60 * 60); // 1 year
            themeCookie.setPath("/");
            themeCookie.setHttpOnly(false); // Allow JavaScript to read for theme switching
            response.addCookie(themeCookie);
        }
        
        // Redirect back to the page user came from
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isEmpty()) {
            response.sendRedirect(referer);
        } else {
            response.sendRedirect("dashboard");
        }
    }
}
