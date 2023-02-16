package io.termproject.collegefootballdashboard.data;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import io.termproject.collegefootballdashboard.model.Conference;

public class ConferenceDataProcessor implements ItemProcessor<ConferenceInput, Conference>  {
    @Override
    @Nullable
    public Conference process(@NonNull ConferenceInput conferenceInput) throws Exception {
        Conference conference = new Conference();

        conference.setConferenceCode(Long.parseLong(conferenceInput.getConferenceCode()));
        conference.setConference(conferenceInput.getConference());
        conference.setSubdivision(conferenceInput.getSubdivision());

        return conference;
    }
}
