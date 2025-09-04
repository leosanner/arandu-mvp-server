package dev.leonardosanner.arandu_mvp_server.service.ai.useCases;

import dev.leonardosanner.arandu_mvp_server.model.entity.AiUsageEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.repository.AiUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportUsageUseCase {

    @Value("${ai.usage.credits}")
    private Integer creditAmount;

    private final AiUsageRepository aiUsageRepository;

    public ReportUsageUseCase(AiUsageRepository aiUsageRepository) {
        this.aiUsageRepository = aiUsageRepository;
    }

    public void execute(UserEntity user) {

        AiUsageEntity usageEntity = this.aiUsageRepository.findByUser(user).orElseGet(
                () -> this.createUsageEntity(user)
        );

        if (usageEntity.getCredits() - 1 < 0) {
            throw new RuntimeException("Reached the amount of credits: " + creditAmount);
        }

        usageEntity.setCredits(usageEntity.getCredits() - 1);
        this.aiUsageRepository.save(usageEntity);
    }

    private AiUsageEntity createUsageEntity(UserEntity user) {
        AiUsageEntity newEntity = AiUsageEntity.builder()
                .credits(creditAmount)
                .user(user)
                .build();

        this.aiUsageRepository.save(newEntity);

        return newEntity;
    }

}
