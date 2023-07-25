package sparta.kingdombe.domain.home.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.home.entity.Home;

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

    public HomeJobResponseDto(Home home) {
        this.id = home.getId();
        this.logoImage = home.getLogoImage();
        this.companyname = home.getCompanyname();
        this.title = home.getTitle();
        this.location = home.getLocation();
        this.salary = home.getSalary();
        this.workInfraImage = home.getWorkInfraImage();
        this.jobdetailurl = home.getJobdetailurl();
    }
}
