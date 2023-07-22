package sparta.kingdombe.domain.job.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.job.dto.JobRequestDto;
import sparta.kingdombe.global.utils.Timestamped;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "jobinfo")
public class JobInfo extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Date recruitmentPeriod;
    //모집기간 String 인가 Date 인가

    @Column(nullable = false)
    private String managerName;

    @Column(unique = true, nullable = false)
    private String managerEmail;

    @Column(nullable = false)
    private Long recruitmentPersonNum;

    @Column(nullable = false)
    private Long salary;

    @Column
    private String companyName;
//추가사항

    @Column
    private String images;


    public JobInfo(JobRequestDto jobRequestDto) {
        this.title = jobRequestDto.getTitle();
        this.location = jobRequestDto.getLocation();
        this.content = jobRequestDto.getContent();
        this.recruitmentPeriod = jobRequestDto.getRecruitmentPeriod();
        this.managerName = jobRequestDto.getManagerName();
        this.managerEmail = jobRequestDto.getManagerEmail();
        this.recruitmentPersonNum = jobRequestDto.getRecruitmentPersonNum();
        this.salary=jobRequestDto.getSalary();
        this.companyName = jobRequestDto.getCompanyName();
    }
}

