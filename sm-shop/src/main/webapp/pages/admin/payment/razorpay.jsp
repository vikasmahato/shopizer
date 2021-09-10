<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>




                 <div class="control-group">
                        <label class="required"><s:message code="module.payment.razorpay.id" text="Key Id"/></label>
	                        <div class="controls">
	                        		<form:input cssClass="input-large highlight" path="integrationKeys['key_id']" />
	                        </div>
	                        <span class="help-inline">
	                        	<c:if test="${username!=null}">
	                        	<span id="identifiererrors" class="error"><s:message code="module.payment.razorpay.message.id" text="Field in error"/></span>
	                        	</c:if>
	                        </span>
                  </div>

                   <div class="control-group">
                        <label class="required"><s:message code="module.payment.razorpay.secret" text="Key Secret"/></label>
	                        <div class="controls">
									<form:input cssClass="input-large highlight" path="integrationKeys['key_secret']" />
	                        </div>
	                        <span class="help-inline">
	                        	<c:if test="${password!=null}">
	                        		<span id="apikeyerrors" class="error"><s:message code="module.payment.razorpay.message.secret" text="Field in error"/></span>
	                        	</c:if>
	                        </span>
                  </div>

					<div class="control-group">
						<label class="required"><s:message code="module.payment.transactiontype" text="Transaction type"/></label>
						<div class="controls">
							<form:radiobutton cssClass="input-large highlight" path="integrationKeys['transaction']" value="AUTHORIZE" />&nbsp;<s:message code="module.payment.transactiontype.auth" text="Authorize" /><br/>
							<form:radiobutton cssClass="input-large highlight" path="integrationKeys['transaction']" value="AUTHORIZECAPTURE" />&nbsp;<s:message code="module.payment.transactiontype.authcap" text="Authorize & Capture" /></br>
						</div>
					</div>