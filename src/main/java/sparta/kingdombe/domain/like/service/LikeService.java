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
import sparta.kingdombe.global.exception.systemException.DataNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final StoryRepository storyRepository;

    public StoryResponseDto updateLike(Long storyId, User user) {
        Story story = storyRepository.findById(storyId).orElseThrow(() ->
                new DataNotFoundException("존재하지 않는 게시물입니다"));
        boolean isLike = false;

        if (!isLikedStory(story, user)) {
            createLike(story, user);
            story.increaseLike();
            isLike = true;
            return new StoryResponseDto(story, isLike);
        }

        removeLike(story, user);
        story.decreaseLike();
        return new StoryResponseDto(story, isLike);
    }

    private boolean isLikedStoryId(Long storyId, Long id) {
        return likeRepository.findByStoryIdAndUserId(storyId, id).isPresent();
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
