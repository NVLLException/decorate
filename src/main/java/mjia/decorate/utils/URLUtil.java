package mjia.decorate.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class URLUtil {
    @Value("${url.retrieve.remote}")
    private String retrieveRemote;

    public Boolean getRemote() {
        return "1".equals(retrieveRemote);
    }
}
