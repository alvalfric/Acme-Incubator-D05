<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<acme:form>
	<acme:form-textbox code="administrator.technology-record.form.label.title" path="title" placeholder="Title"/>
	<acme:form-select code="administrator.technology-record.form.label.activitySector" path="activitySector">
			<jstl:forEach var="sector" items="${activitySectors}">
			<jstl:choose>
			<jstl:when test="${activitySector == sector}">
				<acme:form-option code="${sector}" value="${sector}" selected="true"/>
			</jstl:when>
			<jstl:otherwise>
				<acme:form-option code="${sector}" value="${sector}"/>
			</jstl:otherwise>
			</jstl:choose>
		</jstl:forEach>
	</acme:form-select>
	<acme:form-textbox code="administrator.technology-record.form.label.inventorName" path="inventorName" placeholder="Inventors' Name"/>
	<acme:form-textarea code="administrator.technology-record.form.label.description" path="description" placeholder="Description"/>
	<acme:form-url code="administrator.technology-record.form.label.website" path="website" placeholder="https://example.com/"/>
	<acme:form-textbox code="administrator.technology-record.form.label.email" path="email" placeholder="user@mail.com"/>
	<acme:form-select code="administrator.technology-record.form.label.sourceType" path="sourceType">
		<acme:form-option code="administrator.technology-record.form.label.openSource" value="Open-Source"/>
		<acme:form-option code="administrator.technology-record.form.label.closedSource" value="Closed-Source"/>
	</acme:form-select>
	<acme:form-textbox code="administrator.technology-record.form.label.stars" path="stars" placeholder="5"/>

	<acme:form-submit test="${command == 'show'}"
		code="administrator.technology-record.form.button.update" 
		action="/administrator/technology-record/update"/>
	<acme:form-submit test="${command == 'show'}"
		code="administrator.technology-record.form.button.delete" 
		action="/administrator/technology-record/delete"/>
	<acme:form-submit test="${command == 'create'}"
		code="administrator.technology-record.form.button.create" 
		action="/administrator/technology-record/create"/>
	<acme:form-submit test="${command == 'update'}"
		code="administrator.technology-record.form.button.update" 
		action="/administrator/technology-record/update"/>
	<acme:form-submit test="${command == 'delete'}"
		code="administrator.technology-record.form.button.delete" 
		action="/administrator/technology-record/delete"/>
	<acme:form-return code="administrator.technology-record.form.button.return"/>
</acme:form>