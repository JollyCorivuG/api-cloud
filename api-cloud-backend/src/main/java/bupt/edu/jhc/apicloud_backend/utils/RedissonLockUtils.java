package bupt.edu.jhc.apicloud_backend.utils;

import bupt.edu.jhc.apicloud_common.common.enums.ErrorStatus;
import bupt.edu.jhc.apicloud_common.common.exception.BizException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description: Redisson 分布式锁工具类
 * @Author: <a href="https://github.com/JollyCorivuG">JollyCorivuG</a>
 * @CreateTime: 2023/10/10
 */
@Component
@Slf4j
public class RedissonLockUtils {
    @Resource
    private RedissonClient redissonClient;

    /**
     * 给某个执行任务分配锁
     * @param lockName
     * @param runnable
     * @param errorStatus
     * @param errorMsg
     */
    public void distributedLocks(String lockName, Runnable runnable, ErrorStatus errorStatus, String errorMsg) {
        RLock rLock = redissonClient.getLock(lockName);
        try {
            if (rLock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                runnable.run();
            } else {
                throw new BizException(errorStatus, errorMsg);
            }
        } catch (Exception e) {
            throw new BizException(ErrorStatus.OPERATION_ERROR, e.getMessage());
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                log.info("lockName:{},unLockId:{} ", lockName, Thread.currentThread().getId());
                rLock.unlock();
            }
        }
    }
}
