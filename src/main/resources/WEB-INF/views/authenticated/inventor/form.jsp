<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textarea code="authenticated.inventor.form.label.bio" path="bio" readonly="false"/>
	<acme:form-textarea code="authenticated.inventor.form.label.keyWords" path="keyWords" readonly="false"/>
	<acme:form-checkbox code="authenticated.inventor.form.label.licensed" path="licensed" readonly="false"/>

	
	<acme:submit code="authenticated.inventor.form.button.create" action="/authenticated/inventor/create"/>
	<acme:submit code="authenticated.inventor.form.button.update" action="/authenticated/inventor/update"/>
</acme:form>
