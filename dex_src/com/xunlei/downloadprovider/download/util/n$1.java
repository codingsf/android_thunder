package com.xunlei.downloadprovider.download.util;

import com.xunlei.downloadprovider.businessutil.XLFileTypeUtil.EFileCategoryType;

// compiled from: TaskHelper.java
/* synthetic */ class n$1 {
    static final /* synthetic */ int[] a;

    static {
        a = new int[EFileCategoryType.values().length];
        try {
            a[EFileCategoryType.E_VIDEO_CATEGORY.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            a[EFileCategoryType.E_MUSIC_CATEGORY.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            a[EFileCategoryType.E_BOOK_CATEGORY.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            a[EFileCategoryType.E_PICTURE_CATEGORY.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
        try {
            a[EFileCategoryType.E_ZIP_CATEGORY.ordinal()] = 5;
        } catch (NoSuchFieldError e5) {
        }
        try {
            a[EFileCategoryType.E_TORRENT_CATEGORY.ordinal()] = 6;
        } catch (NoSuchFieldError e6) {
        }
        try {
            a[EFileCategoryType.E_OTHER_CATEGORY.ordinal()] = 7;
        } catch (NoSuchFieldError e7) {
        }
    }
}
