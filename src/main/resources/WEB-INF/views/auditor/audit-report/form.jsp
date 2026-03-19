<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<acme:form>
    <acme:form-textbox code="auditor.audit-report.form.label.ticker" path="ticker" readonly="${!draftMode}"/>
    <acme:form-textbox code="auditor.audit-report.form.label.name" path="name" readonly="${!draftMode}"/>
    <acme:form-moment code="auditor.audit-report.form.label.startMoment" path="startMoment" readonly="${!draftMode}"/>
    <acme:form-moment code="auditor.audit-report.form.label.endMoment" path="endMoment" readonly="${!draftMode}"/>
    <acme:form-textarea code="auditor.audit-report.form.label.description" path="description" readonly="${!draftMode}"/>
    <acme:form-url code="auditor.audit-report.form.label.moreInfo" path="moreInfo" readonly="${!draftMode}"/>

    <jstl:choose>
        <jstl:when test="${_command == 'show' && draftMode == false}">
            <acme:button code="auditor.audit-report.form.button.audit-section" action="/auditor/audit-section/list?auditReportId=${id}"/>
        </jstl:when>
        <jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
            <acme:button code="auditor.audit-report.form.button.audit-section" action="/auditor/audit-section/list?auditReportId=${id}"/>
            <acme:submit code="auditor.audit-report.form.button.update" action="/auditor/audit-report/update"/>
            <acme:submit code="auditor.audit-report.form.button.delete" action="/auditor/audit-report/delete"/>
            <acme:submit code="auditor.audit-report.form.button.publish" action="/auditor/audit-report/publish"/>
        </jstl:when>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="auditor.audit-report.form.button.create" action="/auditor/audit-report/create"/>
        </jstl:when>
    </jstl:choose>
</acme:form>