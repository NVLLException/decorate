package mjia.decorate.service;

import mjia.decorate.entity.MaterialCategoryVo;
import mjia.decorate.entity.MaterialGroupVo;
import mjia.decorate.entity.MaterialVo;

import java.util.List;

public interface MaterialService {
    List<MaterialVo> listMaterialByCategoryId(String categoryId);
    List<MaterialCategoryVo> listMaterialCategoryByGroupId(String groupId);
    List<MaterialGroupVo> listGroup();
    Integer saveMaterial(MaterialVo materialVo);
    Integer saveCategory(MaterialCategoryVo categoryVo);
    Integer saveGroup(MaterialGroupVo groupVo);
}
