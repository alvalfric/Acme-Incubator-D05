<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="authenticated.challenge.list.label.title" path="title" width="40%" />
	<acme:list-column code="authenticated.challenge.list.label.deadline" path="deadline" width="30%" />
	<acme:list-column code="authenticated.challenge.list.label.description" path="description" width="30%" />
</acme:list>