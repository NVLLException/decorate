package mjia.decorate.controller.openapi;

import lombok.extern.slf4j.Slf4j;
import mjia.decorate.controller.CommonCallback;
import mjia.decorate.controller.DefaultCallback;
import mjia.decorate.controller.OperateTemplate;
import mjia.decorate.entity.BaseResponse;
import mjia.decorate.entity.MaterialVo;
import mjia.decorate.enums.BizTypeEnum;
import mjia.decorate.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static mjia.decorate.enums.BizTypeEnum.*;

@Slf4j
@RestController
@RequestMapping("/openApi/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

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
                response.setData(materialService.listGroup());
            }
        });
    }
}
