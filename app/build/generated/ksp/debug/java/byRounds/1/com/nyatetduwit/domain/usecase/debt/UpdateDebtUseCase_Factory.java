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
public final class UpdateDebtUseCase_Factory implements Factory<UpdateDebtUseCase> {
  private final Provider<DebtRepository> repositoryProvider;

  public UpdateDebtUseCase_Factory(Provider<DebtRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateDebtUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateDebtUseCase_Factory create(Provider<DebtRepository> repositoryProvider) {
    return new UpdateDebtUseCase_Factory(repositoryProvider);
  }

  public static UpdateDebtUseCase newInstance(DebtRepository repository) {
    return new UpdateDebtUseCase(repository);
  }
}
