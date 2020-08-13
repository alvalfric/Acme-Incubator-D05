<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="authenticated.investor.form.label.firmName" path="firmName"/>
	<acme:form-select code="authenticated.investor.form.label.activitySector" path="activitySector">
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
	<acme:form-textarea code="authenticated.investor.form.label.profile" path="profile"/>

	<acme:form-submit test="${command == 'create'}" code="authenticated.investor.form.button.create" action="/authenticated/investor/create"/>
	<acme:form-submit test="${command == 'update'}" code="authenticated.investor.form.button.update" action="/authenticated/investor/update"/>
	<acme:form-return code="authenticated.investor.form.button.return"/>
</acme:form>
