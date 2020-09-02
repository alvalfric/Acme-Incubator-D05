<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<acme:form>
	<acme:form-textbox code="administrator.tool-record.form.label.title" path="title" placeholder="Title"/>
	<acme:form-select code="administrator.tool-record.form.label.activitySector" path="activitySector">
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
	<acme:form-textbox code="administrator.tool-record.form.label.inventorName" path="inventorName" placeholder="Inventors' Name"/>
	<acme:form-textarea code="administrator.tool-record.form.label.description" path="description" placeholder="Description"/>
	<acme:form-url code="administrator.tool-record.form.label.website" path="website" placeholder="https://example.com/"/>
	<acme:form-textbox code="administrator.tool-record.form.label.email" path="email" placeholder="user@mail.com"/>
	<acme:form-select code="administrator.tool-record.form.label.sourceType" path="sourceType">
	<jstl:choose>
		<jstl:when test="${sourceType == 'Closed-Source'}">
			<acme:form-option code="administrator.tool-record.form.label.openSource" value="Open-Source"/>
			<acme:form-option code="administrator.tool-record.form.label.closedSource" value="Closed-Source" selected="true"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:form-option code="administrator.tool-record.form.label.openSource" value="Open-Source" selected="true"/>
			<acme:form-option code="administrator.tool-record.form.label.closedSource" value="Closed-Source"/>
		</jstl:otherwise>
	</jstl:choose>
	</acme:form-select>
	<acme:form-textbox code="administrator.tool-record.form.label.stars" path="stars" placeholder="5"/>

	<acme:form-submit test="${command == 'show'}"
		code="administrator.tool-record.form.button.update" 
		action="/administrator/tool-record/update"/>
	<acme:form-submit test="${command == 'show'}"
		code="administrator.tool-record.form.button.delete" 
		action="/administrator/tool-record/delete"/>
	<acme:form-submit test="${command == 'create'}"
		code="administrator.tool-record.form.button.create" 
		action="/administrator/tool-record/create"/>
	<acme:form-submit test="${command == 'update'}"
		code="administrator.tool-record.form.button.update" 
		action="/administrator/tool-record/update"/>
	<acme:form-submit test="${command == 'delete'}"
		code="administrator.tool-record.form.button.delete" 
		action="/administrator/tool-record/delete"/>
	<acme:form-return code="administrator.tool-record.form.button.return"/>
</acme:form>