<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<acme:form>
    <acme:form-textbox code="any.audit-section.form.label.name" path="name" readonly="true"/>
    <acme:form-textarea code="any.audit-section.form.label.notes" path="notes" readonly="true"/>
    <acme:form-double code="any.audit-section.form.label.hours" path="hours" readonly="true"/>
    <acme:form-textbox code="any.audit-section.form.label.kind" path="kind" readonly="true"/>
</acme:form>