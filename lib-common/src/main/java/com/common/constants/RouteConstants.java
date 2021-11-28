package com.common.constants;

/**
 * @ClassName RouteConstants
 * @Author zhangjun
 * @Date 2021/11/28 15:46
 * @Description 封装ARouter 跳转路径
 */
public class RouteConstants {
    //app模板中的配置
    public static class Module_app {
        private static final String MAIN = "/app";
        public static final String PAGER_NAVIGATION = MAIN + "/Navigation";
        public static final String PAGER_SPLASH = MAIN + "/Splash";
    }

    //advanceBook 路径模板
    public static class Module_advanceBook {
        private static final String MAIN = "/advanceBook";
    }
    //inCanteen 路径模板
    public static class Module_inCanteen {
        private static final String MAIN = "/inCanteen";
    }
    //takeout 路径模板
    public static class Module_takeout {
        private static final String MAIN = "/takeout";
    }
}
