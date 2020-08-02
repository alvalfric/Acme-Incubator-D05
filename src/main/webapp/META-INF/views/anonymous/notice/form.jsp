<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<acme:form readonly="true">
	<img style="display: block; margin-left: auto; margin-right: auto; width: 50%;" src="<jstl:out value="${headerPicture}"/>"/>
	<acme:form-textbox code="anonymous.notice.form.label.title" path="title" />
	<acme:form-textbox code="anonymous.notice.form.label.creation" path="creation" />
	<acme:form-textarea code="anonymous.notice.form.label.body" path="body" />

	<jstl:if test="${!relatedLink1.isEmpty()}">
	<acme:form-url code="anonymous.notice.form.label.relatedLink1" path="relatedLink1" />
	</jstl:if>

	<jstl:if test="${!relatedLink2.isEmpty()}">
	<acme:form-url code="anonymous.notice.form.label.relatedLink2" path="relatedLink2" />
	</jstl:if>
	<!--
	<acme:form-submit code="anonymous.notice.form.button.create" action="/anonymous/notice/create/" />
	-->
	<acme:form-return code="anonymous.notice.form.button.return"/>
</acme:form>