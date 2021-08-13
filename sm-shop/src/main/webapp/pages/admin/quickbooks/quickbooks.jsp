<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>

<script src="<c:url value="/resources/js/jquery.showLoading.min.js" />"></script>
<script src="<c:url value="/resources/js/toastr.min.js" />"></script>
<link href="<c:url value="/resources/css/showLoading.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/toastr.min.css" />" rel="stylesheet">

<script>

    function launchPopup(path) {
        var win;
        var parameters = "location=1,width=800,height=650";
        parameters += ",left=" + (screen.width - 800) / 2 + ",top=" + (screen.height - 650) / 2;
        // Launch Popup
        window.open(path, 'connectPopup', parameters);
    }



    $(function() {

        $(".signin-with-intuit").click(function() {
            $(".alert-error").hide();
            $(".alert-success").hide();
            $('.tab-content').showLoading();
            launchPopup('signInWithIntuit')
            return false;
        });
    });

    $(function() {

        $(".connect-to-quickbooks").click(function() {
            $(".alert-error").hide();
            $(".alert-success").hide();
            $('.tab-content').showLoading();
            launchPopup('connectToQuickbooks')
            return false;
        });
    });


</script>


<div class="tabbable">


    <jsp:include page="/common/adminTabs.jsp" />

    <div class="tab-content">


        <div class="tab-pane active" id="admin-quickbooks">


            <div class="sm-ui-component">
                <h3><s:message code="menu.quickbooks" text="Quickbooks" /></h3>
                <br/>
                <s:message code="text.qbo.intro"/>
                <ul>
                    <li>
                        <s:message code="text.qbo.step1"/>
                    </li>

                    <li>
                        <s:message code="text.qbo.step2"/>
                    </li>
                </ul>
                <br /><br /><br />

                <!-- Sign In With Intuit Button -->
                <b><s:message code="label.qbo.signin"/></b><br />

                <button type="button" class="btn signin-with-intuit"><s:message code="button.label.loginQBO" text="Login to QBO" /></button>

                <br /><br /><br />

                <!-- Connect To QuickBooks Button -->
                <b><s:message code="label.qbo.connect"/></b><br />

                <button type="button" class="btn connect-to-quickbooks"><s:message code="button.label.connectQBO" text="Connect to QBO" /></button>

                <br /><br /><br />
            </div>


        </div>

    </div>
</div>