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
public final class DeleteDebtUseCase_Factory implements Factory<DeleteDebtUseCase> {
  private final Provider<DebtRepository> repositoryProvider;

  public DeleteDebtUseCase_Factory(Provider<DebtRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteDebtUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteDebtUseCase_Factory create(Provider<DebtRepository> repositoryProvider) {
    return new DeleteDebtUseCase_Factory(repositoryProvider);
  }

  public static DeleteDebtUseCase newInstance(DebtRepository repository) {
    return new DeleteDebtUseCase(repository);
  }
}
