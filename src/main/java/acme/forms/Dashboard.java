
package acme.forms;

import java.io.Serializable;
import java.util.List;

import acme.framework.datatypes.Money;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dashboard implements Serializable {

	private static final long	serialVersionUID	= 1L;

	//D02
	int							totalNumberOfNotices;
	int							totalNumberOfTechnologyRecords;
	int							totalNumberOfToolRecords;
	Money						minimumMoneyIntervalsOfActiveInquiries;
	Money						maximunMoneyIntervalsOfActiveInquiries;
	Money						averageMoneyIntervalsOfActiveInquiries;
	Money						standardDeviationMoneyIntervalsOfActiveInquiries;
	Money						minimumMoneyIntervalsOfActiveOvertures;
	Money						maximunMoneyIntervalsOfActiveOvertures;
	Money						averageMoneyIntervalsOfActiveOvertures;
	Money						standardDeviationMoneyIntervalsOfActiveOvertures;

	String[][]					totalNumberOfTechnologiesGroupedByActivitySector;
	double						ratioOfOpenSourceTechnologiesVSClosedSourceTechnologies;
	String[][]					totalNumberOfToolsGroupedByActivitySector;
	double						ratioOfOpenSourceToolsVSClosedSourceTools;
	List<List<String>>			chartTechnologyTool;

	//D04
	double						averageNumberOfInvestmentRoundPerEntrepeneur;
	double						averageNumberOfApplicationsPerEntrepeneur;
	double						averageNumberOfApplicationsPerInvestor;

	List<List<String>>			chartInvestmentApplications;

	//D05
	List<List<String>>			chartTimeSeriesApplication;

}
