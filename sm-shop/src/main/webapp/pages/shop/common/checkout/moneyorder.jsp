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

                
          <div class="control-group">
            <label class="control-label"><s:message code="label.payment.moneyorder.usemoneyorder" text="Use Money Order" /></label>
            <div class="controls">
               <jsp:include page="/pages/shop/common/checkout/selectedPayment.jsp" />
            </div>
          </div>
          
         <div class="control-group payment-method-box">
         	<s:message code="label.checkout.moneyorder" text="COD (Cash/Cheque on Delivery)"/><br/>
			<c:out value="${requestScope.paymentMethod.informations.integrationKeys['address']}" escapeXml="false"/>
         </div>