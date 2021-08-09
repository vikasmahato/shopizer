<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/WEB-INF/shopizer-tags.tld" prefix="sm" %>			


<%@ page session="false" %>			

<link href="<c:url value="/resources/css/bootstrap/css/datepicker.css" />" rel="stylesheet"></link>
<script src="<c:url value="/resources/js/bootstrap/bootstrap-datepicker.js" />"></script>
<script src="<c:url value="/resources/js/ckeditor/ckeditor.js" />"></script>
<script src="<c:url value="/resources/js/jquery.formatCurrency-1.4.0.js" />"></script>
<script src="<c:url value="/resources/js/jquery.alphanumeric.pack.js" />"></script>
<script src="<c:url value="/resources/js/adminFunctions.js" />"></script>

<script type="text/javascript">

	
	$(function(){		
		$('#order').numeric();
		
		if($("#code").val()=="") {
			$('.btn').addClass('disabled');
		}

		<c:forEach items="${brand.descriptions}" var="description" varStatus="counter">
			$("#name${counter.index}").friendurl({id : 'url${counter.index}'});
		</c:forEach>
	});

	
	function removeImage(imageId){
			$("#store.error").show();
			$.ajax({
			  type: 'POST',
			  url: '<c:url value="/admin/brands/brand/removeImage.html"/>',
			  data: 'imageId=' + imageId,
			  dataType: 'json',
			  success: function(response){
		
					var status = isc.XMLTools.selectObjects(response, "/response/status");
					if(status==0 || status ==9999) {
						
						//remove delete
						$("#imageControlRemove").html('');
						//add field
						$("#imageControl").html('<input class=\"input-file\" id=\"image\" name=\"image\" type=\"file\">');
						$(".alert-success").show();
						
					} else {
						
						//display message
						$(".alert-error").show();
					}
		
			  
			  },
			  error: function(xhr, textStatus, errorThrown) {
			  	alert('error ' + errorThrown);
			  }
			  
			});
	}
	
	
	
	function validateCode() {
		$('#checkCodeStatus').html('<img src="<c:url value="/resources/img/ajax-loader.gif" />');
		$('#checkCodeStatus').show();
		var code = $("#code").val();
		var id = $("#id").val();
		checkCode(code,id,'<c:url value="/admin/brand/checkCode.html" />');
	}
	
	function callBackCheckCode(msg,code) {
		console.log(code);
		if(code==0) {
			$('.btn').removeClass('disabled');
		}
		if(code==9999) {

			$('#checkCodeStatus').html('<font color="green"><s:message code="message.code.available" text="This code is available"/></font>');
			$('#checkCodeStatus').show();
			$('.btn').removeClass('disabled');
		}
		if(code==9998) {

			$('#checkCodeStatus').html('<font color="red"><s:message code="message.code.exist" text="This code already exist"/></font>');
			$('#checkCodeStatus').show();
			$('.btn').addClass('disabled');
		}
		
	}
	
	
</script>

<div class="tabbable">

		 <jsp:include page="/common/adminTabs.jsp" />
  					
		 <div class="tab-content">

			<div class="tab-pane active" id="catalogue-section">
				
				<h3>
					<c:choose>
						<c:when test="${brand.brand.id!=null && brand.brand.id>0}">
							<s:message code="label.brand.edit" text="Edit brand" /> <c:out value="${product.product.sku}"/>
						</c:when>
						<c:otherwise>
							<s:message code="label.brand.create" text="Create a brand" />
						</c:otherwise>
					</c:choose>
				</h3>
				<br/><br/>
								
				<c:url var="brandSave" value="/admin/catalogue/brand/save.html"/>
 
				<form:form method="POST" modelAttribute="brand" action="${brandSave}">

      				<form:hidden path="brand.id" />
      				

                    <form:errors path="*" cssClass="alert alert-error" element="div" />
                    <div id="store.success" class="alert alert-success" style="<c:choose><c:when test="${success!=null}">display:block;</c:when><c:otherwise>display:none;</c:otherwise></c:choose>"><s:message code="message.success" text="Request successfull"/></div>   
                    <div id="store.error" class="alert alert-error" style="display:none;"><s:message code="message.error" text="An error occured"/></div>
	
		            <div class="control-group">
                            <label><s:message code="label.entity.code" text="Code"/></label>
	                        <div class="controls">
	                        		<form:input cssClass="input-large highlight" path="code" onblur="validateCode()"/>
	                                <span class="help-inline"><div id="checkCodeStatus" style="display:none;"></div><form:errors path="code" cssClass="error" /></span>
	                        </div>
                 	</div>
					
					<c:forEach items="${brand.descriptions}" var="description" varStatus="counter">
					           
						<div class="control-group">
	                        <label class="required"><s:message code="label.brandedit.brandname" text="brand Name"/> (<c:out value="${description.language.code}"/>)</label>
	                        <div class="controls">
	                                  <form:input cssClass="input-large highlight" id="name${counter.index}" path="descriptions[${counter.index}].name"/>
	                                  <span  class="help-inline"><form:errors path="descriptions[${counter.index}].name" cssClass="error" /></span>
	                        </div>
	                  	</div> 

						<div class="control-group">
	                        <label><s:message code="label.brandedit.brandtitle" text="brand Title"/> (<c:out value="${description.language.code}"/>)</label>
	                        <div class="controls">
	                                  <form:input cssClass="input-large" id="title${counter.index}" path="descriptions[${counter.index}].title"/>
	                                  <span  class="help-inline"><form:errors path="descriptions[${counter.index}].title" cssClass="error" /></span>
	                        </div>
	                  	</div> 

 	                  	<div class="control-group">
	                        <label><s:message code="label.brandedit.brandurl" text="URL"/> (<c:out value="${description.language.code}"/>)</label>
	                        <div class="controls">
	                                  <form:input cssClass="input-large" id="url${counter.index}" path="descriptions[${counter.index}].url"/>
	                                  <span  class="help-inline"><form:errors path="descriptions[${counter.index}].url" cssClass="error" /></span>
	                        </div>
	                  	</div>	

	                    <div class="control-group">
	                            <label class="required"><s:message code="label.brandedit.branddescription" text="brand Description"/> (<c:out value="${description.language.code}"/>)</label>
	                            <div class="controls">
	                     	 
	                        			 <textarea cols="30" id="descriptions${counter.index}.description" name="descriptions[${counter.index}].description">
	                        				<c:out value="${brand.descriptions[counter.index].description}"/>
	                        			 </textarea>
	                            </div>	                  	
	                            <script type="text/javascript">
						//<![CDATA[

									CKEDITOR.replace('descriptions[${counter.index}].description',
									{
										skin : 'office2003',
										toolbar : 
										[
											['Source','-','Save','NewPage','Preview'], 
											['Cut','Copy','Paste','PasteText','-','Print'], 
											['Undo','Redo','-','Find','-','SelectAll','RemoveFormat'], '/', 
											['Bold','Italic','Underline','Strike','-','Subscript','Superscript'], 
											['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'], 
											['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'], 
											['Link','Unlink','Anchor'], 
											['Image','Flash','Table','HorizontalRule','SpecialChar','PageBreak'], '/', 
											['Styles','Format','Font','FontSize'], ['TextColor','BGColor'], 
											['Maximize', 'ShowBlocks'] 
										],
										
										filebrowserWindowWidth : '720',
		        						filebrowserWindowHeight : '740',
										filebrowserImageBrowseUrl :    '<c:url value="/admin/content/fileBrowser.html"/>'
										
		
									});

						//]]>
								</script>  
	                              
						</div>
						 <form:hidden path="descriptions[${counter.index}].language.id" />
                         <form:hidden path="descriptions[${counter.index}].language.code" />
						 <form:hidden path="descriptions[${counter.index}].id" />
	                  	                 		                  		                  	 
                	</c:forEach>
    <%--                              
                 <div class="control-group">
                        <label><s:message code="label.brand.image" text="Image"/>&nbsp;<c:if test="${brand.productImage.productImage!=null && brand.productImage.productImage!=''}"><span id="imageControlRemove"> - <a href="#" onClick="removeImage('${brand.productImage.id}')"><s:message code="label.generic.remove" text="Remove"/></a></span></c:if></label>
                        <div class="controls" id="imageControl">
                        		<c:choose>
	                        		<c:when test="${brand.productImage.productImage==null || brand.productImage.productImage==''}">
	                                    <input class="input-file" id="image" name="image" type="file">
	                                </c:when>
	                                <c:otherwise>
	                                	<img src="<sm:brandImage imageName="${brand.productImage.productImage}" brand="${brand.brand}"/>" width="200"/>
	                                </c:otherwise>
                                </c:choose>
                        </div>
                  </div>
  --%>                                                 	      
                 	<div class="control-group">
                        <label><s:message code="label.brandedit.brandorder" text="Order"/></label>
                        <div class="controls">
                                  <form:input id="order" cssClass="input-large" path="order"/>
                                  <span  class="help-inline"><form:errors path="order" cssClass="error" /></span>
                        </div>
                  	</div> 	        
            	 
            	    <div class="form-actions">
                            <div class="pull-right">
                                    <button type="submit" class="btn btn-success"><s:message code="button.label.submit2" text="Submit"/></button>
                            </div>
                    </div>
                   
            	</form:form>
          
            </div>
         </div>
                    