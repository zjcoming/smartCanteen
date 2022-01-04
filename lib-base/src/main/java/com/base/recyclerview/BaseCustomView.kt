package com.base.recyclerview

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.viewbinding.ViewBinding
import com.base.util.UIUtils
import com.base.util.bindViewWithGeneric
import com.base.util.inflateBindingWithGeneric

/**
 * Created by chenxiong
 * date 11/26/21
 */

/*
    将每一个item抽离成一个具体的View，只需继承BaseCustomView，提供布局ID、具体的数据绑定
    如binding.textView = data.text

    public class MyView extends BaseCustomView<TestLayoutBinding,MyModel> {

    public MyView(@NotNull Context context) {
        super(context);
    }


    @Override
    public int getLayoutId() {
        return R.layout.test_layout;
    }

    @Override
    protected void setDataToView(@NotNull MyModel myModel) {
        binding.name.setText(myModel.name);
    }
}
 */
@RequiresApi(Build.VERSION_CODES.P)
abstract class BaseCustomView<DATA : IBaseCustomViewModel, VIEW : ViewBinding> @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null, style: Int = 0
) : FrameLayout(context, attr, style), ICustomView<DATA> {
    protected lateinit var binding: VIEW
    private lateinit var mData: DATA

    init {
        init()
    }

    private fun init() {
        binding = inflateBindingWithGeneric(UIUtils.getLayoutInflater(context),this,false)
        addView(binding.root)
    }

    override fun setData(data: DATA) {
        mData = data
        setDataToView(data)
    }

    protected abstract fun setDataToView(data: DATA)

}