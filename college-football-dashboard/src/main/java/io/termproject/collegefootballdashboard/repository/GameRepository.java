package io.termproject.collegefootballdashboard.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.termproject.collegefootballdashboard.model.Game;
import io.termproject.collegefootballdashboard.model.Stadium;
import io.termproject.collegefootballdashboard.model.Team;

@Repository
@Transactional(readOnly = true)
public interface GameRepository extends CrudRepository<Game, Long> {
    
    @Query("select g from Game g where (g.HomeTeamCode = :teamCode1 or g.VisitTeamCode = :teamCode2) order by Date desc")
    List<Game> getByTeam1OrTeam2OrderByDateDesc(Team teamCode1, Team teamCode2, Pageable pageable);

    @Query("select g from Game g where (g.HomeTeamCode = :teamCode or g.VisitTeamCode = :teamCode) and g.Date between :dateStart and :dateEnd order by Date desc")
    List<Game> getGamesByTeamBetweenDates(
        @Param("teamCode") Team teamCode, 
        @Param("dateStart") LocalDate dateStart, 
        @Param("dateEnd") LocalDate dateEnd
    );

    default List<Game> findLatestGamesbyTeam(Team teamCode, int count) {
        return getByTeam1OrTeam2OrderByDateDesc(teamCode, teamCode, PageRequest.of(0, count));
    }

    @Query(value = "SELECT DISTINCT g1.home_team_code as team1, g1.visit_team_code as team2, g1.stadium_code, g2.home_team_code as team3, g2.visit_team_code as team4 FROM game g1 "
			+ "INNER JOIN game g2 ON g1.home_team_code = g2.home_team_code AND g1.visit_team_code != g2.visit_team_code AND g1.stadium_code = g2.stadium_code "
			+ "WHERE g1.home_team_code = ?1 AND g2.visit_team_code = ?2 AND g1.stadium_code = ?3 LIMIT 100", nativeQuery = true)
	public List<List<Object>> getOneDegreeOfSeparationOfStadiumsBetweenTeams(@Param("homeTeamCode") Team team1, @Param("visitTeamCode") Team team2, @Param("stadiumCode") Stadium stadium);

}
