<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<jstl:if test="${!draftMode}">
		<acme:form-textbox code="fundraiser.strategy.form.label.ticker" path="ticker" readonly="true"/>
		<acme:form-textbox code="fundraiser.strategy.form.label.name" path="name" readonly="true"/>
		<acme:form-moment code="fundraiser.strategy.form.label.startMoment" path="startMoment" readonly="true"/>
		<acme:form-moment code="fundraiser.strategy.form.label.endMoment" path="endMoment" readonly="true"/>
		<acme:form-textbox code="fundraiser.strategy.form.label.description" path="description" readonly="true"/>
		<acme:form-url code="fundraiser.strategy.form.label.moreInfo" path="moreInfo" readonly="true"/>
	</jstl:if>	
	<jstl:if test="${draftMode || command == 'create'}">
		<acme:form-textbox code="fundraiser.strategy.form.label.ticker" path="ticker" />
		<acme:form-textbox code="fundraiser.strategy.form.label.name" path="name" />
		<acme:form-moment code="fundraiser.strategy.form.label.startMoment" path="startMoment" />
		<acme:form-moment code="fundraiser.strategy.form.label.endMoment" path="endMoment" />
		<acme:form-textbox code="fundraiser.strategy.form.label.description" path="description" />
		<acme:form-url code="fundraiser.strategy.form.label.moreInfo" path="moreInfo" />
	</jstl:if>
	

	<jstl:if test="${_command != 'create'}">
		<acme:form-textbox code="fundraiser.strategy.form.label.monthsActive" path="monthsActive" readonly="true"/>
		<acme:form-url code="fundraiser.strategy.form.label.expectedPercentage" path="expectedPercentage" readonly="true"/>
		<acme:submit code="fundraiser.strategy.form.button.tactic" action="/fundraiser/tactic/list?strategyId=${id}"/>		
	</jstl:if>
	
	<jstl:if test="${draftMode}">
		<acme:submit code="fundraiser.strategy.form.button.update" action="/fundraiser/strategy/update"/>
		<acme:submit code="fundraiser.strategy.form.button.publish" action="/fundraiser/strategy/publish"/>
		<acme:submit code="fundraiser.strategy.form.button.delete" action="/fundraiser/strategy/delete"/>
	</jstl:if>
</acme:form>