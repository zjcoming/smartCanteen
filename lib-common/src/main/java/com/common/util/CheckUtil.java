package com.common.util;

import java.util.regex.Pattern;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 主要是通过正则表达式验证
 *
 */
public class CheckUtil {
    //验证身份证号
    public boolean personIdValidation(String text) {
        /**
         * 验证身份证号是否符合规则
         * @param text 身份证号
         * @return
         */
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        return text.matches(regx) || text.matches(reg1) || text.matches(regex);
    }
    //验证是否为空
    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        if (value != null && !"".equalsIgnoreCase(value.trim())
                && !"null".equalsIgnoreCase(value.trim())) {
            return false;
        } else {
            return true;
        }
    }

    //验证手机号
    /**
     * 手机号码校验(三大运营商最新号段 合作版 2021-03)
     * 移动号段：
     * 134 135 136 137 138 139 147 148 150 151 152 157 158 159 172 178 182 183 184 187 188 195 198
     * <p>
     * 联通号段：
     * 130 131 132 145 146 155 156 166 167 171 175 176 185 186 196
     * <p>
     * 电信号段：
     * 133 149 153 173 174 177 180 181 189 191 193 199
     * <p>
     * 虚拟运营商:
     * 162 165 167 170 171
     * <p>
     * 13开头排序：(0-9)（134 135 136 137 138 139 130 131 132 133）
     * 14开头排序：(5-9)（147 148 145 146 149）
     * 15开头排序：(0-3|5-9)（150 151 152 157 158 159 155 156 153）
     * 16开头排序：(6-7)（166 167）
     * 17开头排序：(1-8)（172 178 171 175 176 173 174 177）
     * 18开头排序：(0-9)（182 183 184 187 188 185 186 180 181 189）
     * 19开头排序：(1|3|5|6|8|9)（195 198 196 191 193 199）
     *
     * @param phone 手机号码
     * @return 是否属于三大运营商号段范围
     * @see {https://www.qqzeng.com/article/phone.html}
     */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5-9])|(15([0-3]|[5-9]))|(16[6-7])|(17[1-8])|(18[0-9])|(19[1|3])|(19[5|6])|(19[8|9]))\\d{8}$";
        Pattern p = Pattern.compile(regex);
        return p.matcher(phone).matches();
    }
}
