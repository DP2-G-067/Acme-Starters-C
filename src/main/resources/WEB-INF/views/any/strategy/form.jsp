<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.strategy.form.label.ticker" path="ticker" readonly="true"/>
	<acme:form-textbox code="any.strategy.form.label.name" path="name" readonly="true"/>
	<acme:form-moment code="any.strategy.form.label.startMoment" path="startMoment" readonly="true"/>
	<acme:form-moment code="any.strategy.form.label.endMoment" path="endMoment" readonly="true"/>
	<acme:form-textbox code="any.strategy.form.label.description" path="description" readonly="true"/>
	<acme:form-url code="any.strategy.form.label.moreInfo" path="moreInfo" readonly="true"/>
	<acme:form-url code="any.strategy.form.label.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-url code="any.strategy.form.label.expectedPercentage" path="expectedPercentage" readonly="true"/>

	<jstl:if test="${_command == 'show'}">
		<acme:submit code="any.strategy.form.button.fundraiser" action="/any/fundraiser/show?strategyId=${id}"/>
		<acme:submit code="any.strategy.form.button.tactic" action="/any/tactic/list?strategyId=${id}"/>
	</jstl:if>
</acme:form>