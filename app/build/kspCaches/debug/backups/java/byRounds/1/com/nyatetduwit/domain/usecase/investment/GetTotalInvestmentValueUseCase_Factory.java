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
public final class GetTotalInvestmentValueUseCase_Factory implements Factory<GetTotalInvestmentValueUseCase> {
  private final Provider<InvestmentRepository> repositoryProvider;

  public GetTotalInvestmentValueUseCase_Factory(Provider<InvestmentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTotalInvestmentValueUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTotalInvestmentValueUseCase_Factory create(
      Provider<InvestmentRepository> repositoryProvider) {
    return new GetTotalInvestmentValueUseCase_Factory(repositoryProvider);
  }

  public static GetTotalInvestmentValueUseCase newInstance(InvestmentRepository repository) {
    return new GetTotalInvestmentValueUseCase(repository);
  }
}
