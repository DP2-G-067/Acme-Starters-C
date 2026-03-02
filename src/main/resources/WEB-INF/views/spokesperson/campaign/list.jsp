<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="spokesperson.campaign.list.label.name" path="name" width="80%"/>	
	<acme:list-column code="spokesperson.campaign.list.label.description" path="description" width="20%"/>
	<acme:list-column code="spokesperson.campaign.list.label.startMoment" path="startMoment"/> 
	<acme:list-column code="spokesperson.campaign.list.label.endMoment" path="endMoment"/> 
	<acme:list-column code="spokesperson.campaign.list.label.draftMode" path="draftMode"/> 
	<acme:list-hidden path="moreInfo"/>	
</acme:list>

<jstl:if test="${showCreate}">
	<acme:button code="spokesperon.campaign.button.create" action="/spokesperson/campaign/create?campaignId=${campaignId}"/>
</jstl:if>