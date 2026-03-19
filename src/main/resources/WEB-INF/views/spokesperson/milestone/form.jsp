<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="spokesperson.milestone.form.label.title" path="title" readonly="${!draftMode}"/>
	<acme:form-textarea code="spokesperson.milestone.form.label.achievements" path="achievements" readonly="${!draftMode}"/>	
	<acme:form-double code="spokesperson.milestone.form.label.effort" path="effort" readonly="${!draftMode}"/>
	
	<jstl:if test="${draftMode}">
		<acme:form-select code="spokesperson.milestone.form.label.kind" path="kind" choices="${choices}"/>	
	</jstl:if>
	
	<jstl:if test="${!draftMode}">
		<acme:form-textbox code="spokesperson.milestone.form.label.kind" path="kind" readonly="true"/>	
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="spokesperson.milestone.form.button.update" action="/spokesperson/milestone/update"/>
			<acme:submit code="spokesperson.milestone.form.button.delete" action="/spokesperson/milestone/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="spokesperson.milestone.button.create" action="/spokesperson/milestone/create?campaignId=${campaignId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>