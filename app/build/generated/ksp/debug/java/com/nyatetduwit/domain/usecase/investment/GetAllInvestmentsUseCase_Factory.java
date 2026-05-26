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
public final class GetAllInvestmentsUseCase_Factory implements Factory<GetAllInvestmentsUseCase> {
  private final Provider<InvestmentRepository> repositoryProvider;

  public GetAllInvestmentsUseCase_Factory(Provider<InvestmentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetAllInvestmentsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetAllInvestmentsUseCase_Factory create(
      Provider<InvestmentRepository> repositoryProvider) {
    return new GetAllInvestmentsUseCase_Factory(repositoryProvider);
  }

  public static GetAllInvestmentsUseCase newInstance(InvestmentRepository repository) {
    return new GetAllInvestmentsUseCase(repository);
  }
}
