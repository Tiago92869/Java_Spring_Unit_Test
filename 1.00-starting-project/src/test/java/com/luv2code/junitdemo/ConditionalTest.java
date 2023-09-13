package com.luv2code.junitdemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

public class ConditionalTest {

    @Test
    @Disabled("Don't run until JIRA #123 is resolved")
    void basicText(){

    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void testForWindowsOS(){

    }

    @Test
    @EnabledOnOs(OS.MAC)
    void testForMacOS(){

    }

    @Test
    @EnabledOnOs({OS.MAC, OS.WINDOWS})
    void testForMacAndWindowsOS(){

    }

    @Test
    @EnabledOnOs({OS.MAC, OS.LINUX})
    void testForMacAndLinuxOS(){

    }

    @Test
    @EnabledOnJre(JRE.JAVA_17)
    void testForOnlyJava17(){

    }

    @Test
    @EnabledOnJre(JRE.JAVA_13)
    void testForOnlyJava13(){

    }

    @Test
    @EnabledOnJre(JRE.JAVA_18)
    void testForOnlyJava18(){

    }

    @Test
    @EnabledForJreRange(min=JRE.JAVA_11)
    void testForMinJava11(){

    }

    @Test
    @EnabledIfEnvironmentVariable(named="LUV2CODE_ENV", matches="DEV")
    void testOnlyForDevEnvironment(){

    }

    @Test
    @EnabledIfSystemProperty(named="LUV2CODE_SYS_PROP", matches="CI_CD_DEPLOY")
    void testOnlyForSystemProperty(){

    }
}
