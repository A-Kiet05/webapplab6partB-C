/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.student.controller;
import com.student.dao.UserDAO; 
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import java.sql.SQLException;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets; 
import javax.xml.bind.DatatypeConverter;

// src/controller/ChangePasswordController.java
@WebServlet("/change-password")
public class ChangePasswordController extends HttpServlet {
    
    private UserDAO userDAO;

    public void init() throws ServletException {
        this.userDAO = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/change-password.jsp");
        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("login?error=Session expired. Please log in again.");
            return;
        }
        
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        boolean isValid = true;
        Map<String, String> errors = new HashMap<>();

        try {
            // Validate current password (sử dụng DAO.isCurrentPasswordCorrect)
            if (!userDAO.isCurrentPasswordCorrect(userId, currentPassword)) {
                errors.put("errorCurrent", "Current password is incorrect.");
                isValid = false;
            }
            
            // Validate new password (minimum 8 characters)
            if (newPassword == null || newPassword.length() < 8) {
                errors.put("errorNew", "New password must be at least 8 characters.");
                isValid = false;
            }
            
            // Validate confirmation match
            if (!newPassword.equals(confirmPassword)) {
                errors.put("errorConfirm", "New password and confirmation do not match.");
                isValid = false;
            }
            
            if (isValid) {
                
                boolean success = userDAO.updatePassword(userId, newPassword); 
                
                // Show success/error message
                if (success) {
                    request.setAttribute("successMessage", "Password updated successfully!");
                } else {
                    request.setAttribute("errorMessage", "Error updating password. Please try again.");
                }
            }
            
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            e.printStackTrace();
        }

        // Forward errors/messages
        errors.forEach(request::setAttribute);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/change-password.jsp");
        dispatcher.forward(request, response);
    }
    
    
}