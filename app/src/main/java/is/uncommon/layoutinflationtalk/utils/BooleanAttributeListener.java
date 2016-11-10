package is.uncommon.layoutinflationtalk.utils;

import android.support.annotation.AttrRes;
import android.view.View;

import is.uncommon.layoutinflationtalk.library.BalloonBooleanAttributeListener;

public class BooleanAttributeListener extends BalloonBooleanAttributeListener<View> {

    @Override
    public void onApplyAttribute(View inflatedView, @AttrRes int attributeResId, Boolean bool) {
        inflatedView.setEnabled(bool);
    }

}
