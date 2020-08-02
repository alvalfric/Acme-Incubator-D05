<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<acme:form>
	<acme:form-textarea code="administrator.customization-parameter.form.label.spamWordsEnglish" path="spamWordsEnglish" placeholder="word, word, word"/>
	<acme:form-textarea code="administrator.customization-parameter.form.label.spamWordsSpanish" path="spamWordsSpanish" placeholder="palabra, palabra, palabra"/>
	<acme:form-textbox code="administrator.customization-parameter.form.label.spamThreshold" path="spamThreshold" placeholder="0.00"/>

	<acme:form-submit
		code="administrator.customisation-parameter.form.button.update"
		action="/administrator/customization-parameter/update"/>
	<acme:form-return code="administrator.customization-parameter.form.button.return"/>
</acme:form>