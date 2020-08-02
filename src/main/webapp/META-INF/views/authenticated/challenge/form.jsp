<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<acme:form readonly="true">
	<acme:form-textbox code="authenticated.challenge.form.label.title" path="title" />
	<acme:form-textbox code="authenticated.challenge.form.label.deadline" path="deadline"/>
	<acme:form-textarea code="authenticated.challenge.form.label.description" path="description"/>
	<acme:form-textbox code="authenticated.challenge.form.label.expertGoalReward" path="expertGoalReward"/>
	<acme:form-textbox code="authenticated.challenge.form.label.averageGoalReward" path="averageGoalReward"/>
	<acme:form-textbox code="authenticated.challenge.form.label.rookieGoalReward" path="rookieGoalReward"/>

	<acme:form-return code="authenticated.challenge.form.button.return"/>
</acme:form>