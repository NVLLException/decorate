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
        //todo
        return null;
    }

    @RequestMapping("/saveGroup")
    public BaseResponse saveGroup(@RequestBody MaterialGroupVo groupVo) {
        //todo
        return null;
    }
}
