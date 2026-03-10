<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.spokesperson.form.label.cv" path="cv" readonly = "true"/>
	<acme:form-textbox code="any.spokesperson.form.label.achievements" path="achievements" readonly = "true"/>
	<acme:form-checkbox code="any.spokesperson.form.label.licensed" path="licensed" readonly = "true"/>
</acme:form>