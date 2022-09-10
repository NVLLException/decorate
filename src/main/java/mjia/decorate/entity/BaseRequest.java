package mjia.decorate.entity;

import lombok.Data;

@Data
public class BaseRequest<T> {
    T data;
}