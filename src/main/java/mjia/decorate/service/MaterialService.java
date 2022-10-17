package mjia.decorate.service;

import mjia.decorate.entity.MaterialCategoryVo;
import mjia.decorate.entity.MaterialGroupVo;
import mjia.decorate.entity.MaterialVo;
import mjia.decorate.entity.UrlVo;
import mjia.decorate.enums.URLTypeEnum;

import java.util.List;

public interface MaterialService {
    List<MaterialVo> listMaterialByCategoryId(String categoryId);
    List<MaterialCategoryVo> listMaterialCategoryByGroupId(String groupId);
    List<MaterialGroupVo> listGroup();

    List<MaterialVo> backEndListMaterialByCategoryId(String categoryId);
    List<MaterialCategoryVo> backEndListMaterialCategoryByGroupId(String groupId);
    List<MaterialGroupVo> backEndListGroup();
    Integer saveMaterial(MaterialVo materialVo);
    Integer saveCategory(MaterialCategoryVo categoryVo);
    Integer saveGroup(MaterialGroupVo groupVo);
    Integer saveUrl(UrlVo urlVo);

    boolean deleteMaterial(String materialId);
    boolean deleteCategory(String categoryId);
    boolean deleteGroup(String groupId);
    boolean deleteUrl(String id);

    MaterialCategoryVo queryCategoryById(String id);
    MaterialVo queryMaterialById(String id);
}
