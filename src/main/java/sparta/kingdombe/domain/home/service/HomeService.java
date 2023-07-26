package sparta.kingdombe.domain.home.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.kingdombe.domain.home.dto.HomeJobResponseDto;
import sparta.kingdombe.domain.home.dto.HomeStoryResponseDto;
import sparta.kingdombe.domain.job.repository.JobRepository;
import sparta.kingdombe.domain.story.repository.StoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeService {

    private final JobRepository jobRepository;
    private final StoryRepository storyRepository;

    public List<HomeJobResponseDto> getJobInfoAtHome() {

        List<HomeJobResponseDto> result = jobRepository.findJobInfoAtHome()
                .stream()
                .map(HomeJobResponseDto::new)
                .collect(Collectors.toList());

        return result;
    }

    public List<HomeStoryResponseDto> getStoryAtHome() {

        List<HomeStoryResponseDto> result = storyRepository.findStoryAtHome()
                .stream()
                .map(HomeStoryResponseDto::new)
                .collect(Collectors.toList());

        return result;
    }
}
