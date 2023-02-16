package io.termproject.collegefootballdashboard.data;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import io.termproject.collegefootballdashboard.model.Player;
import io.termproject.collegefootballdashboard.repository.TeamRepository;

public class PlayerDataProcessor implements ItemProcessor<PlayerInput, Player> {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    @Nullable
    public Player process(@NonNull PlayerInput playerInput) throws Exception {
        Player player = new Player();

        player.setPlayerCode(Long.parseLong(playerInput.getPlayerCode()));
        player.setPlayerTeamCode(teamRepository.findTeamByTeamCode(Long.parseLong(playerInput.getPlayerTeamCode())));
        player.setLastName(playerInput.getLastName());
        player.setFirstName(playerInput.getFirstName());
        player.setUniformNumber(playerInput.getUniformNumber());
        player.setPlayerClass(playerInput.getPlayerClass());
        player.setPosition(playerInput.getPosition());

        return player;
    }
}
