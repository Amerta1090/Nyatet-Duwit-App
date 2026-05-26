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
public final class GetInvestmentByIdUseCase_Factory implements Factory<GetInvestmentByIdUseCase> {
  private final Provider<InvestmentRepository> repositoryProvider;

  public GetInvestmentByIdUseCase_Factory(Provider<InvestmentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetInvestmentByIdUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetInvestmentByIdUseCase_Factory create(
      Provider<InvestmentRepository> repositoryProvider) {
    return new GetInvestmentByIdUseCase_Factory(repositoryProvider);
  }

  public static GetInvestmentByIdUseCase newInstance(InvestmentRepository repository) {
    return new GetInvestmentByIdUseCase(repository);
  }
}
