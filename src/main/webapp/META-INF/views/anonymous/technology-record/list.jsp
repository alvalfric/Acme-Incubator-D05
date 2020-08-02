<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="anonymous.technology-record.list.label.title" path="title" width="35%" />
	<acme:list-column code="anonymous.technology-record.list.label.activitySector" path="activitySector" width="35%" />
	<acme:list-column code="anonymous.technology-record.list.label.inventorName" path="inventorName" width="25%" />
	<acme:list-column code="anonymous.technology-record.list.label.stars" path="stars" width="5%" />
</acme:list>