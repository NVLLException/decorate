package mjia.decorate.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BaseResponse<T> {
    private Boolean success;
    private String errorCode;
    private String errorMessage;
    private T data;
}
