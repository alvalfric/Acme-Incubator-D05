<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="authenticated.entrepeneur.form.label.startUpName" path="startUpName"/>
	<acme:form-select code="authenticated.entrepeneur.form.label.activitySector" path="activitySector">
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
	<acme:form-textarea code="authenticated.entrepeneur.form.label.qualification" path="qualification"/>
	<acme:form-textarea code="authenticated.entrepeneur.form.label.skills" path="skills"/>
		
	<acme:form-submit test="${command == 'create'}" code="authenticated.entrepeneur.form.button.create" action="/authenticated/entrepeneur/create"/>
	<acme:form-submit test="${command == 'update'}" code="authenticated.entrepeneur.form.button.update" action="/authenticated/entrepeneur/update"/>
	<acme:form-return code="authenticated.entrepeneur.form.button.return"/>
</acme:form>
