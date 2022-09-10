package mjia.decorate.controller.manage;

import mjia.decorate.entity.*;
import mjia.decorate.enums.BizTypeEnum;
import mjia.decorate.enums.URLTypeEnum;
import mjia.decorate.service.MaterialService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Value("${file.name.encrypt.salt}")
    private String fileNameSalt;

    @Autowired
    private MaterialService materialService;

    @RequestMapping("/uploadMaterial")
    public BaseResponse uploadMaterial(@RequestParam MultipartFile file, @RequestBody BaseRequest<MaterialVo> request) {
        MaterialVo vo = request.getData();
        //todo save data
        return saveFile(file, materialFilePath, vo.getId());
    }

    @RequestMapping("/uploadCategory")
    public BaseResponse uploadCategory(@RequestParam MultipartFile file, @RequestBody BaseRequest<MaterialCategoryVo> request) {
        MaterialCategoryVo vo = request.getData();
        BaseResponse baseResponse = saveFile(file, categoryFilePath, vo.getId(), URLTypeEnum.CATEGORY);
        if(!baseResponse.getSuccess()) {
            return baseResponse;
        }
        UrlVo urlVo = (UrlVo) baseResponse.getData();
        materialService.saveUrl(urlVo);
        return baseResponse;
    }

    @RequestMapping("/uploadGroup")
    public BaseResponse uploadGroup(@RequestParam MultipartFile file, @RequestBody BaseRequest<MaterialGroupVo> request) {
        MaterialGroupVo vo = request.getData();
        //todo save data
        return null; //saveFile(file, groupFilePath, vo.getId());
    }

    private BaseResponse saveFile(MultipartFile file, String filePath, String id, URLTypeEnum type) {
        UrlVo urlVo = UrlVo.builder().build();
        if (file.isEmpty()) {
            return BaseResponse.builder()
                    .success(false)
                    .errorCode(BizTypeEnum.FILE_EMPTY.getCode())
                    .errorMessage(BizTypeEnum.FILE_EMPTY.getName())
                    .build();
        }
        try {
            id = DigestUtils.md5Hex(fileNameSalt + id);
            File path = new File(filePath);
            if (!path.exists()) {
                path.mkdir();
            }
            String fileName = getFileName(id, type, file.getName().split("\\.")[1]);
            file.transferTo(new File(filePath, fileName));
            urlVo.setFileName(fileName);
            urlVo.setReferId(id);
            urlVo.setType(type.getCode());
            urlVo.setLocalUrl("");//todo
            //todo upload to cloud and set remote url
            return BaseResponse.builder().success(true).data(urlVo).build();
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.builder()
                    .success(false)
                    .errorCode(BizTypeEnum.SAVE_FILE.getCode())
                    .errorMessage(BizTypeEnum.SAVE_FILE.getName())
                    .build();
        }
    }

    private String getFileName(String id, URLTypeEnum type, String suffix) {
        return DigestUtils.md5Hex(fileNameSalt + type.getCode() + id) + "." + suffix.split("\\.")[1];
    }
}
