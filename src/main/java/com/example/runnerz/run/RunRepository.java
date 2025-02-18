package com.example.runnerz.run;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class RunRepository {

    private static final Logger log = LoggerFactory.getLogger(RunRepository.class);

    private  final JdbcClient jdbcClient;
    public RunRepository(JdbcClient jdbcClient){
        this.jdbcClient=jdbcClient;
    }

    public List<Run> findAll(){
        return jdbcClient.sql("select * from run").query(Run.class).list();
    }

    public void delete(Integer id){
        log.info("Attempting to delete{} ", id);
        jdbcClient.sql("delete from run where id=:id ").param("id",id).update();
        log.info("Deleted the{}", id);
    }




//    private List<Run> runs= new ArrayList<>();
//
//
//



    public Optional<Run> findByID(Integer id) {
        try {
            return Optional.of(
                    jdbcClient.sql("SELECT * FROM run WHERE id=:id")
                            .param("id", id)
                            .query(Run.class)
                            .single()  // Returns one result or throws an exception
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


//
//
//
//    List<Run> findAll(){
//        return runs;
//    }
//
    void create(Run run){
        var updated = jdbcClient.sql("INSERT INTO Run(id,title,started_on,completed_on,miles,location) VALUES (?,?,?,?,?,?)").params(List.of(run.id(),run.title(),run.startedOn(),run.completedOn(),run.miles(),run.location().toString())).update();
        Assert.state(updated==1,"Failed to create run "+run.title());
    }
//
    void update(Run run, Integer id){

        var updated= jdbcClient.sql("UPDATE Run SET title=?,started_on=?,completed_on=?,miles=?,location=? WHERE id=?").params(List.of(run.title(),run.startedOn(),run.completedOn(),run.miles(), run.location().toString(),id)).update();
        Assert.state(updated==1,"Failed to update run "+run.title());
    }

    public void saveAll(List<Run> runs){
        runs.stream().forEach((this::create));
    }
    public int count(){
        return jdbcClient.sql("SELECT * FROM Run").query().listOfRows().size();
    }
//
//    void delete(Integer id) {
//        runs.removeIf(run -> run.id().equals(id));
//    }
//
//
//
//
//    @PostConstruct
//    private void init(){
//        runs.add(new Run(1,
//                "Monday morning run",
//                LocalDateTime.now(),
//                LocalDateTime.now().plus(30, ChronoUnit.MINUTES),
//                3,
//                Location.INDOOR));
//
//        runs.add((new Run(
//                2,
//                "Tuesday Morning run ",
//                LocalDateTime.now(),
//                LocalDateTime.now().plus(60,ChronoUnit.MINUTES),
//                5,
//                Location.OUTDOOR)));
//
//    }
//
//

}
