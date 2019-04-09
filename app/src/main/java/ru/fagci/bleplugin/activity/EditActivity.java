package ru.fagci.bleplugin.activity;

import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.twofortyfouram.annotation.*;
import com.twofortyfouram.locale.sdk.client.ui.activity.*;
import com.twofortyfouram.spackle.bundle.*;
import ru.fagci.bleplugin.*;
import ru.fagci.bleplugin.bundles.*;

public class EditActivity extends AbstractPluginActivity {
	EditText messageEditText;
	Button saveButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		final Bundle localeBundle = intent.getBundleExtra(com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE);

		BundleScrubber.scrub(intent);
		BundleScrubber.scrub(localeBundle);

		setContentView(R.layout.activity_edit);

		messageEditText = findViewById(R.id.text1);
		saveButton = findViewById(R.id.activity_editSaveButton);

		saveButton.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1) {
					mIsCancelled = false;
					finish();
				}
			});

		mIsCancelled = true; // if other events except save finish activity, then cancelled

		if (null != savedInstanceState) {
			Toast.makeText(this, "saved instance is not null", Toast.LENGTH_SHORT).show();
			return;
		}

		if (null == localeBundle) {
			Toast.makeText(this, "locale bundle is null", Toast.LENGTH_SHORT).show();
			return;
		}

		if (!PluginBundleValues.isBundleValid(localeBundle)) {
			Toast.makeText(this, "saved instance is null and locale data is invalid", Toast.LENGTH_SHORT).show();
			return;
		}

		final String message = localeBundle.getString(PluginBundleValues.BUNDLE_EXTRA_STRING_MESSAGE);
		messageEditText.setText(message);
	}

	@Override
	public void finish() {
		Bundle resultBundle = getResultBundle();

		if (mIsCancelled || null == resultBundle) {
			setResult(RESULT_CANCELED);
			super.finish();
			Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
			return;
		}

		Intent resultIntent = new Intent();


		resultIntent.putExtra(com.twofortyfouram.locale.api.Intent.EXTRA_BUNDLE, resultBundle);
		resultIntent.putExtra(com.twofortyfouram.locale.api.Intent.EXTRA_STRING_BLURB, getResultBlurb(resultBundle));

		TaskerPlugin.addRelevantVariableList(resultIntent, new String [] { 
																					 "%resultmsg\nResultMsg"
																				 });

		setResult(RESULT_OK, resultIntent);


		if (TaskerPlugin.Setting.hostSupportsSynchronousExecution(getIntent().getExtras()))
			TaskerPlugin.Setting.requestTimeoutMS(resultIntent, 3000);

		super.finish();

		Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onPostCreateWithPreviousResult(@NonNull Bundle bundle, @NonNull String s) {
		final String message = getResultBlurb(bundle);
		messageEditText.setText(message);
		Toast.makeText(this, "on post create with prev. result", Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean isBundleValid(@NonNull Bundle bundle) {
		return PluginBundleValues.isBundleValid(bundle);
	}

	@Nullable
	@Override
	public Bundle getResultBundle() {
		Bundle result = null;

		final String message = messageEditText.getText().toString();

		if (message.isEmpty()) {
			return null;
		}

		result = PluginBundleValues.generateBundle(getApplicationContext(), message);

		if (null == result) return null;

		//TaskerPlugin.Setting.setVariableReplaceKeys(result, new String [] { PluginBundleValues.BUNDLE_EXTRA_STRING_MESSAGE });

		return result;
	}

	@NonNull
	@Override
	public String getResultBlurb(@NonNull Bundle bundle) {
		final String message = PluginBundleValues.getMessage(bundle);
		return message;
	}
}
