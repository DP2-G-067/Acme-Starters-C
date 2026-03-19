<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
    <acme:list-column code="auditor.audit-report.list.label.ticker" path="ticker"/>
    <acme:list-column code="auditor.audit-report.list.label.name" path="name"/>
    <acme:list-column code="auditor.audit-report.list.label.description" path="description"/>
    <acme:list-column code="auditor.audit-report.list.label.startMoment" path="startMoment"/>
    <acme:list-column code="auditor.audit-report.list.label.endMoment" path="endMoment"/>
</acme:list>

<acme:button code="auditor.audit-report.list.button.create" action="/auditor/audit-report/create"/>