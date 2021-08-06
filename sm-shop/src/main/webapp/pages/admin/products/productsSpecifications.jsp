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


           			<c:if test="${product.id!=null && product.id>0}">
						<c:set value="${product.id}" var="productId" scope="request"/>
						<jsp:include page="/pages/admin/products/product-menu.jsp" />
					</c:if>


				<h3>
					<s:message code="label.product.category.association" text="Associate to specifications" />
				</h3>



			<c:url var="addCategory" value="/admin/products/addProductToCategories.html" />
			<form:form method="POST" enctype="multipart/form-data" modelAttribute="product" action="${addCategory}">
			<form:errors path="*" cssClass="alert alert-error" element="div" />
			<div id="store.success" class="alert alert-success"	style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>">
					<s:message code="message.success" text="Request successfull" />
			</div>
			<br/>
			<strong><c:out value="${product.sku}"/></strong>
			<br/><br/>


           <div class="control-group">
                 <table id="specifications">
                   <th>Specification</th>
                   <th>Value</th>

                   <c:forEach items="${categories}"  varStatus="count">
                   <c:forEach items="${categories[count.index].specifications}" varStatus="counter">

                   <tr>
                    <td>${categories[count.index].specifications[counter.index].specification}</td>
                    <td><form:input cssClass="input-large highlight" id="name${counter.index}" path="${product_specifications.value}" required="${(categories[count.index].specifications[counter.index].filter == 1) ? true : false }" /></td>
                   </tr>

                   </c:forEach>
                   </c:forEach>
                 </table>
           </div>


			<input type="hidden" name="productId" value="${product.id}">
			<div class="form-actions">
                  		<div class="pull-right">
                  			<button type="submit" class="btn btn-success"><s:message code="label.generic.add" text="Add"/></button>
                  		</div>
            	 </div>

		  </form:form>



				<br />



		</div>
	   </div>
	</div>
</div>