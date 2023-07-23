package sparta.kingdombe.domain.job.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.job.dto.JobRequestDto;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.global.utils.Timestamped;

import java.util.Date;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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
    private Date recruitmentStartPeriod;
    //모집기간 String 인가 Date 인가
    @Column(nullable = false)
    private Date recruitmentEndPeriod;
    @Column(nullable = false)
    private Long recruitmentPersonNum;

    @Column(nullable = false)
    private Long salary;

    @Column
    private String companyname;

    @Column
    private String images;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public JobInfo(JobRequestDto jobRequestDto, User user) {
        this.title = jobRequestDto.getTitle();
        this.location = jobRequestDto.getLocation();
        this.content = jobRequestDto.getContent();
        this.recruitmentStartPeriod = jobRequestDto.getRecruitmentstartperiod();
        this.recruitmentEndPeriod = jobRequestDto.getRecruitmentendperiod();
        this.recruitmentPersonNum = jobRequestDto.getRecruitmentpersonnum();
        this.salary = jobRequestDto.getSalary();
        this.companyname = jobRequestDto.getCompanyname();
        this.user = user;
    }

    public void update(JobRequestDto jobRequestDto) {
        this.title = jobRequestDto.getTitle();
        this.location = jobRequestDto.getLocation();
        this.content = jobRequestDto.getContent();
        this.recruitmentStartPeriod = jobRequestDto.getRecruitmentstartperiod();
        this.recruitmentEndPeriod = jobRequestDto.getRecruitmentendperiod();
        this.recruitmentPersonNum = jobRequestDto.getRecruitmentpersonnum();
        this.salary = jobRequestDto.getSalary();
        this.companyname = jobRequestDto.getCompanyname();
    }
}

