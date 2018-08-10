package cvc.api.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cvc.domain.Cv;
import cvc.logic.CvLogic;
import cvc.logic.interfaces.ICvRepository;
import cvc.logic.interfaces.service.ICvSearchService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/cv", produces = MediaType.APPLICATION_JSON_VALUE)
public class CvController {
    private ICvRepository cvRepository;
    private ICvSearchService cvSearchService;
    private CvLogic cvLogic;

    public CvController(ICvRepository cvRepository,
                        ICvSearchService cvSearchService) {
        this.cvRepository = cvRepository;
        this.cvSearchService = cvSearchService;
    }

    /*
     * forwards to getCvs to smart filter by default
     */
    @GetMapping("/")
    public List<Cv> index() {
        return getCvs("smart");
    }

    /*
     * Gets the list of Cv filtered by filter param
     */
    @GetMapping("/filter/{filter}")
    public List<Cv> getCvs(@PathVariable String filter) {
        return this.cvRepository.findAll();
    }

    /*
     * Gets the cv by id
    */
    @GetMapping("/{id}")
    public Cv getCv(@PathVariable String id) {
        return cvRepository.findById(Long.parseLong(id)).orElse(null);
    }

    /*
     * Inserts the new Cv
     */
    @PostMapping(value = "/add", consumes = "application/json")
    public void addCv(@RequestBody String payload) {
        System.out.println(payload);

        try {
            ObjectMapper mapper = new ObjectMapper();
            Cv cv = mapper.readValue(payload, Cv.class);
            System.out.println(cv);
            this.cvRepository.save(cv);
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
        }
        catch (JsonParseException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/filter/name/{nameStartsWith}")
    public List<Cv> getCvsByName(@PathVariable String nameStartsWith) {
        return cvSearchService.nameStartsWith(nameStartsWith);
    }
}
