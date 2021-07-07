<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>


<script type="text/javascript">
    window.opener.location.reload(false);
    window.setTimeout('window.close()', 2000);
</script>


<div class="tabbable">
    <jsp:include page="/common/adminTabs.jsp" />
    <div class="tab-content">

        <div class="tab-pane active" id="admin-quickbooks">

            <div style="text-align: center; font-family: sans-serif; font-weight: bold;">
                You're connected! Please wait...
            </div>

        </div>

    </div>
</div>