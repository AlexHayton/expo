// Copyright 2015-present 650 Industries. All rights reserved.

package abi41_0_0.host.exp.exponent.modules.internal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import abi41_0_0.com.facebook.react.bridge.ReactApplicationContext;
import abi41_0_0.com.facebook.react.module.annotations.ReactModule;
import abi41_0_0.com.facebook.react.modules.storage.AsyncStorageModule;
import abi41_0_0.com.facebook.react.modules.storage.ReactDatabaseSupplier;
import host.exp.exponent.ExponentManifest;
import host.exp.exponent.kernel.KernelProvider;

@ReactModule(name = ExponentAsyncStorageModule.NAME, canOverrideExistingModule = true)
public class ExponentAsyncStorageModule extends AsyncStorageModule {

  public static String experienceIdToDatabaseName(String experienceId) throws UnsupportedEncodingException {
    String experienceIdEncoded = URLEncoder.encode(experienceId, "UTF-8");
    return "RKStorage-scoped-experience-" + experienceIdEncoded;
  }

  public ExponentAsyncStorageModule(ReactApplicationContext reactContext, JSONObject manifest) {
    super(reactContext);

    try {
      String experienceId = manifest.getString(ExponentManifest.MANIFEST_ID_KEY);
      String databaseName = experienceIdToDatabaseName(experienceId);
      mReactDatabaseSupplier = new ReactDatabaseSupplier(reactContext, databaseName);
    } catch (JSONException e) {
      KernelProvider.getInstance().handleError("Requires Experience Id");
    } catch (UnsupportedEncodingException e) {
      KernelProvider.getInstance().handleError("Couldn't URL encode Experience Id");
    }
  }

  @Override
  public boolean canOverrideExistingModule() {
    return true;
  }
}
