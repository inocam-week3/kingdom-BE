package sparta.kingdombe.domain.story.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sparta.kingdombe.domain.image.S3Service;
import sparta.kingdombe.domain.story.dto.StoryRequestDto;
import sparta.kingdombe.domain.story.dto.StoryResponseDto;
import sparta.kingdombe.domain.story.dto.StorySearchCondition;
import sparta.kingdombe.domain.story.entity.Story;
import sparta.kingdombe.domain.story.repository.StoryRepository;
import sparta.kingdombe.domain.user.entity.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StoryService {

    private final StoryRepository storyRepository;
    private final S3Service s3Service;

    @Transactional(readOnly = true)
    public Page<StoryResponseDto> findAllStory(int page) {

        Pageable pageable = PageRequest.of(page - 1, 20, Sort.by(Sort.Direction.DESC, "id"));
        Page<Story> storyList = storyRepository.findAll(pageable);

        List<StoryResponseDto> result = storyList
                .stream()
                .map(story -> new StoryResponseDto().All(story))
                .collect(Collectors.toList());

        int totalPages = storyList.getTotalPages();

        return new PageImpl<>(result, pageable, totalPages);
    }

    public StoryResponseDto findOnePost(Long storyId) {
        Story story = findStory(storyId);
        story.increaseViewCount();
        return new StoryResponseDto(story);
    }

    public StoryResponseDto createStory(StoryRequestDto storyRequestDto, MultipartFile file, User user) {
        String image = s3Service.upload(file);
        Story story = new Story(storyRequestDto, image, user);
        storyRepository.save(story);
        return new StoryResponseDto(story);
    }

    public StoryResponseDto updateStory(Long storyId, StoryRequestDto storyRequestDto, MultipartFile file, User user) {
        Story story = confirmStory(storyId, user);
        updateStoryDetail(storyRequestDto, file, story);
        return new StoryResponseDto(story);
    }

    public String deleteStory(Long storyId, User user) {
        Story story = confirmStory(storyId, user);
        deleteImage(story);
        storyRepository.delete(story);
        return "삭제 완료";
    }

    private void updateStoryDetail(StoryRequestDto storyRequestDto, MultipartFile image, Story story) {
        if (image != null && !image.isEmpty()) {
            String existingImageUrl = story.getImage();
            String imageUrl = s3Service.upload(image);
            story.updateImage(imageUrl);

            // 새로운 이미지 업로드 후에 기존 이미지 삭제
            if (StringUtils.hasText(existingImageUrl)) {
                s3Service.delete(existingImageUrl);
            }
        }
        story.update(storyRequestDto);
    }

    private void deleteImage(Story story) {
        String imageUrl = story.getImage();
        if (StringUtils.hasText(imageUrl)) {
            s3Service.delete(imageUrl);
        }
    }

    private Story findStory(Long storyId) {
        Story story = storyRepository.findById(storyId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다"));
        return story;
    }

    private Story confirmStory(Long storyId, User user) {
        Story story = findStory(storyId);
        if (!user.getId().equals(story.getUser().getId()))
            throw new IllegalArgumentException("게시글 작성자만 수정,삭제할 수 있습니다");
        return story;
    }

    public Page<StoryResponseDto> searchStory(StorySearchCondition condition, Pageable pageable) {
        return storyRepository.searchStory(condition, pageable);
    }
}
