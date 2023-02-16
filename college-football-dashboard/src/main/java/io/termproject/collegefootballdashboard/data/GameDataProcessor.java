package io.termproject.collegefootballdashboard.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import io.termproject.collegefootballdashboard.model.Game;
import io.termproject.collegefootballdashboard.service.StadiumService;
import io.termproject.collegefootballdashboard.service.TeamService;

public class GameDataProcessor implements ItemProcessor<GameInput, Game>, Serializable {

    @Autowired
    private TeamService teamService;
    @Autowired
    private StadiumService stadiumService;

    @Override
    @Nullable
    public Game process(@NonNull GameInput gameInput) throws Exception {
        Game game = new Game();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        game.setGameCode(Long.parseLong(gameInput.getGameCode()));
        game.setDate(LocalDate.parse(gameInput.getDate(), formatter));
        game.setHomeTeamCode(teamService.findTeamByTeamCode(Long.parseLong(gameInput.getHomeTeamCode())));
        game.setVisitTeamCode(teamService.findTeamByTeamCode(Long.parseLong(gameInput.getVisitTeamCode())));
        game.setStadiumCode(stadiumService.findStadiumByStadiumCode(Long.parseLong(gameInput.getStadiumCode())));
        game.setResult(gameInput.getResult());
        game.setScore(gameInput.getScore());
        game.setCurrentWins(Integer.parseInt(gameInput.getCurrentWins()));
        game.setCurrentLosses(Integer.parseInt(gameInput.getCurrentLosses()));

        return game;
    }
}
