package mjia.decorate.utils;

import com.qcloud.cos.transfer.TransferManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Objects;

@Component
@Slf4j
public class FileSyncToCloudUtil {
    //@Value("${file.to.cloud.secretId}")
    private String secretId;

    //@Value("${file.to.cloud.secretKey}")
    private String secretKey;

    @PostConstruct
    private void init() {
        try {
            File file = ResourceUtils.getFile("/work/pass/cospass");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            secretId = reader.readLine();
            secretKey = reader.readLine();
            reader.close();
            log.info("load file to cloud secret success");
        } catch (Exception e) {
            log.error("load file to cloud secret error");
            log.error(e.getMessage());
        }
    }


    public boolean syncToCloud(String filePath, String fileName) {
        try {
            TransferManagerCreator.upload(secretId, secretKey, filePath, fileName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
