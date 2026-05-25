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
public final class DeactivateInvestmentUseCase_Factory implements Factory<DeactivateInvestmentUseCase> {
  private final Provider<InvestmentRepository> repositoryProvider;

  public DeactivateInvestmentUseCase_Factory(Provider<InvestmentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeactivateInvestmentUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeactivateInvestmentUseCase_Factory create(
      Provider<InvestmentRepository> repositoryProvider) {
    return new DeactivateInvestmentUseCase_Factory(repositoryProvider);
  }

  public static DeactivateInvestmentUseCase newInstance(InvestmentRepository repository) {
    return new DeactivateInvestmentUseCase(repository);
  }
}
