<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="fundraiser.strategy.list.label.ticker" path="ticker" width="20%"/>	
	<acme:list-column code="fundraiser.strategy.list.label.name" path="name" width="40%"/>
	<acme:list-column code="fundraiser.strategy.list.label.description" path="description" width="40%"/>
</acme:list>
		<acme:button code="fundraiser.strategy.form.button.create" action="/fundraiser/strategy/create"/>
