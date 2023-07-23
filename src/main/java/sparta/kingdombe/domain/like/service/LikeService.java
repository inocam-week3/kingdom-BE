package sparta.kingdombe.domain.like.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sparta.kingdombe.domain.like.repository.LikeRepository;
import sparta.kingdombe.domain.story.entity.Story;
import sparta.kingdombe.domain.story.repository.StoryRepository;
import sparta.kingdombe.domain.user.entity.User;
import sparta.kingdombe.global.responseDto.ApiResponse;
import sparta.kingdombe.global.utils.ResponseUtils;

import static sparta.kingdombe.global.utils.ResponseUtils.*;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final StoryRepository storyRepository;

    public ApiResponse<?> updateLike(Long storyId, User user) {
        Story story = storyRepository.findById(storyId).orElseThrow(()->
                new IllegalArgumentException("해당 게시글은 존재하지 않습니다"));

        if (!isLikedStory(story, user)) {
            createLike(story, user);
            story.increaseLike();
            return ok(story);
        }

        removeLike(story, user);
        story.decreaseLike();
        return ok(story);
    }

    private boolean isLikedPost(Story, User user) {
        return likeRepository.findByStory
    }
}
