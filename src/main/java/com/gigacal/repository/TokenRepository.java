package com.gigacal.repository;

import com.gigacal.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {

    @Query(value = "SELECT * FROM tokens t " +
            "INNER JOIN users u ON t.user_id = u.id " +
            "WHERE u.id = :userId AND (t.expired = FALSE OR t.revoked = FALSE)", nativeQuery = true)
    List<TokenEntity> findAllValidTokensByUser(Long userId);

    TokenEntity findByToken(String token);
}
