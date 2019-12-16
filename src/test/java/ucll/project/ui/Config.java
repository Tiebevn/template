package ucll.project.ui;

public class Config {
    public static String baseURL() {
        if (IsRunningInsideDocker.isRunningInsideDocker()) {
            return "https://dev.template.projectweek.be/";
        } else {
            return "http://localhost:8080";
        }
    }



}
