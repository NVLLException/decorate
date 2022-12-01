package mjia.decorate.service;

import mjia.decorate.controller.openapi.utils.PictureUtil;
import mjia.decorate.entity.MaterialCategoryVo;
import mjia.decorate.entity.MaterialGroupVo;
import mjia.decorate.entity.MaterialVo;
import mjia.decorate.entity.openapi.AddCartOpenVo;
import mjia.decorate.entity.openapi.ShoppingCartOpenVo;
import mjia.decorate.mapper.CartMapper;
import mjia.decorate.mapper.MaterialMapper;
import mjia.decorate.utils.URLUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CartServiceImpl implements CartService{

    @Value("${add.cart.max.item}")
    private String maxCount;

    @Resource
    private CartMapper cartMapper;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private PictureUtil pictureUtil;

    @Override
    public void addCart(AddCartOpenVo addCartOpenVo) {
        AddCartOpenVo tempCartOpenVo = queryCart(addCartOpenVo);
        if (addCartOpenVo.getCount() == null || addCartOpenVo.getCount() <= 0) {
            return;
        }
        //查询category
        MaterialVo materialVo = materialService.queryMaterial(addCartOpenVo.getMaterialId());
        String categoryId = materialVo.getCategoryId();
        MaterialCategoryVo categoryVo = materialService.queryCategoryById(categoryId);
        String groupId = categoryVo.getGroupId();
        addCartOpenVo.setCategoryId(categoryId);
        addCartOpenVo.setGroupId(groupId);
        //更新购物车
        if (Objects.nonNull(tempCartOpenVo)) {
            addCartOpenVo.setCount(tempCartOpenVo.getCount() + addCartOpenVo.getCount());
            cartMapper.updateCart(addCartOpenVo);
            return;
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
    public ShoppingCartOpenVo queryCartList(AddCartOpenVo addCartOpenVo) {
        List<AddCartOpenVo> cartOpenVoList = cartMapper.queryCartList(addCartOpenVo);
        if (CollectionUtils.isNotEmpty(cartOpenVoList)) {
            Map<String, Integer> materialIdCount = new HashMap();
            Map<String, List<String>> categoryIdMap = new HashMap();
            Map<String, List<String>> groupIdMap = new HashMap();
            cartOpenVoList.forEach(openVo -> {
                //material
                materialIdCount.put(openVo.getMaterialId(), openVo.getCount());
                //category
                if (categoryIdMap.get(openVo.getCategoryId()) == null) {
                    categoryIdMap.put(openVo.getCategoryId(), new ArrayList<>());
                }
                categoryIdMap.get(openVo.getCategoryId()).add(openVo.getMaterialId());
                //group
                if (groupIdMap.get(openVo.getGroupId()) == null) {
                    groupIdMap.put(openVo.getGroupId(), new ArrayList<>());
                }
                groupIdMap.get(openVo.getGroupId()).add(openVo.getCategoryId());
            });

            Map<String, MaterialVo> materialVoMap = new HashMap();
            Map<String, MaterialCategoryVo> categoryVoMap = new HashMap();
            Map<String, MaterialGroupVo> groupVoMap = new HashMap();
            //查询material
            materialIdCount.keySet().forEach(id -> {
                materialVoMap.put(id, materialService.queryMaterial(id));
            });
            //查询category
            categoryIdMap.keySet().forEach(id -> {
                categoryVoMap.put(id, materialService.queryCategoryById(id));
            });
            //查询group
            groupIdMap.keySet().forEach(id -> {
                groupVoMap.put(id, materialService.queryGroup(id));
            });
            ShoppingCartOpenVo result = new ShoppingCartOpenVo();
            result.setStoreGoods(new ArrayList());
            for(Map.Entry<String, MaterialGroupVo> group: groupVoMap.entrySet()) {
                ShoppingCartOpenVo.StoreGood storeGood = new ShoppingCartOpenVo.StoreGood();
                storeGood.setStoreId(group.getKey());
                storeGood.setStoreName(group.getValue().getName());
                storeGood.setPromotionGoodsList(new ArrayList());
                result.getStoreGoods().add(storeGood);

                groupIdMap.get(group.getKey()).forEach(categoryId -> {
                    ShoppingCartOpenVo.PromotionGoods promotionGoods = new ShoppingCartOpenVo.PromotionGoods();
                    promotionGoods.setDescription(categoryVoMap.get(categoryId).getName());
                    promotionGoods.setGoodsPromotionList(new ArrayList());
                    storeGood.getPromotionGoodsList().add(promotionGoods);

                    categoryIdMap.get(categoryId).forEach(materialId -> {
                        ShoppingCartOpenVo.GoodsPromotion goodsPromotion = new ShoppingCartOpenVo.GoodsPromotion();
                        MaterialVo materialVo = materialVoMap.get(materialId);
                        goodsPromotion.setSpuId(materialId);
                        goodsPromotion.setQuantity(materialIdCount.get(materialId));
                        goodsPromotion.setPrice(materialVo.getPrice().toString());
                        List<String> size = new ArrayList();
                        if (StringUtils.isNotBlank(materialVo.getLength())) {
                            size.add(materialVo.getLength());
                        }
                        if (StringUtils.isNotBlank(materialVo.getWidth())) {
                            size.add(materialVo.getWidth());
                        }
                        if (StringUtils.isNotBlank(materialVo.getHigh())) {
                            size.add(materialVo.getHigh());
                        }
                        goodsPromotion.setSize(StringUtils.join(size, " x "));
                        goodsPromotion.setDescription(materialVo.getDescription());
                        goodsPromotion.setTitle(materialVo.getName());

                        goodsPromotion.setThumb(pictureUtil.getThumbnailUrL(materialVo.getUrlVoList()));
                        promotionGoods.getGoodsPromotionList().add(goodsPromotion);
                    });
                });
            }
            return result;
        }
        return null;
    }

    @Override
    public void removeCart(AddCartOpenVo addCartOpenVo) {
        AddCartOpenVo tempCartOpenVo = queryCart(addCartOpenVo);
        if (addCartOpenVo.getCount() == null || addCartOpenVo.getCount() <= 0) {
            return;
        }
        addCartOpenVo.setCount(tempCartOpenVo.getCount() - addCartOpenVo.getCount());
        if (addCartOpenVo.getCount() <= 0) {
            addCartOpenVo.setCount(0);
        }
        cartMapper.updateCart(addCartOpenVo);
    }

}
