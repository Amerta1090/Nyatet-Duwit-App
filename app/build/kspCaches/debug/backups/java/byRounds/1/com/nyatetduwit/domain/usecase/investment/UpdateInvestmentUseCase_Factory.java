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
public final class UpdateInvestmentUseCase_Factory implements Factory<UpdateInvestmentUseCase> {
  private final Provider<InvestmentRepository> repositoryProvider;

  public UpdateInvestmentUseCase_Factory(Provider<InvestmentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateInvestmentUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateInvestmentUseCase_Factory create(
      Provider<InvestmentRepository> repositoryProvider) {
    return new UpdateInvestmentUseCase_Factory(repositoryProvider);
  }

  public static UpdateInvestmentUseCase newInstance(InvestmentRepository repository) {
    return new UpdateInvestmentUseCase(repository);
  }
}
