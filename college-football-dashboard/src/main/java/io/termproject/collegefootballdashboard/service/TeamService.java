package io.termproject.collegefootballdashboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.termproject.collegefootballdashboard.model.Team;
import io.termproject.collegefootballdashboard.repository.TeamRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TeamService {
    @Autowired
	private final TeamRepository teamRepository;
	
	public Team findTeamByTeamCode(Long teamCode) {
		return teamRepository.findTeamByTeamCode(teamCode);
	}
}
