package sparta.kingdombe.domain.home.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.kingdombe.domain.home.dto.HomeJobResponseDto;
import sparta.kingdombe.domain.home.dto.HomeStoryResponseDto;
import sparta.kingdombe.domain.job.entity.JobInfo;
import sparta.kingdombe.domain.job.repository.JobRepository;
import sparta.kingdombe.domain.story.entity.Story;
import sparta.kingdombe.domain.story.repository.StoryRepository;
import sparta.kingdombe.global.responseDto.ApiResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class HomeService {

    private final JobRepository jobRepository;
    private final StoryRepository storyRepository;

    public ApiResponse<?> getHome() throws IOException {
        Pageable pageable = PageRequest.of(0,40);

        List<HomeJobResponseDto> result = jobRepository.findAllByOrderByCreatedAtDesc(pageable)
                .stream()
                .map(HomeJobResponseDto::new)
                .collect(Collectors.toList());

        return new ApiResponse<>(true, result, null);
    }

    public ApiResponse<?> getStory() throws IOException {
        Pageable pageable = PageRequest.of(0,10);
        Page<Story> homeList = storyRepository.findAllByOrderByCreatedAtDesc(pageable);


        List<HomeStoryResponseDto> result = homeList.getContent()
                .stream()
                .map(HomeStoryResponseDto::new)
                .collect(Collectors.toList());
        return new ApiResponse<>(true, result, null);
    }
}
