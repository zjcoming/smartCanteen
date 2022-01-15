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
        public static final String PAGER_NAVIGATION = MAIN + "/Navigation";
        public static final String PAGER_AD = MAIN + "/Ad";
        public static final String PAGER_SPLASH = MAIN + "/Splash";
        public static final String PAGER_MAIN = MAIN + "/Main";
    }

    public static class Module_order {
        private static final String MAIN = "/module_order";
        public static final String PAGER_CHOOSE_FOOD = MAIN + "/ChooseFood";
    }

}
