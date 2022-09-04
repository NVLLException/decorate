package mjia.decorate.controller;

import mjia.decorate.entity.BaseResponse;
import mjia.decorate.enums.BizTypeEnum;

public class OperateTemplate {
    public static <T extends BaseResponse> T invoke(BaseResponse baseResponse, BizTypeEnum bizTypeEnum, CommonCallback commonCallback) {
        try{
            commonCallback.before();

            commonCallback.execute();
            baseResponse.setSuccess(true);
        } catch (Exception e) {
            baseResponse.setSuccess(false);
            baseResponse.setErrorCode(bizTypeEnum.getCode());
            baseResponse.setErrorMessage(bizTypeEnum.getName());
        } finally {
            commonCallback.after();
        }
        return (T) baseResponse;
    }
}
