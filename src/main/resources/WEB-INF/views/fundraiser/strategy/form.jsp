<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="fundraiser.strategy.form.label.ticker" path="ticker" readonly="${!draftMode}"/>
	<acme:form-textbox code="fundraiser.strategy.form.label.name" path="name" readonly="${!draftMode}"/>
	<acme:form-moment code="fundraiser.strategy.form.label.startMoment" path="startMoment" readonly="${!draftMode}"/>
	<acme:form-moment code="fundraiser.strategy.form.label.endMoment" path="endMoment" readonly="${!draftMode}"/>
	<acme:form-textbox code="fundraiser.strategy.form.label.description" path="description" readonly="${!draftMode}"/>
	<acme:form-url code="fundraiser.strategy.form.label.moreInfo" path="moreInfo" readonly="${!draftMode}"/>
	
	<jstl:if test="${_command != 'create' && draftMode}">
		<acme:form-textbox code="fundraiser.strategy.form.label.monthsActive" path="monthsActive" readonly="true"/>
		<acme:form-url code="fundraiser.strategy.form.label.expectedPercentage" path="expectedPercentage" readonly="true"/>
		<acme:submit code="fundraiser.strategy.form.button.update" action="/fundraiser/strategy/update"/>
		<acme:submit code="fundraiser.strategy.form.button.publish" action="/fundraiser/strategy/publish"/>
		<acme:submit code="fundraiser.strategy.form.button.delete" action="/fundraiser/strategy/delete"/>
	</jstl:if>
	
	<jstl:if test="${_command != 'create'}">
		<acme:button code="fundraiser.strategy.form.button.tactic" action="/fundraiser/tactic/list?strategyId=${id}"/>
	</jstl:if>

	<jstl:if test="${_command == 'create'}">
		<acme:submit code="fundraiser.strategy.form.button.create" action="/fundraiser/strategy/create"/>
	</jstl:if>
</acme:form>