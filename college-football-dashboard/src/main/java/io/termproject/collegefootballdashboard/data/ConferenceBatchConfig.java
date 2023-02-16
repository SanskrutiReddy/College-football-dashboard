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

import io.termproject.collegefootballdashboard.model.Conference;

// @Configuration
// @EnableBatchProcessing
public class ConferenceBatchConfig {
    private final String[] FIELD_NAMES = new String[] { "Conference Code","Conference","Subdivision" };

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<ConferenceInput> conferenceReader() {
        return new FlatFileItemReaderBuilder<ConferenceInput>().name("ConferenceItemReader")
                .resource(new ClassPathResource("Conference.csv")).delimited().names(FIELD_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<ConferenceInput>() {
                    {
                        setTargetType(ConferenceInput.class);
                    }
                }).build();
    }

    @Bean
    public ConferenceDataProcessor conferenceProcessor() {
        return new ConferenceDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Conference> conferenceWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Conference>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO conference (conference_code, conference, subdivision) "
                        + " VALUES (:conferenceCode, :conference, :subdivision)")
                .dataSource(dataSource).build();
    }

    @Bean
    public Job importConferenceJob(JobCompletionNotificationListener listener, Step step2) {
        return jobBuilderFactory
            .get("importConferenceJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step2)
            .end()
            .build();
    }

    @Bean
    public Step step2(JdbcBatchItemWriter<Conference> writer) {
        return stepBuilderFactory
            .get("step2")
            .<ConferenceInput, Conference>chunk(10)
            .reader(conferenceReader())
            .processor(conferenceProcessor())
            .writer(writer)
            .build();
    }
}
