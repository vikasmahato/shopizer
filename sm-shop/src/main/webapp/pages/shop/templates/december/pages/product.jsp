
<%
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/WEB-INF/shopizer-tags.tld" prefix="sm"%>

<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<script
	src="<c:url value="/resources/js/jquery.elevateZoom-3.0.8.min.js" />"></script>
<script src="<c:url value="/resources/js/jquery.raty.min.js" />"></script>


<div id="shop" class="container">
	<!-- all-hyperion-page-start -->
	<div class="all-hyperion-page">
		<div class="container">
			<div class="row">
				<div style="margin-top:20px;">
				<jsp:include page="/pages/shop/templates/exoticamobilia/sections/breadcrumb.jsp" />
				</div>
				<div class="col-lg-9 col-md-9 col-sm-12 col-xs-12">
					<!-- product-simple-area-start -->
					<div class="product-simple-area ptb-80 ptb-40-md ptb-20-xs">
						<div class="row">
							<div class="col-lg-5 col-md-7 col-sm-7 col-xs-12">
								<div class="tab-content">
									<div class="tab-pane active" id="view1">
										<a class="image-link" href="${product.image.imageUrl}"><img
											src="${product.image.imageUrl}" alt=""></a>
									</div>
									<c:if
										test="${product.images!=null && fn:length(product.images) gt 1}">
										<c:forEach items="${product.images}" var="thumbnail">
											<c:if test="${thumbnail.imageType==0}">
												<div class="tab-pane"
													id="view<c:out value="${thumbnail.id}"/>">
													<c:choose>
														<c:when test="${thumbnail.externalUrl==null}">
															<a href="<c:url value="${thumbnail.imageUrl}"/>"
																class="image-link"
																imgId="im-<c:out value="${thumbnail.id}"/>"
																title="<c:out value="${thumbnail.imageName}"/>"
																rel="<c:url value="${thumbnail.imageUrl}"/>"><img
																src="<c:url value="${thumbnail.imageUrl}"/>"
																alt="<c:url value="${thumbnail.imageName}"/>"></a>
														</c:when>
														<c:otherwise>
															<a href="javascript:;"
																" class="detailsThumbImg thumbImg thumbnail image-link" 
																imgId="im-<c:out value="${thumbnail.id}"/>"
																title="<c:out value="${product.description.name}"/>"
																rel="<c:url value="${thumbnail.externalUrl}"/>"><img
																src="${thumbnail.externalUrl}"
																alt="<c:url value="${product.description.name}"/>" style="border:0 !important;"></a>
														</c:otherwise>
													</c:choose>
												</div>
											</c:if>
										</c:forEach>
									</c:if>
								</div>
								<!-- Nav tabs -->
								<ul class="sinple-tab-menu" role="tablist">
									<c:if
										test="${product.images!=null && fn:length(product.images) gt 1}">
										<c:forEach items="${product.images}" var="thumbnail">
											<c:if test="${thumbnail.imageType==0}">
												<li><a href="#view<c:out value="${thumbnail.id}"/>"
													data-toggle="tab"><img
														src="<c:url value="${thumbnail.imageUrl}"/>"
														alt="<c:url value="${thumbnail.imageName}"/>" /></a></li>
											</c:if>
										</c:forEach>
									</c:if>
								</ul>
							</div>
							<!-- fin col-lg -->
							<div class="col-lg-7 col-md-5 col-sm-5 col-xs-12">
								<div class="product-simple-content">
									<div class="sinple-c-title">
										<h1>${product.description.name}</h1>
									</div>
									<div class="checkbox">
										<span>
											<c:if test="${not product.productVirtual}">

													<!-- availability -->
													<strong><s:message code="label.product.available"
															text="Available" /></strong><br>

											</c:if></span>
									</div>
									<!-- sku-->
									<span> <s:message code="label.product.code"
											text="Product code" /> ${product.sku}
									</span>
									<div class="product-price-star star-2">
                                            <!-- Review -->
                                          <%--  <jsp:include page="/pages/shop/common/catalog/rating.jsp" /> --%>
                                    </div>

                                    <c:if test="${displayVaiantDropdown}">
                                    									    <c:if test="${doesVariantExists}">
                                                                                <div>

                                                                                    <div class="variant-select" id="variantDropdowns">
                                                                                    </div>

                                                                                </div>
                                                                            </c:if>
                                    									</c:if>
									<!-- price -->
									<h4>
										<span itemprop="offerDetails" itemscope
											itemtype="http://data-vocabulary.org/Offer">
											<meta itemprop="seller"
												content="${requestScope.MERCHANT_STORE.storename}" />
											<meta itemprop="currency"
												content="<c:out value="${requestScope.MERCHANT_STORE.currency.code}" />" />
											<span id="productPrice" class="price">
											    <span itemprop="price" id="sellingPrice"><c:out value="${product.finalPrice}" /></span>
											    <span id="variant_price" style="display:none;"></span>
										    </span>
										</span>
									</h4>
									<c:if test="${product.productPrice.description!=null}"><strong><c:out value="${product.productPrice.description.priceAppender}"/></strong></c:if>


									<input id="variant_id" type="hidden" value="" name="variant_id">
									<jsp:include
										page="/pages/shop/common/catalog/addToCartProduct.jsp" />
								</div>
							</div>
						</div>
					</div>
					<!-- product-simple-area-end -->
					<div class="product-info-detailed pb-80 ptb-40-md ptb-20-xs" id="productHeading">
						<div class="row">
							<div class="col-lg-12">
								<div class="product-info-tab">
									<!-- Nav tabs -->
									<ul class="product-info-tab-menu" role="tablist">
										<li class="active"><a href="#details"><i
												class="fa fa-file-text-o pr-5"></i> 
													<s:message
													code="label.productedit.productdesc"
													text="Product description" /></a></li>

									</ul>
									<!-- Tab panes -->
									<div class="tab-content">
										<div class="tab-pane active" id="details">
											<div class="product-info-tab-content">
												<c:out value="${product.description.description}"
													escapeXml="false" />
												<dl class="dl-horizontal" id="productDesc">

                                                    <c:forEach items="${specifications}" var="entry">
                                                        <c:if test="${!(entry.value).equals('-')}">
                                                        <dt>
                                                            ${entry.key}
                                                            :
                                                        </dt>
                                                        <dd>
                                                            ${entry.value}
                                                        </dd>
                                                        </c:if>
                                                    </c:forEach>


												</dl>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- fin product info -->
						</div>
						<!--fin col 9 -->
					</div>
				
				<!-- product-simple-area-end -->
				<%-- <div class="product-info-detailed pb-80 ptb-40-md ptb-20-xs">
                              						<div class="row">
                              							<div class="col-lg-12">
                              								<div class="product-info-tab">
                              									<!-- Nav tabs -->
                              									<ul class="product-info-tab-menu" role="tablist">
                              										<li class="active"><a href="#reviews" data-toggle="tab"><i
                              												class="fa fa-star pr-5"></i> <s:message
                              													code="label.product.customer.reviews"
                              													text="Customer reviews" /></a></li>
                              									</ul>
                              									<!-- Tab panes -->
                              									<div>
                              										<div class="tab-pane" id="reviews">
                              											<div class="customer-review-top">
                              												<h4>
                              													<s:message code="label.product.customer.reviews"
                              														text="Customer reviews" />
                              												</h4>
                              												<!-- reviews -->
                              												<jsp:include page="/pages/shop/common/catalog/reviews.jsp" />
                              											</div>
                              										</div>
                              									</div>
                              								</div>
                              							</div>
                              						</div>
                              						<!--fin col 9 -->
                              					</div> --%>


                              				<!-- customer review -->


				<!-- Related items -->
				<div class="col-lg-3 col-md-3 col-sm-12 col-xs-12">
					<c:if test="${relatedProducts!=null}">
						<div
							class="feature-preduct-area hyperion home-page-2 pb-50 pb-50-md"
							style="padding-top: 40px !important;padding-bottom:10px !important;">
							<div class="row">
								<div class="col-lg-12">
									<div class="hyper-title">
										<h4 class="text-uppercase">
											<s:message code="label.product.related.title"
												text="Related items" />
										</h4>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-lg-12">
									<div>
										<div>
											<!-- Iterate over featuredItems -->
											<c:set var="ITEMS" value="${relatedProducts}" scope="request" />
											<jsp:include
												page="/pages/shop/templates/generic/sections/productBox.jsp" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</c:if>
					<c:if test="${requestScope.CONTENT['sideBar']!=null}">
									<sm:pageContent contentCode="sideBar"/>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</div>
</div>

<script>
		
		$(function () {
			
			$('.popup-img').magnificPopup({type:'image'});
		
		
		    $('.thumbImg').click(function(){
		    	var igId = $(this).attr('imgId');
		        var url = $(this).attr('rel');
		        var name = $(this).attr('title');
		        $("#largeImg").html("<img src='" + url + "' /><a href='" + url + "' data-mfp-src='" + url + "' class='popup-img overlay' title='" + name + "'><i class='fa fa-search-plus'></i></a>");
		        //re bind action
		        $('.popup-img').magnificPopup({type:'image'});
		    })

		    $.ajax({
                        type: 'GET',
                        url: '<c:url value="/shop/product/searchByCode.html"/>?code=${product.sku}',
                        dataType: 'json',
                        success: function(response){
                             var data = response.response.data;

                             var specificationDetails = JSON.parse(data[0].specficationDetails);
                             //console.log(specificationDetails);
                             var variantOptions = "";
                             var i=0;
                             for (const [key, value] of Object.entries(specificationDetails)) {
                               var optionString = "";

                                value.forEach(function (item, index) {
                                  optionString += "<option value='"+item.substring( item.indexOf("__")+2)+"'>"+ item.substring( 0, item.indexOf("__")) +"</option>"
                                });

                                var html = "<div class='control-group'>";
                                html += "<label>"+key+"</label>";
                                html += "<div class='controls'>";
                                html += "<select id='variant_"+i+"' name='variants[]' onchange='getPrice()'>"; //TODO: Give ID
                                html += optionString;
                                html += "</select>";
                                html += "</div>";
                                html += "</div>";

                                variantOptions += html;
                                i++;
                             }

                            $("#variantDropdowns").html(variantOptions);
                            getPrice();
                        },
                          error: function(xhr, textStatus, errorThrown) {
                            alert('error ' + errorThrown);
                        }
                    });

		});

		(function(){
			if($("#productDesc").html().trim() === "") {
				$("#productHeading").hide();
			}
		})();


		function getPrice()
            {
                        $("#avail_id").val("");
                        $("#price_id").val("");
                        $("#sellingPrice").val("");
						$("#variant_id").val("");
                      var variants = "";

                      var selects = document.getElementsByTagName('select');
                      var sel;
                      for(var z=0; z<selects.length; z++){
                           sel = selects[z];
                           if(sel.name.indexOf('variants') === 0){
                               variants+=sel.value+",";
                           }
                      }
                      variants = variants.slice(0, variants.length - 1);

                       $.ajax({
                          type: 'GET',
                          url: '<c:url value="/shop/product/getVariantsPrices.html"/>',
                          dataType: 'json',
                          data: {
                            withSymbol: 'true',
                            variants: variants,
                            code: '${product.sku}'
                          },
                          success: function(response){
                            console.log(response);
                            var data = response.response.data;
                            data = JSON.parse(data[0].prices);
                            console.log(data);
                            if(data.price=="" || data.price == null || data.price == undefined ) {
								$("#sellingPrice").show();
								$("#sellingPrice").html("This variant does not exist");
								var button = '<div class="store-btn-addtocart"><a class="open-askForPrice askForPrice" data-sku="{{sku}}" data-name="{{description.name}}" data-toggle="modal" data-target="#modalContactForm"><s:message code="button.label.askForPrice" text="Ask for price"/></a></div>'
                                $(".options-form").html(button);
							}
							else if(data.price == "Rs.0.00"){
								$("#sellingPrice").hide();
								var button = '<div class="store-btn-addtocart"><a class="open-askForPrice askForPrice" data-sku="{{sku}}" data-name="{{description.name}}" data-toggle="modal" data-target="#modalContactForm"><s:message code="button.label.askForPrice" text="Ask for price"/></a></div>'
								$(".options-form").html(button);
							}
                            else {
								$("#sellingPrice").show();
								$("#sellingPrice").html(data.price);
                                $("#variant_price").html(data.price_id);
								$("#variant_id").val(data.variant_id);
                            }

                          },
                          error: function(xhr, textStatus, errorThrown) {
                            alert('error ' + errorThrown);
                          }
                       });
            }

		</script>

