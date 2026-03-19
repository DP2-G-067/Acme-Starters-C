<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<acme:form>
    <acme:form-textbox code="any.audit-report.form.label.ticker" path="ticker" readonly="true"/>
    <acme:form-textbox code="any.audit-report.form.label.name" path="name" readonly="true"/>
    <acme:form-moment code="any.audit-report.form.label.startMoment" path="startMoment" readonly="true"/>
    <acme:form-moment code="any.audit-report.form.label.endMoment" path="endMoment" readonly="true"/>
    <acme:form-textarea code="any.audit-report.form.label.description" path="description" readonly="true"/>
    <acme:form-url code="any.audit-report.form.label.moreInfo" path="moreInfo" readonly="true"/>
    <acme:form-double code="any.audit-report.form.label.hours" path="hours" readonly="true"/>

    <acme:button code="any.audit-report.form.button.auditor" action="/any/auditor/show?id=${auditorId}"/>
    <acme:button code="any.audit-report.form.button.audit-section" action="/any/audit-section/list?auditReportId=${id}"/>
</acme:form>