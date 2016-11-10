package is.uncommon.layoutinflationtalk.library;

import android.support.annotation.AttrRes;
import android.view.View;

/**
 * An attribute listener that can receive String values.
 */
public abstract class BalloonStringAttributeListener<V extends View> extends BalloonAttributeListener<V, String> {

    @Override
    protected void internalOnApplyAttribute(V inflatedView, @AttrRes int attributeResId, String rawAttributeValue) {
        onApplyAttribute(inflatedView, attributeResId, rawAttributeValue);
    }

}
