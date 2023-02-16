package io.termproject.collegefootballdashboard.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "game")
@Getter
@Setter
public class Game {
    @Id
    private long GameCode;
    private LocalDate Date;
    @ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(nullable = true, name = "home_team_code", referencedColumnName = "TeamCode")
    private Team HomeTeamCode;
    @ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(nullable = true, name = "visit_team_code", referencedColumnName = "TeamCode")
    private Team VisitTeamCode;
    @ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(nullable = true, name = "stadium_code", referencedColumnName = "StadiumCode")
    private Stadium StadiumCode;
    private String Result;
    private String Score;
    private int CurrentWins;
    private int CurrentLosses;
}
