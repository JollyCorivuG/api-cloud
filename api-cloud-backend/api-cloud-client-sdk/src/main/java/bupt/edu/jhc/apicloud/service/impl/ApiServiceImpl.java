package bupt.edu.jhc.apicloud.service.impl;

import bupt.edu.jhc.apicloud.client.ApiCloudClient;
import bupt.edu.jhc.apicloud.common.exception.SDKException;
import bupt.edu.jhc.apicloud.model.req.PoisonousChickenSoupReq;
import bupt.edu.jhc.apicloud.model.resp.PoisonousChickenSoupResp;
import bupt.edu.jhc.apicloud.service.BaseService;

/**
 * @Description: api 服务实现类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/11
 */
public class ApiServiceImpl extends BaseService {
    @Override
    public PoisonousChickenSoupResp getPoisonousChickenSoup() throws SDKException {
        PoisonousChickenSoupReq req = new PoisonousChickenSoupReq();
        return super.request(req);
    }

    @Override
    public PoisonousChickenSoupResp getPoisonousChickenSoup(ApiCloudClient apiCloudClient) throws SDKException {
        PoisonousChickenSoupReq req = new PoisonousChickenSoupReq();
        return super.request(apiCloudClient, req);
    }
}
