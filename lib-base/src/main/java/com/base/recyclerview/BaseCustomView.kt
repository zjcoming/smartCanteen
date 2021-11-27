package com.base.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.base.util.bindViewWithGeneric

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
abstract class BaseCustomView<VIEW : ViewBinding, DATA : IBaseCustomViewModel> @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null, style: Int = 0
) : FrameLayout(context, attr, style), ICustomView<DATA> {
    protected lateinit var binding: VIEW
    private lateinit var mData: DATA

    init {
        init()
    }

    private fun init() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(getLayoutId(),this,false)
        binding = bindViewWithGeneric(view)
        addView(binding.root)
    }
    abstract fun getLayoutId(): Int


    override fun setData(data: DATA) {
        mData = data
        setDataToView(data)
    }

    protected abstract fun setDataToView(data: DATA)

}