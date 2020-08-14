<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<acme:form>
	<acme:message code="investor.application.form.label.investment"/> <a href="/acme-incubator/investor/investment-round/show?id=${investmentRound.id}"><b><jstl:out value="${investmentRound.ticker}"/></b></a>
	<br><br>
	<acme:form-textbox code="investor.application.form.label.ticker" path="ticker" readonly="true"/>
	<acme:form-textbox code="investor.application.form.label.creation" path="creation" readonly="true"/>
	<acme:form-textarea code="investor.application.form.label.statement" path="statement"/>
	<acme:form-textbox code="investor.application.form.label.offer" path="offer" />
	<jstl:if test="${status != 'pending' && !status.isEmpty() && command != 'create'}">
		<acme:form-textarea code="investor.application.form.label.rejectJustification" path="rejectJustification"/>
	</jstl:if>
	<acme:form-hidden path="investmentRoundId"/>

	<acme:form-submit test="${command == 'create'}"
		code="investor.application.form.button.create" 
		action="/investor/application/create"/>
	<acme:form-return code="investor.application.form.button.return"/>
</acme:form>