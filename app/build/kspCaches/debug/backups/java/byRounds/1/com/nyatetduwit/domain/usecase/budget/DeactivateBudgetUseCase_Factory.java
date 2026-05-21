package com.nyatetduwit.domain.usecase.budget;

import com.nyatetduwit.domain.repository.BudgetRepository;
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
public final class DeactivateBudgetUseCase_Factory implements Factory<DeactivateBudgetUseCase> {
  private final Provider<BudgetRepository> repositoryProvider;

  public DeactivateBudgetUseCase_Factory(Provider<BudgetRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeactivateBudgetUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeactivateBudgetUseCase_Factory create(
      Provider<BudgetRepository> repositoryProvider) {
    return new DeactivateBudgetUseCase_Factory(repositoryProvider);
  }

  public static DeactivateBudgetUseCase newInstance(BudgetRepository repository) {
    return new DeactivateBudgetUseCase(repository);
  }
}
