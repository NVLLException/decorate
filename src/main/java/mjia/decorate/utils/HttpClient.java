package mjia.decorate.utils;

import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
public class HttpClient {
    public String doGet(String url, Map<String, String> param) {
        try {
            if (MapUtils.isNotEmpty(param)) {
                url += "?";
                for (Map.Entry<String, String> entry : param.entrySet()) {
                    url = url + entry.getKey() + "=" + entry.getValue();
                }
            }
            RestTemplate template = new RestTemplate();
            log.info("url: " + url);
            ResponseEntity<String> responseEntity = template.exchange(url, HttpMethod.GET, null, String.class);
            if(responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            }
            log.error("HttpClient doGet response status code=", responseEntity.getStatusCode());
        } catch (Exception e) {
            log.error("HttpClient doGet error: ", e);
            return null;
        }
        return null;
    }
}
