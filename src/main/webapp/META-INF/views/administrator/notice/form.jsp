<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<acme:form>
	<jstl:if test="${command == 'show'}">
		<img style="display: block; margin-left: auto; margin-right: auto; width: 50%;" src="<jstl:out value="${headerPicture}"/>" />
	</jstl:if>
	<jstl:if test="${command != 'show'}">
		<acme:form-url code="administrator.notice.form.label.headerPicture" path="headerPicture" />
	</jstl:if>

	<acme:form-textbox code="administrator.notice.form.label.title" path="title" placeholder="Title"/>

	<jstl:if test="${command != 'create'}">
		<acme:form-textbox code="administrator.notice.form.label.creation" path="creation" readonly="true"/>
	</jstl:if>
	<jstl:if test="${command != 'show'}">
		<acme:form-textbox code="administrator.notice.form.label.deadline" path="deadline" />
	</jstl:if>

	<acme:form-textarea code="administrator.notice.form.label.body" path="body" placeholder="Body"/>

	<jstl:if test="${command != 'show'}">
		<acme:form-url code="administrator.notice.form.label.relatedLink1" path="relatedLink1" />
		<acme:form-url code="administrator.notice.form.label.relatedLink2" path="relatedLink2" />
	</jstl:if>

	<jstl:if test="${command == 'show'}">
		<jstl:if test="${!relatedLink1.isEmpty()}">
			<acme:form-url code="administrator.notice.form.label.relatedLink1" path="relatedLink1" />
		</jstl:if>

		<jstl:if test="${!relatedLink2.isEmpty()}">
			<acme:form-url code="administrator.notice.form.label.relatedLink2" path="relatedLink2" />
		</jstl:if>
	</jstl:if>

	<jstl:if test="${command == 'create'}">
		<acme:form-checkbox code="administrator.notice.form.label.checkbox" path="checkbox" />
	</jstl:if>

	<acme:form-submit test="${command == 'create'}" 
		code="administrator.notice.form.button.create" 
		action="/administrator/notice/create/" />
	<acme:form-return code="administrator.notice.form.button.return" />
</acme:form>