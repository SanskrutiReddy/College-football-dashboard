package io.termproject.collegefootballdashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.termproject.collegefootballdashboard.model.Conference;

@Repository
@Transactional(readOnly = true)
public interface ConferenceRepository extends CrudRepository<Conference, Long> {
    @Query("select c from Conference c where c.ConferenceCode = ?1")
    List<Conference> getByConferenceCode(Long conferenceCode);
}
