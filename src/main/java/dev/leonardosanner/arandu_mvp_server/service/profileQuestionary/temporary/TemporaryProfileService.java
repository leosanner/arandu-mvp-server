package dev.leonardosanner.arandu_mvp_server.service.profileQuestionary.temporary;

import dev.leonardosanner.arandu_mvp_server.model.entity.TemporaryProfileEntity;
import dev.leonardosanner.arandu_mvp_server.service.profileQuestionary.temporary.useCases.CreateTemporaryProfileUseCase;
import dev.leonardosanner.arandu_mvp_server.service.profileQuestionary.temporary.useCases.GetTemporaryProfileUseCase;
import org.springframework.stereotype.Service;

@Service
public class TemporaryProfileService {

    private final GetTemporaryProfileUseCase getTemporaryProfileUseCase;
    private final CreateTemporaryProfileUseCase createTemporaryProfileUseCase;

    public TemporaryProfileService(
            GetTemporaryProfileUseCase getTemporaryProfileUseCase,
            CreateTemporaryProfileUseCase createTemporaryProfileUseCase
    ) {
        this.getTemporaryProfileUseCase = getTemporaryProfileUseCase;
        this.createTemporaryProfileUseCase = createTemporaryProfileUseCase;
    }

    public TemporaryProfileEntity getProfile(String userEmail) {
        return this.getTemporaryProfileUseCase.execute(userEmail);
    }

    public Boolean createProfile(String userEmail, TemporaryProfileEntity temporaryProfileEntity) {
        return this.createTemporaryProfileUseCase.execute(userEmail, temporaryProfileEntity);
    }
}
