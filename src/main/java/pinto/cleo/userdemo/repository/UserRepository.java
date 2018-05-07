package pinto.cleo.userdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pinto.cleo.userdemo.entity.UserEntity;

/**
 * Created by cleo on 5/5/18.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
