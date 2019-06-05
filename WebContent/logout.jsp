<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.*"%>
<%
HttpSession session = request.getSession(false);
if(session != null && session.getAttribute("user") != null){
    session.removeAttribute("user");
    session.removeAttribute("uid");
}
%>