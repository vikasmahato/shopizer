<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>



				{title:"<s:message code="label.entity.id" text="Id"/>", name:"specificationId", canFilter:false},
        		{title:"<s:message code="label.category.specification" text="Specification"/>", name:"specification", canFilter:false},
        		{title:"<s:message code="label.category.filter" text="Filter"/>", name:"filter", type:"boolean", canFilter:false},
        		{title:"<s:message code="label.category.variant" text="Variant"/>", name:"variant", type:"boolean", canFilter:false},
        		{title:"<s:message code="label.entity.details" text="Details"/>", name: "buttonField", align: "center",canFilter:false,canSort:false, canReorder:false}