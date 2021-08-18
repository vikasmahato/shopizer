<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>



<div class="tabbable">

    <jsp:include page="/common/adminTabs.jsp" />

    <div class="tab-content">

        <div class="tab-pane active" id="catalogue-section">
            <h3>
                <!-- Page title -->
                <s:message code="menu.product-price" text="Product Price" />
            </h3>
            <br/>
            <div class="control-group">
            <label>Search Product Code</label>
                <div class="controls">
                        <input id="productCode" class="input-large highlight" placeholder="Required" required="true" type="text" value="">
                        <span class="help-inline"><div id="searchStatus" style=""></div></span>
                </div>
            </div>
            <button type="text" class="btn btn-primary">Search</button>

        </div>
    </div>


</div>