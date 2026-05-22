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
public final class GetBudgetByIdUseCase_Factory implements Factory<GetBudgetByIdUseCase> {
  private final Provider<BudgetRepository> repositoryProvider;

  public GetBudgetByIdUseCase_Factory(Provider<BudgetRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetBudgetByIdUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetBudgetByIdUseCase_Factory create(Provider<BudgetRepository> repositoryProvider) {
    return new GetBudgetByIdUseCase_Factory(repositoryProvider);
  }

  public static GetBudgetByIdUseCase newInstance(BudgetRepository repository) {
    return new GetBudgetByIdUseCase(repository);
  }
}
