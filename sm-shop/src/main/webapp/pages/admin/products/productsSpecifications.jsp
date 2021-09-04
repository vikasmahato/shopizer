<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%@ page session="false" %>

<script type="text/javascript">

    initFeatures();

    function addFeature(count, key) {
    var featureDiv = $('#feature_'+count)
    console.log(featureDiv);
          //featureDiv.append(pillInput);
          //featureDiv.find('input:last').focus()
      //var $features = $('.features')
      var pillInput = $('<span class="tag pill-input"><input type="text" size="1" name="valueMap['+key+']" value=""/><i class="delete">&times;</i></span>');
      featureDiv.append(pillInput);
      console.log(featureDiv);
      initFeatures();
      //$('.input_'+count).last().find('input').focus();
      featureDiv.find('input:last').focus();
    }

    function initFeatures() {
      var $parent = $('.pill-input');
      var $input = $('.pill-input > input');
      var $closeButton = $('.delete');

      $closeButton.click(function() {
        $(this).parent().remove();
      });

      $parent.click(function() {
        $(this).find('input').focus();
      });

      $input.focus(function() {
        $(this).parent().addClass('focus');
      });

      $input.blur(function() {
        var input = $(this);

        if (input.length) {
          input.parent().addClass('tag-gray')
        }

        input.parent().removeClass('focus');
      });

      var e = 'keyup,keypress,focus,blur,change'.split(',');

      for (var i in e) {
        $input.on(e[i], function() {
          var $this = $(this);
          if ($this.val().length == 0) {
            $this.attr('size', 1);
          } else {
            $this.attr('size', $this.val().length);
          }
        });
      }
    }

    $("input").on('change, keyup, keydown, keypress, focus, blur',function (e){
        alert();
        if(e.keyCode == 188){
            e.preventDefault();
        }
    });

    $( document ).ready(function() {
        $('.delete').click(function() {
          $(this).parent().remove();
        });
    });

    function removeFeatures(featureDivId) {
      $('#'+featureDivId).empty();
    }

</script>

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



			<c:url var="addSpecificationValue" value="/admin/products/addProductSpecifications.html" />
			<form:form method="POST" enctype="multipart/form-data" modelAttribute="product_specification" action="${addSpecificationValue}">
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
                   <c:forEach items="${product_specification.valueMap}" var="values" varStatus="spec_count">
                   <tr>
                    <td>${category.specifications[spec_count.index].specification}</td>
                    <td>
                        <c:choose>
                            <c:when test="${category.specifications[spec_count.index].variant == true}">
                                <div class="pad container">
                                  <div class="actions push-bottom">
                                    <button type="button" id="addMore_${spec_count.index}" class="button button-default" data-key="" onClick="addFeature(${spec_count.index}, ${values.key})">Add Values</button>
                                    <!-- <button type="button" id = "removeAll_${spec_count.index}"class="button button-red" onClick="removeFeatures()">Remove All</button> -->
                                  </div>
                                  <div class="features" id="feature_${spec_count.index}">
                                    <c:forEach items="${values.value}" var="key_values">
                                        <span><input class="input-large highlight pill-input input_${spec_count.index}" name="valueMap[${values.key}]" value="${key_values}" required="true">
                                        <i class="delete">&times;</i></span>
                                    </c:forEach>
                                  </div>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <form:input cssClass="input-large highlight" id="name${spec_count.index}" path="valueMap[${values.key}]" required="${(category.specifications[spec_count.index].filter == true) ? true : false }" />
                            </c:otherwise>
                        </c:choose>
                    </td>
                   </tr>

                   </c:forEach>
                 </table>
           </div>


			<input type="hidden" name="productId" value="${product.id}">
			<input type="hidden" name="categoryId" value="${category.category.id}">
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