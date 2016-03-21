/*
 * Copyright 2016 Hippo Seven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hippo.ehviewer.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.hippo.ehviewer.R;
import com.hippo.ehviewer.Settings;
import com.hippo.widget.lockpattern.LockPatternUtils;
import com.hippo.widget.lockpattern.LockPatternView;
import com.hippo.yorozuya.ViewUtils;

public class SetSecurityActivity extends ToolbarActivity implements View.OnClickListener {

    @Nullable
    private LockPatternView mPatternView;
    @Nullable
    private View mCancel;
    @Nullable
    private View mSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_security);
        setNavigationIcon(R.drawable.v_arrow_left_dark_x24);

        mPatternView = (LockPatternView) ViewUtils.$$(this, R.id.pattern_view);
        mCancel = ViewUtils.$$(this, R.id.cancel);
        mSet = ViewUtils.$$(this, R.id.set);

        String pattern = Settings.getSecurity();
        if (!TextUtils.isEmpty(pattern)) {
            mPatternView.setPattern(LockPatternView.DisplayMode.Correct,
                    LockPatternUtils.stringToPattern(pattern));
        }

        mCancel.setOnClickListener(this);
        mSet.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPatternView = null;
    }

    @Override
    public void onClick(View v) {
        if (v == mCancel) {
            finish();
        } else if (v == mSet) {
            if (null != mPatternView) {
                String security;
                if (mPatternView.getCellSize() <= 1) {
                    security = "";
                } else {
                    security = mPatternView.getPatternString();
                }
                Settings.putSecurity(security);
            }
            finish();
        }
    }
}
