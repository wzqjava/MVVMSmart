package com.wzq.mvvmsmart.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.widget.ImageView;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.wzq.mvvmsmart.R;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;

public class GlideLoadUtils {

    /***
     *  加载圆角图片
     *  解决imageView在布局文件设置android:scaleType="centerCrop"属性后，圆角无效的问题
     * @param placeHolderID 占位图
     * @param target 目标控件
     * @param url  资源路径
     */
    public static void loadRoundCornerImg(ImageView target, String url, int placeHolderID, int round) {

        try {
            RequestOptions options = new RequestOptions().transform(new CenterCrop());
            Glide.with(target.getContext())
                    .asBitmap()
                    .load(url)
                    .placeholder(placeHolderID)
                    .apply(options)
                    .into(new BitmapImageViewTarget(target) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            super.setResource(resource);
                            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(target.getContext().getResources(), resource);
                            roundedBitmapDrawable.setCornerRadius(round);
                            target.setImageDrawable(roundedBitmapDrawable);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     *  加载圆形图片
     * @param context 上下文
     * @param placeHolderID 占位图
     * @param target 目标控件
     * @param url  资源路径
     */
    public static void loadCircleImg(Context context, int placeHolderID, ImageView target, String url) {
        try {
            Glide.with(context)
                    .load(url)
                    .placeholder(placeHolderID)
                    .apply(RequestOptions.circleCropTransform())
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 王志强 2019/12/23
     * 圆形图片外边带一个圈, 经常用于加载头像
     */
    public static void loadCircleImgBorder(Context context, int placeHolderID, ImageView target, String url) {
        try {

            Glide.with(context)
                    .load(url)
                    .circleCrop()
                    .placeholder(placeHolderID)
                    .transform(new GlideCircleTransformWithBorder(context, 2, context.getResources().getColor(R.color.white)))
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(target);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置圆角bitmap
     *
     * @param context
     * @param placeHolderID
     * @param target
     * @param bitmap
     */
    public static void loadRoundImgWithBitmap(final Context context, int placeHolderID, final ImageView target, Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();
            Glide.with(context)
                    .load(bytes)
                    .placeholder(placeHolderID)
                    .apply(RequestOptions.circleCropTransform())
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static class GlideCircleTransformWithBorder extends BitmapTransformation {
        private Paint mBorderPaint;
        private float mBorderWidth;

        public GlideCircleTransformWithBorder(Context context) {
        }

        public GlideCircleTransformWithBorder(Context context, int borderWidth, int borderColor) {
            mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;
            mBorderPaint = new Paint();
            mBorderPaint.setDither(true);
            mBorderPaint.setAntiAlias(true);
            mBorderPaint.setColor(borderColor);
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mBorderPaint.setStrokeWidth(mBorderWidth);
        }


        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            if (mBorderPaint != null) {
                float borderRadius = r - mBorderWidth / 2;
                canvas.drawCircle(r, r, borderRadius, mBorderPaint);
            }
            return result;
        }

        @Override
        public void updateDiskCacheKey(MessageDigest messageDigest) {

        }

    }


}