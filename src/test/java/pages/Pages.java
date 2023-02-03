package pages;
public class Pages {
    LoginPage loginPage;
    DashboardPage dashboardPage;
    JobPage jobPage;

    public LoginPage loginPage() {
        if (loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;

    }

    public DashboardPage dashboardPage() {
        if (dashboardPage == null) {
            dashboardPage = new DashboardPage();
        }
        return dashboardPage;

    }

    public JobPage jobPage() {
        if (jobPage == null) {
            jobPage = new JobPage();
        }
        return jobPage;

    }


}
