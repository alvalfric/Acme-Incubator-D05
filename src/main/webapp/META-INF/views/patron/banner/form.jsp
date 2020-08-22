<%@page language="java"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<acme:form>
	<acme:form-textbox code="patron.banner.form.label.picture" path="picture" placeholder="https://picture.com/"/>
	<acme:form-textbox code="patron.banner.form.label.slogan" path="slogan" placeholder="Slogan"/>
	<acme:form-textbox code="patron.banner.form.label.url" path="url" placeholder="https://example.com/"/>

	<acme:form-submit test="${command == 'show'}" code="patron.banner.form.button.update"
		action="/patron/banner/update" />
	<acme:form-submit test="${command == 'update'}" code="patron.banner.form.button.update"
		action="/patron/banner/update" />
	<acme:form-submit test="${command == 'show'}" code="patron.banner.form.button.delete"
		action="/patron/banner/delete" />
	<acme:form-submit test="${command == 'update'}" code="patron.banner.form.button.delete"
		action="/patron/banner/delete" />
	<acme:form-submit test="${command == 'delete'}" code="patron.banner.form.button.delete"
		action="/patron/banner/delete" />
	<acme:form-submit test="${command == 'create'}" code="patron.banner.form.button.create"
		action="/patron/banner/create" />
	<acme:form-return code="patron.banner.form.button.return" />
</acme:form>