package collect.jhjz.com.mytest.tool;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class UIHelper {

    public static View inflater(Context context, int resource) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(resource, null);
    }

    public static int getIdentifier(Context context, String name, String defType) {
        Resources res = context.getResources();
        String packageName = context.getPackageName();
        return res.getIdentifier(name, defType, packageName);
    }

    /**
     * 查询窗口大小
     *
     * @param context
     * @return
     */
    public static Point getWindowSize(Context context) {
        //API < 13
        /*WindowManager winManager=(WindowManager)getSystemService(Context.WINDOW_SERVICE);
		winManager.getDefaultDisplay().getWidth();*/

        //API > 13
        WindowManager winManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point outSize = new Point();
        winManager.getDefaultDisplay().getSize(outSize);
        return outSize;
    }

    /**
     * 查询和设置View高度
     *
     * @param view
     * @return
     */
    public static int getViewHeight(View view) {
        return view == null ? 0 : view.getLayoutParams().height;
    }

    public static void setViewHeight(View view, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = height;
        view.setLayoutParams(params);
    }

    /**
     * 获取ListView的Y方向滚动位置,因为ListView.getScrollY()有问题.
     *
     * @param listView
     * @return
     */
    public static int getListViewScrollY(ListView listView) {
        View c = listView.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }

    /**
     * 根据内容设置ListView高度
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        setListViewHeightBasedOnChildren(listView, true);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView,
                                                        boolean measure) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            int height = listItem.getMeasuredHeight();
            if (!measure) {
                height = listItem.getLayoutParams().height;
            }
            totalHeight += height;
        }
        System.out.println("totalHeight:" + totalHeight);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 根据内容设置GridView高度
     */
    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int rows;
        int columns = 0;
        int horizontalBorderHeight = 0;
        Class<?> clazz = gridView.getClass();
        try {
            //利用反射，取得每行显示的个数
            Field column = clazz.getDeclaredField("mRequestedNumColumns");
            column.setAccessible(true);
            columns = (Integer) column.get(gridView);
            //利用反射，取得横向分割线高度
            Field horizontalSpacing = clazz.getDeclaredField("mRequestedHorizontalSpacing");
            horizontalSpacing.setAccessible(true);
            horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //判断数据总数除以每行个数是否整除。不是整除代表有多余，需要加一行
        if (listAdapter.getCount() % columns > 0) {
            rows = listAdapter.getCount() / columns + 1;
        } else {
            rows = listAdapter.getCount() / columns;
        }
        int totalHeight = 0;
        for (int i = 0; i < rows; i++) { //只计算每项高度*行数
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + horizontalBorderHeight * (rows - 1);//最后加上分割线总高度
        gridView.setLayoutParams(params);
    }

    /**
     * 设置前景色
     *
     * @param text
     * @param start
     * @param end
     * @param color
     */
    public static void setFontColor(SpannableString text, int start, int end,
                                    int color) {
        text.setSpan(new ForegroundColorSpan(color), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public static void showToast(Context context, CharSequence message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, int messageRsd) {
        Toast.makeText(context, messageRsd, Toast.LENGTH_SHORT).show();
    }

    /**
     * 设置删除线
     *
     * @param text
     * @param start
     * @param end
     */
    public static void setStrike(SpannableString text, int start, int end) {
        text.setSpan(new StrikethroughSpan(), start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 设置删除线
     */
    public static void setStrike(TextView view) {
        view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); // 中间横线
        view.getPaint().setAntiAlias(true);// 抗锯齿
    }

    public static void setUnderline(TextView view) {
        view.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void setBackgroundResource(View view, int resid) {
        int bottom = view.getPaddingBottom();
        int top = view.getPaddingTop();
        int right = view.getPaddingRight();
        int left = view.getPaddingLeft();
        view.setBackgroundResource(resid);
        view.setPadding(left, top, right, bottom);
    }

    public static void setCompoundDrawableLeft(TextView view, int resid) {
        Drawable icon = view.getContext().getResources().getDrawable(resid);
        view.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
    }

    public static void setCompoundDrawableRight(TextView view, int resid) {
        Drawable icon = view.getContext().getResources().getDrawable(resid);
        view.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
    }

    /**
     * 隐藏输入法
     *
     * @param activity
     */
    public static void hideSoftInputFromWindow(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏输入法
     *
     * @param view
     */
    public static void hideSoftInputFromWindow(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view
                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void tooglePwdVisible(EditText view) {
        int inputType = view.getInputType();
        if (inputType != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            view.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            view.setInputType(InputType.TYPE_CLASS_TEXT
                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public static void shareText(Context context, String title, String value) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, value);
        // sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, title));
    }

    public static void setTooglePwdVisible(CheckBox chk, final EditText view) {
        chk.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                UIHelper.tooglePwdVisible(view);
                buttonView.setText(isChecked ? "隐藏" : "显示");
            }
        });
    }

    public static void setEditEnabled(ViewGroup group, boolean enabled) {
        for (int i = 0, j = group.getChildCount(); i < j; i++) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                view.setEnabled(enabled);
            } else if (view instanceof ViewGroup) {
                setEditEnabled((ViewGroup) view, enabled);
            }
        }
    }

    public static String checkEmpty(EditText... views) {
        if (views.length == 0)
            return "";
        String msg = "";
        for (EditText edit : views) {
            String txt = edit.getText().toString();
            if (StringUtils.isEmpty(txt)) {
                msg = edit.getHint().toString();
                break;
            }
        }
        return msg;
    }

    public static int getTag2Int(View view) {
        Object obj = view.getTag();
        return StringUtils.toInt(obj);
    }


    static private ProgressDialog progressDialog;
    /**
     * 添加ProgressBar
     */
    static public void showProgressBar(Context context, String messageString) {
        Activity activity = (Activity) context;
        while (activity==null||activity.isFinishing()) {
            activity = activity.getParent();
        }
        if(activity==null||activity.isFinishing()){
            return;
        }
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (StringUtils.isEmpty(messageString)) {
            progressDialog.setMessage("请稍后...");
        } else {
            progressDialog.setMessage(messageString);
        }
        if (progressDialog != null) {
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    static public void hideProgressBar() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }
}
