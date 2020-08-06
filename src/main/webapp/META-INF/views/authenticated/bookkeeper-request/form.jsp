<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="authenticated.bookkeeper-request.form.label.firmName" path="firmName"/>
	<acme:form-textbox code="authenticated.bookkeeper-request.form.label.responsabilityStatement" path="responsabilityStatement"/>
	
	<acme:form-submit test="${command == 'create'}" code="authenticated.bookkeeper-request.form.button.create" action="/authenticated/bookkeeper-request/create"/>
	<acme:form-submit test="${command == 'update'}" code="authenticated.bookkeeper-request.form.button.update" action="/authenticated/bookkeeper-request/update"/>
	<acme:form-return code="authenticated.bookkeeper-request.form.button.return"/>
</acme:form>
