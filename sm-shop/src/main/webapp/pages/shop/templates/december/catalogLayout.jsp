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
<%@ taglib uri="/WEB-INF/shopizer-tags.tld" prefix="sm"%>
 
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
  
 <%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
 <c:set var="lang" scope="request" value="${requestScope.locale.language}"/> 
 
 
 <html xmlns="http://www.w3.org/1999/xhtml"> 
 
 
     <head>
        	 	<meta charset="utf-8">
        	 	<meta http-equiv="x-ua-compatible" content="ie=edge">
    			<title><c:out value="${requestScope.PAGE_INFORMATION.pageTitle}" /></title>
    			<meta name="viewport" content="width=device-width, initial-scale=1.0">
    			<meta name="description" content="<c:out value="${requestScope.PAGE_INFORMATION.pageDescription}" />">
    			<meta name="author" content="<c:out value="${requestScope.MERCHANT_STORE.storename}"/>">

				<!-- include all header js and css -->
                <jsp:include page="/pages/shop/templates/december/sections/shopLinks.jsp" />

                <style>
                    .backroundhover:hover {
                        border: 1px solid yellow;
                        box-shadow: 0 0 11px rgba(33,33,33,.2);
                    }
                </style>
                
                
    <script type="text/html" id="productBoxTemplate">
			{{#products}}
                        <div itemscope itemtype="http://schema.org/Enumeration" style="height:350px; margin-bottom:1px;" class="col-md-COLUMN-SIZE col-sm-6 col-xs-12 product backroundhover" item-order="{{sortOrder}}" item-name="{{description.name}}" item-price="{{price}}" data-id="{{id}}">

								<div class="thumbnail product-img" style="border:none; margin-bottom:0px; height:220px !important;">
                                    {{#image}}
									<a href="<c:url value="/shop/product/" />{{description.friendlyUrl}}.html/ref=<c:out value="${requestScope.ref}"/>">
										<img src="<c:url value=""/>{{image.imageUrl}}" alt=""  height="65%" width="65%"/>
									</a>
									{{/image}}
								</div>
								<div class="product-content text-center">
									<a class="listing-product-name" href="<c:url value="/shop/product/" />{{description.friendlyUrl}}.html/ref=<c:out value="${requestScope.ref}"/>"><h3 itemprop="name" style="line-height:1.3; padding:0px; margin:0px; height:60px; overflow:hidden;">{{description.name}}</h3></a>
									<!-- commented <div class="stars" id="productRating_{{id}}"></div> -->

                                    <h4 style="padding:0px; margin:0px;">
										{{#discounted}}<del>{{originalPrice}}</del>&nbsp;<span itemprop="price" class="specialPrice">{{finalPrice}}</span>{{/discounted}}
										{{^discounted}}<span itemprop="price">{{#price}}{{finalPrice}}{{/price}}{{^price}}Ask for price{{/price}}</span>{{/discounted}}
								    </h4>
									<c:if test="${requestScope.CONFIGS['allowPurchaseItems'] == true}">
									<div class="store-btn">
      									<div class="store-btn-addtocart"><a class="addToCart" href="javascript:void(0)" productId="{{id}}"><s:message code="button.label.addToCart" text="Add to cart"/></a></div>
   									</div>
									</c:if>
								</div>
						</div>
			{{/products}}
    </script>

	<c:if test="${requestScope.PAGE_INFORMATION.pageImageUrl!=null}">
		<!-- open graph metatag -->
		 <meta property="og:site_name" content="${requestScope.MERCHANT_STORE.storename}"/>
		 <meta property="og:type" content="article"/>
		 <meta property="og:title" content="${requestScope.PAGE_INFORMATION.pageTitle}"/>
		 <meta property="og:url" content="${requestScope.PAGE_INFORMATION.pageUrl}"/>
		 <meta property="og:description" content="${requestScope.PAGE_INFORMATION.pageDescription}"/>
		 <meta property="og:image" content="${requestScope.PAGE_INFORMATION.pageImageUrl}"/>

		 <!-- twitter vcard metatags -->
		 <meta name="twitter:card" content="summary_large_image"/>
		 <meta name="twitter:site" content="${requestScope.MERCHANT_STORE.storename}"/>
		 <meta name="twitter:title" content="${requestScope.PAGE_INFORMATION.pageTitle}"/>
		 <meta name="twitter:url" content="${requestScope.PAGE_INFORMATION.pageUrl}"/>
		 <meta name="twitter:description" content="${requestScope.PAGE_INFORMATION.pageDescription}"/>
		 <meta name="twitter:image" content="${requestScope.PAGE_INFORMATION.pageImageUrl}"/>
	</c:if>
	 </head>
 
 	<body>
 	
 	     <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->

		<tiles:insertAttribute name="header" ignore="true"/>
	
		<tiles:insertAttribute name="body" ignore="true"/>
	
		<tiles:insertAttribute name="footer" ignore="true"/>

	<jsp:include page="/pages/shop/templates/december/sections/jsLinks.jsp" />
     
    <!-- Cookie policy --> 
    <!-- https://www.osano.com/cookieconsent/download/ -->
	<script src="https://cdn.jsdelivr.net/npm/cookieconsent@3/build/cookieconsent.min.js" data-cfasync="false"></script>
			<script>
			window.cookieconsent.initialise({
			  "palette": {
			    "popup": {
			      "background": "#eaf7f7",
			      "text": "#5c7291"
			    },
			    "button": {
		            "background": "#555555",
		            "text": "#ffffff"
			    }
			  },
			  "position": "top",
			  "content": {
				    "message": "<c:out value="${message.cookie.policy}" default="This website uses cookies to ensure you get the best experience on our website."/>",
				    "href": "/shop/pages/terms-and-policy.html"
			   }
			});
	 </script>
	

	    <c:if test="${requestScope.CONTENT['beforeCloseBody']!=null}">
			<sm:pageContent contentCode="beforeCloseBody"/>
		</c:if>
 	</body>
 
 </html>
 
