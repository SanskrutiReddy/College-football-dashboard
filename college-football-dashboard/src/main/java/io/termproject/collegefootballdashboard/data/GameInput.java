package io.termproject.collegefootballdashboard.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameInput {
    private String GameCode;
    private String Date;
    private String HomeTeamCode;
    private String VisitTeamCode;
    private String StadiumCode;
    private String Result;
    private String Score;
    private String CurrentWins;
    private String CurrentLosses;
}
