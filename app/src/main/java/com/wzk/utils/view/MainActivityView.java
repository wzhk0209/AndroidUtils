package com.wzk.utils.view;

import com.wzk.mvp.view.BaseView;
import com.wzk.utils.entity.NewTopResponseInfo;

/**
 * Created by wangzhaokang on 2019/1/9.
 */

public interface MainActivityView extends BaseView {

    void onServiceDataSuccess(NewTopResponseInfo responseInfo);

    void onServiceDataFailure(String message);

}
