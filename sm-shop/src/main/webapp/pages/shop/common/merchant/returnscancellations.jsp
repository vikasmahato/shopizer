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
    <h1 class="contact-title"><s:message code="label.customer.refundsandcancellations" text="Refunds & Cancellations"/></h1>
    <div id="mainRefundsCancellationsRow" class="row-fluid common-row">
        Screws n Tools retains the exclusive and sole right as to whether an item may be returned, exchanged or order cancelled. Screws n Tools thus encourages customers to use care when placing an order, as once placed the order cannot be cancelled, nor the item returned or exchanged unless Screws n Tools expressly consents. In order to obtain a refund or exchange, prior approval must be given by Screws n Tools. Please mail us at <a href="mailto:care@screwsntools.com"><u><b>care@screwsntools.com</b></u></a> for the same.<p> Generally, Screws n Tools will consider returns within 4 to 5 days of purchase and only if all the Product(s) and Packaging are intact and in original form. Refunds are for the cost of product only; shipping and handling charges are not refundable.<p>Due to their product nature, Screws n Tools cannot accept returns on Hand Tools.

        Screws n Tools cannot accept responsibility for damage incurred after a customer has signed for delivered products in good condition. <p>
        <ul style="list-style-type: disc;">
            <lh><b>Product Cancellation Criteria</b></lh>
            <li >Products must be returned within 4-5 days of receipt for refund or exchange.</li>
            <li >Products must be unused or defective to be returned. There may be a 15% restocking fee for products that do not meet these conditions.</li>
            <li >In case of wrong product price or specification mentioned on screwsntools.com.</li>
            <li >Processing your return via mail will take 1-2 weeks.</li>
            <li >Refunds are for the cost of the products only.</li>
            <li >Hand Tools Categories products cannot be returned.</li>
        </ul>
    </div>
</div>