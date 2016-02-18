package com.safewaychina.tabmenu;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

/**
 * @author liu_haifang
 * @version 1.0
 * @TitleSAFEYE@
 * @Description
 * @date 2015-07-07
 */
public class DeafultTabAdapter extends BaseTabAdapter {

    private List<TabItem> items;
    private Context context;


    public DeafultTabAdapter(Context context, List<TabItem> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public TabItem getItem(int position) {
        if (items != null) {
            return items.get(position);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    @Override
    public BubbleTabItemLayout getView(TabConfig tabConfig, int position) {
        TabItem tabItem = items.get(position);
        BubbleTabItemLayout tableLayout = new BubbleTabItemLayout(context);
        int imageBg = tabItem.getImageBg();
        String tabText = tabItem.getTabText();
        boolean showBubble = tabItem.isShowBubble();
        if (imageBg != View.NO_ID) {
            tableLayout.setImageBg(imageBg);
        }
        if (!TextUtils.isEmpty(tabText)) {
            tableLayout.setTabText(tabText);
        }
        tableLayout.isCanShowBubble(showBubble);

        float scope = tabConfig.getScope();
        int textDefaultColor = tabConfig.getTextDefaultColor();
        int textFilterColor = tabConfig.getTextFilterColor();
        int tabSlidingMode = tabConfig.getTabSlidingMode();
        float tabTextSize = tabConfig.getTabTextSize();
        tableLayout.setScope(scope);
        tableLayout.setTabSlidingMode(tabSlidingMode);
        TabTextView textView = tableLayout.getTextView();
        TabImageView iconView = tableLayout.getIconView();
        if (textView != null) {
            textView.setTextDefaultColor(textDefaultColor);
            textView.setmTextSize(tabTextSize);
            textView.setTextSelectColor(textFilterColor);
        }
        if (iconView != null) {
            iconView.setTabSlidingMode(tabSlidingMode);
            iconView.setTabFilterColor(textFilterColor);
        }
        tableLayout.setClickAble(tabItem.isClickAble());
        return tableLayout;
    }

    public List<TabItem> getItems() {
        return items;
    }

}
