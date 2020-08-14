<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
table {
  border-collapse: collapse;
}
td, th {
  border: 1px solid #e9ecef;
  text-align: left;
  padding: 8px;
}
tr:nth-child(even) {
  background-color: #e9ecef;
}
</style>

<acme:form >
	<acme:form-textbox code="authenticated.forum-user.form.label.addUserByUsername" path="addUserByUsername"/>
	
	<acme:form-submit method="post"
		code="authenticated.forum-user.form.button.add" 
		action="/authenticated/forum-user/add?forumId=${forumId}"/>
	<acme:form-hidden path="forumId"/>
	<br><br>

	<jstl:if test="${users != null && !users.isEmpty()}">
	<b><acme:message code="authenticated.forum-user.form.label.users"/></b>
	<br>
	<table>
		<tr>
			<th><acme:message code="authenticated.forum-user.form.label.users.username"/></th>
		</tr>
		<jstl:forEach var="user" items="${users}">
		<tr>
			<td><acme:print value="${user.userAccount.username}"/></td>
			<td style="border: 0px; background-color: #ffffff;">
			<jstl:if test="${user.userAccount.id != cantDeleteUserId}">
			<acme:form-submit
				code="authenticated.forum-user.form.button.users.delete"
				action="/authenticated/forum-user/delete?username=${user.userAccount.username}"/>
			</jstl:if>	
			</td>
		</tr>
		</jstl:forEach>
	</table>
	<br>
	</jstl:if>

	<acme:form-return code="authenticated.forum-message.form.button.return"/>
</acme:form>