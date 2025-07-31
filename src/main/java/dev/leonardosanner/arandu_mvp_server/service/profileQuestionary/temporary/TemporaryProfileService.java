package dev.leonardosanner.arandu_mvp_server.service.profileQuestionary.temporary;

import dev.leonardosanner.arandu_mvp_server.model.entity.TemporaryProfileEntity;
import dev.leonardosanner.arandu_mvp_server.service.profileQuestionary.temporary.useCases.CreateTemporaryProfileUseCase;
import dev.leonardosanner.arandu_mvp_server.service.profileQuestionary.temporary.useCases.GetTemporaryProfileUseCase;
import dev.leonardosanner.arandu_mvp_server.service.profileQuestionary.temporary.useCases.VerifyProfileExistenceUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TemporaryProfileService {

    @Autowired
    private GetTemporaryProfileUseCase getTemporaryProfileUseCase;

    @Autowired
    private CreateTemporaryProfileUseCase createTemporaryProfileUseCase;

    @Autowired
    private VerifyProfileExistenceUseCase verifyProfileExistenceUseCase;


    public TemporaryProfileEntity getProfile(String userEmail) {
        return this.getTemporaryProfileUseCase.execute(userEmail);
    }

    public Boolean verifyProfileExistence(String userEmail) {
        return this.verifyProfileExistenceUseCase.execute(userEmail);
    }


    public Boolean createProfile(String userEmail, TemporaryProfileEntity temporaryProfileEntity) {
        return this.createTemporaryProfileUseCase.execute(userEmail, temporaryProfileEntity);
    }
}
