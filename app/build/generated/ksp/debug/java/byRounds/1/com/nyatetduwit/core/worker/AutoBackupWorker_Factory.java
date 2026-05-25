package com.nyatetduwit.core.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.nyatetduwit.data.local.ExportManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class AutoBackupWorker_Factory {
  private final Provider<ExportManager> exportManagerProvider;

  public AutoBackupWorker_Factory(Provider<ExportManager> exportManagerProvider) {
    this.exportManagerProvider = exportManagerProvider;
  }

  public AutoBackupWorker get(Context appContext, WorkerParameters params) {
    return newInstance(appContext, params, exportManagerProvider.get());
  }

  public static AutoBackupWorker_Factory create(Provider<ExportManager> exportManagerProvider) {
    return new AutoBackupWorker_Factory(exportManagerProvider);
  }

  public static AutoBackupWorker newInstance(Context appContext, WorkerParameters params,
      ExportManager exportManager) {
    return new AutoBackupWorker(appContext, params, exportManager);
  }
}
