package io.termproject.collegefootballdashboard.data;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import io.termproject.collegefootballdashboard.model.Team;

// @Configuration
// @EnableBatchProcessing
public class TeamBatchConfig {
    private final String[] FIELD_NAMES = new String[] { "Team Code","Name" };

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<TeamInput> teamReader() {
        return new FlatFileItemReaderBuilder<TeamInput>().name("TeamItemReader")
                .resource(new ClassPathResource("Team.csv")).delimited().names(FIELD_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<TeamInput>() {
                    {
                        setTargetType(TeamInput.class);
                    }
                }).build();
    }

    @Bean
    public TeamDataProcessor teamProcessor() {
        return new TeamDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Team> teamWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Team>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO team (team_code, name) "
                        + " VALUES (:teamCode, :name)")
                .dataSource(dataSource).build();
    }

    @Bean
    public Job importConferenceJob(JobCompletionNotificationListener listener, Step step3) {
        return jobBuilderFactory
            .get("importTeamJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step3)
            .end()
            .build();
    }

    @Bean
    public Step step3(JdbcBatchItemWriter<Team> writer) {
        return stepBuilderFactory
            .get("step3")
            .<TeamInput, Team>chunk(10)
            .reader(teamReader())
            .processor(teamProcessor())
            .writer(writer)
            .build();
    }
}
