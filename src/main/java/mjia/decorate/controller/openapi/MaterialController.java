package mjia.decorate.controller.openapi;

import mjia.decorate.controller.CommonCallback;
import mjia.decorate.controller.DefaultCallback;
import mjia.decorate.controller.OperateTemplate;
import mjia.decorate.entity.BaseResponse;
import mjia.decorate.enums.BizTypeEnum;
import mjia.decorate.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static mjia.decorate.enums.BizTypeEnum.*;

@RestController
@RequestMapping("/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @RequestMapping("/listMaterialByGroupId")
    public BaseResponse queryMaterialVoListByGroupId(@RequestParam("groupId") String groupId) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(response, QUERY_MATERIAL, new DefaultCallback() {
            @Override
            public void execute() {
                response.setData(materialService.listMaterialByGroupId(groupId));
            }
        });
    }

    @RequestMapping("/listByCategoryId")
    public BaseResponse queryGroupVoListByGroupId(@RequestParam("categoryId") String categoryId) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(response, QUERY_CATEGORY, new DefaultCallback() {
            @Override
            public void execute() {
                response.setData(materialService.listMaterialGroupByCatetoryId(categoryId));
            }
        });
    }

    @RequestMapping("/listCategory")
    public BaseResponse listGroup() {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(response, QUERY_GROUP, new DefaultCallback() {
            @Override
            public void execute() {
                response.setData(materialService.listCategory());
            }
        });
    }
}
