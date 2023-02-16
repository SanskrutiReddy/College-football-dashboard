package io.termproject.collegefootballdashboard.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import io.termproject.collegefootballdashboard.model.Stadium;

@Repository
@Transactional(readOnly = true)
public interface StadiumRepository extends CrudRepository<Stadium, Long> {
	@Query("SELECT s from Stadium s where s.StadiumCode = ?1")
    public Stadium findStadiumByStadiumCode(Long stadiumCode);

    @Query("SELECT s from Stadium s where s.Name = ?1")
    public Stadium findStadiumByStadiumName(String name);
}
