# Tablibrary
初始化工程
# Toolbar

#### 使用自定义drawable实现底部菜单滑动的功能。支持两种方式。
<ol>
<li>普通滑动方式</li>
<li>仿微信滑动方式</li>
</ol>


<img src="/shapshot/shapshot.gif" alt="alt text" style="width:200;height:200">

#Usage

1. 添加xml布局.

```xml
    <com.safewaychina.tabmenu.TabLayout
        android:id="@+id/tab_layout"
        android:layout_width="match_parent"
        android:layout_height="55dp"
        android:background="#2196F3"
        app:tab_filter_color="#FFE0B2"
        app:tab_scope="5dp"
        app:tab_silidingMode="tab_selector"
        app:tab_text_default_color="@android:color/white" />               
```

2.  设置布局.

```java
       TabItem item = new TabItem("消息", R.drawable.dr_msg, true, true);
        TabItem item2 = new TabItem("任务", R.drawable.dr_task, true, true);
        TabItem item3 = new TabItem(false, "", R.drawable.dr_menu_check, false);
        TabItem item4 = new TabItem("应用", R.drawable.dr_index, true);
        TabItem item5 = new TabItem("发现", R.drawable.dr_found, true);
        //TabItem item6 = new TabItem("设置",R.drawable.dr_setting);
        items = new ArrayList();
        items.add(item);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        
        DeafultTabAdapter adapter = new DeafultTabAdapter(this.getApplicationContext(), items);
        tabLayout.setAdapter(adapter);
        tabLayout.setFragmentAdapter(adapter, getSupportFragmentManager(), mTabs, R.id.menu_fragment);
        tabLayout.setCurrentFragment(isNotificationClick(getIntent()) ? 0 : 3);
        tabLayout.setSelectValidator(new UserAuthValidator());
```

 



#Compatibility
  
  * Android GINGERBREAD 2.3+
  
# 历史记录


### Version: 1.0

  * 初始化编译


## License

    Copyright 2015, liuhaifang

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
