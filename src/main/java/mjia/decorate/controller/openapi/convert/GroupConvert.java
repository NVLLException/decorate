package mjia.decorate.controller.openapi.convert;

import lombok.extern.slf4j.Slf4j;
import mjia.decorate.controller.openapi.utils.PictureUtil;
import mjia.decorate.entity.MaterialCategoryVo;
import mjia.decorate.entity.MaterialGroupVo;
import mjia.decorate.entity.openapi.MaterialCategoryOpenVo;
import mjia.decorate.entity.openapi.MaterialGroupOpenVo;
import mjia.decorate.entity.openapi.MaterialGroupParentOpenVo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class GroupConvert {

    @Autowired
    private PictureUtil pictureUtil;

    public List<MaterialGroupParentOpenVo> convertGroupVo(List<MaterialGroupVo> groupVoList) {
        try {
            if (CollectionUtils.isEmpty(groupVoList)) {
                return null;
            }
            List<MaterialGroupOpenVo> groupOpenVoList = new ArrayList();
            groupVoList.forEach(groupVo -> {
                MaterialGroupOpenVo groupOpenVo = new MaterialGroupOpenVo();
                groupOpenVo.setGroupId(groupVo.getId());
                groupOpenVo.setName(groupVo.getName());
                groupOpenVoList.add(groupOpenVo);
                if (CollectionUtils.isEmpty(groupVo.getCategoryList())) {
                    return;
                }
                groupOpenVo.setChildren(convertCategoryVo(groupVo.getId(), groupVo.getCategoryList()));
            });

            List<MaterialGroupParentOpenVo> groupParentOpenVoList = new ArrayList();
            groupOpenVoList.forEach(groupOpenVo -> {
                MaterialGroupParentOpenVo groupParentOpenVo = new MaterialGroupParentOpenVo();
                groupParentOpenVo.setGroupId(groupOpenVo.getGroupId());
                groupParentOpenVo.setName(groupOpenVo.getName());
                groupParentOpenVo.setChildren(Arrays.asList(groupOpenVo));
                groupParentOpenVoList.add(groupParentOpenVo);
            });
            return groupParentOpenVoList;
        } catch (Exception e) {
            log.error("convertGroupVo error: ", e);
            return null;
        }
    }

    public List<MaterialCategoryOpenVo> convertCategoryVo(String groupId, List<MaterialCategoryVo> categoryVoList) {
        try{
            if (CollectionUtils.isEmpty(categoryVoList)) {
                return null;
            }
            List<MaterialCategoryOpenVo> categoryOpenVoList = new ArrayList();
            categoryVoList.forEach(categoryVo -> {
                MaterialCategoryOpenVo categoryOpenVo = new MaterialCategoryOpenVo();
                categoryOpenVo.setGroupId(categoryVo.getId());
                categoryOpenVo.setName(categoryVo.getName());
                categoryOpenVo.setThumbnail(pictureUtil.getThumbnailUrL(categoryVo.getUrlVoList()));
                categoryOpenVoList.add(categoryOpenVo);
            });
            return categoryOpenVoList;
        } catch (Exception e) {
            log.error("convertCategoryVo error: ", e);
            return null;
        }
    }
}
