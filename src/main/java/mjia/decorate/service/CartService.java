package mjia.decorate.service;

import mjia.decorate.entity.BaseResponse;
import mjia.decorate.entity.openapi.AddCartOpenVo;
import mjia.decorate.entity.openapi.ShoppingCartOpenVo;

import java.util.List;

public interface CartService {
    BaseResponse addCart(AddCartOpenVo addCartOpenVo);

    AddCartOpenVo queryCart(AddCartOpenVo addCartOpenVo);

    ShoppingCartOpenVo queryCartList(AddCartOpenVo addCartOpenVo);

    void removeCart(AddCartOpenVo addCartOpenVo);
}
