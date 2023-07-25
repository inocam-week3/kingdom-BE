package sparta.kingdombe.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sparta.kingdombe.domain.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);

    Optional<User> findByKakaoId(Long kakaoId);
}
