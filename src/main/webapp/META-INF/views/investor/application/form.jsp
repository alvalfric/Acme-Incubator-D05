<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<acme:form readonly="true">
	<acme:form-textbox code="investor.application.form.label.ticker" path="ticker" />
	<acme:form-textbox code="investor.application.form.label.creation" path="creation"/>
	<acme:form-textarea code="investor.application.form.label.statement" path="statement"/>
	<acme:form-textbox code="investor.application.form.label.offer" path="offer" />

	<acme:form-return code="investor.application.form.button.return"/>
</acme:form>