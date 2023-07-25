package sparta.kingdombe.domain.home.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sparta.kingdombe.domain.home.entity.Home;

import java.util.List;

public interface HomeRepository extends JpaRepository<Home, Long> {

}
