package mjia.decorate.service;

import mjia.decorate.entity.openapi.AddCartOpenVo;

import java.util.List;

public interface CartService {
    void addCart(AddCartOpenVo addCartOpenVo);

    AddCartOpenVo queryCart(AddCartOpenVo addCartOpenVo);

    List<AddCartOpenVo> queryCartList(AddCartOpenVo addCartOpenVo);

    void removeCart(AddCartOpenVo addCartOpenVo);
}
