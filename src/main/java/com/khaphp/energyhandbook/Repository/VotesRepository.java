package com.khaphp.energyhandbook.Repository;

import com.khaphp.energyhandbook.Entity.Votes;
import com.khaphp.energyhandbook.Entity.keys.VotesKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VotesRepository extends JpaRepository<Votes, VotesKey> {
    @Query("SELECT AVG(c.star) FROM Votes c WHERE c.cookingRecipe.id = ?1")
    float averageVoteStar(String id);
}
