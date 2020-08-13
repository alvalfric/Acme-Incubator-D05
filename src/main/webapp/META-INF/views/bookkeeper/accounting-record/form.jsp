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
	<acme:form-select code="bookkeeper.accounting-record.form.label.status" path="status">
		<acme:form-option code="bookkeeper.accounting-record.form.label.status.draft" value="draft"/>
		<acme:form-option code="bookkeeper.accounting-record.form.label.status.published" value="published"/>
	</acme:form-select>
	<acme:form-textarea code="bookkeeper.accounting-record.form.label.body" path="body" />
	<acme:form-hidden path="investmentRoundId"/>

	<acme:form-submit test="${command == 'create'}"
		code="bookkeeper.accounting-record.form.button.create" 
		action="/bookkeeper/accounting-record/create"/>
	<acme:form-submit test="${command == 'show'}"
		code="bookkeeper.accounting-record.form.button.update" 
		action="/bookkeeper/accounting-record/update"/>
	<acme:form-submit test="${command == 'update'}"
		code="bookkeeper.accounting-record.form.button.update" 
		action="/bookkeeper/accounting-record/update"/>
	<acme:form-return code="bookkeeper.accounting-record.form.button.return"/>
</acme:form>