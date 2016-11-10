package is.uncommon.layoutinflationtalk.library;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.List;

/**
 * Extracts custom attributes from Views during the inflation process.
 */
public class BalloonCustomAttributesHandler {

    /**
     * Handle the created view.
     */
    public void onViewCreated(@NonNull View view, AttributeSet attributeSet) {
        List<Integer> registeredAttributeIds = Balloon.config().registeredAttributeIds();

        for (Integer attributeResId : registeredAttributeIds) {
            try {
                Object resolvedValue = resolveAttribute(view.getContext(), attributeSet, attributeResId);

                if (resolvedValue != null) {
                    BalloonAttributeListener attributeListener = Balloon.config().attrListenerFor(attributeResId);
                    //noinspection unchecked
                    attributeListener.internalOnApplyAttribute(view, attributeResId, String.valueOf(resolvedValue));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Resolving font path from xml attrs, style attrs or theme.
     */
    private Object resolveAttribute(Context context, AttributeSet attributeSet, int attributeResId) {
        Object resolvedAttrValue;

        resolvedAttrValue = pullAttributeValueFromView(context, attributeSet, attributeResId);

        if (resolvedAttrValue == null) {
            resolvedAttrValue = pullAttributeValueFromStyle(context, attributeSet, attributeResId);
        }

        if (resolvedAttrValue == null) {
            resolvedAttrValue = pullAttributeValueFromTheme(context, attributeResId);
        }

        return resolvedAttrValue;
    }

    /**
     * Tries to pull the custom attribute directly from the View.
     *
     * @return null if the attribute wasn't found defined on the View.
     */
    @Nullable
    static Object pullAttributeValueFromView(Context context, AttributeSet attributeSet, int attributeResId) {
        final String attributeName;
        try {
            attributeName = context.getResources().getResourceEntryName(attributeResId);
        } catch (Resources.NotFoundException e) {
            // Invalid attribute ID
            return null;
        }
        return attributeSet.getAttributeValue(null, attributeName);
    }

    /**
     * Tries to pull the custom attribute from the View Style.
     *
     * @return null if the attribute wasn't found defined in the View's style.
     */
    @Nullable
    static Object pullAttributeValueFromStyle(Context context, AttributeSet attributeSet, int attributeResId) {
        // Get the theme defined on the View.
        @StyleRes int style = attributeSet.getStyleAttribute();

        // Skip if the View doesn't have any style.
        if (style == 0) {
            return null;
        }

        final TypedArray typedArray = context.obtainStyledAttributes(style, new int[] { attributeResId });
        if (typedArray != null) {
            try {
                if (!typedArray.hasValue(0)) {
                    return null;
                }

                TypedValue typedValue = new TypedValue();
                boolean valueRetrieved = typedArray.getValue(0, typedValue);
                if (valueRetrieved) {
                    return parseTypedValueData(typedValue);
                }

            } catch (Exception ignore) {
                // Failed for some reason.
                ignore.printStackTrace();

            } finally {
                typedArray.recycle();
            }
        }
        return null;
    }

    /**
     * Last but not least, try to pull the Font Path from the Theme, if defined.
     */
    @Nullable
    static Object pullAttributeValueFromTheme(Context context, int attributeResId) {
        final TypedValue value = new TypedValue();
        boolean valueRetrieved = context.getTheme().resolveAttribute(attributeResId, value, true);
        return valueRetrieved ? parseTypedValueData(value) : null;
    }

    private static Object parseTypedValueData(TypedValue value) {
        switch (value.type) {
            case TypedValue.TYPE_STRING:
                return value.string;

            case TypedValue.TYPE_REFERENCE:
                return "@" + value.data;

            case TypedValue.TYPE_ATTRIBUTE:
                return "?" + value.data;

            case TypedValue.TYPE_FLOAT:
                return Float.toString(Float.intBitsToFloat(value.data));

            case TypedValue.TYPE_DIMENSION:
                return TypedValue.complexToFloat(value.data);

            case TypedValue.TYPE_FRACTION:
                return TypedValue.complexToFloat(value.data) * 100;

            case TypedValue.TYPE_INT_HEX:
                return "0x" + Integer.toHexString(value.data);

            case TypedValue.TYPE_INT_BOOLEAN:
                return value.data != 0 ? "true" : "false";

            case TypedValue.TYPE_NULL:
                return null;

            default:
                if (value.type >= TypedValue.TYPE_FIRST_COLOR_INT && value.type <= TypedValue.TYPE_LAST_COLOR_INT) {
                    return "#" + Integer.toHexString(value.data);

                } else if (value.type >= TypedValue.TYPE_FIRST_INT && value.type <= TypedValue.TYPE_LAST_INT) {
                    return Integer.toString(value.data);
                }
                return null;
        }
    }

}
