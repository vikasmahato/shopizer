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
            <button type="text" id="searchButton" onclick="searchProductByCode()" class="btn btn-primary">Search</button>


                <h2 id="productsku"></h2>

                <div id="variantDropdowns"></div>

            <div class="control-group">
                        <label for="sellingPrice">Selling Price</label>
                            <div class="controls">
                                    <input id="sellingPrice" class="input-large highlight" placeholder="Required" required="true" type="number" value="">
                            </div>

                        <label for="dealerPrice">Dealer Price</label>
                            <div class="controls">
                                    <input id="dealerPrice" class="input-large highlight" placeholder="Required" required="true" type="number" value="">
                            </div>
                        <label for="listingPrice">Listing Price</label>
                            <div class="controls">
                                    <input id="listingPrice" class="input-large highlight" placeholder="Required" required="true" type="number" value="">
                            </div>
                        <input class="btn btn-primary" type="submit" value="Submit">

            </div>

        </div>
    </div>


</div>


<script>

function searchProductByCode() {
    $.ajax({
        type: 'GET',
        url: '<c:url value="/admin/products/searchByCode.html"/>?code=' + $("#productCode").val(),
        dataType: 'json',
        success: function(response){
             debugger;
             var data = response.response.data;
             var stringToDisplay = data[0].code + ": " + data[0].name;
             $("#productsku").html(stringToDisplay);

             var specificationDetails = JSON.parse(data[0].specficationDetails);

             var variantOptions = "";

             for (const [key, value] of Object.entries(specificationDetails)) {

               var optionString = "";

                value.forEach(function (item, index) {
                  optionString += "<option value='"+item+"'>"+ item +"</option>"
                });

                var html = "<div class='control-group'>";
                html += "<label>"+key+"</label>";
                html += "<div class='controls'>";
                html += "<select id=''>"; //TODO: Give ID
                html += optionString;
                html += "</select>";
                html += "</div>";
                html += "</div>";

                variantOptions += html;
             }

            $("#variantDropdowns").html(variantOptions);

        },
          error: function(xhr, textStatus, errorThrown) {
            alert('error ' + errorThrown);
        }

    });
}
</script>