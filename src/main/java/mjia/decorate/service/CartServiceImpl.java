package mjia.decorate.service;

import mjia.decorate.entity.openapi.AddCartOpenVo;
import mjia.decorate.mapper.CartMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class CartServiceImpl implements CartService{

    @Value("${add.cart.max.item}")
    private String maxCount;

    @Resource
    private CartMapper cartMapper;

    @Override
    public void addCart(AddCartOpenVo addCartOpenVo) {
        AddCartOpenVo tempCartOpenVo = queryCart(addCartOpenVo);
        if (addCartOpenVo.getCount() == null || addCartOpenVo.getCount() <= 0) {
            return;
        }
        //更新购物车
        if (Objects.nonNull(tempCartOpenVo)) {
            addCartOpenVo.setCount(tempCartOpenVo.getCount() + addCartOpenVo.getCount());
            cartMapper.updateCart(addCartOpenVo);
        }
        if (addCartOpenVo.getCount() > Integer.valueOf(maxCount)) {
            addCartOpenVo.setCount(Integer.valueOf(maxCount));
        }
        //添加购物车
        cartMapper.addCart(addCartOpenVo);
    }

    @Override
    public AddCartOpenVo queryCart(AddCartOpenVo addCartOpenVo) {
        AddCartOpenVo result = cartMapper.queryCart(addCartOpenVo);
        return result;
    }

    @Override
    public List<AddCartOpenVo> queryCartList(AddCartOpenVo addCartOpenVo) {
        return cartMapper.queryCartList(addCartOpenVo);
    }

    @Override
    public void removeCart(AddCartOpenVo addCartOpenVo) {
        AddCartOpenVo tempCartOpenVo = queryCart(addCartOpenVo);
        if (addCartOpenVo.getCount() == null || addCartOpenVo.getCount() >= 0) {
            return;
        }
        addCartOpenVo.setCount(tempCartOpenVo.getCount() - addCartOpenVo.getCount());
        if (addCartOpenVo.getCount() <= 0) {
            addCartOpenVo.setCount(0);
        }
        cartMapper.updateCart(addCartOpenVo);
    }

}
