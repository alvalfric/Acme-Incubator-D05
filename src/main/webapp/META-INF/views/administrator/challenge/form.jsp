<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<acme:form>
	<acme:form-textbox code="administrator.challenge.form.label.title" path="title" placeholder="Title"/>
	<acme:form-textbox code="administrator.challenge.form.label.deadline" path="deadline"/>
	<acme:form-textarea code="administrator.challenge.form.label.description" path="description" placeholder="Description"/>
	<acme:form-textbox code="administrator.challenge.form.label.expertGoal" path="expertGoal" placeholder="Expert Goal"/>
	<acme:form-textbox code="administrator.challenge.form.label.expertReward" path="expertReward" placeholder="Expert Reward"/>
	<acme:form-textbox code="administrator.challenge.form.label.averageGoal" path="averageGoal" placeholder="Average Goal"/>
	<acme:form-textbox code="administrator.challenge.form.label.averageReward" path="averageReward" placeholder="Average Reward"/>
	<acme:form-textbox code="administrator.challenge.form.label.rookieGoal" path="rookieGoal" placeholder="Rookie Goal"/>
	<acme:form-textbox code="administrator.challenge.form.label.rookieReward" path="rookieReward" placeholder="Rookie Reward"/>
	
	<acme:form-submit test="${command == 'show'}"
		code="administrator.challenge.form.button.update" 
		action="/administrator/challenge/update"/>
	<acme:form-submit test="${command == 'show'}"
		code="administrator.challenge.form.button.delete" 
		action="/administrator/challenge/delete"/>
	<acme:form-submit test="${command == 'create'}"
		code="administrator.challenge.form.button.create" 
		action="/administrator/challenge/create"/>
	<acme:form-submit test="${command == 'update'}"
		code="administrator.challenge.form.button.update" 
		action="/administrator/challenge/update"/>
	<acme:form-submit test="${command == 'delete'}"
		code="administrator.challenge.form.button.delete" 
		action="/administrator/challenge/delete"/>
	<acme:form-return code="administrator.challenge.form.button.return"/>
</acme:form>