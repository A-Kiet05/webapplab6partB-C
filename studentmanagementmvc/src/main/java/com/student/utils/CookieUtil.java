/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.student.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

/**
 * Reusable utility class for static cookie operations:
 * Create, Read, Update, Delete cookies.
 * Uses proper null checking and error handling.
 */
public class CookieUtil {

    // --- 1. CREATE COOKIE ---
    /**
     * Creates and adds a new HTTP cookie to the response.
     * @param response HTTP response
     * @param name Cookie name
     * @param value Cookie value
     * @param maxAge Cookie lifetime in seconds
     */
    public static void createCookie(HttpServletResponse response, String name, String value, int maxAge) {
        // 1. Create new Cookie with name and value
        Cookie cookie = new Cookie(name, value);
        
        // 2. Set maxAge
        cookie.setMaxAge(maxAge);
        
        // 3. Set path to "/" (accessible throughout the application)
        cookie.setPath("/");
        
        // 4. Set HttpOnly to true (helps mitigate XSS attacks)
        cookie.setHttpOnly(true);
        
        // 5. Add cookie to response
        response.addCookie(cookie);
    }
    
    // --- 2. GET COOKIE VALUE (READ) ---
    /**
     * Gets cookie value by name from the request.
     * @param request HTTP request
     * @param name Cookie name
     * @return Cookie value or null if not found
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        // 1. Get all cookies from request
        Cookie[] cookies = request.getCookies();
        
        // Proper null checking for cookies array
        if (cookies == null || name == null || name.isEmpty()) {
            return null;
        }

        // 2. Loop through cookies (using Java 8 stream for efficiency and cleanliness)
        // 3. Find cookie with matching name
        Optional<Cookie> cookie = Arrays.stream(cookies)
                .filter(c -> name.equals(c.getName()))
                .findFirst();

        // 4. Return value or null
        return cookie.isPresent() ? cookie.get().getValue() : null;
    }

    // --- 3. CHECK IF COOKIE EXISTS ---
    /**
     * Check if a cookie exists.
     * @param request HTTP request
     * @param name Cookie name
     * @return true if cookie exists, false otherwise
     */
    public static boolean hasCookie(HttpServletRequest request, String name) {
        // Simply checks if the value returned is not null
        return getCookieValue(request, name) != null;
    }

    // --- 4. DELETE COOKIE ---
    /**
     * Deletes cookie by setting max age to 0.
     * @param response HTTP response
     * @param name Cookie name to delete
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        // 1. Create cookie with same name and empty value
        // 2. Set maxAge to 0 (tells the browser to delete the cookie immediately)
        // 3. Set path to "/" (important: must match the path used to create the cookie)
        // 4. Add to response
        
        // We can reuse the createCookie method with value=empty string and maxAge=0
        createCookie(response, name, "", 0);
    }

    // --- 5. UPDATE COOKIE ---
    /**
     * Updates an existing cookie by creating a new one with the same name.
     * @param response HTTP response
     * @param name Cookie name
     * @param newValue New cookie value
     * @param maxAge New max age
     */
    public static void updateCookie(HttpServletResponse response, String name, String newValue, int maxAge) {
        // Simply create a new cookie with same name (it will overwrite the old one)
        createCookie(response, name, newValue, maxAge);
    }
    
    
}
