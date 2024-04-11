package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.Votes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotesRepository extends JpaRepository<Votes, String> {
}
