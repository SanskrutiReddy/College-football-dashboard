package io.termproject.collegefootballdashboard.data;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import io.termproject.collegefootballdashboard.model.Game;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
 
final class GamePreparedStatementSetter implements ItemPreparedStatementSetter<Game> {
 
    @Override
    public void setValues(Game game, 
                          PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, game.getGameCode());
        preparedStatement.setDate(2, Date.valueOf(game.getDate()));
        preparedStatement.setLong(3, game.getHomeTeamCode().getTeamCode());
        preparedStatement.setLong(4, game.getVisitTeamCode().getTeamCode());
        preparedStatement.setLong(5, game.getStadiumCode().getStadiumCode());
        preparedStatement.setString(6, game.getResult());
        preparedStatement.setString(7, game.getScore());
        preparedStatement.setInt(8, game.getCurrentWins());
        preparedStatement.setInt(9, game.getCurrentLosses());
    }
}
