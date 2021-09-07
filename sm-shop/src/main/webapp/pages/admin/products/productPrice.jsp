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

				<h3>
					<s:message code="menu.product-price" text="Product Price" />
				</h3>

			<c:url var="addProductPrice" value="/admin/products/price/save.html" />
			<form:form method="POST" modelAttribute="price" action="${addProductPrice}">
			<form:errors path="*" cssClass="alert alert-error" element="div" />
			<div id="store.success" class="alert alert-success"	style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>">
					<s:message code="message.success" text="Request successfull" />
			</div>

			<div class="control-group">
                <label>Search Product Code</label>
                <div class="controls">
                        <input id="productCode" class="input-large highlight" placeholder="Required" required="true" type="text" value="" name="productCode">
                        <span class="help-inline"><div id="searchStatus" style=""></div></span>
                </div>
            </div>
            <button type="button" id="searchButton" onclick="searchProductByCode()" class="btn btn-primary">Search</button>

                <h2 id="productsku"></h2>

                <div id="variantDropdowns"></div>

           <div class="control-group">

           </div>


			<input type="hidden" name="productId" value="${product.id}">
			<input type="hidden" name="categoryId" value="${category.category.id}">
			<div class="form-actions" style="display: none;">
                  		<div class="pull-right">
                  			<button type="submit" class="btn btn-success"><s:message code="label.generic.add" text="Add"/></button>
                  		</div>
            </div>

            <div class="control-group">
                        <label for="sellingPrice">Selling Price</label>
                            <div class="controls">
                                <input id="sellingPrice" class="input-large highlight" placeholder="Required" required="true" type="text" value="" name="priceText">
                            </div>

                        <label for="dealerPrice">Dealer Price</label>
                            <div class="controls">
                                <input id="sellingPrice" class="input-large highlight" placeholder="Required" required="true" type="text" value="" name="dealerPrice">
                            </div>
                        <label for="listingPrice">Listing Price</label>
                            <div class="controls">
                                <input id="sellingPrice" class="input-large highlight" placeholder="Required" required="true" type="text" value="" name="listPrice">
                            </div>
                        <input class="btn btn-primary" type="submit" value="Submit">

            </div>

		  </form:form>
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
                 console.log(data);

                 var stringToDisplay = data[0].code + ": " + data[0].name;
                 $("#productsku").html(stringToDisplay);

                 var specificationDetails = JSON.parse(data[0].specficationDetails);
                 console.log(specificationDetails);
                 var variantOptions = "";

                 for (const [key, value] of Object.entries(specificationDetails)) {

                   var optionString = "";

                    value.forEach(function (item, index) {
                      optionString += "<option value='"+item.substring( item.indexOf("__")+2)+"'>"+ item.substring( 0, item.indexOf("__")) +"</option>"
                    });

                    var html = "<div class='control-group'>";
                    html += "<label>"+key+"</label>";
                    html += "<div class='controls'>";
                    html += "<select id='' name='variants[]'>"; //TODO: Give ID
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