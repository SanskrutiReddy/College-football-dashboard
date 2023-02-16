package io.termproject.collegefootballdashboard.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Conference {
    @Id
    private long ConferenceCode;
    private String Conference;
    private String Subdivision;
}
