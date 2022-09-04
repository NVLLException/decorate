package mjia.decorate.service;

import mjia.decorate.entity.MaterialCategoryVo;
import mjia.decorate.entity.MaterialGroupVo;
import mjia.decorate.entity.MaterialVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService{

    @Override
    public List<MaterialVo> listMaterialByGroupId(String groupId) {
        return null;
    }

    @Override
    public List<MaterialGroupVo> listMaterialGroupByCatetoryId(String materialId) {
        return null;
    }

    @Override
    public List<MaterialCategoryVo> listCategory() {
        return null;
    }


}
