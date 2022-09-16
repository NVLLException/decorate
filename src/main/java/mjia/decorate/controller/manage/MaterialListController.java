package mjia.decorate.controller.manage;

import mjia.decorate.controller.DefaultCallback;
import mjia.decorate.controller.OperateTemplate;
import mjia.decorate.entity.BaseResponse;
import mjia.decorate.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static mjia.decorate.enums.BizTypeEnum.*;

@RestController
@RequestMapping("/manage/list")
public class MaterialListController {

    @Autowired
    private MaterialService materialService;

    @RequestMapping("/listGroup")
    public BaseResponse listGroup() {
       BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(response, BACK_QUERY_GROUP, new DefaultCallback() {
            @Override
            public void execute() {
                response.setData(materialService.backEndListGroup());
            }
        });
    }

    @RequestMapping("/listCategory")
    public BaseResponse listCategory(@RequestParam("groupId") String groupId) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(response, BACK_QUERY_CATEGORY, new DefaultCallback() {
            @Override
            public void execute() {
                response.setData(materialService.backEndListMaterialCategoryByGroupId(groupId));
            }
        });
    }

    @RequestMapping("/listMaterial")
    public BaseResponse listMaterial(@RequestParam("categoryId") String categoryId) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(response, BACK_QUERY_MATERIAL, new DefaultCallback() {
            @Override
            public void execute() {
                response.setData(materialService.backEndListMaterialByCategoryId(categoryId));
            }
        });
    }

/*    @RequestMapping("/listUrl")
    public BaseResponse listUrl(String materialId) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(response, BACK_QUERY_MATERIAL, new DefaultCallback() {
            @Override
            public void execute() {
                response.setData(materialService.listUrl(materialId));
            }
        });
    }*/
}
