<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:form-textbox  
		code="inventor.part.form.label.name" 
		path="name"
		readonly="${!draftMode && id != 0}"/>

	<acme:form-textarea 
		code="inventor.part.form.label.description" 
		path="description"
		readonly="${!draftMode && id != 0}"/>

	<acme:form-money    
		code="inventor.part.form.label.cost" 
		path="cost"
		readonly="${!draftMode && id != 0}"/>

	<jstl:if test="${draftMode || id == 0}">
		<acme:form-select 
			code="inventor.part.form.label.kind" 
			path="kind" 
			choices="${kinds}"/>
	</jstl:if>

	<jstl:if test="${!draftMode && id != 0}">
		<acme:form-textbox 
			code="inventor.part.form.label.kind" 
			path="kind" 
			readonly="true"/>
	</jstl:if>

	<jstl:if test="${_command == 'create'}">
		<acme:submit 
			code="inventor.part.form.button.create"
			action="/inventor/part/create?inventionId=${inventionId}"/>
	</jstl:if>

	<jstl:if test="${_command != 'create' && draftMode}">
		<acme:submit 
			code="inventor.part.form.button.update"
			action="/inventor/part/update"/>

		<acme:submit 
			code="inventor.part.form.button.delete"
			action="/inventor/part/delete"/>
	</jstl:if>

</acme:form>