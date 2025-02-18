package com.example.runnerz.run;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/run")
public class RunController {
    private final RunRepository runRepository;

    public RunController(RunRepository runRepository){

        this.runRepository=runRepository;
    }

    @GetMapping("/{id}")
    Run findById(@PathVariable Integer id){
        Optional<Run> run= runRepository.findByID(id);
        if(run.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return run.get();
    }


    @GetMapping("/runs")
    List<Run> findAll(){
        return runRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        runRepository.delete(id);

    }



    @GetMapping("/hello")
    public String hello(){
        return "Hello, This is Lokesh Pallikonda";
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    void create(@RequestBody Run run){
        runRepository.create(run);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@RequestBody Run run, @PathVariable Integer id){
        runRepository.update(run,id);
    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping("/{id}")
//    void delete(@PathVariable Integer id){
//        runRepository.delete(id);
//    }


}
