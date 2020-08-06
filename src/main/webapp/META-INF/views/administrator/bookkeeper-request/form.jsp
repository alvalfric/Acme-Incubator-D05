<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="administrator.bookkeeper-request.form.label.username" path="username"/>
	<acme:form-textbox code="administrator.bookkeeper-request.form.label.firmName" path="firmName"/>
	<acme:form-textbox code="administrator.bookkeeper-request.form.label.responsabilityStatement" path="responsabilityStatement"/>
	
	<acme:form-submit test="${command == 'show'}" code="administrator.bookkeeper-request.form.button.accept" action="/administrator/bookkeeper-request/accept"/>
	<acme:form-submit test="${command == 'accept'}" code="administrator.bookkeeper-request.form.button.accept" action="/administrator/bookkeeper-request/accept"/>
	<acme:form-return code="administrator.bookkeeper-request.form.button.return"/>
</acme:form>
