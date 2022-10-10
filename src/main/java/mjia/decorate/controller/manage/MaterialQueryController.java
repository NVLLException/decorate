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
@RequestMapping("/manage/query")
public class MaterialQueryController {
    @Autowired
    private MaterialService materialService;

    @RequestMapping("/queryCategory")
    public BaseResponse queryCategory(@RequestParam("id") String id) {
        BaseResponse response = BaseResponse.builder().build();
        return OperateTemplate.invoke(response, BACK_QUERY_CATEGORY_DETAIL, new DefaultCallback() {
            @Override
            public void execute() {
                response.setData(materialService.queryCategoryById(id));
            }
        });
    }
}
