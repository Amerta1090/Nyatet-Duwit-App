package com.nyatetduwit.domain.usecase.debt;

import com.nyatetduwit.domain.repository.DebtRepository;
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
public final class GetActiveDebtsUseCase_Factory implements Factory<GetActiveDebtsUseCase> {
  private final Provider<DebtRepository> repositoryProvider;

  public GetActiveDebtsUseCase_Factory(Provider<DebtRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetActiveDebtsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetActiveDebtsUseCase_Factory create(Provider<DebtRepository> repositoryProvider) {
    return new GetActiveDebtsUseCase_Factory(repositoryProvider);
  }

  public static GetActiveDebtsUseCase newInstance(DebtRepository repository) {
    return new GetActiveDebtsUseCase(repository);
  }
}
