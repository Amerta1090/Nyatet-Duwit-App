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
public final class DeleteInvestmentUseCase_Factory implements Factory<DeleteInvestmentUseCase> {
  private final Provider<InvestmentRepository> repositoryProvider;

  public DeleteInvestmentUseCase_Factory(Provider<InvestmentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DeleteInvestmentUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static DeleteInvestmentUseCase_Factory create(
      Provider<InvestmentRepository> repositoryProvider) {
    return new DeleteInvestmentUseCase_Factory(repositoryProvider);
  }

  public static DeleteInvestmentUseCase newInstance(InvestmentRepository repository) {
    return new DeleteInvestmentUseCase(repository);
  }
}
