<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="spokesperson.tactic.list.label.title" path="name"/>	
	<acme:list-column code="spokesperson.tactic.list.label.effort" path="effor"/> 
	<acme:list-column code="spokesperson.tactic.list.label.kind" path="kind"/> 
	<acme:list-hidden path="achievements"/>	
</acme:list>

<jstl:if test="${draftMode}">
	<acme:button code="spokesperon.milestone.button.create" action="/spokesperson/milestone/create?campaignId=${campaignId}"/>
</jstl:if>