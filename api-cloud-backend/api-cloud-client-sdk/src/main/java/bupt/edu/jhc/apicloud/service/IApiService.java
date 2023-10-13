package bupt.edu.jhc.apicloud.service;

import bupt.edu.jhc.apicloud.client.ApiCloudClient;
import bupt.edu.jhc.apicloud.common.domain.resp.SDKCommonResp;
import bupt.edu.jhc.apicloud.common.exception.SDKException;
import bupt.edu.jhc.apicloud.model.req.BaseReq;
import bupt.edu.jhc.apicloud.model.resp.PoisonousChickenSoupResp;

/**
 * @Description: api 服务接口
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/11
 */
public interface IApiService {
    /**
     * 通用请求
     * @param req
     * @return
     * @param <O>
     * @param <T>
     * @throws SDKException
     */
    <O, T extends SDKCommonResp> T request(BaseReq<O, T> req) throws SDKException;

    /**
     * 通用请求
     * @param apiCloudClient
     * @param req
     * @return
     * @param <O>
     * @param <T>
     * @throws SDKException
     */
    <O, T extends SDKCommonResp> T request(ApiCloudClient apiCloudClient, BaseReq<O, T> req) throws SDKException;

    PoisonousChickenSoupResp getPoisonousChickenSoup() throws SDKException;
    PoisonousChickenSoupResp getPoisonousChickenSoup(ApiCloudClient apiCloudClient) throws SDKException;

}
