<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.campaign.form.label.name" path="name" readonly="true"/>	
	<acme:form-textarea code="any.campaign.form.label.description" path="description" readonly="true"/>
	<acme:form-moment code="any.campaign.form.label.startMoment" path="startMoment" readonly="true"/> 
	<acme:form-moment code="any.campaign.form.label.endMoment" path="endMoment" readonly="true"/> 
	<acme:form-url code="any.campaign.form.label.moreInfo" path="moreInfo" readonly="true"/>
 	
	<jstl:choose>
	    <jstl:when test="${_command == 'show'}">
			<acme:form-double code="any.campaign.form.label.monthsActive" path="monthsActive" readonly="true"/>
			<acme:form-double code="any.campaign.form.label.effort" path="effort" readonly="true"/>
			<acme:button code="any.campaign.button.milestones" action="/any/milestone/list?campaignId=${id}"/>
			<acme:button code="any.campaign.button.spokesperson" action="/any/spokesperson/show?campaignId=${id}"/>    
		</jstl:when> 		
	</jstl:choose>		
</acme:form>