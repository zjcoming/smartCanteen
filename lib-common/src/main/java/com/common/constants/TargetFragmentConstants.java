package com.common.constants;

import java.io.StringReader;

/**
 * Created by 刘金豪 on 2021/1/17
 * desc: Activity显示哪个Fragment
 */
public class TargetFragmentConstants {
    /**
     * 跳到UIActivity的时候，跳转到哪个Fragment
     */
    public static final String MESSAGE_FRAGMENT = "MessageFragment";
    public static final String DETAIL_MESSAGE_FRAGMENT = "DetailMessageFragment";
    public static final String DATA_PERSON = "PersonDataFragment";

    /**
     * 跳到NavigationActivity的时候，跳到哪个Fragment
     */
    public static final String USER_FRAGMENT = "UserFragment";
    public static final String ORDER_FRAGMENT = "OrderFragment";
    public static final String HOME_FRAGMENT = "HomeFragment";

    /**
     * 跳到ChooseFoodActivity的时候，跳到哪个Fragment
     */
    public static String CurrentChooseFoodActivity = "";
    public static final String CHOOSE_FOOD_FRAGMENT = "ChooseFoodFragment";
    public static final String CONFIRM_ORDER_FRAGMENT = "ConfirmOrderFragment";
}
