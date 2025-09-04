package dev.leonardosanner.arandu_mvp_server.service.scheduler.useCases;

import dev.leonardosanner.arandu_mvp_server.model.entity.AiUsageEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserJWTEntity;
import dev.leonardosanner.arandu_mvp_server.repository.AiUsageRepository;
import dev.leonardosanner.arandu_mvp_server.repository.JwtUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class SchedulerUsages {

    @Value("${ai.usage.credits}")
    private Integer creditAmount;

    private final JwtUserRepository jwtUserRepository;
    private final AiUsageRepository aiUsageRepository;

    public SchedulerUsages(JwtUserRepository jwtUserRepository,
                           AiUsageRepository aiUsageRepository) {

        this.jwtUserRepository = jwtUserRepository;
        this.aiUsageRepository = aiUsageRepository;
    }

    public void clearPrevUsage() {

        List<UserJWTEntity> allEntries =  jwtUserRepository.findAll();

        if (allEntries.isEmpty()) {
            return;
        }

        for (UserJWTEntity entry : allEntries) {
            var currentTime = Instant.now();

            if (currentTime.isAfter(entry.getExpirationDate())) {
                jwtUserRepository.delete(entry);
            }
        }
    };

    public void resetRequests() {
        List<AiUsageEntity> allUsersUsage = this.aiUsageRepository.findAll();

        for (AiUsageEntity entity : allUsersUsage) {
            entity.setCredits(creditAmount);
            this.aiUsageRepository.save(entity);
        }
    }
}
