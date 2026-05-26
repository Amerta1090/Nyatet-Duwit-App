package com.nyatetduwit.domain.usecase.investment;

import com.nyatetduwit.domain.repository.InvestmentRepository;
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
public final class AddInvestmentUseCase_Factory implements Factory<AddInvestmentUseCase> {
  private final Provider<InvestmentRepository> repositoryProvider;

  public AddInvestmentUseCase_Factory(Provider<InvestmentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public AddInvestmentUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static AddInvestmentUseCase_Factory create(
      Provider<InvestmentRepository> repositoryProvider) {
    return new AddInvestmentUseCase_Factory(repositoryProvider);
  }

  public static AddInvestmentUseCase newInstance(InvestmentRepository repository) {
    return new AddInvestmentUseCase(repository);
  }
}
