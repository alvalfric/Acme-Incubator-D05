<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<acme:form>
	<acme:message code="entrepeneur.application.form.label.investment"/> <a href="/acme-incubator/entrepeneur/investment-round/show?id=${investmentRound.id}"><b><jstl:out value="${investmentRound.ticker}"/></b></a>
	<br><br>
	<acme:form-textbox code="entrepeneur.application.form.label.ticker" path="ticker" readonly="true"/>
	<acme:form-textbox code="entrepeneur.application.form.label.creation" path="creation" readonly="true"/>
	<acme:form-textarea code="entrepeneur.application.form.label.statement" path="statement" readonly="true"/>
	<acme:form-textbox code="entrepeneur.application.form.label.offer" path="offer" readonly="true"/>
	<acme:form-textarea code="entrepeneur.application.form.label.rejectJustification" path="rejectJustification"/>

	<acme:form-submit test="${command == 'show' && status == 'pending'}"
		code="entrepeneur.application.form.button.accept" 
		action="/entrepeneur/application/accept"/>
	<acme:form-submit test="${command == 'accept'}"
		code="entrepeneur.application.form.button.accept" 
		action="/entrepeneur/application/accept"/>
	<acme:form-submit test="${command == 'reject'}"
		code="entrepeneur.application.form.button.accept" 
		action="/entrepeneur/application/accept"/>
	<acme:form-submit test="${command == 'show' && status == 'pending'}"
		code="entrepeneur.application.form.button.reject" 
		action="/entrepeneur/application/reject"/>
	<acme:form-submit test="${command == 'accept'}"
		code="entrepeneur.application.form.button.reject" 
		action="/entrepeneur/application/reject"/>
	<acme:form-submit test="${command == 'reject'}"
		code="entrepeneur.application.form.button.reject" 
		action="/entrepeneur/application/reject"/>
	<acme:form-return code="entrepeneur.application.form.button.return"/>
</acme:form>