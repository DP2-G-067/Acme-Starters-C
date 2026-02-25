<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox  code="inventor.part.form.label.name" path="name"/>
	<acme:form-textarea code="inventor.part.form.label.description" path="description"/>

	<acme:form-money    code="inventor.part.form.label.cost" path="cost"/>

	<acme:form-select code="inventor.part.form.label.kind" path="kind" choices="${kinds}"/>
	

	<!-- CREATE -->
	<jstl:if test="${_command == 'create'}">
		<acme:submit code="inventor.part.form.button.create"
			action="/inventor/part/create?inventionId=${inventionId}"/>
	</jstl:if>

	<!-- UPDATE -->
	<jstl:if test="${_command == 'update'}">
		<acme:submit code="inventor.part.form.button.update"
			action="/inventor/part/update"/>
	</jstl:if>

	<!-- DELETE -->
	<jstl:if test="${_command == 'delete'}">
		<acme:submit code="inventor.part.form.button.delete"
			action="/inventor/part/delete"/>
	</jstl:if>

	
</acme:form>
