package sparta.kingdombe.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.kingdombe.domain.like.entity.Like;
import sparta.kingdombe.domain.like.repository.LikeRepository;
import sparta.kingdombe.domain.story.dto.StoryResponseDto;
import sparta.kingdombe.domain.story.entity.Story;
import sparta.kingdombe.domain.story.repository.StoryRepository;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.global.responseDto.ApiResponse;

import static sparta.kingdombe.global.utils.ResponseUtils.ok;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final StoryRepository storyRepository;

    public ApiResponse<?> updateLike(Long storyId, User user) {
        Story story = storyRepository.findById(storyId).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다"));

        if (!isLikedStory(story, user)) {
            createLike(story, user);
            story.increaseLike();
            return ok(new StoryResponseDto(story));
        }

        removeLike(story, user);
        story.decreaseLike();
        return ok(new StoryResponseDto(story));
    }

    private boolean isLikedStory(Story story, User user) {
        return likeRepository.findByStoryAndUser(story, user).isPresent();
    }

    private void createLike(Story story, User user) {
        Like like = new Like(story, user);
        likeRepository.save(like);
    }

    private void removeLike(Story story, User user) {
        Like like = likeRepository.findByStoryAndUser(story, user).orElseThrow();
        likeRepository.delete(like);
    }
}
