package mjia.decorate.controller.openapi.utils;

import mjia.decorate.entity.UrlVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PictureUtil {
    @Value("${url.retrieve.remote}")
    private String retrieveRemote;

    @Value("${url.retrieve.domain}")
    private String domain;

    @Value("${url.retrieve.remote.domain}")
    private String remoteDomain;

    public String getThumbnailUrL(List<UrlVo> urlVoList) {
        if (CollectionUtils.isEmpty(urlVoList)) {
            return "";
        }
        UrlVo urlVo = urlVoList.get(0);
        if ("1".equals(retrieveRemote)) {
            return remoteDomain + urlVo.getFileName();
        } else {
            return domain + urlVo.getFileName();
        }
    }
}
