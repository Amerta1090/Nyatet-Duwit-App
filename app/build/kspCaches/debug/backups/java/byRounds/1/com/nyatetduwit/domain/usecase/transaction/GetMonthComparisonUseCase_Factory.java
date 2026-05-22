package com.nyatetduwit.domain.usecase.transaction;

import com.nyatetduwit.domain.repository.TransactionRepository;
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
public final class GetMonthComparisonUseCase_Factory implements Factory<GetMonthComparisonUseCase> {
  private final Provider<TransactionRepository> repositoryProvider;

  public GetMonthComparisonUseCase_Factory(Provider<TransactionRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetMonthComparisonUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetMonthComparisonUseCase_Factory create(
      Provider<TransactionRepository> repositoryProvider) {
    return new GetMonthComparisonUseCase_Factory(repositoryProvider);
  }

  public static GetMonthComparisonUseCase newInstance(TransactionRepository repository) {
    return new GetMonthComparisonUseCase(repository);
  }
}
