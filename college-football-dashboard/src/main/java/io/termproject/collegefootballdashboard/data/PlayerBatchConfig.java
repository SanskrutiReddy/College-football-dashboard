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

import io.termproject.collegefootballdashboard.model.Player;

// @Configuration
// @EnableBatchProcessing
public class PlayerBatchConfig {
    private final String[] FIELD_NAMES = new String[] { "PlayerCode", "PlayerTeamCode", "LastName", "FirstName", "UniformNumber", "PlayerClass", "Position" };

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<PlayerInput> playerReader() {
        return new FlatFileItemReaderBuilder<PlayerInput>().name("PlayerItemReader")
                .resource(new ClassPathResource("Player.csv")).delimited().names(FIELD_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<PlayerInput>() {
                    {
                        setTargetType(PlayerInput.class);
                    }
                }).build();
    }

    @Bean
    public PlayerDataProcessor playerProcessor() {
        return new PlayerDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Player> playerWriter(DataSource dataSource) {
        ItemPreparedStatementSetter<Player> valueSetter = 
                new PlayerPreparedStatementSetter();
        return new JdbcBatchItemWriterBuilder<Player>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("DELETE FROM player").dataSource(dataSource)
                .sql("INSERT INTO player (player_code, player_team_code, last_name, first_name, uniform_number, player_class, position) "
                        + " VALUES (?, ?, ?, ?, ?, ?, ?)")
                .itemPreparedStatementSetter(valueSetter)
                .dataSource(dataSource).build();
    }

    @Bean
    public Job importPlayerJob(JobCompletionNotificationListener listener, Step step5) {
        return jobBuilderFactory
            .get("importPlayerJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step5)
            .end()
            .build();
    }

    @Bean
    public Step step5(JdbcBatchItemWriter<Player> writer) {
        return stepBuilderFactory
            .get("step5")
            .<PlayerInput, Player>chunk(10)
            .reader(playerReader())
            .processor(playerProcessor())
            .writer(writer)
            .build();
    }
}
