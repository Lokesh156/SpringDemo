package com.example.runnerz.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.imageio.IIOException;
import java.io.IOException;
import java.io.InputStream;

@Component
public class RunJsonLoader implements CommandLineRunner {
    private static final Logger log= LoggerFactory.getLogger(RunJsonLoader.class);
    private final RunRepository runRepository;
    private final ObjectMapper objectMapper;
    public RunJsonLoader(RunRepository runRepository, ObjectMapper objectMapper){
        this.runRepository=runRepository;
        this.objectMapper=objectMapper;
    }
    @Override
    public void run(String... args) throws Exception{
        if (runRepository.count()==0){
            try (InputStream inputStream= TypeReference.class.getResourceAsStream("/Data/Runs.json")){
              Runs allRuns=objectMapper.readValue(inputStream, Runs.class);
              log.info("Reading {} runs from JSON Data and saving in to database.",allRuns.runs().size());
              runRepository.saveAll(allRuns.runs());
            }
            catch (IOException e){
                throw new RuntimeException("Failed to read JSON data", e);

            }
        }
        else {
            log.info("Not loading Runs from JSON data because the collections contains the data");
        }

    }
}
