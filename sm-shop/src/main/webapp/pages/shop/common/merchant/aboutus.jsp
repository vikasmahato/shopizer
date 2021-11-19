<%
response.setCharacterEncoding("UTF-8");
response.setHeader("Cache-Control","no-cache");
response.setHeader("Pragma","no-cache");
response.setDateHeader ("Expires", -1);
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/shopizer-tags.tld" prefix="sm" %> 
 
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>


	<div id="main-content" class="container clearfix">
	<h1 class="contact-title"><s:message code="label.customer.aboutus" text="About Us"/></h1>
		<div id="mainAboutUsRow" class="row-fluid common-row">
			“Screws n Tools” is India’s first one-stop construction shop. We have all the construction equipment in a single place – Power Tools, Hand Tools, Safety Equipment, Fasteners etc. and can deliver in as little as 24 hours thanks to our network of pan-India warehouses. We are driven by our vision to provide world class services to our clients and at the same time create unprecedented value and opportunity to our employees, investors and ecosystem partners.
        </div>
     </div>