package testing;

import org.junit.Test;

public class ContributionTest {
    private final TableContributionFund contribution = new TableContributionFund();
    @Test
    public void containsAddedFund(){
        ContributionFund contributionFund = new ContributionFund();
        contribution.add(contributionFund);
        assertTrue(contribution.contains(contributionFund));
    }


}
