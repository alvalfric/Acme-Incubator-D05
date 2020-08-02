<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="authenticated.inquirie.list.label.title" path="title" width="50%" />
	<acme:list-column code="authenticated.inquirie.list.label.creation" path="creation" width="25%" />
	<acme:list-column code="authenticated.inquirie.list.label.deadline" path="deadline" width="25%" />
</acme:list>