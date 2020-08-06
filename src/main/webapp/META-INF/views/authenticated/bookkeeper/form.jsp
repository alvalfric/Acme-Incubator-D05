<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="authenticated.bookkeeper.form.label.firmName" path="firmName"/>
	<acme:form-textbox code="authenticated.bookkeeper.form.label.responsabilityStatement" path="responsabilityStatement"/>
	
	<acme:form-submit test="${command == 'create'}" code="authenticated.bookkeeper.form.button.create" action="/authenticated/bookkeeper/create"/>
	<acme:form-submit test="${command == 'update'}" code="authenticated.bookkeeper.form.button.update" action="/authenticated/bookkeeper/update"/>
	<acme:form-return code="authenticated.bookkeeper.form.button.return"/>
</acme:form>
