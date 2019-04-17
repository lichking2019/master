package com.wt.master.core.cache;

import com.wt.master.core.base.BaseController;
import com.wt.master.core.base.BaseService;
import com.wt.master.core.request.HttpResultEntity;
import com.wt.master.core.request.HttpResultHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * redis控制器
 *
 * @author lichking2019@aliyun.com
 * @date Apr 16, 2019 at 12:39:17 PM
 */
@RestController
@RequestMapping("/cache")
public class CacheController {

    /**
     * 清空缓存
     * @return
     */
    @GetMapping(value = "/clearAll")
    public HttpResultEntity cacheEvict(){
        return HttpResultHandler.getResultEntity(HttpResultHandler.ErrorCode.SUCCESS,"缓存清除成功");
    }
}
