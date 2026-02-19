<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="inventor.invention.list.label.ticker" path="ticker" width="20%"/>
	<acme:list-column code="inventor.invention.list.label.name" path="name" width="60%"/>
	<acme:list-column code="inventor.invention.list.label.draftMode" path="draftMode" width="20%"/>
</acme:list>
