package mjia.decorate.service;

import mjia.decorate.entity.openapi.AddCartOpenVo;
import mjia.decorate.entity.openapi.ShoppingCartOpenVo;

import java.util.List;

public interface CartService {
    void addCart(AddCartOpenVo addCartOpenVo);

    AddCartOpenVo queryCart(AddCartOpenVo addCartOpenVo);

    ShoppingCartOpenVo queryCartList(AddCartOpenVo addCartOpenVo);

    void removeCart(AddCartOpenVo addCartOpenVo);
}
