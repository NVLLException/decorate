package mjia.decorate.controller.openapi;

import lombok.extern.slf4j.Slf4j;
import mjia.decorate.controller.DefaultCallback;
import mjia.decorate.controller.OperateTemplate;
import mjia.decorate.entity.*;
import mjia.decorate.entity.openapi.MaterialCategoryOpenVo;
import mjia.decorate.entity.openapi.MaterialGroupOpenVo;
import mjia.decorate.entity.openapi.MaterialGroupParentOpenVo;
import mjia.decorate.service.MaterialService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static mjia.decorate.enums.BizTypeEnum.*;

@Slf4j
@RestController
@RequestMapping("/openApi/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Value("${url.retrieve.remote}")
    private String retrieveRemote;

    @Value("${url.retrieve.domain}")
    private String domain;

    @RequestMapping("/listMaterialByCategoryId")
    public BaseResponse queryMaterialVoListByCategoryId(@RequestParam("categoryId") String categoryId) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(log, response, QUERY_MATERIAL, new DefaultCallback() {
            @Override
            public void execute() {
                response.setData(materialService.listMaterialByCategoryId(categoryId));
            }
        });
    }

    @RequestMapping("/listCategoryByGroupId")
    public BaseResponse queryCategoryVoListByGroupId(@RequestParam("groupId") String groupId) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(log, response, QUERY_CATEGORY, new DefaultCallback() {
            @Override
            public void execute() {
                response.setData(materialService.listMaterialCategoryByGroupId(groupId));
            }
        });
    }

    @RequestMapping("/listGroup")
    public BaseResponse listGroup() {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(log, response, QUERY_GROUP, new DefaultCallback() {
            @Override
            public void execute() {
                List<MaterialGroupVo> groupList = materialService.listGroup();
                groupList.forEach(groupVo -> {
                    groupVo.setCategoryList(materialService.listMaterialCategoryByGroupId(groupVo.getId()));
                });
                response.setData(convertGroupVo(groupList));
            }
        });
    }

    private List<MaterialGroupParentOpenVo> convertGroupVo(List<MaterialGroupVo> groupVoList) {
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

    private List<MaterialCategoryOpenVo> convertCategoryVo(String groupId, List<MaterialCategoryVo> categoryVoList) {
        try{
            if (CollectionUtils.isEmpty(categoryVoList)) {
                return null;
            }
            List<MaterialCategoryOpenVo> categoryOpenVoList = new ArrayList();
            categoryVoList.forEach(categoryVo -> {
                MaterialCategoryOpenVo categoryOpenVo = new MaterialCategoryOpenVo();
                categoryOpenVo.setGroupId(groupId);
                categoryOpenVo.setName(categoryVo.getName());
                categoryOpenVo.setThumbnail(getThumbnailUrL(categoryVo.getUrlVoList()));
                categoryOpenVoList.add(categoryOpenVo);
            });
            return categoryOpenVoList;
        } catch (Exception e) {
            log.error("convertCategoryVo error: ", e);
            return null;
        }
    }

    private String getThumbnailUrL(List<UrlVo> urlVoList) {
        if (CollectionUtils.isEmpty(urlVoList)) {
            return "";
        }
        UrlVo urlVo = urlVoList.get(0);
        if ("1".equals(retrieveRemote)) {
            return "";
        } else {
            return domain + urlVo.getFileName();
        }
    }
}
