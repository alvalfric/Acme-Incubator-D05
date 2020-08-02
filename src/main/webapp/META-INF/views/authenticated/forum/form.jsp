<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">

<acme:form readonly="true">
	<h4>
		<acme:print value="${forumTitle}"/>
	</h4>

	<jstl:forEach var="message" items="${forumMessages}">
		<div class="media border p-2 bg-light">
			<div class="media-body">
				<h5 class="mt-0 mb-1">
					<b><acme:print value="${message.title}"/></b> 
					<small><i><acme:message code="authenticated.forum.form.label.forumMessage.creation"/> <acme:print value="${message.creation}"/> 
					<acme:message code="authenticated.forum.form.label.forumMessage.username"/> <b><acme:print value="${message.user.userAccount.username}"/></b></i></small>
				</h5>
				<acme:print value="${message.body}"/>
				<br>
				<acme:message code="authenticated.forum.form.label.forumMessage.tags"/> <acme:print value="${message.tags}"/>
			</div>
		</div>
		
		<br>
	</jstl:forEach>
	
	<acme:form-return code="authenticated.forum.form.button.return"/>
</acme:form>