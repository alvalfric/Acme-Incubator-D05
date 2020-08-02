<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.accounting-record.form.label.title" path="title" />
	<acme:form-textbox code="authenticated.accounting-record.form.label.creation" path="creation"/>
	<acme:form-textarea code="authenticated.accounting-record.form.label.body" path="body" />

	<acme:form-return code="authenticated.accounting-record.form.button.return"/>
</acme:form>