<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">

<acme:form >
	<acme:form-textbox code="authenticated.forum-message.form.label.title" path="title"/>
	<acme:form-textbox code="authenticated.forum-message.form.label.creation" path="creation" readonly="true"/>
	<acme:form-textbox code="authenticated.forum-message.form.label.tags" path="tags"/>
	<acme:form-textarea code="authenticated.forum-message.form.label.body" path="body"/>
	<acme:form-checkbox code="authenticated.forum-message.form.label.confirmation" path="confirmation"/>
	<acme:form-hidden path="forumId"/>

	<acme:form-submit
		code="authenticated.forum-message.form.button.create" 
		action="/authenticated/forum-message/create?forumId=${forumId}"/>

	<acme:form-return code="authenticated.forum-message.form.button.return"/>
</acme:form>