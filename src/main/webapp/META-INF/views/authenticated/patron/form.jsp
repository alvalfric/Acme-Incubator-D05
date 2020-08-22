<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:form>
	<acme:form-textbox code="authenticated.patron.form.label.organizationName" path="organizationName" />
	<b><acme:message code="authenticated.patron.form.label.creditCard"/></b>
	<br><br>
	<acme:form-textbox code="authenticated.patron.form.label.holderName" path="holderName" placeholder="Holder Name"/>
	<acme:form-integer code="authenticated.patron.form.label.number" path="number" placeholder="1111111111111111"/>
	<acme:form-textbox code="authenticated.patron.form.label.brand" path="brand" placeholder="Brand"/>
	<acme:form-textbox code="authenticated.patron.form.label.expirationDate" path="expirationDate" placeholder="MM/YYYY"/>
	<acme:form-integer code="authenticated.patron.form.label.CVV" path="CVV" placeholder="123"/>

	<acme:form-submit test="${command == 'create'}" code="authenticated.patron.form.button.create"
		action="/authenticated/patron/create" />
	<acme:form-submit test="${command == 'update'}" code="authenticated.patron.form.button.update"
		action="/authenticated/patron/update" />
	<acme:form-return code="authenticated.patron.form.button.return" />
</acme:form>
