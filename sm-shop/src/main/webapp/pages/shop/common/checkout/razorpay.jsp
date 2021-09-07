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
            <label class="control-label"><s:message code="label.payment.moneyorder.usemoneyorder" text="Use money order" /></label>
            <div class="controls">
               <jsp:include page="/pages/shop/common/checkout/selectedPayment.jsp" />
            </div>
          </div>

         <div class="control-group payment-method-box">
         <button id="rzp-button1">Pay</button>

         	<s:message code="label.checkout.moneyorder" text="Please make your check or money order payable to:"/><br/>
			<c:out value="${requestScope.paymentMethod.informations.integrationKeys['key_id']}" escapeXml="false"/>
         </div>


         <script src="https://checkout.razorpay.com/v1/checkout.js"></script>

         <script>
         var options = {
             "key": "${requestScope.paymentMethod.informations.integrationKeys['key_id']}", // Enter the Key ID generated from the Dashboard
             "amount": "50000", // Amount is in currency subunits. Default currency is INR. Hence, 50000 refers to 50000 paise
             "currency": "INR",
             "name": "Screws n Tools",
             "description": "Test Transaction",
             "image": "https://example.com/your_logo",
             "order_id": "order_", //This is a sample Order ID. Pass the `id` obtained in the response of Step 1
             "handler": function (response){
                 alert(response.razorpay_payment_id);
                 alert(response.razorpay_order_id);
                 alert(response.razorpay_signature)
             },
             "prefill": {
                 "name": "Gaurav Kumar",
                 "email": "gaurav.kumar@example.com",
                 "contact": "9999999999"
             },
             "notes": {
                 "address": "Razorpay Corporate Office"
             },
             "theme": {
                 "color": "#3399cc"
             }
         };
         var rzp1 = new Razorpay(options);
         rzp1.on('payment.failed', function (response){
                 alert(response.error.code);
                 alert(response.error.description);
                 alert(response.error.source);
                 alert(response.error.step);
                 alert(response.error.reason);
                 alert(response.error.metadata.order_id);
                 alert(response.error.metadata.payment_id);
         });
         document.getElementById('rzp-button1').onclick = function(e){
             rzp1.open();
             e.preventDefault();
         }
         </script>