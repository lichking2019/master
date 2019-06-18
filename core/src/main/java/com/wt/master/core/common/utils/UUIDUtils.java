package com.wt.master.core.common.utils;

import java.util.UUID;

/**
 * uuid工具
 *
 * @author lichking2019@aliyun.com
 * @date Apr 26, 2019 at 1:51:32 PM
 */
public class UUIDUtils {

    /**
     * 获取32位uuid
     *
     * @return
     */
    public static String getUUID32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }

    /**
     * 获取正常的UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }
}
