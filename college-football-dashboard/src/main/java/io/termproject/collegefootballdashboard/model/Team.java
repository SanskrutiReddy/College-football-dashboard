package io.termproject.collegefootballdashboard.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Team implements Serializable{
    @Id
    private long TeamCode;
    private String Name;

    // @Transient
    // private List<Object> Games;
}
