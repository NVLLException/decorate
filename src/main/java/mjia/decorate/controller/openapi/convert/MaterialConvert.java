package mjia.decorate.controller.openapi.convert;

import lombok.extern.slf4j.Slf4j;
import mjia.decorate.controller.openapi.utils.PictureUtil;
import mjia.decorate.entity.MaterialGroupVo;
import mjia.decorate.entity.MaterialVo;
import mjia.decorate.entity.openapi.MaterialDetailOpenVo;
import mjia.decorate.entity.openapi.MaterialListOpenVo;
import mjia.decorate.entity.openapi.MaterialOpenVo;
import org.apache.commons.collections4.CollectionUtils;
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
        detail.setImages(pictureUtil.getThumbnailUrLList(materialVo.getUrlVoList()));
        return detail;
    }
}
