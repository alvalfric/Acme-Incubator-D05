<%@page language="java"%>
<%@taglib prefix="jstl" uri ="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir ="/WEB-INF/tags"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<acme:form readonly="true">
	<acme:form-textbox code="administrator.dashboard.label.totalNumberOfNotices" path="totalNumberOfNotices" />
	<acme:form-textbox code="administrator.dashboard.label.totalNumberOfTechnologyRecords" path="totalNumberOfTechnologyRecords" />
	<acme:form-textbox code="administrator.dashboard.label.totalNumberOfToolRecords" path="totalNumberOfToolRecords" />
	<acme:form-textbox code="administrator.dashboard.label.minimumMoneyIntervalsOfActiveInquiries" path="minimumMoneyIntervalsOfActiveInquiries" />
	<acme:form-textbox code="administrator.dashboard.label.maximunMoneyIntervalsOfActiveInquiries" path="maximunMoneyIntervalsOfActiveInquiries" />
	<acme:form-textbox code="administrator.dashboard.label.averageMoneyIntervalsOfActiveInquiries" path="averageMoneyIntervalsOfActiveInquiries" />
	<acme:form-textbox code="administrator.dashboard.label.standardDeviationMoneyIntervalsOfActiveInquiries" path="standardDeviationMoneyIntervalsOfActiveInquiries" />
	<acme:form-textbox code="administrator.dashboard.label.minimumMoneyIntervalsOfActiveOvertures" path="minimumMoneyIntervalsOfActiveOvertures" />
	<acme:form-textbox code="administrator.dashboard.label.maximunMoneyIntervalsOfActiveOvertures" path="maximunMoneyIntervalsOfActiveOvertures" />
	<acme:form-textbox code="administrator.dashboard.label.averageMoneyIntervalsOfActiveOvertures" path="averageMoneyIntervalsOfActiveOvertures" />
	<acme:form-textbox code="administrator.dashboard.label.standardDeviationMoneyIntervalsOfActiveOvertures" path="standardDeviationMoneyIntervalsOfActiveOvertures" />

<b><acme:message code="administrator.dashboard.chart.label.technologyTool"/></b>
<canvas id="chartTechnologyTool" width="250" height="50"></canvas>
<script>
	var ctx = document.getElementById("chartTechnologyTool").getContext('2d');
	var chartTechnologyTool = new Chart(ctx, {
		type : 'bar',
		data : {
			labels : [
				"<jstl:out value="${chartTechnologyTool.get(0).get(0)}" escapeXml="false"/>",
				<jstl:forEach var="label" items="${chartTechnologyTool.get(0)}" begin="1">
					"<jstl:out value="${label}" escapeXml="false"/>",
				</jstl:forEach>
			],
			datasets : [
				{
					data : [
						<jstl:out value="${Double.parseDouble(chartTechnologyTool.get(1).get(0))}" escapeXml="false"/>
						<jstl:forEach var="technologyRecord" items="${chartTechnologyTool.get(1)}" begin="1">
							,<jstl:out value="${Double.parseDouble(technologyRecord)}" escapeXml="false"/>
						</jstl:forEach>
					],
					label: 'Technology Records/Registros Tecnológicos',
					backgroundColor : 'rgba(255, 99, 132, 0.2)',
					borderColor : 'rgba(255,99,132,1)',
					borderWidth : 1
				},
				{
					data : [
						<jstl:out value="${Double.parseDouble(chartTechnologyTool.get(2).get(0))}" escapeXml="false"/>
						<jstl:forEach var="toolRecord" items="${chartTechnologyTool.get(2)}" begin="1">
							,<jstl:out value="${Double.parseDouble(toolRecord)}" escapeXml="false"/>
						</jstl:forEach>
					],
					label: 'Tool Records/Registros de Herramientas',
					backgroundColor : 'rgba(54, 162, 235, 0.2)',
					borderColor : 'rgba(54, 162, 235, 1)',
					borderWidth : 1
				}
			]
		},
		options : {
			scales : {
				yAxes : [
					{
						ticks : {
							suggestedMin : 0.0,
							suggestedMax : 1.0
						}
					}
				]
			},
			legend : {
				display : true
			}
		}
	});
</script>
	<acme:form-textbox code="administrator.dashboard.label.averageNumberOfInvestmentRoundPerEntrepeneur" path="averageNumberOfInvestmentRoundPerEntrepeneur" />
	<acme:form-textbox code="administrator.dashboard.label.averageNumberOfApplicationsPerEntrepeneur" path="averageNumberOfApplicationsPerEntrepeneur" />
	<acme:form-textbox code="administrator.dashboard.label.averageNumberOfApplicationsPerInvestor" path="averageNumberOfApplicationsPerInvestor" />

<b><acme:message code="administrator.dashboard.chart.label.chartInvestment"/></b>
<canvas id="chartInvestmentApplication" width="250" height="50"></canvas>
<b><acme:message code="administrator.dashboard.chart.label.chartApplication"/></b>
<canvas id="chartApplication" width="250" height="50"></canvas>
<script>
	var ctx = document.getElementById("chartInvestmentApplication").getContext('2d');
	var chartInvestmentApplication = new Chart(ctx, {
		type : 'pie',
		data : {
			labels : [
				"<jstl:out value="${chartInvestmentApplications.get(0).get(0)}" escapeXml="false"/>",
				<jstl:forEach var="label" items="${chartInvestmentApplications.get(0)}" begin="1">
					"<jstl:out value="${label}" escapeXml="false"/>",
				</jstl:forEach>
			],
			datasets : [
				{
					data : [
						<jstl:out value="${Double.parseDouble(chartInvestmentApplications.get(1).get(0))}" escapeXml="false"/>
						<jstl:forEach var="investment" items="${chartInvestmentApplications.get(1)}" begin="1">
							,<jstl:out value="${Double.parseDouble(investment)}" escapeXml="false"/>
						</jstl:forEach>
					],
					label: 'Investment Rounds/Registros de Inversión',
					backgroundColor : [
						'rgba(255, 99, 132)',
						'rgba(255, 175, 132)',
						'rgba(255, 252, 132)',
						'rgba(175, 252, 132)',
						'rgba(99, 252, 132)',
						'rgba(99, 252, 252)'
					]		
				}
			]
		},
		options : {
			legend : {
				display : true
			}
		}
	});
</script>
<script>
	var ctx = document.getElementById("chartApplication").getContext('2d');
	var chartApplication = new Chart(ctx, {
		type : 'pie',
		data : {
			labels : [
				"<jstl:out value="${chartInvestmentApplications.get(2).get(0)}" escapeXml="false"/>",
				<jstl:forEach var="label" items="${chartInvestmentApplications.get(2)}" begin="1">
					"<jstl:out value="${label}" escapeXml="false"/>",
				</jstl:forEach>
			],
			datasets : [
				{
					data : [
						<jstl:out value="${Double.parseDouble(chartInvestmentApplications.get(3).get(0))}" escapeXml="false"/>
						<jstl:forEach var="investment" items="${chartInvestmentApplications.get(3)}" begin="1">
							,<jstl:out value="${Double.parseDouble(investment)}" escapeXml="false"/>
						</jstl:forEach>
					],
					label: 'Applications/Aplicaciones',
					backgroundColor : [
						'rgba(99, 252, 132)',
						'rgba(224, 224, 224)',
						'rgba(255, 99, 132)'
					]		
				}
			]
		},
		options : {
			legend : {
				display : true
			}
		}
	});
</script>

<b><acme:message code="administrator.dashboard.chart.label.chartTimeSeriesApplication"/></b>
<canvas id="chartTimeSeriesApplication" width="250" height="80"></canvas>
<script>
new Chart(document.getElementById("chartTimeSeriesApplication"), {
	  type: 'line',
	  data: {
	    labels: [
			<jstl:forEach var="label" items="${chartTimeSeriesApplication.get(0)}" begin="0">
				"<jstl:out value="${label}" escapeXml="false"/>",
			</jstl:forEach>
		],
	    datasets: [{ 
	        data: [
				<jstl:forEach var="data" items="${chartTimeSeriesApplication.get(1)}" begin="0">
				"<jstl:out value="${data}" escapeXml="false"/>",
				</jstl:forEach>
	        	],
	        label: "ACCEPTED",
	        backgroundColor: 'rgba(99, 252, 132)',
	        borderColor: 'rgba(99, 252, 132)',
	        fill: false
	      }, { 
	        data: [
				<jstl:forEach var="data" items="${chartTimeSeriesApplication.get(3)}" begin="0">
				"<jstl:out value="${data}" escapeXml="false"/>",
				</jstl:forEach>
	        	],
	        label: "PENDING",
	        backgroundColor: 'rgba(224, 224, 224)',
	        borderColor: 'rgba(224, 224, 224)',
	        fill: false
	      }, { 
	        data: [
				<jstl:forEach var="data" items="${chartTimeSeriesApplication.get(2)}" begin="0">
				"<jstl:out value="${data}" escapeXml="false"/>",
				</jstl:forEach>
	        	],
	        label: "REJECTED",
	        backgroundColor: 'rgba(255, 99, 132)',
	        borderColor: 'rgba(255, 99, 132)',
	        fill: false
	      }
	    ]
	  },
	  options: {
	        scales: {
	            yAxes: [{
	                ticks: {
	                    stepSize: 1
	                }
	            }]
	        }
	    }
	});
	</script>
	<acme:form-return code="administrator.dashboard.form.button.return"/>
</acme:form>
