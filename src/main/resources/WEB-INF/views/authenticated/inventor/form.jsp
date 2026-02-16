<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textarea code="authenticated.inventor.form.label.bio" path="bio"/>
	<acme:form-textarea code="authenticated.inventor.form.label.keyWords" path="keyWords"/>
	<acme:form-checkbox code="authenticated.inventor.form.label.licensed" path="licensed"/>
</acme:form>
