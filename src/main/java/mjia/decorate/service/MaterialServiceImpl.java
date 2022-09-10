package mjia.decorate.service;

import mjia.decorate.entity.MaterialCategoryVo;
import mjia.decorate.entity.MaterialGroupVo;
import mjia.decorate.entity.MaterialVo;
import mjia.decorate.entity.UrlVo;
import mjia.decorate.enums.URLTypeEnum;
import mjia.decorate.mapper.MaterialMapper;
import mjia.decorate.mapper.URLMapper;
import mjia.decorate.utils.URLUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MaterialServiceImpl implements MaterialService{

    @Autowired
    private URLUtil urlUtil;

    @Resource
    private MaterialMapper materialMapper;

    @Resource
    private URLMapper urlMapper;

    @Override
    public List<MaterialVo> listMaterialByCategoryId(String categoryId) {
        List<MaterialVo> materialVos = materialMapper.queryMaterialList(categoryId);
        if (CollectionUtils.isEmpty(materialVos)) {
            return materialVos;
        }
        //查询图片URL
        List<String> materialIdList = new ArrayList();
        materialVos.forEach(materialVo -> {
            materialIdList.add(materialVo.getId());
        });
        List<UrlVo> urlVos = urlMapper.queryListByReferIdAndType(StringUtils.join(materialIdList, ","), URLTypeEnum.MATERIAL.getCode());
        materialVos.forEach(materialVo -> {
            urlVos.forEach(urlVo -> {
                if (materialVo.getId().equals(urlVo.getReferId())) {
                    String url = urlUtil.getRemote() ? urlVo.getRemoteUrl() : urlVo.getLocalUrl();
                    if (Objects.isNull(materialVo.getUrlList())) {
                        materialVo.setUrlList(new ArrayList());
                    }
                    materialVo.getUrlList().add(url);
                }
            });
        });
        return materialVos;
    }

    @Override
    public List<MaterialCategoryVo> listMaterialCategoryByGroupId(String groupId) {
        List<MaterialCategoryVo> categoryVos = materialMapper.listMaterialCategoryByGroupId(groupId);
        if (CollectionUtils.isEmpty(categoryVos)) {
            return categoryVos;
        }
        //查询图片
        List<String> categoryIdList = new ArrayList();
        categoryVos.forEach(categoryVo -> {
            categoryIdList.add(categoryVo.getId());
        });
        List<UrlVo> urlVos = urlMapper.queryListByReferIdAndType(StringUtils.join(categoryIdList, ","), URLTypeEnum.CATEGORY.getCode());
        categoryVos.forEach(categoryVo -> {
            urlVos.forEach(urlVo -> {
                if (categoryVo.getId().equals(urlVo.getReferId())) {
                    String url = urlUtil.getRemote() ? urlVo.getRemoteUrl() : urlVo.getLocalUrl();
                    if (Objects.isNull(categoryVo.getUrlList())) {
                        categoryVo.setUrlList(new ArrayList());
                    }
                    categoryVo.getUrlList().add(url);
                }
            });
        });
        return categoryVos;
    }

    @Override
    public List<MaterialGroupVo> listGroup() {
        return materialMapper.listGroup();
    }

    @Override
    public List<MaterialVo> backEndListMaterialByCategoryId(String categoryId) {
        List<MaterialVo> materialVos = materialMapper.queryMaterialList(categoryId);
        if (CollectionUtils.isEmpty(materialVos)) {
            return materialVos;
        }
        //查询图片URL
        List<String> materialIdList = new ArrayList();
        materialVos.forEach(materialVo -> {
            materialIdList.add(materialVo.getId());
        });
        List<UrlVo> urlVos = urlMapper.queryListByReferIdAndType(StringUtils.join(materialIdList, ","), URLTypeEnum.MATERIAL.getCode());
        materialVos.forEach(materialVo -> {
            urlVos.forEach(urlVo -> {
                if (materialVo.getId().equals(urlVo.getReferId())) {
                    if (Objects.isNull(materialVo.getUrlVoList())) {
                        materialVo.setUrlVoList(new ArrayList());
                    }
                    materialVo.getUrlVoList().add(urlVo);
                }
            });
        });
        return materialVos;
    }

    @Override
    public List<MaterialCategoryVo> backEndListMaterialCategoryByGroupId(String groupId) {
        List<MaterialCategoryVo> categoryVos = materialMapper.listMaterialCategoryByGroupId(groupId);
        if (CollectionUtils.isEmpty(categoryVos)) {
            return categoryVos;
        }
        //查询图片
        List<String> categoryIdList = new ArrayList();
        categoryVos.forEach(categoryVo -> {
            categoryIdList.add(categoryVo.getId());
        });
        List<UrlVo> urlVos = urlMapper.queryListByReferIdAndType(StringUtils.join(categoryIdList, ","), URLTypeEnum.CATEGORY.getCode());
        categoryVos.forEach(categoryVo -> {
            urlVos.forEach(urlVo -> {
                if (categoryVo.getId().equals(urlVo.getReferId())) {
                    if (Objects.isNull(categoryVo.getUrlVoList())) {
                        categoryVo.setUrlVoList(new ArrayList());
                    }
                    categoryVo.getUrlVoList().add(urlVo);
                }
            });
        });
        return categoryVos;
    }

    @Override
    public List<MaterialGroupVo> backEndListGroup() {
        return listGroup();
    }


    @Override
    public Integer saveMaterial(MaterialVo materialVo) {
        if (StringUtils.isEmpty(materialVo.getId())) {
            return materialMapper.insertMaterial(materialVo);
        } else {
            materialMapper.updateMaterial(materialVo);
            return Integer.valueOf(materialVo.getId());
        }
    }

    @Override
    public Integer saveCategory(MaterialCategoryVo categoryVo) {
        if (StringUtils.isEmpty(categoryVo.getId())) {
            return materialMapper.insertCategory(categoryVo);
        } else {
            materialMapper.updateCategory(categoryVo);
            return Integer.valueOf(categoryVo.getId());
        }
    }

    @Override
    public Integer saveGroup(MaterialGroupVo groupVo) {
        if (StringUtils.isEmpty(groupVo.getId())) {
            return materialMapper.insertGroup(groupVo);
        } else {
            materialMapper.updateGroup(groupVo);
            return Integer.valueOf(groupVo.getId());
        }
    }

    @Override
    public Integer saveUrl(UrlVo urlVo) {
        return materialMapper.insertUrl(urlVo);
    }


    @Override
    public boolean deleteMaterial(String materialId) {
        urlMapper.deleteUrl(materialId, URLTypeEnum.MATERIAL.getCode());
        materialMapper.deleteMaterials(materialId);
        return true;
    }

    @Override
    public boolean deleteCategory(String categoryId) {
        //先删除material
        //1.查询materialIdList
        List<String> materialIds = materialMapper.queryMaterialIdsByCategoryId(categoryId);
        deleteMaterial(StringUtils.join(materialIds, ","));

        //删除category
        urlMapper.deleteUrl(categoryId, URLTypeEnum.CATEGORY.getCode());
        materialMapper.deleteCategorys(categoryId);
        return true;
    }

    @Override
    public boolean deleteGroup(String groupId) {
        //先删除category
        //1.查询categoryIdList
        List<String> categoryIds = materialMapper.queryCategoryIdsByGroupId(groupId);
        deleteCategory(StringUtils.join(categoryIds, ","));

        //删除group
        urlMapper.deleteUrl(groupId, URLTypeEnum.GROUP.getCode());
        materialMapper.deleteGroups(groupId);
        return true;
    }

    @Override
    public boolean deleteUrl(String id) {
        urlMapper.deleteUrlById(id);
        return true;
    }


}
