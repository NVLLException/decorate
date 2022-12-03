package mjia.decorate.controller;

import mjia.decorate.entity.BaseResponse;
import mjia.decorate.enums.BizTypeEnum;
import org.slf4j.Logger;
import org.springframework.util.StopWatch;

public class OperateTemplate {
    public static <T extends BaseResponse> T invoke( Logger log, BaseResponse baseResponse, BizTypeEnum bizTypeEnum, CommonCallback commonCallback) {
        StopWatch stopWatch = new StopWatch();
        try{
            stopWatch.start();
            commonCallback.before();

            commonCallback.execute();
            log.info("execute method: [{}]", bizTypeEnum.getName());
        } catch (Exception e) {
            log.error("execute method [{}] exception:[{}]", bizTypeEnum.getName(), e.getMessage());
            e.printStackTrace();
            baseResponse.setSuccess(false);
            baseResponse.setErrorCode(bizTypeEnum.getCode());
            baseResponse.setErrorMessage(bizTypeEnum.getName());
        } finally {
            commonCallback.after();
            stopWatch.stop();
            log.info("after execute method: [{}], time:[{}]", bizTypeEnum.getName(), stopWatch.getTotalTimeMillis() + "ms");
        }
        return (T) baseResponse;
    }
}
