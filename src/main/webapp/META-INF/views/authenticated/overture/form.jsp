<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<acme:form readonly="true">
	<acme:form-textbox code="anonymous.overture.form.label.title" path="title" />
	<acme:form-textbox code="anonymous.overture.form.label.creation" path="creation"/>
	<acme:form-textbox code="anonymous.overture.form.label.deadline" path="deadline"/>
	<acme:form-textarea code="anonymous.overture.form.label.body" path="body"/>
	<acme:form-textbox code="anonymous.overture.form.label.minMoney" path="minMoney"/>
	<acme:form-textbox code="anonymous.overture.form.label.maxMoney" path="maxMoney"/>
	<acme:form-url code="anonymous.overture.form.label.contactEmail" path="contactEmail"/>

	<acme:form-return code="anonymous.overture.form.button.return"/>
</acme:form>