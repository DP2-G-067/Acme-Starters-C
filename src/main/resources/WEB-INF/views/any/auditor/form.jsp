<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<acme:form>
    <acme:form-textbox code="any.auditor.form.label.firm" path="firm" readonly="true"/>
    <acme:form-textarea code="any.auditor.form.label.highlights" path="highlights" readonly="true"/>
    <acme:form-checkbox code="any.auditor.form.label.solicitor" path="solicitor" readonly="true"/>
</acme:form>