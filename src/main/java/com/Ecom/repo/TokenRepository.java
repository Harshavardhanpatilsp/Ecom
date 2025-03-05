package com.Ecom.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Ecom.Model.Token;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
	@Query("""
		    select t from Token t 
		    where t.user.id = :userId and t.loggedOut = false
		""")
		List<Token> findAllAccessTokensByUser(Integer userId);

			     Optional<Token> findByAccessToken(String token);

			     Optional<Token > findByRefreshToken(String token);
			     
}