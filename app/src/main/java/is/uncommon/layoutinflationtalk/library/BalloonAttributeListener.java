package is.uncommon.layoutinflationtalk.library;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.AttrRes;
import android.view.View;

/**
 * See: {@link BalloonStringAttributeListener}, {@link BalloonDimensionAttributeListener} and {@link BalloonBooleanAttributeListener}.
 * TODO: Get rid of type specific attribute listeners.
 *
 * <p>
 * Interface for classes that are registered for custom attributes using {@link Balloon.Builder#registerAttr(int, BalloonAttributeListener)}.
 * All registered instances of this interface will be stored in a static instance, so make sure you're not leaking any memory.
 *
 * @param <V> Type of the View where you're using the attribute. You cab either pass in a specific View like "TextView" or a
 *            generic "View"/"ViewGroup", etc., when the attribute is being used in multiple types of Views.
 * @param <A> Value type the attribute supports.
 */
abstract class BalloonAttributeListener<V extends View, A> {

    /**
     * Gets called whenever the registered attribute is found in a View being inflated.
     *
     * @param inflatedView The inflated View.
     */
    public abstract void onApplyAttribute(V inflatedView, @AttrRes int attributeResId, A attributeValue);

    abstract void internalOnApplyAttribute(V inflatedView, @AttrRes int attributeResId, String rawAttributeValue);

    protected String getAttributeName(Context context, @AttrRes int attributeResId) {
        try {
            return context.getResources().getResourceEntryName(attributeResId);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }

}
