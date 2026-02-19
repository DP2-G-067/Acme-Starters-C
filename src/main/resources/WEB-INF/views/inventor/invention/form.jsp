<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox  code="inventor.invention.form.label.ticker" path="ticker"/>
	<acme:form-textbox  code="inventor.invention.form.label.name" path="name"/>
	<acme:form-textarea code="inventor.invention.form.label.description" path="description"/>
	<acme:form-moment   code="inventor.invention.form.label.startMoment" path="startMoment"/>
	<acme:form-moment   code="inventor.invention.form.label.endMoment" path="endMoment"/>
	<acme:form-url      code="inventor.invention.form.label.moreInfo" path="moreInfo"/>

	<!-- Campos solo lectura -->
	<acme:form-double   code="inventor.invention.form.label.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-checkbox code="inventor.invention.form.label.draftMode" path="draftMode" readonly="true"/>

	<!-- Botones según comando -->
	<jstl:if test="${_command == 'create'}">
		<acme:submit code="inventor.invention.form.button.create" action="/inventor/invention/create"/>
	</jstl:if>

	<jstl:if test="${_command == 'update'}">
		<acme:submit code="inventor.invention.form.button.update" action="/inventor/invention/update"/>
	</jstl:if>

	<jstl:if test="${_command == 'delete'}">
		<acme:submit code="inventor.invention.form.button.delete" action="/inventor/invention/delete"/>
	</jstl:if>

	<!-- Botón Publish: solo tiene sentido si estás en update/show y está en borrador -->
	<jstl:if test="${_command == 'update' || _command == 'show'}">
		<jstl:if test="${draftMode}">
			<acme:submit code="inventor.invention.form.button.publish" action="/inventor/invention/publish"/>
		</jstl:if>
	</jstl:if>

	<!-- Navegación a parts (siempre en show/update) -->
	<jstl:if test="${_command == 'show' || _command == 'update'}">
		<acme:button code="inventor.invention.form.button.parts" action="/inventor/part/list?inventionId=${id}"/>
	</jstl:if>
</acme:form>
