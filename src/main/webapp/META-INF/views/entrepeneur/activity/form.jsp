<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<acme:form>
	<acme:form-textbox code="entrepeneur.activity.form.label.title" path="title" />
	<acme:form-textbox code="entrepeneur.activity.form.label.creation" path="creation" readonly="true" />
	<acme:form-textbox code="entrepeneur.activity.form.label.deadline" path="deadline" />
	<acme:form-textbox code="entrepeneur.activity.form.label.money" path="money" />

	<acme:form-hidden path="investmentRoundId"/>

	<acme:form-submit test="${command == 'show'}"
		code="entrepeneur.activity.form.button.update" 
		action="/entrepeneur/activity/update"/>
	<acme:form-submit test="${command == 'show'}"
		code="entrepeneur.activity.form.button.delete" 
		action="/entrepeneur/activity/delete"/>
	<acme:form-submit test="${command == 'create'}"
		code="entrepeneur.activity.form.button.create" 
		action="/entrepeneur/activity/create"/>
	<acme:form-submit test="${command == 'update'}"
		code="entrepeneur.activity.form.button.update" 
		action="/entrepeneur/activity/update"/>
	<acme:form-submit test="${command == 'delete'}"
		code="entrepeneur.activity.button.delete" 
		action="/entrepeneur/activity/delete"/>
	<acme:form-return code="entrepeneur.activity.form.button.return" />
</acme:form>