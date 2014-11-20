package com.foobnix.android.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Views {

    public static View find(final Object o, final int resId) {
        if (o instanceof Activity) {
            return ((Activity) o).findViewById(resId);
        }
        if (o instanceof View) {
            return ((View) o).findViewById(resId);
        }
        return null;
    }

    public static List<View> findAll(final Object av, final int... resIds) {
        List<View> res = new ArrayList<View>(resIds.length);
        for (int id : resIds) {
            res.add(find(av, id));
        }
        return res;
    }

    public static View findView(final Activity a, final View v, final int viewId) {
        if (a != null) {
            return a.findViewById(viewId);
        }
        return v.findViewById(viewId);
    }

    public static View click(final View v, final int resId, final OnClickListener onClick) {
        View findViewById = v.findViewById(resId);
        if (onClick != null) {
            findViewById.setOnClickListener(onClick);
        }
        return findViewById;
    }

    public static View click(final Activity a, final int resId, final OnClickListener onClick) {
        View findViewById = a.findViewById(resId);
        if (onClick != null) {
            findViewById.setOnClickListener(onClick);
        }
        return findViewById;
    }

    public static TextView text(final Object view, final int resId) {
        return (TextView) find(view, resId);
    }

    public static EditText editText(final Object view, final int resId) {
        return (EditText) find(view, resId);
    }

    public static Button button(final Object view, final int resId) {
        return (Button) find(view, resId);
    }

    public static ImageView image(final Object view, final int resId) {
        return (ImageView) find(view, resId);
    }

    public static TextView text(final Object view, final int resId, final String text) {
        TextView textView = (TextView) find(view, resId);
        textView.setText(text);
        return textView;
    }

    public static TextView text(final Object view, final int resId, final int msgId) {
        TextView textView = (TextView) find(view, resId);
        textView.setText(msgId);
        return textView;
    }

    public static TextView htmlText(final Object view, final int resId, final String htmlText) {
        TextView textView = (TextView) find(view, resId);
        if (htmlText == null) {
            textView.setText("");
        } else {
            if (htmlText.contains("<")) {
                textView.setText(Html.fromHtml(htmlText));
            } else {
                textView.setText(htmlText);
            }
        }
        return textView;
    }

    public static TextView textIfFind(Object view, final int resId, final String text) {
        TextView textView = (TextView) find(view, resId);
        if (textView != null) {
            textView.setText(text);
        }
        return textView;
    }

    public static void gone(final Object view, final int... resIds) {
        for (int resId : resIds) {
            View viewById = find(view, resId);
            if (viewById != null) {
                viewById.setVisibility(View.GONE);
            }
        }
    }

    public static void unselect(final Object view, final int... resIds) {
        for (int resId : resIds) {
            View viewById = find(view, resId);
            viewById.setSelected(false);
        }
    }

    public static void unselect(final List<View> viewIds) {
        for (View view : viewIds) {
            view.setSelected(false);
        }
    }

    public static void unselect(final View... viewIds) {
        for (View view : viewIds) {
            view.setSelected(false);
        }
    }

    public static void unchecked(final View... viewIds) {
        for (View view : viewIds) {
            view.setSelected(false);
        }
    }

    public static void unselectChilds(final LinearLayout layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            layout.getChildAt(i).setSelected(false);
        }
    }

    public static void uncheckedChilds(final LinearLayout layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View childAt = layout.getChildAt(i);
            if (childAt instanceof CheckBox) {
                ((CheckBox) childAt).setChecked(false);
            }
        }
    }

    public static void forAllChilds(final LinearLayout layout, String field, Object value) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            View childAt = layout.getChildAt(i);

            try {
                Field field2 = childAt.getClass().getField(field);
                field2.setAccessible(true);
                field2.set(childAt, value);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static <T> List<T> findChilds(final ViewGroup layout, Class<T> type) {
        List<T> childs = new ArrayList<T>();
        for (int i = 0; i < layout.getChildCount(); i++) {
            View childAt = layout.getChildAt(i);
            if (childAt.getVisibility() == View.VISIBLE && childAt instanceof ViewGroup) {
                childs.addAll(findChilds((ViewGroup) childAt, type));
            } else if (childAt.getVisibility() == View.VISIBLE && childAt.getClass().equals(type)) {
                childs.add((T) childAt);
            }
        }
        return childs;
    }

    public static void setListViewHeightBasedOnChildren(final ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
