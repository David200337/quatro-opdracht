package src.domain;

public class Certificate {
    private int certificateId;
    private String staffName;
    private int grade;

    public Certificate(int certificateId, String staffName, int grade) {
        this.setCertificateId(certificateId);
        this.setStaffName(staffName);
        this.setGrade(grade);
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int certificateId) {
        this.certificateId = certificateId;
    }
}
