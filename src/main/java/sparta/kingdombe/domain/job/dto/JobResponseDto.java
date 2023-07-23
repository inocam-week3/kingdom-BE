package sparta.kingdombe.domain.job.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.job.entity.JobInfo;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class JobResponseDto {
    private Long id;
    private String companyname;
    private String title;
    private String content;
    private String location;
    private String managername;
    private String manageremail;
    private Date recruitstartperiod;
    private Date recruitendperiod;
    private Long recruitpersonnum;
    private Long salary;
    private LocalDateTime createdAt;


    public JobResponseDto(JobInfo jobInfo) {
        this.id = jobInfo.getId();
        this.companyname = jobInfo.getCompanyname();
        this.title = jobInfo.getTitle();
        this.content = jobInfo.getContent();
        this.location = jobInfo.getLocation();
        this.managername = jobInfo.getUser().getUsername();
        this.manageremail = jobInfo.getUser().getEmail();
        this.recruitstartperiod = jobInfo.getRecruitmentStartPeriod();
        this.recruitendperiod = jobInfo.getRecruitmentEndPeriod();
        this.recruitpersonnum = jobInfo.getRecruitmentPersonNum();
        this.salary = jobInfo.getSalary();
        this.createdAt = jobInfo.getCreatedAt();
    }
}
