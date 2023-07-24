package sparta.kingdombe.domain.resume.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sparta.kingdombe.domain.resume.entity.Resume;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResumeResponseDto {
    private Long id;
    private String username;
    private String gender;
    private String content;
    private String career;
    private LocalDateTime createdAt;

    public ResumeResponseDto(Resume resume) {
        this.id = resume.getId();
        this.username = resume.getUser().getUsername();
        this.gender = resume.getUser().getGender().toStringGender();
        this.content = resume.getContent();
        this.career = resume.getCareer();
        this.createdAt = resume.getCreatedAt();
    }
}
