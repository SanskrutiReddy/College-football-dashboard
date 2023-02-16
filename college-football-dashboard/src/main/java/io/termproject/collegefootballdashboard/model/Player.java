package io.termproject.collegefootballdashboard.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Player {
    @Id
    private Long PlayerCode;

    @ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(nullable = true, name = "player_team_code", referencedColumnName = "TeamCode")
    private Team PlayerTeamCode;

    private String LastName;
    private String FirstName;
    private String UniformNumber;
    private String PlayerClass;
    private String Position;
}
