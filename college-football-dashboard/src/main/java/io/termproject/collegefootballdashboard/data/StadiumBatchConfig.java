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

import io.termproject.collegefootballdashboard.model.Stadium;

// @Configuration
// @EnableBatchProcessing
public class StadiumBatchConfig {
    private final String[] FIELD_NAMES = new String[] { "Site","Stadium Code","City","State","Capacity","Surface","Year Opened" };

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<StadiumInput> stadiumReader() {
        return new FlatFileItemReaderBuilder<StadiumInput>().name("StadiumItemReader")
                .resource(new ClassPathResource("Stadium.csv")).delimited().names(FIELD_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<StadiumInput>() {
                    {
                        setTargetType(StadiumInput.class);
                    }
                }).build();
    }

    @Bean
    public StadiumDataProcessor stadiumProcessor() {
        return new StadiumDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Stadium> stadiumWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Stadium>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("DELETE FROM stadium").dataSource(dataSource)
                .sql("INSERT INTO stadium (stadium_code, name, city, state, capacity, surface, year_opened) "
                        + " VALUES (:stadiumCode, :name, :city, :state, :capacity, :surface, :yearOpened)")
                .dataSource(dataSource).build();
    }

    @Bean
    public Job importStadiumJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory
            .get("importStadiumJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1)
            .end()
            .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Stadium> writer) {
        return stepBuilderFactory
            .get("step1")
            .<StadiumInput, Stadium>chunk(10)
            .reader(stadiumReader())
            .processor(stadiumProcessor())
            .writer(writer)
            .build();
    }
}
