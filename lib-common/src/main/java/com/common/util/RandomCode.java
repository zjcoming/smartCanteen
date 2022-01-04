package com.common.util;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.base.bean.UserBean;
import com.base.util.BaseUtil;
import com.common.api.Bean;
import com.common.api.ResponseModel;
import com.common.constants.LoginAndRegisterConstants;
import com.common.retrofitservice.UserLoginService;
import com.swu.lib_common.R;

import java.util.HashMap;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 刘金豪 on 2021/11/26
 * desc: 此类是用来生成验证码的
 */
public class RandomCode {
    public static void sendCodeFun(String telephone){

    }
    public static void createAlertAndCode(Context context, Button sendCode,FragmentActivity fragmentActivity,
                                          String telephone, int color, int currentPage){
        //保存人机识别的真实验证码
        String[] randomCodeString = new String[1];
        //设置弹窗
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //获取布局
        View view = View.inflate(context, R.layout.view_codealert, null);
        //获取布局中的控件
        EditText inputRandomCode = (EditText) view.findViewById(R.id.inputRandomCode);
        inputRandomCode.setInputType(InputType.TYPE_CLASS_TEXT);
        ImageView randomCode = (ImageView)view.findViewById(R.id.randomCode);
        Button confirmCode = (Button) view.findViewById(R.id.confirmRandomCode);

        //给ImageView设置点击事件
        randomCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重新
                randomCode.setImageBitmap(RandomCode.getInstance().createBitmap());
                randomCodeString[0] = RandomCode.getInstance().getCode().toLowerCase();//保存真实的验证码
            }
        });

        //设置显示的验证码图片
        randomCode.setImageBitmap(RandomCode.getInstance().createBitmap());
        //保存真正的验证码
        randomCodeString[0] = RandomCode.getInstance().getCode().toLowerCase();
        //Log.v("ljh","randomCodeString:"+randomCodeString);
        //设置弹窗显示的标题
        builder.setTitle("请输入以下验证码(点击图案刷新)").setView(view);

        //创建对话框
        //弹窗
        AlertDialog alertDialog = builder.create();

        alertDialog.show();

        //设置弹窗大小
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        fragmentActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeigh = dm.heightPixels;
        WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();//获取dialog信息
        params.width = screenWidth - 20;
        params.height = screenHeigh / 2 + 500 ;
        alertDialog.getWindow().setAttributes(params);//设置大小

        confirmCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //得到用户输入的人机验证码
                String inputRandomCodeString = inputRandomCode.getText().toString().toLowerCase();
                //与正确的进行比对
                if (inputRandomCodeString.equals(randomCodeString[0])){
                    //输入正确，发送验证码
                    //关闭对话框
                    alertDialog.dismiss();
                    if (telephone.equals("")){
                        Toast.makeText(context,"输入内容不能为空",Toast.LENGTH_LONG).show();
                    }else {
                        //发送验证码
                          if (currentPage == LoginAndRegisterConstants.LOGIN_BY_PHONE || currentPage == LoginAndRegisterConstants.FORGET_PASSWORD) {
                            //当前页是 手机验证码登录 页面  或者 忘记密码 页面
                            BaseUtil.INSTANCE.sendMsgCode(telephone,context);//发送验证码
                            LoginAndRegisterConstants.LAST_SEND_CODE_TIME = System.currentTimeMillis();
                            new Handler().postDelayed(BtnCountDownUtil.getCountDownRunnable(sendCode,60,"","s后重新发送"),1000);
                        } else {
                            //当前页是 注册页面
                            BaseUtil.INSTANCE.sendMsgCode(telephone,context);//发送验证码
                        }
                    }
                }else {
                    //Toast.makeText(getContext(),"验证码输入错误，请重试",Toast.LENGTH_LONG).show();
                    inputRandomCode.setText("");
                    inputRandomCode.setHint("验证码输入错误，请重新输入");
                    inputRandomCode.setHintTextColor(color);//getResources().getColor(R.color.red)
                    //重新设置显示的验证码
                    randomCode.setImageBitmap(RandomCode.getInstance().createBitmap());
                    randomCodeString[0] = RandomCode.getInstance().getCode().toLowerCase();//保存真实的验证码
                }
            }
        });
    }
    /**
     * 随机数数组
     * 去除了易混淆的 数字 0 和 字母 o O
     *                数字 1 和 字母 i I l L
     *                数字 6 和 字母 b
     *                数字 9 和 字母 q
     *                字母 c C 和 G
     *                字母 t （经常和随机线混在一起看不清）
     */
    private static final char[] CHARS = {
            '2', '3', '4', '5',  '7', '8',
            'a',  'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
            'n', 'p',  'r', 's',  'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B',  'D', 'E', 'F',  'H',  'J', 'K', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static RandomCode bmpCode;

    public static RandomCode getInstance() {
        if(bmpCode == null){
            synchronized (RandomCode.class){
                if(bmpCode == null){
                    bmpCode = new RandomCode();
                }
            }
        }

        return bmpCode;
    }

    //default settings
    //验证码默认随机数的个数
    private static final int DEFAULT_CODE_LENGTH = 4;
    //默认字体大小
    private static final int DEFAULT_FONT_SIZE = 25;
    //默认线条的条数
    private static final int DEFAULT_LINE_NUMBER = 5;
    //padding值
    private static final int BASE_PADDING_LEFT = 10, RANGE_PADDING_LEFT = 15, BASE_PADDING_TOP = 15, RANGE_PADDING_TOP = 20;
    //验证码的默认宽高
    private static final int DEFAULT_WIDTH = 100, DEFAULT_HEIGHT = 40;

    //settings decided by the layout xml
    //canvas width and height
    private int width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT;

    //random word space and pading_top
    private int base_padding_left = BASE_PADDING_LEFT, range_padding_left = RANGE_PADDING_LEFT,
            base_padding_top = BASE_PADDING_TOP, range_padding_top = RANGE_PADDING_TOP;

    //number of chars, lines; font size
    private int codeLength = DEFAULT_CODE_LENGTH, line_number = DEFAULT_LINE_NUMBER, font_size = DEFAULT_FONT_SIZE;

    //variables
    private String code;
    private int padding_left, padding_top;
    private Random random = new Random();
    //验证码图片
    public Bitmap createBitmap() {
        padding_left = 0;

        Bitmap bp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bp);

        code = createCode();

        c.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(font_size);
        //画验证码
        for (int i = 0; i < code.length(); i++) {
            randomTextStyle(paint);
            randomPadding();
            c.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
        }
        //画线条
        for (int i = 0; i < line_number; i++) {
            drawLine(c, paint);
        }

//        c.save( Canvas.ALL_SAVE_FLAG );//保存
        c.save();//保存
        c.restore();//
        return bp;
    }

    public String getCode() {
        return code;
    }

    //生成验证码
    public String createCode() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < codeLength; i++) {
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }
    //画干扰线
    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        int stopX = random.nextInt(width);
        int stopY = random.nextInt(height);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }
    //生成随机颜色
    private int randomColor() {
        return randomColor(1);
    }

    private int randomColor(int rate) {
        int red = random.nextInt(256) / rate;
        int green = random.nextInt(256) / rate;
        int blue = random.nextInt(256) / rate;
        return Color.rgb(red, green, blue);
    }
    //随机生成文字样式，颜色，粗细，倾斜度
    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean());  //true为粗体，false为非粗体
        float skewX = random.nextInt(11) / 10;
        skewX = random.nextBoolean() ? skewX : -skewX;
        paint.setTextSkewX(skewX); //float类型参数，负数表示右斜，整数左斜
        //paint.setUnderlineText(true); //true为下划线，false为非下划线
        //paint.setStrikeThruText(true); //true为删除线，false为非删除线
    }
    //随机生成padding值
    private void randomPadding() {
        padding_left += base_padding_left + random.nextInt(range_padding_left);
        padding_top = base_padding_top + random.nextInt(range_padding_top);
    }
}
