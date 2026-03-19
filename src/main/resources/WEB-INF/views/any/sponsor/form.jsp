<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.sponsor.form.label.address" path="address" readonly="true"/>
	<acme:form-textbox code="any.sponsor.form.label.im" path="im" readonly="true"/>
	<acme:form-checkbox code="any.sponsor.form.label.gold" path="gold" readonly="true"/>
</acme:form>
