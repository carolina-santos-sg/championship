package com.championship.matches.repository;

import com.championship.matches.matches.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchesRepository extends JpaRepository<Matches, Integer> {


}