package bupt.edu.jhc.apicloud.dao.api_interface;

import bupt.edu.jhc.apicloud.mapper.InterfaceMapper;
import bupt.edu.jhc.apicloud.model.entity.ApiInterface;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

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
}
