
package acme.features.administrator.dashboard;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.forms.Dashboard;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.datatypes.Money;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorDashboardShowService implements AbstractShowService<Administrator, Dashboard> {

	@Autowired
	private AdministratorDashboardRepository repository;


	@Override
	public boolean authorise(final Request<Dashboard> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<Dashboard> request, final Dashboard entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "totalNumberOfNotices", "totalNumberOfTechnologyRecords", "totalNumberOfToolRecords", "minimumMoneyIntervalsOfActiveInquiries", "maximunMoneyIntervalsOfActiveInquiries", "averageMoneyIntervalsOfActiveInquiries",
			"standardDeviationMoneyIntervalsOfActiveInquiries", "minimumMoneyIntervalsOfActiveOvertures", "maximunMoneyIntervalsOfActiveOvertures", "averageMoneyIntervalsOfActiveOvertures", "standardDeviationMoneyIntervalsOfActiveOvertures",
			"ratioOfOpenSourceTechnologiesVSClosedSourceTechnologies", "ratioOfOpenSourceToolsVSClosedSourceTools", "chartTechnologyTool", "averageNumberOfInvestmentRoundPerEntrepeneur", "averageNumberOfApplicationsPerEntrepeneur",
			"averageNumberOfApplicationsPerInvestor", "chartInvestmentApplications", "chartTimeSeriesApplication");
	}

	@Override
	public Dashboard findOne(final Request<Dashboard> request) {
		assert request != null;

		Dashboard result = new Dashboard();

		//D02
		result.setTotalNumberOfNotices(this.repository.totalNumberOfNotices());
		result.setTotalNumberOfTechnologyRecords(this.repository.totalNumberOfTechnologyRecords());
		result.setTotalNumberOfToolRecords(this.repository.totalNumberOfToolRecords());

		Money minimumMoneyIntervalsOfActiveInquiries = new Money();
		minimumMoneyIntervalsOfActiveInquiries.setAmount(this.repository.minimumMoneyIntervalsOfActiveInquiries());
		minimumMoneyIntervalsOfActiveInquiries.setCurrency("€");
		Money maximunMoneyIntervalsOfActiveInquiries = new Money();
		maximunMoneyIntervalsOfActiveInquiries.setAmount(this.repository.maximunMoneyIntervalsOfActiveInquiries());
		maximunMoneyIntervalsOfActiveInquiries.setCurrency("€");
		Money averageMoneyIntervalsOfActiveInquiries = new Money();
		double avgInquiries = this.repository.averageMoneyIntervalsOfActiveInquiries();
		averageMoneyIntervalsOfActiveInquiries.setAmount(avgInquiries);
		averageMoneyIntervalsOfActiveInquiries.setCurrency("€");
		Money standardDeviationMoneyIntervalsOfActiveInquiries = new Money();
		double standardDeviationSumInquiries = this.repository.standardDeviationSumMoneyIntervalsOfActiveInquiries(avgInquiries);
		standardDeviationMoneyIntervalsOfActiveInquiries.setAmount(this.repository.standardDeviationMoneyIntervalsOfActiveInquiries(standardDeviationSumInquiries));
		standardDeviationMoneyIntervalsOfActiveInquiries.setCurrency("€");

		result.setMinimumMoneyIntervalsOfActiveInquiries(minimumMoneyIntervalsOfActiveInquiries);
		result.setMaximunMoneyIntervalsOfActiveInquiries(maximunMoneyIntervalsOfActiveInquiries);
		result.setAverageMoneyIntervalsOfActiveInquiries(averageMoneyIntervalsOfActiveInquiries);
		result.setStandardDeviationMoneyIntervalsOfActiveInquiries(standardDeviationMoneyIntervalsOfActiveInquiries);

		Money minimumMoneyIntervalsOfActiveOvertures = new Money();
		minimumMoneyIntervalsOfActiveOvertures.setAmount(this.repository.minimumMoneyIntervalsOfActiveOvertures());
		minimumMoneyIntervalsOfActiveOvertures.setCurrency("€");
		Money maximunMoneyIntervalsOfActiveOvertures = new Money();
		maximunMoneyIntervalsOfActiveOvertures.setAmount(this.repository.maximunMoneyIntervalsOfActiveOvertures());
		maximunMoneyIntervalsOfActiveOvertures.setCurrency("€");
		Money averageMoneyIntervalsOfActiveOvertures = new Money();
		double avgOvertures = this.repository.averageMoneyIntervalsOfActiveOvertures();
		averageMoneyIntervalsOfActiveOvertures.setAmount(avgOvertures);
		averageMoneyIntervalsOfActiveOvertures.setCurrency("€");
		Money standardDeviationMoneyIntervalsOfActiveOvertures = new Money();
		double standardDeviationSumOvertures = this.repository.standardDeviationSumMoneyIntervalsOfActiveOvertures(avgOvertures);
		standardDeviationMoneyIntervalsOfActiveOvertures.setAmount(this.repository.standardDeviationMoneyIntervalsOfActiveOvertures(standardDeviationSumOvertures));
		standardDeviationMoneyIntervalsOfActiveOvertures.setCurrency("€");

		result.setMinimumMoneyIntervalsOfActiveOvertures(minimumMoneyIntervalsOfActiveOvertures);
		result.setMaximunMoneyIntervalsOfActiveOvertures(maximunMoneyIntervalsOfActiveOvertures);
		result.setAverageMoneyIntervalsOfActiveOvertures(averageMoneyIntervalsOfActiveOvertures);
		result.setStandardDeviationMoneyIntervalsOfActiveOvertures(standardDeviationMoneyIntervalsOfActiveOvertures);

		result.setTotalNumberOfTechnologiesGroupedByActivitySector(this.repository.totalNumberOfTechnologiesGroupedByActivitySector());
		result.setRatioOfOpenSourceTechnologiesVSClosedSourceTechnologies(this.repository.ratioOfOpenSourceTechnologiesVSClosedSourceTechnologies());
		result.setTotalNumberOfToolsGroupedByActivitySector(this.repository.totalNumberOfToolsGroupedByActivitySector());
		result.setRatioOfOpenSourceToolsVSClosedSourceTools(this.repository.ratioOfOpenSourceToolsVSClosedSourceTools());
		result.setChartTechnologyTool(this.generateChartTechnologyTools());

		//D04
		result.setAverageNumberOfInvestmentRoundPerEntrepeneur(this.repository.averageNumberOfInvestmentRoundPerEntrepeneur());
		result.setAverageNumberOfApplicationsPerEntrepeneur(this.repository.averageNumberOfApplicationsPerEntrepeneur());
		result.setAverageNumberOfApplicationsPerInvestor(this.repository.averageNumberOfApplicationsPerInvestor());
		result.setChartInvestmentApplications(this.generateChartInvestmentApplication());

		//D05
		result.setChartTimeSeriesApplication(this.generateChartTimeSeriesApplication());
		return result;
	}

	private List<List<String>> generateChartTechnologyTools() {
		List<List<String>> chart = new ArrayList<>();

		Collection<String> labels = new TreeSet<>();
		List<String> technology_labels = new ArrayList<>();
		List<String> tool_labels = new ArrayList<>();

		List<String> technology_data = new ArrayList<>();
		List<String> tool_data = new ArrayList<>();

		for (String[] item : this.repository.totalNumberOfTechnologiesGroupedByActivitySector()) {
			labels.add(item[0]);
			technology_labels.add(item[0]);
		}

		for (String[] item : this.repository.totalNumberOfToolsGroupedByActivitySector()) {
			if (labels.contains(item[0])) {
			} else {
				labels.add(item[0]);
			}

			tool_labels.add(item[0]);
		}

		labels.add("Ratio of Open-Source versus Closed-Source");

		for (String label : labels) {
			if (technology_labels.contains(label)) {
				for (String[] cr : this.repository.totalNumberOfTechnologiesGroupedByActivitySector()) {
					if (label.equals(cr[0])) {
						technology_data.add(cr[1]);
					}
				}
			} else if (label.equals("Ratio of Open-Source versus Closed-Source")) {
				technology_data.add(String.valueOf(this.repository.ratioOfOpenSourceTechnologiesVSClosedSourceTechnologies()));
			} else {
				technology_data.add("0");
			}
		}

		for (String label : labels) {
			if (tool_labels.contains(label)) {
				for (String[] cr : this.repository.totalNumberOfToolsGroupedByActivitySector()) {
					if (label.equals(cr[0])) {
						tool_data.add(cr[1]);
					}
				}
			} else if (label.equals("Ratio of Open-Source versus Closed-Source")) {
				tool_data.add(String.valueOf(this.repository.ratioOfOpenSourceToolsVSClosedSourceTools()));
			} else {
				tool_data.add("0");
			}
		}

		chart.add(new ArrayList<>(labels));
		chart.add(technology_data);
		chart.add(tool_data);

		return chart;
	}

	private List<List<String>> generateChartInvestmentApplication() {
		List<List<String>> chart = new ArrayList<>();

		Collection<String> labels_investment = new TreeSet<>(Arrays.asList("SEED", "ANGEL", "SERIES-A", "SERIES-B", "SERIES-C", "BRIDGE"));
		List<String> data_investment = new ArrayList<>(Arrays.asList("0", "0", "0", "0", "0", "0"));

		for (int i = 0; i < labels_investment.size(); i++) {
			List<String> labelsList = new ArrayList<>(labels_investment);

			for (String[] cr : this.repository.ratioOfInvestmentRoundGroupedByKind()) {
				if (labelsList.get(i).equals(cr[0])) {
					data_investment.set(i, cr[1]);
				}
			}
		}

		chart.add(new ArrayList<>(labels_investment));
		chart.add(data_investment);

		Collection<String> labels_application = new TreeSet<>(Arrays.asList("PENDING", "ACCEPTED", "REJECTED"));
		List<String> data_application = new ArrayList<>(Arrays.asList("0", "0", "0"));

		for (int i = 0; i < labels_application.size(); i++) {
			List<String> labelsList = new ArrayList<>(labels_application);

			for (String[] cr : this.repository.ratioOfApplicationsRoundGroupedByStatus()) {
				if (labelsList.get(i).toLowerCase().equals(cr[0])) {
					data_application.set(i, cr[1]);
				}
			}
		}

		chart.add(new ArrayList<>(labels_application));
		chart.add(data_application);

		return chart;
	}

	private List<List<String>> generateChartTimeSeriesApplication() {
		List<List<String>> chart = new ArrayList<>();

		List<String> labels = new ArrayList<>();
		List<String> data_accepted = new ArrayList<>();
		List<String> data_rejected = new ArrayList<>();
		List<String> data_pending = new ArrayList<>();

		LocalDate localDate = LocalDate.now().minusDays(15 - 1);
		Date queryDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
		this.repository.numberOfApplicationsPerDayByStatus("accepted", queryDate);

		for (int i = 0; i < 15; i++) {
			String dateFormatted = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(LocalDate.now().minusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant()));
			labels.add(dateFormatted);
			data_accepted.add("0");
			data_rejected.add("0");
			data_pending.add("0");
		}

		for (int i = 0; i < labels.size(); i++) {
			List<String> labelsList = new ArrayList<>(labels);
			for (String[] cr : this.repository.numberOfApplicationsPerDayByStatus("accepted", queryDate)) {
				if (String.valueOf(cr[0]).substring(0, 10).equals(labelsList.get(i).substring(0, 10))) {
					data_accepted.set(i, cr[1]);

				}
			}
		}

		for (int i = 0; i < labels.size(); i++) {
			List<String> labelsList = new ArrayList<>(labels);
			for (String[] cr : this.repository.numberOfApplicationsPerDayByStatus("rejected", queryDate)) {
				if (String.valueOf(cr[0]).substring(0, 10).equals(labelsList.get(i).substring(0, 10))) {
					data_rejected.set(i, cr[1]);

				}
			}
		}

		for (int i = 0; i < labels.size(); i++) {
			List<String> labelsList = new ArrayList<>(labels);
			for (String[] cr : this.repository.numberOfApplicationsPerDayByStatus("pending", queryDate)) {
				if (String.valueOf(cr[0]).substring(0, 10).equals(labelsList.get(i).substring(0, 10))) {
					data_pending.set(i, cr[1]);
				}
			}
		}

		chart.add(new ArrayList<>(labels));
		chart.add(data_accepted);
		chart.add(data_rejected);
		chart.add(data_pending);

		return chart;
	}
}
