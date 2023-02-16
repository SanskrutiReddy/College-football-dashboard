package io.termproject.collegefootballdashboard.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StadiumInput {
    private String Site;
    private String StadiumCode;
    private String City;
    private String State;
    private String Capacity;
    private String Surface;
    private String YearOpened;
}