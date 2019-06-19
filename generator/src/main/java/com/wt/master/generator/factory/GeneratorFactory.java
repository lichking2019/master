package com.wt.master.generator.factory;

import com.wt.master.generator.Generator;
import com.wt.master.generator.handler.*;

/**
 * 生成器工厂
 *
 * @author lichking2019@aliyun.com
 * @date Jun 18, 2019 at 4:02:23 PM
 */
public class GeneratorFactory {
    public static BaseGenerator create(Generator.GenerateItem item) {
        BaseGenerator baseGenerator = null;
        switch (item) {
            case service:
                baseGenerator = new ServiceGeneratorHandler();
                break;
            case controller:
                baseGenerator = new ControllerGeneratorHandler();
                break;
            case mapper:
                baseGenerator = new MapperGeneratorHandler();
                break;
            case domain:
                baseGenerator = new DomainGeneratorHandler();
                break;
            default:
                baseGenerator = new ControllerGeneratorHandler();
        }
        return baseGenerator;
    }
}
