package com.wzq.mvvmsmart.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzq.mvvmsmart.R;
import com.wzq.mvvmsmart.base.IBaseViewMVVM;


/**
 * author : 王志强
 * date   : 2019/11/11 19:16
 */
public class EmptyViewHelper {

    private String text;//显示的文本
    private int imgId;//图片资源id
    private TextView reload;//重新加载
    private Context mContext;
    private View rootView;
    private ImageView placeImg;
    private TextView placeText;
    private boolean isAdd = false;
    private IBaseViewMVVM reloadCallBack;

    public EmptyViewHelper(Context mContext) {
        this(mContext, null, 0);
    }

    public EmptyViewHelper(Context mContext, String text, int imgId) {
        this.mContext = mContext;
        this.text = text;
        this.imgId = imgId;
        loadPage();
    }

    public void setReloadCallBack(IBaseViewMVVM reloadCallBack) {
        this.reloadCallBack = reloadCallBack;
    }

    private void loadPage() {
        if (mContext == null)
            return;
        rootView = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, null);
        placeImg = rootView.findViewById(R.id.placeImg);
        placeText = rootView.findViewById(R.id.placeText);
        reload = rootView.findViewById(R.id.reload);
        LinearLayout mLayout = rootView.findViewById(R.id.empty_ll);
        loadNewRes(text, imgId);

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadCallBack.onContentReload();
            }
        });

        mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reloadCallBack.onContentReload();
            }
        });
    }

    public void loadPlaceLayout(View view, String text, int imgId) {
        placeView(view);
        loadNewRes(text, imgId);
    }

    public void loadPlaceLayout(View view, String text, int imgId, boolean showRel) {
        if (showRel) {
            if (reload.getVisibility() == View.GONE) {
                reload.setVisibility(View.VISIBLE);
            }
        } else {
            if (reload.getVisibility() == View.VISIBLE) {
                reload.setVisibility(View.GONE);
            }
        }
        placeView(view);
        loadNewRes(text, imgId);
    }

    private void loadNewRes(String text, int imgId) {
        if (!TextUtils.isEmpty(text)) {
            placeText.setText(text);
        }
        if (imgId != 0) {
            placeImg.setImageResource(imgId);
        }
    }

    private void placeView(View view) {
        if (view == null || rootView == null)
            return;
        if (!isAdd) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            ViewGroup.MarginLayoutParams placeParams =
                    new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            placeParams.width = params.width;
            placeParams.height = params.height;
            rootView.setLayoutParams(placeParams);
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.addView(rootView, params);
            isAdd = true;
        } else {
            rootView.setVisibility(View.VISIBLE);
        }
        view.setVisibility(View.GONE);
    }

    public void loadNormallLayout(View view) {
        if (view.getVisibility() == View.GONE || view.getVisibility() == View.INVISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        if (rootView.getVisibility() == View.VISIBLE) {
            rootView.setVisibility(View.GONE);
        }
    }

    public void releaseRes() {
        mContext = null;
        rootView = null;
    }
}
