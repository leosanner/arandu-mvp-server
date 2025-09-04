package dev.leonardosanner.arandu_mvp_server.service.scheduler;

import dev.leonardosanner.arandu_mvp_server.model.entity.AiUsageEntity;
import dev.leonardosanner.arandu_mvp_server.repository.AiUsageRepository;
import dev.leonardosanner.arandu_mvp_server.service.ai.threads.NewsApiThread;
import dev.leonardosanner.arandu_mvp_server.service.ai.threads.NewsThreadsManager;
import dev.leonardosanner.arandu_mvp_server.service.scheduler.useCases.SchedulerUsages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskSchedulerService {

    private final NewsThreadsManager newsThreadsManager;
    private final SchedulerUsages schedulerUsages;

    public TaskSchedulerService(NewsThreadsManager newsThreadsManager,
                                SchedulerUsages schedulerUsages) {

        this.newsThreadsManager = newsThreadsManager;
        this.schedulerUsages = schedulerUsages;
    }


    @Scheduled(fixedRate = 5000 * 60)
    public void clearPreviousUsage() {
        this.schedulerUsages.clearPrevUsage();
    }

    @Scheduled(fixedRate = 1000*60*60*24*3)
    public void resetAiRequests() {
        this.schedulerUsages.resetRequests();
    }

//    @Scheduled(fixedRateString = "${SEND_EMAILS_SCHEDULER_TIME}")

    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void sendNews() {
       this.newsThreadsManager.executeThreads();
    }
}
