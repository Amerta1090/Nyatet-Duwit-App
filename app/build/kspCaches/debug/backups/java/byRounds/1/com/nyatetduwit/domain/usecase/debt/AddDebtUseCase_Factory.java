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
public final class AddDebtUseCase_Factory implements Factory<AddDebtUseCase> {
  private final Provider<DebtRepository> repositoryProvider;

  public AddDebtUseCase_Factory(Provider<DebtRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AddDebtUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static AddDebtUseCase_Factory create(Provider<DebtRepository> repositoryProvider) {
    return new AddDebtUseCase_Factory(repositoryProvider);
  }

  public static AddDebtUseCase newInstance(DebtRepository repository) {
    return new AddDebtUseCase(repository);
  }
}
