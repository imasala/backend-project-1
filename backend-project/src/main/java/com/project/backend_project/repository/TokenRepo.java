package com.project.backend_project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.backend_project.entities.Token;

public interface TokenRepo extends JpaRepository<Token, Integer> {

     @Query(value = """
      select t from Token t where t.staff.id = :id and (t.expired = false or t.revoked = false)
      """)
  List<Token> findAllValidTokenByUser(Long id);

  Optional<Token> findByAccessToken(String accessToken);
    
}
