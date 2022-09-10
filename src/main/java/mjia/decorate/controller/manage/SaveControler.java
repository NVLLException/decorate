package mjia.decorate.controller.manage;

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
import org.springframework.web.bind.annotation.RestController;

import static mjia.decorate.enums.BizTypeEnum.*;

@RestController
@RequestMapping("/manage/save")
public class SaveControler {

    @Autowired
    private MaterialService materialService;

    @RequestMapping("/saveMaterial")
    public BaseResponse saveMaterial(@RequestBody MaterialVo materialVo) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(response, SAVE_MATERIAL, new DefaultCallback() {
            @Override
            public void execute() {
                materialService.saveMaterial(materialVo);
                response.setData(materialVo.getId());
            }
        });

    }

    @RequestMapping("/saveCategory")
    public BaseResponse saveCategory(@RequestBody MaterialCategoryVo categoryVo) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(response, SAVE_CATEGORY, new DefaultCallback() {
            @Override
            public void execute() {
                materialService.saveCategory(categoryVo);
                response.setData(categoryVo.getId());
            }
        });
    }

    @RequestMapping("/saveGroup")
    public BaseResponse saveGroup(@RequestBody MaterialGroupVo groupVo) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(response, SAVE_GROUP, new DefaultCallback() {
            @Override
            public void execute() {
                materialService.saveGroup(groupVo);
                response.setData(groupVo.getId());
            }
        });
    }
}
