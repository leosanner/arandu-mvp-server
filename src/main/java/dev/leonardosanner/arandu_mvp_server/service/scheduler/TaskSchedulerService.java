package dev.leonardosanner.arandu_mvp_server.service.scheduler;

import dev.leonardosanner.arandu_mvp_server.model.entity.AiUsageEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.model.entity.UserJWTEntity;
import dev.leonardosanner.arandu_mvp_server.repository.AiUsageRepository;
import dev.leonardosanner.arandu_mvp_server.repository.JwtUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class TaskSchedulerService {

    @Autowired
    private JwtUserRepository jwtUserRepository;

    @Autowired
    private AiUsageRepository aiUsageRepository;

    @Value("${ai.usage.credits}")
    private Integer creditAmount;

    private List<UserEntity> activeUsers;

//    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
//    public List<UserEntity> getActiveUsers() {
//
//        System.out.println("Getting all the active users");
//
//        List<UserJWTEntity> entries = jwtUserRepository.findAll();
//
//        var setEntries =  entries
//                .stream()
//                .map(UserJWTEntity::getUser)
//                .distinct()
//                .collect(Collectors.toList());
//
//        System.out.println(setEntries);
//
//        return setEntries;
//    };

    @Scheduled(fixedRate = 5000 * 60)
    public void clearPreviousUsage() {

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

    @Scheduled(fixedRate = 1000*60*60*24*3)
    public void resetAiRequests() {
        List<AiUsageEntity> allUsersUsage = this.aiUsageRepository.findAll();

        for (AiUsageEntity entity : allUsersUsage) {
            entity.setCredits(creditAmount);
            this.aiUsageRepository.save(entity);
        }
    }
}
