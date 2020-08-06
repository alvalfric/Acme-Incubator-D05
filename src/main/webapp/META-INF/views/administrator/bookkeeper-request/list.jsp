<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="administrator.bookkeeper-request.list.label.firmName" path="firmName" width="40%" />
	<acme:list-column code="administrator.bookkeeper-request.list.label.responsabilityStatement" path="responsabilityStatement" width="60%" />
</acme:list>