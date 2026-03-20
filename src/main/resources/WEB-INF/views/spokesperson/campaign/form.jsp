<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="spokesperson.campaign.form.label.ticker" path="ticker" readonly="${!draftMode}"/>
	<acme:form-textbox code="spokesperson.campaign.form.label.name" path="name" readonly="${!draftMode}"/>	
	<acme:form-textarea code="spokesperson.campaign.form.label.description" path="description" readonly="${!draftMode}"/>
	<acme:form-moment code="spokesperson.campaign.form.label.startMoment" path="startMoment" readonly="${!draftMode}"/> 
	<acme:form-moment code="spokesperson.campaign.form.label.endMoment" path="endMoment" readonly="${!draftMode}"/> 
	<acme:form-url code="spokesperson.campaign.form.label.moreInfo" path="moreInfo" readonly="${!draftMode}"/>	
	
	<jstl:choose>
	    <jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:form-double code="spokesperson.campaign.form.label.monthsActive" path="monthsActive" readonly="true"/>
			<acme:form-double code="spokesperson.campaign.form.label.effort" path="effort" readonly="true"/>
			<acme:button code="spokesperson.campaign.button.milestones" action="/spokesperson/milestone/list?campaignId=${id}"/>
		</jstl:when> 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:form-double code="spokesperson.campaign.form.label.monthsActive" path="monthsActive" readonly="true"/>
			<acme:submit code="spokesperson.campaign.form.button.update" action="/spokesperson/campaign/update"/>
			<acme:submit code="spokesperson.campaign.form.button.publish" action="/spokesperson/campaign/publish"/>
			<acme:submit code="spokesperson.campaign.form.button.delete" action="/spokesperson/campaign/delete"/>
			<acme:button code="spokesperson.campaign.button.milestones" action="/spokesperson/milestone/list?campaignId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="spokesperson.campaign.button.create" action="/spokesperson/campaign/create"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>