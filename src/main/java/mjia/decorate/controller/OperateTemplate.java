package mjia.decorate.controller;

import mjia.decorate.entity.BaseResponse;
import mjia.decorate.enums.BizTypeEnum;
import org.slf4j.Logger;

public class OperateTemplate {
    public static <T extends BaseResponse> T invoke( Logger log, BaseResponse baseResponse, BizTypeEnum bizTypeEnum, CommonCallback commonCallback) {
        try{
            commonCallback.before();

            commonCallback.execute();
            baseResponse.setSuccess(true);
            log.info("execute method: [{}]", bizTypeEnum.getName());
        } catch (Exception e) {
            log.error("execute method [{}] exception:%s", bizTypeEnum.getName(), e.getMessage());
            e.printStackTrace();
            baseResponse.setSuccess(false);
            baseResponse.setErrorCode(bizTypeEnum.getCode());
            baseResponse.setErrorMessage(bizTypeEnum.getName());
        } finally {
            commonCallback.after();
            log.info("after execute method: [{}]", bizTypeEnum.getName());
        }
        return (T) baseResponse;
    }
}
