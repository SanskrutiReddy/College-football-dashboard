package io.termproject.collegefootballdashboard.data;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import io.termproject.collegefootballdashboard.model.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
 
final class PlayerPreparedStatementSetter implements ItemPreparedStatementSetter<Player> {
 
    @Override
    public void setValues(Player player, 
                          PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, player.getPlayerCode());
        preparedStatement.setLong(2, player.getPlayerTeamCode().getTeamCode());
        preparedStatement.setString(3, player.getLastName());
        preparedStatement.setString(4, player.getFirstName());
        preparedStatement.setString(5, player.getUniformNumber());
        preparedStatement.setString(6, player.getPlayerClass());
        preparedStatement.setString(7, player.getPosition());
    }
}
