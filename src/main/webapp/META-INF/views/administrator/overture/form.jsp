<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<acme:form>
	<acme:form-textbox code="administrator.overture.form.label.title" path="title" placeholder="Title"/>
	<jstl:if test="${command != 'create'}">
		<acme:form-textbox code="administrator.overture.form.label.creation" path="creation" readonly="true"/>
	</jstl:if>	
	<acme:form-textbox code="administrator.overture.form.label.deadline" path="deadline"/>
	<acme:form-textarea code="administrator.overture.form.label.body" path="body" placeholder="Body"/>
	<acme:form-textbox code="administrator.overture.form.label.minMoney" path="minMoney" placeholder="1000 EUR"/>
	<acme:form-textbox code="administrator.overture.form.label.maxMoney" path="maxMoney" placeholder="1000 EUR"/>
	<acme:form-textbox code="administrator.overture.form.label.contactEmail" path="contactEmail" placeholder="user@mail.com"/>

	<acme:form-submit test="${command == 'show'}"
		code="administrator.overture.form.button.update" 
		action="/administrator/overture/update"/>
	<acme:form-submit test="${command == 'show'}"
		code="administrator.overture.form.button.delete" 
		action="/administrator/overture/delete"/>
	<acme:form-submit test="${command == 'create'}"
		code="administrator.overture.form.button.create" 
		action="/administrator/overture/create"/>
	<acme:form-submit test="${command == 'update'}"
		code="administrator.overture.form.button.update" 
		action="/administrator/overture/update"/>
	<acme:form-submit test="${command == 'delete'}"
		code="administrator.overture.form.button.delete" 
		action="/administrator/overture/delete"/>
	<acme:form-return code="administrator.overture.form.button.return"/>
</acme:form>