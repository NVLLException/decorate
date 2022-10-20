package mjia.decorate.controller.openapi;

import lombok.extern.slf4j.Slf4j;
import mjia.decorate.controller.DefaultCallback;
import mjia.decorate.controller.OperateTemplate;
import mjia.decorate.controller.openapi.convert.GroupConvert;
import mjia.decorate.controller.openapi.convert.MaterialConvert;
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

    @Autowired
    private GroupConvert groupConvert;

    @Autowired
    private MaterialConvert materialConvert;

    @RequestMapping("/listMaterialByCategoryId")
    public BaseResponse queryMaterialVoListByCategoryId(@RequestParam("categoryId") String categoryId) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(log, response, QUERY_MATERIAL, new DefaultCallback() {
            @Override
            public void execute() {
                response.setData(materialConvert.convertMaterial(materialService.listMaterialByCategoryId(categoryId)));
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
                response.setData(groupConvert.convertGroupVo(groupList));
            }
        });
    }




}
