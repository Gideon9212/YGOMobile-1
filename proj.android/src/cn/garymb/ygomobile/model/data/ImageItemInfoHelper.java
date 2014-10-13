package cn.garymb.ygomobile.model.data;

import java.io.File;

import cn.garymb.ygomobile.StaticApplication;
import cn.garymb.ygomobile.common.Constants;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

public final class ImageItemInfoHelper {
	public final static String HTTP_PREFIX = "http://";
	public final static String HTTPS_PREFIX = "https://";

	public final static String MEIDA_PREFIX = "content://";
	public final static String FILE_PREFIX = "file://";
	
	public final static String ABSOLUTE_PATH_PREFIX = "/";

	public static final String AVATAR_IMAGE_PRIFIX = "avatar";
	public static final String PNG_IMAGE_SUFFIX = ".png";

	private static final String JPG_IMAGE_SUFFIX = ".jpg";

	public static boolean isThumnailExist(ImageItem item) {
		final String path = getThumnailPath(item);
		if (path == null)
			return false;

		File file = new File(path);
		if (file.exists() && file.isFile())
			return true;

		return false;
	}

	public static String getThumnailPath(ImageItem item) {
		if (item == null)
			return null;
		// if id equals file's url, just return it
		if (item.id.startsWith(MEIDA_PREFIX)) {
			ContentResolver cr = StaticApplication.peekInstance()
					.getContentResolver();
			String[] projection = { MediaStore.MediaColumns.DATA };
			Cursor cursor = cr.query(Uri.parse(item.id), projection, null,
					null, null);
			if (cursor != null && cursor.moveToFirst()) {
				String path = cursor.getString(0);
				cursor.close();
				return path;
			} else {
				if (cursor != null) {
					cursor.close();
				}
				return null;
			}
		} else if (item.id.startsWith(ABSOLUTE_PATH_PREFIX)){
			return item.id;
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(StaticApplication.peekInstance().getCardImagePath())
					.append(Constants.THUMBNAIL_IMAGE_DIRECTORY);
			if (!new File(sb.toString()).exists()) {
				new File(sb.toString()).mkdirs();
			}
			sb.append(item.id).append(JPG_IMAGE_SUFFIX);
			return sb.toString();
		}
	}

	public static String getThumnailUrl(ImageItem item) {
		String url = item.urlSegment;

		if (url != null
				&& (url.startsWith(HTTP_PREFIX) || url.startsWith(HTTPS_PREFIX)))
			return url;

		final String baseUrl = ResourcesConstants.THUMBNAIL_URL;
		url = TextUtils.isEmpty(baseUrl) ? null : baseUrl + item.id
				+ JPG_IMAGE_SUFFIX;
		return url;
	}

	public static boolean isImageExist(ImageItem item) {
		final String path = getImagePath(item);
		if (path == null)
			return false;
		File file = new File(path);
		if (file.exists() && file.isFile())
			return true;

		return false;
	}

	public static String getImagePath(ImageItem item) {
		if (item == null)
			return null;

		// if id equals file's url, just return it
		if (item.id.startsWith(MEIDA_PREFIX)) {
			ContentResolver cr = StaticApplication.peekInstance()
					.getContentResolver();
			String[] projection = { MediaStore.MediaColumns.DATA };
			Cursor cursor = cr.query(Uri.parse(item.id), projection, null,
					null, null);
			if (cursor != null && cursor.moveToFirst()) {
				String path = cursor.getString(0);
				cursor.close();
				return path;
			} else {
				if (cursor != null) {
					cursor.close();
				}
				return null;
			}
		} else if (item.id.startsWith(ABSOLUTE_PATH_PREFIX)){
			return item.id;
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(StaticApplication.peekInstance().getCardImagePath());
			if (!new File(sb.toString()).exists()) {
				new File(sb.toString()).mkdirs();
			}
			sb.append(item.id).append(JPG_IMAGE_SUFFIX);
			return sb.toString();
		}
	}

	public static String getImageUrl(ImageItem item) {
		String url = item.urlSegment;

		if (url != null
				&& (url.startsWith(HTTP_PREFIX) || url.startsWith(HTTPS_PREFIX)))
			return url;

		final String baseUrl = ResourcesConstants.IMAGE_URL;
		url = TextUtils.isEmpty(baseUrl) ? null : baseUrl + item.id
				+ JPG_IMAGE_SUFFIX;
		return url;
	}

}