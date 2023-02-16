package io.termproject.collegefootballdashboard.data;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import io.termproject.collegefootballdashboard.model.Stadium;

public class StadiumDataProcessor implements ItemProcessor<StadiumInput, Stadium> {

    @Override
    @Nullable
    public Stadium process(@NonNull StadiumInput stadiumInput) throws Exception {
        Stadium stadium = new Stadium();

        stadium.setStadiumCode(Long.parseLong(stadiumInput.getStadiumCode()));
        stadium.setName(stadiumInput.getSite());
        stadium.setCity(stadiumInput.getCity());
        stadium.setState(stadiumInput.getState());
        stadium.setCapacity(Integer.parseInt(stadiumInput.getCapacity()));
        stadium.setSurface(stadiumInput.getSurface());
        stadium.setYearOpened(Integer.parseInt(stadiumInput.getYearOpened()));

        return stadium;
    }
    
}
