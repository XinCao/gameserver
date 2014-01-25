package gameserver.jmx.service;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * 远程代理bean服务器调用
 * 
 * @author caoxin
 */
@ManagedResource(objectName = "AppDebugger", description = "应用程序调试接口")
public class AppDebugger {
    
    @ManagedOperation(description = "测试使用.")
    @ManagedOperationParameters({
        @ManagedOperationParameter(name="param1", description="参数1"),
        @ManagedOperationParameter(name="param2", description="参数2")
    })
    public String test(int param1, int param2) {
        return "hello world!";
    }
}
