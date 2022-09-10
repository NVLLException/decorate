package mjia.decorate.controller.manage;

import mjia.decorate.entity.*;
import mjia.decorate.enums.BizTypeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequestMapping("/manage/upload")
public class UploadController {

    @Value("${material.file.path}")
    private String materialFilePath;

    @Value("${category.file.path}")
    private String categoryFilePath;

    @Value("${group.file.path}")
    private String groupFilePath;

    @RequestMapping("/uploadMaterial")
    public BaseResponse uploadMaterial(@RequestParam MultipartFile file, @RequestBody BaseRequest<MaterialVo> request) {
        MaterialVo vo = request.getData();
        //todo save data
        return saveFile(file, materialFilePath, vo.getId());
    }

    @RequestMapping("/uploadCategory")
    public BaseResponse uploadCategory(@RequestParam MultipartFile file, @RequestBody BaseRequest<MaterialCategoryVo> request) {
        MaterialCategoryVo vo = request.getData();
        //todo save data
        return saveFile(file, categoryFilePath, vo.getId());
    }

    @RequestMapping("/uploadGroup")
    public BaseResponse uploadGroup(@RequestParam MultipartFile file, @RequestBody BaseRequest<MaterialGroupVo> request) {
        MaterialGroupVo vo = request.getData();
        //todo save data
        return saveFile(file, groupFilePath, vo.getId());
    }

    private BaseResponse saveFile(MultipartFile file, String filePath, String id) {
        if (file.isEmpty()) {
            return BaseResponse.builder()
                    .success(false)
                    .errorCode(BizTypeEnum.FILE_EMPTY.getCode())
                    .errorMessage(BizTypeEnum.FILE_EMPTY.getName())
                    .build();
        }
        try {
            File path = new File(filePath);
            if (!path.exists()) {
                path.mkdir();
            }
            file.transferTo(new File(filePath, id +"."+ file.getName().split("\\.")));
            return BaseResponse.builder().success(true).build();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.builder()
                    .success(false)
                    .errorCode(BizTypeEnum.FILE_EMPTY.getCode())
                    .errorMessage(BizTypeEnum.FILE_EMPTY.getName())
                    .build();
        }
    }
}
