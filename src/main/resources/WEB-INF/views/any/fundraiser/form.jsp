<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.strategy.form.label.bank" path="bank" readonly = "true"/>
	<acme:form-textbox code="any.strategy.form.label.statement" path="statement" readonly = "true"/>
	<acme:form-checkbox code="any.strategy.form.label.agent" path="agent" readonly = "true"/>
</acme:form>