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
public final class AddBudgetUseCase_Factory implements Factory<AddBudgetUseCase> {
  private final Provider<BudgetRepository> repositoryProvider;

  public AddBudgetUseCase_Factory(Provider<BudgetRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AddBudgetUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static AddBudgetUseCase_Factory create(Provider<BudgetRepository> repositoryProvider) {
    return new AddBudgetUseCase_Factory(repositoryProvider);
  }

  public static AddBudgetUseCase newInstance(BudgetRepository repository) {
    return new AddBudgetUseCase(repository);
  }
}
