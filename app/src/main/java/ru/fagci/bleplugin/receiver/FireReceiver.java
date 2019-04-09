package ru.fagci.bleplugin.receiver;

import android.content.*;
import android.os.*;
import android.support.annotation.*;
import com.twofortyfouram.locale.sdk.client.receiver.*;
import ru.fagci.bleplugin.*;
import ru.fagci.bleplugin.bundles.*;

public class FireReceiver extends AbstractPluginSettingReceiver {

	@Override
	protected boolean isBundleValid(@NonNull Bundle bundle) {
		return PluginBundleValues.isBundleValid(bundle);
	}

	@Override
	protected boolean isAsync() {
		return false;
	}

	@Override
	protected void firePluginSetting(@NonNull Context context, @NonNull Bundle bundle) {
		final String message = bundle.getString(PluginBundleValues.BUNDLE_EXTRA_STRING_MESSAGE);
		if ( isOrderedBroadcast() )  {
			setResultCode( TaskerPlugin.Setting.RESULT_CODE_OK );
		Bundle resultBundle = new Bundle();
		//setResultCode( TaskerPlugin.Setting.RESULT_CODE_PENDING );
		resultBundle.putString("%resultmsg", "boo from fire recver with: "+message);
		//setResultCode(TaskerPlugin.Setting.RESULT_CODE_OK);

		//if ( TaskerPlugin.Setting.hostSupportsVariableReturn(intent.getExtras())) {

		TaskerPlugin.addVariableBundle(getResultExtras(true), resultBundle);
		//}

		//	Toast.makeText(context, "isorder",Toast.LENGTH_SHORT);
		}
	}
}
