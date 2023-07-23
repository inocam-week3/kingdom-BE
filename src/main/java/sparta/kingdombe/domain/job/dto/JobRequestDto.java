package sparta.kingdombe.domain.job.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class JobRequestDto {

    String companyname;
    String title;
    String content;
    String location;
    Long salary;
    Date recruitmentstartperiod;
    Date recruitmentendperiod;
    Long recruitmentpersonnum;

}
