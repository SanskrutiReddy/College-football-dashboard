package io.termproject.collegefootballdashboard.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.STARTING) {
            log.info("!!! JOB STARTING! Resetting database");

            jdbcTemplate.update("DELETE FROM stadium");
            jdbcTemplate.update("DELETE FROM conference");
            jdbcTemplate.update("DELETE FROM team");
        }
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            jdbcTemplate.query("SELECT stadium_code, name FROM stadium",
            (rs, row) -> "Stadium Code " + rs.getString(1) + " Stadium Name " + rs.getString(2)
            ).forEach(str -> System.out.println(str));

            jdbcTemplate.query("SELECT conference_code, conference, subdivision FROM conference",
            (rs, row) -> "Conference Code " + rs.getString(1) + " Conference Name " + rs.getString(2) + " Subdivision " + rs.getString(3)
             ).forEach(str -> System.out.println(str));

            jdbcTemplate.query("SELECT team_code, name FROM team",
            (rs, row) -> "Team Code " + rs.getString(1) + " Name " + rs.getString(2)
             ).forEach(str -> System.out.println(str));

            jdbcTemplate.query("SELECT game_code, date, home_team_code, visit_team_code, stadium_code, result, score FROM game",
            (rs, row) -> "Game Code " + rs.getString(1) + " Date " + rs.getString(2) + " Home Team Code " + rs.getString(3) + " Visit Team Code " + rs.getString(4) + " Stadium Code " + rs.getString(5) + " Result " + rs.getString(6) + " Score " + rs.getString(7)
             ).forEach(str -> System.out.println(str));

            jdbcTemplate.query("SELECT player_code, player_team_code, last_name, first_name FROM player",
            (rs, row) -> "Player Code " + rs.getString(1) + " Player Team Code " + rs.getString(2) + " Last Name " + rs.getString(3) + " First Name " + rs.getString(4)
              ).forEach(str -> System.out.println(str));
        }
    }
}
