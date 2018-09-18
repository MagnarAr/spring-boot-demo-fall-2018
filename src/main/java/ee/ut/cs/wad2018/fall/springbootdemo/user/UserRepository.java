package ee.ut.cs.wad2018.fall.springbootdemo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // SELECT * FROM users
    List<User> findAll();

    @Query(value = "SELECT * FROM users WHERE userId = :userId",
            nativeQuery = true)
    User findByUserId(@Param("userId") Long userId);

}
