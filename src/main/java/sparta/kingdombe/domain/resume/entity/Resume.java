package sparta.kingdombe.domain.resume.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.resume.dto.ResumeRequestDto;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.global.utils.Timestamped;

@Entity
@Table(name = "resume")
@Getter
@NoArgsConstructor
public class Resume extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String content;

    @Column(nullable = false)
    private String career;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Resume(ResumeRequestDto resumeRequestDto, User user) {
        this.content = resumeRequestDto.getContent();
        this.career = resumeRequestDto.getCareer();
        this.user = user;
    }

    public void update(ResumeRequestDto resumeRequestDto) {
        this.content = resumeRequestDto.getContent();
        this.career = resumeRequestDto.getCareer();
    }
}
