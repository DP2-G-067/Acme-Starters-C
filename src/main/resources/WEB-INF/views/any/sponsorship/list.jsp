<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.sponsorship.list.label.ticker" path="ticker" width="20%"/>
	<acme:list-column code="any.sponsorship.list.label.name" path="name" width="30%"/>
	<acme:list-column code="any.sponsorship.list.label.startMoment" path="startMoment" width="25%"/>
	<acme:list-column code="any.sponsorship.list.label.endMoment" path="endMoment" width="25%"/>
</acme:list>
