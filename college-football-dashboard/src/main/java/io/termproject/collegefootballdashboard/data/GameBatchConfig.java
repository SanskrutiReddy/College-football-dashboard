package io.termproject.collegefootballdashboard.data;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import io.termproject.collegefootballdashboard.model.Game;

// @Configuration
// @EnableBatchProcessing
public class GameBatchConfig {
    private final String[] FIELD_NAMES = new String[] { "GameCode", "Date", "HomeTeamCode", "VisitTeamCode", "Stadium Code", "Result", "Score", "Current Wins", "Current Losses" };

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<GameInput> gameReader() {
        return new FlatFileItemReaderBuilder<GameInput>().name("GameItemReader")
                .resource(new ClassPathResource("Game.csv")).delimited().names(FIELD_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<GameInput>() {
                    {
                        setTargetType(GameInput.class);
                    }
                }).build();
    }

    @Bean
    public GameDataProcessor gameProcessor() {
        return new GameDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Game> gameWriter(DataSource dataSource) {
        ItemPreparedStatementSetter<Game> valueSetter = 
                new GamePreparedStatementSetter();
        return new JdbcBatchItemWriterBuilder<Game>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("DELETE FROM game").dataSource(dataSource)
                .sql("INSERT INTO game (game_code, date, home_team_code, visit_team_code, stadium_code, result, score, current_wins, current_losses) "
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")
                .itemPreparedStatementSetter(valueSetter)
                .dataSource(dataSource).build();
    }

    @Bean
    public Job importGameJob(JobCompletionNotificationListener listener, Step step4) {
        return jobBuilderFactory
            .get("importGameJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step4)
            .end()
            .build();
    }

    @Bean
    public Step step4(JdbcBatchItemWriter<Game> writer) {
        return stepBuilderFactory
            .get("step4")
            .<GameInput, Game>chunk(10)
            .reader(gameReader())
            .processor(gameProcessor())
            .writer(writer)
            .build();
    }
}
