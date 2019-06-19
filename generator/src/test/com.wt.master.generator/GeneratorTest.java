package com.wt.master.generator;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * Generator Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Jun 18, 2019</pre>
 */
public class GeneratorTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: create(String tableName, String moduleName, String moduleNameCN, String packagePath, GenerateItem...
     * item)
     */
    @Test
    public void testCreate() throws Exception {
        //TODO: Test goes here...
        Generator.create("SecurityRoleT", "SecurityRole", "角色管理", "com.wt.common.security",
                Generator.GenerateItem.controller, Generator.GenerateItem.service, Generator.GenerateItem.mapper,
                Generator.GenerateItem.domain);
    }


    /**
     * Method: getTableInfo(String tableName)
     */
    @Test
    public void testGetTableInfo() throws Exception {
        //TODO: Test goes here...
/* 
try { 
   Method method = Generator.getClass().getMethod("getTableInfo", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
