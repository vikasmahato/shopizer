<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>



<div class="tabbable">

    <jsp:include page="/common/adminTabs.jsp" />

    <div class="tab-content">
        <div class="tab-pane active" id="catalogue-section">
            <div class="sm-ui-component">

                <c:if test="${category.id!=null && category.id>0}">
                    <c:set value="${category}" var="adminCategory" scope="request"/>
                        <jsp:include page="/pages/admin/categories/category-menu.jsp" />
                </c:if>

                <h3><s:message code="label.category.specifications" text="category specifications" /></h3>
                <br/>
                <strong><c:out value="${category.code}"/></strong>

                <br/>
                <a href="<c:url value="/admin/category/createSpecification.html?categoryId=${category.id}"/>"><s:message code="label.category.specifications.create" text="Create category specification" /></a>
                <br/><br/>

                 <!-- Listing grid include -->
                 <c:set value="/admin/specifications/page.html?categoryId=${category.id}" var="pagingUrl" scope="request" />
                 <!--<c:set value="/admin/specifications/specification/remove.html" var="removeUrl" scope="request"/>-->
                 <c:set value="/admin/category/editSpecification.html" var="editUrl" scope="request"/>
                 <c:set value="/admin/categories/specifications/list.html?id=${category.id}" var="afterRemoveUrl" scope="request"/>
                 <c:set var="entityId" value="specificationId" scope="request"/>
                 <c:set var="appendQueryStringToEdit" value="categoryId=${category.id}" scope="request"/>
                 <c:set var="groupByEntity" value="category_specification" scope="request"/>
                 <c:set var="componentTitleKey" value="label.category.specifications" scope="request"/>
                 <c:set var="gridHeader" value="/pages/admin/categories/specifications-gridHeader.jsp" scope="request"/>
                 <c:set var="canRemoveEntry" value="true" scope="request"/>

                 <jsp:include page="/pages/admin/components/list.jsp"></jsp:include>
                 <!-- End listing grid include -->

            </div>
        </div>
    </div>
</div>