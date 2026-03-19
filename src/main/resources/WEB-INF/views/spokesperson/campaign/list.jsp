<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="spokesperson.campaign.list.label.name" path="name"/>	
	<acme:list-column code="spokesperson.campaign.list.label.startMoment" path="startMoment"/> 
	<acme:list-column code="spokesperson.campaign.list.label.endMoment" path="endMoment"/> 
	<acme:list-hidden path="description"/>	
	<acme:list-hidden path="moreInfo"/>	
</acme:list>

	<acme:button code="spokesperon.campaign.button.create" action="/spokesperson/campaign/create?campaignId=${campaignId}"/>