package io.termproject.collegefootballdashboard.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.termproject.collegefootballdashboard.model.Stadium;
import io.termproject.collegefootballdashboard.repository.StadiumRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StadiumService {
    @Autowired
	private final StadiumRepository stadiumRepository;
	
	public Stadium findStadiumByStadiumCode(Long stadiumCode) {
		return stadiumRepository.findStadiumByStadiumCode(stadiumCode);
	}
}
