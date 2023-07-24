package sparta.kingdombe.domain.story.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sparta.kingdombe.domain.comment.dto.CommentResponseDto;
import sparta.kingdombe.domain.story.dto.StoryRequestDto;
import sparta.kingdombe.domain.story.dto.StoryResponseDto;
import sparta.kingdombe.domain.story.entity.Story;
import sparta.kingdombe.domain.story.repository.StoryRepository;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.global.responseDto.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

import static sparta.kingdombe.global.utils.ResponseUtils.ok;

@Service
@RequiredArgsConstructor
@Transactional
public class StoryService {

    private final StoryRepository storyRepository;
    private final S3Service s3Service;


    public ApiResponse<?> findAllStory() {

        List<StoryResponseDto> result = storyRepository.findAll()
                .stream()
                .map(story -> StoryResponseDto.builder()
                        .id(story.getId())
                        .title(story.getTitle())
                        .content(story.getContent())
                        .liked(story.getLiked())
                        .username(story.getUser().getUsername())
                        .createdAt(story.getCreatedAt())
                        .viewCount(story.getViewCount())
                        .commentList(story.getCommentList().stream().map(comment -> new CommentResponseDto(comment)).toList())
                        .build())
                .collect(Collectors.toList());
        ;
        return ok(result);
    }

    public ApiResponse<?> findOnePost(Long storyId) {
        Story story = findStory(storyId);
        StoryResponseDto storyResponseDto = new StoryResponseDto(story);
        return ok(storyResponseDto);
    }

    public ApiResponse<?> createStory(StoryRequestDto storyRequestDto, MultipartFile file, User user) {
        String image = s3Service.upload(file);
        Story story = new Story(storyRequestDto, image, user);
        storyRepository.save(story);
        return ok(new StoryResponseDto(story));
    }

    public ApiResponse<?> updateStory(Long storyId, StoryRequestDto storyRequestDto, MultipartFile file, User user) {
        Story story = confirmStory(storyId, user);
        updateStoryDetail(storyRequestDto, file, story);
        StoryResponseDto storyResponseDto = new StoryResponseDto(story);
        return ok(storyResponseDto);
    }

    public ApiResponse<?> deleteStory(Long storyId, User user) {
        Story story = confirmStory(storyId, user);
        deleteImage(story);
        storyRepository.delete(story);
        return ok("삭제 완료");
    }

    private void updateStoryDetail(StoryRequestDto storyRequestDto, MultipartFile image, Story story) {
        if (image != null && !image.isEmpty()) {
            String existingImageUrl = story.getImage();
            String imageUrl = s3Service.upload(image);
            story.updateAll(storyRequestDto, imageUrl);

            // 새로운 이미지 업로드 후에 기존 이미지 삭제
            if (StringUtils.hasText(existingImageUrl)) {
                s3Service.delete(existingImageUrl);
            }
            story.update(storyRequestDto);
        }
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
        story.increaseViewCount();
        return story;
    }

    private Story confirmStory(Long storyId, User user) {
        Story story = findStory(storyId);
        if (!user.getId().equals(story.getUser().getId())) {
            throw new IllegalArgumentException("게시글 작성자만 수정,삭제할 수 있습니다");
        }
        return story;
    }


}
