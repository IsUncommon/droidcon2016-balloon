package is.uncommon.layoutinflationtalk.utils;

import android.support.annotation.AttrRes;
import android.text.InputFilter;
import android.widget.EditText;

import is.uncommon.layoutinflationtalk.library.BalloonStringAttributeListener;

/**
 * Listens for "formatter" attribute on EditText Views.
 */
public class EditTextFormatterAttributeListener extends BalloonStringAttributeListener<EditText> {

    @Override
    public void onApplyAttribute(EditText inflatedView, @AttrRes int attributeResId, String attributeValue) {
        inflatedView.setHint(attributeValue);

        switch (attributeValue) {
            case "IndianRupees":
                inflatedView.setFilters(new InputFilter[] { new IndianInputFormatter() });
                break;

            case "CreditCardNumber":
                inflatedView.setFilters(new InputFilter[] { new CardInputFormatter() });
                break;
        }
    }

}
