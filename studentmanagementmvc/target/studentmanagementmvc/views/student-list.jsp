<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student List - MVC</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
        }
        
        h1 {
            color: #333;
            margin-bottom: 10px;
            font-size: 32px;
        }
        
        .subtitle {
            color: #666;
            margin-bottom: 30px;
            font-style: italic;
        }
        
        .message {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            font-weight: 500;
        }
        
        .success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        
        .error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        .btn {
            display: inline-block;
            padding: 12px 24px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: 500;
            transition: all 0.3s;
            border: none;
            cursor: pointer;
            font-size: 14px;
        }
        
        .btn-primary {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        .btn-primary:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.4);
        }
        
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-danger {
            background-color: #dc3545;
            color: white;
            padding: 8px 16px;
            font-size: 13px;
        }
        
        .btn-danger:hover {
            background-color: #c82333;
        }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        
        thead {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        
        th {
            font-weight: 600;
            text-transform: uppercase;
            font-size: 13px;
            letter-spacing: 0.5px;
        }
        
        tbody tr {
            transition: background-color 0.2s;
        }
        
        tbody tr:hover {
            background-color: #f8f9fa;
        }
        
        .actions {
            display: flex;
            gap: 10px;
        }
        
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #999;
        }
        
        .empty-state-icon {
            font-size: 64px;
            margin-bottom: 20px;
        }
        .search-box {
    margin-bottom: 20px;
    padding: 15px;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #f7f7f7;
}

.search-box form {
    display: flex;
    gap: 10px;
    align-items: center;
}

.search-box input[type="text"] {
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    flex-grow: 1; 
}


.btn-clear {
    background-color: #ffc107;
    color: #343a40;
    padding: 10px 15px;
    text-decoration: none;
    border-radius: 5px;
    font-weight: 500;
}

.btn-clear:hover {
    background-color: #e0a800;
}
/* Thêm vào khối <style> hiện có trong student-list.jsp */
.filter-box {
    display: flex;
    align-items: center;
    gap: 20px;
    padding: 15px 0;
    margin-bottom: 20px;
    border-bottom: 1px solid #eee;
}

.filter-box label {
    font-weight: 600;
    color: #444;
}

.filter-box select {
    padding: 8px 12px;
    border: 1px solid #ccc;
    border-radius: 5px;
    transition: border-color 0.3s;
}

.filter-box select:focus {
    outline: none;
    border-color: #667eea;
}

.btn-clear-filter {
    text-decoration: none;
    padding: 8px 12px;
    border-radius: 5px;
    background-color: #f8d7da;
    color: #721c24;
    font-size: 14px;
    border: 1px solid #f5c6cb;
    transition: background-color 0.3s;
}

.btn-clear-filter:hover {
    background-color: #f5c6cb;
}


th a {
    text-decoration: none;
    color: #333; 
    display: inline-block;
}

th a:hover {
    color: #667eea;
}
.navbar {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px 30px;
    background-color: #333;
    color: white;
    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    margin-bottom: 20px;
}

.navbar h2 {
    font-size: 24px;
    margin: 0;
}

.navbar-right {
    display: flex;
    align-items: center;
    gap: 20px;
}

.user-info {
    display: flex;
    align-items: center;
    gap: 10px;
    font-size: 14px;
}

.role-badge {
    padding: 4px 8px;
    border-radius: 5px;
    font-weight: bold;
    text-transform: uppercase;
    font-size: 11px;
    color: white;
}

.role-admin {
    background-color: #dc3545; /* Red for Admin */
}

.role-user {
    background-color: #007bff; /* Blue for regular User */
}

.btn-nav, .btn-logout {
    color: white;
    text-decoration: none;
    padding: 8px 15px;
    border-radius: 5px;
    transition: background-color 0.2s;
}

.btn-nav:hover {
    background-color: #555;
}

.btn-logout {
    background-color: #f8f9fa;
    color: #333;
}
.btn-logout:hover {
    background-color: #e2e6ea;
}


.message {
    
    display: flex;
    align-items: center;
    gap: 10px;
}
    </style>
</head>
<body>
    
    



    <!-- Navigation Bar -->
    <div class="navbar">
        <h2>📚 Student Management System</h2>
        <div class="navbar-right">
            <div class="user-info">
                <span>Welcome, ${sessionScope.fullName}</span>
                <span class="role-badge role-${sessionScope.role}">
                    ${sessionScope.role}
                </span>
            </div>
            <a href="dashboard" class="btn-nav">Dashboard</a>
            <a href="logout" class="btn-logout">Logout</a>
        </div>
    </div>
    
   


    <div class="container">
        <h1>📚 Student Management System</h1>
        <p class="subtitle">MVC Pattern with Jakarta EE & JSTL</p>
        
        <!-- Success Message -->
        <c:if test="${not empty param.message}">
            <div class="message success">
                ✅ ${param.message}
            </div>
        </c:if>
        
        <!-- Error Message -->
        <c:if test="${not empty param.error}">
            <div class="message error">
                ❌ ${param.error}
            </div>
        </c:if>
        
        <div class="search-box">
            <form action="student" method="GET">
                <input type="hidden" name="action" value="search">
                
                <input type="text" 
                       name="keyword" 
                       placeholder="Search by name, code, or email..." 
                       value="<c:out value="${keyword}"/>">
                
                <button type="submit" class="btn btn-primary">
                    🔍 Search
                </button>
                
                <c:if test="${isSearching}">
                    <a href="student?action=list" class="btn-clear">
                        Show All
                    </a>
                </c:if>
            </form>
        </div>
        
        <c:if test="${isSearching}">
            <p style="margin-bottom: 15px; font-style: italic;">
                Search results for: 
                <strong>"<c:out value="${keyword}"/>"</strong> 
                (${fn:length(students)} students found)
            </p>
        </c:if>
        
        <!-- Add New Student Button -->
        <div style="margin-bottom: 20px;">
            <c:if test="${sessionScope.role eq 'admin'}">
                <a href="student?action=new" class="btn btn-primary">
                    ➕ Add New Student
                </a>
            </c:if>
        </div>
        
        <div class="filter-box">
    <form action="student" method="GET">
        <input type="hidden" name="action" value="list"> 
        
        <label for="major">Filter by Major:</label>
        <select name="major" id="major" onchange="this.form.submit()">
            
            <c:set var="selectedMajor" value="${requestScope.majorFilter}"/>
            
            <option value="">-- All Majors --</option>
            
            <option value="Computer Science" 
                    ${selectedMajor == 'Computer Science' ? 'selected' : ''}>
                Computer Science
            </option>
            <option value="Information Technology" 
                    ${selectedMajor == 'Information Technology' ? 'selected' : ''}>
                Information Technology
            </option>
            <option value="Software Engineering" 
                    ${selectedMajor == 'Software Engineering' ? 'selected' : ''}>
                Software Engineering
            </option>
            <option value="Business Administration" 
                    ${selectedMajor == 'Business Administration' ? 'selected' : ''}>
                Business Administration
            </option>
        </select>
        
        <input type="hidden" name="sortBy" value="${requestScope.sortBy}">
        <input type="hidden" name="order" value="${requestScope.order}">

        <button type="submit" style="display: none;">Apply Filter</button>
    </form>
    
    <c:if test="${not empty requestScope.majorFilter}">
        <a href="student?action=list" class="btn-clear-filter">❌ Clear Filter</a>
    </c:if>
</div>
        
        <!-- Student Table -->
        <c:choose>
            <c:when test="${not empty students}">
                <table>
                    <thead>
                        <tr>
                            <c:set var="currentSortBy" value="${requestScope.sortBy}"/>
                            <c:set var="currentOrder" value="${requestScope.order}"/>
                            <c:set var="majorFilter" value="${requestScope.majorFilter}"/>
                            
                            <th>
                                <c:url var="sortUrl" value="student">
                                    <c:param name="action" value="list"/>
                                    <c:param name="sortBy" value="id"/>
                                    <c:param name="order" value="${currentSortBy == 'id' && currentOrder == 'ASC' ? 'DESC' : 'ASC'}"/>
                                    <c:param name="major" value="${majorFilter}"/>
                                </c:url>
                                <a href="${sortUrl}">
                                    ID
                                    <c:if test="${currentSortBy == 'id'}">
                                        <span style="color: yellow;">${currentOrder == 'ASC' ? '▲' : '▼'}</span>
                                    </c:if>
                                </a>
                            </th>
                            
                            <th>
                                <c:url var="sortUrl" value="student">
                                    <c:param name="action" value="list"/>
                                    <c:param name="sortBy" value="student_code"/>
                                    <c:param name="order" value="${currentSortBy == 'student_code' && currentOrder == 'ASC' ? 'DESC' : 'ASC'}"/>
                                    <c:param name="major" value="${majorFilter}"/>
                                </c:url>
                                <a href="${sortUrl}">
                                    Student Code
                                    <c:if test="${currentSortBy == 'student_code'}">
                                        <span style="color: yellow;">${currentOrder == 'ASC' ? '▲' : '▼'}</span>
                                    </c:if>
                                </a>
                            </th>
                            
                            <th>
                                <c:url var="sortUrl" value="student">
                                    <c:param name="action" value="list"/>
                                    <c:param name="sortBy" value="full_name"/>
                                    <c:param name="order" value="${currentSortBy == 'full_name' && currentOrder == 'ASC' ? 'DESC' : 'ASC'}"/>
                                    <c:param name="major" value="${majorFilter}"/>
                                </c:url>
                                <a href="${sortUrl}">
                                    Full Name
                                    <c:if test="${currentSortBy == 'full_name'}">
                                        <span style="color: yellow;">${currentOrder == 'ASC' ? '▲' : '▼'}</span>
                                    </c:if>
                                </a>
                            </th>
                            
                            <th>
                                <c:url var="sortUrl" value="student">
                                    <c:param name="action" value="list"/>
                                    <c:param name="sortBy" value="email"/>
                                    <c:param name="order" value="${currentSortBy == 'email' && currentOrder == 'ASC' ? 'DESC' : 'ASC'}"/>
                                    <c:param name="major" value="${majorFilter}"/>
                                </c:url>
                                <a href="${sortUrl}">
                                    Email
                                    <c:if test="${currentSortBy == 'email'}">
                                        <span style="color: yellow;">${currentOrder == 'ASC' ? '▲' : '▼'}</span>
                                    </c:if>
                                </a>
                            </th>

                            <th>
                                <c:url var="sortUrl" value="student">
                                    <c:param name="action" value="list"/>
                                    <c:param name="sortBy" value="major"/>
                                    <c:param name="order" value="${currentSortBy == 'major' && currentOrder == 'ASC' ? 'DESC' : 'ASC'}"/>
                                    <c:param name="major" value="${majorFilter}"/>
                                </c:url>
                                <a href="${sortUrl}">
                                    Major
                                    <c:if test="${currentSortBy == 'major'}">
                                        <span style="color: yellow;">${currentOrder == 'ASC' ? '▲' : '▼'}</span>
                                    </c:if>
                                </a>
                            </th>
                            
                           <c:if test="${sessionScope.role eq 'admin'}">
                                <th>Actions</th>
                            </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="student" items="${students}">
                            <tr>
                                <td>${student.id}</td>
                                <td><strong>${student.studentCode}</strong></td>
                                <td>${student.fullName}</td>
                                <td>${student.email}</td>
                                <td>${student.major}</td>
                                <c:if test="${sessionScope.role eq 'admin'}">
                                <td>
                                    <div class="actions">
                                        <a href="student?action=edit&id=${student.id}" class="btn btn-secondary">
                                            ✏️ Edit
                                        </a>
                                        <a href="student?action=delete&id=${student.id}" 
                                           class="btn btn-danger"
                                           onclick="return confirm('Are you sure you want to delete this student?')">
                                            🗑️ Delete
                                        </a>
                                    </div>
                                </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <div class="empty-state-icon">📭</div>
                    <h3>No students found</h3>
                    <p>Start by adding a new student</p>
                </div>
            </c:otherwise>
        </c:choose>
        
        
    </div>
</body>
</html>
