package com.abeer.store.repository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.abeer.store.entity.User;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    public Optional<User> findByUsername(String username);
    public boolean existsByEmail(String email);
    public boolean existsByUsername(String username);
}
