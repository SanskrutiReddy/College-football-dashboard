package io.termproject.collegefootballdashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.termproject.collegefootballdashboard.model.Player;
import io.termproject.collegefootballdashboard.model.Team;

@Repository
@Transactional(readOnly = true)
public interface PlayerRepository extends CrudRepository<Player, Long> {
    @Query("select p from Player p where p.PlayerTeamCode = :teamCode")
    List<Player> getByTeamCode(Team teamCode);
}
