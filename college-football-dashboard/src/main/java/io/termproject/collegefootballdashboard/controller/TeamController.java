package io.termproject.collegefootballdashboard.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.termproject.collegefootballdashboard.model.Game;
import io.termproject.collegefootballdashboard.model.Player;
import io.termproject.collegefootballdashboard.model.Stadium;
import io.termproject.collegefootballdashboard.model.Team;
import io.termproject.collegefootballdashboard.repository.GameRepository;
import io.termproject.collegefootballdashboard.repository.PlayerRepository;
import io.termproject.collegefootballdashboard.repository.StadiumRepository;
import io.termproject.collegefootballdashboard.repository.TeamRepository;

@RestController
@CrossOrigin
@RequestMapping
public class TeamController {
    
    private TeamRepository teamRepository;
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private StadiumRepository stadiumRepository;
    
    public TeamController(TeamRepository teamRepository, GameRepository gameRepository, PlayerRepository playerRepository, StadiumRepository stadiumRepository) {
        this.teamRepository = teamRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.stadiumRepository = stadiumRepository;
    }


    @GetMapping("/team")
    public Iterable<Team> getAllTeam() {
        return this.teamRepository.findAll();
    }

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        Team team = this.teamRepository.findByName(teamName);
            
        return team;
    }

    @GetMapping("/games/{teamName}")
    public List<Game> getGames(@PathVariable String teamName) {
        Team team = this.teamRepository.findByName(teamName);
        List<Game> games = this.gameRepository.findLatestGamesbyTeam(team,4);
            
        return games;
    }

    @GetMapping("/team/{teamName}/games")
    public List<Game> getGamesForTeam(@PathVariable String teamName, @RequestParam int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year + 1, 1, 1);
        Team team = this.teamRepository.findByName(teamName);
        return this.gameRepository.getGamesByTeamBetweenDates(
            team,
            startDate,
            endDate
            );
    }

    @GetMapping("/team/{teamName}/players")
    public List<Player> getPlayers(@PathVariable String teamName) {
        Team team = this.teamRepository.findByName(teamName);
        List<Player> players = this.playerRepository.getByTeamCode(team);
            
        return players;
    }

    @GetMapping("/team/{homeTeamName}/{visitTeamName}/{stadiumName}/oneDegreeOfSeparation")
    public List<List<Object>> getOneDegreeOfSeparationOfStadiumsBetweenTeams(@PathVariable String homeTeamName, @PathVariable String visitTeamName, @PathVariable String stadiumName) {
        Team team1 = this.teamRepository.findByName(homeTeamName);
        Team team2 = this.teamRepository.findByName(visitTeamName);
        Stadium stadium = this.stadiumRepository.findStadiumByStadiumName(stadiumName);

        List<List<Object>> oneDegreeOfSeparation = this.gameRepository.getOneDegreeOfSeparationOfStadiumsBetweenTeams(team1, team2, stadium);
        List<List<Object>> oneDegreeValuesList = new ArrayList<List<Object>>();

        for (List<Object> object : oneDegreeOfSeparation) {
            
            List<Object> oneDegreeValues = new ArrayList<Object>();
            oneDegreeValues.add(this.teamRepository.findById(Long.parseLong(String.valueOf(object.get(0)))));
            oneDegreeValues.add(this.teamRepository.findById(Long.parseLong(String.valueOf(object.get(1)))));
            oneDegreeValues.add(this.stadiumRepository.findStadiumByStadiumCode(Long.parseLong(String.valueOf(object.get(2)))));
            oneDegreeValues.add(this.teamRepository.findById(Long.parseLong(String.valueOf(object.get(3)))));
            oneDegreeValues.add(this.teamRepository.findById(Long.parseLong(String.valueOf(object.get(4)))));

            oneDegreeValuesList.add(oneDegreeValues);
        }

        return oneDegreeValuesList;

    }
}
