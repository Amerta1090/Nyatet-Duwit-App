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
public final class DeleteDebtPaymentUseCase_Factory implements Factory<DeleteDebtPaymentUseCase> {
  private final Provider<DebtRepository> repositoryProvider;

  public DeleteDebtPaymentUseCase_Factory(Provider<DebtRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteDebtPaymentUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteDebtPaymentUseCase_Factory create(
      Provider<DebtRepository> repositoryProvider) {
    return new DeleteDebtPaymentUseCase_Factory(repositoryProvider);
  }

  public static DeleteDebtPaymentUseCase newInstance(DebtRepository repository) {
    return new DeleteDebtPaymentUseCase(repository);
  }
}
