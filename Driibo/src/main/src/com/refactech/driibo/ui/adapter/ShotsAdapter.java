package com.refactech.driibo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.refactech.driibo.AppData;
import com.refactech.driibo.R;
import com.refactech.driibo.data.RequestManager;
import com.refactech.driibo.type.dribble.Item;
import com.refactech.driibo.vendor.DribbbleApi;

public class ShotsAdapter extends CursorAdapter {
	private LayoutInflater mLayoutInflater;

	private ListView mListView;

	private BitmapDrawable mDefaultAvatarBitmap = (BitmapDrawable) AppData
			.getContext().getResources().getDrawable(R.drawable.default_avatar);

	private Drawable mDefaultImageDrawable = new ColorDrawable(Color.argb(255,
			201, 201, 201));

	public ShotsAdapter(Context context, ListView listView) {
		super(context, null, false);
		mLayoutInflater = ((Activity) context).getLayoutInflater();
		mListView = listView;
	}

	@Override
	public Item getItem(int position) {
		mCursor.moveToPosition(position);
		return Item.fromCursor(mCursor);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		return mLayoutInflater.inflate(R.layout.listitem_shot, null);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		Holder holder = getHolder(view);
		if (holder.contentImgRequest != null) {
			holder.contentImgRequest.cancelRequest();
		}

		if (holder.userImgRequest != null) {
			holder.userImgRequest.cancelRequest();
		}

		view.setEnabled(!mListView.isItemChecked(cursor.getPosition()
				+ mListView.getHeaderViewsCount()));

		final Item shot = Item.fromCursor(cursor);

		if (shot.user == null) {
			holder.userInfoLayout.setVisibility(View.GONE);
		} else {
			holder.userInfoLayout.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(shot.user.icon)) {
				holder.userImgRequest = RequestManager.loadImage(DribbbleApi
						.getUserImgURL(shot.user), RequestManager
						.getImageListener(holder.userIconImg,
								mDefaultAvatarBitmap, mDefaultAvatarBitmap,true));
				Log.d("UserImgURL", DribbbleApi.getUserImgURL(shot.user));
			}else{
				holder.userIconImg.setImageResource(R.drawable.default_avatar);
			}
			holder.userNameText.setText(shot.user.login);
		}
		if (!TextUtils.isEmpty(shot.image)) {
			holder.contentImg.setVisibility(View.VISIBLE);
			holder.contentImgRequest = RequestManager.loadImage(DribbbleApi
					.getContentImgURL(shot.image), RequestManager
					.getImageListener(holder.contentImg, mDefaultImageDrawable,
							mDefaultImageDrawable,false));
			Log.d("ContentImgURL", DribbbleApi.getContentImgURL(shot.image));
		}else{
			holder.contentImg.setVisibility(View.GONE);
		}
		holder.contentText.setText(shot.content);
		holder.contentTagText.setText(shot.tag);
		holder.comeUpBtn.setText(String.valueOf(shot.votes.up));
		holder.comeDownBtn.setText(String.valueOf(shot.votes.down));
		holder.commentText.setText(String.valueOf(shot.comments_count));
	}

	private Holder getHolder(final View view) {
		Holder holder = (Holder) view.getTag();
		if (holder == null) {
			holder = new Holder(view);
			view.setTag(holder);
		}
		return holder;
	}

	private static class Holder {
		public RelativeLayout userInfoLayout;
		public ImageView userIconImg;
		public TextView userNameText;
		public TextView contentText;
		public ImageView contentImg;
		public TextView contentTagText;
		public Button comeUpBtn;
		public TextView sendPlusText;
		public Button comeDownBtn;
		public TextView sendDownText;
		public TextView commentText;
		public ImageLoader.ImageContainer contentImgRequest;

		public ImageLoader.ImageContainer userImgRequest;

		public Holder(View view) {
			userInfoLayout = (RelativeLayout) view
					.findViewById(R.id.userinfo_layout);
			userIconImg = (ImageView) view.findViewById(R.id.usericon_img);
			userNameText = (TextView) view.findViewById(R.id.username_text);
			contentText = (TextView) view.findViewById(R.id.content_text);
			contentImg = (ImageView) view.findViewById(R.id.content_img);
			contentTagText = (TextView) view.findViewById(R.id.contenttag_text);
			comeUpBtn = (Button) view.findViewById(R.id.comeup_btn);
			sendPlusText = (TextView) view.findViewById(R.id.sendplus_text);
			comeDownBtn = (Button) view.findViewById(R.id.comedown_btn);
			sendPlusText = (TextView) view.findViewById(R.id.sendplus_text);
			commentText = (TextView) view.findViewById(R.id.comment_text);
		}
	}
}
