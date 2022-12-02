package mjia.decorate.entity.openapi;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingCartOpenVo {
    private String id;
    private List<StoreGood> storeGoods = new ArrayList();

    @Data
    public static class StoreGood {
        private String storeId;
        private String storeName;
        private List<PromotionGoods> promotionGoodsList = new ArrayList();
    }

    @Data
    public static class PromotionGoods {
        private String promotionId;
        private String description;
        private List<GoodsPromotion> goodsPromotionList = new ArrayList();
    }

    @Data
    public static class GoodsPromotion {
        private String spuId;
        private Integer quantity;
        private String thumb;
        private String categoryName;
        private String title;
        private String description;
        private String price;
        private String size;
    }
}

