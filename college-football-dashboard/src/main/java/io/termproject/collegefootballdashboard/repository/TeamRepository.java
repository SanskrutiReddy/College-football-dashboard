package io.termproject.collegefootballdashboard.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.termproject.collegefootballdashboard.model.Team;

@Repository
@Transactional(readOnly = true)
public interface TeamRepository extends CrudRepository<Team, Long> {
    
    @Query("SELECT t from Team t where t.Name = ?1")
    public Team findByName(String name);

    @Query("SELECT t from Team t where t.TeamCode = ?1")
	public Team findTeamByTeamCode(Long teamCode);

}
