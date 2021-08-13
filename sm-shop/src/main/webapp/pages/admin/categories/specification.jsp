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

                <h3>
                    <s:message code="label.category.specifications" text="Specifications" />
                </h3>
                <br/>
                <strong><c:out value="${category.code}"/></strong>

                <br/><br/>

                <c:url var="specificationSave" value="/admin/category/specifications/save.html"/>
                <form:form method="POST" modelAttribute="category_specification" action="${specificationSave}">

                    <form:errors path="*" cssClass="alert alert-error" element="div" />
                    <div id="store.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>"><s:message code="message.success" text="Request successfull"/></div>

                    <div class="control-group">
                        <label><s:message text="Specification Name"/></label>
                        <div class="controls">
                            <form:input id="specification" cssClass="highlight" path="specification"/>
                            <span id="help-specification" class="help-inline"><form:errors path="specification" cssClass="error" /></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label><s:message text="Filter"/></label>
                        <div class="controls">
                                    <form:checkbox id="filter" path="filter"/>
                                    <span class="help-inline"><form:errors path="filter" cssClass="error" /></span>
                        </div>
                    </div>

                    <div class="control-group">
                        <label><s:message text="Variant"/></label>
                        <div class="controls">
                                    <form:checkbox id="variant" path="variant"/>
                                    <span class="help-inline"><form:errors path="variant" cssClass="error" /></span>
                        </div>
                    </div>

                    <form:hidden path="id" />
                    <form:hidden path="category.id" />

                    <div class="form-actions">
                        <div class="pull-right">
                            <button type="submit" class="btn btn-success"><s:message code="button.label.submit" text="Submit"/></button>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
