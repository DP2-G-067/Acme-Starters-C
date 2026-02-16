<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textarea code="authenticated.inventor.form.label.bio" path="bio"/>
	<acme:form-textarea code="authenticated.inventor.form.label.keyWords" path="keyWords"/>
	<acme:form-checkbox code="authenticated.inventor.form.label.licensed" path="licensed"/>

	<jstl:choose>
		<jstl:when test="${command == 'create'}">
			<acme:submit code="authenticated.inventor.form.button.create" action="/authenticated/inventor/create"/>
		</jstl:when>
		<jstl:when test="${command == 'update'}">
			<acme:submit code="authenticated.inventor.form.button.update" action="/authenticated/inventor/update"/>
		</jstl:when>
	</jstl:choose>

</acme:form>
