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
public final class GetTotalCostBasisUseCase_Factory implements Factory<GetTotalCostBasisUseCase> {
  private final Provider<InvestmentRepository> repositoryProvider;

  public GetTotalCostBasisUseCase_Factory(Provider<InvestmentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetTotalCostBasisUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetTotalCostBasisUseCase_Factory create(
      Provider<InvestmentRepository> repositoryProvider) {
    return new GetTotalCostBasisUseCase_Factory(repositoryProvider);
  }

  public static GetTotalCostBasisUseCase newInstance(InvestmentRepository repository) {
    return new GetTotalCostBasisUseCase(repository);
  }
}
