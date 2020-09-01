<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<acme:form>
	<acme:form-textbox code="bookkeeper.accounting-record.form.label.title" path="title" />
	<jstl:if test="${command != 'create'}">
	<acme:form-textbox code="bookkeeper.accounting-record.form.label.creation" path="creation" readonly="true"/>
	</jstl:if>
	<jstl:if test="${canUpdate || command == 'create'}">
	<acme:form-select code="bookkeeper.accounting-record.form.label.status" path="status">
	<jstl:choose>
		<jstl:when test="${status == 'published'}">
			<acme:form-option code="bookkeeper.accounting-record.form.label.status.published" value="published" selected="true"/>
			<acme:form-option code="bookkeeper.accounting-record.form.label.status.draft" value="draft"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:form-option code="bookkeeper.accounting-record.form.label.status.published" value="published"/>
			<acme:form-option code="bookkeeper.accounting-record.form.label.status.draft" value="draft" selected="true"/>
		</jstl:otherwise>
	</jstl:choose>
	</acme:form-select>
	</jstl:if>
	<acme:form-textarea code="bookkeeper.accounting-record.form.label.body" path="body" />
	<acme:form-hidden path="investmentRoundId"/>

	<acme:form-submit test="${command == 'create'}"
		code="bookkeeper.accounting-record.form.button.create" 
		action="/bookkeeper/accounting-record/create"/>
	<acme:form-submit test="${command == 'show' && canUpdate}"
		code="bookkeeper.accounting-record.form.button.update" 
		action="/bookkeeper/accounting-record/update"/>
	<acme:form-submit test="${command == 'update' && canUpdate}"
		code="bookkeeper.accounting-record.form.button.update" 
		action="/bookkeeper/accounting-record/update"/>
	<acme:form-return code="bookkeeper.accounting-record.form.button.return"/>
</acme:form>