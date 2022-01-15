package com.common.constants;

/**
 * @ClassName RouteConstants
 * @Author zhangJun
 * @Date 2021/11/28 15:46
 * @Description 封装ARouter 跳转路径
 */
public class RouteConstants {
    //app模板中的配置
    public static class Module_app {
        private static final String MAIN = "/app";
        private static final String FRAGMENT = "/fragment";
        public static final String PAGER_NAVIGATION = MAIN + "/Navigation";
        public static final String PAGER_AD = MAIN + "/Ad";
        public static final String PAGER_SPLASH = MAIN + "/Splash";
        public static final String PAGER_MAIN = MAIN + "/Main";
        public static final String PAGER_USER_FRAGMENT = MAIN + FRAGMENT + "/User";//跳转到UserFragment
    }

    //persion 路径模板
    public static class Module_person {
        private static final String MAIN = "/person";
        private static final String FRAGMENT = "/fragment";
        public static final String PAGER_MESSAGE_FRAGMENT = MAIN + FRAGMENT + "/Message";//MessageFragment
        public static final String PAGER_DETAIL_MESSAGE_FRAGMENT = MAIN + FRAGMENT + "/DetailMessage";//DetailMessageFragment
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
