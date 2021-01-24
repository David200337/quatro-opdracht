package src.domain;

public class ViewStatistics {
    private String moduleTitle;
    private int versionNr;
    private String webcastTitle;
    private double percentage;

    public ViewStatistics(String moduleTitle, int versionNr, double percentage) {
        this.setModuleTitle(moduleTitle);
        this.setVersionNr(versionNr);
        this.setPercentage(percentage);
    }
    public ViewStatistics(String webcastTitle, double percentage) {
        this.setWebcastTitle(webcastTitle);
        this.setPercentage(percentage);
    }

    public String getWebcastTitle() {
        return webcastTitle;
    }

    public void setWebcastTitle(String webcastTitle) {
        this.webcastTitle = webcastTitle;
    }

    

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getVersionNr() {
        return versionNr;
    }

    public void setVersionNr(int versionNr) {
        this.versionNr = versionNr;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

}
