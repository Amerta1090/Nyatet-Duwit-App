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
public final class GetTotalBudgetUseCase_Factory implements Factory<GetTotalBudgetUseCase> {
  private final Provider<BudgetRepository> repositoryProvider;

  public GetTotalBudgetUseCase_Factory(Provider<BudgetRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTotalBudgetUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTotalBudgetUseCase_Factory create(
      Provider<BudgetRepository> repositoryProvider) {
    return new GetTotalBudgetUseCase_Factory(repositoryProvider);
  }

  public static GetTotalBudgetUseCase newInstance(BudgetRepository repository) {
    return new GetTotalBudgetUseCase(repository);
  }
}
