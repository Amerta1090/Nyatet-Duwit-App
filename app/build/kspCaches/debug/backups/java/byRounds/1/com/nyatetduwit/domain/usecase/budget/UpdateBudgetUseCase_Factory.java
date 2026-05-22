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
public final class UpdateBudgetUseCase_Factory implements Factory<UpdateBudgetUseCase> {
  private final Provider<BudgetRepository> repositoryProvider;

  public UpdateBudgetUseCase_Factory(Provider<BudgetRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateBudgetUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateBudgetUseCase_Factory create(Provider<BudgetRepository> repositoryProvider) {
    return new UpdateBudgetUseCase_Factory(repositoryProvider);
  }

  public static UpdateBudgetUseCase newInstance(BudgetRepository repository) {
    return new UpdateBudgetUseCase(repository);
  }
}
