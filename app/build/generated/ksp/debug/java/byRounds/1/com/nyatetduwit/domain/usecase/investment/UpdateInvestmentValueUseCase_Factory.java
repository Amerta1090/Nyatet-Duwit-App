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
public final class UpdateInvestmentValueUseCase_Factory implements Factory<UpdateInvestmentValueUseCase> {
  private final Provider<InvestmentRepository> repositoryProvider;

  public UpdateInvestmentValueUseCase_Factory(Provider<InvestmentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UpdateInvestmentValueUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UpdateInvestmentValueUseCase_Factory create(
      Provider<InvestmentRepository> repositoryProvider) {
    return new UpdateInvestmentValueUseCase_Factory(repositoryProvider);
  }

  public static UpdateInvestmentValueUseCase newInstance(InvestmentRepository repository) {
    return new UpdateInvestmentValueUseCase(repository);
  }
}
