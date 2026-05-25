package com.nyatetduwit.presentation.cashflow;

import com.nyatetduwit.domain.usecase.transaction.AnomalyDetectionUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class CashflowTrendViewModel_Factory implements Factory<CashflowTrendViewModel> {
  private final Provider<AnomalyDetectionUseCase> anomalyDetectionUseCaseProvider;

  public CashflowTrendViewModel_Factory(
      Provider<AnomalyDetectionUseCase> anomalyDetectionUseCaseProvider) {
    this.anomalyDetectionUseCaseProvider = anomalyDetectionUseCaseProvider;
  }

  @Override
  public CashflowTrendViewModel get() {
    return newInstance(anomalyDetectionUseCaseProvider.get());
  }

  public static CashflowTrendViewModel_Factory create(
      Provider<AnomalyDetectionUseCase> anomalyDetectionUseCaseProvider) {
    return new CashflowTrendViewModel_Factory(anomalyDetectionUseCaseProvider);
  }

  public static CashflowTrendViewModel newInstance(
      AnomalyDetectionUseCase anomalyDetectionUseCase) {
    return new CashflowTrendViewModel(anomalyDetectionUseCase);
  }
}
