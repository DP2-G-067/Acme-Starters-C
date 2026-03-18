<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.sponsorship.form.label.ticker" path="ticker" readonly="true"/>
	<acme:form-textbox code="any.sponsorship.form.label.name" path="name" readonly="true"/>
	<acme:form-moment code="any.sponsorship.form.label.startMoment" path="startMoment" readonly="true"/>
	<acme:form-moment code="any.sponsorship.form.label.endMoment" path="endMoment" readonly="true"/>
	<acme:form-textarea code="any.sponsorship.form.label.description" path="description" readonly="true"/>
	<acme:form-url code="any.sponsorship.form.label.moreInfo" path="moreInfo" readonly="true"/>
	<acme:form-textbox code="any.sponsorship.form.label.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-money code="any.sponsorship.form.label.totalMoney" path="totalMoney" readonly="true"/>

	<jstl:if test="${_command == 'show'}">
		<acme:button code="any.sponsorship.form.button.sponsor" action="/any/sponsor/show?sponsorshipId=${id}"/>
		<acme:button code="any.sponsorship.form.button.donation" action="/any/donation/list?sponsorshipId=${id}"/>
	</jstl:if>
</acme:form>
