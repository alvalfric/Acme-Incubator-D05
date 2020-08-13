<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
table {
  border-collapse: collapse;
  width: 100%;
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

<acme:form readonly="true">
	<acme:form-textbox code="bookkeeper.investment-round.form.label.ticker" path="ticker" />
	<acme:form-textbox code="bookkeeper.investment-round.form.label.creation" path="creation"/>
	<acme:form-textbox code="bookkeeper.investment-round.form.label.round" path="round"/>
	<acme:form-textbox code="bookkeeper.investment-round.form.label.title" path="title" />
	<acme:form-textarea code="bookkeeper.investment-round.form.label.description" path="description"/>
	<acme:form-textbox code="bookkeeper.investment-round.form.label.amount" path="amount" />
	<jstl:if test="${!link.isEmpty()}">
	<acme:form-textbox code="bookkeeper.investment-round.form.label.link" path="link" />
	</jstl:if>

	<b><acme:message code="bookkeeper.investment-round.form.label.workProgramme"/></b>
	</br>
	<table>
		<tr>
			<th><acme:message code="bookkeeper.investment-round.form.label.workProgramme.title"/></th>
			<th><acme:message code="bookkeeper.investment-round.form.label.workProgramme.creation"/></th>
			<th><acme:message code="bookkeeper.investment-round.form.label.workProgramme.deadline"/></th>
			<th><acme:message code="bookkeeper.investment-round.form.label.workProgramme.money"/></th>
		</tr>
		<jstl:forEach var="activity" items="${workProgramme}">
		<tr>
			<td><acme:print value="${activity.title}"/></td>
			<td><acme:print value="${activity.creation}"/></td>
			<td><acme:print value="${activity.deadline}"/></td>
			<td><acme:print value="${activity.money}"/></td>
		</tr>
		</jstl:forEach>
	</table>
	<br>
	
	<acme:form-submit method="get" test="${canCreateAccountingRecord}"
		code="bookkeeper.investment-round.form.button.create" 
		action="/bookkeeper/accounting-record/create?investmentRoundId=${id}"/>
	<input type="button" class="btn btn-default" onclick="location.href='/acme-incubator/bookkeeper/accounting-record/list?investmentRoundId=${id}'" 
		value="<acme:message code="bookkeeper.investment-round.form.button.accounting-record"/>">
	<acme:form-return code="bookkeeper.investment-round.form.button.return"/>
</acme:form>