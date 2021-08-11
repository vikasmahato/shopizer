<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

        <!-- Additional menu -->
        <div class="btn-group" style="z-index:400000;">
            <button class="btn btn-info dropdown-toggle" data-toggle="dropdown"><s:message code="label.category.configure" text="Category definition"/> ... <span class="caret"></span></button>
             <ul class="dropdown-menu">
                <li><a href="<c:url value="/admin/categories/editCategory.html"/>?id=<c:out value="${adminCategory.id}"/>"><s:message code="label.category.categogydetails" text="Category Details" /></a></li>
                <c:if test = "${adminCategory.depth == 2}">
                <li><a href="<c:url value="/admin/category/specifications/list.html" />?id=<c:out value="${adminCategory.id}"/>"><s:message code="label.category.specifications" text="Specifications" /></a></li>
                </c:if>
             </ul>
        </div>