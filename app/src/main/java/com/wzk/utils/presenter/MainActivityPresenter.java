package com.wzk.utils.presenter;


import com.wzk.callback.RequestCallBack;
import com.wzk.mvp.Presenter.BasePresenter;
import com.wzk.utils.entity.NewTopResponseInfo;
import com.wzk.utils.model.MainActivityModel;
import com.wzk.utils.model.MainActivityModelImpl;
import com.wzk.utils.view.MainActivityView;

import java.util.Map;

/**
 * Created by wangzhaokang on 2019/1/9.
 */

public class MainActivityPresenter<T extends MainActivityView> extends BasePresenter<T> {

    MainActivityModel model = new MainActivityModelImpl<>();

    public void getServiceData(String url, Map<String,Object> params){

        model.getServiceData(url, params, new RequestCallBack<NewTopResponseInfo>() {
            @Override
            protected void onSuccess(NewTopResponseInfo resultBean) {

                mViewRef.get().onServiceDataSuccess(resultBean);
            }

            @Override
            public void onFailure(int status, String message) {
                mViewRef.get().onServiceDataFailure(message);
            }
        });

    }


}
