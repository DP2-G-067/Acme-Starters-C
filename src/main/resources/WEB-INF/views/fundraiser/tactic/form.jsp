<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="fundraiser.tactic.form.label.name" path="name" readonly="${!draftMode}"/>
	<acme:form-textbox code="fundraiser.tactic.form.label.notes" path="notes" readonly="${!draftMode}"/>
	<acme:form-double code="fundraiser.tactic.form.label.expectedPercentage" path="expectedPercentage" readonly="${!draftMode}"/>
	<acme:form-select code="fundraiser.tactic.form.label.kind" path="kind" choices="${choices}" readonly="${!draftMode}"/>	

	<jstl:if test="${_command != 'create' && draftMode}">
		<acme:submit code="fundraiser.tactic.form.button.update" action="/fundraiser/tactic/update"/>
		<acme:submit code="fundraiser.tactic.form.button.delete" action="/fundraiser/tactic/delete"/>
	</jstl:if>

	<jstl:if test="${_command == 'create'}">
		<acme:submit code="fundraiser.tactic.form.button.create" action="/fundraiser/tactic/create"/>
	</jstl:if>
</acme:form>