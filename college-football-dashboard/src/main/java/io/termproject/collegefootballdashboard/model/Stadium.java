package io.termproject.collegefootballdashboard.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Stadium {
    @Id
    private long StadiumCode;
    private String Name;
    private String City;
    private String State;
    private long Capacity;
    private String Surface;
    private int YearOpened;
}
