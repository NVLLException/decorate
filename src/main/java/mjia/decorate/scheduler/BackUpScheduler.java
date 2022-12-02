package mjia.decorate.scheduler;

import lombok.extern.slf4j.Slf4j;
import mjia.decorate.utils.FileSyncToCloudUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;

@Slf4j
@Configuration
@EnableScheduling
public class BackUpScheduler {

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${decorate.bucket.backup.name}")
    private String bucketName;

    @Value("${decorate.backup.filepath}")
    private String fileName;

    @Autowired
    private FileSyncToCloudUtil fileSyncToCloudUtil;

    /**
     * 每天2:00执行
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void backUpSql() {
        try{
            Runtime.getRuntime().exec("sh /work/backup.sh");
            Thread.sleep(1000 * 60);
            fileSyncToCloudUtil.syncToCloud(log, bucketName, "", fileName);
            new File(fileName).deleteOnExit();
            log.info("backUpSql success");
        } catch (Exception e) {
            log.error("backup sql error:", e);
        }
    }
}
