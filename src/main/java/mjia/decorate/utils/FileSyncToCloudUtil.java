package mjia.decorate.utils;

import com.qcloud.cos.transfer.TransferManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class FileSyncToCloudUtil {
    @Value("${file.to.cloud.secretId}")
    private String secretId;

    @Value("${file.to.cloud.secretKey}")
    private String secretKey;


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
