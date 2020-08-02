<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="investor.application.list.label.ticker" path="ticker" width="34%" />
	<acme:list-column code="investor.application.list.label.creation" path="creation" width="33%" />
	<acme:list-column code="investor.application.list.label.offer" path="offer" width="33%" />
</acme:list>