package io.termproject.collegefootballdashboard.data;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import io.termproject.collegefootballdashboard.model.Team;

public class TeamDataProcessor implements ItemProcessor<TeamInput, Team> {
    @Override
    @Nullable
    public Team process(@NonNull TeamInput teamInput) throws Exception {
        Team team = new Team();

        team.setTeamCode(Long.parseLong(teamInput.getTeamCode()));
        team.setName(teamInput.getName());

        return team;
    }
}
