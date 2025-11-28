package com.student.controller;

import com.student.dao.StudentDAO;
import com.student.model.Student;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;
import java.io.IOException;
import java.sql.SQLException;

import java.util.List;

@WebServlet("/student")
public class StudentController extends HttpServlet {
    
    private StudentDAO studentDAO;
    
    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException  {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "list";
        }
        try{
        switch (action) {
            case "new":
                showNewForm(request, response);
                break;
            case "edit":
                showEditForm(request, response);
                break;
            case "delete":
                deleteStudent(request, response);
                break;
            case "search": 
                searchStudents(request, response);
                break;
            case "list":
            case "sort":    
            case "filter":  
            default:
                listStudents(request, response);
                break;
        }
        }catch (SQLException ex) {
        throw new ServletException(ex);
    }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        switch (action) {
            case "insert":
                insertStudent(request, response);
                break;
            case "update":
                updateStudent(request, response);
                break;
        }
    }
    
    private String validateSortBy(String sortBy) {
    String[] validColumns = {"id", "student_code", "full_name", "email", "major"};
    if (sortBy != null) {
        for (String col : validColumns) {
            if (col.equalsIgnoreCase(sortBy.trim())) {
                return col;
            }
        }
    }
    return "id"; // Default
}

private String validateOrder(String order) {
    if ("desc".equalsIgnoreCase(order)) {
        return "DESC";
    }
    return "ASC"; // Default
}
    
    //validate
    private boolean validateStudent(Student student, HttpServletRequest request) {
    boolean isValid = true;
    
    // Regex for Student Code: 2 uppercase letters + 3 or more digits (e.g., SV001, IT123)
    // String codePattern = "^[A-Z]{2}[0-9]{3,}$"; // Regex Hint 
    String codePattern = "^[A-Z]{2}[0-9]{3,}.*$"; // Adjusted to match hint structure: [A-Z]{2}[0-9]{3,}

    // Regex for Email: simple validation (example: user@domain.com)
    // String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"; // Standard, complex version
    String emailPattern = "^[A-Za-z0-9._%+-]+@(.+)\\.(.+)$"; // Simple version based on hint

    String studentCode = student.getStudentCode();
    String fullName = student.getFullName();
    String email = student.getEmail();

    // --- 1. Validate Student Code (Required + Pattern) ---
    if (studentCode == null || studentCode.trim().isEmpty()) {
        request.setAttribute("errorCode", "Student code is required");
        isValid = false;
    } else if (!Pattern.matches(codePattern, studentCode)) {
        request.setAttribute("errorCode", "Invalid format. Must be 2 uppercase letters followed by 3+ digits (e.g., IT123)");
        isValid = false;
    }

    // --- 2. Validate Full Name (Required + Min Length 2) ---
    if (fullName == null || fullName.trim().isEmpty()) {
        request.setAttribute("errorName", "Full Name is required");
        isValid = false;
    } else if (fullName.trim().length() < 2) {
        request.setAttribute("errorName", "Full Name must be at least 2 characters long");
        isValid = false;
    }

    // --- 3. Validate Email (Optional, but if provided, must be valid) ---
    if (email != null && !email.trim().isEmpty()) {
        if (!Pattern.matches(emailPattern, email)) {
            request.setAttribute("errorEmail", "Invalid email format. Must contain '@' and '.'");
            isValid = false;
        }
    }
    
    // Set student back to request scope so form fields can be re-populated
    request.setAttribute("student", student);

    return isValid;
}
    
    //search students
    private void searchStudents(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Student> students = null;

        // 1. Get keyword parameter from request
        String keyword = request.getParameter("keyword");
        String trimmedKeyword = (keyword != null) ? keyword.trim() : "";
        
        // 2. Decide which DAO method to call (Handle null/empty keyword gracefully)
        if (trimmedKeyword.isEmpty()) {
            // If keyword is empty, display all students (using the default list logic)
            students = studentDAO.getAllStudents(); 
        } else {
            // Call DAO's search method for multi-column search
            students = studentDAO.searchStudents(trimmedKeyword); 
        }
        
        // 3. Set BOTH students and keyword as request attributes
        request.setAttribute("students", students);
        request.setAttribute("keyword", trimmedKeyword); // For highlighting and displaying search term
        
        // 4. Forward to view
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student-list.jsp");
        dispatcher.forward(request, response);
    }
    
    // List all students
    
private void listStudents(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException, SQLException{
    
    
    String sortByParam = request.getParameter("sortBy");
    String orderParam = request.getParameter("order");
    String majorFilterParam = request.getParameter("major");
    
   
    String validatedSortBy = validateSortBy(sortByParam);
    String validatedOrder = validateOrder(orderParam);
    
    
    String majorFilter = (majorFilterParam != null && !majorFilterParam.isEmpty()) ? majorFilterParam : null;

    
    List<Student> students;
    
    if (majorFilter != null) {
       
        students = studentDAO.getStudentsByMajor(majorFilter);
        
    } else if (sortByParam != null || orderParam != null) {
        
        students = studentDAO.getStudentsSorted(validatedSortBy, validatedOrder); 
        
    } else {
        
        students = studentDAO.getAllStudents(); 
    }
    
    
    request.setAttribute("students", students);
    
    
    request.setAttribute("sortBy", validatedSortBy);
    request.setAttribute("order", validatedOrder);
    request.setAttribute("majorFilter", majorFilter); 
    
    
    RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student-list.jsp");
    dispatcher.forward(request, response);
}
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student-form.jsp");
        dispatcher.forward(request, response);
    }
    
    // Show form for editing student
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        Student existingStudent = studentDAO.getStudentById(id);
        
        request.setAttribute("student", existingStudent);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student-form.jsp");
        dispatcher.forward(request, response);
    }
    
    // Insert new student
    private void insertStudent(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String studentCode = request.getParameter("studentCode");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String major = request.getParameter("major");
        
        Student newStudent = new Student(studentCode, fullName, email, major);
        
        
        if (!validateStudent(newStudent, request)) {
    // 
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student-form.jsp");
               dispatcher.forward(request, response);
               return; 
     }
        if (studentDAO.addStudent(newStudent)) {
        response.sendRedirect("student?action=list&message=Added successfully");
    } else {
        response.sendRedirect("student?action=list&error=Failed to add student");
    }
  }
    
    // Update student
    private void updateStudent(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException,IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        String studentCode = request.getParameter("studentCode");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String major = request.getParameter("major");
        
        Student student = new Student(studentCode, fullName, email, major);
        student.setId(id);
        
       if (!validateStudent(student, request)) {
   
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/student-form.jsp");
              dispatcher.forward(request, response);
              return; 
              
            }
       
       if (studentDAO.updateStudent(student)) {
        
        response.sendRedirect("student?action=list&message=Student updated successfully");
    } else {
        
        response.sendRedirect("student?action=list&error=Failed to update student");
    }
         
        
}
    
    // Delete student
    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        if (studentDAO.deleteStudent(id)) {
            response.sendRedirect("student?action=list&message=Student deleted successfully");
        } else {
            response.sendRedirect("student?action=list&error=Failed to delete student");
        }
    }
    
}
