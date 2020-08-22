<%--
- menu.jsp
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper,acme.entities.roles.Provider,acme.entities.roles.Consumer"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
			<acme:menu-suboption code="master.menu.anonymous.favourite-link" action="http://www.example.com/"/>
			<acme:menu-suboption code="master.menu.anonymous.favourite-link.alvaro" action="https://codepen.io/"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.anonymous.shout.list" action="/anonymous/shout/list"/>
			<acme:menu-suboption code="master.menu.anonymous.shout.create" action="/anonymous/shout/create"/>
			<acme:menu-suboption code="master.menu.anonymous.alferez-bulletin.list" action="/anonymous/alferez-bulletin/list"/>
		    <acme:menu-suboption code="master.menu.anonymous.alferez-bulletin.create" action="/anonymous/alferez-bulletin/create"/>
		    <acme:menu-separator/>
		    <acme:menu-suboption code="master.menu.anonymous.notice.list" action="/anonymous/notice/list"/>
			<acme:menu-suboption code="master.menu.anonymous.technology-record.list" action="/anonymous/technology-record/list"/>
			<acme:menu-suboption code="master.menu.anonymous.tool-record.list" action="/anonymous/tool-record/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.notice.list" action="/administrator/notice/list"/>
			<acme:menu-suboption code="master.menu.administrator.customization-parameter.show" action="/administrator/customization-parameter/show"/>
			<acme:menu-suboption code="master.menu.administrator.dashboard.show" action="/administrator/dashboard/show"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.inquirie.list" action="/administrator/inquirie/list"/>		
			<acme:menu-suboption code="master.menu.administrator.overture.list" action="/administrator/overture/list"/>		
			<acme:menu-suboption code="master.menu.administrator.technology-record.list" action="/administrator/technology-record/list"/>		
			<acme:menu-suboption code="master.menu.administrator.tool-record.list" action="/administrator/tool-record/list"/>		
			<acme:menu-suboption code="master.menu.administrator.challenge.list" action="/administrator/challenge/list"/>
			<acme:menu-suboption code="master.menu.administrator.bookkeeper-request.list" action="/administrator/bookkeeper-request/list"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.notice.create" action="/administrator/notice/create"/>
			<acme:menu-suboption code="master.menu.administrator.inquirie.create" action="/administrator/inquirie/create"/>
			<acme:menu-suboption code="master.menu.administrator.overture.create" action="/administrator/overture/create"/>
			<acme:menu-suboption code="master.menu.administrator.technology-record.create" action="/administrator/technology-record/create"/>			
			<acme:menu-suboption code="master.menu.administrator.tool-record.create" action="/administrator/tool-record/create"/>			
			<acme:menu-suboption code="master.menu.administrator.challenge.create" action="/administrator/challenge/create"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shutdown" action="/master/shutdown"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.authenticated" access="hasRole('Authenticated')">
		    <acme:menu-suboption code="master.menu.authenticated.notice.list" action="/authenticated/notice/list"/>
			<acme:menu-suboption code="master.menu.authenticated.technology-record.list" action="/authenticated/technology-record/list"/>
			<acme:menu-suboption code="master.menu.authenticated.tool-record.list" action="/authenticated/tool-record/list"/>
			<acme:menu-suboption code="master.menu.authenticated.inquirie.list" action="/authenticated/inquirie/list"/>
			<acme:menu-suboption code="master.menu.authenticated.overture.list" action="/authenticated/overture/list"/>		
			<acme:menu-suboption code="master.menu.authenticated.challenge.list" action="/authenticated/challenge/list"/>
		    <acme:menu-suboption code="master.menu.authenticated.investment-round.list" action="/authenticated/investment-round/list"/>
		    <acme:menu-suboption code="master.menu.authenticated.forum.list" action="/authenticated/forum/list"/>
		</acme:menu-option>
				
		<acme:menu-option code="master.menu.provider" access="hasRole('Provider')">
			<acme:menu-suboption code="master.menu.provider.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.consumer" access="hasRole('Consumer')">
			<acme:menu-suboption code="master.menu.consumer.favourite-link" action="http://www.example.com/"/>
		</acme:menu-option>
		
		<acme:menu-option code="master.menu.entrepeneur" access="hasRole('Entrepeneur')">
		    <acme:menu-suboption code="master.menu.entrepeneur.investment-round.list" action="/entrepeneur/investment-round/list"/>
		    <acme:menu-suboption code="master.menu.entrepeneur.investment-round.create" action="/entrepeneur/investment-round/create"/>
		    <acme:menu-suboption code="master.menu.entrepeneur.application.list" action="/entrepeneur/application/list"/>
		</acme:menu-option>
	
		<acme:menu-option code="master.menu.investor" access="hasRole('Investor')">
		    <acme:menu-suboption code="master.menu.investor.investment-round.list" action="/investor/investment-round/list"/>
		    <acme:menu-suboption code="master.menu.investor.application.list" action="/investor/application/list"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.bookkeeper" access="hasRole('Bookkeeper')">
		    <acme:menu-suboption code="master.menu.bookkeeper.investment-round.list.written" action="/bookkeeper/investment-round/list-written"/>
		    <acme:menu-suboption code="master.menu.bookkeeper.investment-round.list.not-written" action="/bookkeeper/investment-round/list-not-written"/>
		</acme:menu-option>	
		
		<acme:menu-option code="master.menu.patron" access="hasRole('Patron')">
		    <acme:menu-suboption code="master.menu.patron.banner.list" action="/patron/banner/list"/>
		   	<acme:menu-suboption code="master.menu.patron.banner.create" action="/patron/banner/create"/>
		</acme:menu-option>
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-provider" action="/authenticated/provider/create" access="!hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.provider" action="/authenticated/provider/update" access="hasRole('Provider')"/>
			<acme:menu-suboption code="master.menu.user-account.become-consumer" action="/authenticated/consumer/create" access="!hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.consumer" action="/authenticated/consumer/update" access="hasRole('Consumer')"/>
			<acme:menu-suboption code="master.menu.user-account.become-entrepeneur" action="/authenticated/entrepeneur/create" access="!hasRole('Entrepeneur')"/>
			<acme:menu-suboption code="master.menu.user-account.entrepeneur" action="/authenticated/entrepeneur/update" access="hasRole('Entrepeneur')"/>
			<acme:menu-suboption code="master.menu.user-account.become-investor" action="/authenticated/investor/create" access="!hasRole('Investor')"/>
			<acme:menu-suboption code="master.menu.user-account.investor" action="/authenticated/investor/update" access="hasRole('Investor')"/>
			<acme:menu-suboption code="master.menu.user-account.become-bookkeeper-request" action="/authenticated/bookkeeper-request/create" access="!hasRole('BookkeeperRequest') && !hasRole('Bookkeeper')"/>
			<acme:menu-suboption code="master.menu.user-account.bookkeeper-request" action="/authenticated/bookkeeper-request/update" access="hasRole('BookkeeperRequest') && !hasRole('Bookkeeper')"/>
			<acme:menu-suboption code="master.menu.user-account.bookkeeper" action="/authenticated/bookkeeper/update" access="hasRole('Bookkeeper')"/>
			<acme:menu-suboption code="master.menu.user-account.become-patron" action="/authenticated/patron/create" access="!hasRole('Patron')"/>
			<acme:menu-suboption code="master.menu.user-account.patron" action="/authenticated/patron/update" access="hasRole('Patron')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

