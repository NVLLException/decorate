package mjia.decorate.service;

import mjia.decorate.entity.MaterialCategoryVo;
import mjia.decorate.entity.MaterialGroupVo;
import mjia.decorate.entity.MaterialVo;

import java.util.List;

public interface MaterialService {
    List<MaterialVo> listMaterialByGroupId(String groupId);
    List<MaterialGroupVo> listMaterialGroupByCatetoryId(String materialId);
    List<MaterialCategoryVo> listCategory();
}
