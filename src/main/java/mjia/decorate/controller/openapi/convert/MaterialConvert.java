package mjia.decorate.controller.openapi.convert;

import lombok.extern.slf4j.Slf4j;
import mjia.decorate.controller.openapi.utils.PictureUtil;
import mjia.decorate.entity.MaterialGroupVo;
import mjia.decorate.entity.MaterialVo;
import mjia.decorate.entity.openapi.MaterialDetailOpenVo;
import mjia.decorate.entity.openapi.MaterialListOpenVo;
import mjia.decorate.entity.openapi.MaterialOpenVo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MaterialConvert {
    @Autowired
    private PictureUtil pictureUtil;

    public List<MaterialListOpenVo> convertMaterial(List<MaterialVo> materialVos) {
        List<MaterialListOpenVo> openVoList = new ArrayList();
        if (CollectionUtils.isEmpty(materialVos)) {
            return openVoList;
        }
        materialVos.forEach(material -> {
            MaterialListOpenVo openVo = new MaterialListOpenVo();
            openVo.setSpuId(material.getId());
            openVo.setTitle(material.getName());
            openVo.setThumb(pictureUtil.getThumbnailUrL(material.getUrlVoList()));
            openVo.setPrice(material.getPrice() != null ? material.getPrice().toString() : "");
            openVo.setDesc(material.getDescription());
            openVo.setLength(material.getLength());
            openVo.setWidth(material.getWidth());
            openVo.setHigh(material.getHigh());

            List<String> size = new ArrayList();
            if (StringUtils.isNotBlank(material.getLength())) {
                size.add(material.getLength());
            }
            if (StringUtils.isNotBlank(material.getWidth())) {
                size.add(material.getWidth());
            }
            if (StringUtils.isNotBlank(material.getHigh())) {
                size.add(material.getHigh());
            }
            openVo.setSize(StringUtils.join(size, " x "));
            openVo.setCount(material.getCartCount() != null ? String.valueOf(material.getCartCount()) : "");
            openVoList.add(openVo);
        });
        return openVoList;
    }

    public MaterialDetailOpenVo convertMaterialDetail(MaterialVo materialVo) {
        MaterialDetailOpenVo detail = new MaterialDetailOpenVo();
        detail.setDesc(materialVo.getDescription());
        detail.setTitle(materialVo.getName());
        detail.setPrice(materialVo.getPrice() != null ? materialVo.getPrice().toString() : "");
        detail.setId(materialVo.getId());
        detail.setLength(materialVo.getLength());
        detail.setHigh(materialVo.getHigh());
        detail.setWidth(materialVo.getWidth());
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
        detail.setSize(StringUtils.join(size, " x "));
        detail.setImages(pictureUtil.getThumbnailUrLList(materialVo.getUrlVoList()));
        return detail;
    }
}
