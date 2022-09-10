package mjia.decorate.controller.manage;

import mjia.decorate.entity.BaseResponse;
import mjia.decorate.entity.MaterialCategoryVo;
import mjia.decorate.entity.MaterialGroupVo;
import mjia.decorate.entity.MaterialVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/delete")
public class DeleteController {
    @RequestMapping("/deleteMaterial")
    public BaseResponse updateMaterial(@RequestBody MaterialVo materialVo) {
        //todo;
        return null;
    }

    @RequestMapping("/deleteCategory")
    public BaseResponse updateCategory(@RequestBody MaterialCategoryVo categoryVo) {
        //todo
        return null;
    }

    @RequestMapping("/deleteGroup")
    public BaseResponse updateGroup(@RequestBody MaterialGroupVo groupVo) {
        //todo
        return null;
    }
}
