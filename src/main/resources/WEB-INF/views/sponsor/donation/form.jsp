<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="sponsor.donation.form.label.name" path="name" readonly="${!draftMode && id != 0}"/>
	<acme:form-textbox code="sponsor.donation.form.label.notes" path="notes" readonly="${!draftMode && id != 0}"/>
	<acme:form-money code="sponsor.donation.form.label.money" path="money" readonly="${!draftMode && id != 0}"/>

	<jstl:if test="${!draftMode && id != 0}">
		<acme:form-textbox code="sponsor.donation.form.label.kind" path="kind" readonly="true"/>
	</jstl:if>
	<jstl:if test="${!(!draftMode && id != 0)}">
		<acme:form-select code="sponsor.donation.form.label.kind" path="kind" choices="${choices}"/>
	</jstl:if>

	<jstl:if test="${_command != 'create' && draftMode}">
		<acme:submit code="sponsor.donation.form.button.update" action="/sponsor/donation/update"/>
		<acme:submit code="sponsor.donation.form.button.delete" action="/sponsor/donation/delete"/>
	</jstl:if>

	<jstl:if test="${_command == 'create'}">
		<acme:submit code="sponsor.donation.form.button.create" action="/sponsor/donation/create?sponsorshipId=${sponsorshipId}"/>
	</jstl:if>
</acme:form>
