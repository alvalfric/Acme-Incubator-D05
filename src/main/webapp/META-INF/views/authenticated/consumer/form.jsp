<%--
- form.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="authenticated.consumer.consumer.form.label.company" path="company" />
	<acme:form-select code="authenticated.consumer.consumer.form.label.sector" path="sector">
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

	<acme:form-submit test="${command == 'create'}" code="authenticated.consumer.consumer.form.button.create"
		action="/authenticated/consumer/create" />
	<acme:form-submit test="${command == 'update'}" code="authenticated.consumer.consumer.form.button.update"
		action="/authenticated/consumer/update" />
	<acme:form-return code="authenticated.consumer.consumer.form.button.return" />
</acme:form>
