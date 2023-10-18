package bupt.edu.jhc.apicloud_backend.dao.api_interface;

import bupt.edu.jhc.apicloud_backend.mapper.InterfaceMapper;
import bupt.edu.jhc.apicloud_backend.model.entity.ApiInterface;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Consumer;

/**
 * @Description: 接口数据访问对象
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/4
 */
@Repository
public class InterfaceDao extends ServiceImpl<InterfaceMapper, ApiInterface> {
    /**
     * 保存接口
     *
     * @param apiInterface
     */
    public void saveInterface(ApiInterface apiInterface) {
        this.save(apiInterface);
    }

    /**
     * 删除接口
     * @param id
     * @return
     */
    public boolean deleteInterface(Long id) {
        return this.removeById(id);
    }

    /**
     * 更新接口
     * @param interfaceInfo
     */
    public boolean updateInterface(ApiInterface interfaceInfo) {
        return this.updateById(interfaceInfo);
    }

    /**
     * 自定义条件查询
     * @param initWrapper
     * @return
     */
    public ApiInterface getOneByMapper(Consumer<LambdaQueryWrapper<ApiInterface>> initWrapper) {
        LambdaQueryWrapper<ApiInterface> wrapper = new LambdaQueryWrapper<>();
        initWrapper.accept(wrapper);
        List<ApiInterface> apiInterfaces = this.baseMapper.selectList(wrapper);
        return apiInterfaces.isEmpty() ? null : apiInterfaces.get(0);
    }

}
