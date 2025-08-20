package dev.leonardosanner.arandu_mvp_server.controller;


import dev.leonardosanner.arandu_mvp_server.model.entity.TemporaryProfileEntity;
import dev.leonardosanner.arandu_mvp_server.service.profileQuestionary.temporary.TemporaryProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Related with the questionary for RAG and prompt

@RestController
@RequestMapping("/profile") // temporary | long term
public class ProfileController {

    private final TemporaryProfileService temporaryProfileService;

    public ProfileController(TemporaryProfileService temporaryProfileService) {
        this.temporaryProfileService = temporaryProfileService;
    }

    // Temporary paths

    @GetMapping("/temporary/{userEmail}")
    public ResponseEntity<Object> get(@PathVariable String userEmail) {

        TemporaryProfileEntity entity =  this.temporaryProfileService.getProfile(userEmail);
        return ResponseEntity.ok().body(entity);
    }

    @GetMapping("/temporary/exists/{userEmail}")
    public Boolean verifyExistence(@PathVariable String userEmail) {

        return this.temporaryProfileService.verifyProfileExistence(userEmail);
    }

    @PostMapping("/temporary/{userEmail}")
    public ResponseEntity<Object> post(@PathVariable String userEmail,
                                       @RequestBody TemporaryProfileEntity temporaryProfileEntity) {

        this.temporaryProfileService.createProfile(userEmail, temporaryProfileEntity);
        return ResponseEntity.accepted().body("Profile has been created");
    }

    // Long term paths

}
