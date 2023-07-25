package sparta.kingdombe.domain.home.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.job.entity.JobInfo;
import sparta.kingdombe.global.utils.Timestamped;

@Entity
@Getter
@NoArgsConstructor
public class Home extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String logoImage;

    private String companyname;

    private String title;

    private String location;

    private long salary;

    private String workInfraImage;

    private String jobdetailurl;

    @OneToMany(mappedBy = "jobinfo")
    private JobInfo jobInfo;

}
