package is.uncommon.layoutinflationtalk.library;

import android.support.annotation.AttrRes;
import android.view.View;

/**
 * An attribute listener that can receive boolean values.
 */
public abstract class BalloonBooleanAttributeListener<V extends View> extends BalloonAttributeListener<V, Boolean> {

    @Override
    void internalOnApplyAttribute(V inflatedView, @AttrRes int attributeResId, String rawAttributeValue) {
        onApplyAttribute(inflatedView, attributeResId, Boolean.parseBoolean(rawAttributeValue));
    }

}
