package is.uncommon.layoutinflationtalk.library;

import android.support.annotation.AttrRes;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

/**
 * An attribute listener that can receive dimension values (dp, sp, etc.).
 */
public abstract class BalloonDimensionAttributeListener<V extends View> extends BalloonAttributeListener<V, Float> {

    private static final int UNKNOWN = -1;

    @Override
    protected void internalOnApplyAttribute(V inflatedView, @AttrRes int attributeResId, String rawAttributeValue) {
        if (rawAttributeValue.contains(".")) {
            throw new UnsupportedOperationException("Fractional values aren't valid dimension values");
        }

        int valueWithoutUnit = Integer.parseInt(rawAttributeValue.substring(0, rawAttributeValue.length() - 2));
        int typedValueUnit = getTypedValueUnit(rawAttributeValue);

        if (typedValueUnit == UNKNOWN) {
            String attributeName = getAttributeName(inflatedView.getContext(), attributeResId);
            throw new UnsupportedOperationException("Couldn't determine value of attribute: " + attributeName);
        }

        DisplayMetrics displayMetrics = inflatedView.getResources().getDisplayMetrics();
        float valueInPx = TypedValue.applyDimension(typedValueUnit, valueWithoutUnit, displayMetrics);
        onApplyAttribute(inflatedView, attributeResId, valueInPx);
    }

    private int getTypedValueUnit(String rawAttributeValue) {
        if (rawAttributeValue.endsWith("dp")) {
            return TypedValue.COMPLEX_UNIT_DIP;

        } else if (rawAttributeValue.endsWith("sp")) {
            return TypedValue.COMPLEX_UNIT_SP;

        } else if (rawAttributeValue.endsWith("in")) {
            return TypedValue.COMPLEX_UNIT_IN;

        } else if (rawAttributeValue.endsWith("mm")) {
            return TypedValue.COMPLEX_UNIT_MM;

        } else if (rawAttributeValue.endsWith("pt")) {
            return TypedValue.COMPLEX_UNIT_PT;

        } else if (rawAttributeValue.endsWith("px")) {
            return TypedValue.COMPLEX_UNIT_PX;

        } else {
            return UNKNOWN;
        }
    }

}
