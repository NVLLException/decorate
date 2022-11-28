package mjia.decorate.controller.openapi;

import lombok.extern.slf4j.Slf4j;
import mjia.decorate.controller.DefaultCallback;
import mjia.decorate.controller.OperateTemplate;
import mjia.decorate.entity.BaseResponse;
import mjia.decorate.entity.openapi.AddCartOpenVo;
import mjia.decorate.service.CartService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static mjia.decorate.enums.BizTypeEnum.*;

@Slf4j
@RestController
@RequestMapping("/openApi/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/addShop")
    public BaseResponse addShoppingCart(@RequestBody AddCartOpenVo addCartOpenVo) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(log, response, ADD_SHOP_CART, new DefaultCallback() {
            @Override
            public void before() {
                Assert.notNull(addCartOpenVo, "参数为空");
                Assert.notNull(addCartOpenVo.getWxUserId(), "参数为空");
            }
            @Override
            public void execute() {
                cartService.addCart(addCartOpenVo);
                response.setSuccess(true);
            }
        });
    }

    @RequestMapping("/removeShop")
    public BaseResponse removeShoppingCart(@RequestBody AddCartOpenVo addCartOpenVo) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(log, response, REMOVE_SHOP_CART, new DefaultCallback() {
            @Override
            public void before() {
                Assert.notNull(addCartOpenVo, "参数为空");
                Assert.notNull(addCartOpenVo.getWxUserId(), "参数为空");
            }

            @Override
            public void execute() {
                cartService.removeCart(addCartOpenVo);
                response.setSuccess(true);
            }
        });
    }

    @RequestMapping("/queryCartList")
    public BaseResponse queryShoppingCartList(@RequestBody AddCartOpenVo addCartOpenVo) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(log, response, QUERY_SHOP_CART_LIST, new DefaultCallback() {
            @Override
            public void before() {
                Assert.notNull(addCartOpenVo, "参数为空");
                Assert.notNull(addCartOpenVo.getWxUserId(), "参数为空");
            }

            @Override
            public void execute() {
                response.setData(cartService.queryCartList(addCartOpenVo));
                response.setSuccess(true);
            }
        });
    }
}