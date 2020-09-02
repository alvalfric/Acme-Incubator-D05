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

<acme:form>
	<acme:form-textbox code="entrepeneur.investment-round.form.label.ticker" path="ticker" readonly="true"/>
	<acme:form-textbox code="entrepeneur.investment-round.form.label.creation" path="creation" readonly="true"/>	
	<acme:form-select code="entrepeneur.investment-round.form.label.round" path="round" readonly="${isFinalMode}">
		<acme:form-option code="entrepeneur.investment-round.form.label.seed" value="SEED" selected="${round == 'SEED'}"/>
		<acme:form-option code="entrepeneur.investment-round.form.label.angel" value="ANGEL" selected="${round == 'ANGEL'}"/>
		<acme:form-option code="entrepeneur.investment-round.form.label.seriesA" value="SERIES-A" selected="${round == 'SERIES-A'}"/>
		<acme:form-option code="entrepeneur.investment-round.form.label.seriesB" value="SERIES-B" selected="${round == 'SERIES-B'}"/>
		<acme:form-option code="entrepeneur.investment-round.form.label.seriesC" value="SERIES-C" selected="${round == 'SERIES-C'}"/>
		<acme:form-option code="entrepeneur.investment-round.form.label.bridge" value="BRIDGE" selected="${round == 'BRIDGE'}"/>
	</acme:form-select>
	<acme:form-textbox code="entrepeneur.investment-round.form.label.title" path="title" readonly="${isFinalMode}"/>
	<acme:form-textarea code="entrepeneur.investment-round.form.label.description" path="description" readonly="${isFinalMode}"/>
	<acme:form-textbox code="entrepeneur.investment-round.form.label.amount" path="amount" readonly="true"/>
	<acme:form-textbox code="entrepeneur.investment-round.form.label.link" path="link" readonly="${isFinalMode}"/>
	<jstl:if test="${command != 'create'}">
		<acme:form-checkbox code="entrepeneur.investment-round.form.label.finalMode" path="finalMode" />
	</jstl:if>

	<jstl:if test="${workProgramme != null && !workProgramme.isEmpty()}">
	<b><acme:message code="entrepeneur.investment-round.form.label.workProgramme"/></b>
	<br>
	<table>
		<tr>
			<th><acme:message code="entrepeneur.investment-round.form.label.workProgramme.title"/></th>
			<th><acme:message code="entrepeneur.investment-round.form.label.workProgramme.creation"/></th>
			<th><acme:message code="entrepeneur.investment-round.form.label.workProgramme.deadline"/></th>
			<th><acme:message code="entrepeneur.investment-round.form.label.workProgramme.money"/></th>
		</tr>
		<jstl:forEach var="activity" items="${workProgramme}">
		<tr>
			<td><acme:print value="${activity.title}"/></td>
			<td><acme:print value="${activity.creation}"/></td>
			<td><acme:print value="${activity.deadline}"/></td>
			<td><acme:print value="${activity.money}"/></td>
			<jstl:if test="${command != 'create' && finalMode == false}">
			<td>
			<acme:form-submit method="get" test="${command != 'create' && finalMode == false}" 
					code="entrepeneur.investment-round.form.button.activity.show"
					action="/entrepeneur/activity/show?id=${activity.id}"/>
			</td>
			</jstl:if>
		</tr>
		</jstl:forEach>
	</table>
	<br>
	</jstl:if>
	
	 
	<jstl:if test="${command != 'create' && finalMode == false}">
	<acme:form-submit method="get" 
		code="entrepeneur.investment-round.form.button.activity"
		action="/entrepeneur/activity/create?investmentRoundId=${id}"/>
	<br>
	<br>
	</jstl:if>
		
	<acme:form-submit test="${command == 'show' && finalMode == false}"
		code="entrepeneur.investment-round.form.button.update" 
		action="/entrepeneur/investment-round/update"/>
	<acme:form-submit test="${command == 'show' && canBeDeleted}"
		code="entrepeneur.investment-round.form.button.delete" 
		action="/entrepeneur/investment-round/delete"/>
	<acme:form-submit test="${command == 'create'}"
		code="entrepeneur.investment-round.form.button.create" 
		action="/entrepeneur/investment-round/create"/>
	<acme:form-submit test="${command == 'update' && finalMode == false}"
		code="entrepeneur.investment-round.form.button.update" 
		action="/entrepeneur/investment-round/update"/>
	<acme:form-submit test="${command == 'delete' && canBeDeleted}"
		code="entrepeneur.investment-round.button.delete" 
		action="/entrepeneur/investment-round/delete"/>
	<acme:form-submit test="${canCreateForum && isFinalMode}"
		code="entrepeneur.investment-round.form.button.forum.create"
		action="/authenticated/forum/create?investmentRoundId=${id}"/>
	<acme:form-submit method="get" test="${command == 'show'}" 
		code="entrepeneur.investment-round.form.button.accounting-record"
		action="/entrepeneur/accounting-record/list-mine?investmentRoundId=${id}"/>

	<acme:form-return code="entrepeneur.investment-round.form.button.return"/>
</acme:form>