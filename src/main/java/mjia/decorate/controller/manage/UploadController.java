package mjia.decorate.controller.manage;

import lombok.extern.slf4j.Slf4j;
import mjia.decorate.entity.*;
import mjia.decorate.enums.BizTypeEnum;
import mjia.decorate.enums.URLTypeEnum;
import mjia.decorate.service.MaterialService;
import mjia.decorate.utils.FileSyncToCloudUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.GC;

import java.io.File;
import java.util.Date;
import java.util.UUID;

@Slf4j
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

    @Value("${zip.file.max.byte.size}")
    private String maxFileSize;

    @Value("${decorate.bucket.upload.name}")
    private String bucketName;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private FileSyncToCloudUtil fileSyncToCloudUtil;

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
        BaseResponse baseResponse = saveFile(log, file, filePath, id, URLTypeEnum.getByCode(type));
        if(!baseResponse.getSuccess()) {
            return baseResponse;
        }
        UrlVo urlVo = (UrlVo) baseResponse.getData();
        //??????Id
        urlVo.setReferId(id);
        materialService.saveUrl(urlVo);
        if (URLTypeEnum.CATEGORY.equals(URLTypeEnum.getByCode(type))) {
            MaterialCategoryVo categoryVo = new MaterialCategoryVo();
            categoryVo.setId(id);
            categoryVo.setName(name);
            materialService.saveCategory(categoryVo);
        } else if (URLTypeEnum.MATERIAL.equals(URLTypeEnum.getByCode(type))) {
            MaterialVo materialVo = new MaterialVo();
            materialVo.setId(id);
            materialVo.setName(name);
            //materialService.saveMaterial(materialVo);
        }
        return baseResponse;
    }

    private BaseResponse saveFile(Logger log, MultipartFile file, String filePath, String id, URLTypeEnum type) {
        UrlVo urlVo = UrlVo.builder().build();
        if (file.isEmpty()) {
            return BaseResponse.builder()
                    .success(false)
                    .errorCode(BizTypeEnum.FILE_EMPTY.getCode())
                    .errorMessage(BizTypeEnum.FILE_EMPTY.getName())
                    .build();
        }
        try {
            String salt = String.valueOf(System.currentTimeMillis());

            String md5Id = DigestUtils.md5Hex(fileNameSalt + salt + id);
            File path = new File(filePath);
            if (!path.exists()) {
                path.mkdir();
            }
            String fileName = getFileName(md5Id, type, file.getOriginalFilename());

            long fileSize = file.getSize();
            //??????????????????????????????????????????
            //????????????????????????,????????????
/*            if (getMaxFileSize() < fileSize) {
                file.transferTo(new File(filePath, fileName));
                //????????? getMaxFileSize = getMaxFileSize() / fileSize

                Thumbnails.of(filePath + fileName).scale(1f).outputQuality(Float.valueOf(getMaxFileSize()) / Float.valueOf(fileSize)).toFile(filePath +fileName);
            } else {
                file.transferTo(new File(filePath, fileName));
            }*/
            file.transferTo(new File(filePath, fileName));
            urlVo.setFileName(fileName);
            urlVo.setReferId(md5Id);
            urlVo.setType(type.getCode());

            Boolean syncFlag = fileSyncToCloudUtil.syncToCloud(log, bucketName, filePath, fileName);
            if (!syncFlag) {
                throw new Exception("syncToCloud error");
            }
            log.info("??????????????????, id:[{}], type:[{}], fileName:[{}]", id, type.getName(), fileName);
            return BaseResponse.builder().success(true).data(urlVo).build();
        } catch (Exception e) {
            log.error("??????????????????, id:[{}], type:[{}], exception={}", id, type.getName(), e.getMessage());
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

    private Long getMaxFileSize() {
        try{
            return Long.valueOf(maxFileSize);
        } catch (Exception e) {
            return Long.MAX_VALUE;
        }
    }
}
