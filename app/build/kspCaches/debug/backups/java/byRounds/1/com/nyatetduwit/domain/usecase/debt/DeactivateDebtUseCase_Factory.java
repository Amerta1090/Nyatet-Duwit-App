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
public final class DeactivateDebtUseCase_Factory implements Factory<DeactivateDebtUseCase> {
  private final Provider<DebtRepository> repositoryProvider;

  public DeactivateDebtUseCase_Factory(Provider<DebtRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeactivateDebtUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeactivateDebtUseCase_Factory create(Provider<DebtRepository> repositoryProvider) {
    return new DeactivateDebtUseCase_Factory(repositoryProvider);
  }

  public static DeactivateDebtUseCase newInstance(DebtRepository repository) {
    return new DeactivateDebtUseCase(repository);
  }
}
