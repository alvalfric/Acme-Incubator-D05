<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.inquirie.form.label.title" path="title" />
	<acme:form-textbox code="authenticated.inquirie.form.label.creation" path="creation"/>
	<acme:form-textbox code="authenticated.inquirie.form.label.deadline" path="deadline"/>
	<acme:form-textarea code="authenticated.inquirie.form.label.body" path="body"/>
	<acme:form-textbox code="authenticated.inquirie.form.label.minMoney" path="minMoney"/>
	<acme:form-textbox code="authenticated.inquirie.form.label.maxMoney" path="maxMoney"/>
	<acme:form-textbox code="authenticated.inquirie.form.label.contactEmail" path="contactEmail"/>

	<acme:form-return code="authenticated.inquirie.form.button.return"/>
</acme:form>