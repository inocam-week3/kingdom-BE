package sparta.kingdombe.domain.home.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.job.entity.JobInfo;

@Getter
@NoArgsConstructor
public class HomeJobResponseDto {

    private Long id;
    private String logoImage;
    private String companyname;
    private String title;
    private String location;
    private long salary;
    private String workInfraImage;
    private String jobdetailurl;

    public HomeJobResponseDto(JobInfo jobInfo) {
        this.id = jobInfo.getId();
        this.logoImage = jobInfo.getLogoImage();
        this.companyname = jobInfo.getCompanyname();
        this.title = jobInfo.getTitle();
        this.location = jobInfo.getLocation();
        this.salary = jobInfo.getSalary();
        this.workInfraImage = jobInfo.getWorkInfraImage();
        this.jobdetailurl = "http://3.34.136.177/api/job/" + jobInfo.getId();
    }
}
