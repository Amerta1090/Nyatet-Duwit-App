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
public final class GetActiveInvestmentsUseCase_Factory implements Factory<GetActiveInvestmentsUseCase> {
  private final Provider<InvestmentRepository> repositoryProvider;

  public GetActiveInvestmentsUseCase_Factory(Provider<InvestmentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetActiveInvestmentsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetActiveInvestmentsUseCase_Factory create(
      Provider<InvestmentRepository> repositoryProvider) {
    return new GetActiveInvestmentsUseCase_Factory(repositoryProvider);
  }

  public static GetActiveInvestmentsUseCase newInstance(InvestmentRepository repository) {
    return new GetActiveInvestmentsUseCase(repository);
  }
}
