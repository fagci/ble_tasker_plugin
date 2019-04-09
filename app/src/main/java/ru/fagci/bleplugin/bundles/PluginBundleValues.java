package ru.fagci.bleplugin.bundles;

import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.support.annotation.*;
import com.twofortyfouram.assertion.*;
import com.twofortyfouram.log.*;
import net.jcip.annotations.*;

import static com.twofortyfouram.assertion.Assertions.assertNotEmpty;
import static com.twofortyfouram.assertion.Assertions.assertNotNull;

@ThreadSafe
public final class PluginBundleValues
{
    public static final String BUNDLE_EXTRA_STRING_MESSAGE = "ru.fagci.bleplugin.extra.STRING_MESSAGE";
    private static final String BUNDLE_EXTRA_INT_VERSION_CODE = "ru.fagci.bleplugin.extra.VERSION_CODE";

    public static boolean isBundleValid(@Nullable final Bundle bundle)
		{
        if (null == bundle)
				{
            return false;
        }

        try
				{
            BundleAssertions.assertHasString(bundle, BUNDLE_EXTRA_STRING_MESSAGE, false, false);
            BundleAssertions.assertHasInt(bundle, BUNDLE_EXTRA_INT_VERSION_CODE);
            BundleAssertions.assertKeyCount(bundle, 2);
        }
				catch (final AssertionError e)
				{
            Lumberjack.e("Bundle failed verification%s", e); //$NON-NLS-1$
            return false;
        }

        return true;
    }

    @NonNull
    public static Bundle generateBundle(@NonNull final Context context, @NonNull final String message)
		{
        assertNotNull(context, "context"); //$NON-NLS-1$
        assertNotEmpty(message, "message"); //$NON-NLS-1$

        final Bundle result = new Bundle();
        try
				{
						result.putInt(BUNDLE_EXTRA_INT_VERSION_CODE, context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
				}
				catch (PackageManager.NameNotFoundException e)
				{}
        result.putString(BUNDLE_EXTRA_STRING_MESSAGE, message);

        return result;
    }

    /**
     * @param bundle A valid plug-in bundle.
     * @return The message inside the plug-in bundle.
     */
    @NonNull
    public static String getMessage(@NonNull final Bundle bundle)
		{
        return bundle.getString(BUNDLE_EXTRA_STRING_MESSAGE);
    }

    /**
     * Private constructor prevents instantiation
     *
     * @throws UnsupportedOperationException because this class cannot be instantiated.
     */
    private PluginBundleValues()
		{
        throw new UnsupportedOperationException("This class is non-instantiable"); //$NON-NLS-1$
    }
}
