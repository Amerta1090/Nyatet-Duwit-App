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
public final class AddDebtPaymentUseCase_Factory implements Factory<AddDebtPaymentUseCase> {
  private final Provider<DebtRepository> repositoryProvider;

  public AddDebtPaymentUseCase_Factory(Provider<DebtRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AddDebtPaymentUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static AddDebtPaymentUseCase_Factory create(Provider<DebtRepository> repositoryProvider) {
    return new AddDebtPaymentUseCase_Factory(repositoryProvider);
  }

  public static AddDebtPaymentUseCase newInstance(DebtRepository repository) {
    return new AddDebtPaymentUseCase(repository);
  }
}
