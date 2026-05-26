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
public final class DeleteBudgetUseCase_Factory implements Factory<DeleteBudgetUseCase> {
  private final Provider<BudgetRepository> repositoryProvider;

  public DeleteBudgetUseCase_Factory(Provider<BudgetRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteBudgetUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteBudgetUseCase_Factory create(Provider<BudgetRepository> repositoryProvider) {
    return new DeleteBudgetUseCase_Factory(repositoryProvider);
  }

  public static DeleteBudgetUseCase newInstance(BudgetRepository repository) {
    return new DeleteBudgetUseCase(repository);
  }
}
