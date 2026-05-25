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
public final class GetDebtByIdUseCase_Factory implements Factory<GetDebtByIdUseCase> {
  private final Provider<DebtRepository> repositoryProvider;

  public GetDebtByIdUseCase_Factory(Provider<DebtRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetDebtByIdUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetDebtByIdUseCase_Factory create(Provider<DebtRepository> repositoryProvider) {
    return new GetDebtByIdUseCase_Factory(repositoryProvider);
  }

  public static GetDebtByIdUseCase newInstance(DebtRepository repository) {
    return new GetDebtByIdUseCase(repository);
  }
}
