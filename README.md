# RefreshLayout
仿百思不得姐 可做任何控件的下拉刷新，倾入性低

项目地址：https://github.com/fangxiaogang/RefreshLayout

在 [**SmartRefreshLayout**](https://github.com/scwang90/SmartRefreshLayout) 的基础上做的二次修改，这是一个非常好的下拉刷新、上拉加载框架，在 github 上的star 数超过13k，代码质量不必多疑。



*少废话，先看东西*


![](http://onfkdy4l9.bkt.clouddn.com/333.gif)

### 代码分析

看着复杂，其实就是多张图片的播放，最简单的帧动画而已。把仿百思不得姐反编译或者解压，就可以发现在 drawable 文件夹中存在多张熟悉的小图片。轻松得到源文件。

逐一分析后，总的过程分为三部分：

- 下拉过程中，显示一张图片及文字
- 刷新过程，显示帧动画的动态效果，切换文字
- 松开刷新完成后回弹

### 代码实现

```

public class CustomRefreshHeader extends LinearLayout implements RefreshHeader {

    private ImageView mImage;
    private AnimationDrawable pullDownAnim;
    private AnimationDrawable refreshingAnim;
    private TextView tv_refresh_header;
    private boolean hasSetPullDownAnim = false;

    public CustomRefreshHeader(Context context) {
        this(context, null, 0);
    }

    public CustomRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.refreshheader, this);
        mImage = (ImageView) view.findViewById(R.id.header_iv);
        tv_refresh_header = (TextView) view.findViewById(R.id.header_tv);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {

    }

    /**
     * 状态改变时调用。在这里切换第三阶段的动画卖萌小人
     * @param refreshLayout
     * @param oldState
     * @param newState
     */
    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case PullDownToRefresh: //下拉刷新开始。正在下拉还没松手时调用
                mImage.setImageResource(R.mipmap.image);
                tv_refresh_header.setText("下拉可以刷新");
                break;
            case Refreshing: //正在刷新。只调用一次
                mImage.setImageResource(R.drawable.refresh);
                refreshingAnim = (AnimationDrawable) mImage.getDrawable();
                tv_refresh_header.setText("正在刷新...");
                refreshingAnim.start();
                break;
            case ReleaseToRefresh:

                break;
        }
    }

    /**
     * 下拉过程中不断调用此方法。第一阶段从小变大的小人头动画，和第二阶段翻跟头动画都在这里设置
     */
    @Override
    public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {


        // 下拉的百分比小于100%时，不断调用 setScale 方法改变图片大小
        if (percent < 1) {
            mImage.setScaleX(percent);
            mImage.setScaleY(percent);

            //是否执行过翻跟头动画的标记
            if (hasSetPullDownAnim) {
                hasSetPullDownAnim = false;
            }
        }

        //当下拉的高度达到Header高度100%时，开始加载正在下拉的初始动画，即翻跟头
        if (percent >= 1.0) {
            if (!hasSetPullDownAnim) {
                tv_refresh_header.setText("松开立即刷新...");
                mImage.setImageResource(R.mipmap.image2);
            }
        }
    }

    /**
     * 动画结束后调用
     */
    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        // 结束动画
        if (pullDownAnim != null && pullDownAnim.isRunning()) {
            pullDownAnim.stop();
        }
        if (refreshingAnim != null && refreshingAnim.isRunning()) {
            refreshingAnim.stop();
        }
        //重置状态
        hasSetPullDownAnim = false;
        return 0;
    }

    @Override
    public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {

    }

    @Override
    public void onRefreshReleased(RefreshLayout layout, int headerHeight, int extendHeight) {

    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }


}
```

帧动画资源文件：

```
<?xml version="1.0" encoding="utf-8"?>
<animation-list android:oneshot="false"
    xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:duration="80" android:drawable="@mipmap/image_01" />
    <item android:duration="80" android:drawable="@mipmap/image_02" />
    <item android:duration="80" android:drawable="@mipmap/image_03" />

    </animation-list>
```

详细见项目地址 [**RefreshLayout**](https://github.com/fangxiaogang/RefreshLayout)

