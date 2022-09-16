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

    @Value("${material.file.absolute.path}")
    private String materialFilePath;

    @Value("${category.file.absolute.path}")
    private String categoryFilePath;

    @Value("${group.file.absolute.path}")
    private String groupFilePath;

    @Value("${file.name.encrypt.salt}")
    private String fileNameSalt;

    @Autowired
    private MaterialService materialService;

    @RequestMapping("/uploadUrl")
    public BaseResponse uploadMaterial(@RequestParam MultipartFile file, @RequestParam("type") String type, @RequestParam("id") String id, @RequestParam("name") String name) {
        String filePath = "";
        if (URLTypeEnum.MATERIAL.equals(URLTypeEnum.getByCode(type))) {
            filePath = materialFilePath;
        } else if (URLTypeEnum.CATEGORY.equals(URLTypeEnum.getByCode(type))) {
            filePath = categoryFilePath;
        } else if (URLTypeEnum.GROUP.equals(URLTypeEnum.getByCode(type))) {
            filePath = groupFilePath;
        }
        BaseResponse baseResponse = saveFile(file, filePath, id, URLTypeEnum.getByCode(type));
        if(!baseResponse.getSuccess()) {
            return baseResponse;
        }
        UrlVo urlVo = (UrlVo) baseResponse.getData();
        //填充Id
        urlVo.setReferId(id);
        materialService.saveUrl(urlVo);
        if (URLTypeEnum.CATEGORY.equals(URLTypeEnum.getByCode(type))) {
            MaterialCategoryVo categoryVo = new MaterialCategoryVo();
            categoryVo.setId(id);
            categoryVo.setName(name);
            materialService.saveCategory(categoryVo);
        }
        return baseResponse;
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
            String fileName = getFileName(id, type, file.getOriginalFilename());
            file.transferTo(new File(filePath, fileName));
            urlVo.setFileName(fileName);
            urlVo.setReferId(id);
            urlVo.setType(type.getCode());

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

    private String getFileName(String id, URLTypeEnum type, String fileName) {
        return DigestUtils.md5Hex(fileNameSalt + type.getCode() + id) + "." + fileName.split("\\.")[1];
    }
}
