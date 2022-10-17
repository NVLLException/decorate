package mjia.decorate.controller.manage;

import lombok.extern.slf4j.Slf4j;
import mjia.decorate.controller.DefaultCallback;
import mjia.decorate.controller.OperateTemplate;
import mjia.decorate.entity.BaseResponse;
import mjia.decorate.entity.MaterialCategoryVo;
import mjia.decorate.entity.MaterialGroupVo;
import mjia.decorate.entity.MaterialVo;
import mjia.decorate.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static mjia.decorate.enums.BizTypeEnum.*;

@Slf4j
@RestController
@RequestMapping("/manage/delete")
public class DeleteController {

    @Autowired
    private MaterialService materialService;

    @RequestMapping("/deleteMaterial")
    public BaseResponse deleteMaterial(@RequestParam("id") String id) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(log, response, DELETE_MATERIAL, new DefaultCallback() {
            @Override
            public void execute() {
                materialService.deleteMaterial(id);
                response.setData(true);
            }
        });
    }

    @RequestMapping("/deleteCategory")
    public BaseResponse deleteCategory(@RequestParam("id") String id) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(log, response, DELETE_CATEGORY, new DefaultCallback() {
            @Override
            public void execute() {
                materialService.deleteCategory(id);
                response.setData(true);
            }
        });
    }

    @RequestMapping("/deleteGroup")
    public BaseResponse deleteGroup(@RequestParam("id") String id) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(log, response, DELETE_GROUP, new DefaultCallback() {
            @Override
            public void execute() {
                materialService.deleteGroup(id);
                response.setData(true);
            }
        });
    }

    @RequestMapping("/deleteUrl")
    public BaseResponse deleteUrl(@RequestParam("id") String id) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(log, response, DELETE_URL, new DefaultCallback() {
            @Override
            public void execute() {
                materialService.deleteUrl(id);
                response.setData(true);
            }
        });
    }
}
